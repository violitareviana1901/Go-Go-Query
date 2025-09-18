package main;

public class managerDetail {
	private int transactionid;
	private int customerid;
	private String customeremail;
	private String date;
	private double total;
	private String Status;
	
	public managerDetail(int transactionid, int customerid, String customeremail, String date, double total,
			String status) {
		super();
		this.transactionid = transactionid;
		this.customerid = customerid;
		this.customeremail = customeremail;
		this.date = date;
		this.total = total;
		Status = status;
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getCustomeremail() {
		return customeremail;
	}

	public void setCustomeremail(String customeremail) {
		this.customeremail = customeremail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}
