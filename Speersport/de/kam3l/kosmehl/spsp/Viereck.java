package de.kam3l.kosmehl.spsp;

import java.awt.Graphics;
import java.awt.Polygon;

/**
 * class to define rectangles in the 2D-space
 * @author Keks0r
 */
public class Viereck extends GeoObjekt{
	/** inclination of the whole rectangle */
	private double neigung = 0;
	/** inner angle */
	private double inneigung = 0;
	/** corner as Vertex' **/
	public Vertex roecke, luecke, ruecke;
	/** the conjunction of all corners as a polygon **/
	Polygon poly = null;

	/**
	 * @param loecke {@link GeoObjekt#loecke loecke}
	 * @param xlaenge {@link GeoObjekt#xlaenge xlaenge}
	 * @param ylaenge {@link GeoObjekt#ylaenge ylaenge}
	 */
	public Viereck(Vertex loecke, double xlaenge, double ylaenge) {
		super(xlaenge, ylaenge, loecke);
		hintun();
	}
	
	/**
	 * @param loecke {@link GeoObjekt#loecke loecke}
	 * @param xlaenge {@link GeoObjekt#xlaenge xlaenge}
	 * @param ylaenge {@link GeoObjekt#ylaenge ylaenge}
	 * @param negung {@link Viereck#neigung neigung}
	 */
	public Viereck(Vertex loecke, double xlaenge, double ylaenge, double negung) {
		super(xlaenge, ylaenge, loecke);
		this.neigung = negung;
		hintun();
	}
	
	/**
	 * @param loecke {@link GeoObjekt#loecke loecke}
	 * @param xlaenge {@link GeoObjekt#xlaenge xlaenge}
	 * @param ylaenge {@link GeoObjekt#ylaenge ylaenge}
	 * @param innernegung {@link Viereck#inneigung inneigung}
	 * @param negung {@link Viereck#neigung neigung}
	 */
	public Viereck(Vertex loecke, double xlaenge, double ylaenge, double innernegung, double negung) {
		super(xlaenge, ylaenge, loecke);
		this.neigung = negung;
		this.inneigung = innernegung;
		hintun2();
	}
	
	
	/**
	 * a free tetragon defined by lining the 4 given points together 
	 * @param locke
	 * @param lucke
	 * @param rucke
	 * @param rocke
	 */
	public Viereck(Vertex locke, Vertex lucke, Vertex rucke, Vertex rocke) {
		super(rocke.xwert - locke.xwert, lucke.ywert - locke.ywert, locke);
		this.luecke = lucke;
		this.ruecke = rucke;
		this.roecke = rocke;
	}
	
	
	/**
	 * recalculates the coordinates of all points except the loecke which is the rotation angle
	 * @param n
	 */
	public void rechNeigung(double n) {
		this.luecke = this.luecke.drehenPP(this.loecke, this.neigung);
		this.roecke = this.roecke.drehenPP(this.loecke, this.neigung);
		this.ruecke = this.ruecke.drehenPP(this.loecke, this.neigung);
	}
	
	/** sets the {@link Viereck#neigung neigung} and recalculates everything **/
	public void setNeigung(double n) {
		this.neigung = n;
		hintun();
	}
	
	/**
	 * @return {@link Viereck#neigung neigung}
	 */
	public double getNeigung() {
		return this.neigung;
	}
	
	/**
	 * @return the inner-angle
	 */
	public double getInneigung() {
		return inneigung;
	}

	/**
	 * sets the {@link Viereck#inneigung inneigung} and new calculation for depending points
	 * @param inneigung
	 */
	public void setInneigung(double inneigung) {
		this.inneigung = inneigung;
		this.hintun2();
	}
	
	/**
	 * resets the loecke and arranging everything depending
	 * @param locke
	 */
	public void setLoecke(Vertex locke) {
		hintun();
	}
	
	/**
	 * calculation and setting of all coordinates
	 */
	public void hintun() {
		this.roecke = new Vertex(loecke.xwert + xlaenge, loecke.ywert + 0);
		this.luecke = new Vertex(loecke.xwert + 0, loecke.ywert + ylaenge);
		this.ruecke = new Vertex(loecke.xwert + xlaenge, loecke.ywert + ylaenge);
		// neigung einrechnen:
		this.rechNeigung(this.neigung);
		this.initPoly();
	}
	
	/**
	 * calculation and setting of all coordinates implying the inner-angle(s)
	 */
	public void hintun2() {
		this.roecke = new Vertex(loecke.xwert + xlaenge, loecke.ywert + 0);
		Vertex v = new Vertex(loecke.xwert + 0, loecke.ywert + ylaenge);
		v = v.drehenPP(this.loecke, this.inneigung);
		this.luecke = v;
		v = new Vertex(loecke.xwert + xlaenge, loecke.ywert + ylaenge);
		v = v.drehenPP(this.roecke, this.inneigung);
		this.ruecke = v;
		// neigung einrechnen:
		this.rechNeigung(this.neigung);
		this.initPoly();
	}
	
	/**
	 * passing the coordinates to the {@link Viereck#poly poly}
	 */
	public void initPoly() {
		poly = new Polygon();
		int[] xecken = new int[4];
		int[] yecken = new int[4];
		xecken[0] = (int)this.getLoecke().xwert;
		yecken[0] = (int)this.getLoecke().ywert;
		xecken[1] = (int)this.roecke.xwert;
		yecken[1] = (int)this.roecke.ywert;
		xecken[2] = (int)this.ruecke.xwert;
		yecken[2] = (int)this.ruecke.ywert;
		xecken[3] = (int)this.luecke.xwert;
		yecken[3] = (int)this.luecke.ywert;
		poly.npoints = 4;
		poly.xpoints = xecken;
		poly.ypoints = yecken;
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
	 * checks whether this object touches another geometric object or not 
	 * @param that a GeoObejkt that is proofed if it touches
	 * @return true if that touches - otherwise false
	 */
	public boolean touches(Viereck that) {
		if (poly.contains(that.loecke.xwert, that.loecke.ywert)) {
			return true;
		}
		if (poly.contains(that.luecke.xwert, that.luecke.ywert)) {
			return true;
		}
		if (poly.contains(that.roecke.xwert, that.roecke.ywert)) {
			return true;
		}
		if (poly.contains(that.ruecke.xwert, that.ruecke.ywert)) {
			return true;
		}
		if (poly.intersects(that.getLoecke().xwert, that.getLoecke().ywert, that.xlaenge, that.ylaenge)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "\nloe: " + this.loecke.toString() + "\nlue: " + this.luecke.toString() 
			+ "\nrue: " + this.ruecke + "\nroe: " + this.roecke;
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
