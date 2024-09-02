package com.adobe.bookstore;

import com.adobe.bookstore.bookStock.BookStock;
import com.adobe.bookstore.bookStock.BookStockRepository;
import com.adobe.bookstore.order.Order;
import com.adobe.bookstore.order.OrderService;
import com.adobe.bookstore.order.dto.OrderDto;
import com.adobe.bookstore.order.dto.OrderSave;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookStockRepository bookStockRepository;
    @Test
    @Sql(statements = {
            "INSERT INTO status (id, name) VALUES (99, 'status test 99')",
            "INSERT INTO orders (id, status_id, order_date) VALUES (99, 99, '2024-09-01 12:00:00')",
            "INSERT INTO book_stock (id, name, quantity) VALUES ('99TEST', 'Test Book', 10)",
            "INSERT INTO ordered_item (id, order_id, book_id, quantity) VALUES (99, 99, '99TEST', 2)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM ordered_item WHERE id = 99",
            "DELETE FROM orders WHERE id = 99",
            "DELETE FROM book_stock WHERE id = '99TEST'",
            "DELETE FROM status WHERE id = 99"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldReturnAllOrdersWithBooks() {
        List<OrderDto> orders = orderService.getAllOrdersWithBooks();

        OrderDto order102 = orders.stream()
                .filter(order -> order.getId().equals(99L))
                .findFirst()
                .orElse(null);

        assertThat(order102).isNotNull();
        assertThat(order102.getBooks()).isNotEmpty();
        assertThat(order102.getBooks().get(0).getBook_name()).isEqualTo("Test Book");
        assertThat(order102.getStatus_id()).isEqualTo(99L);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO status (id, name) VALUES (100, 'test status')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM ordered_item WHERE order_id IN (SELECT id FROM orders WHERE status_id = 100)",
            "DELETE FROM orders WHERE status_id = 100",
            "DELETE FROM status WHERE id = 100"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateOrderAndGenerateOrderCode() {
        OrderSave orderSave = new OrderSave();
        orderSave.setOrderDate(LocalDateTime.now());
        orderSave.setStatusId(100L);

        Order createdOrder = orderService.createOrder(orderSave);

        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getId()).isNotNull();
        assertThat(createdOrder.getOrderCode()).isNotBlank();
    }

    @Test
    @Sql(statements = {
            "INSERT INTO book_stock (id, name, quantity) VALUES ('101Test', 'Test Book', 20)",
            "INSERT INTO status (id, name) VALUES (101, 'test status 101')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM ordered_item WHERE order_id IN (SELECT id FROM orders WHERE status_id = 101)",
            "DELETE FROM orders WHERE status_id = 101",
            "DELETE FROM book_stock WHERE id = '101Test'",
            "DELETE FROM status WHERE id = 101"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateBookStock() {
        OrderSave orderSave = new OrderSave();
        orderSave.setOrderDate(LocalDateTime.now());
        orderSave.setStatusId(101L);

        OrderSave.OrderedItemDto itemDto = new OrderSave.OrderedItemDto();
        itemDto.setBookId("101Test");
        itemDto.setQuantity(5);

        List<OrderSave.OrderedItemDto> items = new ArrayList<>();
        items.add(itemDto);
        orderSave.setOrderedItems(items);

        orderService.createOrder(orderSave);

        BookStock updatedBookStock = bookStockRepository.findById("101Test").orElseThrow();
        assertThat(updatedBookStock.getQuantity()).isEqualTo(15);
    }

}

