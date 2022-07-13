package com.sas.ste.wiz;

import com.sas.ste.wiz.keys;
import com.sas.ste.wiz.process;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

class encryptionProgram {

protected static String processString = "";
private static String staticInputType = "";
private static String staticTextAmount = "";
private static String staticFunctionType = "";
private static String staticOutputType = "";
private static String inputFilePath = "";
private static Scanner scannedFile;
private static String saveFilePath = "";
private static boolean fileHasNextLine;
private static String finalResult;
private static boolean enableProcess;
private static boolean enableOutput;
private static boolean firstRunDone;
private static boolean dissableIPOalter;
private static String currentMethod = "";
private static boolean cmmndDisplayed;
private static boolean tempFilePathExtracted = false;
private static boolean saveToInputFileProcessingDisplayed = false;
private static String tempFilePath = "";
protected static int backAmount = 0;
protected static String key = "";


	protected static void start() throws FileNotFoundException {
	  masterControl();
	}

	private static void masterControl() throws FileNotFoundException {

		process.getStaticKeys();

		encryptionProgram(); // to get user inputs
		firstRunDone = true;
		encryptionProgram(); // to process

		switch (staticInputType) {
			case "1":
				//test
				if (staticTextAmount == "2") {      
				    while (staticTextAmount == "2" && processString.equals("!END!") == false) {     
				      encryptionProgram();
                    } 
                } else if (staticTextAmount == "1") {
                    if (staticOutputType == "1") {
                      System.out.println("");
                    }
                }
				
				break;
			case "2":
				fileHasNextLine = scannedFile.hasNextLine();

				while (fileHasNextLine == true) {
					encryptionProgram();
					fileHasNextLine = scannedFile.hasNextLine();
				}
				if (staticOutputType.equals("3")) {
					saveToInputFile();
				}
				break;
			default:
				System.out.println("Error at MC");
		}
		if (staticInputType == "2") {
		  if (staticOutputType == "1") {
		    System.out.println("");
          }
        }
		System.out.println("[+]Finished");
	}


	private static void encryptionProgram() throws FileNotFoundException {

		//-----Input-----
		processInputType();
		
        //-----Function-----
		processFunctionType();
		
		//-----GetResult-----
		getFinalResult();

		//-----Output-----
		processOutputType();
		
		//-----enable-----
		if (dissableIPOalter == false) {
		enableProcess = true;
		enableOutput = true;
		}
		dissableIPOalter = false;
		
		//-----multiTextInput-----
		
		if (staticTextAmount == "2") {
		    if (staticOutputType == "1") {
		        if (finalResult.equals("") == false) {
		          System.out.println("");
		        }
            }
            if (cmmndDisplayed == false) {
            System.out.println("=========================================");
		    System.out.println("Commands: ");
		    System.out.println("  !END! = Finish   |  !BCK! = Back");
		    System.out.println("  !FE! = Encrypt   |  !FD! = Decrypt");
		    System.out.println("  !EL! = EmptyLine | ");
		    System.out.println("=========================================");
		    System.out.println("");
		    cmmndDisplayed = true;
		    }
		    
		    String Func = "";
		    switch (staticFunctionType) {
		      case "1":
		        Func = "Encrypt";
		        break;
		      case "2":
		        Func = "Decrypt";
		        break;
            };
            
		    System.out.println("Function: "+Func);
			getText();
		}
		
	}
	
	
	private static void processInputType() throws FileNotFoundException {
	
	    currentMethod = "processInputType()";
	    if (staticInputType == "") {
			askInputType();
		}
		switch (staticInputType) {
			case "1":
			    if (staticTextAmount == "") {
					askTextAmount();
				}
				switch(staticTextAmount) {
	              case "1":
	                if (firstRunDone == false) {
		    	      getText();
		    	    }
	                break;
	              case "2":
		    	    // Continued as "multiTextInput" at end of EP
	                break;
	              case "3": 
	                back();
	                break;                             
	              case "4":
		    	    quit();
	                break;
	             default:
	              System.out.println("Error at EP-AIT-ATA");
	              askTextAmount();   
	           }
			
				break;
			case "2":
				if (inputFilePath == "") {
					getFile();
				}
				if (firstRunDone == true) {
				getNextLine();
				}
				break;
			default:
				System.out.println("Error at EP-AIT");
		}
	}
	
	
	protected static void processFunctionType() throws FileNotFoundException {
	
	    currentMethod = "processFunctionType()";
	    if (staticFunctionType == "") {
			askFunctionType();
		}
		switch (staticFunctionType) {
			case "1":
			    if (key == "") {
			      keys.encryptKeys();
                }
			    if (enableProcess = true) { 
				  process.encrypt();
				}
				break;
			case "2":
			    if (key == "") {
			      keys.decryptKeys();
                }
			    if (enableProcess = true) { 
				  process.decrypt();
				}
				break;
			case "3":
				back();
				break;
			case "4":
				quit();
				break;
			default:
				System.out.println("Error at EP-AFT");
		}
	}
	
	
	private static void processOutputType() throws FileNotFoundException {
	
	    currentMethod = "processOutputType()";
	    if (staticOutputType == "") {
			askOutputType();
		}
		switch (staticOutputType) {
			case "1":   
			    if (enableOutput == true) {
				  System.out.println(finalResult);
			    }
				break;
			case "2":
			    if (saveFilePath == "") {
	              askSaveFilePath();
	            }
			    if (enableOutput == true) {
				  saveToFile();
				}
				break;
			case "3":
				if (staticInputType.equals("1")) {
					back();
				}else if (staticInputType.equals("2")) {
					if (!saveToInputFileProcessingDisplayed) {
						System.out.println("[+]Output will be saved to " + inputFilePath);
						System.out.println("[+]Processing");
						saveToInputFileProcessingDisplayed = true;
					}
						saveToTempFile();
				}
				break;
			case "4":
				if (staticInputType.equals("1")) {
					quit();
				}else if (staticInputType.equals("2")) {
					back();
				}
				break;
			case "5":
				if (staticInputType.equals("2")) {
					quit();
				}
				break;
			default:
				System.out.println("Error at EP-AOT");
		}
	}


