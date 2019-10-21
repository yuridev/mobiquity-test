package com.mobiquityinc.packer;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

public class PackerTest {
	
	
	private String getResourceFolder() {
		return new File("src/test/resources/").getAbsolutePath();
	}

	@Test
	public void testPackerWithSuccesExecution() {
		String result;
		try {
			
			String path = getResourceFolder() + "/items.txt";
			result = Packer.pack(path);
			System.out.println(result);
		} catch (FileNotFoundException | APIException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testPackerWithPriceNumberBadFormatation() throws FileNotFoundException, APIException {
		Assertions.assertThrows(APIException.class, () -> {
			Packer.pack(getResourceFolder() + "/itemsWithBadFormatedPriceNumber.txt.txt");
		});
	}
	
	
	@Test
	public void testPackerWithWeightNumberBadFormatation() throws FileNotFoundException, APIException {
		Assertions.assertThrows(APIException.class, () -> {
			Packer.pack(getResourceFolder() + "/itemsWithBadFormatedWeightNumber.txt.txt");
		});
	}
	
	@Test
	public void testPackerWithBagLimitMoreThan100() throws FileNotFoundException, APIException {
		Assertions.assertThrows(APIException.class, () -> {
			Packer.pack(getResourceFolder() + "/itemsWithBagLimitMoreThan100.txt");
		});
	}
}
