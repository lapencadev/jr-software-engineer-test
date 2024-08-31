package com.adobe.bookstore.orderedItem;

import com.adobe.bookstore.bookStock.BookStock;
import com.adobe.bookstore.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ordered_item")
@Getter
@Setter
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_order"))
    private Order order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_book_id"))
    private BookStock book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonProperty(value = "bookId")
    public String getBookId() {
        return (this.book != null) ? this.book.getId() : null;
    }

    @JsonProperty(value = "orderId")
    public Long getOrderId() {
        return (this.order != null) ? this.order.getId() : null;
    }
}
