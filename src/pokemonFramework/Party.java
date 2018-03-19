package pokemonFramework;

import java.io.Serializable;
import java.util.ArrayList;

public class Party implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -401661132587565350L;
	
	private Pokemon memb0;
	private Pokemon memb1;
	private Pokemon memb2;
	private Pokemon memb3;
	private Pokemon memb4;
	private Pokemon memb5;
	private Pokemon[] partyArray;
	private ArrayList<Pokemon> partyArrayList = new ArrayList<Pokemon>(6);
	
	public Party(Pokemon memb0, Pokemon memb1, Pokemon memb2, Pokemon memb3, Pokemon memb4, Pokemon memb5) {
		this.memb0 = memb0;
		this.memb1 = memb1;
		this.memb2 = memb2;
		this.memb3 = memb3;
		this.memb4 = memb4;
		this.memb5 = memb5;
		this.partyArray = new Pokemon[] {memb0, memb1, memb2, memb3, memb4, memb5};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(memb1);
		this.partyArrayList.add(memb2);
		this.partyArrayList.add(memb3);
		this.partyArrayList.add(memb4);
		this.partyArrayList.add(memb5);
	}
	public Party(Pokemon memb0, Pokemon memb1, Pokemon memb2, Pokemon memb3, Pokemon memb4) {
		this.memb0 = memb0;
		this.memb1 = memb1;
		this.memb2 = memb2;
		this.memb3 = memb3;
		this.memb4 = memb4;
		this.memb5 = null;
		this.partyArray = new Pokemon[] {memb0, memb1, memb2, memb3, memb4, null};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(memb1);
		this.partyArrayList.add(memb2);
		this.partyArrayList.add(memb3);
		this.partyArrayList.add(memb4);
		this.partyArrayList.add(null);
	}
	public Party(Pokemon memb0, Pokemon memb1, Pokemon memb2, Pokemon memb3) {
		this.memb0 = memb0;
		this.memb1 = memb1;
		this.memb2 = memb2;
		this.memb3 = memb3;
		this.memb4 = null;
		this.memb5 = null;
		this.partyArray = new Pokemon[] {memb0, memb1, memb2, memb3, null, null};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(memb1);
		this.partyArrayList.add(memb2);
		this.partyArrayList.add(memb3);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
	}
	public Party(Pokemon memb0, Pokemon memb1, Pokemon memb2) {
		this.memb0 = memb0;
		this.memb1 = memb1;
		this.memb2 = memb2;
		this.memb3 = null;
		this.memb4 = null;
		this.memb5 = null;
		this.partyArray = new Pokemon[] {memb0, memb1, memb2, null, null, null};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(memb1);
		this.partyArrayList.add(memb2);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
	}
	public Party(Pokemon memb0, Pokemon memb1) {
		this.memb0 = memb0;
		this.memb1 = memb1;
		this.memb2 = null;
		this.memb3 = null;
		this.memb4 = null;
		this.memb5 = null;
		this.partyArray = new Pokemon[] {memb0, memb1, null, null, null, null};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(memb1);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
	}
	public Party(Pokemon memb0) {
		this.memb0 = memb0;
		this.memb1 = null;
		this.memb2 = null;
		this.memb3 = null;
		this.memb4 = null;
		this.memb5 = null;
		this.partyArray = new Pokemon[] {memb0, null, null, null, null, null};
		this.partyArrayList.add(memb0);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
		this.partyArrayList.add(null);
	}
	
	
	public Pokemon getMemb0() {
		return memb0;
	}
	public void setMemb0(Pokemon memb0) {
		this.memb0 = memb0;
	}
	public Pokemon getMemb1() {
		return memb1;
	}
	public void setMemb1(Pokemon memb1) {
		this.memb1 = memb1;
	}
	public Pokemon getMemb2() {
		return memb2;
	}
	public void setMemb2(Pokemon memb2) {
		this.memb2 = memb2;
	}
	public Pokemon getMemb3() {
		return memb3;
	}
	public void setMemb3(Pokemon memb3) {
		this.memb3 = memb3;
	}
	public Pokemon getMemb4() {
		return memb4;
	}
	public void setMemb4(Pokemon memb4) {
		this.memb4 = memb4;
	}
	public Pokemon getMemb5() {
		return memb5;
	}
	public void setMemb5(Pokemon memb5) {
		this.memb5 = memb5;
	}
	public Pokemon[] getArray(){
		return partyArray;
	}
	
	
	public Pokemon getFromIndex(int i){
		return partyArray[i];
	}
	public int getIndexOf(Pokemon pkmn){
		return partyArrayList.indexOf(pkmn);
	}
}
