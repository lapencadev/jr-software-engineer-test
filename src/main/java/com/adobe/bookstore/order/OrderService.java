package com.adobe.bookstore.order;

import com.adobe.bookstore.bookStock.dto.BookDto;
import com.adobe.bookstore.order.dto.OrderDto;
import com.adobe.bookstore.orderedItem.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;

        @Autowired
        public OrderService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository) {
            this.orderRepository = orderRepository;
            this.orderedItemRepository = orderedItemRepository;
        }

        public List<OrderDto> getAllOrdersWithBooks() {
            List<OrderDto> orders = orderRepository.getOrders();

            for (OrderDto order : orders) {
                List<BookDto> books = orderedItemRepository.findBooksByOrderId(order.getOrder_id());
                order.setOrdered_books(books);
            }

            return orders;
        }
    }
