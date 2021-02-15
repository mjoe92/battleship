package com.codecool.battleship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class istvanTotin {
    // main-ból meghívva:
    /*istvanTotin.playSound("src/media/hit.wav");
        try {
        Thread.sleep(2000);
    } catch (Exception ex) {
        System.out.println("Talált!");
    }

        istvanTotin.playSound("src/media/donthit.wav");
        try {
        Thread.sleep(2000);
    } catch (Exception ex) {
        System.out.println("Nem talált");
    }

    istvanTotin.playSound("src/media/endMusic.wav");
        try {
        Thread.sleep(18000);
    } catch (Exception ex) {
        System.out.println("Nem talált");
    }*/

    //ugyancsak main-ból meghívva:
    /*MultithreadingPlayer player = new MultithreadingPlayer();
    Thread tPlayer = new Thread(player);
        tPlayer.start();

        istvanTotin.titlePage();*/

    public static void titlePage() {
        try {
            readFromFile("src/media/Ship01.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship02.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship03.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship04.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship05.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship06.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship07.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship08.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship09.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship10.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship11.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship12.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship13.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship14.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        try {
            readFromFile("src/media/Ship15.txt");
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }


    public static void readFromFile(String fileName) {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
        BufferedReader objReader = null;
        try {
            String content;
            objReader = new BufferedReader(new FileReader(fileName));
            while ((content = objReader.readLine()) != null) {
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void playSound(String soundFileName) {
        Clip clip;
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(soundFileName));
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

}
