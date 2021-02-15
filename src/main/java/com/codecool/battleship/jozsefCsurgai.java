package com.codecool.battleship;

import java.util.Arrays;
import java.util.Scanner;

public class jozsefCsurgai {

    // Invalid input 1: outside of range
    public static boolean isInsideRange(int pickRow, int pickCol, char[][] playerBoard) {

        boolean isInside = (playerBoard.length > pickRow && pickRow >= 0) &&
                (playerBoard[0].length > pickCol && pickCol >= 0);

        return isInside;
    }

    public static void displayIfOutsideRange(boolean isInsideRange) {
        if (!isInsideRange) {
            System.out.println("Nem érvényes!\nKérlek a táblán belül válaszd ki a pozíciót!");
        }
    }

    // Invalid input 2: not correct input
    public static boolean isValidType(String input){

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        try{
            Integer.parseInt(input.toLowerCase());
            return true;
        } catch(NumberFormatException ex) {
            int insideAlphabet = alphabet.indexOf(input.toLowerCase());
            if (input.length() == 1 && insideAlphabet > -1) {
                return true;
            }
        }
        return false;
    }
    public static void displayValidType(boolean validation) {
        if (!validation) {
            System.out.println("Nem érvényes!\nKérlek, megfelelően írd be a pozíciót a táblára (pl. 'B5')!");
        }
    }

    // Invalid input 3: already hit
    public static boolean isAlreadyHit(char[][] boardPlayer, int rowShoot, int colShoot) {
        char posShoot = boardPlayer[rowShoot + 1][colShoot];
        if (posShoot == 'M' || posShoot == 'H' || posShoot == 'S') return true;
        return false;
    }

    // Shooting phase
    // Begin game
    public static void beginGameScreen(String player) {

        System.out.println("Zsír, Kezdjük a játékot!");
        System.out.println("Minden kérésnél írd be előbb az oszlop, aztán a sor pozícióját (pl. A3 vagy a3)");
        System.out.print(player + " fogja kezdeni! Kérem az első lövést: ");
    }

    public static void boardDrawerPhantom(char[][] playerBoard) {
        char element;
        for (int row = 0; row < playerBoard.length; row++){
            for (int col = 0; col < playerBoard[row].length; col++){
                element = playerBoard[row][col];
                if (element == 'X') element = 'O';
                System.out.print(" " + element);
            }
            System.out.println();
        }
    }

    public static char[][] boardAfterShoot(char[][] boardPlayer, int[][]boardRecognized, int rowShoot, int colShoot) {

        int[][] boardWithSingleDoubleShips = boardRecognized;
        char posShoot = boardPlayer[rowShoot + 1][colShoot];
        switch (posShoot) {
            case 'O': {
                istvanTotin.playSound("src/media/donthit.wav");
                try {
                    Thread.sleep(2000);
                    posShoot = 'M';
                    System.out.println("Nem találtad el!");
                    break;
                } catch (Exception ex) {

                }
            }
            case 'X': {
                istvanTotin.playSound("src/media/hit.wav");
                try {
                    Thread.sleep(2000);
                    if (boardWithSingleDoubleShips[rowShoot + 1][colShoot] == 2) {
                        posShoot = 'H';
                        System.out.println("Eltaláltál egy hajót!");
                    } else if (boardWithSingleDoubleShips[rowShoot + 1][colShoot] == 1) {
                        posShoot = 'S';
                        System.out.println("Elsüllyesztettél egy hajót!");
                    }
                    break;
                } catch (Exception ex) {

                }
            }
        }
        boardPlayer[rowShoot + 1][colShoot] = posShoot;
        return boardPlayer;
    }

