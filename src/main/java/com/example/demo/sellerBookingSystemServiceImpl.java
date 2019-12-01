package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="sellerBookingSystemService")
public class sellerBookingSystemServiceImpl implements sellerBookingSystemService{
	
	@Autowired
	private sellerBookingSystemDao sbsDao;
	
	/*卖家Dao方法实现*/
	@Override
	public boolean importCommodity(commodityInfo ci) {
		return sbsDao.importCommodity(ci);
	}

	@Override
	public boolean withdrawCommodity(commodityInfo ci) {
		return sbsDao.withdrawCommodity(ci);
	}

	@Override
	public boolean acceptOrder(String orderId) {
		return sbsDao.acceptOrder(orderId);
	}

	@Override
	public boolean rejectOrder(String orderId) {
		return sbsDao.rejectOrder(orderId);
	}
	
	@Override
	public boolean increaseCatalogue(String catalogue) {
		return sbsDao.increaseCatalogue(catalogue);
	}
	
	@Override
	public List<orderInfo> todayTopTen() {		
		return sbsDao.todayTopTen();
	}
}
