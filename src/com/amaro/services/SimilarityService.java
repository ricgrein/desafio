/**
 * 
 */
package com.amaro.services;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;

/**
 * @author jgrein
 *
 */
public class SimilarityService {

	/**
	 * S = 1/(1 + D), where 
	 * D = is the distance between the vectors  v1 and v2.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static float calculateSimilarity(int[] v1, int [] v2) {
		try {
			float distance = calculateDistance(v1, v2);
			float similarity = 1/(1 + distance);

			return similarity;
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return new Float(0);
		}
	}

	/**
	 * Calculate the distance between the vectors v1 and v2, 
	 * the distance is calculated with the formula:
	 * Distance = Math.sqrt((v1[0] - v2[0])^2 + (v1[1] - v2[1])^2 + .. + (v1[N-1] - v2[N-1])^2)
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	private static float calculateDistance(int[] v1, int[] v2) throws Exception {
		float subResult = 0;

		for (int i = 0; i < v1.length; i++) {
			subResult += (float) Math.pow(v1[i] - v2[i], 2);
		}

		double distance = Math.sqrt(subResult);

		return (float) distance;
	}

	/**
	 * Return the 3 most similar products.
	 * 
	 * @param product
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Product[] getSimilarProducts(Product product) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("getSimilarProducts in");
		ProductService productService = new ProductService();

		if (null != product) {
			ProductList productList = productService.getAllProduct();

			Product[] products = new Product[productList.getProducts().size() - 1];
			int iterator = 0;
			for (Product prod : productList.getProducts()) {
				if (prod.getId() != product.getId()) {
					float similarity = calculateSimilarity(product.getTagsVector(), prod.getTagsVector());
					prod.setSimilarity(similarity);

					products[iterator] = prod;
					iterator++;
				} else {
					System.out.println("Product ignored:" + product.getId());
				}
			}

			Arrays.sort(products, Product.SimilarityComparator.reversed());

			System.out.println("getSimilarProducts out");
			return Arrays.copyOf(products,3);
		} else {
			System.out.println("getSimilarProducts out, Product is null.");
			return null;
		}
	}
}