    public static int[][] boardWithSingleDoubleShipRecognized(char[][] playerBoard) {

        int[][]changedBoard = new int[playerBoard.length][playerBoard[0].length];
        for (int i = 0; i < changedBoard.length; i++) {
            for (int j = 0; j < changedBoard[0].length; j++) {
                if (playerBoard[i][j] == 'X') {
                    changedBoard[i][j] = 1;
                } else {
                    changedBoard[i][j] = 0;
                }
            }
        }
        // check in rows
        for (int i = 0; i < changedBoard.length; i++) {
            for (int j = 0; j < changedBoard[0].length - 1; j++) {
                if (playerBoard[i][j] == 'X' && playerBoard[i][j + 1] == 'X') {
                    changedBoard[i][j] = 2;
                    changedBoard[i][j + 1] = 2;
                }
            }
        }
        // check in columns
        for (int i = 0; i < changedBoard[0].length; i++) {
            for (int j = 0; j < changedBoard.length - 1; j++) {
                if (playerBoard[j][i] == 'X' && playerBoard[j + 1][i] == 'X') {
                    changedBoard[j][i] = 2;
                    changedBoard[j + 1][i] = 2;
                }
            }
        }
        return changedBoard;
    }

    public static char[][] boardChangeToDestroyed(char[][] playerBoard) {

        char[][]changedBoard = playerBoard;
        // check in rows
        for (int i = 0; i < changedBoard.length; i++) {
            for (int j = 0; j < changedBoard[0].length - 1; j++) {
                if (playerBoard[i][j] == 'H' && playerBoard[i][j + 1] == 'H') {
                    changedBoard[i][j] = 'S';
                    changedBoard[i][j + 1] = 'S';
                    System.out.println("Elsüllyesztettél egy hajót!");
                }
            }
        }
        // check in columns
        for (int i = 0; i < changedBoard[0].length; i++) {
            for (int j = 0; j < changedBoard.length - 1; j++) {
                if (playerBoard[j][i] == 'H' && playerBoard[j + 1][i] == 'H') {
                    changedBoard[j][i] = 'S';
                    changedBoard[j + 1][i] = 'S';
                    System.out.println("Elsüllyesztettél egy hajót!");
                }
            }
        }
        return changedBoard;
    }

    public static boolean isNeighborCoordinateAfterHit( char[][] playerBoard, int row, int col) {
        try {
            if (row == playerBoard.length-1) {
                if (playerBoard[row - 1][col] == 'H'|| playerBoard[row][col - 1] == 'H' || playerBoard[row][col + 1] == 'H') {
                    return true;
                } else {
                    return false;
                }
            } else if (col == playerBoard[row].length-1) {
                if (playerBoard[row - 1][col] == 'H'|| playerBoard[row + 1][col] == 'H' || playerBoard[row][col - 1] == 'H') {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (playerBoard[row - 1][col] == 'H'|| playerBoard[row + 1][col] == 'H' || playerBoard[row][col - 1] == 'H' || playerBoard[row][col + 1] == 'H') {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            return false;
        }
    }

    public static boolean isSingleShip( char[][] playerBoard, int row, int col) {
        try {
            if (row == playerBoard.length-1) {
                if (playerBoard[row - 1][col] == 'O'|| playerBoard[row][col - 1] == 'O' || playerBoard[row][col + 1] == 'O') {
                    return true;
                } else {
                    return false;
                }
            } else if (col == playerBoard[row].length-1) {
                if (playerBoard[row - 1][col] == 'O'|| playerBoard[row + 1][col] == 'O' || playerBoard[row][col - 1] == 'O') {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (playerBoard[row - 1][col] == 'O'|| playerBoard[row + 1][col] == 'O' || playerBoard[row][col - 1] == 'O' || playerBoard[row][col + 1] == 'O') {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            return false;
        }
    }

    public static int countingShips(char[][] boardPlayer) {

        int i = 0;
        for (char[] boardRow : boardPlayer) {
            for (char stateCell : boardRow) {
                if (stateCell == 'X') i++;
            }
        }
        return i;

    }

    // GAME OVER screen
    public static void gameOver(String winner, String loser) {
        System.out.println("\nVÉGE A JÁTÉKNAK!\n");
        System.out.println("A győztes " + winner + "!");
        System.out.println("Sajnálom " + loser + ", esetleg a következő alkalommal sikerül...\n");
        System.out.println("Még egy játékot (nyomj bármilyen gombot vagy írd be ezt: quit)?");
    }

    public static void maybeExitGame(String input) {
        if (input.equals("quit")) System.exit(0);
    }

}
