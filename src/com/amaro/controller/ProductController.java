package com.amaro.controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.services.ProductService;
import com.amaro.services.ProductUtil;
import com.amaro.services.SimilarityService;

@Path("/service")
public class ProductController {

	@POST
	@Path("/updateProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProducts(String doc) {
		System.out.println("updateProducts in.");
		ProductUtil productUtil = new ProductUtil();
		try {
			ObjectMapper mapper = new ObjectMapper();

			if(doc.isEmpty()) {
				System.out.println("Body is Null or Empty.");
				return errorMessage("Body is Null or Empty.");
			}

			ProductList productList = mapper.readValue(doc, ProductList.class);

			for (Product product : productList.getProducts()) {
				product.initTagsVector();
				productUtil.populateTagVector(product);
			}

			productUtil.writeJsonFile(productList);

			String result = mapper.writeValueAsString(productList.getProducts());

			System.out.println("updateProducts out.");
			return result;
		} catch (JsonParseException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		} catch (JsonMappingException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		}
	}

	@GET
	@Path("/similarproducts/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSimilars(@PathParam("productId") String productId) {
		System.out.println("getSimilars in.");
		ProductService productService = new ProductService();
		try {
			if (null != productId) {
				System.out.println("productId: " + productId);
				ObjectMapper mapper = new ObjectMapper();

				Product product = productService.getProduct(productId);

				Product[] similarList = SimilarityService.getSimilarProducts(product);
				if (null == similarList) {
					System.out.println("Product doesn't exist.");
					return errorMessage("Product doesn't exist.");
				}

				System.out.println("getSimilars out.");
				String result = mapper.writeValueAsString(similarList);

				return result;
			} else {
				System.out.println("ProductId is Null.");
				return errorMessage("ProductId is Null.");
			}
		} catch (JsonParseException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		} catch (JsonMappingException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return errorMessage("Error: " + e.getMessage());
		}
	}

	private String errorMessage(String message ) {
		return "{\"message\": \"" + message.replace("\"", "") + "\"}";
	}
}
