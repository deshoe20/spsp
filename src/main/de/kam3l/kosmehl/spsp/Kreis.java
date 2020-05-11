package de.kam3l.kosmehl.spsp;

import java.awt.Graphics;

/**
 * class for simple circles
 * @author Keks0r
 *
 */
public class Kreis extends GeoObjekt{
	
	/** the radius of the circle **/
	private double radius;
	/** the bounding-box - a square **/
	private int[] kreis = null;
	/** the center-Vertex of the circle **/
	private Vertex mpunkt;
	
	/**
	 * @param mpunk
	 * @param xlaenge
	 */
	public Kreis(Vertex mpunk, double xlaenge) {
		super(xlaenge, xlaenge, new Vertex(mpunk.xwert - (xlaenge / 2), mpunk.ywert - (xlaenge / 2)));
		this.radius = xlaenge / 2;
		this.mpunkt = mpunk;
		initKreis();
	}
	
	@Override
	double area() {
		return ((Math.PI / 4) * ((2 * this.radius) * (2 * this.radius)));
	}
	
	
	/**
	 * This function initiates the bounding-box coordinates for the circle.
	 */
	public void initKreis() {
		kreis = new int[4];
		this.loecke.xwert = mpunkt.xwert - (xlaenge / 2);
		this.loecke.ywert = mpunkt.ywert - (xlaenge / 2);
		kreis[0] = (int)this.loecke.xwert;
		kreis[1] = (int)this.loecke.ywert;
		kreis[2] = (int)this.xlaenge;
		kreis[3] = (int)this.xlaenge;
	}
	
	/**
	 * @return the center Vertex
	 */
	public Vertex getMpunkt() {
		return mpunkt;
	}

	/**
	 * sets the center Vertex of this circle and recalculates its coordinates
	 * @param mpunkt
	 */
	public void setMpunkt(Vertex mpunkt) {
		this.mpunkt = mpunkt;
		initKreis();
	}
	
	/**
	 * @return radius of the circle
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * sets the radius and then initiates the circle anew
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		initKreis();
	}

	
	@Override
	public void paintMeTo(Graphics g) {
		if (this.kreis == null) {
			System.out.println("Kreis nicht initalisiert");
			return;
		}
		g.setColor(this.col);
		g.fillOval(kreis[0], kreis[1], kreis[2], kreis[3]);
	}

	@Override
	public String toString() {
		return "loecke: " + this.getLoecke() + " radius: " + radius + " flaeche: " + this.area();
	}
	
	@Override
	public void move() {
		if ((this.getLoecke().xwert = this.getLoecke().xwert + this.getLoecke().xwert * (1 / this.beweg.xwert)) >= 800) {
			this.getLoecke().xwert = this.getLoecke().xwert - this.getLoecke().xwert * (1 / this.beweg.xwert);
			this.beweg.xwert = this.beweg.xwert * -1;
		}
		if ((this.getLoecke().ywert = this.getLoecke().ywert + this.getLoecke().ywert * (1 / this.beweg.ywert)) >= 800) {
			this.getLoecke().ywert = this.getLoecke().ywert - this.getLoecke().ywert * (1 / this.beweg.ywert);
			this.beweg.ywert = this.beweg.ywert * -1;
		}
	}
	
	@Override
	public boolean hasWithin(Vertex dot) {
		if (this.mpunkt.abstandP(dot) <= this.radius) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kreis other = (Kreis) obj;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}
	
}
