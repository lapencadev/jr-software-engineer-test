package com.adobe.bookstore.bookStock;

import com.adobe.bookstore.orderedItem.OrderedItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "book_stock")
@Getter
@Setter
public class BookStock {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita ciclos infinitos durante la serialización JSON
    private List<OrderedItem> orderedItems;
}
