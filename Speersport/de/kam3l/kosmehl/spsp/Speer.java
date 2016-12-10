package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * class of the Speere used in the Speersport<br>
 * <h1>tip:</h1>in the real Speersport, Speere are made<br> of bamboo with a rubber head wrapped with a rag<br><br>
 * subclass of GeoOjekt
 * @author Keks0r
 */
public class Speer extends GeoObjekt {
	/** list of the contained {@link GeoObjekt GeoObjekts} **/
	List<GeoObjekt> geoL = new ArrayList<GeoObjekt>();
	/** the resolution of the field-panel (height + width)(given) **/
	double aufloesung = 0;
	/** the length of the shaft **/
	double schaftlaen = aufloesung / 9;
	/** width of the Speer **/
	double brt = aufloesung / 277;
	/** the rotation degree of this object **/
	private double neigung = 0;
	/** alignment **/
	private int richt = 1;
	/** the circle part of the Speere's head **/
	Kreis sp1;
	/** the triangle part of the Speere's head **/
	Dreieck sp2;
	/** the spears shaft **/
	Viereck sp3;
	public int flag = 0;
	public boolean flag2 = false;
	
	public Speer() {
		super(100, 30, null); // pseudo-numbers
	}
	
	/**
	 * Initialization of this Speer - with its location, rotation, alignment and resolution.
	 * @param loecke
	 * @param neig
	 * @param rich
	 * @param aufloes
	 */
	public void setSpeer(Vertex loecke, double neig, int rich, double aufloes) {
		if ((rich == -1) || (rich == 1)) {
			this.aufloesung = aufloes;
			this.loecke = loecke;
			this.neigung = neig;
			this.richt = rich;
			this.initRicht();
			this.hintun();
		} else {
			System.out.println("keine gueltige Richtung des Speers " + loecke + " initialisiert");
		}
	}
	
	/**
	 * Recalculation of the shaft length and width with the current resolution.
	 */
	private void initialisieren() {
		schaftlaen = aufloesung / 9;
		brt = aufloesung / 277;
	}
	
	/**
	 * method to generate and set all geometric objects this Speer contains of
	 */
	private void hintun() {
		if (loecke == null) {
			System.out.println("kein Anfangspunkt/Neigung gesetzt");
		}
		this.geoL.clear();
		initialisieren();
		sp3 = new Viereck(loecke, brt, schaftlaen, neigung);
		Vertex v = new Vertex(sp3.ruecke.xwert - (brt / 2), sp3.ruecke.ywert + (brt / 2));
		v = v.drehenPP(sp3.ruecke, neigung);
		sp1 = new Kreis(v, brt + (aufloesung / 344));
		Vertex i = new Vertex(v.xwert + 1.5 * brt, v.ywert - 3 * brt);
		i = i.drehenPP(v, neigung);
		Vertex z = new Vertex(v.xwert - 1.5 * brt, v.ywert - 3 * brt);
		z = z.drehenPP(v, neigung);
		sp2 = new Dreieck(v, i, z);
		sp3.col = new Color(185, 169, 132);
		sp1.col = new Color(248, 138, 29);
		sp2.col = new Color(248, 138, 29);
		geoL.add(sp3);
		geoL.add(sp1);
		geoL.add(sp2);
	}
	
	
	/**
	 * to set the alignment of this
	 * @param rich
	 */
	public void setRichtung(int rich) {
		if ((rich == -1) || (rich == 1)) {
			this.richt = rich;
			initRicht();
			this.hintun();
		} else {
			System.out.println("keine gueltige Richtung des Speers " + loecke + " initialisiert");
		}
	}
	
	
	/**
	 * this function detects if a Kaempfer k is hit by this Speer 
	 * @param k
	 * @return whether a k is hit or not
	 */
	public boolean getroffen(Kaempfer k) {
		Vertex p2 = new Vertex(sp1.loecke.xwert + (2 * sp1.getRadius()), sp1.loecke.ywert);
		Vertex p3 = new Vertex(sp1.loecke.xwert, sp1.loecke.ywert + (2 * sp1.getRadius()));
		Vertex p4 = new Vertex(sp1.loecke.xwert + (2 * sp1.getRadius()), sp1.loecke.ywert + (2 * sp1.getRadius()));
		List<Vertex> punkte = new ArrayList<Vertex>();
		punkte.add(sp2.loecke);
		punkte.add(sp2.luecke);
		punkte.add(sp2.recke);
		punkte.add(sp1.loecke);
		punkte.add(p2);
		punkte.add(p3);
		punkte.add(p4);
		for (Vertex d : punkte) {
			int res = k.getroffenA(d);
			if (res == 1) {
				return true;
			}
			if ((res > 1) && (flag == 0)) {
				this.flag = (int)((aufloesung / 2.3) / 8);
			}
		}
		return false;
	}
	
