package aoc2023.day7;

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

        // Part 1
        Collections.sort(hands);
        int rank = 1;
        Long winnings = 0L;
        for(Hand hand : hands) {
            System.out.println(rank + " " + hand.cards + " " + hand.handType.name());
            winnings += (hand.bet * rank);
            rank++;
        }
        System.out.println("Winnings Part 1: " + winnings);


        // Part 2
        for(Hand hand : hands) {
            hand.processPart2();
        }
        Collections.sort(hands);
        rank = 1;
        winnings = 0L;
        for(Hand hand : hands) {
            System.out.println(rank + " " + hand.cards + " " + hand.handType.name());
            winnings += (hand.bet * rank);
            rank++;
        }
        System.out.println("Winnings Part 2: " + winnings);
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
            handType = getHandType(true, true);
        }

        public void processPart2() {
            // Updater Jack's value from 11 to 1 in the cards list
            for(int i = 0; i < 5; i++) {
                if (cards.get(i) == 11) {
                    cards.set(i, 1);
                }
            }
            // Get the jacks from the card map
            Integer numJacks = cardMap.get(11);
            // remove jacks from the card map
            cardMap.remove(11);

            if (numJacks != null) {
                if (numJacks == 5) {
                    // Convert JJJJJ to AAAAA
                    cardMap.put(14, 5);
                } else if (numJacks == 4) {
                    // Convert to 5 of a kind of the non-jack card
                    for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                        if (card.getValue() > 0) {
                            cardMap.put(card.getKey(), 5);
                        }
                    }
                } else if (numJacks == 3) {
                    // Convert HIGH_CARD to 4 of a kind
                    // Convert ONE_PAIR to 5 of a kind
                    for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                        if (card.getValue() == 2) {
                            // this was a pair, so convert to 5 of a kind
                            cardMap.put(card.getKey(), 5);
                        } else {
                            cardMap.put(card.getKey(), 4);
                            // break out of for loop, so we don't set the second single card to a 4 of a kind
                            break;
                        }
                    }
                } else if (numJacks == 2) {
                    // Convert 3 of a kind to 5 of a kind
                    // Convert one pair to 4 of a kind
                    // Convert high card to 3 of a kind
                    if (cardMap.containsValue(3)) {
                        // contains a 3 of a kind
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            // 3 of a kind + 2 jacks = 5 of a kind
                            cardMap.put(card.getKey(), 5);
                        }
                    } else if (cardMap.containsValue(2)) {
                        // contains a pair
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            // pair + 2 jacks = 4 of a kind
                            if (card.getValue() == 2) {
                                cardMap.put(card.getKey(), 4);
                            }
                        }
                    } else if (cardMap.containsValue(1)) {
                        // Convert high card to 3 of a kind
                        Integer keyWithHighestValue = null;
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            if (keyWithHighestValue == null) {
                                keyWithHighestValue = card.getKey();
                            } else {
                                keyWithHighestValue = card.getKey() > keyWithHighestValue ? card.getKey() : keyWithHighestValue;
                            }
                        }
                        cardMap.put(keyWithHighestValue, 3);
                    }
                } else if (numJacks == 1) {
                    // Convert 4 of a kind to 5 of a kind
                    // Convert 3 of a kind to 4 of a kind
                    // Convert 2 pair to Full House
                    // Convert pair to 3 of a kind
                    // Convert High Card to Pair
                    if (cardMap.containsValue(4)) {
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            cardMap.put(card.getKey(), 5);
                        }
                    } else if (cardMap.containsValue(3)) {
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            if (card.getValue() == 3) {
                                cardMap.put(card.getKey(), 4);
                            }
                        }
                    } else if (cardMap.containsValue(2)) {
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            if (card.getValue() == 2) {
                                cardMap.put(card.getKey(), 3);
                                break;
                            }
                        }
                    } else if (cardMap.containsValue(1)) {
                        for (Map.Entry<Integer, Integer> card : cardMap.entrySet()) {
                            if (card.getValue() == 1) {
                                cardMap.put(card.getKey(), 2);
                                break;
                            }
                        }
                    }
                }
            }
            getHandType(true, false);
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


        private HandType getHandType(boolean force, boolean buildCardMap) {
            if (force) {
                handType = null;
            }
            if (handType == null) {
                if (buildCardMap) {
                    for (Integer card : cards) {
                        Integer count = cardMap.get(card);
                        if (count != null) {
                            cardMap.put(card, ++count);
                        } else {
                            cardMap.put(card, 1);
                        }
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