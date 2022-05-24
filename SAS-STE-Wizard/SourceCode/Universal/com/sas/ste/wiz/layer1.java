package com.sas.ste.wiz;

import com.sas.ste.wiz.process;



class layer1 {

private static char[] processChar;
private static char[] charSet;
protected static String layer1out = "";
private static int arrNum = 0;
private static final int[] StaticKeyL1 = {9, 29, 91, 67, 75, 30, 59, 38, 68, 55, 80, 52, 26, 37, 56, 40, 66, 10, 65, 11, 81, 49, 18, 85, 88, 70, 51, 6, 43, 82, 41, 42, 74, 87, 12, 86, 45, 39, 58, 35, 47, 21, 3, 31, 23, 33, 16, 57, 32, 17, 84, 60, 93, 19, 20, 69, 22, 24, 5, 46, 8, 90, 83, 78, 54, 4, 48, 13, 53, 94, 77, 76, 89, 63, 34, 0, 62, 28, 79, 36, 1, 14, 2, 50, 61, 64, 72, 73, 25, 7, 15, 27, 71, 44, 92};

	protected static void encryptL1() {
	  layer1out  = "";
	  getEncryptProcessChar();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL1() {
	  layer1out = "";
	  getDecryptProcessChar();
	  getCharSet();
	  decryptKey();
}

	private static void getEncryptProcessChar() {
	  processChar = process.layer0out.toCharArray();
}

	private static void getDecryptProcessChar() {
	  processChar = process.layer2out.toCharArray();
}

	private static void getCharSet() {
	  charSet = process.charSet;
}

    private static void encryptKey() {
     
	   for(char layer1: processChar) {
	
	     arrNum = 0;
        
         while (arrNum < 94  && layer1 != charSet[arrNum]) {
          if (arrNum != 94) {
           arrNum++;
          };
         }
         if (arrNum <= 94) {
          if (layer1 == charSet[arrNum]){
	          layer1out += charSet[StaticKeyL1[arrNum]];
          }else {
           layer1out += layer1;
          }
         }   
      }
     
     }
     

	private static void decryptKey() {
  
	  for(char layer1: processChar) {
	 
	    arrNum = 0;
        
        while (arrNum < 94 && layer1 != charSet[StaticKeyL1[arrNum]]) {
         if (arrNum != 94) {
            arrNum++;
         
         }
        }
        if (arrNum <= 94) {
         if (layer1 == charSet[StaticKeyL1[arrNum]]){
	         layer1out += charSet[arrNum];
         }else {
           layer1out += layer1;
         }
        }
	   
     }
     
    }

}
