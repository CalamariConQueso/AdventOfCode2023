package day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CamelCards {

    List<Hand> hands = new ArrayList<>();

    public void play(List<String> handList) {
        for (String hand : handList) {
            String[] parts = hand.split("\\s+");
            this.hands.add(new Hand(parts[0], Integer.parseInt(parts[1])));
        }

        Collections.sort(hands);

        int rank = 1;
        Long winnings = 0L;
        for(Hand hand : hands) {
            System.out.println(hand.cards + " " + hand.bet);
            winnings += (hand.bet * rank);
            rank++;
        }
        System.out.println("Winnings: " + winnings);
    }




    class Hand implements Comparable<Hand> {

        Integer bet;
        List<Integer> cards = new ArrayList<>();
        Map<Integer, Integer> cardMap = new HashMap<>();
        HandType handType = null;

        public Hand(String cardsString, Integer bet) {
            this.bet = bet;

            for(char c : cardsString.toCharArray()) {
                switch (c) {
                    case 'A' -> cards.add(14);
                    case 'K' -> cards.add(13);
                    case 'Q' -> cards.add(12);
                    case 'J' -> cards.add(11);
                    case 'T' -> cards.add(10);
                    default -> cards.add(Character.getNumericValue(c));
                }
            }
            handType = getHandType();
        }

        @Override
        public int compareTo(Hand inputHand) {
            if (this.handType != inputHand.handType) {
                return this.handType.ordinal() - inputHand.handType.ordinal();
            } else {
                // same hand type, compare cards
                for (int i = 0; i < 5; i++) {
                    if (!Objects.equals(this.cards.get(i), inputHand.cards.get(i))) {
                        return this.cards.get(i) - inputHand.cards.get(i);
                    }
                }
            }
            // if we made it here, that means these are exactly the same hands - same handType, and all cards match.
            return 0;
        }


        private HandType getHandType() {
            if (handType == null) {
                for (Integer card : cards) {
                    Integer count = cardMap.get(card);
                    if (count != null) {
                        cardMap.put(card, ++count);
                    } else {
                        cardMap.put(card, 1);
                    }
                }

                HandType type = HandType.HIGH_CARD;
                if (cardMap.containsValue(5)) {
                    type = HandType.FIVE_OF_A_KIND;
                } else if (cardMap.containsValue(4)) {
                    type = HandType.FOUR_OF_A_KIND;
                } else if (cardMap.containsValue(3)) {
                    type = HandType.THREE_OF_A_KIND;
                    if (cardMap.containsValue(2)) {
                        type = HandType.FULL_HOUsE;
                    }
                } else if (cardMap.containsValue(2)) {
                    int pairs = 0;
                    for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                        if (card.getValue() == 2) {
                            pairs++;
                        }
                    }
                    type = pairs > 1 ? HandType.TWO_PAIR : HandType.ONE_PAIR;
                }
                handType = type;
            }
            return handType;
        }

        enum HandType {
            HIGH_CARD,
            ONE_PAIR,
            TWO_PAIR,
            THREE_OF_A_KIND,
            FULL_HOUsE,
            FOUR_OF_A_KIND,
            FIVE_OF_A_KIND
        }

    }

}