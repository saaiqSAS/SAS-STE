package com.sas.ste.wizard;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class SasSte {

    protected static final char[] charSet = {' ','!','\"','#','$','%','&','\'','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~'}; //95chars
    protected static String key = "";
    protected static String processString = "";
    protected static String result = "";


    protected static String encryptMulti(String textToEncrypt) {
        String[] processStringArr = textToEncrypt.split("\n");
        result = "";

        for (String estring : processStringArr) {
            processString = estring;
            process.encrypt();
            result += process.result + "\n";
        }

        return result;
    }

    protected static String decryptMulti(String textToDecrypt) {
        String[] processStringArr = textToDecrypt.split("\n");
        result = "";

        for (String estring : processStringArr) {
            processString = estring;
            process.decrypt();
            result += process.result + "\n";
        }

        return  result;
    }

    protected static String encrypt(String textToEncrypt) {
        result = "";
        processString = textToEncrypt;
        process.encrypt();
        result += process.result;

        return result;
    }

    protected static String decrypt(String textToDecrypt) {
        result = "";
        processString = textToDecrypt;
        process.decrypt();
        result += process.result;

        return  result;
    }

    protected static void extractStaticKeys() {
        process.getStaticKeys();
    }

    protected static boolean getKeyFromText(String keyText) {
        if (keyText.length() == 112) {
            key = keyText;
            Main.log("Key Accepted",1);
            process.keyExtracted = false;
            return true;
        } else {
            Main.log("Error!! SAS-STE Key Is In An Incorrect Format", 3);
            return false;
        }
    }
    protected static boolean getKeyFromFile(String keyFilePath) {

        char[] keyFilePathArr = null;
        int keyFilePathLength = 0;
        Boolean errorHasOccurred = false;
        int us = 0;
        int crrNum = 0;
        String tempfileLineValue = "";
        char[] tempfileLineValueArr;
        String fileLineValue = "";
        Boolean fileHasNextLine;

        // ----- Keys extraction -----
        File steKeyFile = new File(keyFilePath);

        // Check if file exists
        if (steKeyFile.exists()) {
            keyFilePathArr = keyFilePath.toCharArray();
            keyFilePathLength = keyFilePath.length()-1;

            // Check file extension
            if (keyFilePathArr[keyFilePathLength-4] != '.' || keyFilePathArr[keyFilePathLength-3] != 's' || keyFilePathArr[keyFilePathLength-2] != 'k' || keyFilePathArr[keyFilePathLength-1] != 'e' || keyFilePathArr[keyFilePathLength] != 'y') {
                Main.log("Error!! Input File Has An Incorrect File Extension",3);
                Main.log("Only ' .skey ' File Extension Are Accepted",4);
                errorHasOccurred = true;
            } else {
                try {
                    Scanner steKeyFileRead = new Scanner(steKeyFile);
                    fileHasNextLine = steKeyFileRead.hasNextLine();

                    tempfileLineValue = steKeyFileRead.nextLine();
                    tempfileLineValueArr = tempfileLineValue.toCharArray();

                    /*

                    for (char echar : tempfileLineValueArr) {
                        if (crrNum != us) {
                            fileLineValue += echar;
                        }
                        crrNum++;
                    }

                     */

                    for (char echar : tempfileLineValueArr) {
                        fileLineValue += echar;
                    }

                    // Check key length
                    if (fileLineValue.length() == 112) {
                        key = fileLineValue;
                    } else {
                        Main.log("Error!! SAS-STE Key Is In An Incorrect Format", 3);
                        errorHasOccurred = true;
                    }

                } catch (Exception e) {
                    Main.log("Error!! Could Not Successfully Read Key File", 3);
                    errorHasOccurred = true;
                }
            }
        } else if (!steKeyFile.exists()) {
            Main.log("Error!! File Not Found At : "+keyFilePath,3);
            errorHasOccurred = true;
        }
        //----------

        if (!errorHasOccurred) {
            Main.log("Key File Accepted",1);
            process.keyExtracted = false;
            return true;
        } else {
            //Main.log("An Error Has Occurred",3);
            return false;
        }
    }

    protected static void setNoKey() {
        key = "$STE::";
        key += "0";
        key += "0";
        for (char echar : charSet){
            key += echar;
        }
        key += "0";
        key += "0";
        key += "0";
        key += "::KEY$";

        process.keyExtracted = false;
    }

    protected static String generateKey(int fullRep) {
        String tempKey = "$STE::";

        Random rand = new Random();
        Integer[] charSetKey = new Integer[95];

        for (int i = 0; i < charSetKey.length; i++) {
            charSetKey[i] = i;
        }
        Collections.shuffle(Arrays.asList(charSetKey));

        tempKey += rand.nextInt(3); // L0Type
        tempKey += rand.nextInt(6); //L2pAsign

        for (int charKey : charSetKey) {
            tempKey += charSet[charKey];
        }

        tempKey += rand.nextInt(10); //L1Rep
        tempKey += rand.nextInt(10); //L2Rep
        //tempKey += rand.nextInt(5); // fullRep
        tempKey += fullRep; // fullRep

        tempKey += "::KEY$";

        return tempKey;

    }

    protected static String generateStaticKeys(String name) {
                Integer[] arr = new Integer[95];
                String tempStaticKeys = "";
                String tempArrToString = "";
                char[] tempArrToStringArr = new char[95];

                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                tempStaticKeys += "SAS-STE-StaticKeys\n";
                tempStaticKeys += "==================\n";
                tempStaticKeys += "StaticKeys: " + name + "\n";
                tempStaticKeys += "==================\n";

                tempStaticKeys += "Layer1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL1 : tempArrToStringArr) {
                    if (echarL1 != '[' && echarL1 != ']') {
                        tempArrToString += echarL1;
                    } else {

                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p1 : tempArrToStringArr) {
                    if (echarL2p1 != '[' && echarL2p1 != ']') {
                        tempArrToString += echarL2p1;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p2:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p2 : tempArrToStringArr) {
                    if (echarL2p2 != '[' && echarL2p2 != ']') {
                        tempArrToString += echarL2p2;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p3:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p3 : tempArrToStringArr) {
                    if (echarL2p3 != '[' && echarL2p3 != ']') {
                        tempArrToString += echarL2p3;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "==================\n";

                return tempStaticKeys;
    }

    protected static boolean saveStaticKeysToFile(String staticKeys) {
        boolean errorHasOccurred = false;
        try {
            File staticKeysFile = new File(Main.class.getResource("/conf/ste_conf/StaticKeys.stkey").toURI());

            FileWriter staticKeysFileWrite = new FileWriter(staticKeysFile, false);

            staticKeysFileWrite.write(staticKeys);

            staticKeysFileWrite.close();

            //Extract New Keys
            process.keyExtracted = false;
        } catch (Exception e) {
            Main.log("Error!! Could Not Save StaticKeys",3);
            errorHasOccurred = true;
        }

        if (!errorHasOccurred) {
            return true;
        } else {
            return false;
        }
    }

    protected static boolean restoreDefaultStaticKeys() {
        String defaultStaticKeys = "SAS-STE-StaticKeys\n" +
                "==================\n" +
                "StaticKeys: Default\n" +
                "==================\n" +
                "Layer1:\n" +
                " 9, 29, 91, 67, 75, 30, 59, 38, 68, 55, 80, 52, 26, 37, 56, 40, 66, 10, 65, 11, 81, 49, 18, 85, 88, 70, 51, 6, 43, 82, 41, 42, 74, 87, 12, 86, 45, 39, 58, 35, 47, 21, 3, 31, 23, 33, 16, 57, 32, 17, 84, 60, 93, 19, 20, 69, 22, 24, 5, 46, 8, 90, 83, 78, 54, 4, 48, 13, 53, 94, 77, 76, 89, 63, 34, 0, 62, 28, 79, 36, 1, 14, 2, 50, 61, 64, 72, 73, 25, 7, 15, 27, 71, 44, 92,\n" +
                "Layer2p1:\n" +
                " 59, 89, 15, 43, 34, 78, 5, 72, 68, 82, 86, 7, 85, 65, 69, 8, 16, 33, 94, 93, 42, 18, 53, 26, 32, 87, 41, 17, 2, 55, 79, 83, 31, 46, 76, 6, 12, 54, 36, 11, 23, 20, 9, 21, 74, 92, 62, 58, 3, 57, 35, 1, 66, 13, 67, 61, 10, 47, 52, 14, 22, 48, 60, 39, 75, 25, 88, 64, 73, 38, 19, 81, 56, 71, 0, 90, 30, 50, 77, 37, 24, 44, 40, 80, 63, 70, 45, 84, 91, 27, 4, 29, 49, 51, 28,\n" +
                "Layer2p2:\n" +
                " 47, 40, 29, 35, 11, 8, 86, 1, 63, 80, 19, 24, 79, 85, 60, 56, 3, 62, 59, 64, 42, 83, 44, 32, 21, 65, 78, 82, 53, 75, 15, 6, 76, 50, 66, 25, 31, 55, 58, 84, 36, 0, 69, 61, 87, 34, 74, 54, 18, 22, 37, 48, 5, 71, 7, 12, 51, 73, 67, 93, 10, 43, 94, 49, 9, 89, 13, 41, 28, 91, 38, 57, 20, 4, 92, 30, 39, 27, 70, 23, 46, 14, 33, 72, 68, 2, 26, 52, 16, 81, 17, 45, 90, 77, 88,\n" +
                "Layer2p3:\n" +
                " 10, 73, 51, 34, 87, 53, 76, 41, 74, 31, 23, 39, 63, 57, 32, 43, 36, 20, 92, 9, 64, 33, 46, 88, 26, 13, 55, 1, 3, 60, 14, 67, 82, 59, 94, 49, 22, 84, 28, 2, 62, 5, 65, 25, 6, 78, 37, 21, 29, 30, 85, 91, 16, 71, 79, 52, 61, 7, 19, 8, 12, 44, 0, 45, 89, 77, 80, 27, 68, 50, 42, 24, 15, 72, 69, 35, 56, 47, 83, 66, 17, 18, 48, 38, 81, 86, 93, 54, 4, 75, 58, 70, 90, 40, 11,\n" +
                "==================";

        boolean restoredDefaults = saveStaticKeysToFile(defaultStaticKeys);

        return restoredDefaults;
    }

    protected static void saveKeyToFile(String saveKeyFilePath) {
        try {

        } catch (Exception e) {

        }
    }
}
