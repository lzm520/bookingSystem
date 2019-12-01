package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;


@Service(value="customerBookingSystemService")
public class customerBookingSystemServiceImpl implements customerBookingSystemService{
	
	@Autowired
	private customerBookingSystemDao cbsDao;
	
	/*买家Dao方法实现*/
	@Override
	public List<commodityInfo> searchAllCommodityInfos() {
		return	cbsDao.searchAllCommodityInfos();
	}

	@Override
	public List<catalogueBean> getCatalogueList() {
		return cbsDao.getCatalogueList();
	}

	@Override
	public commodityInfo searchCertainCommodityInfo(String name) {		
		return cbsDao.searchCertainCommodityInfo(name);
	}

	@Override
	public void submitOrder(Map<String, Integer> purchaseGoods,String customer,float orderCost) {
		cbsDao.submitOrder(purchaseGoods,customer,orderCost);
	}

	@Override
	public int checkOrder(String orderId) {
		return cbsDao.checkOrder(orderId);
	}
}
