package com.polozov.springeshop.service;

import com.polozov.springeshop.domain.Bucket;
import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.BucketDto;

import java.util.List;

public interface BucketService {
	Bucket createBucket(User user, List<Long> productIds);

	void addProducts(Bucket bucket, List<Long> productIds);

	BucketDto getBucketByUser(String name);

	void commitBucketToOrder(String username);
}
