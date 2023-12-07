package day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CamelCards {

    List<Hand> hands = new ArrayList<>();

    public void play(List<String> hands) {
        for (String hand : hands) {
            String[] parts = hand.split("\\s+");
            this.hands.add(new Hand(parts[0], Integer.parseInt(parts[1])));
        }

        Collections.sort(hands);
    }




    class Hand implements Comparable {

        Integer bet;
        List<Integer> cards;

        public Hand(String cardsString, Integer bet) {
            this.bet = bet;

            for(char c : cardsString.toCharArray()) {
                switch(c) {
                    case 'A':
                        cards.add(14);
                        break;
                    case 'K':
                        cards.add(13);
                        break;
                    case 'Q':
                        cards.add(12);
                        break;
                    case 'J':
                        cards.add(11);
                        break;
                    case 'T':
                        cards.add(10);
                        break;
                    default:
                        cards.add(Character.getNumericValue(c));
                }
            }

        }

        @Override
        public int compareTo(Object o) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        }


        private HandType getHandType() {

            return HandType.HIGH_CARD;
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