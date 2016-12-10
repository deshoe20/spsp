package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * class to construct the background for the Speersport-Duell - the field
 * @author Keks0r
 */
public class Hintergrund extends GeoObjekt {
	/** list of the contained {@link GeoObjekt GeoObjekts} **/
	List<GeoObjekt> geoL = new ArrayList<GeoObjekt>();
	/** the resolution of the field-panel (given) **/
	Vertex aufloesung = new Vertex(0, 0);
	
	/**
	 * @param aufloes resolution
	 */
	public Hintergrund(Vertex aufloes) {
		super(aufloes.xwert, aufloes.ywert, new Vertex(0, 0));
		this.aufloesung = aufloes;
		hintun();
	}
	
	/**
	 * constructing the background and calculating<br> positions and dependencies, setting the colors<br><br>
	 * all constructed elements are added to the {@link Hintergrund#geoL geoL}
	 */
	public void hintun() {
		Viereck spielflaeche1, spielflaeche2;
		double x = aufloesung.xwert, y = aufloesung.ywert;
		double brt = y / 80;
		spielflaeche1 = new Viereck(new Vertex(x / 13.9, y / 2.9), x / 2.36, y / 1.65, 0, 0);
		spielflaeche1.col = new Color(75, 149, 45);
		spielflaeche2 = new Viereck(new Vertex(spielflaeche1.roecke.xwert + brt, spielflaeche1.roecke.ywert), x / 2.36, y / 1.65, 0, 0);
		spielflaeche2.col = new Color(75, 149, 45);
		Viereck r1 = new Viereck(spielflaeche2.roecke, spielflaeche1.xlaenge * 2 + brt, brt, spielflaeche1.getInneigung(), spielflaeche1.getNeigung() + 180);
		Vertex v = new Vertex(spielflaeche1.loecke.xwert - brt, spielflaeche1.loecke.ywert - brt);
		Viereck r2 = new Viereck(v, brt, spielflaeche1.ylaenge + brt, spielflaeche1.getInneigung(), spielflaeche1.getNeigung());
		v.xwert = v.xwert + spielflaeche1.xlaenge * 2 + brt;
		Viereck r3 = new Viereck(v, brt, spielflaeche1.ylaenge + brt, spielflaeche1.getInneigung(), spielflaeche1.getNeigung());
		Viereck r4 = new Viereck(spielflaeche2.ruecke, spielflaeche1.xlaenge * 2 + brt, brt, spielflaeche1.getInneigung(), spielflaeche1.getNeigung() + 180);
		v.xwert = v.xwert - spielflaeche1.xlaenge - 1;
		Viereck r5 = new Viereck(v, brt + 2, spielflaeche1.ylaenge + brt, spielflaeche1.getInneigung(), spielflaeche1.getNeigung());
		r1.col = Color.BLACK;
		r2.col = Color.BLACK;
		r3.col = Color.BLACK;
		r4.col = Color.BLACK;
		r5.col = Color.BLACK;
		geoL.add(spielflaeche1);
		geoL.add(spielflaeche2);
		geoL.add(r1);
		geoL.add(r2);
		geoL.add(r3);
		geoL.add(r4);
		geoL.add(r5);
	}

	/**
	 * @return the geoL
	 */
	public List<GeoObjekt> getGeoL() {
		return geoL;
	}

	@Override
	public void paintMeTo(Graphics g) {
		for (GeoObjekt p : this.geoL) {
			p.paintMeTo(g);
		}
	}
	
}
