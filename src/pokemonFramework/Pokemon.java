// #,Pokemon,HP,Attack,Defense,Sp. Attack,Sp. Defense,Speed,Total,Ability,Move1,Move2,Move3,Move4
//#,Name,Type,Category,Contest,PP,Power,Accuracy,Gen
package pokemonFramework;

import java.io.Serializable;

public class Pokemon implements Serializable{
	private static final long serialVersionUID = -725146924209724780L;	
	
	private String id;
	private String name;
	private String HP;
	private String cHP;
	private String attack;
	private String defense;
	private String spAttack;
	private String spDefense;
	private String speed;
	private String baseStatTotal;

	private String ability; // see csv file NUMBER

	private Move move0; // Numbers
	private Move move1;
	private Move move2;
	private Move move3;

	public Pokemon(String id, String name, String hP, String cHP, String attack, String defense, String spAttack,
			String spDefense, String speed, String baseStatTotal, String ability, Move move0, Move move1, Move move2,
			Move move3) {
		this.id = id;
		this.name = name;
		this.HP = hP;
		this.cHP = hP;
		this.attack = attack;
		this.defense = defense;
		this.spAttack = spAttack;
		this.spDefense = spDefense;
		this.speed = speed;
		this.baseStatTotal = baseStatTotal;
		this.ability = ability;
		this.move0 = move0;
		this.move1 = move1;
		this.move2 = move2;
		this.move3 = move3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHP() {
		return HP;
	}

	public void setHP(String hP) {
		this.HP = hP;
	}

	public String getcHP() {
		return cHP;
	}

	public void setcHP(String cHP) {
		this.cHP = cHP;
	}
	
	public void setcHP(float cHP) {
		this.cHP = String.valueOf(cHP);
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public String getDefense() {
		return defense;
	}

	public void setDefense(String defense) {
		this.defense = defense;
	}

	public String getSpAttack() {
		return spAttack;
	}

	public void setSpAttack(String spAttack) {
		this.spAttack = spAttack;
	}

	public String getSpDefense() {
		return spDefense;
	}

	public void setSpDefense(String spDefense) {
		this.spDefense = spDefense;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getBaseStatTotal() {
		return baseStatTotal;
	}

	public void setBaseStatTotal(String baseStatTotal) {
		this.baseStatTotal = baseStatTotal;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public Move getMove0() {
		return move0;
	}

	public void setMove0(Move move0) {
		this.move0 = move0;
	}

	public Move getMove1() {
		return move1;
	}

	public void setMove1(Move move1) {
		this.move1 = move1;
	}

	public Move getMove2() {
		return move2;
	}

	public void setMove2(Move move2) {
		this.move2 = move2;
	}

	public Move getMove3() {
		return move3;
	}

	public void setMove3(Move move3) {
		this.move3 = move3;
	}

	// FROM NUMBER!!! \/
	public static Pokemon getPkmnInfo(String number) {
		int pkmnIndex = ReadCSV.read("pokemon.csv")[0].indexOf(number);
		return new Pokemon(ReadCSV.read("pokemon.csv")[0].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[1].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[2].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[2].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[3].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[4].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[5].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[6].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[7].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[8].get(pkmnIndex).toString(),
				ReadCSV.read("pokemon.csv")[9].get(pkmnIndex).toString(),
				Move.getMoveInfo(ReadCSV.read("pokemon.csv")[10].get(pkmnIndex).toString()),
				Move.getMoveInfo(ReadCSV.read("pokemon.csv")[11].get(pkmnIndex).toString()),
				Move.getMoveInfo(ReadCSV.read("pokemon.csv")[12].get(pkmnIndex).toString()),
				Move.getMoveInfo(ReadCSV.read("pokemon.csv")[13].get(pkmnIndex).toString()));
	}

}