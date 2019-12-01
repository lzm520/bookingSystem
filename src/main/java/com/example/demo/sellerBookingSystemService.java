package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

public interface sellerBookingSystemService {
	/*卖家上架商品*/
	public boolean importCommodity(commodityInfo ci);
	/*卖家下架商品*/
	public boolean withdrawCommodity(commodityInfo ci);
	/*卖家接受订单*/
	public boolean acceptOrder(String orderId);
	/*卖家拒绝订单*/
	public boolean rejectOrder(String orderId);
	/*卖家新增目录接口*/
	public boolean increaseCatalogue(String catalogue);
	/*当天下单金额top10*/
	public List<orderInfo> todayTopTen();
}
