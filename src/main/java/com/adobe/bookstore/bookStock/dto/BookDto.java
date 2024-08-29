package com.adobe.bookstore.bookStock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private String book_id;
    private String book_name;
    private Integer ordered_quantity;

    public BookDto(String book_id, String book_name, Integer ordered_quantity) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.ordered_quantity = ordered_quantity;
    }
}
