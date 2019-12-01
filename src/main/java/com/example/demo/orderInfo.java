package com.example.demo;

/*订单信息Bean*/
public class orderInfo {
	/*订单号*/
	private String orderId;
	/*买家姓名*/
	private String name;	
	/*订单信息*/
	private String orderMessage;
	/*订单状态*/
	private int orderStyle;
	/*订单费用*/
	private float orderCost;
	/*订购日期*/
	private String orderDate;
	
	public float getOrderCost() {
		return orderCost;
	}
	public void setOrderCost(float orderCost) {
		this.orderCost = orderCost;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public int getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(int orderStyle) {
		this.orderStyle = orderStyle;
	}
	
	
}
