package com.polozov.springeshop.endpoint;

import com.polozov.springeshop.dto.ProductDto;
import com.polozov.springeshop.service.ProductService;
import com.polozov.springeshop.ws.products.GetProductsRequest;
import com.polozov.springeshop.ws.products.GetProductsResponse;
import com.polozov.springeshop.ws.products.ProductsWS;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class ProductsEndpoint {

	public static final String NAMESPACE_URL = "http://polozov.com/springeshop/ws/products";

	private final ProductService productService;

	public ProductsEndpoint(ProductService productService) {
		this.productService = productService;
	}

	@PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
	@ResponsePayload
	public GetProductsResponse getGreeting(@RequestPayload GetProductsRequest request)
			throws DatatypeConfigurationException {
		GetProductsResponse response = new GetProductsResponse();
		productService.getAll()
				.forEach(dto -> response.getProducts().add(createProductWS(dto)));
		return response;
	}

	private ProductsWS createProductWS(ProductDto dto){
		ProductsWS ws = new ProductsWS();
		ws.setId(dto.getId());
		ws.setPrice(dto.getPrice());
		ws.setTitle(dto.getTitle());
		return ws;
	}
}