	private static void askInputType() throws FileNotFoundException {
	  Scanner grab = new Scanner(System.in);
	  
	  System.out.println("Select an input method (select from [1]-[3]) \n [1]Text \n [2]File (text based)  \n\n [3]Quit");
	  String inputType = grab.next();
	  System.out.println("");
	  
	  switch(inputType) {
	    case "1":
	     staticInputType = "1";
	     break;
	    case "2":
	     staticInputType = "2";
	     break;
	    case "3":
	     quit();
	     break;
	    default:
	     System.out.println("Error!! Pls select a number from the given range");
	     askInputType();
	  }
	  
	}


	protected static void askFunctionType() throws FileNotFoundException {
	 Scanner grab = new Scanner(System.in);
	
	  System.out.println("Select a function (select from [1]-[4]) \n [1]Encrypt \n [2]Decrypt \n\n [3]Back \n [4]Quit");
	  String functionType = grab.next();
	  System.out.println("");
	  
	  switch(functionType) {
	    case "1":
			staticFunctionType = "1";
	     break;
	    case "2":
			staticFunctionType = "2";
	     break;
	    case "3":
			staticFunctionType = "3";
	     break;
	    case "4":
			staticFunctionType = "4";
	     break;
	    default:
	     System.out.println("Error!! Pls select a number from the given range");
	     askFunctionType();   
	  }
	  
	}
	
	
	private static void askTextAmount() {
	
	  currentMethod = "askTextAmount()";
	  Scanner grab = new Scanner(System.in);
	
	  System.out.println("Select a text input amount (select from [1]-[4]) \n [1]One time \n [2]Multiple times \n\n [3]Back \n [4]Quit");
	  String textAmount = grab.next();
	  System.out.println("");
	  
	  switch(textAmount) {
	    case "1":
			staticTextAmount = "1";
	     break;
	    case "2":
			staticTextAmount = "2";
			enableProcess = false; // to allow text input after all inputs
			enableOutput = false; // to allow text input after all inputs
	     break;
	    case "3":
			staticTextAmount = "3";
	     break;
	    case "4":
			staticTextAmount = "4";
	     break;
	    default:
	     System.out.println("Error!! Pls select a number from the given range");
	     askTextAmount();   
	  }
    }


	private static void getText() throws FileNotFoundException {
	
	     currentMethod = "getText()";
	     Scanner grab = new Scanner(System.in);

		 switch (staticTextAmount) {
		   case "1":
		     System.out.println("[Type !BCK! to go Back] \nEnter your text:");
		     break;
		   case "2":
		     System.out.println("Enter your text: ");
		     break;
         }
		 String text = grab.next();
		 text += grab.nextLine();
		 System.out.println("");
	  
	     processString = text;
	     
	     switch (processString) {
	       case "!EL!":
	         processString = "";
	         break;
	       case "!FE!":
	         staticFunctionType = "1";
	         enableProcess = false;
	         enableOutput = false;
	         break;
	       case "!FD!":
	         staticFunctionType = "2";
	         enableProcess = false;
	         enableOutput = false;
	         break;
	       case "!BCK!":
	         back();
	         break;
         };
	     
	  
	}


