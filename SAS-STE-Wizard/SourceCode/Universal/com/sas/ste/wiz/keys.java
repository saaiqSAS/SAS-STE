package com.sas.ste.wiz;

import com.sas.ste.wiz.encryptionProgram;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


class keys {

private static String staticKeyType = "";
private static String staticKeyInputType = "";
private static String staticKeyOutputType = "";
private static String saveFilePath = "";
private static String currentMethod = "";
private static String staticFunctionType = "";
protected static String key = "$STE::";
protected static final char[] charSet = {' ','!','\"','#','$','%','&','\'','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~'}; //95chars

    protected static void encryptKeys() throws FileNotFoundException {
      encryptControl(); 
    }
    
    protected static void decryptKeys() throws FileNotFoundException {
      decryptControl(); 
    }
    
    private static void encryptControl() throws FileNotFoundException {
    
      staticFunctionType = "1";
      
      if (staticKeyType == "") {
      askEnKeyToUse();
      }
      
      switch (staticKeyType) {
        case "1":
          askKeyInputType();
          break;
          
        case "2":
          if (key == "$STE::") {
          genKey();
          askFullRep();
          key += "::KEY$";
          
          
          keyOutput();
          }
          break;
          
        case "3":
          key += "0";
          key += "0";
          for (char echar : charSet){
            key += echar;
          }
          key += "0";
          key += "0";
          key += "0";
          key += "::KEY$";
          break;
      }
      
      encryptionProgram.key = key;
      
    }
    
    private static void decryptControl() throws FileNotFoundException {
    
        staticFunctionType = "2";
        
        if (staticKeyType == "") {
          askDeKeyToUse();
        }
               
        switch (staticKeyType) {
        case "1":
          askKeyInputType();
          break; 
            
        case "2":
         key += "0";
         key += "0";
         for (char echar : charSet){
            key += echar;
          }
          key += "0";
          key += "0";
          key += "0";
          key += "::KEY$";
          break;
      }
      
      encryptionProgram.key = key;
    }
    
    private static void askEnKeyToUse() throws FileNotFoundException {
        currentMethod = "askEnKeyToUse()";
        Scanner grab = new Scanner(System.in);
    
        System.out.println("Select an Encrypt/Decrypt method (Select from [1]-[3]) \n [1]Existing Key \n [2]New Key \n [3]No Key \n\n [4]Back \n [5]Quit");
        String keyType = grab.next();
        System.out.println("");
    
        switch (keyType) {
          case "1":
            staticKeyType = "1";
            break;
          case "2":
            staticKeyType = "2";
            break;
          case "3":
            staticKeyType = "3";
            break;
          case "4":
            back();
            break;
          case "5":
            quit();
            break;
          default:
            System.out.println("Error!! Pls select a number from the given range ");
            askEnKeyToUse();
        }
      
    }
    
    
    
    private static void askDeKeyToUse() throws FileNotFoundException {
        currentMethod = "askDeKeyToUse()";
        Scanner grab = new Scanner(System.in);
    
        System.out.println("Select an Encrypt/Decrypt method (Select from [1]-[3]) \n [1]Keyed \n [2]Keyless \n\n [3]Back \n [4]Quit");
        String keyType = grab.next();
        System.out.println("");
    
        switch (keyType) {
          case "1":
            staticKeyType = "1";
            break;
          case "2":
            staticKeyType = "2";
            break;
          case "3":
            back();
            break;
          case "4":
            quit();
            break;
          default:
            System.out.println("Error!! Pls select a number from the given range ");
            askDeKeyToUse();
        }
      
    }
    
    private static void askFullRep() throws FileNotFoundException {
        currentMethod = "askFullRep()";
        Scanner grab = new Scanner(System.in);
    
        String multiEncrypt = "";
        int multiEncryptInt = 0;
        System.out.println("[Type !BCK! to go Back] \nEnter the number of times you want to encrypt (max 5 times)");

        multiEncrypt = grab.next();
        System.out.println("");
        
         switch (multiEncrypt) {
           case "!BCK!":
             back();
             break;    
            default:
              
         switch (multiEncrypt) {
           case "0":
              multiEncryptInt = 0;
              break;
            case "1":
              multiEncryptInt = 1;
              break;
            case "2":
              multiEncryptInt = 2;
              break;
            case "3":
              multiEncryptInt = 3;
              break;
            case "4":
              multiEncryptInt = 4;
              break;
            case "5":
              multiEncryptInt = 5;
              break;
            default:          
              System.out.println("Error!! Pls enter a number from 0-5");
            }
        
        if (multiEncryptInt > 5) {
          System.out.println("Error!! Pls enter a number from 1-5");
          askFullRep();
        } else if (multiEncryptInt == 0) {
          System.out.println("Error!! Pls enter a number from 1-5");
          askFullRep();
        } else {
          key += multiEncryptInt-1; // fullRep
        }   
        }
    }
    
