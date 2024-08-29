package com.adobe.bookstore.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderDto {
    private Long order_id;
    private LocalDateTime order_date;
    private String book_name;
    private Integer ordered_quantity;
    private String order_status;

    public OrderDto(Long order_id, LocalDateTime order_date, String book_name, Integer ordered_quantity, String order_status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.book_name = book_name;
        this.ordered_quantity = ordered_quantity;
        this.order_status = order_status;
    }


}
