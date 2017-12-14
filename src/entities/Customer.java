package entities;

public class Customer implements Comparable<Customer>{
	
	
	private int totalSpent;
	private int userId;
	
	public Customer (int totalSpent, int userId) {
		this.totalSpent = totalSpent;
		this.userId = userId;
	}
	
	
	public void spendMore (int x) {
		totalSpent += x;
	}
	
	public int getTotalSpent() {
		return totalSpent;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int compareTo(Customer c) {
		return this.totalSpent - c.getTotalSpent();
	}


}
