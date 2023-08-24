package com.zerobase.cafebom.pay.controller;

import com.zerobase.cafebom.pay.controller.form.PayOrdersForm;
import com.zerobase.cafebom.pay.service.PayService;
import com.zerobase.cafebom.pay.service.dto.PayOrdersDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "pay-controller", description = "결제 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class PayController {

    private final PayService payService;

    // 카카오 QR 테스트 결제-yesun-23.08.24
    @PostMapping("/pay/kakao")
    public @ApiIgnore String payKakaoQR(Model model) {
        payService.payKakaoQR(model);
        return "pay";
    }

    // yesun-23.08.24
    @ApiOperation(value = "결제 시 주문 생성",
        notes = "주문한 상품, 상품의 옵션, 결제 수단 등을 받아 주문 테이블에 저장합니다.")
    @PostMapping("/pay")
    public void payOrders(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody PayOrdersForm payOrdersForm) {
        payService.payOrders(token,PayOrdersDto.from(payOrdersForm));
    }
}