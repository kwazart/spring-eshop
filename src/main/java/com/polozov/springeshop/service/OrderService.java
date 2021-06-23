package com.polozov.springeshop.service;

import com.polozov.springeshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);

    Order getOrder(Long id);
}
