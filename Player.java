package airhockey;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int Score;
	private String name;
	private int Id;

	public Player(String name, int Id){
		this.name = name;
		this.Id = Id;
	}
	// Testing purposes I guess?
	public Player(){}

	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId(){
		return Id;
	}
}
