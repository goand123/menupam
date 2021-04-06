package com.kh.toy.order.model.service;

import java.util.List;
import java.util.Map;

import com.kh.toy.shop.model.vo.Shop;

public interface OrderService {
	
	List<Shop> searchShopbyName(Map<String,String> commandMap);
	List<Shop> searchShopbyCategory(Map<String,String> commnadMap);
}
