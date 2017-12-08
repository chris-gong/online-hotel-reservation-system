package entities;

public class Hotel {
	
	private int hotelId;
	private String hName;
	public Hotel(int id, String name) {
		hotelId = id;
		hName = name;
	}
	
	public int getId() {
		return hotelId;
	}
	public String getName() {
		return hName;
	}
}
