package com.kh.toy.shop.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.toy.order.model.vo.Order;
import com.kh.toy.shop.model.vo.Menu;
import com.kh.toy.shop.model.vo.MenuCategory;
import com.kh.toy.shop.model.vo.MenuOrdering;
import com.kh.toy.shop.model.vo.Shop;

public interface ShopSerivce {

	int insertShop(Shop shop);
	int updateShop(Shop shop);
	Shop selectShopInfo(String userId);
	void ShopInfoModify(Shop shop, String userId);
	
	Map<String,Object> selectCategoryList(String shopIdx);
	void updateCategoryName(MenuCategory menuCategory);
	int insertCategory(MenuCategory menuCategory, String shopIdx);
	int deleteCategory(MenuCategory menuCategory);
	
	void menuRegister(MultipartFile file, Menu menu, String uploadPath);
	Map<String,Object> selectMenuList(String shopIdx);
	
	Order selectOrder(String userId);
	List<Map<String,Object>> selectMenuOrderList(String orderIdx);
	int deleteSelectionMenuOrder(MenuOrdering menuOrdering);
	List<Order> selectOrderList();
}
