package com.yue.service.impl;

import com.yue.bean.Product;
import com.yue.service.ProductService;

public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProdById(int id) {
        return new Product(id, "可口可乐");
    }
}
