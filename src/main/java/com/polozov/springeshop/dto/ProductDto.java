package com.polozov.springeshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {
	private Long id;
	private String title;
	private Double price;
}
