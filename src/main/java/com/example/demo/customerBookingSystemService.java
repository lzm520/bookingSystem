package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface customerBookingSystemService {
	/*浏览所有商品*/
	public List<commodityInfo> searchAllCommodityInfos();
	/*获取目录列表*/
	public List<catalogueBean> getCatalogueList();
	/*买家搜索指定商品*/
	public commodityInfo searchCertainCommodityInfo(String name);
	/*买家提交订单*/
	public void submitOrder(Map<String, Integer> purchaseGoods,String customer,float orderCost);
	/*买家查看订单状态*/
	public int checkOrder(String orderId);
}
