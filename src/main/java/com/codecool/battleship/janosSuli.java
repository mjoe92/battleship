package com.codecool.battleship;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class janosSuli {

    /*public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] players = welcomeScreen();
        String playerOne = players[0], playerTwo = players[1];
        char[][] playerOneBoard = boardSizeConfig(playerOne, playerTwo);
        char[][] playerTwoBoard = arrayCopier(playerOneBoard);
        playerOneBoard = boardInitializer(playerOneBoard);
        Display.displayInitializer(playerOneBoard);
        playerOneBoard = playerBoardShipFiller(playerOneBoard, playerOne);
        System.out.println();
        System.out.println("A másik játékos következik!");
        scanner.nextLine();
        playerTwoBoard = boardInitializer(playerTwoBoard);
        playerTwoBoard = playerBoardShipFiller(playerTwoBoard, playerTwo);
        System.out.println();
        System.out.println(playerOne);
        System.out.println();
        boardDrawer(playerOneBoard);

        System.out.println();
        System.out.println(playerTwo);
        System.out.println();
        boardDrawer(playerTwoBoard);
    }*/

    public static String[] welcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        String[] playersNames = new String[2];

        System.out.println();
        System.out.println("Üdvözöllek a Torpedó című játékban!");
        System.out.println("Feladatod az ellenfél hajóinak elpusztítása lesz!");
        System.out.println("A játékban két parancsnok küzd egymás ellen. " +
                "Az nyer, aki az ellenfél utolsó hajóját is elpusztította.");
        System.out.print("Kérem az első játékos nevét: ");
        playersNames[0] = scanner.nextLine();
        System.out.print("Kérem a második játékos nevét: ");
        playersNames[1] = scanner.nextLine();
        return playersNames;
    }

    public static char[][] boardSizeConfig(String playerOne, String playerTwo) {
        Scanner scanner = new Scanner(System.in);
        int boardSizeIndex = 0;

        System.out.println();
        System.out.println("Kedves " + playerOne + " és " + playerTwo + ", a következő három pályaméret közül választhattok:");
        System.out.println("1: 5x5");
        System.out.println("2: Egyéni(5-10)");
        while (boardSizeIndex > 0 || boardSizeIndex < 4) {
            try {
                System.out.print("Melyik legyen az: ");
                boardSizeIndex = scanner.nextInt();
                switch (boardSizeIndex) {
                    case 1:
                        return new char[6][6];
                    case 2:
                        return setCustomBoard();
                    default:
                        System.out.println("Sajnos nem érvényes számot adtál meg! Próbáld újra!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Sajnos nem érvényes számot adtál meg! Próbáld újra!");
            }
            scanner.nextLine();
        }
        return null;
    }

    public static char[][] setCustomBoard(){
        Scanner scanner = new Scanner(System.in);
        int customIndex = 5;

        while ((customIndex <= 5) || (customIndex >= 10)){
            try{
                System.out.println();
                System.out.println("Kérem az egyéni pálya méretét (5-10): ");
                customIndex = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Sajnos nem érvényes számot adtál meg! Próbáld újra!");
            }
            scanner.nextLine();
        }
        return new char[customIndex + 1][customIndex + 1];
    }

    public static char[][] playerBoardShipFiller(char[][] initializedPlayerBoard, String playerName) {
        char[][] playerBoard = initializedPlayerBoard;
        int singleShipCount = 3, doubleShipCount = 2;

        System.out.println();
        System.out.println("Kedves " + playerName + ", kérlek helyezz el 3 sima és 2 dupla hajót a térképen.");
        System.out.println();
        Display.reDrawDisplay(playerBoard, playerName);

        while (singleShipCount != 0) {
            playerBoard = singleShipDrawer(singleShipCount, playerBoard);
            Display.reDrawDisplay(playerBoard, playerName);
            singleShipCount = singleShipCount - 1;
        }

        while (doubleShipCount != 0) {
            playerBoard = doubleShipDrawer(doubleShipCount, playerBoard);
            Display.reDrawDisplay(playerBoard, playerName);
            doubleShipCount = doubleShipCount - 1;
        }

        return playerBoard;
    }

    public static char[][] shootTheShip(char[][] playerBoard, char[][] targetPlayerBoard, String player) {
        Scanner scanner = new Scanner(System.in);
        String playerInput;

        while (true) {
            try {
                Display.reDrawDisplay(targetPlayerBoard, player);
                System.out.println(player + " következik.");
                System.out.print("Kérem a koordinátát: ");
                playerInput = scanner.nextLine();
                playerInput = playerInput.toUpperCase();
                System.out.println();
                int row = rowFinder(playerBoard, playerInput, 0);
                int col = Character.getNumericValue(playerInput.charAt(1));
                if (targetPlayerBoard[row][col] == 'H' || targetPlayerBoard[row][col] == 'M'){
                    System.out.println("Ide már lőttél egyszer! Próbáld újra");
                    continue;
                }
                if (playerBoard[row][col] == 'O'){
                    targetPlayerBoard[row][col] = 'M';
                    istvanTotin.playSound("src/media/donthit.wav");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        System.out.println("Nem talált");
                    }
                    System.out.println("Sajnos elvétetted a lövést!");
                } else if (playerBoard[row][col] == 'X'){
                    targetPlayerBoard[row][col] = 'H';
                    if (!isNeighborCoordinate(playerBoard, row, col)){
                        targetPlayerBoard[row][col] = 'S';
                    }
                    istvanTotin.playSound("src/media/hit.wav");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        System.out.println("Talált!");
                    }
                    System.out.println("A lövésed talált!");
                }
                return targetPlayerBoard;
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Sajnos érvénytelen koordinátát adtál meg! Próbáld újra!");
            }

        }
    }

    public static boolean isShipAlive(char[][] playerTargetBoard){
        int shipCounter = 0;
        for (int i = 0; i < playerTargetBoard.length; i++) {
            for (int j = 0; j < playerTargetBoard[i].length; j++) {
                if (playerTargetBoard[i][j] == 'H' || playerTargetBoard[i][j] == 'S') {
                    shipCounter++;
                }
            }
        }
        if (shipCounter == 7){
            return false;
        } else {
            return true;
        }
    }


    public static boolean isValidCoordinate( char[][] playerBoard, int row, int col) {
        if ((row > 0 && row <= playerBoard.length-1) && (col > 0 && col <= playerBoard[row].length-1)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNeighborCoordinate( char[][] playerBoard, int row, int col) {
        try {
            if (row == playerBoard.length-1) {
                if (playerBoard[row - 1][col] == 'X'|| playerBoard[row][col - 1] == 'X' || playerBoard[row][col + 1] == 'X') {
                    return true;
                } else {
                    return false;
                }
            } else if (col == playerBoard[row].length-1) {
                if (playerBoard[row - 1][col] == 'X'|| playerBoard[row + 1][col] == 'X' || playerBoard[row][col - 1] == 'X') {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (playerBoard[row - 1][col] == 'X'|| playerBoard[row + 1][col] == 'X' || playerBoard[row][col - 1] == 'X' || playerBoard[row][col + 1] == 'X') {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            return false;
        }
    }

    public static boolean isAlreadyOccupied( char[][] playerBoard, int row, int col) {
        try {
            if (playerBoard[row][col] == 'X') {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            return false;
        }

    }

    public static boolean isValidDoubleShipCoordinate(int row, int col, int secRow, int secCol){
        if ((secRow == row + 1 || secRow == row - 1) || (secCol == col + 1 || secCol == col - 1)){
            return true;
        } else {
            return false;
        }
    }

    public static char[][] doubleShipDrawer(int doubleShipCount, char[][] playerBoard) {
        Scanner scanner = new Scanner(System.in);
        String playerInput;

        while (true) {
            try {
                System.out.println();
                System.out.println("Még " + doubleShipCount + " nagy hajód maradt hátra.");
                System.out.print("Kérem a két egymás melletti koordinátát: ");
                playerInput = scanner.nextLine();
                playerInput = playerInput.toUpperCase();
                System.out.println();
                int row = rowFinder(playerBoard, playerInput, 0);
                int col = Character.getNumericValue(playerInput.charAt(1));
                int secRow = rowFinder(playerBoard, playerInput, 2);
                int secCol = Character.getNumericValue(playerInput.charAt(3));
                if (!isValidDoubleShipCoordinate(row, col, secRow, secCol)) {
                    System.out.println("Sajnos csak egymás melletti koordinátát adhatsz meg! Kérlek adj meg új koordinátát!");
                    continue;
                }
                if (isNeighborCoordinate(playerBoard, row, col) || isNeighborCoordinate(playerBoard, secRow, secCol)) {
                    System.out.println("Sajnos túl közel vagy a másik halyóhoz! Kérlek adj meg új koordinátát!");
                    continue;
                }
                if (isAlreadyOccupied(playerBoard, row, col) || isAlreadyOccupied(playerBoard, secRow, secCol)) {
                    System.out.println("Sajnos ez a koordináta már foglalt! Kérlek adj meg új koordinátát!");
                    continue;
                }
                if (isValidCoordinate(playerBoard, row, col) && isValidCoordinate(playerBoard, secRow, secCol)) {
                    playerBoard[row][col] = 'X';
                    playerBoard[secRow][secCol] = 'X';
                    return playerBoard;
                } else {
                    System.out.println("Sajnos érvénytelen koordinátát adtál meg! Próbáld újra!");
                }
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Sajnos érvénytelen koordinátát adtál meg! Próbáld újra!");
            }
        }
    }

    public static char[][] singleShipDrawer(int singleShipCount, char[][] playerBoard) {
        Scanner scanner = new Scanner(System.in);
        String playerInput;

        while (true) {
            try {
                System.out.println();
                System.out.println("Még " + singleShipCount + " kis hajód maradt hátra.");
                System.out.print("Kérem a koordinátát: ");
                playerInput = scanner.nextLine();
                playerInput = playerInput.toUpperCase();
                System.out.println();
                int row = rowFinder(playerBoard, playerInput, 0);
                int col = Character.getNumericValue(playerInput.charAt(1));
                if (isNeighborCoordinate(playerBoard, row, col)) {
                    System.out.println("Sajnos túl közel vagy a másik hajóhoz! Kérlek adj meg új koordinátát!");
                    continue;
                }
                if (isAlreadyOccupied(playerBoard, row, col)) {
                    System.out.println("Sajnos ez a koordináta már foglalt! Kérlek adj meg új koordinátát!");
                    continue;
                }
                if (isValidCoordinate(playerBoard, row, col)) {
                    playerBoard[row][col] = 'X';
                    return playerBoard;
                } else {
                    System.out.println("Sajnos érvénytelen koordinátát adtál meg! Próbáld újra!");
                }
            } catch (StringIndexOutOfBoundsException ex) {

                System.out.println("Sajnos érvénytelen koordinátát adtál meg! Próbáld újra!");
            }
        }
    }

    public static int rowFinder(char[][] initializedPlayerBoard, String playerInput, int inspectedCharIndex) {
        char rowIndex = playerInput.charAt(inspectedCharIndex);

        for (int i = 0; i < initializedPlayerBoard.length; i++){
            if (initializedPlayerBoard[i][0] == rowIndex){
                return i;
            }
        }
        return 0;
    }

    public static char[][] boardInitializer(char[][] playerBoard){
        char rowIndex = 'A';
        for (char[] row: playerBoard)
            Arrays.fill(row, 'O');
        for (int row = 0; row < playerBoard.length; row++){
            if (row == 0) {
                for (int col = 1; col < playerBoard[row].length; col++) {
                    playerBoard[row][col] = Character.forDigit(col, 10);
                }
            } else {
                playerBoard[row][0] = rowIndex;
                rowIndex++;
            }
        }
        playerBoard[0][0] = ' ';
        return playerBoard;
    }

    public static void boardDrawer(char[][] playerBoard) {
        for (int row = 0; row < playerBoard.length; row++){
            System.out.printf("%30s ", "");
            for (int col = 0; col < playerBoard[row].length; col++){
                System.out.print(" " + playerBoard[row][col]);

            }
            System.out.println();
            System.out.printf("%15s ", "");
        }

    }

    public static char[][] arrayCopier(char[][] inputArray) {
        char[][] outputArray = new char[inputArray.length][inputArray[0].length];
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++) {
                outputArray[i][j] = inputArray [i][j];
            }
        }
        return outputArray;
    }

}
