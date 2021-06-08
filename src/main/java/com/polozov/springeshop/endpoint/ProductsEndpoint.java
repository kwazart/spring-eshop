package com.polozov.springeshop.endpoint;

import com.polozov.springeshop.dto.ProductDTO;
import com.polozov.springeshop.service.ProductService;
import com.polozov.springeshop.ws.products.GetProductsRequest;
import com.polozov.springeshop.ws.products.GetProductsResponse;
import com.polozov.springeshop.ws.products.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductsEndpoint {

	public static final String NAMESPACE_URL = "http://polozov.com/springeshop/ws/products";

	private ProductService productService;

	@Autowired
	public ProductsEndpoint(ProductService productService) {
		this.productService = productService;
	}

	@PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
	@ResponsePayload
	public GetProductsResponse getProductWS(@RequestPayload GetProductsRequest request) {
		GetProductsResponse response = new GetProductsResponse();
		productService.getAll()
				.forEach(dto -> response.getProducts().add(createProductWs(dto)));
		return response;
	}

	private ProductsWS createProductWs(ProductDTO dto) {
		ProductsWS ws = new ProductsWS();
		ws.setId(dto.getId());
		ws.setPrice(Double.parseDouble(dto.getPrice().toString()));
		ws.setTitle(dto.getTitle());
		return ws;
	}
}