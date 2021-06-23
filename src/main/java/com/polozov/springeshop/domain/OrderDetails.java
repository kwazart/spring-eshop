package com.polozov.springeshop.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders_details")
public class OrderDetails {
	private static final String SEQ_NAME = "order_details_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private BigDecimal amount;
	private BigDecimal price;

	public OrderDetails(Order order, Product product, Long amount) {
		this.order = order;
		this.product = product;
		this.amount = new BigDecimal(amount);
		this.price = new BigDecimal(product.getPrice());
	}
}
