package com.mobiquityinc.packer;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

public class PackerTest {
	
	
	private String getResourceFolder() {
		return new File("src/test/resources/").getAbsolutePath();
	}

	@Test
	public void testPacker() {
		String result;
		try {
			
			String path = getResourceFolder() + "/items1.txt";
			result = Packer.pack(path);
			System.out.println(result);
		} catch (FileNotFoundException | APIException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testPackerWithItemBadFomarttely() throws FileNotFoundException, APIException {
		Packer.pack(getResourceFolder() + "/itemsWithBadFormatedNumbers.txt");
		
		
	}
}
