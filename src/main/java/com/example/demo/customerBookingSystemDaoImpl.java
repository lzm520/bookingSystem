package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class customerBookingSystemDaoImpl implements customerBookingSystemDao{
	@Autowired
	private JdbcTemplate jt;
	
	/*买家Dao方法实现*/
	@Override
	public List<commodityInfo> searchAllCommodityInfos() {
		String sql="SELECT * FROM commodityInfo ORDER BY commodityStyle ASC";	
		return jt.query(sql, new BeanPropertyRowMapper(commodityInfo.class));
	}
	
	@Override
	public List<catalogueBean> getCatalogueList() {
		String sql="SELECT * FROM catalogueList ORDER BY catalogueName ASC";
		return jt.query(sql, new BeanPropertyRowMapper(catalogueBean.class));
	}

	@Override
	public commodityInfo searchCertainCommodityInfo(String name) {
		String sql="SELECT * FROM commodityInfo WHERE name=?";
		
		Map commodityInfoMap=null;
		
		boolean flag=false;
		
		try {
			if(jt.queryForMap(sql, name)!=null) {
				commodityInfoMap=jt.queryForMap(sql, name);
				flag=true;
			}
		} 
		catch (Exception e) {
		}		
		if(flag==false) {
			return null;
		}
		
		commodityInfo ci=new commodityInfo();
		ci.setName(commodityInfoMap.get("name").toString());
		ci.setPrice(Float.parseFloat(commodityInfoMap.get("price").toString()));
		ci.setStore(Integer.parseInt(commodityInfoMap.get("store").toString()));
		ci.setComment(commodityInfoMap.get("comment").toString());
		ci.setCommodityStyle(commodityInfoMap.get("commodityStyle").toString());
		return ci;
	}
	
	@Override
	public void submitOrder(Map<String, Integer> purchaseGoods,String customer,float orderCost) {
		String sql="INSERT INTO allorder(orderId,name,orderMessage,orderCost,orderStyle,orderDate) VALUES (?,?,?,?,?,?)";
		Set<String> goodNames=purchaseGoods.keySet();
		String orderId=produceOrderId.getOrderIdByTime();
		String orderMessage="";
		orderMessage+="{";
		orderMessage+="\"";
		orderMessage+="stat";
		orderMessage+="\"";
		orderMessage+=" : ";
		orderMessage+="\"";
		orderMessage+="ok";
		orderMessage+="\"";
		orderMessage+=",";
		for(String name:goodNames) {
			orderMessage+="\"";
			orderMessage+=name;
			orderMessage+="\"";
			orderMessage+=" : ";
			orderMessage+="\"";
			orderMessage+=purchaseGoods.get(name);
			orderMessage+="\"";
			orderMessage+=",";
		}
		orderMessage=orderMessage.substring(0, orderMessage.length()-1);
		orderMessage+="}";
		System.out.println(orderMessage);
		orderInfo oi=new orderInfo();
		oi.setOrderId(orderId);
		oi.setName(customer);
		oi.setOrderMessage(orderMessage);
		oi.setOrderCost(orderCost);
		oi.setOrderStyle(0);
		oi.setOrderDate(produceOrderId.getToday());
		jt.update(sql, oi.getOrderId(),oi.getName(),oi.getOrderMessage(),oi.getOrderCost(),oi.getOrderStyle(),oi.getOrderDate());
	}

	@Override
	public int checkOrder(String orderId) {
		String sql="SELECT orderStyle FROM allorder WHERE orderId=?";
		Map<String, Object> result= jt.queryForMap(sql, orderId);
		System.out.println(result.get("orderStyle").toString());
		return Integer.parseInt(result.get("orderStyle").toString());
	}
}
