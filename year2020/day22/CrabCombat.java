/* Licensed under Apache-2.0 */
package advent.year2020.day22;

import java.util.*;

public class CrabCombat {
    public static class Game {
        public final int subGameNumber;
        public final Set<String> previousRoundKeys = new HashSet<>();
        public final List<Integer>[] playersCards;
        public int indexWinningSubGamePlayer = -1;

        public Game(List<Integer>[] playersCards, int subGameNumber) {
            this.playersCards = new List[playersCards.length];

            for (int i = 0; i < this.playersCards.length; i++) {
                this.playersCards[i] = new LinkedList<>(playersCards[i]);
            }

            this.subGameNumber = subGameNumber;
        }
    }

    public static int getWinningPlayerScore(String input) {
        String[] playerInputs = input.split("\n\n");

        Queue<Integer>[] playersCards = new Queue[playerInputs.length];
        int numCardsCount = 0;

        for (int i = 0; i < playerInputs.length; i++) {
            String[] cards = playerInputs[i].split("\n");
            Queue<Integer> playerCards = new LinkedList<>();

            for (int j = 1; j < cards.length; j++) {
                playerCards.add(Integer.parseInt(cards[j]));
                numCardsCount++;
            }

            playersCards[i] = playerCards;
        }

        final int numCards = numCardsCount;

        boolean doesOnePlayerHaveAllCards = Arrays.stream(playersCards).anyMatch((p) -> p.size() == numCards);

        int indexPlayerWithMax = 0;
        while (!doesOnePlayerHaveAllCards) {
            List<Integer> roundPlayersCards = new LinkedList<>();

            Integer maxValueCard = null;
            for (int i = 0; i < playersCards.length; i++) {
                if (playersCards[i].size() > 0) {
                    Integer cardValue = playersCards[i].remove();

                    if (maxValueCard == null) {
                        indexPlayerWithMax = i;
                        maxValueCard = cardValue;
                    } else if (cardValue > maxValueCard) {
                        roundPlayersCards.add(indexPlayerWithMax, maxValueCard);

                        indexPlayerWithMax = i;
                        maxValueCard = cardValue;
                    } else {
                        roundPlayersCards.add(cardValue);
                    }
                }
            }

            playersCards[indexPlayerWithMax].add(maxValueCard);
            for (Integer cardValue : roundPlayersCards) {
                playersCards[indexPlayerWithMax].add(cardValue);
            }

            doesOnePlayerHaveAllCards = Arrays.stream(playersCards).anyMatch((p) -> p.size() == numCards);
        }

        int winningPlayerValue = 0;
        while (playersCards[indexPlayerWithMax].size() > 0) {
            int cardValue = playersCards[indexPlayerWithMax].remove();
            winningPlayerValue += cardValue * (playersCards[indexPlayerWithMax].size() + 1);
        }

        return winningPlayerValue;
    }

