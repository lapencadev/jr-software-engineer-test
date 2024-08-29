package com.adobe.bookstore.orderedItem;

import com.adobe.bookstore.bookStock.dto.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

        @Query("SELECT new com.adobe.bookstore.bookStock.dto.BookDto(" +
                "b.id, b.name, oi.quantity) " +
                "FROM OrderedItem oi " +
                "JOIN oi.book b " +
                "WHERE oi.order.id = :orderId")
        List<BookDto> findBooksByOrderId(@Param("orderId") Long orderId);


}
