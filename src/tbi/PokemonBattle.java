package tbi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import pokemonFramework.Bundle;
import pokemonFramework.FuncDraw;
import pokemonFramework.Move;
import pokemonFramework.Party;
import pokemonFramework.Pokemon;
import pokemonFramework.Profile;
import pokemonFramework.Server;
import processing.core.PApplet;
import processing.core.PImage;

@SuppressWarnings("unused")
public class PokemonBattle extends PApplet {

	// Multiplayer Stuff
	private String ip = "localhost";

	private int port = 4563;
	private boolean sentInfo = false;
	private boolean recevedInfo = false;

	private Socket socket;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	private int errors = 0;
	private boolean unableToComm = false;

	public boolean playing = false;
	public boolean won = false;
	public boolean oppWon = false;

	Profile playerProfile = new Profile("GizmoTek", 0);
	PImage playerImage;
	Party playerParty = new Party(Pokemon.getPkmnInfo("7"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"),
			Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"));
	Pokemon playerPkmn = playerParty.getFromIndex(0);
	Move currMove;

	Profile oppProfile = new Profile("GizmoTek", 0);
	PImage oppImage;
	Party oppParty;
	Pokemon oppPkmn;
	Move oppCurrMove;

	Bundle clientBundle = new Bundle(playerProfile, playerParty);

	int health;
	int MaxHealth;
	int oppHealth;
	int oppMaxHealth;

	public int GUIScreen = 0;
	public int boxIn = 0;
	public String battleMessage;

	boolean waiting;
	String eol = System.getProperty("line.separator");

	List<Server> Ips = new ArrayList<Server>();

	// framework
	FuncDraw DrawFunc = new FuncDraw();
	// ServerFunc ServerFunc = new ServerFunc(in, out, socket, port);

	/*
	 * public static final void main(String[] args) {
	 * PApplet.main("PokemonBattle"); }
	 * 
	 * public void setup() { size(800, 600); Ips = findIPs(); }
	 */
	/*
	 * public void draw() { if (playing) { background(255, 235, 205);
	 * drawChars(); drawGui(); } else { background(Color.cyan.getRGB());
	 * drawGui(); } checkMouse(); update();
	 * 
	 * }
	 */

	/*
	 * public void update() { if (playing) { if
	 * (Double.parseDouble(playerPkmn.getcHP()) <= 0) { battleMessage =
	 * "You Win"; won = true;
	 * 
	 * } else if (Double.parseDouble(oppPkmn.getcHP()) <= 0) { battleMessage =
	 * "You Lose"; oppWon = true;
	 * 
	 * }
	 * 
	 * if (errors >= 10) { unableToComm = true; }
	 * 
	 * if (!unableToComm && sentInfo) { Object input = null;
	 * 
	 * try { while (in.readObject() == null) { } input = in.readObject(); }
	 * catch (IOException | ClassNotFoundException e) { e.printStackTrace();
	 * errors++; }
	 * 
	 * System.out.println(1);
	 * 
	 * if (input instanceof Pokemon) { oppPkmn = (Pokemon) input; } else if
	 * (input instanceof Move) { oppCurrMove = (Move) input; }
	 * System.out.println(2); }
	 * 
	 * if (sentInfo && recevedInfo) { if (Integer.parseInt(oppPkmn.getSpeed()) >
	 * Integer.parseInt(playerPkmn.getSpeed())) { oUseMove(1);
	 * pUseMove(currMove); } else { pUseMove(currMove); oUseMove(1); } } }
	 * 
	 * clientBundle = new Bundle(playerProfile, playerParty, currMove,
	 * oppProfile, oppParty, oppCurrMove);
	 * 
	 * }
	 */

	public void checkMouse() {
		if (playing) {
			if (mouseX > 450 && mouseX < 450 + 175 && mouseY > 450 && mouseY < 450 + 75) {
				boxIn = 1;
			} else if (mouseX > 450 && mouseX < 450 + 175 && mouseY > 525 && mouseY < 525 + 75) {
				boxIn = 2;
			} else if (mouseX > 625 && mouseX < 625 + 175 && mouseY > 450 && mouseY < 450 + 75) {
				boxIn = 3;
			} else if (mouseX > 625 && mouseX < 625 + 175 && mouseY > 525 && mouseY < 525 + 75) {
				boxIn = 4;
			} else {
				boxIn = 0;
			}
		} else {
			switch (GUIScreen) {
			case 0:
				if (mouseX > 50 && mouseX < 50 + 300 && mouseY > 475 && mouseY < 475 + 100) {
					boxIn = 1;
				} else if (mouseX > 450 && mouseX < 450 + 300 && mouseY > 475 && mouseY < 475 + 100) {
					boxIn = 2;
				} else {
					boxIn = 0;
				}
				break;
			case 1:
				if (mouseX > 10 && mouseX < 10 + 300 && mouseY > 100 && mouseY < 100 + 100) {
					boxIn = 1;
				} else if (mouseX > 10 && mouseX < 10 + 300 && mouseY > 250 && mouseY < 250 + 100) {
					boxIn = 2;
				} else {
					boxIn = 0;
				}
				break;
			}
		}
	}

	/*
	 * public void mousePressed() { if (playing) { switch (GUIScreen) { case 0:
	 * switch (boxIn) { case 1:
	 * 
	 * GUIScreen = 1; break; case 2: GUIScreen = 0; break; case 3: GUIScreen =
	 * 0; break; case 4: GUIScreen = 0; break; } break; case 1: switch (boxIn) {
	 * case 1: if (playerPkmn.getMove0().getcPp() >= 1) {
	 * pUseMove(playerPkmn.getMove0()); GUIScreen = 0; } break; case 2: if
	 * (playerPkmn.getMove1().getcPp() >= 1) { pUseMove(playerPkmn.getMove1());
	 * GUIScreen = 0; } break; case 3: if (playerPkmn.getMove2().getcPp() >= 1)
	 * { pUseMove(playerPkmn.getMove2()); GUIScreen = 0; } break; case 4: if
	 * (playerPkmn.getMove3().getcPp() >= 1) { pUseMove(playerPkmn.getMove3());
	 * GUIScreen = 0; } break; } break; } } else { switch (GUIScreen) { case 0:
	 * switch (boxIn) { case 1: System.out.println("Hai"); ip =
	 * Ips.get(0).getServerip().getHostAddress(); // setupOnline(); GUIScreen =
	 * 1; break; case 2: GUIScreen = 0; break; } break;
	 * 
	 * case 1: switch (boxIn) { case 1: GUIScreen = 2; break; case 2: GUIScreen
	 * = 0; break; } break; } }
	 * 
	 * }
	 */

	/*
	 * public void pUseMove(Move move) { float damage = move.getDamage(move,
	 * playerPkmn, oppPkmn);
	 * 
	 * if (damage == 0) { battleMessage = playerPkmn.getName() +
	 * " Failed To Use " + move.getName().toString(); } else { battleMessage =
	 * playerPkmn.getName() + " Used " + move.getName().toString(); }
	 * oppPkmn.setcHP((int) Double.parseDouble((oppPkmn.getcHP())) -
	 * move.getDamage(move, playerPkmn, oppPkmn)); move.cPp--; //
	 * ServerFunc.sendMove(clientBundle, move); oUseMove(1); draw(); }
	 * 
	 * private void oUseMove(int user) { Move oppMove; if (user == 0) { if
	 * (oppPkmn.getMove0().getAverage() >= oppPkmn.getMove1().getAverage() &&
	 * oppPkmn.getMove0().getAverage() >= oppPkmn.getMove2().getAverage() &&
	 * oppPkmn.getMove0().getAverage() >= oppPkmn.getMove3().getAverage()) {
	 * oppMove = oppPkmn.getMove0(); } else if (oppPkmn.getMove1().getAverage()
	 * >= oppPkmn.getMove0().getAverage() && oppPkmn.getMove1().getAverage() >=
	 * oppPkmn.getMove2().getAverage() && oppPkmn.getMove1().getAverage() >=
	 * oppPkmn.getMove3().getAverage()) { oppMove = oppPkmn.getMove1(); } else
	 * if (oppPkmn.getMove2().getAverage() >= oppPkmn.getMove0().getAverage() &&
	 * oppPkmn.getMove2().getAverage() >= oppPkmn.getMove2().getAverage() &&
	 * oppPkmn.getMove1().getAverage() >= oppPkmn.getMove3().getAverage()) {
	 * oppMove = oppPkmn.getMove2(); } else { oppMove = oppPkmn.getMove3(); } }
	 * else { oppMove = oppCurrMove; } float damage = oppMove.getDamage(oppMove,
	 * oppPkmn, playerPkmn);
	 * 
	 * if (damage == 0) { battleMessage = battleMessage + eol + "and" + eol +
	 * oppPkmn.getName() + " Failed To Use " + oppMove.getName().toString(); }
	 * else { battleMessage = battleMessage + eol + "and" + eol +
	 * oppPkmn.getName() + " Used " + oppMove.getName().toString(); }
	 * oppPkmn.setcHP((int) Double.parseDouble(oppPkmn.getcHP()) - damage);
	 * oppMove.cPp--; }
	 */

	/************* Drawing Gui *****************/

	public void drawChars() {
		image(playerImage, 50, 150, 450, 450);
		image(oppImage, 400, -100, 450, 450);
	}

	/*
	 * public void drawGui() { if (playing) { DrawFunc.drawRect(0, 450, 450,
	 * 150, Color.white); DrawFunc.drawRect(450, 450, 175, 75, Color.red);
	 * DrawFunc.drawRect(450, 525, 175, 75, Color.green); DrawFunc.drawRect(625,
	 * 450, 175, 75, Color.yellow); DrawFunc.drawRect(625, 525, 175, 75,
	 * Color.blue); DrawFunc.drawRect(450, 250, 350, 150, Color.lightGray);
	 * DrawFunc.drawRect(0, 50, 350, 150, Color.lightGray);
	 * DrawFunc.drawHealth(0, playerPkmn); DrawFunc.drawHealth(1, oppPkmn); }
	 * else { switch (GUIScreen) { case 0: DrawFunc.drawRect(50, 475, 300, 100,
	 * Color.gray, "Play", 40); DrawFunc.drawRect(450, 475, 300, 100,
	 * Color.gray, "PC", 40); break; case 1: DrawFunc.drawRect(10, 100, 300,
	 * 100, Color.gray); DrawFunc.drawRect(10, 250, 300, 100, Color.gray);
	 * break; } } drawText(); }
	 */

	public void drawText() {
		textSize(20);
		fill(50);
		textAlign(CENTER, CENTER);

		if (playing) {
			text(battleMessage, 0, 450, 450, 150);
			text(playerPkmn.getcHP() + " / " + playerPkmn.getHP(), 550, 350, 200, 50);
			textSize(32);
			text(playerPkmn.getName(), 500, 260, 200, 50);
			text(oppPkmn.getName(), 50, 75, 200, 50);

			switch (GUIScreen) {
			case 0:
				text("Fight", 450, 450, 175, 75);
				text("Pokemon", 450, 525, 175, 75);
				text("Bag", 625, 450, 175, 75);
				text("Run", 625, 525, 175, 75);
				break;
			case 1:
				textSize(20);
				text(playerPkmn.getMove0().getName() + "    " + playerPkmn.getMove0().getcPp() + "/"
						+ playerPkmn.getMove0().getPp() + " PP", 450, 450, 175, 75);
				text(playerPkmn.getMove1().getName() + "    " + playerPkmn.getMove1().getcPp() + "/"
						+ playerPkmn.getMove1().getPp() + " PP", 450, 525, 175, 75);
				text(playerPkmn.getMove2().getName() + "    " + playerPkmn.getMove2().getcPp() + "/"
						+ playerPkmn.getMove2().getPp() + " PP", 625, 450, 175, 75);
				text(playerPkmn.getMove3().getName() + "    " + playerPkmn.getMove3().getcPp() + "/"
						+ playerPkmn.getMove3().getPp() + " PP", 625, 525, 175, 75);
				break;
			}
		} else {
			switch (GUIScreen) {
			case 0:
				textSize(75);
				textAlign(LEFT, TOP);
				text("Pokemon" + eol + "Battle Sim", 25, 25, 400, 400);
				break;
			case 1:
				break;
			}
		}
	}

	/*
	 * public void setupOnline() { FuncServer.connect(ip, port);
	 * FuncServer.sendData(clientBundle);
	 * System.out.println(playerPkmn.getName());
	 * System.out.println(oppPkmn.getName()); battleMessage =
	 * oppProfile.getName() + "'s " + oppPkmn.getName() + " Apeared"; health =
	 * Integer.parseInt(playerPkmn.getcHP()); MaxHealth =
	 * Integer.parseInt(playerPkmn.getHP()); oppHealth =
	 * Integer.parseInt(oppPkmn.getcHP()); oppMaxHealth =
	 * Integer.parseInt(oppPkmn.getHP()); playerImage = loadImage("Sprites/" +
	 * playerPkmn.getName() + "_back.png"); oppImage = loadImage("Sprites/" +
	 * oppPkmn.getName() + "_front.png"); System.out.println("Loading Done!");
	 * playing = true; }
	 */
}