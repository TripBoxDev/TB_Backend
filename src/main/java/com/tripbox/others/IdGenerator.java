package com.tripbox.others;


import java.util.Random;

public class IdGenerator {

	private static int ID_LENGHT = 12;
	
	public static String generateId (){
		StringBuffer randomSequence = new StringBuffer();
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i <ID_LENGHT){
			char c = (char)r.nextInt(255);
			if((c>='0' && c<='9')|| (c>='A' && c<='Z')||(c>='a'&&c<='z')){
				randomSequence.append(c);
				i ++;
			}
		}
		
		return randomSequence.toString();
	}
}
