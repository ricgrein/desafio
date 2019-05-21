package com.amaro.services;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.enumeration.Tag;

public class ProductUtil {

	/**
	 * Populate the tagsVector based on tags.
	 * If the value exists on the Enum Tag the position of the value is used to update the position on the vector with the value '1'.
	 * If the value doesn't exist, a Log is send to inform that a value different from the tags mapped exists.
	 * 
	 * @param product
	 * @return
	 */
	public Product populateTagVector(Product product) {
		for(String tag : product.getTags()) {
			try {
				product.getTagsVector()[Tag.valueOf(tag).ordinal()] = 1;
			} catch (IllegalArgumentException e) {
				// If something happens wrong, log, because it doesn't should happen.
				System.out.println("Error: " + e.getMessage());
			}
		}
		return product;
	}

	/**
	 * Save the product list on a Json File on the directory '{$user.home}/db/Amaro/products.json'.
	 * 
	 * @param productList
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void writeJsonFile (ProductList productList) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("writeJsonFile in.");
		if (null != productList) {
			String directory = getDirectory();
			new File(directory).mkdirs();

			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(new File(getJsonDirectory()), productList);
			System.out.println("writeJsonFile out.");
		} else {
			System.out.println("error: productList is null.");
			System.out.println("writeJsonFile out.");
		}
	}

	public String getDirectory() {
		System.out.println("getDirectory in.");
		String directory = System.getProperty("user.home") +"/db/Amaro/";
		System.out.println("The directory is: " + directory);

		System.out.println("getAmaroDirectory out.");
		return directory;
	}

	public String getJsonDirectory() {
		System.out.println("getJsonDirectory in.");
		String jsonDirectory = getDirectory() + "products.json";
		System.out.println("The directory of the file is: " + jsonDirectory);

		System.out.println("getJsonDirectory out.");
		return jsonDirectory;
	}
}
