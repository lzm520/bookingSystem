package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class sellerBookingSystemDaoImpl implements sellerBookingSystemDao{
	@Autowired
	private JdbcTemplate jt;
	
	/*卖家Dao方法实现*/
	@Override
	public boolean importCommodity(commodityInfo ci) {		
		String sql="INSERT INTO commodityInfo(name,price,store,comment,commodityStyle) VALUES(?,?,?,?,?)";
		int i=jt.update(sql,ci.getName(),ci.getPrice(),ci.getStore(),ci.getComment(),ci.getCommodityStyle());
		String sql_2="UPDATE cataloguelist SET number=number+1 WHERE catalogueName=?";
		if(i==1) {
			jt.update(sql_2, ci.getCommodityStyle());
			return true;
		}
			
		else 
			return false;
	}

	@Override
	public boolean withdrawCommodity(commodityInfo ci) {
		String name=ci.getName();
		String commodityStyle=ci.getCommodityStyle();
		String sql="DELETE FROM commodityInfo WHERE name=? AND commodityStyle=?";
		String sql_2="UPDATE cataloguelist SET number=number-1 WHERE catalogueName=?";
		int i=jt.update(sql, name,commodityStyle);
		if(i==1) {
			jt.update(sql_2, ci.getCommodityStyle());
			return true;
		}			
		else 
			return false;
	}

	@Override
	public boolean acceptOrder(String orderId) {
		String sql_1="UPDATE allorder SET orderStyle=1 WHERE orderId=?";
		String sql_2="SELECT * FROM allorder WHERE orderId='"+orderId+"'";
		String sql_3="INSERT INTO acceptorder(orderId,name,orderMessage,orderCost,orderStyle,orderDate) VALUES(?,?,?,?,?,?)";
		jt.update(sql_1,orderId);
		orderInfo oi=(orderInfo) jt.query(sql_2,new BeanPropertyRowMapper(orderInfo.class)).get(0);
		int i=jt.update(sql_3, oi.getOrderId(),oi.getName(),oi.getOrderMessage(),oi.getOrderCost(),oi.getOrderStyle(),oi.getOrderDate());
		if(oi!=null&&i==1)
			return true;
		else
			return false;
	}	

	@Override
	public boolean rejectOrder(String orderId) {
		String sql="UPDATE allorder SET orderStyle=-1 WHERE orderId=?";
		int i=jt.update(sql, orderId);
		if(i==1)
			return true;
		else
			return false;
	}

	@Override
	public boolean increaseCatalogue(String catalogue) {
		String sql="INSERT INTO cataloguelist(catalogueName,number) VALUES(?,0)";
		int i=jt.update(sql, catalogue);
		if(i==1)
			return true;
		else
			return false;
	}
	
	@Override
	public List<orderInfo> todayTopTen() {
		String date=produceOrderId.getToday();
		String sql="SELECT * FROM acceptorder WHERE orderDate='"+ date +"' ORDER BY orderCost DESC LIMIT 10";
		return jt.query(sql, new BeanPropertyRowMapper(orderInfo.class));
	}
	
}
