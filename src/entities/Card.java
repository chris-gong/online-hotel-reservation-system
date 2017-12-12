package entities;

public class Card {
	private int cardId;
	private String type;
	private String name;
	private String expDate;
	public Card(int cardId, String type, String name, String expDate){
		this.cardId = cardId;
		this.type = type;
		this.name = name;
		this.expDate = expDate;
	}
	public int getCardId(){
		return cardId;
	}
	public String getType(){
		return type;
	}
	public String getName(){
		return name;
	}
	public String getExpDate(){
		return expDate;
	}
}
