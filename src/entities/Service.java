package entities;

public class Service {
	private String type;
	private int price;
	public Service(String type, int price){
		this.type = type;
		this.price = price;
	}
	public String getType(){
		return type;
	}
	public int getPrice(){
		return price;
	}
}
