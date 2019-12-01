package com.example.demo;

/*商品目录Bean*/
public class catalogueBean {
	/*目录名称*/
	private String catalogueName;
	/*该目录下商品数量*/
	private int number;
	
	public String getCatalogueName() {
		return catalogueName;
	}
	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