	private static void getFile() throws FileNotFoundException {
		 
		 currentMethod = "getFile()";
		 Scanner grab = new Scanner(System.in);
		 inputFilePath = "";
		  System.out.println("[Type !BCK! to go Back] \nGive the input file path. Start from root directory,(eg:D:\\Test\\file.txt): ");
	 try {
		 String tempInputFilePath = grab.next() + grab.nextLine();
		 System.out.println("");
		 char[] tempInputFilePathArr = tempInputFilePath.toCharArray();

		 for (char echar : tempInputFilePathArr) {
			 if (echar == '\\') {
				 inputFilePath += "\\\\";
			 } else {
				 inputFilePath += echar;
			 }
		 }
		 
		 switch (inputFilePath) {
		   case "!BCK!":
		     back();
		     break;
		   default:         
		 File file = new File(inputFilePath);

		  scannedFile = new Scanner(file);
		  
		  if (scannedFile.hasNextLine() == false) {
		    System.out.println("Error!! File is empty");
		    getFile();
          }
          }

	 } catch (Exception e) {
		   System.out.println("Error!! File not found");
		   getFile();
	 }
	
	}


	private static void getNextLine() throws FileNotFoundException {
	
	 fileHasNextLine = scannedFile.hasNextLine();
	
	 if (fileHasNextLine == true) {
	  processString = scannedFile.nextLine();
	 }
	}


	private static void getFinalResult() throws FileNotFoundException {
	  finalResult = process.result;
	}


	private static void askOutputType() throws FileNotFoundException {
	   Scanner grab = new Scanner(System.in);

	   System.out.println("Select an output method (select from [1]-[4]) \n [1]Print output \n [2]Save to file");
	   if (staticInputType.equals("2")) {
		   System.out.println(" [3]Save to input file \n\n [4]Back \n [5]Quit");
	   }else if (staticInputType.equals("1")) {
		   System.out.println("\n [3]Back \n [4]Quit");
	   }

	   String outputType = grab.next();
		System.out.println("");

	   switch (outputType) {
		 case "1":
		  staticOutputType = "1";
		  break;
		 case "2":
		  staticOutputType = "2";
		  break;
		 case "3":
		  staticOutputType = "3";
		  break;
		 case "4":
		  staticOutputType = "4";
		  break;
	     case "5":
		  if (staticInputType.equals("2")) {
			  staticOutputType = "5";
		  }else{
			  System.out.println("\nError!! Pls select a number from the given range");
			  askOutputType();
		  }
		  break;
		 default:
		  System.out.println("\nError!! Pls select a number from the given range");
		  askOutputType();

	   }

	}

	private static void saveToTempFile() {
		//get path for temp file
		try {
			if (!tempFilePathExtracted) {
				String tempFilePathExtract = new File("Temp.stemp").getCanonicalPath();
				char[] tempFilePathArr = tempFilePathExtract.toCharArray();

				for (char echar : tempFilePathArr) {
					if (echar == '\\') {
						tempFilePath += "\\\\";
					} else {
						tempFilePath += echar;
					}
				}
				tempFilePathExtracted = true;
			}
			FileWriter wfile = new FileWriter(tempFilePath, true);
			if (firstRunDone) {
				wfile.write(finalResult + "\n");
			}
			wfile.close();
		}catch (Exception e){
			System.out.println(e);
		}
	}

	private static void saveToInputFile() {
		try {
			File tempFile = new File(tempFilePath);
			boolean scannedTempFileHasNextLine;
			String tempFileLine = "";
			int shredAmount = 0;

			Scanner scannedTempFile = new Scanner(tempFile);
			scannedTempFileHasNextLine = scannedTempFile.hasNextLine();

			FileWriter shredInputFile = new FileWriter(inputFilePath,false);
			File deleteInputFile = new File(inputFilePath);
			if (deleteInputFile.exists()) {
				shredAmount = 0;
				while (shredAmount <= 10) {
					shredInputFile.write("SAS-STE");
					shredAmount++;
				}
				scannedFile.close();
				shredInputFile.close();
				deleteInputFile.delete();
			}

			FileWriter inputFileOverride = new FileWriter(inputFilePath,true);

			while (scannedTempFileHasNextLine) {
				tempFileLine = scannedTempFile.nextLine();
				inputFileOverride.write(tempFileLine + "\n");
				scannedTempFileHasNextLine = scannedTempFile.hasNextLine();
			}
			inputFileOverride.close();

			if (tempFile.exists()) {
				FileWriter shredTempFile = new FileWriter(tempFilePath, false);
				shredAmount = 0;
				while (shredAmount <= 10) {
					shredTempFile.write("SAS-STE temp file");
					shredAmount++;
				}
				shredTempFile.close();
				scannedTempFile.close();
				tempFile.delete();
			}

		}catch (Exception e) {
			System.out.println(e);
		}


	}
 
