package com.sas.ste.wizard;

class layer1 {

private static char[] processChar;
private static char[] charSet;
protected static String layer1out = "";
private static int arrNum = 0;
private static int[] StaticKeyL1 = new int[95];

	protected static void encryptL1() {
	  layer1out  = "";
	  getEncryptProcessChar();
      getStaticKey();
	  getCharSet();
	  encryptKey();
}

	protected static void decryptL1() {
	  layer1out = "";
	  getDecryptProcessChar();
      getStaticKey();
	  getCharSet();
	  decryptKey();
}

    private static void getStaticKey() { StaticKeyL1 = process.StaticKeyL1;}

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
