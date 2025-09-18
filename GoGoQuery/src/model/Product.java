package model;

public class Product {
	private int ItemID;
	private String ItemName;
	private double ItemPrice;
	private String ItemDesc;
	private int ItemStock;
	private String ItemCategory;
	
	public Product(int itemID, String itemName, double itemPrice, String itemDesc, int itemStock, String itemCategory) {
		super();
		ItemID = itemID;
		ItemName = itemName;
		ItemPrice = itemPrice;
		ItemDesc = itemDesc;
		ItemStock = itemStock;
		ItemCategory = itemCategory;
	}
	public int getItemID() {
		return ItemID;
	}
	public void setItemID(int itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public double getItemPrice() {
		return ItemPrice;
	}
	public void setItemPrice(double itemPrice) {
		ItemPrice = itemPrice;
	}
	public String getItemDesc() {
		return ItemDesc;
	}
	public void setItemDesc(String itemDesc) {
		ItemDesc = itemDesc;
	}
	public int getItemStock() {
		return ItemStock;
	}
	public void setItemStock(int itemStock) {
		ItemStock = itemStock;
	}
	public String getItemCategory() {
		return ItemCategory;
	}
	public void setItemCategory(String itemCategory) {
		ItemCategory = itemCategory;
	}
	
	
}
