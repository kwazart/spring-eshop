package com.polozov.springeshop.service;

import com.polozov.springeshop.dao.BucketRepository;
import com.polozov.springeshop.dao.ProductRepository;
import com.polozov.springeshop.domain.Bucket;
import com.polozov.springeshop.domain.Product;
import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.BucketDTO;
import com.polozov.springeshop.dto.BucketDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
	private final BucketRepository bucketRepository;
	private final ProductRepository productRepository;
	private final UserService userService;

	public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
		this.bucketRepository = bucketRepository;
		this.productRepository = productRepository;
		this.userService = userService;
	}

	@Override
	@Transactional
	public Bucket createBucket(User user, List<Long> productIds) {
		Bucket bucket = new Bucket();
		bucket.setUser(user);
		List<Product> productList = getCollectRefProductsByIds(productIds);
		bucket.setProducts(productList);
		return bucketRepository.save(bucket);
	}

	private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
		return productIds.stream()
				// getOne вытаскивает ссылку на объект, findById - вытаскивает сам объект
				.map(productRepository::getOne)
				.collect(Collectors.toList());
	}

	@Override
	public void addProducts(Bucket bucket, List<Long> productIds) {
		List<Product> products = bucket.getProducts();
		List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
		newProductList.addAll(getCollectRefProductsByIds(productIds));
		bucket.setProducts(newProductList);
		bucketRepository.save(bucket);
	}

	@Override
	public BucketDTO getBucketByUser(String name) {
		User user = userService.findByName(name);
		if (user == null || user.getBucket() == null) {
			return new BucketDTO();
		}

		BucketDTO bucketDTO = new BucketDTO();
		Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

		List<Product> products = user.getBucket().getProducts();
		for (Product product : products) {
			BucketDetailDTO detail = mapByProductId.get(product.getId());
			if (detail == null) {
				mapByProductId.put(product.getId(), new BucketDetailDTO(product));
			} else {
				detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
				detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
			}
		}

		bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
		bucketDTO.aggregate();

		return bucketDTO;
	}
}