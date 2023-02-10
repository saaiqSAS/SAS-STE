package com.sas.ste.wizard;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Wizard {

    private static Scanner grab = new Scanner(System.in);

    // Input
    private static String askFunctionInput = "";
    private static String askInputInput = "";
    private static String askOutputInput = "";

    // Process
    private static String functionType = "";
    private static String inputType = "";
    private static String outputType = "";
    private static String keyType = "";

    // Inputs
    private static String processText = "";
    private static String[] processFilesPaths = null;
    private static int processFileNumber = 1;

    // Saves
    private static String saveFilePath = "";

    protected static void start() {
        masterControl();
    }

    private static void masterControl() {
        System.out.println(" ");
        SasSte.extractStaticKeys();

        askFunction();
        processFunction();

        askInput();
        processInput();

        askOutput();
        processOutput();

        // Process
        Main.log("Processing...",2);
        switch (askInputInput) {
            case "1": // Single Line Text
                switch (outputType) {
                    case "Text-Print":
                        System.out.println(" ");
                        System.out.println("================================");
                        System.out.println("             Output             ");
                        System.out.println("================================");
                        if (functionType.equals("1")) {
                            System.out.println(SasSte.encrypt(processText));
                        } else if (functionType.equals("2")) {
                            System.out.println(SasSte.decrypt(processText));
                        }
                        System.out.println("================================");
                        break;
                    case "Text-SaveToFile":
                        try {
                            File saveFile = new File(saveFilePath);
                            FileWriter saveFileWrite = new FileWriter(saveFile, true);

                            String output = "";
                            if (functionType.equals("1")) {
                                output = SasSte.encrypt(processText);
                            } else if (functionType.equals("2")) {
                                output = SasSte.decrypt(processText);
                            }
                            Main.log("Saving Output To '"+saveFilePath+"'",2);
                            saveFileWrite.write(output+"\n");
                            saveFileWrite.close();

                        } catch (Exception e) {
                            Main.log("Error!! An Error Has Occurred While Saving Output To File",3);
                        }
                        break;
                }
                break;
            case "2": // Multiple Text
                System.out.println(" ");
                System.out.println("+-------------------------------------+");
                System.out.println("| Commands:                           |");
                System.out.println("+------------------+------------------+");
                System.out.println("| !END! = Finish   | !BCK! = Back     |");
                System.out.println("| !FE! = Encrypt   | !FD! = Decrypt   |");
                System.out.println("| !EL! = EmptyLine |                  |");
                System.out.println("+------------------+------------------+");
                System.out.println(" ");

                boolean endLoop = false;
                while (!endLoop) {
                    if (functionType.equals("1")) {
                        System.out.println("Function: Encrypt");
                    } else if (functionType.equals("2")) {
                        System.out.println("Function: Decrypt");
                    }
                    System.out.println("Enter Text To Process:");
                    System.out.print("> ");

                    String input = grab.next() + grab.nextLine();

                    switch (input) {
                        case "!FE!":
                            functionType = "1";
                            break;
                        case "!FD!":
                            functionType = "2";
                            break;
                        case "!BCK!":
                        case "<":
                            endLoop = true;
                            back("masterControl()-multipleTextProcess");
                            break;
                        case "!END!":
                            endLoop = true;
                            break;
                        default:
                            if (outputType.equals("Text-Print")) {
                                System.out.print("Output: ");
                                if (input.equals("!EL!")) {
                                    System.out.println("");
                                } else {
                                    if (functionType.equals("1")) {
                                        System.out.println(SasSte.encrypt(input));
                                    } else if (functionType.equals("2")) {
                                        System.out.println(SasSte.decrypt(input));
                                    }
                                }
                                System.out.println("----------------------");
                            } else if (outputType.equals("Text-SaveToFile")) {
                                try {
                                    File saveFile = new File(saveFilePath);
                                    FileWriter saveFileWrite = new FileWriter(saveFile,true);

                                    if (input.equals("!EL!")) {
                                        saveFileWrite.write("\n");
                                    } else {
                                        if (functionType.equals("1")) {
                                            saveFileWrite.write(SasSte.encrypt(input)+"\n");
                                        } else if (functionType.equals("2")) {
                                            saveFileWrite.write(SasSte.decrypt(input)+"\n");
                                        }
                                    }
                                    saveFileWrite.close();

                                } catch (Exception e) {
                                    Main.log("Error!! An Error Has Occurred While Saving Output To File",3);
                                }

                            }
                    }

                }

                break;
            case "3": // Text Based File(s)
                if (outputType.equals("File-Print")) {
                    System.out.println(" ");
                    System.out.println("================================");
                    System.out.println("             Output             ");
                    System.out.println("================================");

                    int fileNum = 1;
                    for (String eString : processFilesPaths) {
                        if (!eString.equals("") && eString != null) {
                            try {
                                File file = new File(eString);
                                Scanner fileRead = new Scanner(file);
                                boolean fileHasNextLine = fileRead.hasNextLine();

                                System.out.println("-----------------------------------------------------------");
                                System.out.println("["+fileNum+"] "+eString);
                                System.out.println("-----------------------------------------------------------");

                                while (fileHasNextLine) {
                                    if (functionType.equals("1")) {
                                        System.out.println(SasSte.encrypt(fileRead.nextLine()));
                                    } else if (functionType.equals("2")) {
                                        System.out.println(SasSte.decrypt(fileRead.nextLine()));
                                    }
                                    fileHasNextLine = fileRead.hasNextLine();
                                }

                                fileNum++;
                            } catch (Exception e) {
                                Main.log("Error!! An Error Has Occurred While Processing The File(s)",3);
                                System.out.println(e.toString());
                            }
                        }
                    }

                    System.out.println("================================");
                } else if (outputType.equals("File-SaveToFile")) {
                    for (String eString : processFilesPaths) {
                        if (!eString.equals("") && eString != null) {
                            try {
                                File file = new File(eString);
                                File saveFile = new File(saveFilePath);
                                Scanner fileRead = new Scanner(file);
                                FileWriter saveFileWrite = new FileWriter(saveFile,true);
                                boolean fileHasNextLine = fileRead.hasNextLine();

                                if (functionType.equals("1")) {
                                    Main.log("Encrypting File '"+eString+"'",2);
                                } else if (functionType.equals("2")) {
                                    Main.log("Decrypting File '"+eString+"'",2);
                                }

                                while (fileHasNextLine) {
                                    if (functionType.equals("1")) {
                                        saveFileWrite.write(SasSte.encrypt(fileRead.nextLine())+"\n");
                                    } else if (functionType.equals("2")) {
                                        saveFileWrite.write(SasSte.decrypt(fileRead.nextLine())+"\n");
                                    }
                                    fileHasNextLine = fileRead.hasNextLine();
                                }

                                Main.log("Saving Output To '"+saveFilePath+"'",2);
                                saveFileWrite.close();

                            } catch (Exception e) {
                                if (!e.toString().equals("java.util.NoSuchElementException: No line found")) {
                                    Main.log("Error!! An Error Has Occurred While Processing The File(s)", 3);
                                    System.out.println(e.toString());
                                }
                            }
                        }
                    }
                } else if (outputType.equals("File-SaveToInputFile")) {
                    for (String eString : processFilesPaths) {
                        if (!eString.equals("") && eString != null) {
                            try {
                                File file = new File(eString);
                                File tempFile = new File(Main.class.getResource("/temp/temp.tmp").toURI());
                                Scanner fileRead = new Scanner(file);
                                FileWriter tempFileWrite = new FileWriter(tempFile,false);
                                boolean fileHasNextLine = fileRead.hasNextLine();

                                if (functionType.equals("1")) {
                                    Main.log("Encrypting File '"+eString+"'",2);
                                } else if (functionType.equals("2")) {
                                    Main.log("Decrypting File '"+eString+"'",2);
                                }

                                while (fileHasNextLine) {
                                    if (functionType.equals("1")) {
                                        tempFileWrite.write(SasSte.encrypt(fileRead.nextLine())+"\n");
                                    } else if (functionType.equals("2")) {
                                        tempFileWrite.write(SasSte.decrypt(fileRead.nextLine())+"\n");
                                    }
                                    fileHasNextLine = fileRead.hasNextLine();
                                }
                                tempFileWrite.close();

                                FileWriter fileWriteShred = new FileWriter(file,false);
                                int shredNum = 0;
                                while (shredNum < 50) {
                                    fileWriteShred.write("This File Was Processed By SAS-STE-Wizard\nVisit saaiqsas.github.io To Get SAS-STE Releases");
                                    fileWriteShred.write("Visit saaiqsas.github.io To Get SAS-STE Releases\nThis File Was Processed By SAS-STE-Wizard");
                                    shredNum++;
                                }
                                fileWriteShred.write("");
                                fileWriteShred.close();

                                Scanner tempFileRead = new Scanner(tempFile);
                                boolean tempFileHasNextLine = tempFileRead.hasNextLine();
                                FileWriter fileWrite = new FileWriter(file,false);

                                while (tempFileHasNextLine) {
                                    fileWrite.write(tempFileRead.nextLine()+"\n");
                                    tempFileHasNextLine = tempFileRead.hasNextLine();
                                }
                                fileWrite.close();
                                tempFileRead.close();

                                FileWriter tempFileShred = new FileWriter(tempFile,false);
                                int shredNumtemp = 0;
                                while (shredNumtemp < 50) {
                                    tempFileShred.write("This File Was Processed By SAS-STE-Wizard\nVisit saaiqsas.github.io To Get SAS-STE Releases");
                                    tempFileShred.write("Visit saaiqsas.github.io To Get SAS-STE Releases\nThis File Was Processed By SAS-STE-Wizard");
                                    shredNumtemp++;
                                }
                                tempFileShred.write("");
                                tempFileShred.close();

                                FileWriter emptyTempFile = new FileWriter(tempFile,false);
                                emptyTempFile.write("");
                                emptyTempFile.close();


                            } catch (Exception e) {
                                if (!e.toString().equals("java.util.NoSuchElementException: No line found")) {
                                    Main.log("Error!! An Error Has Occurred While Processing The File(s)", 3);
                                    System.out.println(e.toString());
                                }
                            }
                        }
                    }
                }
                break;
        }
        Main.log("Finished Processing",1);
        exit();

    }

    private static void processFunction() {
        switch (askFunctionInput) {
            case "1": // Encrypt-No Key
                functionType = "1";
                SasSte.setNoKey();
                break;
            case "2": // Encrypt-New Key
                functionType = "1";
                askFullRep();
                break;
            case "3": // Encrypt-Existing Key
                functionType = "1";
                askKeyInput();
                break;
            case "4": // Decrypt-No Key
                functionType = "2";
                SasSte.setNoKey();
                break;
            case "5": // Decrypt-Keyed
                functionType = "2";
                askKeyInput();
                break;
        }
    }

    private static void processInput() {
        switch (askInputInput) {
            case "1": // Single Line Text
                inputType = "1";
                inputSingleLineText();
                break;
            case "2": // Multiple Text
                inputType = "1";
                break;
            case "3": // Text Based File(s)
                inputType = "2";
                inputFiles();
                break;
            case "<": // Back
                back("askInput()");
        }
    }

    private static void processOutput() {
        if (inputType == "1") {  // Text
            switch (askOutputInput) {
                case "1": // Print
                    outputType = "Text-Print";
                    break;
                case "2": // Save To File
                    outputType = "Text-SaveToFile";
                    saveFilePath = askSaveFilePath();
                    break;
                case "<":
                    back("askOutput()Text");
                    break;
            }
        } else if (inputType == "2") {  // File
            if (processFileNumber == 1) {  // One File
                switch (askOutputInput) {
                    case "1": // Print
                        outputType = "File-Print";
                        break;
                    case "2": // Save To File
                        outputType = "File-SaveToFile";
                        saveFilePath = askSaveFilePath();
                        break;
                    case "3": // Save To Input File
                        outputType = "File-SaveToInputFile";
                        break;
                    case "<":
                        back("askOutput()File");
                        break;
                }
            } else if (processFileNumber > 1) {  // More Than One File
                switch (askOutputInput) {
                    case "1": // Print
                        outputType = "File-Print";
                        break;
                    case "2": // Save To Input Files
                        outputType = "File-SaveToInputFile";
                        break;
                    case "<":
                        back("askOutput()File");
                        break;
                }
            }
        }
    }

    private static void askFunction() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("Select A Function-Key (Select Between [1]-[5]):");
            System.out.println(" [1] Encrypt-No Key");
            System.out.println(" [2] Encrypt-New Key");
            System.out.println(" [3] Encrypt-Existing Key");
            System.out.println(" ");
            System.out.println(" [4] Decrypt-No Key");
            System.out.println(" [5] Decrypt-Keyed");
            System.out.println(" ");
            System.out.println(" [?] Help");
            System.out.println(" [x] Exit");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    accepted = true;
                    askFunctionInput = input;
                    break;
                case "98":
                    boolean restored = SasSte.restoreDefaultStaticKeys();
                    if (restored) {
                        Main.log("Default StaticKeys Restored",1);
                    } else {
                        Main.log("Error!! An Error Has Occurred While Restoring Default StaticKeys",3);
                    }
                    Main.log("Please Re Execute/Open SAS-STE-Wizard",4);
                    exit();
                    break;
                case "99":
                    accepted = true;
                    askStaticKeys();
                    break;
                case "?":
                    System.out.println("");
                    System.out.println("+-----+----------------------+------------------------------------------------+");
                    System.out.println("|  #  | Options              | Description                                    |");
                    System.out.println("+-----+----------------------+------------------------------------------------+");
                    System.out.println("| [1] | Encrypt-No Key       | Use Only StaticKeys To Encrypt Data            |");
                    System.out.println("| [2] | Encrypt-New Key      | Generate A New Key To Encrypt Data             |");
                    System.out.println("| [3] | Encrypt-Existing Key | Use A Previously Generated Key To Encrypt Data |");
                    System.out.println("|     |                      |                                                |");
                    System.out.println("| [4] | Decrypt-No Key       | Use Only StaticKeys To Decrypt Data            |");
                    System.out.println("| [5] | Decrypt-Keyed        | Decrypt Data Using A Key                       |");
                    System.out.println("+-----+----------------------+------------------------------------------------+");
                    break;
                case "x":
                case "X":
                    accepted = true;
                    exit();
                    break;
                default:
                    Main.log("Error!! Invalid Input. Please Select An Input From The Given Options",3);
            }
        }
    }

    private static void askInput() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("Select An Input Method (Select Between [1]-[3]):");
            System.out.println(" [1] Single Line Text");
            System.out.println(" [2] Multiple Text");
            System.out.println(" [3] Text Based File(s)");
            System.out.println(" ");
            System.out.println(" [?] Help");
            System.out.println(" [<] Back");
            System.out.println(" [x] Exit");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                case "2":
                case "3":
                case "<":
                    accepted = true;
                    askInputInput = input;
                    break;
                case "?":
                    System.out.println("");
                    System.out.println("+-----+--------------------+---------------------------------------------------------+");
                    System.out.println("|  #  | Options            | Description                                             |");
                    System.out.println("+-----+--------------------+---------------------------------------------------------+");
                    System.out.println("| [1] | Single Line Text   | Input Only One Line Of Text For Processing              |");
                    System.out.println("| [2] | Multiple Text      | Keeps Inputting,Processing And Outputting Until Stopped |");
                    System.out.println("| [3] | Text Based File(s) | Input The Path To Files/Directories To Process Files    |");
                    System.out.println("+-----+--------------------+---------------------------------------------------------+");
                    break;
                case "x":
                case "X":
                    accepted = true;
                    exit();
                    break;
                default:
                    Main.log("Error!! Invalid Input. Please Select An Input From The Given Options",3);
            }
        }
    }

    private static void inputSingleLineText() {
        System.out.println(" ");
        System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
        System.out.println("Enter The Text To Process:");
        System.out.print("> ");

        String input = grab.next() + grab.nextLine();

        switch (input) {
            case "!BCK!":
            case "<":
                back("inputSingleLineText()");
                break;
            case "!END!":
                exit();
            default:
                processText = input;
        }
    }

    private static void inputMultipleText() {

    }

    private static void inputFiles() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Full Paths Of Files/Directories To Process:");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "!BCK!":
                case "<":
                    accepted = true;
                    back("inputFiles()");
                    break;
                case "!END!":
                    accepted = true;
                    exit();
                default:
                    DIRCrawler crawler = new DIRCrawler();
                    String detectedFilesPath = crawler.crawl(input);

                    if (crawler.success) {
                        accepted = true;

                        String[] detectedFilesPathArr = Main.stringToArr(detectedFilesPath, ';');
                        String tempProcessFilesPath = "";
                        boolean firstAdded = false;
                        processFileNumber = 1;

                        System.out.println(" ");
                        System.out.println("================================");
                        System.out.println("   Detected Text Based File(s)  ");
                        System.out.println("================================");

                        for (String eString : detectedFilesPathArr) {
                            try {
                                File file = new File(eString);
                                Scanner scannedFile = new Scanner(file);

                                if (scannedFile.hasNextLine()) {
                                    if (!firstAdded) {
                                        tempProcessFilesPath += eString;
                                        firstAdded = true;
                                    } else {
                                        tempProcessFilesPath += ";" + eString;
                                    }
                                    System.out.println("["+processFileNumber+"] "+eString);
                                    processFileNumber++;
                                }

                            } catch (Exception e) {
                               // Main.log("Error!! File '" + eString + "' Does Not Exist", 3);
                               // accepted = false;
                            }
                        }

                        System.out.println("================================");
                        processFileNumber--;
                        processFilesPaths = Main.stringToArr(tempProcessFilesPath,';');
                        removeInputFiles();
                    }
            }
        }
    }

    private static void removeInputFiles() {
        boolean accepted = false;

        while (!accepted) {
            if (processFilesPaths.length > 0) {
                System.out.println(" ");
                System.out.println("[Commands: !END! = Exit]");
                System.out.println("[Enter Any Text Other Than The File Numbers To Continue]");
                System.out.println("Enter The Number Of A File You Want To Remove:");
                System.out.print("> ");

                String input = grab.next() + grab.nextLine();
                int inputInt = Main.stringToInt(input);

                if (inputInt >= 1 && inputInt <= processFileNumber) {
                    processFilesPaths[inputInt - 1] = "";
                    String[] updatedProcessFilesPaths = new String[processFileNumber-1];
                    processFileNumber = 1;

                    System.out.println(" ");
                    System.out.println("================================");
                    System.out.println("   Updated Text Based File(s)   ");
                    System.out.println("================================");

                    for (String eString : processFilesPaths) {
                        if (eString != null) {
                            if (!eString.equals("")) {
                                System.out.println("[" + processFileNumber + "] " + eString);
                                updatedProcessFilesPaths[processFileNumber - 1] = eString;
                                processFileNumber++;
                            }
                        }
                    }
                    processFilesPaths = null;
                    processFilesPaths = updatedProcessFilesPaths;
                    processFileNumber--;
                    System.out.println("================================");
                } else if (input.equals("!END!")) {
                    accepted = true;
                    exit();
                } else {
                    accepted = true;
                    Main.log("File(s) Accepted", 1);
                }
            } else {
                accepted = true;
                Main.log("Error!! No Files Found To Process",3);
                inputFiles();
            }
        }
    }

    private static void askOutput() {
        boolean accepted = false;

        while (!accepted) {
            if (inputType.equals("1")) {  // Text
                System.out.println(" ");
                System.out.println("Select An Output Method (Select Between [1]-[2]):");
                System.out.println(" [1] Print");
                System.out.println(" [2] Save To File");
                System.out.println(" ");
                System.out.println(" [?] Help");
                System.out.println(" [<] Back");
                System.out.println(" [x] Exit");
                System.out.print("> ");

                String input = grab.next() + grab.nextLine();

                switch (input) {
                    case "1":
                    case "2":
                    case "<":
                        accepted = true;
                        askOutputInput = input;
                        break;
                    case "?":
                        System.out.println("");
                        System.out.println("+-----+--------------+-------------------------------+");
                        System.out.println("|  #  | Options      | Description                   |");
                        System.out.println("+-----+--------------+-------------------------------+");
                        System.out.println("| [1] | Print        | Prints Output To The Terminal |");
                        System.out.println("| [2] | Save To File | Saves Output To A Given File  |");
                        System.out.println("+-----+--------------+-------------------------------+");
                        break;
                    case "x":
                    case "X":
                        accepted = true;
                        exit();
                        break;
                    default:
                        Main.log("Error!! Invalid Input. Please Select An Input From The Given Options",3);
                }

            } else if (inputType.equals("2")) {  // File
                if (processFileNumber == 1) {  // One File
                    System.out.println(" ");
                    System.out.println("Select An Output Method (Select Between [1]-[3]):");
                    System.out.println(" [1] Print");
                    System.out.println(" [2] Save To File");
                    System.out.println(" [3] Save To Input File");
                    System.out.println(" ");
                    System.out.println(" [?] Help");
                    System.out.println(" [<] Back");
                    System.out.println(" [x] Exit");
                    System.out.print("> ");

                    String input = grab.next() + grab.nextLine();

                    switch (input) {
                        case "1":
                        case "2":
                        case "3":
                        case "<":
                            accepted = true;
                            askOutputInput = input;
                            break;
                        case "?":
                            System.out.println("");
                            System.out.println("+-----+--------------------+---------------------------------+");
                            System.out.println("|  #  | Options            | Description                     |");
                            System.out.println("+-----+--------------------+---------------------------------+");
                            System.out.println("| [1] | Print              | Prints Output To The Terminal   |");
                            System.out.println("| [2] | Save To File       | Saves Output To A Given File    |");
                            System.out.println("| [3] | Save To Input File | Save To The File Given As Input |");
                            System.out.println("+-----+--------------------+---------------------------------+");
                            break;
                        case "x":
                        case "X":
                            accepted = true;
                            exit();
                            break;
                        default:
                            Main.log("Error!! Invalid Input. Please Select An Input From The Given Options", 3);
                    }
                } else if (processFileNumber > 1) {  // More Than One File

                    System.out.println(" ");
                    System.out.println("Select An Output Method (Select Between [1]-[2]):");
                    System.out.println(" [1] Print");
                    System.out.println(" [2] Save To Input Files");
                    System.out.println(" ");
                    System.out.println(" [?] Help");
                    System.out.println(" [<] Back");
                    System.out.println(" [x] Exit");
                    System.out.print("> ");

                    String input = grab.next() + grab.nextLine();

                    switch (input) {
                        case "1":
                        case "2":
                        case "<":
                            accepted = true;
                            askOutputInput = input;
                            break;
                        case "?":
                            System.out.println("");
                            System.out.println("+-----+---------------------+----------------------------------+");
                            System.out.println("|  #  | Options             | Description                      |");
                            System.out.println("+-----+---------------------+----------------------------------+");
                            System.out.println("| [1] | Print               | Prints Output To The Terminal    |");
                            System.out.println("| [2] | Save To Input Files | Save To The Files Given As Input |");
                            System.out.println("+-----+---------------------+----------------------------------+");
                            break;
                        case "x":
                        case "X":
                            accepted = true;
                            exit();
                            break;
                        default:
                            Main.log("Error!! Invalid Input. Please Select An Input From The Given Options", 3);
                    }
                }
            }
        }
    }

    private static String askSaveFilePath() {
        boolean accepted = false;
        String returnString = "";

        while (!accepted) {
            System.out.println(" ");
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Full Path To Save File:");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "!BCK!":
                case "<":
                    accepted = true;
                    back("askSaveFilePath()");
                    break;
                case "!END!":
                    accepted = true;
                    exit();
                default:
                    File inputFile = new File(input);
                    if (!inputFile.exists()) {
                        boolean confirmed = confirmSaveFilePath(input,false);
                        if (confirmed) {
                            accepted = true;
                            try {
                                Main.log("Creating File '"+input+"'",2);
                                boolean createFile = inputFile.createNewFile();
                                if (createFile && inputFile.exists()) {
                                    Main.log("File '" + inputFile + "' SuccessFully Created", 1);
                                } else {
                                    Main.log("Error!! Failed To Create File '" + inputFile + "'", 1);
                                }
                                returnString = input;
                            } catch (Exception e) {
                                Main.log("Error!! An Error Has Occurred While Create File '" + inputFile + "'", 1);
                            }
                        }
                    } else {
                        boolean confirmed = confirmSaveFilePath(input,true);
                        if (confirmed) {
                            accepted = true;
                            returnString = input;
                        }
                    }
            }
        }
        return returnString;
    }

    private static boolean confirmSaveFilePath(String filePath, boolean fileExists) {
        boolean returnBoolean = false;
        System.out.println(" ");
        if (fileExists) {
            System.out.println("Do You Want To Save Output To");
            System.out.println("'"+filePath+"' ?");
        } else {
            System.out.println("File '"+filePath+"' Does Not Exist.");
            System.out.println("Do You Want To Create The File And Save Output To It?");
        }
        System.out.println(" [1] Yes");
        System.out.println(" [2] No");
        System.out.println(" ");
        System.out.println(" [<] Back");
        System.out.println(" [x] Exit");
        System.out.print("> ");

        String input = grab.next() + grab.nextLine();

        switch (input) {
            case "1":
                returnBoolean = true;
                break;
            case "2":
            case "<":
                returnBoolean = false;
                break;
            case "x":
            case "X":
                exit();
                break;
            default:
                Main.log("Error!! Invalid Input. Pretending You Chose 'No'",3);
                returnBoolean = false;
        }
        return returnBoolean;
    }

    private static void askKeyInput() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("Select A Key Input Method (Select Between [1]-[2]):");
            System.out.println(" [1] Text");
            System.out.println(" [2] Key File");
            System.out.println(" ");
            System.out.println(" [?] Help");
            System.out.println(" [<] Back");
            System.out.println(" [x] Exit");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                    accepted = true;
                    inputKeyText();
                    break;
                case "2":
                    accepted = true;
                    inputKeyFile();
                    break;
                case "?":
                    System.out.println("");
                    System.out.println("+-----+----------+---------------------------------+");
                    System.out.println("|  #  | Options  | Description                     |");
                    System.out.println("+-----+----------+---------------------------------+");
                    System.out.println("| [1] | Text     | Input Key As Plain Text         |");
                    System.out.println("| [2] | Key File | Input Key As A '.skey' Key File |");
                    System.out.println("+-----+----------+---------------------------------+");
                    break;
                case "<":
                    accepted = true;
                    back("askKeyInput()");
                    break;
                case "x":
                case "X":
                    accepted = true;
                    exit();
                    break;
                default:
                    Main.log("Error!! Invalid Input. Please Select An Input From The Given Options", 3);
            }
        }
    }

    private static void inputKeyText() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Your Key:");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "!BCK!":
                case "<":
                    accepted = true;
                    back("inputKeyText()");
                    break;
                case "!END!":
                    accepted = true;
                    exit();
                    break;
                default:
                    boolean keyAsigned = SasSte.getKeyFromText(input);
                    if (keyAsigned) {
                        accepted = true;
                    }
            }
        }
    }

    private static void inputKeyFile() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println(" ");
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Your Key File's Full Path:");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "!BCK!":
                case "<":
                    accepted = true;
                    back("inputKeyFile()");
                    break;
                case "!END!":
                    accepted = true;
                    exit();
                    break;
                default:
                    boolean keyAsigned = SasSte.getKeyFromFile(input);
                    if (keyAsigned) {
                        accepted = true;
                    }
            }
        }
    }

    private static void askFullRep() {
        boolean accepted = false;

        while (!accepted) {
            System.out.println("");
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Number Of Times You Want To Encrypt [Between 1-10]");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                    accepted = true;
                    askGenKey(Main.stringToInt(input)-1);
                    break;
                case "!BCK!":
                case "<":
                    accepted = true;
                    back("askFullRep()");
                    break;
                case "!END!":
                    accepted = true;
                    exit();
                    break;
                default:
                    Main.log("Error!! Invalid Input. Please Enter An Input Within The Given Range",3);
            }
        }
    }

    private static void askGenKey(int fullRep) {
        boolean accepted = false;

        while (!accepted) {
            String generatedKey = SasSte.generateKey(fullRep);

            System.out.println(" ");
            System.out.println("Generated Key: "+generatedKey);
            System.out.println("Do You Want To Accept The Generated Key?");
            System.out.println(" [1] Yes");
            System.out.println(" [2] Yes, Save Key To File");
            System.out.println(" [3] No, Regenerate Key");
            System.out.println(" ");
            System.out.println(" [<] Back");
            System.out.println(" [x] Exit");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                    accepted = true;
                    SasSte.getKeyFromText(generatedKey);
                    break;
                case "2":
                    accepted = true;
                    saveKeyToFile(generatedKey);
                    break;
                case "3":
                    break;
                case "<":
                    accepted = true;
                    back("askGenKey()");
                    break;
                case "x":
                case "X":
                    accepted = true;
                    exit();
                    break;
                default:
                    Main.log("Error!! Invalid Input. Pretending You Chose 'No'",3);
            }
        }
    }

    private static void saveKeyToFile(String keyToSave) {
        boolean accepted = false;

        while (!accepted) {
            System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
            System.out.println("Enter The Full Path To Save Key File [ Without File Extension ] ");
            System.out.print("> ");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "<":
                case "!BCK!":
                    accepted = true;
                    back("saveKeyToFile()");
                    break;
                case "x":
                case "X":
                case "!END!":
                    accepted = true;
                    exit();
                    break;
                default:
                    try {
                        accepted = true;
                        String keyFilePath = input+".skey";
                        File saveKeyFile = new File(keyFilePath);

                        if (!saveKeyFile.exists()) {
                            boolean created = saveKeyFile.createNewFile();
                            if (created) {
                                Main.log("Key File '"+keyFilePath+"' Created",1);
                            } else {
                                Main.log("Error!! Could Not Create Key File '"+keyFilePath+"'",3);
                            }
                        }

                        FileWriter keyFileWrite = new FileWriter(saveKeyFile,true);
                        keyFileWrite.write(keyToSave);
                        keyFileWrite.close();
                        Main.log("Saved Key To Key File '"+keyFilePath+"'",1);

                    } catch (Exception e) {
                        Main.log("Error!! An Error Has Occurred While Saving Key To File",3);
                        Main.log("Please Recheck The Path To Save Key File",4);
                        accepted = false;
                    }
            }
        }
    }

    private static void askStaticKeys() {
        System.out.println("");
        System.out.println("[Commands: !BCK! = Back | !END! = Exit]");
        System.out.println("Enter An Identifier Name For The New StaticKeys");
        System.out.print("> ");

        String input = grab.next() + grab.nextLine();

        switch (input) {
            case "<":
            case "!BCK!":
                back("askStaticKeys()");
                break;
            case "x":
            case "X":
            case "!END!":
                exit();
                break;
            default:
                String identifier = input;
                confirmStaticKeys(identifier);
        }
    }

    private static void confirmStaticKeys(String name) {
        String generatedStaticKeys = "";
        boolean accepted = false;

        while (!accepted) {
            generatedStaticKeys = SasSte.generateStaticKeys(name);
            System.out.println("");
            System.out.println(generatedStaticKeys);
            System.out.println("Do You Accept The Generated StaticKeys");
            System.out.println(" [1] Yes");
            System.out.println(" [2] No");
            System.out.println("");
            System.out.println(" [<] Back");
            System.out.println(" [x] Exit");

            String input = grab.next() + grab.nextLine();

            switch (input) {
                case "1":
                    accepted = true;
                    boolean saved = SasSte.saveStaticKeysToFile(generatedStaticKeys);
                    if (saved) {
                        Main.log("New StaticKeys Saved",1);
                    } else {
                        Main.log("Error!! An Error Has Occurred While Saving StaticKeys",3);
                    }
                    Main.log("Please Re Execute/Open SAS-STE-Wizard",4);
                    exit();
                    break;
                case "2":
                    accepted = false;
                    break;
                case "<":
                case "!BCK!":
                    accepted = true;
                    back("confirmStaticKeys()");
                    break;
                case "x":
                case "X":
                case "!END!":
                    accepted = true;
                    exit();
                    break;
                default:
                    accepted = false;
                    Main.log("Error!! Invalid Input. Pretending You Chose 'No'",3);
            }
        }
    }

    private static void back(String crrMethod) {
        switch (crrMethod) {
            case "askFullRep()":
            case "askKeyInput()":
            case "askStaticKeys()":
            case "confirmStaticKeys()":
                askFunctionInput = "";
                functionType = "";
                askFunction();
                processFunction();
                break;
            case "askGenKey()":
            case "saveKeyToFile()":
                askFullRep();
                break;
            case "inputKeyText()":
            case "inputKeyFile()":
                askKeyInput();
                break;
            case "askInput()":
                switch (askFunctionInput) {
                    case "1":
                    case "4":
                        askFunctionInput = "";
                        functionType = "";
                        askFunction();
                        processFunction();
                        break;
                    case "2":
                        askFullRep();
                        break;
                    case "3":
                    case "5":
                        askKeyInput();
                        break;
                }
                askInputInput = "";
                inputType = "";
                askInput();
                processInput();
                break;
            case "askOutput()Text":
            case "askOutput()File":
                askInputInput = "";
                inputType = "";
                askInput();
                processInput();

                askOutputInput = "";
                outputType = "";
                askOutput();
                processOutput();
                break;
            case "askSaveFilePath()":
                askOutputInput = "";
                outputType = "";
                askOutput();
                processOutput();
                break;
            case "inputFiles()":
                askInputInput = "";
                inputType = "";
                askInput();
                processInput();
                break;
        }
    }

    private static void exit() {
        Main.log("Exiting SAS-STE-Wizard",2);
        System.exit(2);
    }


}
