package com.sas.ste.mobile;

import com.sas.ste.mobile.layer2Main;



class layer2p2 {

private static char[] processChar;
private static char[] charSet;
protected static String layer2p2out = "";
private static int arrNum = 0;
private static int[] StaticKeyL2P2 = new int[95];
	protected static void encryptL2P2() {
	  layer2p2out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL2P2() {
	  layer2p2out = "";
	  getProcessChar();
      getStaticKey();
	  getCharSet();
	  decryptKey();
}

    private static void getStaticKey() { StaticKeyL2P2 = layer2Main.StaticKeyL2P2;}
	  
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
