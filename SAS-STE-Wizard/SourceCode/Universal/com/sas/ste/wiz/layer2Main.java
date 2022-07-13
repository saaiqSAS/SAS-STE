package com.sas.ste.wiz;

import com.sas.ste.wiz.process;
import com.sas.ste.wiz.layer2p1;
import com.sas.ste.wiz.layer2p2;
import com.sas.ste.wiz.layer2p3;


class layer2Main {

private static char[] processChar;
private static String processString;
protected static char[] charSet;
protected static int L2pAsign;

protected static int[] StaticKeyL2P1 = new int[95];
protected static int[] StaticKeyL2P2 = new int[95];
protected static int[] StaticKeyL2P3 = new int[95];

protected static String p1ProcessString = "";
private static char[] p1Out;
protected static String p2ProcessString = "";
private static char[] p2Out;
protected static String p3ProcessString = "";
private static char[] p3Out;

protected static String layer2out = "";


	protected static void encryptL2() {
	  getEncryptProcessChar();
	  getEncryptProcessString();
	  getStaticKeys();
	  getCharSet();
	  distributeChars(); 
	  encryptProcess();
}

	protected static void decryptL2() {
	  getDecryptProcessChar();
	  getDecryptProcessString();
	  getStaticKeys();
	  getCharSet();
	  distributeChars();
	  decryptProcess();
}

	private static void getStaticKeys() {
		StaticKeyL2P1 = process.StaticKeyL2p1;
		StaticKeyL2P2 = process.StaticKeyL2p2;
		StaticKeyL2P3 = process.StaticKeyL2p3;
	}

	private static void getCharSet() {
	  charSet = process.charSet;
}

	private static void getEncryptProcessChar() {
	  processChar = process.layer1out.toCharArray();
}

	private static void getDecryptProcessChar() {
	  processChar = process.processChar;
}

	private static void getEncryptProcessString() {
	  processString = process.layer1out;
}

	private static void getDecryptProcessString() {
	  processString = process.processString;
}


	private static void distributeChars() {
	  int ones = 0;
	  int twos = 1;
	  int threes = 2;
	  int length = processString.length();
	  p1ProcessString = "";
	  p2ProcessString = "";
	  p3ProcessString = "";
	  
	  String p1ProcessStringTmp = "";
	  String p2ProcessStringTmp = "";
	  String p3ProcessStringTmp = "";
	  
	  try{
	  while (ones <= length) {
	   p1ProcessStringTmp += processChar[ones];
	   ones += 3;
	  }
	  }catch (Exception e){}
	  
	  try{
	  while (twos <= length) {
	   p2ProcessStringTmp += processChar[twos];
	   twos += 3; 
	  }
	  }catch (Exception e){}
	  
	  try{
	  while (threes <= length) {
	   p3ProcessStringTmp += processChar[threes];
	   threes += 3; 
	  }
	  }catch (Exception e){}
	  
	  switch (L2pAsign) {
	    case 0:
	      p1ProcessString = p1ProcessStringTmp;
	      p2ProcessString = p2ProcessStringTmp;
	      p3ProcessString = p3ProcessStringTmp;
	      break;
	    case 1:
	      p1ProcessString = p3ProcessStringTmp;
	      p2ProcessString = p2ProcessStringTmp;
	      p3ProcessString = p1ProcessStringTmp;
	      break;
	    case 2:
	      p1ProcessString = p1ProcessStringTmp;
	      p2ProcessString = p3ProcessStringTmp;
	      p3ProcessString = p2ProcessStringTmp;
	      break;
	    case 3:
	      p1ProcessString = p2ProcessStringTmp;
	      p2ProcessString = p1ProcessStringTmp;
	      p3ProcessString = p3ProcessStringTmp;
	      break;
	    case 4:
	      p1ProcessString = p3ProcessStringTmp;
	      p2ProcessString = p1ProcessStringTmp;
	      p3ProcessString = p2ProcessStringTmp;
	      break;
	    case 5:
	      p1ProcessString = p2ProcessStringTmp;
	      p2ProcessString = p3ProcessStringTmp;
	      p3ProcessString = p1ProcessStringTmp;
	      break;
	    default:
	      System.out.println("Error at L2PAD");
      }
	 
}

	private static void compile() {
	
	 layer2out = "";
	 int num = 0;
	 int lengthIn = processString.length();
	 int lengthOut = layer2out.length();
	 
	 switch (L2pAsign) {
	   case 0:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p1Out[num];
	       layer2out += p2Out[num];
	       layer2out += p3Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  case 1:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p3Out[num];
	       layer2out += p2Out[num];
	       layer2out += p1Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  case 2:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p1Out[num];
	       layer2out += p3Out[num];
	       layer2out += p2Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  case 3:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p2Out[num];
	       layer2out += p1Out[num];
	       layer2out += p3Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  case 4:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p2Out[num];
	       layer2out += p3Out[num];
	       layer2out += p1Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  case 5:
	     try{
	     while (lengthIn >= lengthOut) {
	     
	       layer2out += p3Out[num];
	       layer2out += p1Out[num];
	       layer2out += p2Out[num];
	       num++;
	    }
	    }catch (Exception e){}
	    break;
	  default:
	      System.out.println("Error at L2PAC");
	}
}


	private static void encryptProcess() { 
	
	  layer2p1.encryptL2P1();
	  p1Out = layer2p1.layer2p1out.toCharArray() ;
	
	  layer2p2.encryptL2P2();
	  p2Out = layer2p2.layer2p2out.toCharArray() ;
	
	  layer2p3.encryptL2P3();
	  p3Out = layer2p3.layer2p3out.toCharArray();
	
	compile();
	
}

	private static void decryptProcess() {
	  
	  layer2p1.decryptL2P1();
	  p1Out = layer2p1.layer2p1out.toCharArray() ;
	  
	  layer2p2.decryptL2P2();
	  p2Out = layer2p2.layer2p2out.toCharArray() ;
	  
	  layer2p3.decryptL2P3();
	  p3Out = layer2p3.layer2p3out.toCharArray() ;
	  
	 compile();
	 
}

}
