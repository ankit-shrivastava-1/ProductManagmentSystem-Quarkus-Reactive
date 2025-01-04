package com.productmanagement.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestResponse;

import com.productmanagement.entity.Product;
import com.productmanagement.exception.ProductNotFoundException;
import com.productmanagement.service.ProductService;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(ProductResource.RESOURCE_PATH)
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	public static final String RESOURCE_PATH = "/api/v1/product";

	private final ProductService productService;

	@Inject
	public ProductResource(ProductService productService) {
		this.productService = productService;
	}

	@GET
	@Operation(summary = "Get all products")
	public Uni<List<Product>> getAllProducts() {
		return Product.listAll();
	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Get product by ID")
	public Uni<Product> getProductById(@PathParam("id") Long id) {
		return Product.findById(id);
	}

	@POST
	@Operation(summary = "Create a new product")
	public Uni<RestResponse<Product>> createProduct(@Valid Product product) {
		if (product == null || product.id != null) {
            throw new WebApplicationException("Id is invalidly set on request.", 422);
        }
		
		return Panache
				.withTransaction(product::persist)
				.replaceWith(RestResponse.status(Response.Status.CREATED, product));
	}

	@PUT
	@Path("/{id}")
	@Operation(summary = "Update an existing product")
	public Uni<Response> updateProduct(@PathParam("id") Long id, @Valid Product product) {
		 if (product == null || product.name == null || product.description == null) {
	            throw new WebApplicationException("Product name or description was not set in request.", 422);
	        }
		
		return Panache
                .withTransaction(() -> Product.<Product> findById(id)
                        .onItem().ifNotNull().invoke(entity -> entity.name = product.name)
                        .onItem().ifNotNull().invoke(entity -> entity.description = product.description)
                        .onItem().ifNotNull().invoke(entity -> entity.price = product.price)
                        .onItem().ifNotNull().invoke(entity -> entity.quantity = product.quantity)
                    )
                    .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                    .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);
	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Delete a product")
	public Uni<Response> deleteProduct(@PathParam("id") Long id) throws ProductNotFoundException{
		return Panache.withTransaction(() -> Product.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(Response.Status.NO_CONTENT).build()
                        : Response.ok().status(Response.Status.NOT_FOUND).build());
	}

	@GET
	@Path("/checkProductStock/{id}/{count}")
	@Operation(summary = "Check Product Quantity")
	public Uni<Boolean> getProductStockAvailabilty(@PathParam("id") Long id, @PathParam("count") int count)
			throws ProductNotFoundException {
		return productService.checkProductStockAvailable(id, count);
	}

	@GET
	@Path("/ascProductsByPrice")
	@Operation(summary = "Gets products by increasing price")
	public Uni<List<PanacheEntityBase>> getProductAscendingByPrice() {
		return Product.findAll(Sort.ascending("price")).list();
	}
}
