package com.polozov.springeshop.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
	private static final String SEQ_NAME = "order_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
	private Long id;
	@CreationTimestamp
	private LocalDateTime created;
	@UpdateTimestamp
	private LocalDateTime changed;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private BigDecimal sum;
	private String address;
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private List<OrderDetails> details;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
}
