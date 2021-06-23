package com.polozov.springeshop.dto;

import com.polozov.springeshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDto {
	private String title;
	private Long productId;
	private Double price;
	private Double amount;
	private Double sum;

	public BucketDetailDto(Product product){
		this.title = product.getTitle();
		this.productId = product.getId();
		this.price = product.getPrice();
		this.amount = 1.0;
		this.sum = product.getPrice();
	}
}
