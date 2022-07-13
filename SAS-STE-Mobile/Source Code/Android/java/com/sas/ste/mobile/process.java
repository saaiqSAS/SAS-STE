package com.sas.ste.mobile;


import android.widget.Toast;

import com.sas.ste.mobile.layer0;
import com.sas.ste.mobile.layer1;
import com.sas.ste.mobile.layer2Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class process {

    protected static String result = "";
    protected static char[] processChar;
    protected static char[] charSet;
    protected static String processString = "";
    private static String key = "";
    private static int fullRep;
    private static int L1rep;
    private static int L2rep;
    private static int crrfullRep = 0;
    private static int crrL1rep = 0;
    private static int crrL2rep = 0;
    private static boolean getStaticKeysDone = false;

    protected static int[] StaticKeyL1 = new int[95];
    protected static int[] StaticKeyL2p1 = new int[95];
    protected static int[] StaticKeyL2p2 = new int[95];
    protected static int[] StaticKeyL2p3 = new int[95];


    protected static void encrypt() throws FileNotFoundException {
        getStaticKeys();
        getProcessChar();
        getProcessString();
        extractKey();
        encryptProcess();
    }
    protected static void decrypt() throws FileNotFoundException {
        getStaticKeys();
        getProcessChar();
        getProcessString();
        extractKey();
        decryptProcess();
    }

    private static void getStaticKeys() {
        if (!getStaticKeysDone) {
            StaticKeyL1 = MainActivity.StaticKeyL1;
            StaticKeyL2p1 = MainActivity.StaticKeyL2p1;
            StaticKeyL2p2 = MainActivity.StaticKeyL2p2;
            StaticKeyL2p3 = MainActivity.StaticKeyL2p3;

            getStaticKeysDone = true;
        }
    }

    private static void getProcessChar() {
        processChar = Output.processString.toCharArray();
    }

    private static void getProcessString() {
        processString = Output.processString;
    }

    private static void extractKey() {

        key = Output.key;
        char[] keyChar = key.toCharArray();

        //=====Charset======

        int charSetNum = 8;
        String stringCharSet = "";

        while (charSetNum <= 103){
            stringCharSet += keyChar[charSetNum];
            charSetNum++;
        }

        charSet = stringCharSet.toCharArray();

        //=====RepAmounts=====

        String repAmounts = "";
        repAmounts += keyChar[103];
        repAmounts += keyChar[104];
        repAmounts += keyChar[105];

        // System.out.println("L1rep keyChar[103] = "+keyChar[103]);  //test
        // System.out.println("L2rep keyChar[104] = "+keyChar[104]);  //test
        // System.out.println("fullRep keyChar[105] = "+keyChar[105]);  //test

        char[] repAmountsChar = repAmounts.toCharArray();
        int repLayer = 0;
        int repNum;

        for (char eChar : repAmountsChar) {
            repNum = 0;
            switch (eChar) {
                case '0':
                    repNum = 0;
                    break;
                case '1':
                    repNum = 1;
                    break;
                case '2':
                    repNum = 2;
                    break;
                case '3':
                    repNum = 3;
                    break;
                case '4':
                    repNum = 4;
                    break;
                case '5':
                    repNum = 5;
                    break;
                case '6':
                    repNum = 6;
                    break;
                case '7':
                    repNum = 7;
                    break;
                case '8':
                    repNum = 8;
                    break;
                case '9':
                    repNum = 9;
                    break;
            }
            switch (repLayer) {
                case 0:
                    L1rep = repNum;
                    break;
                case 1:
                    L2rep = repNum;
                    break;
                case 2:
                    fullRep = repNum;
                    break;
            }
            repLayer++;
        }

        //=====Others=====

        String others = "";
        others += keyChar[6];
        others += keyChar[7];

        // System.out.println("L0salt keyChar[6] = "+keyChar[6]);  //test
        // System.out.println("L2pAsign keyChar[7] = "+keyChar[7]);  //test
        // System.out.println("");  //test

        char[] othersChar = others.toCharArray();
        int othersPlaceValue = 0;
        int othersNum;

        for (char eChar : othersChar) {
            othersNum = 0;
            switch (eChar) {
                case '0':
                    othersNum = 0;
                    break;
                case '1':
                    othersNum = 1;
                    break;
                case '2':
                    othersNum = 2;
                    break;
                case '3':
                    othersNum = 3;
                    break;
                case '4':
                    othersNum = 4;
                    break;
                case '5':
                    othersNum = 5;
                    break;
                case '6':
                    othersNum = 6;
                    break;
                case '7':
                    othersNum = 7;
                    break;
                case '8':
                    othersNum = 8;
                    break;
                case '9':
                    othersNum = 9;
                    break;
            }
            switch (othersPlaceValue) {
                case 0:
                    layer0.L0Type = othersNum;
                    break;
                case 1:
                    layer2Main.L2pAsign = othersNum;
                    break;
            }
            othersPlaceValue++;
        }

    }


    protected static String layer0out = "";
    protected static String layer1out = "";
    protected static String layer2out = "";
    protected static String layer3out = "";

    private static void encryptProcess() throws FileNotFoundException {

        crrfullRep = 0;
        crrL1rep = 0;
        crrL2rep = 0;

        while (crrfullRep <= fullRep) {
            crrL1rep = 0;
            crrL2rep = 0;

            layer0.encryptL0();
            layer0out = layer0.layer0out;

            while (crrL1rep <= L1rep) {
                layer1.encryptL1();
                layer1out = layer1.layer1out;

                layer0out = layer1out;
                crrL1rep++;
            }

            while (crrL2rep <= L2rep) {
                layer2Main.encryptL2();
                layer2out = layer2Main.layer2out;

                layer1out = layer2out;
                crrL2rep++;
            }

            //System.out.println("layer0out: "+layer0out);//test
            //System.out.print("layer1out: "+ layer1out+"\n"); //test
            //System.out.println("layer2out: "+layer2out+"\n\n");//test

            result = layer2out;
            processString = layer2out;
            processChar = processString.toCharArray();
            crrfullRep++;
        }
    }


    private static void decryptProcess() throws FileNotFoundException {

        crrfullRep = 0;
        crrL1rep = 0;
        crrL2rep = 0;

        while (crrfullRep <= fullRep) {
            crrL1rep = 0;
            crrL2rep = 0;

            while (crrL2rep <= L2rep) {
                layer2Main.decryptL2();
                layer2out = layer2Main.layer2out;

                processString = layer2out;
                processChar = processString.toCharArray();
                crrL2rep++;
            }

            while (crrL1rep <= L1rep) {
                layer1.decryptL1();
                layer1out = layer1.layer1out;

                layer2out = layer1out;
                crrL1rep++;
            }

            layer0.decryptL0();
            layer0out = layer0.layer0out;



            //System.out.println("layer0out: "+layer0out);//test
            //System.out.print("layer1out: "+ layer1out+"\n"); //test
            //System.out.println("layer2out: "+layer2out+"\n\n");//test

            result = layer0out;
            processString = layer0out;
            processChar = processString.toCharArray();
            crrfullRep++;
        }
    }

}

