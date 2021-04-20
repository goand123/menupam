package com.kh.toy.notification.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.toy.notification.model.service.NotificationService;
import com.kh.toy.notification.model.vo.Notification;
import com.kh.toy.review.model.vo.Review;

@Controller
@RequestMapping("notification")
public class NotificationController {

	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@PostMapping("notifications")
	@ResponseBody
	public Map<Integer, Notification> getNotifications(String memberId) {
		
		Map<Integer, Notification> notificationMap = notificationService.getNotifications(memberId);
		
		return notificationMap;
	}
}
