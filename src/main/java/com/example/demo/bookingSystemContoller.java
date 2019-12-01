package com.example.demo;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class bookingSystemContoller {
	@Autowired
	customerBookingSystemService cbss;
	@Autowired
	sellerBookingSystemService sbss;
	
	/*浏览所有商品*/
	@ResponseBody
	@RequestMapping("searchAllCommodityInfos")
	public String searchAllCommodityInfos() 
	{
		List<commodityInfo> allCommodityList= cbss.searchAllCommodityInfos();
		List<catalogueBean> catalogueList=cbss.getCatalogueList();
		
		String json="";
		json+="{";
		
		json+="\"";
		json+="stat";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+="ok";
		json+="\"";
		json+=",";
		json+="\"";
		json+="catalogues";
		json+="\"";
		json+=" : ";
		json+="{";
		
		int sum=0;
		int i=0;
		for(int k=0;k<catalogueList.size();k++) {
			sum+=catalogueList.get(k).getNumber();
			json+="\"";
			json+=catalogueList.get(k).getCatalogueName();
			json+="\"";
			json+=" : ";
			json+="{";
			for(;i<sum;i++) {
				json+="\"";
				json+=allCommodityList.get(i).getName();
				json+="\"";
				json+=" : ";
				json+="{";
				
				json+="\"";
				json+="name";
				json+="\"";
				json+=" : ";
				json+="\"";
				json+=allCommodityList.get(i).getName();
				json+="\"";
				json+=",";
				
				json+="\"";
				json+="price";
				json+="\"";
				json+=" : ";
				json+="\"";
				json+=allCommodityList.get(i).getPrice();
				json+="\"";
				json+=",";
				
				json+="\"";
				json+="store";
				json+="\"";
				json+=" : ";
				json+="\"";
				json+=allCommodityList.get(i).getStore();
				json+="\"";
				json+=",";
				
				json+="\"";
				json+="comment";
				json+="\"";
				json+=" : ";
				json+="\"";
				json+=allCommodityList.get(i).getComment();
				json+="\"";
				json+=",";
				
				json+="\"";
				json+="commodityStyle";
				json+="\"";
				json+=" : ";
				json+="\"";
				json+=allCommodityList.get(i).getCommodityStyle();
				json+="\"";
							
				json+="}";
				if(i!=sum-1)
					json+=",";
				
			}
			json+="}";
			if(k!=catalogueList.size()-1)
				json+=",";
		}
		
		
		json+="}";
		json+="}";
		
		return json;
	}
	
	/*搜索指定商品*/
	@ResponseBody
	@RequestMapping("searchCertainCommodityInfo")
	public String searchCertainCommodityInfo(HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("name");
		commodityInfo ci=cbss.searchCertainCommodityInfo(name);
		if(ci==null) {
			return "null";
		}
		String json="";
		json+="{";
		
		json+="\"";
		json+="stat";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+="ok";
		json+="\"";
		json+=",";
		json+="\"";
		json+="name";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+=ci.getName();
		json+="\"";
		json+=",";	
		json+="\"";
		json+="price";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+=ci.getPrice();
		json+="\"";
		json+=",";	
		json+="\"";
		json+="store";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+=ci.getStore();
		json+="\"";
		json+=",";	
		json+="\"";
		json+="comment";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+=ci.getComment();
		json+="\"";
		json+=",";
		json+="\"";
		json+="commodityStyle";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+=ci.getCommodityStyle();
		json+="\"";
			
		json+="}";
		
		return json;
	}
	
	/*提交订单*/
	@ResponseBody
	@RequestMapping("submitOrder")
	public String submitOrder(HttpServletRequest request,HttpServletResponse response) {
		String customer=(String) request.getSession().getAttribute("customer");
		int i=0;
		Map<String, Integer> purchaseGoods=new HashMap<String, Integer>();
		
		Enumeration<String> goods=request.getParameterNames();
		
		while(goods.hasMoreElements()) {
			String goodName=goods.nextElement();
			if(!goodName.equals("orderCost"))
				purchaseGoods.put(goodName, Integer.parseInt(request.getParameter(goodName)));
			System.out.println(goodName);
		}
		
		float orderCost=Float.parseFloat(request.getParameter("orderCost"));
		cbss.submitOrder(purchaseGoods,customer,orderCost);
		return "successful";
	}
	
	/*查看订单状态*/
	@ResponseBody
	@RequestMapping("checkOrder")
	public String checkOrder(HttpServletRequest request,HttpServletResponse response) {
		String orderId=request.getParameter("orderId");
		int flag=cbss.checkOrder(orderId);
		if(flag==1) {
			return "订单已接受";
		}
		else if(flag==0){
			return "卖家还未查看订单";
		}
		else if(flag==-1) {
			return "订单已拒绝";
		}
		else {
			return "";
		}
	}
	
	/*上架一个商品*/
	@ResponseBody
	@RequestMapping("importCommodity")
	public String importCommodity(HttpServletRequest request,HttpServletResponse response) {		
		String name=request.getParameter("name");
		float price=Float.parseFloat(request.getParameter("price"));
		String comment=request.getParameter("comment");
		int store=Integer.parseInt(request.getParameter("store"));
		String commodityStyle=request.getParameter("commodityStyle");

		commodityInfo ci=new commodityInfo();
		ci.setName(name);
		ci.setPrice(price);
		ci.setComment(comment);
		ci.setStore(store);
		ci.setCommodityStyle(commodityStyle);	
		
		boolean flag=false;		
		try {
			flag=sbss.importCommodity(ci);
		}
		catch (org.springframework.dao.DuplicateKeyException e) {			
			return "商品名称重复输入";
		}
		catch(Exception e) {
			return e.toString();
		}
		
		if(flag)
			return "successful";
		else	
			return "false";
	}
	
	/*下架一个商品*/
	@ResponseBody
	@RequestMapping("withdrawCommodity")
	public String withdrawCommodity(HttpServletRequest request,HttpServletResponse response) {	
		String name=request.getParameter("name");
		float price=Float.parseFloat(request.getParameter("price"));
		String comment=request.getParameter("comment");
		int store=Integer.parseInt(request.getParameter("store"));
		String commodityStyle=request.getParameter("commodityStyle");
		
		commodityInfo ci=new commodityInfo();
		ci.setName(name);
		ci.setPrice(price);
		ci.setComment(comment);
		ci.setStore(store);
		ci.setCommodityStyle(commodityStyle);
		boolean flag=sbss.withdrawCommodity(ci);
		if(flag)
			return "successful";
		else	
			return "false";
	}
	
	/*接受订单*/
	@ResponseBody
	@RequestMapping("acceptOrder")
	public String acceptOrder(HttpServletRequest request,HttpServletResponse response) {
		String orderId=request.getParameter("orderId");
		boolean flag=false;
		try {
			flag=sbss.acceptOrder(orderId);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			return "已接受";
		}	
		if(flag)
			return "successful";
		else	
			return "false";
	}
	
	/*拒绝订单*/
	@ResponseBody
	@RequestMapping("rejectOrder")
	public String rejectOrder(HttpServletRequest request,HttpServletResponse response) {
		String orderId=request.getParameter("orderId");
		boolean flag=sbss.rejectOrder(orderId);
		if(flag)
			return "successful";
		else	
			return "false";
	}
	
	/*新增一个商品目录*/
	@ResponseBody
	@RequestMapping("increaseCatalogue")
	public String increaseCatalogue(HttpServletRequest request,HttpServletResponse response) {
		String catalogue=request.getParameter("catalogue");
		boolean flag=false;
		try {
			flag=sbss.increaseCatalogue(catalogue);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			return "该目录已存在";
		}
		if(flag)
			return "successful";
		else	
			return "false";
	}
	
	/*获取当天top10订单*/
	@ResponseBody
	@RequestMapping("todayTopTen")
	public String todayTopTen() {
		List<orderInfo> todayTopTenList=sbss.todayTopTen();
		String json="";
		json+="{";
		
		json+="\"";
		json+="stat";
		json+="\"";
		json+=" : ";
		json+="\"";
		json+="ok";
		json+="\"";
		json+=",";
		
		json+="\"";
		json+="list";
		json+="\"";
		json+=" : ";
		json+="{";
		for(int i=0;i<todayTopTenList.size();i++) {
			json+="\"";
			json+="order"+i;
			json+="\"";
			json+=" : ";
			json+="{";
			
			json+="\"";
			json+="orderId";
			json+="\"";
			json+=" : ";
			json+="\"";
			json+=todayTopTenList.get(i).getOrderId();
			json+="\"";
			json+=",";
			
			json+="\"";
			json+="name";
			json+="\"";
			json+=" : ";
			json+="\"";
			json+=todayTopTenList.get(i).getName();
			json+="\"";
			json+=",";
			
			json+="\"";
			json+="orderMessage";
			json+="\"";
			json+=" : ";
			json+=todayTopTenList.get(i).getOrderMessage();
			json+=",";
			
			json+="\"";
			json+="orderCost";
			json+="\"";
			json+=" : ";
			json+="\"";
			json+=todayTopTenList.get(i).getOrderCost();
			json+="\"";
			json+=",";
			
			json+="\"";
			json+="orderDate";
			json+="\"";
			json+=" : ";
			json+="\"";
			json+=todayTopTenList.get(i).getOrderDate();
			json+="\"";
			
			json+="}";
			
			if(i!=todayTopTenList.size()-1) {
				json+=",";
			}
		}
		json+="}";
		
		json+="}";
		return json;
	}
}
