package com.zerobase.cafebom.product.controller;

import static org.springframework.http.HttpStatus.OK;

import com.zerobase.cafebom.product.controller.form.BestProductForm;
import com.zerobase.cafebom.product.dto.ProductDetailDto;
import com.zerobase.cafebom.product.dto.ProductDetailForm;
import com.zerobase.cafebom.product.dto.ProductDto;
import com.zerobase.cafebom.product.dto.ProductListForm;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.dto.BestProductDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "product-controller", description = "상품 관련 API")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // wooyoung-23.08.22
    @ApiOperation(value = "상품 카테고리 별 상품 목록 조회", notes = "상품 카테고리 별로 상품을 조회합니다.")
    @GetMapping("/list/{productCategoryId}")
    public ResponseEntity<List<ProductListForm.Response>> productList(@PathVariable Integer productCategoryId) {

        List<ProductDto> productDtoList = productService.findProductList(productCategoryId);

        List<ProductListForm.Response> productListForm = new ArrayList<>();

        for (ProductDto productDto : productDtoList) {
            productListForm.add(ProductListForm.Response.from(productDto));
        }

        return ResponseEntity.status(OK)
            .body(productListForm);
    }

    // wooyoung-23.08.28
    @ApiOperation(value = "상품 상세보기", notes = "상품의 상세 정보를 확인합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailForm.Response> productDetails(@PathVariable Integer productId) {
        ProductDetailDto productDetails = productService.findProductDetails(productId);

        return ResponseEntity.status(OK)
            .body(ProductDetailForm.Response.from(productDetails));
    }

    // minsu-23.09.05
    @ApiOperation(value = "베스트 상품 조회", notes = "주문 상품 수량을 기준으로 베스트 상품을 조회합니다.")
    @GetMapping("/best-list")
    public ResponseEntity<List<BestProductForm.Response>> BestProductList() {

        List<BestProductDto> bestProduct = productService.findBestProductList();

        List<BestProductForm.Response> bestProductResponse = BestProductForm.Response.from(
            bestProduct);

        return ResponseEntity.status(OK).body(bestProductResponse);
    }
}
