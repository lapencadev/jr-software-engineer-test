package com.adobe.bookstore.order.dto;

import com.adobe.bookstore.bookStock.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String order_code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime order_date;
    private Long status_id;
    private List<BookDto> books;

    public OrderDto(Long id, String order_code, LocalDateTime order_date, Long status_id) {
        this.id = id;
        this.order_code = order_code;
        this.order_date = order_date;
        this.status_id = status_id;
        this.books = new ArrayList<>();
    }

}
