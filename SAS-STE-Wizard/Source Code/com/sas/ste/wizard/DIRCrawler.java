package com.sas.ste.wizard;
import java.io.File;

class DIRCrawler {

    String[] allPathArr = null;
    String files = "";
    String directoriesSub = "";
    boolean success = true;
    protected String crawl(String paths) {

        // Add double '\' to single '\'
        char[] pathsCharArr = paths.toCharArray();
        String path = "";
        for (char echar : pathsCharArr) {
            if (echar == '\\') {
                path += "\\\\";
            } else {
                path += echar;
            }
        }

        // Add each path to array
        allPathArr = stringToArr(path, ';');

        File[] allFilesArr = new File[allPathArr.length];
        int allFilesArrPlaceValue = 0;
        boolean firstFileAdded = false;
        boolean firstDirAdded = false;

        for (String eString : allPathArr) {
            allFilesArr[allFilesArrPlaceValue] = new File(eString);
            allFilesArrPlaceValue++;
        }

        for (File eFile : allFilesArr) {
            if (eFile.exists()) {
                if (eFile.isFile()) {
                    if (!firstFileAdded) {
                        files += eFile.getAbsolutePath();
                        firstFileAdded = true;
                    } else {
                        files += ";" + eFile.getAbsolutePath();
                    }
                } else if (eFile.isDirectory()) {

                    File[] allFilesSubArr = eFile.listFiles();

                    for (File eFileSub : allFilesSubArr) {
                        if (eFileSub.isFile()) {
                            if (!firstFileAdded) {
                                files += eFileSub.getAbsolutePath();
                                firstFileAdded = true;
                            } else {
                                files += ";" + eFileSub.getAbsolutePath();
                            }
                        } else if (eFileSub.isDirectory()) {
                            if (!firstDirAdded) {
                                directoriesSub += eFileSub.getAbsolutePath();
                                firstDirAdded = true;
                            } else {
                                directoriesSub += ";" + eFileSub.getAbsolutePath();
                            }
                        }
                    }
                }
            } else {
                if (eFile.getName().equals("") || eFile.getName().equals(" ") || eFile.getName() == null) {
                    Main.log("Error!! Unnecessary ';' Found ",3);
                } else {
                    Main.log("Error!! File/Directory '" + eFile.getAbsolutePath() + "' Does Not Exist",3);
                }
                success = false;
            }
        }

        if (directoriesSub != "") {
            DIRCrawler crawler = new DIRCrawler();
            files += ";"+crawler.crawl(directoriesSub);
        }

        return files;
    }

    private String[] stringToArr(String string , char separatorCharacter) {

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
