package com.codecool.battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {

        //title song

        MultithreadingPlayer player = new MultithreadingPlayer();
        Thread tPlayer = new Thread(player);
        tPlayer.start();
        istvanTotin.titlePage();


        while (true) {

            //PP
            //first player
            String[] players = janosSuli.welcomeScreen();
            char[][] playerOneBoard = janosSuli.boardSizeConfig(players[0], players[1]);
            char[][] playerTwoBoard = janosSuli.arrayCopier(playerOneBoard);
            Display.displayInitializer(playerOneBoard);
            playerOneBoard = janosSuli.boardInitializer(playerOneBoard);
            playerOneBoard = janosSuli.playerBoardShipFiller(playerOneBoard, players[0]);
            int[][] playerOneRecognized = jozsefCsurgai.boardWithSingleDoubleShipRecognized(playerOneBoard);

            // second player
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n" + "A másik játékos következik! Nyomj egy ENTER-t!");
            scanner.nextLine();
            playerTwoBoard = janosSuli.boardInitializer(playerTwoBoard);
            playerTwoBoard = janosSuli.playerBoardShipFiller(playerTwoBoard, players[1]);
            int[][] playerTwoRecognized = jozsefCsurgai.boardWithSingleDoubleShipRecognized(playerTwoBoard);

            char[][][] playersBoard = {playerOneBoard, playerTwoBoard};
            int[][][] playersBoardRecognized = {playerOneRecognized, playerTwoRecognized};

            //SP
            int playerFirst = 0;
            int playerOther = 1;
            String input;

            jozsefCsurgai.beginGameScreen(players[playerFirst]);
            while (true) {
                int colInput = 0;
                int rowInput = 0;
                boolean validRow, validCol, validPos, isAlreadyHit;
                while (true) {

                    //read input row and column
                    Scanner inputPos = new Scanner(System.in);
                    try {
                        input = inputPos.nextLine().toLowerCase();
                    } catch (Exception ex) {
                        input = "";
                    }
                    String rowFromInput = (String) input.subSequence(0, 1);
                    String colFromInput = (String) input.subSequence(1, input.length());

                    //errorHandling of input
                    String alphabet = "abcdefghijklmnopqrstuvwxyz";
                    validRow = jozsefCsurgai.isValidType(rowFromInput);
                    validCol = jozsefCsurgai.isValidType(colFromInput);
                    validPos = false;
                    isAlreadyHit = false;
                    if (validRow == true && validCol) {
                        colInput = Integer.parseInt(colFromInput);
                        rowInput = alphabet.indexOf(rowFromInput);
                        validPos = jozsefCsurgai.isInsideRange(rowInput, colInput, playersBoard[playerOther]);
                        jozsefCsurgai.displayIfOutsideRange(validPos);
                        try {
                            isAlreadyHit = jozsefCsurgai.isAlreadyHit(playersBoard[playerOther], rowInput, colInput);
                        } catch (Exception ex) {
                            isAlreadyHit = false;
                        }
                    } else {
                        jozsefCsurgai.displayValidType(validCol == true && validRow);
                    }

                    if (validRow == true && validCol && validPos && !isAlreadyHit) {
                        break;
                    }
                    System.out.print("\nHelytelen!\nPróbáld meg még egyszer: ");
                }

                //shoot board, display, alternate player
                playersBoard[playerOther] = jozsefCsurgai.boardAfterShoot(playersBoard[playerOther], playersBoardRecognized[playerOther], rowInput, colInput);
                playersBoard[playerOther] = jozsefCsurgai.boardChangeToDestroyed(playersBoard[playerOther]);
                jozsefCsurgai.boardDrawerPhantom(playersBoard[playerOther]);
                //janosSuli.boardDrawer(playersBoard[playerOther]);

                //exit if no more ships
                int xRemained = jozsefCsurgai.countingShips(playersBoard[playerOther]);
                if (xRemained == 0) break;

                if (playerFirst == 0) {
                    playerFirst = 1;
                    playerOther = 0;
                } else {
                    playerFirst = 0;
                    playerOther = 1;
                }
                System.out.print("\n" + players[playerFirst] + " következik! Kérem a lövést: ");
            }
            //GAME OVER, play again?
            istvanTotin.playSound("src/media/endMusic.wav");
            try {
                Thread.sleep(18000);
            } catch (Exception ex) {

            }
            String winner = (playerFirst == 0 ? players[0] : players[1]);
            String loser = (playerOther == 1 ? players[1] : players[0]);
            jozsefCsurgai.gameOver(winner, loser);
            Scanner inputTryAgain = new Scanner(System.in);
            String tryAgain = inputTryAgain.nextLine().toLowerCase();
            jozsefCsurgai.maybeExitGame(tryAgain);
        }

    }

}
