package com.kh.toy.waiting.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.toy.shop.model.vo.Shop;
import com.kh.toy.waiting.model.repository.WaitingRepository;
import com.kh.toy.waiting.model.vo.Waiting;

@Service
public class WaitingServiceImpl implements WaitingService {

	private final WaitingRepository waitingRepository;
	
	public WaitingServiceImpl(WaitingRepository waitingRepository) {
		this.waitingRepository = waitingRepository;
	}

	@Override
	public List<Waiting> selectWaitingListByDate(Date waitRegDate) {
		return waitingRepository.selectWaitingListByDate(waitRegDate);
	}

	@Override
	public int insertWaiting(Waiting waiting) {
		return waitingRepository.insertWaiting(waiting);
	}

	@Override
	public int updateArrived(String waitIdx) {
		return waitingRepository.updateArrived(waitIdx);
	}

	@Override
	public int updateCancel(String waitIdx) {
		return waitingRepository.updateCancel(waitIdx);
	}

	@Override
	public Shop selectShopByShopIdx(String shopIdx) {
		return waitingRepository.selectShopByShopIdx(shopIdx);
	}
}
