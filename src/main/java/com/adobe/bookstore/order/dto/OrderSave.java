package com.adobe.bookstore.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderSave {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime orderDate;
    private Long statusId;
    private List<OrderedItemDto> orderedItems = new ArrayList<>();

    @Getter
    @Setter
    public static class OrderedItemDto {
        private String bookId;
        private Integer quantity;
    }
}
