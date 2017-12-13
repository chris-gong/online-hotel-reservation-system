package entities;

public class Service {
	private String type;
	private int price;
	private int timesOrdered;
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
	public void setTimesOrdered(int i){
		timesOrdered = i;
	}
	public int getTimesOrdered(){
		return timesOrdered;
	}
}
