package com.polozov.springeshop.service;

import com.polozov.springeshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
	List<ProductDTO> getAll();
	void addToUserBucket(Long productId, String username);
}
