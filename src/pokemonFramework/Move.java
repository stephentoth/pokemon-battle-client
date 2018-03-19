package pokemonFramework;

import java.io.Serializable;
import java.util.Random;

public class Move implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4173378680595472320L;
	// 1,Pound,Normal,Physical,35,40,100%

	private String id;
	private String name;
	private String type;
	private String moveType;
	private int pp;
	public int cPp;
	private int power;
	private String acc;

	public Move(String id, String name, String type, String moveType, int pp, int cPp, int power, String acc) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.moveType = moveType;
		this.cPp = cPp;
		this.pp = pp;
		this.power = power;
		this.acc = acc;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		this.pp = pp;
	}

	public int getcPp() {
		return cPp;
	}

	public void setcPp(int cPp) {
		this.cPp = cPp;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public static Move getMoveInfo(String number) {

		int pkmnIndex = ReadCSV.read("moves.csv")[0].indexOf(number);
		if (ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString().equals("")
				&& ReadCSV.read("moves.csv")[5].get(pkmnIndex).toString().equals("")) {
			return new Move(ReadCSV.read("moves.csv")[0].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[1].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[2].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[3].get(pkmnIndex).toString(), Integer.MAX_VALUE, Integer.MAX_VALUE, 0,
					ReadCSV.read("moves.csv")[6].get(pkmnIndex).toString());
		} else if (ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString().equals("")) {
			return new Move(ReadCSV.read("moves.csv")[0].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[1].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[2].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[3].get(pkmnIndex).toString(), Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.parseInt(ReadCSV.read("moves.csv")[5].get(pkmnIndex).toString()),
					ReadCSV.read("moves.csv")[6].get(pkmnIndex).toString());
		} else if (ReadCSV.read("moves.csv")[5].get(pkmnIndex).toString().equals("")) {
			return new Move(ReadCSV.read("moves.csv")[0].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[1].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[2].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[3].get(pkmnIndex).toString(),
					Integer.parseInt(ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString()),
					Integer.parseInt(ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString()), 0,
					ReadCSV.read("moves.csv")[6].get(pkmnIndex).toString());
		} else {
			return new Move(ReadCSV.read("moves.csv")[0].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[1].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[2].get(pkmnIndex).toString(),
					ReadCSV.read("moves.csv")[3].get(pkmnIndex).toString(),
					(int) Double.parseDouble((ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString())),
					(int) Double.parseDouble((ReadCSV.read("moves.csv")[4].get(pkmnIndex).toString())),
					(int) Double.parseDouble((ReadCSV.read("moves.csv")[5].get(pkmnIndex).toString())),
					ReadCSV.read("moves.csv")[6].get(pkmnIndex).toString());
		}

	}

	public float getDamage(Move move, Pokemon play1, Pokemon play2) {
		Random rand = new Random();
		float d;
		if (rand.nextInt(100) <= Integer.parseInt(move.getAcc())) {
			d = (50f / 250f) * (Float.parseFloat(play1.getAttack()) / Float.parseFloat(play2.getDefense()))
					* move.getPower();
		} else {
			d = 0;
		}
		return d;
	}
	
	public float getBaseDamage(Move move, Pokemon play1, Pokemon play2) {
		float d = (50f / 250f) * (Float.parseFloat(play1.getAttack()) / Float.parseFloat(play2.getDefense()))
					* move.getPower();
		return d;
	}

	public float getAverage() {
		return (cPp + power + Integer.parseInt(acc)) / 3;
	}

}
