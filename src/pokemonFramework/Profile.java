package pokemonFramework;

import java.io.Serializable;

public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6905947139643950662L;
	private String name;
	private int gender; //0=m 1=f 3=u
	
	public Profile(String name, int gender) {
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
}
