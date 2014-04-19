package com.tripbox.others;


import java.util.Random;


public class IdGenerator {

	private static int ID_LENGHT = 12;
	private static IdGenerator uniqueInstance;
	private static String previousId;
	
	private IdGenerator(){}

	public static IdGenerator getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new IdGenerator();
			previousId=null;

		}
		return uniqueInstance;
	}
	public String generateId (){
		StringBuffer randomSequence = new StringBuffer();
		do{
			randomSequence = new StringBuffer();
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
		}while(randomSequence.toString().equalsIgnoreCase(previousId));
		previousId=randomSequence.toString();
		return randomSequence.toString();
	}
}
