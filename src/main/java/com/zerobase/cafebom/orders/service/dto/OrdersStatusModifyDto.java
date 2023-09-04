package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersStatusModifyDto {

    private OrdersCookingStatus newStatus;

    public static OrdersStatusModifyDto from(OrdersStatusModifyForm ordersStatusModifyForm) {
        return OrdersStatusModifyDto.builder()
            .newStatus(ordersStatusModifyForm.getNewStatus())
            .build();
    }
}
