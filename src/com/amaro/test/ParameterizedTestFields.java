package com.amaro.test;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.amaro.dto.Product;
import com.amaro.dto.ProductList;
import com.amaro.services.ProductService;
import com.amaro.services.ProductUtil;
import com.amaro.services.SimilarityService;

@RunWith(Parameterized.class)
public class ParameterizedTestFields {

	/**
	 * Example values for test.
	 * "id": 8371, "tagsVector": [1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0]
	 * "id": 8367, "tagsVector": [0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0]
	 * "id": 8293, "tagsVector": [0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,1,0]
	 * "id": 8291, "tagsVector": [0,0,0,1,0,0,1,0,1,0,0,1,0,0,1,0,0,1,1,0]
	 * "id": 75162,"tagsVector": [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
	 * 
	 */

	// fields used together with @Parameter must be public
	@Parameter(0)
	public int m1;
	@Parameter(1)
	public int m2;
	@Parameter(2)
	public int result;


	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 1 , 2, 2 }, { 5, 3, 15 }, { 121, 4, 484 } };
		return Arrays.asList(data);
	}


	@Test
	public void testUpdateProducts() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProductUtil productUtil = new ProductUtil();

			ProductList productList = mapper.readValue(populateDoc(), ProductList.class);

			assertTrue(true);

			for (Product product : productList.getProducts()) {
				product.initTagsVector();
				productUtil.populateTagVector(product);
			}

			productUtil.writeJsonFile(productList);

			String result = mapper.writeValueAsString(productList.getProducts());
			assertTrue("result is Null.", null != result);

			System.out.println("testUpdateProducts Done!");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateProductsNull() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProductUtil productUtil = new ProductUtil();

			JsonParser doc = null;
			assertTrue("Doc is Null.", null != doc);
			ProductList productList = mapper.readValue(doc, ProductList.class);

			for (Product product : productList.getProducts()) {
				product.initTagsVector();
				productUtil.populateTagVector(product);
			}

			productUtil.writeJsonFile(productList);

			System.out.println("testUpdateProducts Done!");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateProductsEmpty() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProductUtil productUtil = new ProductUtil();

			String doc = "";
			ProductList productList = mapper.readValue(doc, ProductList.class);
			assertTrue("Doc is Null.", null != doc);

			for (Product product : productList.getProducts()) {
				product.initTagsVector();
				productUtil.populateTagVector(product);
			}

			productUtil.writeJsonFile(productList);

			System.out.println("testUpdateProducts Done!");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSimilars() {
		try {
			String productId = "8301";
			ProductService productService = new ProductService();

			Product product = productService.getProduct(productId);
			assertTrue("Product is Null.", null != product);

			Product[] similarList = SimilarityService.getSimilarProducts(product);
			assertTrue("List of similars is Null.", null != similarList);

			System.out.println("testGetSimilars Done!");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}


	@Test
	public void testGetSimilarsNull() {
		try {
			String productId = null;
			ProductService productService = new ProductService();

			Product product = productService.getProduct(productId);
			assertTrue("Product is Null.", null != product);

			Product[] similarList = SimilarityService.getSimilarProducts(product);
			assertTrue("List of similars is Null.", null != similarList);

			System.out.println("testGetSimilars Done!");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}


//ProductService
    @Test
    public void TestGetProduct(String productId){}

    @Test
    public void TestGetAllProduct() {
    	
    }

//ProductUtil
    @Test
    public void TestPopulateTagVector(Product product) {
    	
    }

    @Test
    public void TestWriteJsonFile (ProductList productList) {
    	
    }

    @Test
    public void TestGetJsonDirectory() {
    	
    }

    @Test
    public void TestGetAmaroDirectory() {
    	
    }

//SimilarityService
    @Test
    public void testCalculateSimilarity(int[] v1, int [] v2) {
    	
    }

    @Test
    public void testCalculateDistance() {
    	
    }

    @Test
    public void testGetSimilarProducts(){
    	
    }
	
	private static String populateDoc() {
		return "{\n" + 
				"  \"products\": [\n" + 
				"    {\n" + 
				"      \"id\": 8371,\n" + 
				"      \"name\": \"VESTIDO TRICOT CHEVRON\",\n" + 
				"      \"tags\": [\"balada\", \"neutro\", \"delicado\", \"festa\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8367,\n" + 
				"      \"name\": \"VESTIDO MOLETOM COM CAPUZ MESCLA\",\n" + 
				"      \"tags\": [\"casual\", \"metal\", \"metal\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8363,\n" + 
				"      \"name\": \"VESTIDO CURTO MANGA LONGA LUREX\",\n" + 
				"      \"tags\": [\"colorido\", \"metal\", \"delicado\", \"estampas\", \"passeio\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8360,\n" + 
				"      \"name\": \"VESTIDO FEMININO CANELADO\",\n" + 
				"      \"tags\": [\"workwear\", \"viagem\", \"descolado\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8358,\n" + 
				"      \"name\": \"VESTIDO REGATA FEMININO COM GOLA\",\n" + 
				"      \"tags\": [\"moderno\", \"inverno\", \"liso\", \"basics\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8314,\n" + 
				"      \"name\": \"VESTIDO PLISSADO ACINTURADO\",\n" + 
				"      \"tags\": [\"casual\", \"viagem\", \"delicado\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8311,\n" + 
				"      \"name\": \"VESTIDO SLIPDRESS CETIM\",\n" + 
				"      \"tags\": [\"balada\", \"metal\", \"boho\", \"descolado\", \"passeio\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8310,\n" + 
				"      \"name\": \"VESTIDO CURTO PONTO ROMA MANGA\",\n" + 
				"      \"tags\": [\"casual\", \"metal\", \"delicado\", \"descolado\", \"elastano\", \"estampas\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8309,\n" + 
				"      \"name\": \"VESTIDO MOLETOM COM CAPUZ\",\n" + 
				"      \"tags\": [\"inverno\", \"liso\", \"casual\", \"descolado\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8301,\n" + 
				"      \"name\": \"VESTIDO LONGO CREPE MANGA COMPRIDA\",\n" + 
				"      \"tags\": [\"casual\", \"metal\", \"delicado\", \"descolado\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8300,\n" + 
				"      \"name\": \"VESTIDO MALHA COM FENDA\",\n" + 
				"      \"tags\": [\"balada\", \"metal\", \"estampas\", \"moderno\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8293,\n" + 
				"      \"name\": \"VESTIDO CURTO VELUDO RECORTE GOLA\",\n" + 
				"      \"tags\": [\"colorido\", \"viagem\", \"delicado\", \"descolado\", \"inverno\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8291,\n" + 
				"      \"name\": \"VESTIDO MANGA COMPRIDA COSTAS\",\n" + 
				"      \"tags\": [\"inverno\", \"estampas\", \"delicado\", \"descolado\", \"casual\", \"passeio\", \"basics\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8264,\n" + 
				"      \"name\": \"VESTIDO CURTO VELUDO CRISTAL\",\n" + 
				"      \"tags\": [\"casual\", \"viagem\", \"boho\", \"neutro\", \"festa\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8119,\n" + 
				"      \"name\": \"VESTIDO BABADOS KNIT\",\n" + 
				"      \"tags\": [\"moderno\", \"metal\", \"descolado\", \"elastano\", \"festa\", \"colorido\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8110,\n" + 
				"      \"name\": \"VESTIDO CUT OUT TRICOT\",\n" + 
				"      \"tags\": [\"casual\", \"colorido\", \"delicado\", \"descolado\", \"viagem\", \"inverno\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8109,\n" + 
				"      \"name\": \"VESTIDO BABADOS HORIZONTAIS\",\n" + 
				"      \"tags\": [\"moderno\", \"boho\", \"festa\", \"descolado\", \"colorido\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8104,\n" + 
				"      \"name\": \"VESTIDO BABADO TURTLENECK\",\n" + 
				"      \"tags\": [\"casual\", \"metal\", \"delicado\", \"neutro\", \"basics\", \"inverno\", \"viagem\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8091,\n" + 
				"      \"name\": \"VESTIDO MIDI VELUDO DECOTADO\",\n" + 
				"      \"tags\": [\"couro\", \"veludo\", \"passeio\", \"viagem\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8083,\n" + 
				"      \"name\": \"VESTIDO LONGO ESTAMPADO\",\n" + 
				"      \"tags\": [\"couro\", \"estampado\", \"passeio\", \"viagem\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 8080,\n" + 
				"      \"name\": \"VESTIDO CURTO RENDA VISCOSE\",\n" + 
				"      \"tags\": [\"neutro\", \"workwear\", \"moderno\", \"descolado\", \"liso\", \"elastano\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 7613,\n" + 
				"      \"name\": \"VESTIDO LONGO BABADO\",\n" + 
				"      \"tags\": [\"casual\", \"liso\", \"passeio\", \"colorido\", \"boho\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 7533,\n" + 
				"      \"name\": \"VESTIDO COTTON DOUBLE\",\n" + 
				"      \"tags\": [\"balada\", \"liso\", \"moderno\", \"descolado\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 7518,\n" + 
				"      \"name\": \"VESTIDO CAMISETA FANCY\",\n" + 
				"      \"tags\": [\"casual\", \"liso\"]\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"id\": 7516,\n" + 
				"      \"name\": \"VESTIDO WRAP FLEUR\",\n" + 
				"      \"tags\": [\"neutro\", \"liso\", \"basics\", \"viagem\"]\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
	}
}