	/**
	 * calculation of the flight of this Speer
	 * @param spankr {@link Kaempfer#spann2 tonicity}
	 * @param richt direction for this Speer
	 */
	public void flieg(double spankr, int richt) {
		double steigng = this.loecke.getSteigungP(sp1.getMpunkt());
		double auflf = this.aufloesung / 1400;
		this.beweg.xwert = 1 * spankr * richt * auflf;
		this.beweg.ywert = steigng * spankr * richt * auflf;
		this.hintun();
	}
	
	/**
	 * calculation of this Speer's rotation depending on the {@link Speer#richt richt}
	 */
	public void initRicht() {
		this.neigung = this.neigung * this.richt;
	}
	
	/**
	 * @return the rotation degree
	 */
	public double getNeigung() {
		return neigung;
	}

	/**
	 * the {@link Speer#neigung neigung} to set
	 * @param neigung
	 */
	public void setNeigung(double neigung) {
		this.neigung = neigung;
		this.hintun();
	}
	
	/**
	 * @return the richt
	 */
	public int getRicht() {
		return richt;
	}

	/**
	 * @param richt the richt to set
	 */
	public void setRicht(int richt) {
		this.richt = richt;
	}

	@Override
	public void move() {
		int breite = (int)(aufloesung / 1.75), hoehe = (int)(aufloesung / 2.3);
		if (this.flag != 0) {
			if (this.beweg.xwert * this.richt > 10) {
				this.beweg.xwert = (this.beweg.xwert) * -1;
			} else if (this.beweg.xwert * this.richt < -5) {
				this.beweg.xwert = this.beweg.xwert - (this.beweg.xwert / 2);
			} else {
				this.beweg.xwert = 0;
			}
			if (flag < 10) {
				this.beweg.ywert = 0;
				flag = 0;
			} else {
				this.beweg.ywert = hoehe / 35 + this.ra.nextInt(5);
				this.loecke.addi(beweg.xwert, beweg.ywert);
				flag = flag - (hoehe / 80);
			}			
		} else if ((this.loecke.xwert > breite) || (this.loecke.xwert < (0)) ||
				(this.loecke.ywert > hoehe) || (this.loecke.ywert < (0))) {
			if (this.richt == -1) {
				this.richt = 1;
				this.setSpeer(new Vertex(breite / 6.2, (hoehe / 2.67) + ra.nextInt(10)), 85 + ra.nextInt(10), 1, breite + hoehe);
			} else {
				this.richt = -1;
				this.setSpeer(new Vertex(breite / 1.21, (hoehe / 1.13)  + ra.nextInt(10)), 85 + ra.nextInt(10), -1, breite + hoehe);
			}
			this.beweg.xwert = 0;
			this.beweg.ywert = 0;
			
			//System.out.println(this.toString() + " zurueckgesetzt");
			
		} else {
			this.loecke.addi(beweg.xwert, beweg.ywert);
			if (beweg.xwert * this.richt > (this.aufloesung / 156)) {
				beweg.xwert = beweg.xwert - (this.aufloesung / 1400 * this.richt);
			}
			if (beweg.ywert * this.richt > (this.aufloesung / 156)) {
				beweg.ywert = beweg.ywert - (this.aufloesung / 1400 * this.richt);
			}
		}
		this.hintun();
	}
	
	@Override
	public void verschiebe(Vertex v) {
		this.loecke = new Vertex(v.xwert, v.ywert);
		this.hintun();
	}
	
	@Override
	public String toString() {
		String aus = this.loecke + " " + Math.round(brt) + " " + this.neigung;
		for (GeoObjekt p : this.geoL) {
			aus = aus + "\n" + p.toString();
		}
		return aus;
	}
	
	@Override
	public boolean hasWithin(Vertex dot) {
		for (GeoObjekt gO : this.geoL) {
			if (gO.hasWithin(dot)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paintMeTo(Graphics g) {
		for (GeoObjekt p : this.geoL) {
			p.paintMeTo(g);
		}
	}
	
	@Override
	public boolean touches(GeoObjekt that) {
		for (GeoObjekt p : this.geoL) {
			if ((p instanceof Viereck) && (that instanceof Viereck)) {
				Viereck akt = (Viereck) p;
				Viereck other = (Viereck) that;
				if (akt.touches(other))
				return true;
			}
			if (p.touches(that)) {
				return true;
			}
		}
		return false;
	}
	
}
