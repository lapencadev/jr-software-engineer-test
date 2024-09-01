package com.adobe.bookstore;

import com.adobe.bookstore.order.OrderService;
import com.adobe.bookstore.order.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Sql(statements = {
            "INSERT INTO status (id, name) VALUES (99, 'test-status')",
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

        OrderDto order99 = orders.stream()
                .filter(order -> order.getId().equals(99L))
                .findFirst()
                .orElse(null);

        assertThat(order99).isNotNull();
        assertThat(order99.getBooks()).isNotEmpty();
        assertThat(order99.getBooks().get(0).getBook_name()).isEqualTo("Test Book");
    }
}

