package com.amaro.services;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;

public class ProductService {

	/**
	 * Get the Product on the file '{$user.home}/db/Amaro/products.json', based on the productID.
	 * 
	 * @param productId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Product getProduct(String productId) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("getProduct in.");
		ObjectMapper mapper = new ObjectMapper();
		Map<Long, Product> treeMap = new TreeMap<Long, Product>();
		ProductUtil productUtil = new ProductUtil();

		String productFile = productUtil.getJsonDirectory();
		System.out.println("The Products.json is: " + productFile);
		ProductList productList = mapper.readValue(new File(productFile), ProductList.class);

		if (null != productList) {
			try {
				for (Product product : productList.getProducts()) {
					treeMap.put((long) product.getId(), product);
				}
				Product prodSelected = treeMap.get(new Long(productId));

				System.out.println("getProduct out, WITH product.");
				return prodSelected;
			} catch (NumberFormatException e) {
				System.out.println("Error: [NumberFormatException] " + e.getMessage());
				return null;
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return null;
			}
		} else {
			System.out.println("getProduct out, WITHOUT product.");
			return null;
		}
	}

	/**
	 * Return all products saved on the file '{$user.home}/db/Amaro/products.json'.
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ProductList getAllProduct() throws JsonParseException, JsonMappingException, IOException {
		System.out.println("getAllProduct in.");
		ObjectMapper mapper = new ObjectMapper();
		ProductUtil productUtil = new ProductUtil();

		ProductList productList = mapper.readValue(new File(productUtil.getJsonDirectory()), ProductList.class);

		System.out.println("getAllProduct out.");
		return productList;
	}
}
