package entities;

public class Breakfast {
	private String type;
	private int price;
	private String description;
	private int timesOrdered;
	
	public Breakfast(String type, int price, String description){
		this.type = type;
		this.price = price;
		this.description = description;
	}
	//dummy constructor needed for json parsing in jackson library
	public Breakfast(){
		
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
	public void setTimesOrdered(int i){
		timesOrdered = i;
	}
	public int getTimesOrdered(){
		return timesOrdered;
	}
}
