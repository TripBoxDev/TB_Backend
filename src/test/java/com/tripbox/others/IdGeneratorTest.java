package com.tripbox.others;

import static org.junit.Assert.*;

import org.junit.Test;

public class IdGeneratorTest {

	@Test
	public void test() {
		IdGenerator idGen=new IdGenerator();
		for(int i=0;i<10;i++){
			System.out.println(idGen.generateId());
		}

	}

}
