package com.adobe.bookstore.bookStock;

import com.adobe.bookstore.orderedItem.OrderedItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "book_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStock {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    //One book associated to many ordered items
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderedItem> orderedItems;
}
