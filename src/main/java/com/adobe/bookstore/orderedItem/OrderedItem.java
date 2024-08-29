package com.adobe.bookstore.orderedItem;

import com.adobe.bookstore.BookStock;
import com.adobe.bookstore.order.Order;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ordered_item")
@JsonSerialize
@Getter
@Setter
public class OrderedItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookStock book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
