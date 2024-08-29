package com.adobe.bookstore.order;

import com.adobe.bookstore.order.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDto> getOrders(){
        return orderRepository.getOrders();
    }
}
