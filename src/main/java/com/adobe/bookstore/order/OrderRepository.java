package com.adobe.bookstore.order;

import com.adobe.bookstore.order.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT new com.adobe.bookstore.order.dto.OrderDto(" +
            "o.id, " +
            "o.orderCode, " +
            "o.orderDate, " +
            "o.status.id) " +
            "FROM Order o " +
            "order by id asc")
    List<OrderDto> getOrders();

    @Query("SELECT MAX(o.orderCode) FROM Order o")
    String findMaxOrderCode();
}
