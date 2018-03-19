package pokemonFramework;

import java.awt.Color;
import java.awt.Point;

public class Text {
	private Point loc;
	private int font;
	private Color col;
	private String txt;
	private boolean hid;

	public Text(Point loc, int font, Color col, String txt, boolean hid) {
		super();
		this.loc = loc;
		this.font = font;
		this.col = col;
		this.txt = txt;
		this.hid = hid;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public int getFont() {
		return font;
	}

	public void setFont(int font) {
		this.font = font;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public boolean isHid() {
		return hid;
	}

	public void setHid(boolean hid) {
		this.hid = hid;
	}
}
