package com.sas.ste.wiz;

import com.sas.ste.wiz.layer2Main;


class layer2p2 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p2out = "";
private static int arrNum = 0;
private static final int[] StaticKeyL2P2 = {47, 40, 29, 35, 11, 8, 86, 1, 63, 80, 19, 24, 79, 85, 60, 56, 3, 62, 59, 64, 42, 83, 44, 32, 21, 65, 78, 82, 53, 75, 15, 6, 76, 50, 66, 25, 31, 55, 58, 84, 36, 0, 69, 61, 87, 34, 74, 54, 18, 22, 37, 48, 5, 71, 7, 12, 51, 73, 67, 93, 10, 43, 94, 49, 9, 89, 13, 41, 28, 91, 38, 57, 20, 4, 92, 30, 39, 27, 70, 23, 46, 14, 33, 72, 68, 2, 26, 52, 16, 81, 17, 45, 90, 77, 88};

	protected static void encryptL2P2() {
	  layer2p2out = "";
	  getProcessChar();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P2() {
	  layer2p2out = "";
	  getProcessChar();
	  getCharSet();
	  decryptKey();
}
	
	  
	private static void getProcessChar() {
	 processChar = layer2Main.p2ProcessString.toCharArray();
}
	private static void getCharSet() {
	  charSet = layer2Main.charSet;
}


    private static void encryptKey() {
     
	   for(char layer2p2: processChar) {
	
	     arrNum = 0;
        
         while (arrNum < 94  && layer2p2 != charSet[arrNum]) {
          if (arrNum != 94) {
           arrNum++;
          };
         }
         if (arrNum <= 94) {
          if (layer2p2 == charSet[arrNum]){
	          layer2p2out += charSet[StaticKeyL2P2[arrNum]];
          }else {
           layer2p2out += layer2p2;
          }
         }   
      }
     
     }
     

	private static void decryptKey() {
  
	  for(char layer2p2: processChar) {
	 
	    arrNum = 0;
        
        while (arrNum < 94 && layer2p2 != charSet[StaticKeyL2P2[arrNum]]) {
         if (arrNum != 94) {
            arrNum++;
         
         }
        }
        if (arrNum <= 94) {
         if (layer2p2 == charSet[StaticKeyL2P2[arrNum]]){
	         layer2p2out += charSet[arrNum];
         }else {
           layer2p2out += layer2p2;
          }
        }
	   
     }
     
    }


}
