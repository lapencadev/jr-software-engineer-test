package com.adobe.bookstore.order.dto;

import com.adobe.bookstore.bookStock.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderDto {
    private Long order_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime order_date;
    private String order_status;
    private List<BookDto> ordered_books;

    public OrderDto(Long order_id, LocalDateTime order_date, String order_status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_status = order_status;
        this.ordered_books = new ArrayList<>();
    }


}
