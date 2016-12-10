package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.Graphics;

/**
 * class to define geometric objects in 2D
 * @author Keks0r
 */
public class GeoObjekt {
	/** the width of the object on the x-axis **/
	double xlaenge; 
	/** the height of the object on the y-axis **/
	double ylaenge;
	/** the upper-left edge as a Vertex point **/
	Vertex loecke;
	/** a Vertex to set the movement of the object **/
	Vertex beweg = new Vertex(0.0, 0.0);
	java.util.Random ra = new java.util.Random();
	/** the color of this geometric object **/
	Color col;

	/**
	 * @param xlaenge {@link GeoObjekt#xlaenge xlaenge}
	 * @param ylaenge {@link GeoObjekt#ylaenge ylaenge}
	 * @param loecke {@link GeoObjekt#loecke loecke}
	 * @param col {@link GeoObjekt#col col} - optional
	 */
	public GeoObjekt(double xlaenge, double ylaenge, Vertex loecke, Color col) {
		this.loecke = loecke;
		if (xlaenge < 0) {
			this.loecke.xwert = this.loecke.xwert + xlaenge;
			this.xlaenge = xlaenge * (-1);
		} else {
			this.xlaenge = xlaenge;
		}
		if (ylaenge < 0) {
			this.loecke.ywert = this.loecke.ywert + ylaenge;
			this.ylaenge = ylaenge * (-1);
		} else {
			this.ylaenge = ylaenge;
		}
		this.col = col;
	}
	
	/**
	 * @param xlaenge {@link GeoObjekt#xlaenge xlaenge}
	 * @param ylaenge {@link GeoObjekt#ylaenge ylaenge}
	 * @param loecke {@link GeoObjekt#loecke loecke}
	 */
	public GeoObjekt(double xlaenge, double ylaenge, Vertex loecke) {
		this.loecke = loecke;
		if (xlaenge < 0) {
			this.loecke.xwert = this.loecke.xwert + xlaenge;
			this.xlaenge = xlaenge * (-1);
		} else {
			this.xlaenge = xlaenge;
		}
		if (ylaenge < 0) {
			this.loecke.ywert = this.loecke.ywert + ylaenge;
			this.ylaenge = ylaenge * (-1);
		} else {
			this.ylaenge = ylaenge;
		}
		this.col = new Color(50, 10, 60);
	}
	
	@Override
	public String toString() {
		return "Breite: " + xlaenge + " Hoehe: " + ylaenge + " linker-oberer Eckpunkt: " + loecke.xwert + ", " + loecke.ywert;
	}
	
	/**
	 * movement-method of this geometric object determined by {@link GeoObjekt#beweg beweg}
	 */
	public void move() {
		this.loecke.xwert = this.loecke.xwert + this.beweg.xwert;
		this.loecke.ywert = this.loecke.ywert + this.beweg.ywert;
	}
	
	/**
	 * sets the {@link GeoObjekt#loecke loecke} of this object to the Vertex neu
	 * @param neu new {@link GeoObjekt#loecke loecke}
	 */
	void verschiebe(Vertex neu) {
		this.loecke.xwert = neu.xwert;
		this.loecke.ywert = neu.ywert;
	}
	
	
	/**
	 * does the same as {@link GeoObjekt#move move}
	 * @param um Vertex defining the amount {@link GeoObjekt#loecke loecke} is moved at the x- and y-axis
	 */
	void bewegeum(Vertex um) {
		this.loecke.xwert = this.loecke.xwert + um.xwert;
		this.loecke.ywert = this.loecke.ywert + um.ywert;
	}
	
	/**
	 * calculates the area of this object
	 * @return the area contained by this object
	 */
	double area() {
		return (this.xlaenge * this.ylaenge);
	}
	
	/**
	 * checks if dot is inside the area of this geometric object
	 * @param dot
	 * @return true if dot is inside - otherwise false
	 */
	boolean hasWithin(Vertex dot) {
		if ((dot.xwert >= this.loecke.xwert) && (dot.xwert <= (this.loecke.xwert + this.xlaenge))) {
			if ((dot.ywert >= this.loecke.ywert) && (dot.ywert <= (this.loecke.ywert + this.ylaenge))) {			
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean equals(Object that) {
		if (that instanceof GeoObjekt) {
			GeoObjekt other = (GeoObjekt) that;
			return ((this.loecke.xwert == other.loecke.xwert) && (this.loecke.ywert == other.loecke.ywert) &&
					(this.xlaenge == other.xlaenge) && (this.ylaenge == other.ylaenge));
		}
		return false;
	}
	
	/**
	 * checks whether this object touches another geometric object or not 
	 * @param that a GeoObejkt that is proofed if it touches
	 * @return true if that touches - otherwise false
	 */
	boolean touches(GeoObjekt that) {
		if ((this.loecke.xwert + this.xlaenge) < that.loecke.xwert) {
			return false;
		}
		if ((this.loecke.ywert + this.ylaenge) < that.loecke.ywert) {
			return false;
		}
		if ((that.loecke.xwert + that.xlaenge) < this.loecke.xwert) {
			return false;
		}
		if ((that.loecke.ywert + that.ylaenge) < this.loecke.ywert) {
			return false;
		}
		return true;
	}

	
	/**
	 * painting this object
	 * @param g
	 */
	public void paintMeTo(Graphics g) {
		g.fillRect((int)this.loecke.xwert, (int)this.loecke.ywert, (int)this.xlaenge, (int)this.ylaenge);
	}

	/**
	 * @return this' {@link GeoObjekt#loecke loecke}
	 */
	public Vertex getLoecke() {
		return loecke;
	}

	/**
	 * sets this {@link GeoObjekt#loecke loecke}
	 * @param xwert
	 * @param ywert
	 */
	public void setLoecke(double xwert, double ywert) {
		this.loecke.xwert = xwert;
		this.loecke.ywert = ywert;
	}
}

