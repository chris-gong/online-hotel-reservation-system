package entities;

public class Room {
	private int hotelId;
	private int roomNum;
	private int floorNum;
	private int price;
	private String description;
	private String type;
	private double discount = 0;
	private int capacity;
	public Room(int id, int roomNum, int floorNum, int price, String description, String type, int capacity) {
		this.hotelId = id;
		this.roomNum = roomNum;
		this.floorNum = floorNum;
		this.price = price;
		this.description = description;
		this.type = type;
		this.capacity = capacity;
	}
	
	public int getId() {
		return hotelId;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public int getFloorNum(){
		return floorNum;
	}
	public int getPrice() {
		return price;
	}
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
	public int getCapacity() {
		return capacity;
	}
 	public void setDiscount(double d) {
		discount=d;
	}
	public double getDiscount() {
		return discount;
	}
}
