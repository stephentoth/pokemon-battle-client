package pokemonBattleClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import pokemonFramework.*;
import processing.core.PApplet;
import processing.core.PImage;

public class PokemonBattleClient extends PApplet {
	// ***************ClientSide Vars
	private byte guiScreen = -128; // Negitive - Menu / Positive - Game // -128
									// to 127
	/*
	 * -128 - Main Menu -127 - Server List -126 - -125 - Server Lobby -124 - 1 -
	 * Game Main
	 * 
	 */
	public String clientState = "mMenu";
	private short boxIn;
	
	private int frame = 0;

	// ***************Server Vars
	private String ip = "localhost";
	public static ArrayList<Server> ipList = new ArrayList<Server>(255);
	private boolean looked = false;

	private Server connectedServer = null;

	public static int port = 4563;

	private boolean sentInfo = false;
	private boolean recevedInfo = false;

	public static Socket socket;
	public static ObjectOutputStream out = null;
	public static ObjectInputStream in = null;

	private int errors = 0;
	private boolean unableToComm = false;

	private boolean playing = false;
	public boolean won = false;
	public boolean oppWon = false;

	Profile playerProfile = new Profile("GizmoTek", 3);
	PImage playerImage;
	Party playerParty = new Party(Pokemon.getPkmnInfo("7"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"), Pokemon.getPkmnInfo("0"));
	Pokemon playerPkmn = playerParty.getFromIndex(0);
	Move currMove;

	Profile oppProfile = new Profile("GizmoTek", 3);
	PImage oppImage;
	Party oppParty;
	Pokemon oppPkmn;
	Move oppCurrMove;

	static Profile[] serverPlayers = null;

	Bundle clientBundle = new Bundle(playerProfile, playerParty);

	int health;
	int MaxHealth;
	int oppHealth;
	int oppMaxHealth;
	public String battleMessage;

	boolean waiting;
	String eol = System.getProperty("line.separator");

	// MISC

	ArrayList<Text> texts = new ArrayList<Text>(128);
	ArrayList<Image> images = new ArrayList<Image>(128);
	ArrayList<Button> buttons = new ArrayList<Button>(128);

	List<Server> Ips = new ArrayList<Server>();

	ServerFindertm serverFinder = new ServerFindertm();

	/**************************************************/
	public static final void main(String[] args) {
		PApplet.main("pokemonBattleClient.PokemonBattleClient");

	}

	public void settings() {
		size(800, 600);

	}

	public void setup() {
		playerPkmn = playerParty.getFromIndex(0);

	}

	public void draw() {
		update();// TAG You're IT
		drawGui();
		checkMouse();
	}

	private void update() {
		

		switch (clientState) {
		case "mMenu":
			guiScreen = -128;
			playing = false;
			break;
		case "inGame":
			if (playing) {
				if (guiScreen < 0) {
					guiScreen = 0;
				}
			} else {
				if (guiScreen > 0) {
					guiScreen = -128;
				}
			}
			break;
		case "seekingServer":
			guiScreen = -127;
			drawGui();
			if (looked == false) serverFinder.start();
			looked = true;
			clientState = "selectServer";
			break;
		case "selectServer":
			guiScreen = -127;
			if (connectedServer != null) clientState = "serverLobby";
			break;
		case "serverLobby":
			guiScreen = -125;
			//RTServerManager.run();

			break;

		}

		/***
		 * Playing The Game
		 ***/
		if (clientState == "inGame" && playing) {
			if (Double.parseDouble(playerPkmn.getcHP()) <= 0) {
				battleMessage = "You Win";
				won = true;

			} else
				if (Double.parseDouble(oppPkmn.getcHP()) <= 0) {
					battleMessage = "You Lose";
					oppWon = true;

				}

			/*
			 * if (!unableToComm && sentInfo) { Object input = null;
			 * 
			 * try { while (in.readObject() == null) { } input = in.readObject(); } catch
			 * (IOException | ClassNotFoundException e) { e.printStackTrace(); errors++; }
			 * 
			 * if (input instanceof Pokemon) { oppPkmn = (Pokemon) input; } else if (input
			 * instanceof Move) { oppCurrMove = (Move) input; } }
			 */

			/*
			 * if (sentInfo && recevedInfo) { if (Integer.parseInt(oppPkmn.getSpeed()) >
			 * Integer.parseInt(playerPkmn.getSpeed())) { oUseMove(1); pUseMove(currMove); }
			 * else { pUseMove(currMove); oUseMove(1); } }
			 */
		}

		/*
		 * if (errors >= 10) { unableToComm = true; }
		 */

		clientBundle = new Bundle(playerProfile, playerParty, currMove, oppProfile, oppParty, oppCurrMove);
	}

	private void drawGui() {
		readyGui();
		drawBackground();
		drawButtons();
		drawText();
		frame++;
	}

	private void readyGui() {
		buttons.clear();
		images.clear();
		texts.clear();
		switch (guiScreen) {
		case -128:
			buttons.add(new Button(new Point(50, 475), new Dimension(300, 100), Color.gray, "Play", "changeVar", "clientState", "seekingServer"));
			buttons.add(new Button(new Point(450, 475), new Dimension(300, 100), Color.gray, "PC"));
			break;
		case -127:
			texts.add(new Text(new Point(50, 75), 60, Color.black, "Servers", false));
			if (!ipList.isEmpty()) serverButtons();
			// buttons.add(new Button(new Point(50, 475), new Dimension(300, 100),
			// Color.gray, ""));
			buttons.add(new Button(new Point(450, 475), new Dimension(300, 100), Color.gray, "Back", "changeVar", "clientState", "mMenu"));
			break;
		case -126:
		case -125:
			texts.add(new Text(new Point(50, 75), 60, Color.black, "Lobby:", false));
			texts.add(new Text(new Point(250, 75), 30, Color.black, connectedServer.getServerName(), false));
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			break;
		}

	}

	private void checkMouse() {
		for (int i = 0; i < buttons.size(); i++) {
			Button currButton = buttons.get(i);
			if (mouseX > currButton.getLoc().x && mouseX < currButton.getLoc().x + currButton.getDim().getWidth() && mouseY > currButton.getLoc().y && mouseY < currButton.getLoc().y + currButton.getDim().getHeight()) {
				currButton.State = 1;
				drawTextMouse(currButton, mouseX + 10, mouseY);
			}
		}
	}

	public void mousePressed() {
		for (int i = 0; i < buttons.size(); i++) {
			Button currButton = buttons.get(i);

			if (mouseX > currButton.getLoc().x && mouseX < currButton.getLoc().x + currButton.getDim().getWidth() && mouseY > currButton.getLoc().y && mouseY < currButton.getLoc().y + currButton.getDim().getWidth()) {
				currButton.State = 2;

				switch (currButton.getAct()) {
				case "changeVar":
					switch (currButton.getVar()) {
					case "guiScreen":
						guiScreen = (byte) Integer.parseInt(currButton.getVal());
						break;
					case "playing":
						playing = Boolean.parseBoolean(currButton.getVal());
						break;
					case "clientState":
						clientState = currButton.getVal();
						break;
					}
					break;
				case "connect":
					connect(currButton.getSrv().getServerip().getHostAddress(), port);
					break;
				}
			}
		}

		/*
		 * textSize(32); fill(50); textAlign(LEFT); text("Please Wait.", 0, 0, 100,
		 * 100);// TAG
		 */
	}

	/************************
	 * Dynamic Server GUI
	 *************************************/

	public void serverButtons() {
		// buttons.add(new Button(new Point(50, (100 + 20), new Dimension(500, 75),
		// Color.gray, currServer.getServerName(), "createServer", "", ""));
		for (int i = 0; i < ipList.size(); i++) {
			Server currServer = ipList.get(i);
			buttons.add(new Button(new Point(50, (100 + (20 * i + 1))), new Dimension(500, 75), Color.gray, currServer.getServerName(), "connect", "", "", currServer));
		}
	}

	public void serverPlayers() {
		// -125
		// buttons.add(new Button(new Point(50, (100 + 20), new Dimension(500, 75),
		// Color.gray, currServer.getServerName(), "createServer", "", ""));
		for (int i = 0; i < serverPlayers.length; i++) {
			if (serverPlayers[i] != null) {
				Profile currProfile = serverPlayers[i];
				buttons.add(new Button(new Point(50, (100 + (20 * i + 1))), new Dimension(500, 75), Color.gray, currProfile.getName()));

			}
		}
	}

	/************************ Drawing *************************************/

	private void drawBackground() {
		background(Color.cyan.getRGB());
	}

	private void drawRect(float xPos, float yPos, float width, float height, Color color) {
		fill(color.getRGB());
		rect(xPos, yPos, width, height);
	}

	private void drawRect(float xPos, float yPos, float width, float height, Color color, String text) {
		fill(color.getRGB());
		rect(xPos, yPos, width, height);
		textSize(32);
		fill(50);
		textAlign(CENTER, CENTER);
		text(text, xPos, yPos, width, height);
	}

	private void drawRect(float xPos, float yPos, float width, float height, Color color, String text, int size) {
		fill(color.getRGB());
		rect(xPos, yPos, width, height);
		textSize(size);
		fill(50);
		textAlign(CENTER, CENTER);
		text(text, xPos, yPos, width, height);
	}

	private void drawHealth(int pNum, Pokemon person) {

		float health;
		if (Float.parseFloat(person.getcHP()) < 0) {
			health = 0;
		} else {
			health = Float.parseFloat(person.getcHP());
		}
		float MaxHealth = Float.parseFloat(person.getHP());

		if (pNum == 1) {
			noStroke();
			float drawWidth = (health / MaxHealth) * 200;
			if (health <= (MaxHealth / 4)) {
				drawRect(90, 150, drawWidth, 25, Color.red);
			} else
				if (health <= (MaxHealth / 2)) {
					drawRect(90, 150, drawWidth, 25, Color.orange);
				} else {
					drawRect(90, 150, drawWidth, 25, Color.green);
				}

			// Outline
			stroke(0);
			noFill();
			rect(90, 150, 200, 25);
		} else
			if (pNum == 0) {

				if (health < MaxHealth / 4) {
					fill(255, 0, 0);
				} else
					if (health < MaxHealth / 2) {
						fill(255, 200, 0);
					} else {
						fill(0, 255, 0);
					}

				noStroke();
				// Get fraction 0->1 and multiply it by width of bar
				float drawWidth = (health / MaxHealth) * 200;
				rect(550, 325, drawWidth, 25);

				// Outline
				stroke(0);
				noFill();
				rect(550, 325, 200, 25);
			}

	}

	private void drawButtons() {
		for (int i = 0; i < buttons.size(); i++) {
			Button currButton = buttons.get(i);
			int font = (currButton.getDim().height - currButton.getNam().length()) / 2;
			drawRect(currButton.getLoc().x, currButton.getLoc().y, currButton.getDim().width, currButton.getDim().height, currButton.getCol(), currButton.getNam(), font);
		}
	}

	private void drawImages() {
		for (int i = 0; i < this.images.size(); i++) {
			Image currImage = this.images.get(i);
			this.image(currImage.getImg(), currImage.getLoc().x, currImage.getLoc().y, currImage.getDim().width, currImage.getDim().height);
		}
	}

	private void drawTextMouse(Button currButton, int x, int y) {
		textSize(15);
		fill(50);
		textAlign(LEFT);
		text(currButton.getNam(), x, y);

	}

	private void drawText() {
		for (int i = 0; i < this.texts.size(); i++) {
			Text currText = this.texts.get(i);
			this.textSize(currText.getFont());
			this.fill(currText.getCol().getRGB());
			this.textAlign(LEFT);
			this.text(currText.getTxt(), currText.getLoc().x, currText.getLoc().y);
			this.fill(50);
		}

	}

	/************************ Gameplay *************************************/

	public void pUseMove(Move move) {
		float damage = move.getDamage(move, playerPkmn, oppPkmn);

		if (damage == 0) {
			battleMessage = playerPkmn.getName() + " Failed To Use " + move.getName().toString();
		} else {
			battleMessage = playerPkmn.getName() + " Used " + move.getName().toString();
		}
		oppPkmn.setcHP((int) Double.parseDouble((oppPkmn.getcHP())) - move.getDamage(move, playerPkmn, oppPkmn));
		move.cPp--;
		// ServerFunc.sendMove(clientBundle, move);
		oUseMove(1);
		draw();
	}

	private void oUseMove(int user) {
		Move oppMove;
		if (user == 0) {
			if (oppPkmn.getMove0().getAverage() >= oppPkmn.getMove1().getAverage() && oppPkmn.getMove0().getAverage() >= oppPkmn.getMove2().getAverage() && oppPkmn.getMove0().getAverage() >= oppPkmn.getMove3().getAverage()) {
				oppMove = oppPkmn.getMove0();
			} else
				if (oppPkmn.getMove1().getAverage() >= oppPkmn.getMove0().getAverage() && oppPkmn.getMove1().getAverage() >= oppPkmn.getMove2().getAverage() && oppPkmn.getMove1().getAverage() >= oppPkmn.getMove3().getAverage()) {
					oppMove = oppPkmn.getMove1();
				} else
					if (oppPkmn.getMove2().getAverage() >= oppPkmn.getMove0().getAverage() && oppPkmn.getMove2().getAverage() >= oppPkmn.getMove2().getAverage() && oppPkmn.getMove1().getAverage() >= oppPkmn.getMove3().getAverage()) {
						oppMove = oppPkmn.getMove2();
					} else {
						oppMove = oppPkmn.getMove3();
					}
		} else {
			oppMove = oppCurrMove;
		}
		float damage = oppMove.getDamage(oppMove, oppPkmn, playerPkmn);

		if (damage == 0) {
			battleMessage = battleMessage + eol + "and" + eol + oppPkmn.getName() + " Failed To Use " + oppMove.getName().toString();
		} else {
			battleMessage = battleMessage + eol + "and" + eol + oppPkmn.getName() + " Used " + oppMove.getName().toString();
		}
		oppPkmn.setcHP((int) Double.parseDouble(oppPkmn.getcHP()) - damage);
		oppMove.cPp--;
	}

	/************************ SERVER ******************************/
	/*
	 * public List<Server> findIPs() { List<Server> ips = new ArrayList<Server>();
	 * for (int i = 0; i <= 255; i++) { try { socket = new Socket();
	 * socket.connect(new InetSocketAddress("192.168.1." + i, port), 50); out = new
	 * ObjectOutputStream(socket.getOutputStream()); in = new
	 * ObjectInputStream(socket.getInputStream()); ips.add(getServerInfo());
	 * System.out.println("Yep"); socket.close(); socket = null; } catch
	 * (IOException e) { socket = null; } } return ips; }
	 */

	public void connect(String Ip, int Port) {
		try {
			socket = new Socket(Ip, Port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("No I/O");
		}
		sendData(clientBundle);
		System.out.println("Sent profile data");
		// wait for confirmation
		Object obj = getResponse();
		if (obj instanceof Server) {
			System.out.println("INSTANCE OF SERVER");
			connectedServer = (Server) obj;
		}

	}

	public Server getServerInfo() {
		Object input = null;
		sendData(new String("info"));
		Object obj = getResponse();
		if (obj instanceof Server) {
			System.out.println("INSTANCE OF SERVER");
			connectedServer = (Server) obj;
		}
		return (Server) input;
	}
	
	public void updateClientInfo() {
		Object input = null;
		sendData(new String("info"));
		Object obj = getResponse();
		if (obj instanceof Bundle) {
			if (((Bundle)obj).getType() == 3)
			System.out.println("INSTANCE OF Player Bundle");
			serverPlayers = ((Bundle)obj).getProfiles();
		}
	}

	public Object getResponse() {
		Object input = null;
		try {
			input = in.readObject();
			while (input == null) {
				input = in.readObject();
			}
			System.out.println(input.getClass().getName() + " Receved from " + socket.getInetAddress().getHostAddress());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			System.out.println("response failed");
		}
		return input;
	}

	public void sendMove(Bundle clientBundle, Move move) {
		System.out.println("Sending Client Bundle");
		sendData(clientBundle);
	}

	// data

	public void sendData(String send) {
		try {
			out.writeObject(new String(send));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendData(Bundle bundle) {
		try {
			out.writeObject(bundle);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ServerFindertm extends Thread {
	// replaces findIPs()
	// is un-blocked

	public Socket socket;
	public ObjectOutputStream out = null;
	public ObjectInputStream in = null;

	ServerFindertm() {
	}

	public void run() {
		Object input = null;
		for (int i = 0; i <= 255; i++) {
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress("192.168.1." + i, PokemonBattleClient.port), 50);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				out.writeObject(new String("info"));
				out.flush();
				while (input == null || !(input instanceof Server)) {
					input = in.readObject();
				}
				PokemonBattleClient.ipList.add((Server) input);
				System.out.println("Found server: " + ((Server) input).getServerName());
				socket.close();
				socket = null;
			} catch (IOException | ClassNotFoundException e) {
				socket = null;
			}
		}
	}
}