    private static void askKeyInputType() throws FileNotFoundException {
        currentMethod = "askKeyInputType()";
        Scanner grab = new Scanner(System.in);
        
        System.out.println("Select a key input method (Select from [1]-[4]) \n [1]Text \n [2]File \n\n [3]Back \n [4]Quit");
        String keyInputType = grab.next();
        System.out.println("");
        
        switch (keyInputType) {
          case "1":
            staticKeyInputType = "1";
            getTextKey();
            break;
          case "2":
            staticKeyInputType = "2";
            getKeyFile();
            break;
          case "3":
            staticKeyInputType = "3";
            back();
            break;
          case "4":
            staticKeyInputType = "4";
            quit();
            break;
          default:
          System.out.println("Error!! Pls select a number from the given range ");
          askKeyInputType();
        }
    }
    
    private static void getTextKey() throws FileNotFoundException {
        currentMethod = "getTextKey()";
        Scanner grab = new Scanner(System.in);
        
        System.out.println("[Type !BCK! to go Back] \nEnter your the key:");
        String textKey = grab.next() + grab.nextLine();
        System.out.println("");
        
        switch (textKey) {
		   case "!BCK!":
		     back();
		     break;
		   default:
		   
            if (textKey.length() == 112) {
              key = textKey;
            } else {
              System.out.println("Error!! Incorrect key format");
              getTextKey();
            }
           }
    }
    
    private static void getKeyFile() throws FileNotFoundException {
        currentMethod = "getKeyFile()";
        Scanner grab = new Scanner(System.in);

		  System.out.println("[Type !BCK! to go Back] \nGive the key file path. Start from root directory,(eg:D:\\\\Test\\\\file.skey): ");
	 try {
		 String keyFilePath = grab.next();
		 System.out.println("");
		  
		 switch (keyFilePath) {
		   case "!BCK!":
		     back();
		     break;
		   default:     
		   
		 // File type check for .skey format
		 
		 int filePathCharLength = keyFilePath.length() - 1;
		 char[] keyFilePathChar = keyFilePath.toCharArray();
		 
		 if (keyFilePathChar[filePathCharLength -4] == '.'&& keyFilePathChar[filePathCharLength -3] == 's' && keyFilePathChar[filePathCharLength -2] == 'k' && keyFilePathChar[filePathCharLength -1] == 'e' && keyFilePathChar[filePathCharLength] == 'y') {      
		 
		 File file = new File(keyFilePath);

		  Scanner scannedKeyFile = new Scanner(file);
		  
		  // Error handling 
		  
		  if (scannedKeyFile.hasNextLine() == false) {
		    System.out.println("Error!! File is empty");
		    getKeyFile();
		    
          }else {
          
          // key format (length) check
          
            String fileKey = scannedKeyFile.nextLine();
            
            if (fileKey.length() == 112) {
             key = fileKey;
            } else {
             System.out.println("Error!! Incorrect key format");
             getKeyFile();
          }
          }         
          } else {
            System.out.println("Error!! Incorrect key file format (.skey files are only supported)");
            getKeyFile();
          }
          }

	 } catch (Exception e) {
		   System.out.println("Error!! File not found");
		   getKeyFile();
	 }
    }
    
    private static void genKey() {
   
        Random rand = new Random();
        Integer[] charSetKey = new Integer[95];
    
        for (int i = 0; i < charSetKey.length; i++) {
            charSetKey[i] = i;
        }
        Collections.shuffle(Arrays.asList(charSetKey));
        
        key += rand.nextInt(2); // L0Type
        key += rand.nextInt(5); //L2pAsign
        
        for (int charKey : charSetKey) {
          key += charSet[charKey];
        }
        
        key += rand.nextInt(9); //L1Rep
        key += rand.nextInt(9); //L2Rep
        //key += rand.nextInt(9); // fullRep
      
    }
    