	private static void saveToFile() {
	
	try{ 
	  FileWriter wfile = new FileWriter(saveFilePath, true);
	  
	  wfile.write(finalResult + "\n");
	  wfile.close();
	}catch(Exception e){};
	 
	}
 
 
	private static void askSaveFilePath() throws FileNotFoundException {
	
	  currentMethod = "askSaveFilePath()";
	  Scanner grab = new Scanner(System.in);
	  saveFilePath = "";
	
	  System.out.println("[Type !BCK! to go Back] \nEnter a path to saving file. Start from root directory,(eg:D:\\Test\\file.txt):");
	  String filePath = grab.next() + grab.nextLine();
	  System.out.println("");
		char[] filePathArr = filePath.toCharArray();

		for (char echar : filePathArr) {
			if (echar == '\\') {
				saveFilePath += "\\\\";
			} else {
				saveFilePath += echar;
			}
		}
	  
	  switch (saveFilePath) {
		   case "!BCK!":
		     back();
		     break;
           default:
         
	  File file = new File(filePath);
	  
	  // outputfile same as inputfile Error handle
	  if (saveFilePath.equals(inputFilePath)) {
	    System.out.println("Error!! Cannot save output to input file");
	    askSaveFilePath();
      } else {
	  
	  // file exists or doesn't exist Error handle
	  if (file.exists() == false) {
	    System.out.println("File doesn't exist!!\nDo you want to create and save to the file \n"+filePath+"?"+"\n [1]Yes \n [2]No");
	    
	    String createFile = grab.next();
		  System.out.println("");

	    switch (createFile) {
	      case "1":
	      System.out.println("[+]Creating File " + filePath);
	      System.out.println("[+]Output will be saved to " + filePath);
		  System.out.println("[+]Processing");
		  System.out.println("");
	       break;
	      case "2":
	      askSaveFilePath();
	       break;
	      default:
	      System.out.println("Error!! Taking \"No\" as your choice!!");
	      askSaveFilePath();       
	    }
	  } else if (file.exists() == true) {
		  System.out.println("Are you sure you want to save to  \n" + filePath + "? (File will not be overridden)" + "\n [1]Yes \n [2]No");

		  String reAsk = grab.next();
		  System.out.println("");

		  switch (reAsk) {
			  case "1":
				  System.out.println("[+]Output will be saved to " + filePath);
				  System.out.println("[+]Processing");
				  System.out.println("");
				  break;
			  case "2":
				  askSaveFilePath();
				  break;
			  default:
				  System.out.println("Error!! taking \"No\" as your choice!!");
				  askSaveFilePath();
		  }
	  }
	  }
	  }
	}
	
	
	private static void back() throws FileNotFoundException {
	    backAmount++;
	    int maxBackAmount = 2;
	    
	    if (backAmount <= maxBackAmount) {
	    
	    switch (currentMethod) {
	    
	      case "askTextAmount()":
	        staticTextAmount = ""; 
	        staticInputType = "";
	        processInputType();
	        break;
	        
	      case "processFunctionType()": 
	        switch (staticInputType) {
	          case "1":
	            switch (staticTextAmount) {
	              case "1":
	                staticFunctionType = "";
	                processString = "";
	                getText();
	                processFunctionType();
	                break;
	              case "2":
	                staticFunctionType = "";
	                staticTextAmount = "";
	                processInputType();
	                processFunctionType();                
	                break;      
                }
	            break;
	          case "2":
	            staticFunctionType = "";
	            inputFilePath = "";
	            getFile();
	            processFunctionType(); 
	            break;
            }
	        break;
	        
	      case "processOutputType()":
	        staticOutputType = "";
	        staticFunctionType = "";
	        key = "";
	        keys.keyVarFullRst();
	        processFunctionType();
	        processOutputType();
	        break;
	        
	      case "getText()":
	        processString = "";
	        firstRunDone = false;
	        enableProcess = false;
	        enableOutput = false;
	        switch (staticTextAmount) {
	          case "1" :            
	            staticTextAmount = "";
	            processInputType();
	            break;
	          case "2":            
	            cmmndDisplayed = false;
	            staticOutputType = "";
	            processOutputType();
	            break;
            }
	        break;
	        
	      case "getFile()":
	        inputFilePath = "";
	        staticInputType = "";
	        processInputType();
	        break;
	        
	      case "askSaveFilePath()":
	        saveFilePath = "";
	        staticOutputType = "";
	        processOutputType();
	        break;
	        
	      default:
	        System.out.println("Error at back");
	        System.out.println(currentMethod+" not found");
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


/* backup of Charset
protected static char[] charSet = {' ','!','\"','#','$','%','&','\'','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~','£','€'}; //97chars

*/

}
