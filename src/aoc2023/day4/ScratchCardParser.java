package aoc2023.day4;

import java.util.ArrayList;
import java.util.List;

public class ScratchCardParser {

    List<ScratchCard> cards = new ArrayList<>();
    public void parse(List<String> scratchCards) {
        Integer cardNo = 0;
        Integer totalPoints = 0;
        for (String cardLine : scratchCards) {
            ScratchCard card = new ScratchCard(cardNo, cardLine.split(":")[1]);
            cards.add(card);
            totalPoints += card.getPointsPartOne();
            cardNo++;
        }
        System.out.println("Total Points: " + totalPoints);

        // part 2
        Integer totalCards = 0;
        for (ScratchCard card : cards) {
            // System.out.println("Recursively processing Card " + card.getCardNo());
            totalCards += totalWinningCards(card) + 1;
            // System.out.println("Current Total: " + totalCards);
        }
        System.out.println("Total Cards: " + totalCards);
    }

    private Integer totalWinningCards(ScratchCard card) {
        int winsThisCard = card.getWinningCardsPartTwo();
//        System.out.println("Processing card: " + card.getCardNo() + " - Total Wins: " + winsThisCard);

        Integer total = winsThisCard;

        if (winsThisCard > 0 ) {
            for (int index = card.getCardNo()+1; index <= (card.getCardNo() + winsThisCard); index++) {
                if (index < cards.size()) {
                    total += totalWinningCards(cards.get(index));
                }
            }
        }
        return total;
    }


    class ScratchCard {
        private Integer cardNo;
        private List<Integer> winningNumbers;
        private List<Integer> playedNumbers;

        private Integer totalWinningNumbers;

        public ScratchCard(Integer cardNo, String input) {
            this.cardNo = cardNo;
            String[] numbers = input.split("\\|");

            winningNumbers = parseNumbers(numbers[0]);
            playedNumbers = parseNumbers(numbers[1]);

            // part 2
            int matchingNumbers = 0;
            for (Integer number : playedNumbers) {
                if (winningNumbers.contains(number)) {
                    matchingNumbers++;
                }
            }
            totalWinningNumbers = matchingNumbers;
        }

        public Integer getPointsPartOne() {
            Integer points = 0;
            for (Integer number : playedNumbers) {
                if (winningNumbers.contains(number)) {
                    if (points == 0) {
                        points = 1;
                    } else {
                        points *= 2;
                    }
                }
            }
            return points;
        }

        public Integer getWinningCardsPartTwo() {
            return totalWinningNumbers;
        }

        private List<Integer> parseNumbers(String numberString) {
            List<Integer> numberList = new ArrayList<Integer>();
            String[] numbers = numberString.split(" ");
            for (String num : numbers) {
                if (num != null && num != "") {
                    numberList.add(Integer.parseInt(num));
                }
            }
            return numberList;
        }

        private Integer getCardNo() {
            return cardNo;
        }

    }
}
