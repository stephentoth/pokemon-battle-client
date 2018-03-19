package pokemonFramework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Button {
	private Point loc;
	private Dimension dim;
	private Color col;
	private String nam;
	private String act;
	private String var;
	private String val;
	private Server srv;

	public Byte State; // 0-Nothing 1-MouseOver 2-MouseDown

	public Button(Point loc, Dimension dim, Color col, String nam, String act, String var, String val, Server srv) {
		this.loc = loc;
		this.dim = dim;
		this.col = col;
		this.nam = nam;
		this.act = act;
		this.var = var;
		this.val = val;
		this.srv = srv;
	}
	
	
	public Button(Point loc, Dimension dim, Color col, String nam, String act, String var, String val) {
		this.loc = loc;
		this.dim = dim;
		this.col = col;
		this.nam = nam;
		this.act = act;
		this.var = var;
		this.val = val;
	}

	public Button(Point loc, Dimension dim, Color col, String nam, String act) {
		this.loc = loc;
		this.dim = dim;
		this.col = col;
		this.nam = nam;
		this.act = act;
	}

	public Button(Point loc, Dimension dim, Color col, String nam) {
		super();
		this.loc = loc;
		this.dim = dim;
		this.col = col;
		this.nam = nam;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}


	public Server getSrv() {
		return srv;
	}


	public void setSrv(Server srv) {
		this.srv = srv;
	}
}
