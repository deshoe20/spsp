package de.kam3l.kosmehl.spsp;

import java.awt.Graphics;
import java.awt.Polygon;

/**
 * class to define triangles in 2D-space
 * @author Keks0r
 */
public class Dreieck extends GeoObjekt {
	/** the rotation of this triangle **/
	private double neigung;
	/** length of the side **/
	private double seitea, seiteb, seitec;
	/** corner of the triangle **/
	Vertex recke, luecke;
	/** the conjunction of all corners as a polygon **/
	Polygon poly = null;

	/**
	 * triangle defined from corner to corner to corner
	 * @param locke
	 * @param lucke
	 * @param rcke
	 */
	public Dreieck(Vertex locke, Vertex lucke, Vertex rcke) {
		super(locke.abstandP(lucke), lucke.abstandP(rcke) + rcke.abstandP(locke), locke);
		this.neigung = 0;
		this.recke = rcke;
		this.luecke = lucke;
		this.initPoly();
	}
	
	/**
	 * triangle defined trough Vertex locke, then side a, b and c and the rotation in space around locke
	 * @param locke
	 * @param a
	 * @param b
	 * @param c
	 * @param negung
	 */
	public Dreieck(Vertex locke, double a, double b, double c, double negung) {
		super(a, b + c, locke);
		this.neigung = negung;
		this.seitea = a;
		this.seiteb = b;
		this.seitec = c;
		this.hintun();
	}
	
	/**
	 * recalculates the coordinates of the all points except the loecke which is the rotation angle
	 * @param n
	 */
	public void rechNeigung(double n) {
		this.luecke = this.luecke.drehenPP(this.loecke, neigung);
		this.recke = this.recke.drehenPP(this.loecke, neigung);
	}
	
	/** sets the {@link Dreieck#neigung neigung} and recalculates everything **/
	public void setNeigung(double n) {
		this.neigung = n;
		hintun();
	}
	
	/**
	 * calculation and setting of all coordinates
	 */
	public void hintun() {
		double n;
		this.luecke = new Vertex(loecke.xwert, loecke.ywert + seitea);
		this.recke = new Vertex(luecke.xwert + seiteb, luecke.ywert);
		n = Math.acos((Math.pow(seitea, 2) + Math.pow(seiteb, 2) - Math.pow(seitec, 2)) / (2 * seitea * seiteb)) * -1;
		this.recke = this.recke.drehenPP(this.luecke, n);
		// neigung einrechnen:
		this.rechNeigung(neigung);
		this.initPoly();
	}
	
	@Override
	public boolean hasWithin(Vertex p) {
		if (poly != null) {
			return poly.contains(p.xwert, p.ywert);
		} 
		System.out.println("Polygon nicht initalisiert");
		return false;		
	}
	
	/**
	 * passing the coordinates to the {@link Viereck#poly poly}
	 */
	public void initPoly() {
		poly = new Polygon();
		int[] xecken = new int[3];
		int[] yecken = new int[3];
		xecken[0] = (int)this.loecke.xwert;
		yecken[0] = (int)this.loecke.ywert;
		xecken[1] = (int)this.luecke.xwert;
		yecken[1] = (int)this.luecke.ywert;
		xecken[2] = (int)this.recke.xwert;
		yecken[2] = (int)this.recke.ywert;
		poly.npoints = 3;
		poly.xpoints = xecken;
		poly.ypoints = yecken;
	}
	
	@Override
	public void paintMeTo(Graphics g) {
		if (poly != null) {
			g.setColor(this.col);
			g.fillPolygon(poly);
		} else {
			System.out.println("Polygon nicht initalisiert");
		}
	}
}
