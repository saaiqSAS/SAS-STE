package com.sas.ste.mobile;

import com.sas.ste.mobile.layer2Main;



class layer2p1 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p1out = "";
private static int arrNum = 0;
private static int[] StaticKeyL2P1 = new int[95];

	protected static void encryptL2P1() {
	  layer2p1out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P1() {
	  layer2p1out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  decryptKey();
}

    private static void getStaticKey() { StaticKeyL2P1 = layer2Main.StaticKeyL2P1;}

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
