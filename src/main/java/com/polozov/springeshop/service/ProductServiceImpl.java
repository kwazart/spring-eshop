package com.polozov.springeshop.service;

import com.polozov.springeshop.dao.ProductRepository;
import com.polozov.springeshop.domain.Bucket;
import com.polozov.springeshop.domain.Product;
import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.ProductDto;
import com.polozov.springeshop.mapper.ProductMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductMapper mapper = ProductMapper.MAPPER;

	private final ProductRepository productRepository;
	private final UserService userService;
	private final BucketService bucketService;
	private final SimpMessagingTemplate template;

	public ProductServiceImpl(ProductRepository productRepository,
							  UserService userService,
							  BucketService bucketService,
							  SimpMessagingTemplate template) {
		this.productRepository = productRepository;
		this.userService = userService;
		this.bucketService = bucketService;
		this.template = template;
	}

	@Override
	public List<ProductDto> getAll() {
		return mapper.fromProductList(productRepository.findAll());
	}

	@Override
	@javax.transaction.Transactional
	public void addToUserBucket(Long productId, String username) {
		User user = userService.findByName(username);
		if(user == null){
			throw new RuntimeException("User not found. " + username);
		}

		Bucket bucket = user.getBucket();
		if(bucket == null){
			Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
			user.setBucket(newBucket);
			userService.save(user);
		}
		else {
			bucketService.addProducts(bucket, Collections.singletonList(productId));
		}
	}

	@Override
	@Transactional
	public void addProduct(ProductDto dto) {
		Product product = mapper.toProduct(dto);
		Product savedProduct = productRepository.save(product);

		template.convertAndSend("/topic/products",
				ProductMapper.MAPPER.fromProduct(savedProduct));
	}

	@Override
	public ProductDto getById(Long id) {
		Product product = productRepository.findById(id).orElse(new Product());
		return ProductMapper.MAPPER.fromProduct(product);
	}
}
