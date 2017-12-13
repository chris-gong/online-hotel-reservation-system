package entities;

public class Breakfast {
	private String type;
	private int price;
	private String description;
	
	public Breakfast(String type, int price, String description){
		this.type = type;
		this.price = price;
		this.description = description;
	}
	public String getType(){
		return type;
	}
	public int getPrice(){
		return price;
	}
	public String getDescription(){
		return description;
	}
}
