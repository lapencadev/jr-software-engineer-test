package com.adobe.bookstore.order;

import com.adobe.bookstore.bookStock.BookStock;
import com.adobe.bookstore.bookStock.BookStockRepository;
import com.adobe.bookstore.bookStock.dto.BookDto;
import com.adobe.bookstore.order.dto.OrderDto;
import com.adobe.bookstore.order.dto.OrderSave;
import com.adobe.bookstore.orderedItem.OrderedItem;
import com.adobe.bookstore.orderedItem.OrderedItemRepository;
import com.adobe.bookstore.status.Status;
import com.adobe.bookstore.status.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderedItemRepository orderedItemRepository;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private StatusRepository statusRepository;

        public List<OrderDto> getAllOrdersWithBooks() {
            List<OrderDto> orders = orderRepository.getOrders();

            for (OrderDto order : orders) {
                List<BookDto> books = orderedItemRepository.findBooksByOrderId(order.getId());
                order.setBooks(books);
            }

            return orders;
        }

        @Transactional
        public Order createOrder(OrderSave orderSave) {
            Order order = new Order();
            order.setOrderDate(orderSave.getOrderDate());

            Status status = statusRepository.findById(orderSave.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Status not found with id " + orderSave.getStatusId()));
            order.setStatus(status);

            if (order.getOrderedItems() == null) {
                order.setOrderedItems(new ArrayList<>());
            }

            if (orderSave.getOrderedItems() != null) {
                for (OrderSave.OrderedItemDto itemDTO : orderSave.getOrderedItems()) {
                    if (itemDTO.getBookId() != null) {
                        BookStock bookStock = bookStockRepository.findById(itemDTO.getBookId())
                                .orElseThrow(() -> new RuntimeException("Book not found with id " + itemDTO.getBookId()));

                        if (bookStock.getQuantity() < itemDTO.getQuantity()) {
                            throw new RuntimeException("Not enough stock for book with id " + itemDTO.getBookId());
                        }

                        OrderedItem item = new OrderedItem();
                        item.setBook(bookStock);
                        item.setOrder(order);
                        item.setQuantity(itemDTO.getQuantity());

                        order.getOrderedItems().add(item);

                        bookStock.setQuantity(bookStock.getQuantity() - itemDTO.getQuantity());
                        bookStockRepository.save(bookStock);
                    } else {
                        throw new RuntimeException("Book ID is missing in ordered item");
                    }
                }
            }

            String orderCode = generateOrderCode();
            order.setOrderCode(orderCode);

            return orderRepository.save(order);
        }

    public String generateOrderCode() {
        String orderCode;
        int sequence = 1;
        String maxOrderCode = orderRepository.findMaxOrderCode();


        if (maxOrderCode != null) {
            try {
                String number = maxOrderCode.substring(1);
                sequence = Integer.parseInt(number) + 1;
            }
            catch (NumberFormatException e) {
                System.out.println("Not valid code: " + e.getMessage());

            }
        }
        orderCode = 'P' + String.format("%07d", sequence);

        return orderCode;
    }

}


