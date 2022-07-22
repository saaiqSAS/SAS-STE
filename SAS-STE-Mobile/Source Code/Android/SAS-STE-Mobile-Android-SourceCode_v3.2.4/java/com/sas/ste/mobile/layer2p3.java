package com.sas.ste.mobile;

import com.sas.ste.mobile.layer2Main;


class layer2p3 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p3out = "";
private static int arrNum = 0;
private static int[] StaticKeyL2P3 = new int[95];
	protected static void encryptL2P3() {
	  layer2p3out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P3() {
	  layer2p3out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  decryptKey();
}

    private static void getStaticKey() { StaticKeyL2P3 = layer2Main.StaticKeyL2P3;}

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
