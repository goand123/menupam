package com.kh.toy.shop.model.service;

import java.util.List;

import com.kh.toy.shop.model.vo.Shop;

public interface ShopSerivce {

	int insertShop(Shop shop);
	int updateShop(Shop shop);
	List<Shop> selectMemberShopList(String userId);
}
