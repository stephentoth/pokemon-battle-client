package pokemonFramework;

import java.awt.Dimension;
import java.awt.Point;

import processing.core.PImage;

public class Image {
	private Point loc;
	private Dimension dim;
	private boolean hid;
	private PImage img;

	public Image(Point loc, Dimension dim, PImage img, boolean hid) {
		super();
		this.loc = loc;
		this.dim = dim;
		this.hid = hid;
		this.img = img;
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

	public boolean isHid() {
		return hid;
	}

	public void setHid(boolean hid) {
		this.hid = hid;
	}

	public PImage getImg() {
		return img;
	}

	public void setImg(PImage img) {
		this.img = img;
	}

}
