package org.productmanagement.com;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import com.productmanagement.entity.Product;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;


@QuarkusTest
class ProductResourceTest {
    @Test
    void getAllProductsEndpoint() {
        given()
          .when().get("/api/v1/product")
          .then()
             .statusCode(200)
             .body("$.size()", is(2),
                     "name", containsInAnyOrder("Phones", "Laptop"));
    }
    
    @Test
    void getProductEndpoint() {
        given()
        .pathParam("id", 1)
          .when().get("/api/v1/product/{id}")
          .then()
             .statusCode(200)
             .body("name", is("Phones"))
             .body("description", is("Apple Smartphone iphone 15 Plus"))
             .body("price", is(1903803.9F))
             .body("quantity", is(10))
             .body("id", is(1));
    }
    
    @Test
    void getProductStockAvailabiltyEndpoint() {
        given()
        .pathParam("id", 1)
        .pathParam("count", 2)
          .when().get("/api/v1/product/checkProductStock/{id}/{count}")
          .then()
             .statusCode(200)
             .body(containsString("Available Stock : Yes"));
    }
    
    @Test
    void getProductAscendingByPriceEndpoint() {
        given()
          .when().get("/api/v1/product/ascProductsByPrice")
          .then()
             .statusCode(200)
             .body("$.size()", is(2),
                     "name", containsInAnyOrder("Phones", "Laptop"));
    }
    
    @Test
    void updateProductEndpoint() {
    	Product product = new Product();
    	product.setName("Laptop");
    	product.setDescription("Samsung Original Laptop");
    	product.setPrice(100000);
    	product.setQuantity(1);
    	
        given()
        .pathParam("id", 2)
        .body(product)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when().put("/api/v1/product/{id}")
          .then()
             .statusCode(200)
             .body("name", is("Laptop"))
             .body("description", is("Samsung Original Laptop"))
             .body("price", is(100000.0F))
             .body("quantity", is(1))
             .body("id", is(2));
    }
    
    @Test
    void deleteProductEndpoint() {
    	
        given()
        .pathParam("id", 1)
        .when().delete("/api/v1/product/{id}")
          .then()
             .statusCode(204);
    }
    
    @Test
    void createProductEndpoint() {
    	Product product = new Product();
    	product.setName("Tab");
    	product.setDescription("Samsung Tablets");
    	product.setPrice(500);
    	product.setQuantity(1);
    	
        given()
        .body(product)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when().post("/api/v1/product")
          .then()
             .statusCode(201)
             .body("name", is("Tab"))
             .body("description", is("Samsung Tablets"))
             .body("price", is(500.0F))
             .body("quantity", is(1))
             .body("id", is(3));
    }
    
}