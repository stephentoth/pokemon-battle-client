package pokemonFramework;

import java.io.Serializable;
import java.util.ArrayList;

public class Bundle implements Serializable {


	private static final long serialVersionUID = -4870201060489615035L;

	private int type;

	public static ArrayList<Profile> profiles = new ArrayList<Profile>(8);

	private Party Party0;
	private Move Move0;

	private Party Party1;
	private Move Move1;

	public Bundle(Profile profile0, Party party0) {
		type = 0;
		profiles.add(0, profile0);
		Party0 = party0;
	}

	public Bundle(Profile profile0, Party party0, Move move0) {
		type = 1;
		profiles.add(0, profile0);
		Party0 = party0;
		Move0 = move0;
	}

	public Bundle(Profile profile0, Party party0, Move move0, Profile profile1, Party party1, Move move1) {
		type = 2;
		profiles.add(0, profile0);
		Party0 = party0;
		Move0 = move0;
		profiles.add(1, profile1);
		Party1 = party1;
		Move1 = move1;
	}

	public Bundle(Profile[] profiles) {
		type = 3;
		for (int i = 0; i < profiles.length; i++) {
			Bundle.profiles.add(i, profiles[i]);
		}
	}

	public Bundle(Profile profile0, Party party0, Move move0, Profile profile1, Party party1, Move move1, Profile profile2, Profile profile3, Profile profile4, Profile profile5, Profile profile6, Profile profile7) {
		type = 7;
		Party0 = party0;
		Move0 = move0;
		Party1 = party1;
		Move1 = move1;
		profiles.add(0, profile0);
		profiles.add(1, profile1);
		profiles.add(2, profile2);
		profiles.add(3, profile3);
		profiles.add(4, profile4);
		profiles.add(5, profile5);
		profiles.add(6, profile6);
		profiles.add(7, profile7);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public Profile[] getProfiles() {
		return profiles.toArray(new Profile[profiles.size()]);
	}

	public void setProfiles(Profile[] profiles) {
		for (int i = 0; i < profiles.length; i++) {
			Bundle.profiles.add(i, profiles[i]);
		}
	}

	public Party getParty0() {
		return Party0;
	}

	public void setParty0(Party party0) {
		Party0 = party0;
	}

	public Move getMove0() {
		return Move0;
	}

	public void setMove0(Move move0) {
		Move0 = move0;
	}

	public Party getParty1() {
		return Party1;
	}

	public void setParty1(Party party1) {
		Party1 = party1;
	}

	public Move getMove1() {
		return Move1;
	}

	public void setMove1(Move move1) {
		Move1 = move1;
	}

}