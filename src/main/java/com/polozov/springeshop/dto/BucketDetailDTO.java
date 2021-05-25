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
public class BucketDetailDTO {
	private String title;
	private Long productId;
	private BigDecimal price;
	private BigDecimal amount;
	private Double sum;

	public BucketDetailDTO(Product product) {
		this.title = product.getTitle();
		this.productId = product.getId();
		this.price = product.getPrice();
		this.amount = new BigDecimal(1.0);
		this.sum = Double.valueOf(product.getPrice().toString());
	}
}
