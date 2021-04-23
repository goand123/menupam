package com.kh.toy.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.member.model.vo.Member;
import com.kh.toy.order.model.service.OrderService;
import com.kh.toy.order.model.vo.Order;
import com.kh.toy.shop.model.vo.Menu;
import com.kh.toy.shop.model.vo.Shop;

@Controller
@RequestMapping("order")
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	//매장 검색기능(비동기)
	@PostMapping("find")
	@ResponseBody
	public List<Shop> findShop(String keyword, @RequestParam(defaultValue = "") String location,
					@RequestParam(defaultValue = "name") String type) {
		List<Shop> result = orderService.searchShopbyName(keyword, location);
		return result;
	}
	
	//매장목록(검색 창)
	@GetMapping("shoplist")
	public String shopList() {
		return "order/shopList";
	}
	
	//매장 화면(메뉴뷰) 요청시 메뉴 정보를 넣어서 출력
	//테이블번호를 입력받을 수 있다.
	@GetMapping("menuview")
	public String menuView(String shopIdx,@RequestParam(required= false) String tableNum, Model model) {	
		Shop shopInfo = orderService.selectShopbyIdx(shopIdx);
		model.addAttribute(shopInfo);
		Map<String,List<Menu>> menulist = orderService.getMenulistByShopIdx(shopIdx);
		model.addAttribute("menulist",menulist);
		model.addAttribute("tableNum",tableNum); //테이블 번호를 넣어주고 세션에 장바구니를 만들기 시작하면 테이블 번호를 세션에 기록한다.
		return "order/menuView";
	}
	
	//장바구니 화면
	@GetMapping("menucart")
	public String menuCart(String shopIdx,
							@RequestParam(required = false) String add,
							@RequestParam(required = false) String tableNum,
							HttpSession session,Model model) {
		String cartIdx = (String)session.getAttribute("cartIdx");
		
		//세션에 카트를 등록한 적이 없다면 접근한 매장의 번호 저장, 테이블 번호를 입력받았다면 테이블 번호도 세션에 기록
		if(cartIdx == null) {
			session.setAttribute("cartIdx", shopIdx);
			if(tableNum != null) { session.setAttribute("tableNum", tableNum); }
		}
		//세션에 다른 매장의 카트 데이터가 남아있다면, 데이터를 삭제한다.
		else if(!cartIdx.equals(shopIdx)) {
			session.removeAttribute("cart");
			session.removeAttribute("tableNum");
			session.setAttribute("cartIdx", shopIdx);
			if(tableNum != null) { session.setAttribute("tableNum", tableNum); }
		}
		
		//카트 속성 생성
		if(session.getAttribute("cart") == null) {
			session.setAttribute("cart", new ArrayList<Map>());
		}
		
		//add 파라미터에 menuIdx가 넘어왔다면 메뉴마다 menuIdx, menuName, menuPrice, count를 맵에 담아서 저장
		if(add != null) {
			Menu addMenu = orderService.getMenuInShopIdx(shopIdx,add); //해당 shopIdx에서 menuIdx를 검색
			if(addMenu != null) { //해당 매장에 add한 메뉴가 있다면 메뉴의 정보를 세션에 추가한다.
				
				List<Map> cart = (List)session.getAttribute("cart");
				
				boolean isIn = false; //만약 세션에 이미 동일한 Idx의 메뉴가 들어있다면 추가하지 않는다.
				for(Map sessionData : cart) { 
					if(sessionData.get("menuIdx").equals(add)) {
						isIn = true;
					}
				}
				
				if(!isIn) { //카트 리스트에 찾은 메뉴 데이터를 등록한다.
					Map<String,String> menuData = new HashMap<>();
					menuData.put("menuIdx", addMenu.getMenuIdx());
					menuData.put("menuName",addMenu.getMenuName());
					menuData.put("menuPrice", addMenu.getMenuPrice());
					menuData.put("count", "1");
					cart.add(menuData);
					session.setAttribute("cart", cart);
				}
				
			}
		}
		model.addAttribute(orderService.selectShopbyIdx(shopIdx)); //매장정보를 Model에 입력하고 화면 출력
		return "order/menuCart";
	}
	//세션 cart 수정(카트 메뉴 제거/수량 변경)
	@GetMapping("modifycart")
	public String modifyCart(@RequestParam(required = false) String menuIdx,
								@RequestParam(required = false) String remove,
								@RequestParam(required = false) String countTo,
								HttpSession session) {
		List<Map<String,String>> cart = (List)session.getAttribute("cart"); //세션에서 카트 정보를 읽음
		if(cart == null) { //세션에 카트정보가 없으면 탈출
			return "order/menuCart";
		}

		if(remove != null && remove.equals("all")) { //remove 파라미터에 all이 입력되었다면, 카트 데이터를 제거하고 탈출
			session.removeAttribute("cart");
			return "order/menuCart";
		}
		
		if(menuIdx == null) { // remove all이 아니고 menuIdx에 값이 없으면 탈출
			return "order/menuCart";
		}

		for(int i = 0; i < cart.size(); i++) { //카트정보에서 입력한 menuIdx와 같은 메뉴정보를 찾음
			if(cart.get(i).get("menuIdx").equals(menuIdx)) { //찾으면 remove나 count에 따라 작업 수행
				if(remove != null) {
					cart.remove(i); //remove 파라미터가 null이 아니라면 menuIdx에 해당하는 메뉴정보를 제거
				}
				if(countTo != null) { //count 파라미터가 null이 아니라면 해당 메뉴에 변경할 수량을 입력
					Map<String,String> menu = cart.get(i);
					int menucount = Integer.parseInt(menu.get("count"));
					if(countTo.equals("plus")) {
						menucount++;
					}
					if(countTo.equals("minus") && menucount > 1) {
						menucount--;
					}
					menu.replace("count", ""+menucount);
					cart.set(i, menu);
				}
				session.setAttribute("cart", cart); //수정이 끝난 뒤 세션에 데이터를 덮어쓴다.
				return "order/menuCart"; //해당 menuIdx에서 작업이 끝나면 탈출
			}
		}
		return "order/menuCart";
	}
	
	//결제 화면
	@GetMapping("payment")
	public String payment(String shopIdx,Model model,HttpSession session) {
		//로그인한 사용자의 정보 읽기
		//Member userInfo = (Member)session.getAttribute("userInfo");
		
		//----테스트를 위해 아이디를 'OrderTest'로 임의 등록
		Member userInfo = new Member();
		userInfo.setMemberId("OrderTest");
		
		//payment에 파라미터 없이 접근하면 shoplist로 보낸다.
		if(shopIdx == null) {
			return "redirect:/order/shoplist";
		}
		
		//만약 사용자가 결제하지 않은 주문이 남아있다면, 세션 장바구니를 제거하고 바로 남은 주문 정보를 불러온다.
		
		if(orderService.selectOrderByMemberIdAndShopIdx(userInfo.getMemberId(),shopIdx) == null) {
			//장바구니 내역이 없거나, 장바구니 idx와 입력한 shopIdx가 다르다면 shoplist로 보낸다.
			if(session.getAttribute("cartIdx") == null ||!session.getAttribute("cartIdx").equals(shopIdx) ) {
				return "redirect:/order/shoplist";
			}
			
			//결제하지 않은 주문이 없고 세션에 장바구니 정보가 있다면 세션 정보로 DB에 Order 데이터를 등록한다.
			//장바구니의 menuidx + count를 service에 넘겨주어 order + menu ordering 등록
			List<Map> cart = (List)session.getAttribute("cart");
			List<Map> ordering = new ArrayList<>();
			for(Map<String,String> menu : cart) {
				ordering.add(Map.of("menuIdx",menu.get("menuIdx"),"count",menu.get("count")));
			}
			
			
			orderService.registOrder(ordering,shopIdx,userInfo.getMemberId(),"N",(String)session.getAttribute("tableNum")); //포장여부는 default N으로 보내고 payment할때 수정, 세션의 값을 통해 DB에 Order, Menu_ordering 데이터 등록
		}
		session.removeAttribute("cartIdx");
		session.removeAttribute("cart"); //DB에 모두 등록되었으므로 세션 데이터 제거
		
		//사용자가 가진 주문을 찾고 해당 데이터들을 model로 넘겨준다. 
		Order order = orderService.selectOrderByMemberIdAndShopIdx(userInfo.getMemberId(),shopIdx);
		model.addAttribute(order);
		model.addAttribute("orderMenu",orderService.selectMenuOrderingByOrderIdx(order.getOrderIdx()));
		model.addAttribute(orderService.selectShopbyIdx(shopIdx));
		return "order/payment";
	}
	
	//DB에 저장된 주문정보를 결제하지 않고 취소요청
	@GetMapping("discard")
	public String discard(String orderIdx,HttpSession session,Model model) {
		if(orderIdx == null) {
			model.addAttribute("msg",ErrorCode.AUTH01);
			return "/common/result";
		}
		//테스트를 위해 지정한 아이디
		Member userInfo = new Member();
		userInfo.setMemberId("OrderTest");
		
		if(orderService.discardOrder(orderIdx,userInfo.getMemberId())) {
			model.addAttribute("msg","주문을 취소하였습니다.");
			model.addAttribute("url","/order/shoplist");
			return "/common/result";
		};
		model.addAttribute("msg","취소할 주문이 없습니다.");
		return "/common/result";
	}
	
	@GetMapping("pay")
	public String pay(String payType, String shopIdx, String orderIdx, HttpSession session, Model model) {
		//사용자가 등록된 order의 데이터를 확인하고 결제/결제확인 버튼을 클릭, 결제 데이터를 등록하는 절차
		Member userInfo = new Member();
		userInfo.setMemberId("OrderTest");
		//세션의 사용자 정보와 orderIdx, shopIdx 정보가 일치하는지 체크하고 진행
		Order order = orderService.checkOrderInfo(orderIdx,shopIdx,userInfo.getMemberId());
		if(order == null) {
			model.addAttribute("msg","잘못된 결제정보입니다.");
			model.addAttribute("url","/order/shoplist");
			return "/common/result";
		}
		boolean res = true;
		
		////////////
		//결제 모듈 동작
		/////////////
		
		if(res && orderService.insertPayment(order,payType,shopIdx)) { //결제 성공시 결제데이터 등록 진행
			model.addAttribute("msg","결제가 완료되었습니다.");
			model.addAttribute("url","/order/payment?shopIdx="+shopIdx);
			return "/common/result";
		}
		
		//결제 실패시
		model.addAttribute("msg","결제에 실패하였습니다.");
		model.addAttribute("url","/order/payment?shopIdx="+shopIdx);
		return "/common/result";
	}
	
	//결제내역 화면
	@GetMapping("myorders")
	public String myOrders() {
		return "order/myOrders";
	}
}
