package com.tth.product.service.impl;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.ProductListResponse;
import com.tth.product.entity.Product;
import com.tth.product.enums.ErrorCode;
import com.tth.product.exception.AppException;
import com.tth.product.mapper.ProductMapper;
import com.tth.product.repository.ProductRepository;
import com.tth.product.service.ProductService;
import com.tth.product.service.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product findById(String id) {
        return this.productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public PageResponse<ProductListResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Product> spec = ProductSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductListResponse> result = this.productRepository.findAll(spec, pageable).map(this.productMapper::toProductListResponse);

        return PageResponse.of(result);
    }

}
