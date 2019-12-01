package com.example.demo;

/*商品信息Bean*/
public class commodityInfo {
	/*商品名称*/
	private String name;
	/*商品价格*/
	private float price;
	/*商品信息*/
	private String comment;
	/*商品库存*/
	private int store;
	/*商品类型*/
	private String commodityStyle;
	
	
	public String getCommodityStyle() {
		return commodityStyle;
	}
	public void setCommodityStyle(String commodityStyle) {
		this.commodityStyle = commodityStyle;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
