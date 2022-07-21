package com.sas.ste.mobile;

import com.sas.ste.mobile.process;
import java.util.Random;


class layer0 {

private static char[] charSet;
private static boolean charSetCheck;
private static char[] processChar;
private static String processString = "";
private static String layer0p1out = "";
protected static String layer0out = "";
protected static int L0Type;

	protected static void encryptL0() {
	  layer0out = "";
	  layer0p1out = "";
	  getCharSet();
	  getEncryptProcessCharString();
	  encryptKey();
}

	protected static void decryptL0() {
	  layer0out = "";
	  layer0p1out = "";
	  getCharSet();
	  getDecryptProcessCharString();
	  decryptKey();
}


	private static void getCharSet(){
	
	  if (charSetCheck == false) {
	    charSet = process.charSet;
	    charSetCheck = true;
	  } 
}

	private static void getEncryptProcessCharString() {
	  processChar = process.processChar;
	  processString = process.processString;
}

	private static void getDecryptProcessCharString() {
	  processChar = process.layer1out.toCharArray();
	  processString = process.layer1out;
}



	private static void encryptKey() {
	
	  Random rand = new Random(); 
	  
	  char randChar;
	  
	  int pCharLength = (processString.length()*2)-1;
	  
	  int pChar = 0;
	  int sNum = 1;
	  
	  for (char eChar : processChar) {
	    randChar = charSet[rand.nextInt(94)];
        while (randChar == ' ') {
          randChar = charSet[rand.nextInt(94)];
        }
	    switch (L0Type) {
	      case 0:
	        if (sNum > 3) {
	          sNum = 1;
            }
	        if (sNum == 1 || sNum == 2) {
	          layer0p1out += randChar;
	          layer0p1out += eChar;
	          
	          sNum++;
            } else if (sNum == 3) {
              layer0p1out += eChar;
              sNum++;
            }
	        break;
	        
	      case 1:
	        if (sNum > 3) {
	          sNum = 1;
            } 
	        if (sNum == 2 || sNum == 3) {
	          layer0p1out += randChar;
	          layer0p1out += eChar;
	          
	          sNum++;
            } else if (sNum == 1) {
              layer0p1out += eChar;
              sNum++;
            }
	        break;
	        
	      case 2:
	        if (sNum > 3) {
	          sNum = 1;
            }
	        if (sNum == 1 || sNum == 3) {
	          layer0p1out += randChar;
	          layer0p1out += eChar;
	          
	          sNum++;
            } else if (sNum == 2) {
              layer0p1out += eChar;
              sNum++;
            }
	        break;
        }
      }
      
      char[] layer0p1outChar = layer0p1out.toCharArray();
      int rev = layer0p1out.length()-1;
       
while (layer0out.length() < layer0p1out.length()) {

      layer0out += layer0p1outChar[rev];
      rev -= 1;
      }   
      
}

	private static void decryptKey() {
	  
	  int pCharLength = processString.length();
	  int rev = processString.length() - 1; 
	  
	 while (layer0p1out.length() < pCharLength) {
	   layer0p1out += processChar[rev];
	   rev -= 1;
     }
     
     char[] layer0p1outChar = layer0p1out.toCharArray();
     int sNum = 1;
     switch (L0Type) {
	      case 0:
            for (char eChar : layer0p1outChar) {
              if (sNum > 5) {
                sNum = 1;
              }
              if (sNum == 2 || sNum == 4 || sNum == 5) {
                layer0out += eChar;
              }
              sNum++;
            }
	        break;
	        
	      case 1:
	        for (char eChar : layer0p1outChar) {
              if (sNum > 5) {
                sNum = 1;
              }
              if (sNum == 1 || sNum == 3 || sNum == 5) {
                layer0out += eChar;
              }
              sNum++;
            }
	        break;
	        
	      case 2:
	        for (char eChar : layer0p1outChar) {
              if (sNum > 5) {
                sNum = 1;
              }
              if (sNum == 2 || sNum == 3 || sNum == 5) {
                layer0out += eChar;
              }
              sNum++;
            }
	        break;
        }
        
}

}
