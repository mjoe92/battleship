package com.codecool.battleship;

public class MultithreadingPlayer implements Runnable {

    @Override
    public void run() {
        istvanTotin.playSound("src/media/title_music.wav");
        try {
            Thread.sleep(30000);
        } catch (Exception ex) {
            System.out.println("Zene");
        }
    }
}
