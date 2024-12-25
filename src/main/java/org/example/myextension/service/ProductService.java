/*
package org.example.extension.service;

import org.example.extension.Client.*;
import org.example.extension.dto.ProductRequestDto;
import org.example.extension.dto.ProductResponseDto;
import org.example.extension.entity.ProductEntity;
import org.example.extension.exceptions.ProductNotFoundException;
import org.example.extension.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;






    public ProductResponseDto getProductsById(Long productId) {
      ProductEntity productEntity=productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Product not found with id "+productId));
      ProductResponseDto productResponseDto=modelMapper.map(productEntity, ProductResponseDto.class);
      return productResponseDto;
    }

    }






*/