    public static int getWinningRecursivePlayerScore(String input) {
        String[] playerInputs = input.split("\n\n");

        List<Integer>[] playersCards = new List[playerInputs.length];

        for (int i = 0; i < playerInputs.length; i++) {
            String[] cards = playerInputs[i].split("\n");
            List<Integer> playerCards = new LinkedList<>();

            for (int j = 1; j < cards.length; j++) {
                playerCards.add(Integer.parseInt(cards[j]));
            }

            playersCards[i] = playerCards;
        }

        Stack<Game> gamesToPlay = new Stack<>();
        gamesToPlay.add(new Game(playersCards, 1));

        int indexPlayerWithMaxAllGames = 0;
        while (gamesToPlay.size() > 0) {
            Game game = gamesToPlay.pop();

            if (game.indexWinningSubGamePlayer >= 0) {
                game.playersCards[game.indexWinningSubGamePlayer].add(game.playersCards[game.indexWinningSubGamePlayer].remove(0));

                for (int i = 0; i < game.playersCards.length; i++) {
                    if (game.playersCards[i].size() > 0 && i != game.indexWinningSubGamePlayer) {
                        game.playersCards[game.indexWinningSubGamePlayer].add(game.playersCards[i].remove(0));
                    }
                }
            }

            int gameCardCount = Arrays.stream(game.playersCards).mapToInt((q) -> q.size()).reduce(0, (m, i) -> m + i);
            boolean doesOnePlayerHaveAllCards = Arrays.stream(game.playersCards).anyMatch((p) -> p.size() == gameCardCount);
            int indexPlayerWithMax = -1;

            if (doesOnePlayerHaveAllCards) {
                for (int i = 0; i < game.playersCards.length; i++) {
                    if (game.playersCards[i].size() == gameCardCount) {
                        indexPlayerWithMax = i;
                        break;
                    }
                }
            }

            while (!doesOnePlayerHaveAllCards) {
                List<Integer> roundPlayersCards = new LinkedList<>();

                String roundKey = Arrays.stream(game.playersCards).map((p) -> p.stream().map(Object::toString).reduce("", (m, s) -> String.format("%s,%s", m, s))).reduce("", (m, s) -> String.format("%s   %s", m, s));
                if (game.previousRoundKeys.contains(roundKey)) {
                    indexPlayerWithMax = 0;
                    break;
                } else {
                    boolean allPlayersHandsLargerThanTheirCards = true;
                    for (List<Integer> playerCards : game.playersCards) {
                        if (playerCards.size() > 0 && playerCards.get(0) > playerCards.size() - 1) {
                            allPlayersHandsLargerThanTheirCards = false;
                            break;
                        }
                    }

                    if (allPlayersHandsLargerThanTheirCards) {
                        List<Integer>[] subPlayerCards = new List[game.playersCards.length];

                        for (int i = 0; i < game.playersCards.length; i++) {
                            int subCardLength = Math.min(game.playersCards[i].get(0), game.playersCards[i].size());
                            subPlayerCards[i] = game.playersCards[i].subList(1, subCardLength + 1);
                        }

                        Game subGame = new Game(subPlayerCards, game.subGameNumber + 1);

                        gamesToPlay.add(game);
                        gamesToPlay.add(subGame);
                        indexPlayerWithMax = -1;
                        break;
                    }

                    Integer maxValueCard = null;
                    for (int i = 0; i < game.playersCards.length; i++) {
                        if (game.playersCards[i].size() > 0) {
                            Integer cardValue = game.playersCards[i].remove(0);

                            if (maxValueCard == null) {
                                indexPlayerWithMax = i;
                                maxValueCard = cardValue;
                            } else if (cardValue > maxValueCard) {
                                roundPlayersCards.add(indexPlayerWithMax, maxValueCard);

                                indexPlayerWithMax = i;
                                maxValueCard = cardValue;
                            } else {
                                roundPlayersCards.add(cardValue);
                            }
                        }
                    }

                    game.playersCards[indexPlayerWithMax].add(maxValueCard);
                    for (Integer cardValue : roundPlayersCards) {
                        game.playersCards[indexPlayerWithMax].add(cardValue);
                    }

                    doesOnePlayerHaveAllCards = Arrays.stream(game.playersCards).anyMatch((p) -> p.size() == gameCardCount);

                    game.previousRoundKeys.add(roundKey);
                }
            }

            if (gamesToPlay.size() > 0) {
                gamesToPlay.peek().indexWinningSubGamePlayer = indexPlayerWithMax;
            } else {
                indexPlayerWithMaxAllGames = indexPlayerWithMax;
                playersCards = game.playersCards;
                break;
            }
        }


        int winningPlayerValue = 0;
        while (playersCards[indexPlayerWithMaxAllGames].size() > 0) {
            int cardValue = playersCards[indexPlayerWithMaxAllGames].remove(0);

            winningPlayerValue += cardValue * (playersCards[indexPlayerWithMaxAllGames].size() + 1);
        }

        return winningPlayerValue;
    }
}