    private static void keyOutput() throws FileNotFoundException {
        currentMethod = "keyOutput()";
        Scanner grab = new Scanner(System.in);

	   System.out.println("Select a key output method (select from [1]-[4]) \n [1]Print key \n [2]Save key to file \n\n [3]Back \n [4]Quit");

	   String outputType = grab.next();
		System.out.println("");

	   switch (outputType) {
		 case "1":
		  staticKeyOutputType = "1"; 
		  System.out.println("Key: "+key);
          System.out.println("");
		  break;
		 case "2":
		  staticKeyOutputType = "2";
		  askSaveKeyPath();
		  saveToFile();
		  break;
		 case "3":
		  staticKeyOutputType = "3";
		  back();
		  break;
		 case "4":
		  staticKeyOutputType = "4";
		  quit();
		  break;
		 default:
		  System.out.println("\nError!! Pls select a number from the given range");
		  keyOutput();

	   }
    }
    
    private static void askSaveKeyPath() throws FileNotFoundException {
	     currentMethod = "askSaveKeyPath()";
	     Scanner grab = new Scanner(System.in);
	
	    System.out.println("[Type !BCK! to go Back] \nEnter a path to key saving file. Start from root directory,(eg:D:\\\\Test\\\\fileName)(File extention is not needed):");
	    saveFilePath = grab.next();    
	    System.out.println("");
	  
	    switch (saveFilePath) {
		   case "!BCK!":
		     back();
		     break;
		   default: 
		
		saveFilePath += ".skey";   
	    System.out.println("Are you sure you want to save the key to \n"+saveFilePath+"\n [1]Yes \n [2]No");
	    String reCheck = grab.next();
	    System.out.println("");
	    
	    switch (reCheck) {
	      case "1":
				  System.out.println("[+]Saving key to " + saveFilePath);
				  System.out.println("");
				  break;
			  case "2":
				  askSaveKeyPath();
				  break;
			  default:
				  System.out.println("Error!! Pls select a number from the given range");
				  askSaveKeyPath();
        } 
        }
	}
	
	private static void saveToFile() {
	    try{ 
	    FileWriter wfile = new FileWriter(saveFilePath, true);
	  
	    wfile.write(key + "\n");
	    wfile.close();
	    }catch(Exception e){};
    }
    
    private static void back() throws FileNotFoundException {
        encryptionProgram.backAmount++;
        int backAmount = encryptionProgram.backAmount;
        int maxBackAmount = 2;
        
        if (backAmount <= maxBackAmount) {
        
        switch (currentMethod) {
          case "askEnKeyToUse()":
            staticKeyType = "";
            encryptionProgram.askFunctionType();
            encryptionProgram.processFunctionType();
            break;
            
          case "askDeKeyToUse()":
            staticKeyType = "";
            encryptionProgram.askFunctionType();
            encryptionProgram.processFunctionType();
            break;
            
          case "askFullRep()":
            key = "$STE::";
            staticKeyType = "";
            encryptControl();
            break;
            
          case "askKeyInputType()":
             switch (staticFunctionType) {
               case "1":
                 key = "$STE::";
                 staticKeyType = "";
                 encryptControl();                 
                 break;
               case "2":
                key = "$STE::";
                staticKeyType = "";
                decryptControl();
                 break;
             }
            break;
            
          case "getTextKey()":
            askKeyInputType();
            break;
            
          case "getKeyFile()":
            askKeyInputType();
            break;
            
          case "keyOutput()":            
            char[] keyChar = key.toCharArray();
            int fullRepPlaceValue = 105;
            int currentPlaceValue = 0;
            key = "";
              while (currentPlaceValue < fullRepPlaceValue) {
                key += keyChar[currentPlaceValue];
                currentPlaceValue++;
              }
            askFullRep();
            break;
            
          case "askSaveKeyPath()":
           saveFilePath = "";
           keyOutput();
            break;           
          default:
            System.out.println("Error at KB");
        }
        } else if (backAmount > maxBackAmount) {
        System.out.println("Alert!! Too many backs used");
        quit();
        }
    }
       
    private static void quit() {
	    System.out.println("\n[+]Exiting Program");
	    System.exit(2);
	}
	
	protected static void keyVarFullRst() {
	   staticKeyType = "";
       staticKeyInputType = "";
       staticKeyOutputType = "";
       saveFilePath = "";
        currentMethod = "";
       staticFunctionType = "";
       key = "$STE::";
    }
}
