package com.mobiquityinc.packer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Bag;
import com.mobiquityinc.model.Item;

public class Packer {

	private Packer() {

	}

	public static String pack(String filePath) throws APIException, FileNotFoundException {
		
		File file = new File(filePath);

		if(file.exists()) {
			FileReader fr = new FileReader(file);
	
			BufferedReader bf = new BufferedReader(fr);
	
			List<String[]> lines = bf.lines().map(line -> line.split(":")).collect(Collectors.toList());
			
			StringBuffer result = new StringBuffer();
	
			for (String[] packageInfos : lines) {
				
				Integer limit = Integer.parseInt(packageInfos[0].trim());
	
				if (limit < 100) {
	
					Bag bestBag = processItems(packageInfos, limit);
					
					formatBagOutput(result, limit, bestBag);
					
				} else {
					throw new APIException("Package limit should be less than 100.");
				}
	
			}
			return result.toString();
		}
		throw new APIException("File doesn't exists. Path: " + filePath);
	}

	private static void formatBagOutput(StringBuffer result, Integer limit, Bag bestBag) {
		result.append(limit + "\n-\n");
		
		if(bestBag.getItems().size() > 0) {
			bestBag.getItems().forEach(item -> {
				result.append(item.getWeight() + "\n");
			});
		} else {
			result.append("No items fits in this package.\n");
		}
	}

	private static Bag processItems(String[] packageInfos, Integer limit) throws APIException {
		String itemsInfos = packageInfos[1];

		String[] items = itemsInfos.trim().split(" ");

		Item[] itemsFromFile = extractItems(items);

		Bag bestBag = solve(itemsFromFile, limit);
		
		return bestBag;
	}

	public static Bag solve(Item[] items, int limit) {
		int NUMBERS_OF_ITEMS = items.length;
		limit *= 100;

		int[][] matrix = new int[NUMBERS_OF_ITEMS + 1][limit + 1];

		for (int i = 0; i <= limit; i++) {
			matrix[0][i] = 0;
		}

		for (int i = 1; i <= NUMBERS_OF_ITEMS; i++) {
			for (int j = 0; j <= limit; j++) {
				int weightInt = (int) items[i - 1].getWeight() * 100;
				int priceInt = (int) items[i - 1].getPrice() * 100;

				if (weightInt > j) {
					matrix[i][j] = matrix[i - 1][j];
				} else {
					matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - weightInt] + priceInt);
				}
			}
		}

		int res = matrix[NUMBERS_OF_ITEMS][limit];
		int w = limit;
		List<Item> itemsSolution = new ArrayList<>();

		for (int i = NUMBERS_OF_ITEMS; i > 0 && res > 0; i--) {
			if (res != matrix[i - 1][w]) {
				itemsSolution.add(items[i - 1]);
				res -= (int) items[i - 1].getPrice() * 100;
				w -= (int) items[i - 1].getWeight() * 100;
			}
		}

		return new Bag(itemsSolution, (int) limit/100);
	}

	private static Item[] extractItems(String[] items) throws APIException {
		Item[] itemsFromFile = new Item[items.length];

		for (int i = 0; i < items.length; i++) {
			String[] infos = items[i].replace("€", "").replace("(", "").replace(")", "").split(",");

			try {
				int index = Integer.parseInt(infos[0]);
				double weight = Double.parseDouble(infos[1]);
				double price = Double.parseDouble(infos[2]);
			
				Item item = new Item(index, weight, price);
				if(item.isValid()) {
					itemsFromFile[i] = item;
				}
			} catch(NumberFormatException e) {
				throw new APIException("Format of number not accepted.");
			}
			
			
		}
		return itemsFromFile;
	}
}
