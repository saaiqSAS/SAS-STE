package com.sas.ste.wiz;

import com.sas.ste.wiz.layer2Main;


class layer2p1 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p1out = "";
private static int arrNum = 0;
private static final int[] StaticKeyL2P1 = {59, 89, 15, 43, 34, 78, 5, 72, 68, 82, 86, 7, 85, 65, 69, 8, 16, 33, 94, 93, 42, 18, 53, 26, 32, 87, 41, 17, 2, 55, 79, 83, 31, 46, 76, 6, 12, 54, 36, 11, 23, 20, 9, 21, 74, 92, 62, 58, 3, 57, 35, 1, 66, 13, 67, 61, 10, 47, 52, 14, 22, 48, 60, 39, 75, 25, 88, 64, 73, 38, 19, 81, 56, 71, 0, 90, 30, 50, 77, 37, 24, 44, 40, 80, 63, 70, 45, 84, 91, 27, 4, 29, 49, 51, 28};

	protected static void encryptL2P1() {
	  layer2p1out = "";
	  getProcessChar();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P1() {
	  layer2p1out = "";
	  getProcessChar();
	  getCharSet();
	  decryptKey();
}
	
	  
	private static void getProcessChar() {
	 processChar = layer2Main.p1ProcessString.toCharArray();
}
	private static void getCharSet() {
	  charSet = layer2Main.charSet;
}

    private static void encryptKey() {
     
	   for(char layer2p1: processChar) {
	
	     arrNum = 0;
        
         while (arrNum < 94  && layer2p1 != charSet[arrNum]) {
          if (arrNum != 94) {
           arrNum++;
          };
         }
         if (arrNum <= 94) {
          if (layer2p1 == charSet[arrNum]){
	          layer2p1out += charSet[StaticKeyL2P1[arrNum]];
          }else {
           layer2p1out += layer2p1;
          }
         }   
      }
     
     }
     

	private static void decryptKey() {
  
	  for(char layer2p1: processChar) {
	 
	    arrNum = 0;
        
        while (arrNum < 94 && layer2p1 != charSet[StaticKeyL2P1[arrNum]]) {
         if (arrNum != 94) {
            arrNum++;
         
         }
        }
        if (arrNum <= 94) {
         if (layer2p1 == charSet[StaticKeyL2P1[arrNum]]){
	         layer2p1out += charSet[arrNum];
         }else {
           layer2p1out += layer2p1;
          }
        }
	   
     }
     
    }

}
