package model;

import java.util.List;

public class CartDetail {
	private int userId;
	private Product product;
	private int quantity;
	public int itemStock;
	
	
	public CartDetail(int userId, Product product, int quantity, int itemStock) {
		super();
		this.userId = userId;
		this.product = product;
		this.quantity = quantity;
		this.itemStock = itemStock;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getItemStock() {
		return itemStock;
	}

	public void setItemStock(int itemStock) {
		this.itemStock = itemStock;
	}

	public double totalItemPrice() {
		return product.getItemPrice() * quantity;
	}
	
	public static double getGrandTotal(List<CartDetail> cartItems) {
	    double total = 0.0;
	    for (CartDetail item : cartItems) {
	        total += item.getQuantity() * item.getProduct().getItemPrice();
	    }
	    return total;
	}
}
