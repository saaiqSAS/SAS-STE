package com.sas.ste.wiz;

import com.sas.ste.wiz.layer2Main;


class layer2p3 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p3out = "";
private static int arrNum = 0;
private static final int[] StaticKeyL2P3 = {10, 73, 51, 34, 87, 53, 76, 41, 74, 31, 23, 39, 63, 57, 32, 43, 36, 20, 92, 9, 64, 33, 46, 88, 26, 13, 55, 1, 3, 60, 14, 67, 82, 59, 94, 49, 22, 84, 28, 2, 62, 5, 65, 25, 6, 78, 37, 21, 29, 30, 85, 91, 16, 71, 79, 52, 61, 7, 19, 8, 12, 44, 0, 45, 89, 77, 80, 27, 68, 50, 42, 24, 15, 72, 69, 35, 56, 47, 83, 66, 17, 18, 48, 38, 81, 86, 93, 54, 4, 75, 58, 70, 90, 40, 11};

	protected static void encryptL2P3() {
	  layer2p3out = "";
	  getProcessChar();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P3() {
	  layer2p3out = "";
	  getProcessChar();
	  getCharSet();
	  decryptKey();
}
	
	  
	private static void getProcessChar() {
	 processChar = layer2Main.p3ProcessString.toCharArray();
}
	private static void getCharSet() {
	  charSet = layer2Main.charSet;
}


    private static void encryptKey() {
     
	   for(char layer2p3: processChar) {
	
	     arrNum = 0;
        
         while (arrNum < 94  && layer2p3 != charSet[arrNum]) {
          if (arrNum != 94) {
           arrNum++;
          };
         }
         if (arrNum <= 94) {
          if (layer2p3 == charSet[arrNum]){
	          layer2p3out += charSet[StaticKeyL2P3[arrNum]];
          }else {
           layer2p3out += layer2p3;
          }
         }   
      }
     
     }
     

	private static void decryptKey() {
  
	  for(char layer2p3: processChar) {
	 
	    arrNum = 0;
        
        while (arrNum < 94 && layer2p3 != charSet[StaticKeyL2P3[arrNum]]) {
         if (arrNum != 94) {
            arrNum++;
         
         }
        }
        if (arrNum <= 94) {
         if (layer2p3 == charSet[StaticKeyL2P3[arrNum]]){
	         layer2p3out += charSet[arrNum];
         }else {
           layer2p3out += layer2p3;
          }
        }
	   
     }
     
    }


}
