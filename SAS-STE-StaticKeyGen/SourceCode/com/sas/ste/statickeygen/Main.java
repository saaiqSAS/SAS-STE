package com.sas.ste.statickeygen;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    private static String staticKeyFileIdentifier = "";
    private static Integer[] arr = new Integer[95];
    private static String staticKeysFilePath = "";
    private static String tempArrToString = "";
    private static char[] tempArrToStringArr = new char[95];
    private static String StaticKeys = "";


    public static void main(String[] args) {

       // System.out.println("=============================");
       // System.out.println("SAS-STE-StaticKeyGen [By SAS]");
       // System.out.println("=============================");
        System.out.println("#########################################");
        System.out.println("#     ___   _    ___    ___ ___  __     #");
        System.out.println("#    /___  /_\\  /___   /___  |  |_      #");
        System.out.println("#    ___/ /   \\ ___/   ___/  |  |__     #");
        System.out.println("#       StaticKeys File Generator       #");
        System.out.println("#                 By SAS                #");
        System.out.println("#########################################");
        System.out.println("");

        masterControl();
    }

    private static void masterControl() {
        getStaticKeysFilePath();
        getStaticKeyFileIdentifier();
        generateStaticKeysFile();
        askSaveStaticKeysFile();
    }

    private static void getStaticKeysFilePath() {
        try {
            staticKeysFilePath = "";
            String tempStaticKeysFilePath = new File("StaticKeys.stkey").getCanonicalPath();
            char[] tempStaticKeysFilePathArr = tempStaticKeysFilePath.toCharArray();

            for (char echar : tempStaticKeysFilePathArr) {
                if (echar == '\\') {
                    staticKeysFilePath += "\\\\";
                } else {
                    staticKeysFilePath += echar;
                }
            }
        }catch (Exception e) {
            System.out.println("getStaticKeysFilePath() "+e);
        }
    }

    private static void deleteCrrStaticKeysFile() {
        try {
            File crrStaticKeysFile = new File(staticKeysFilePath);
            if (crrStaticKeysFile.exists()) {
                FileWriter crrStaticKeysFileShred = new FileWriter(staticKeysFilePath, false);

                int shred = 0;

                while (shred <= 10) {
                    crrStaticKeysFileShred.write("SAS-STE StaticKeys File");
                    shred++;
                }
                crrStaticKeysFile.delete();
                if (crrStaticKeysFile.exists()) {
                    System.out.println("[+]Current StaticKeys.stkey deleted");
                }else {
                    System.out.println("[!]Alert!! Couldn't Delete Current StaticKeys.stkey");
                }
            }
        }catch (Exception e) {
            System.out.println("deleteCrrStaticKeysFile() "+e);
        }
    }

    private static void getStaticKeyFileIdentifier() {
        Scanner grab = new Scanner(System.in);
        System.out.println("Enter an identifier name for your new StaticKeys.stkey file:");
        staticKeyFileIdentifier = grab.next() + grab.nextLine();

    }

    private static void generateStaticKeysFile() {
        try {
            StaticKeys = "";

            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            StaticKeys += "SAS-STE-StaticKeys\n";
            StaticKeys += "==================\n";
            StaticKeys += "StaticKeys: " + staticKeyFileIdentifier + "\n";
            StaticKeys += "==================\n";

            StaticKeys += "Layer1:\n";
            Collections.shuffle(Arrays.asList(arr));
            tempArrToStringArr = Arrays.toString(arr).toCharArray();
            tempArrToString = "";
            for (char echarL1 : tempArrToStringArr) {
                if (echarL1 != '[' && echarL1 != ']') {
                    tempArrToString += echarL1;
                }else {

                }
            }
            StaticKeys += " " + tempArrToString + "," + "\n";

            StaticKeys += "Layer2p1:\n";
            Collections.shuffle(Arrays.asList(arr));
            tempArrToStringArr = Arrays.toString(arr).toCharArray();
            tempArrToString = "";
            for (char echarL2p1 : tempArrToStringArr) {
                if (echarL2p1 != '[' && echarL2p1 != ']') {
                    tempArrToString += echarL2p1;
                }
            }
            StaticKeys += " " + tempArrToString + "," + "\n";

            StaticKeys += "Layer2p2:\n";
            Collections.shuffle(Arrays.asList(arr));
            tempArrToStringArr = Arrays.toString(arr).toCharArray();
            tempArrToString = "";
            for (char echarL2p2 : tempArrToStringArr) {
                if (echarL2p2 != '[' && echarL2p2 != ']') {
                    tempArrToString += echarL2p2;
                }
            }
            StaticKeys += " " + tempArrToString + "," + "\n";

            StaticKeys += "Layer2p3:\n";
            Collections.shuffle(Arrays.asList(arr));
            tempArrToStringArr = Arrays.toString(arr).toCharArray();
            tempArrToString = "";
            for (char echarL2p3 : tempArrToStringArr) {
                if (echarL2p3 != '[' && echarL2p3 != ']') {
                    tempArrToString += echarL2p3;
                }
            }
            StaticKeys += " " + tempArrToString + "," + "\n";

            StaticKeys += "==================\n";

            System.out.println(StaticKeys);

        }catch (Exception e) {
            System.out.println("generateStaticKeysFile() "+e);
        }

    }

    private static void askSaveStaticKeysFile() {
        Scanner grab = new Scanner(System.in);
        System.out.println("Do you want to save the generated StaticKeys to StaticKeys.stkey File \n [1]Yes \n [2]No,Regenerate \n\n [3]Quit" );
        String saveOrNot = grab.next();

        switch (saveOrNot) {
            case "1":
                saveStaticKeysFile();
                break;
            case "2":
                masterControl();
                break;
            case "3":
                quit();
                break;
            default:
                System.out.println("Error!! PLease select a number from the given range (1-3)");
                askSaveStaticKeysFile();
        }
    }

    private static void saveStaticKeysFile() {
        deleteCrrStaticKeysFile();
        try {
            FileWriter newStaticKeysFile = new FileWriter(staticKeysFilePath, true);
            newStaticKeysFile.write(StaticKeys);
            newStaticKeysFile.close();
            System.out.println("[+]New StaticKeys.stkey File Generated");
        }catch (Exception e) {
            System.out.println("saveStaticKeysFile() "+e);
        }

    }

    private static void quit() {
        System.out.println("\n[+]Exiting Program");
        System.exit(2);
    }

}
