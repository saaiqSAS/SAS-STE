package com.sas.ste.wizard;

public class Main {
    public static void main(String[] args) {

        System.out.println("###########################################################");
        System.out.println("#   _____     _      _____        _____  _______  ______  #");
        System.out.println("#  / ____|   / \\    / ____|      / ____||__   __||  ____| #");
        System.out.println("# | (___    / _ \\  | (___   ___ | (___     | |   | |__    #");
        System.out.println("#  \\___ \\  / /_\\ \\  \\___ \\ |___| \\___ \\    | |   |  __|   #");
        System.out.println("#  ____) |/ _____ \\ ____) |      ____) |   | |   | |____  #");
        System.out.println("# |_____//_/     \\_\\_____/      |_____/    |_|   |______| #");
        System.out.println("#                      Wizard v4.4.6                      #");
        System.out.println("#                       Made By SAS                       #");
        System.out.println("#                   Saaiq Abdulla Saeed                   #");
        System.out.println("###########################################################");
        System.out.println("+---------------------------------------------------------+ ");
        System.out.println("|        [+] = Success       |      [*] = Processing      | ");
        System.out.println("|        [!] = Error         |      [?] = Info/Help       | ");
        System.out.println("+---------------------------------------------------------+ ");

        Wizard.start();
    }

    protected static void log(String logText,int logType) {
        String logTypeTemp = "";
        switch (logType) {
            case 1:
                logTypeTemp += "[+] ";
                break;
            case 2:
                logTypeTemp += "[*] ";
                break;
            case 3:
                logTypeTemp += "[!] ";
                break;
            case 4:
                logTypeTemp += "[?] ";
                break;
            default:
                //System.out.println("[!] Error!! Invalid logType");
        }
        System.out.println(logTypeTemp + logText);

    }

    protected static int stringToInt(String stringInt) {
        char[] stringIntArr = stringInt.toCharArray();
        int stringIntLength = stringInt.length()-1;
        String revStringInt = "";
        char[] revStringIntArr;
        int tempInt = 0;
        int placeValue = 1;
        int output = 0;

        while (stringIntLength >= 0) {
            revStringInt += stringIntArr[stringIntLength];
            stringIntLength--;
        }

        revStringIntArr = revStringInt.toCharArray();

        for (char echar : revStringIntArr) {
            switch (echar) {
                case ' ':
                    tempInt = 0;
                    break;
                case '0':
                    tempInt = 0;
                    break;
                case '1':
                    tempInt = 1;
                    break;
                case '2':
                    tempInt = 2;
                    break;
                case '3':
                    tempInt = 3;
                    break;
                case '4':
                    tempInt = 4;
                    break;
                case '5':
                    tempInt = 5;
                    break;
                case '6':
                    tempInt = 6;
                    break;
                case '7':
                    tempInt = 7;
                    break;
                case '8':
                    tempInt = 8;
                    break;
                case '9':
                    tempInt = 9;
                    break;
            }
            output += tempInt*placeValue;
            placeValue = placeValue*10;
        }
        return output;
    }

    protected static String[] stringToArr(String string , char separatorCharacter) {

        char[] stringCharArr = string.toCharArray();
        int numOfStrings = 1;

        String[] outArr = null;
        int outArrPlaceValue = 0;
        String tempOutString = "";

        // Calculate the number of strings in the multi string
        for (char echar : stringCharArr) {
            if (echar == separatorCharacter) {
                numOfStrings++;
            }
        }
        // Set output array
        outArr = new String[numOfStrings];

        // Assign values to array
        for (char echar : stringCharArr) {
            if (echar == separatorCharacter) {
                outArr[outArrPlaceValue] = tempOutString;
                tempOutString = "";
                outArrPlaceValue++;
            } else {
                tempOutString += echar;
            }
        }
        outArr[outArrPlaceValue] = tempOutString;
        tempOutString = "";

        return outArr;
    }
}