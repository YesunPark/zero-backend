package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.domain.type.Payment;
import com.zerobase.cafebom.orders.controller.form.OrdersAddForm;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrdersAddDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Payment payment;

        private List<ProductOrderedDto> products;

        public static OrdersAddDto.Request from(OrdersAddForm form) {
            return OrdersAddDto.Request.builder()
                .payment(form.getPayment())
                .products(
                    form.getProducts()
                        .stream().map(orderedProductForm ->
                            ProductOrderedDto.builder()
                                .productId(orderedProductForm.getProductId())
                                .optionIds(orderedProductForm.getOptionIds())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOrderedDto {

        private Integer productId;

        private List<Integer> optionIds;
    }
}