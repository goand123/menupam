package com.kh.toy.review.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.toy.common.code.Code;
import com.kh.toy.member.model.vo.Member;
import com.kh.toy.review.model.service.ReviewService;
import com.kh.toy.review.model.vo.Review;
import com.kh.toy.shop.model.vo.Shop;

@Controller
@RequestMapping("review")
public class reviewController {
	
	private final ReviewService reviewService; 
	
	public reviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@GetMapping("form")
	public String reviewForm(String shopIdx, Model model) {
		Shop shop = reviewService.getShopInform(shopIdx);
		model.addAttribute("shop", shop);
		return "review/reviewForm";		
	}
	
	@GetMapping("view")
	public String reviewView(String shopIdx, Model model) {

		Shop shop = reviewService.getShopInform(shopIdx);
		System.out.println(shop);
		model.addAttribute("shop", shop);
		
		return "review/reviewView";		
	}
	
	@PostMapping("views")
	@ResponseBody
	public Map<Integer, Review> getReviews(String page, String shopIdx) {
		
		int pageNo = Integer.parseInt(page);
		Map<Integer, Review> reviewMap = reviewService.getReview(shopIdx, pageNo);
		
		return reviewMap;
	}
	
	@PostMapping("photo")
	@ResponseBody
	public String getPhoto(String fileIdx) throws IOException {
		String savePath = reviewService.getSavePath(fileIdx);
		return savePath;
	}
	
	@PostMapping("write")
	public String writeReview(@RequestParam MultipartFile file, 
			Review review, 
			@SessionAttribute(name="userInfo", required = false) Member member,
			HttpServletRequest request) {

		//review.setMemberId("lee5031207"); //임시 
		//review.setShopIdx("test"); //임시
		
		//1. 민혁님 부분 사진 링크 어캐하나 -> 해결
		
		//2. 예약 대기 버튼 -> 성공
		
		//3. QR 전송 버튼 -> 
		
		
		review.setMemberId(member.getMemberId());
		String path = request.getSession().getServletContext().getRealPath("/").concat("resources");
		String imgUploadPath = path + File.separator + "reviewPhoto" + File.separator;
		System.out.println(imgUploadPath);
		reviewService.writeReview(file, review, imgUploadPath);
		
		return "review/reviewForm";
	}
	
	//=========================================================옯겨야함 나중에=========================================================
	@GetMapping("QRtest")
	public String QRTest() {
		return "review/QRtest";
	}
	
	@GetMapping("tableQRsend")
	public String sendTableQR(String shopIdx, String tableNo, HttpServletRequest request, Model model) {
		
		String path = request.getSession().getServletContext().getRealPath("/").concat("resources");
		
		if(tableNo == null) {
			//전체 테이블 개수만큼 반복해서 보내준다
			reviewService.sendTableQR(shopIdx, path);
		}else {
			//지정한 테이블의 것만 보내준다
			reviewService.sendTableQR(shopIdx, path, tableNo);
		}
		
		model.addAttribute("shopIdx", shopIdx);
		return "shop/QRManage";
	}
	
	@GetMapping("waitQRsend")
	public String sendWaitQR(String shopIdx, HttpServletRequest request, Model model) {
		
		String path = request.getSession().getServletContext().getRealPath("/").concat("resources");
		reviewService.sendWaitQR(shopIdx, path);
		System.out.println(shopIdx);
		
		model.addAttribute("shopIdx", shopIdx);
		return "shop/QRManage";
	}
	
	
	
	
}
