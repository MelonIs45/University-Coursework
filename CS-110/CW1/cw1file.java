/* Ryan Pietras (2261876)
 * Subtract A Square Assignment (CS-110)
 */

/* A program that asks the user to choose a heap and square amount
 * of coins to subtract and then removes the amount of coins from
 * the heap until there are none left
 */
import java.util.Scanner;

public class CS1File {
    public static void main(String[] args) {
        final Integer maxCoins = 13;
        final Integer maxHeaps = 3;

        Integer[] coinHeap = {maxCoins, maxCoins, maxCoins};

        Scanner input = new Scanner(System.in);
        Integer currentTurn = 0;
        boolean gameEnded = false;

        Player[] players = {new Player(), new Player()};

        while (!gameEnded) {
            // Flips the current players turn number.
            Integer opponentIndex = currentTurn + 1 > 1 ? 0 : 1;
            Integer heapSelected = 0;

            System.out.format("Remaining coins: %s, %s, %s\n",
                    coinHeap[0], coinHeap[1], coinHeap[2]);

            boolean validHeap = false;
            while (!validHeap) {
                System.out.format("Player %s: choose a heap: ", currentTurn + 1);

                /* Removes whitespace to account for invisible characters,
                 * (\\s for any space character).
                 */
                String number = input.nextLine().replaceAll("\\s", "");

                boolean wantsToSkip = number.equalsIgnoreCase("skip");
                if (wantsToSkip) {
                    // Checks if the player has skipped yet.
                    if (!players[currentTurn].getSkipStatus()) {
                        players[currentTurn].setSkipStatus(true);
                        currentTurn = opponentIndex;
                        // Flips the current players turn number again.
                        opponentIndex = currentTurn + 1 > 1 ? 0 : 1;
                    } else {
                        System.out.format("Sorry you have used your skip.\n");
                    }
                }

                if (isNumber(number)) {
                    heapSelected = Integer.parseInt(number);
                    // Checks if the heap selected is within 1-3
                    if (heapSelected > 0 && heapSelected <= maxHeaps
                            // Checks if the coin heap is not empty.
                            && coinHeap[heapSelected - 1] != 0) {
                        validHeap = true;
                    } else {
                        System.out.format("Sorry, that's not a legal heap choice.\n",
                                number);
                    }
                } else if (!wantsToSkip) {
                    System.out.format("Sorry you must enter an integer or skip.\n");
                }
            }

            // Grabs the number of coins in the selected heap
            Integer currentCoinHeap = coinHeap[heapSelected - 1];
            Integer coinsToRemove = 0;

            boolean validRemove = false;
            while (!validRemove) {
                System.out.format("Now choose a square number of coins: ",
                opponentIndex + 1, heapSelected, currentCoinHeap);

                /* Removes whitespace to account for invisible characters,
                 * (\\s for any space character).
                 */
                String number = input.nextLine().replaceAll("\\s", "");

                if (isNumber(number)) {
                    coinsToRemove = Integer.parseInt(number);

                    Double sqrt = Math.sqrt(coinsToRemove);
                    // Checks if the coins selected is within 1-amount in the heap
                    if (coinsToRemove > 0 && coinsToRemove <= currentCoinHeap
                        // Checks if the number input is a perfect square.
                        && sqrt - Math.floor(sqrt) == 0
                        ) {
                        validRemove = true;
                    } else {
                        System.out.format("Sorry that's not a legal number "
                                + "of coins for that heap.\n");
                    }
                } else {
                    System.out.format("Sorry you must enter an integer.\n");
                }
            }
            // Subtracts the users input from the heap they selected.
            coinHeap[heapSelected - 1] -= coinsToRemove;

            if (checkIfAllEmpty(coinHeap)) {
                System.out.format("Player %d wins!\n", currentTurn + 1);
                gameEnded = true;
                input.close();
            }

            /* Makes the current players turn
             * number the same as the opponents.
             */
            currentTurn = opponentIndex;
        }
    }

    /* Checks if a string is a number by
     * trying to parse it as an integer
     * and seeing if it throws an error.
     */
    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException err) {
            return false;
        }
    }

    /* Goes through all the heaps and if any
     * have more than one coin then they are empty.
     */
    public static boolean checkIfAllEmpty(Integer[] coinHeap) {
        boolean allEmpty = true;
        for (int i = 0; i < coinHeap.length; i++) {
            if (coinHeap[i] > 0) {
                allEmpty = false;
            }
        }
        return allEmpty;
    }
}

/* Player class to hold the skip information.
 * Might have been simpler to just hard code it
 * since there's only 2 players.
 */
class Player {
    private boolean skippedTurn = false;

    public boolean getSkipStatus() {
        return skippedTurn;
    }

    public void setSkipStatus(boolean newStatus) {
        skippedTurn = newStatus;
    }
}
