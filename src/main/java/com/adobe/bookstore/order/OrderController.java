package com.adobe.bookstore.order;

import com.adobe.bookstore.order.dto.OrderDto;
import com.adobe.bookstore.order.dto.OrderSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrdersWithBooks();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderSave orderSave) {
        Order createdOrder = orderService.createOrder(orderSave);
        return ResponseEntity.ok(createdOrder);
    }

}
