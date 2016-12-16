package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * class of the fighters "Kaempfer"<br><br>
 * Kaempfer are of the class <br>
 * {@link GeoObjekt GeoObjekt} and consisting<br>
 * itself of a list of {@link Kaempfer#geoL GeoObjekt}<br>
 * 
 * @author Keks0r
 * 
 */
public class Kaempfer extends GeoObjekt{
	/** the base-point of this Kaempfer at the left corner of the left leg **/
	private Vertex apunkt = null;
	/** the size multiplier of this Kaempfer **/
	double groessef = 1.0;
	/** the speed multiplier of this Kaempfer **/
	double geschwindigkeitsf = 1.0;
	/** the jump multiplier of this Kaempfer **/
	double sprungf = 1.0;
	/** the throwing power multiplier of this Kaempfer **/
	double wkraftf = 1.0;
	/** the name **/
	String name;
	/** list of the contained {@link GeoObjekt GeoObjekts} **/
	List<GeoObjekt> geoL = new ArrayList<GeoObjekt>();
		
	/** 
	 * the critical hit-boxes<br><br>
	 * part of the {@link Kaempfer Kaempfer} where<br>
	 * a {@link Speer Speer}-hit counts<br>
	 * originally: kneecap downwards
	 * **/
	Viereck bu1, bu2; 
	/** the arms of the {@link Kaempfer Kaempfer} **/
	Viereck a1, a2; 
	/** list of the {@link Speer Speere} in the {@link Kaempfer#a1 a1} arm **/
	List<Speer> a1spL = new ArrayList<Speer>();
	/** list of the {@link Speer Speere} in the {@link Kaempfer#a2 a2} arm **/
	List<Speer> a2spL = new ArrayList<Speer>();
	
	/** alignment **/
	private int richt = 1;
	/** resolution **/
	double aufloesung = 0;
	/** width **/
	double brt = aufloesung / 107;
	/** lower leg1 part **/
	double blaen1 = aufloesung / 31;
	/** lower leg1 part angle **/
	double bneig1;
	/** lower leg2 part **/
	double blaen2 = aufloesung / 27;
	/** lower leg2 part angle **/
	double bneig2;
	/** upper leg1 part **/
	double blaen3 = aufloesung / 31;
	/** upper leg1 part angle **/
	double bneig3;
	/** upper leg2 part **/
	double blaen4 = aufloesung / 27;
	/** upper leg2 part angle **/
	double bneig4;
	/** rotation degree of the arm1 **/
	double aneig1;
	/** rotation degree of the arm2 **/
	double aneig2;
	/** width of the arm **/
	double abrt = aufloesung / 163;
	/** length of the arm **/
	double alaen = aufloesung / 23;
	/** length of the body part **/
	double colaen = aufloesung / 19;
	/** rotation degree of the body part **/
	double coneig;
	/** width of the head **/
	double korad = aufloesung / 55;
	
	/** field for storing information about the Kaempfer current state **/
	private int status = 13;
	/** "run-time-variable"(hehehe) used in some movements **/
	private int lzv = 0;
	/** vertical direction **/
	private int vricht = 1;
	/** horizontal direction **/
	private int bxricht, byricht;
	/** the strain build up by the arm1 **/
	private double spann1 = 0;
	/** the strain build up by the arm2 **/
	private double spann2 = 0;
	/** aiming degree **/
	private int ziel1 = 0;
	/** aiming degree **/
	private int ziel2 = 0;
	
	/**
	 * defining a Kaempfer through their {@link Kaempfer#groessef groessef}, {@link Kaempfer#geschwindigkeitsf geschwindigkeitsf}, 
	 * {@link Kaempfer#sprungf sprungf}, {@link Kaempfer#wkraftf wkraftf} and {@link Kaempfer#name name}
	 * @param groessef
	 * @param geschwindigkeitsf
	 * @param sprungf
	 * @param wkraftf
	 * @param name
	 */
	public Kaempfer(double groessef, double geschwindigkeitsf,
			double sprungf, double wkraftf, String name, Color co) {
		super(100, 50, null);
		this.groessef = groessef;
		this.geschwindigkeitsf = geschwindigkeitsf;
		this.sprungf = sprungf;
		this.wkraftf = wkraftf;
		this.name = name;
		this.col = co;
	}
	
	/** size|speed|jumppower|throwpower|name **/
	public Kaempfer(Object[] werte, Color co) {
		super(100, 50, null);
		this.groessef = (Double)werte[0];
		this.geschwindigkeitsf = (Double)werte[1];
		this.sprungf = (Double)werte[2];
		this.wkraftf = (Double)werte[3];
		this.name = (String)werte[4];
		this.col = co;
	}
	
	/** defining a Kaempfer only through his name - all other values will be set by default **/
	public Kaempfer(String name, Color co) {
		super(100, 50, null);
		this.col = co;
		this.name = name;
	}
	
	/** initiates all parameters that are depending of the resolution **/
	private void initialisieren() {
		brt = aufloesung / 107;
		blaen1 = aufloesung / 31;
		blaen2 = aufloesung / 27;
		blaen3 = aufloesung / 31;
		blaen4 = aufloesung / 27;
		abrt = aufloesung / 163;
		alaen = aufloesung / 23;
		colaen = aufloesung / 19;
		korad = aufloesung / 55;
	}
	
	/** rotation degrees of all	rotatable objects of the Kaempfer will be reseted to their default value **/
	private void vorgabe() {
		this.bneig1 = -19;
		this.bneig2 = -2;
		this.bneig3 = -8;
		this.bneig4 = 25;
		if (this.spann1 == 0) {
			this.aneig1 = 80;
		}
		if (this.spann2 == 0) {
			this.aneig2 = 110;
		}
		this.coneig = -5;
		this.lzv = 0;
	}
	
	/** sets the Kaempfers base-point, alignment and resolution **/
	public void setKaempfer(Vertex apunk, int rich, double auflosung) {
		if ((rich == -1) || (rich == 1)) {
			vorgabe();
			this.aufloesung = auflosung;
			this.apunkt = apunk;
			this.richt = rich;
			this.beweg = new Vertex(aufloesung / 280, aufloesung / 280);
			initRicht();
			this.hintun();
		} else {
			System.out.println("keine gueltige Richtung des Kaempfers " + this.name + " initialisiert");
		}
	}
	
	/** 
	 * 
	 * @return the alignment
	 */
	public int getRicht() {
		return this.richt;
	}
	
	/**
	 * sets the alignment
	 * @param rich
	 */
	public void setRichtung(int rich) {
		if ((rich == -1) || (rich == 1)) {
			this.richt = rich;
			initRicht();
			this.hintun();
		} else {
			System.out.println("keine gueltige Richtung des Kaempfers " + this.name + " initialisiert");
		}
	}
	
	/**
	 * @return the base-point
	 */
	public Vertex getApunkt() {
		return this.apunkt;
	}
	
	/**
	 * initiates the alignment by calculating all dependent rotations
	 */
	private void initRicht() {
		bneig1 = bneig1 * richt;
		bneig2 = bneig2 * richt;
		bneig3 = bneig3 * richt;
		bneig4 = bneig4 * richt;
		aneig1 = aneig1 * richt;
		aneig2 = aneig2 * richt;
		coneig = coneig * richt;
	}
	
	/**
	 * constructing the Kaempfer and calculating<br> positions and dependencies and setting the colors<br>
	 * for its geometric objects<br><br>
	 * all constructed elements are added to the {@link Hintergrund#geoL geoL}
	 */
	public List<GeoObjekt> hintun() {
		if (apunkt == null) {
			System.out.println("kein Anfangspunkt/Richtung gesetzt");
		}
		this.geoL.clear();
		initialisieren();
		bu1 = new Viereck(apunkt, brt * groessef, blaen1 * groessef, bneig1 + 180);
		Viereck bo1 = new Viereck(bu1.luecke, brt * groessef, blaen1 * groessef, bneig2 + 180);
		Viereck bo2 = new Viereck(bo1.ruecke, brt * groessef, blaen2 * groessef, bneig4); 
		bu2 = new Viereck(bo2.luecke, brt * groessef, blaen1 * groessef, bneig3);
		Viereck co = new Viereck(bo1.luecke, brt * groessef, colaen * groessef, coneig + 180);
		Vertex i = new Vertex(co.ruecke.xwert + (brt / 2), co.ruecke.ywert - korad - (aufloesung / 187));
		i = i.drehenPP(co.ruecke, coneig);
		Kreis ko = new Kreis(i, korad * 2 * groessef);
		Vertex v = new Vertex(co.ruecke.xwert + (brt / 2), co.ruecke.ywert + (aufloesung / 62));
		v = v.drehenPP(co.ruecke, coneig);
		a1 = new Viereck(v, abrt * groessef, alaen * groessef, aneig1); 
		a2 = new Viereck(v, abrt * groessef, alaen * groessef, aneig2);
		bu1.col = new Color(114, 113, 103);
		bu2.col = new Color(114, 113, 103);
		ko.col = this.col;
		co.col = this.col;
		a1.col = this.col;
		a2.col = this.col;
		bo1.col = this.col;
		bo2.col = this.col;
		sphintun2();
		geoL.add(bu1);
		geoL.add(bo1);
		geoL.add(bo2);
		geoL.add(bu2);
		geoL.add(co);
		geoL.add(ko);
		geoL.add(a1);
		geoL.add(a2);
		return geoL;
	}
	
	/**
	 * arranges the Speere on the top of the Kaempfers arms
	 */
	public void sphintun() {
		for (Speer sp : this.a2spL) {
			sp.setSpeer(a2.luecke, ra.nextInt(25) + 30, this.richt, this.aufloesung);
		}
		for (Speer sp : this.a1spL) {
			sp.setSpeer(a1.luecke, ra.nextInt(25), this.richt, this.aufloesung);
		}
	}
	
	/**
	 * moves all Speere to the lower-left corner of the arms
	 */
	public void sphintun2() {
		for (Speer sp : this.a2spL) {
			sp.verschiebe(a2.luecke);
		}
		for (Speer sp : this.a1spL) {
			sp.verschiebe(a1.luecke);
		}
	}
	
	/**
	 * adds a {@link Speer Speer} sp in {@link Kaempfer#a1spL a1spL} or {@link Kaempfer#a2spL a2spL} determined by h
	 * @param sp
	 * @param h
	 */
	public void spnehmenH(Speer sp, int h) {
		if (h == 1) {
			this.a1spL.add(sp);
		} else {
			this.a2spL.add(sp);
		}
		sphintun();
	}
	
	/**
	 * dividing Speere into the arms <br>
	 * one Speer is put in {@link Kaempfer#a2spL a2spL}<br>
	 * remaining Speere are put in {@link Kaempfer#a1spL a1spL}
	 */
	public void spverteil() {
		if (this.a1spL.isEmpty() && this.a2spL.isEmpty()) {
			return;
		}
		this.spgebenH(1);
		this.a2spL.add(this.a1spL.get(this.a1spL.size() - 1));
		this.a1spL.remove(this.a1spL.size() - 1);
		sphintun();
	}
	
	/**
	 * collect all Speere in one hand(h)
	 * @param h - h could be 1 or !1<br>
	 * h = 1 for moving all Speere in {@link Kaempfer#a2spL a2spL} into {@link Kaempfer#a1spL a1spL}<br>
	 * respectively h != 1 to put all Speere in {@link Kaempfer#a2spL a2spL}
	 */
	public void spgebenH(int h) {
		if (h == 1) {
			for (Speer sp : this.a2spL) {
				this.a1spL.add(sp);
			}
			this.a2spL.clear();
		} else {
			for (Speer sp : this.a1spL) {
				this.a2spL.add(sp);
			}
			this.a1spL.clear();
		}
		sphintun();
	}
	
	/**
	 * tries to pick some Speere from the ground
	 * @param spL list of all {@link Speer Speere} on the field
	 */
	public void spaufheben(List<Speer> spL) {
		//die bewegung wird anhand des Status ueberwacht
		if ((this.status == 40) && !(bu1.roecke.abstandP(a2.luecke) < (aufloesung / 20))) { //nur begrenzt runterbuecken
			if (this.a2spL.isEmpty()) { //bueckbewegung erst einleiten wenn eine Hand leer ist
				for (Speer sp : spL) { //alle Speere untersuchen...
					if (sp.flag2) {
						continue;
					}
					Vertex v = new Vertex(a2.luecke.xwert - (aufloesung / 40), a2.luecke.ywert - (aufloesung / 40));
					Viereck rtest = new Viereck(v , aufloesung / 20, aufloesung / 20); //suchviereck anlegen
					//...ob sie im suchviereck liegen										
					if (sp.touches(rtest) && !(this.a1spL.contains(sp)) && (this.coneig * this.richt < -50)) {
						this.spnehmenH(sp, 2); //Speere aufnehmen
						sp.flag2 = true;
					}
				}
				//runterbuecken
				this.coneig = this.coneig - (9 * this.richt * this.geschwindigkeitsf);
				this.aneig1 = this.aneig1 + (7 * this.richt * this.geschwindigkeitsf);
				this.aneig2 = this.aneig2 - (9 * this.richt * this.geschwindigkeitsf);
				einknicken(-2 * this.geschwindigkeitsf, -2, 2 * this.geschwindigkeitsf, 2, 0);
				this.hintun();
			} else if (this.coneig == (-5 * this.richt)) {//alle Speere in einer Hand sammeln
				this.spgebenH(1);
				this.spaufheben(spL); //mit der geleerten hand diese funktion neuaufrufen
			}
		} else if (this.status == 41) { //wieder hochgehen
			//solange hochgehen bis anfangswerte ereicht sind
			if (((this.coneig * this.richt) < 0) && ((this.coneig * this.richt) > -10)) {
				// standard steh-haltung wieder herstellen
				vorgabe();
				initRicht();
				this.spverteil();
				this.hintun();
				this.status = 13;
			} else { //aufrichten
				this.coneig = this.coneig + (9 * this.richt * this.geschwindigkeitsf);
				this.aneig1 = this.aneig1 - (7 * this.richt * this.geschwindigkeitsf);
				this.aneig2 = this.aneig2 + (9 * this.richt * this.geschwindigkeitsf);
				einknicken(2 * this.geschwindigkeitsf, 2, -2 * this.geschwindigkeitsf, -2, 0);
				this.hintun();
			}
		}
	}
	
	/**
	 * Method of the "normal" jump.<br><br>
	 * In this jump motion the Kaempfer hunkers first a bit down, then springs upwards while folding the legs rearwards.
	 */
	public void springen1() {
		// diese bewegung wird ueber den Status gesteuert
		// die dauer der einzelnen bewegungsablaeufe werden durch die lzv bestimmt
		if ((this.status >= 50) && (this.status < 54)) {
			switch (this.status) {
				case 50: //zuerst einfedern
					if ((this.lzv > 6) && (this.vricht == 1)) {
						this.status = 51;
						break;
					}
					if ((this.lzv < 2) && (this.vricht == -1)) {
						vorgabe();
						initRicht();
						this.vricht = 1;
						this.hintun();
						this.status = 13;
						break;
					}
					einknicken(-4, -4, 4, 4.5, 0);
					this.hintun();
					break;
				case 51: //dann strecken
					if ((this.lzv > 12) && (this.vricht == 1)) {
						this.status = 52;
						break;
					}
					if ((this.lzv < 8) && (this.vricht == -1)) {
						this.status = 50;
						break;
					}
					einknicken(6, 6, -6, -10, 0);
					this.hintun();
					break;
				case 52: //dann hochfliegen und beine einklappen
					if ((this.lzv > 17) && (this.vricht == 1)) {
						this.status = 53;
						break;
					}
					if ((this.lzv < 14) && (this.vricht == -1)) {
						this.status = 51;
						break;
					}
					einknicken(-18, -20, 4, 4, aufloesung / 80);
					this.hintun();
					break;	
				case 53: //beine noch ein wenig einklappen und umkehr zum wieder runterkommen
					if ((this.lzv > 23) && (this.vricht == 1)) {
						this.vricht = -1;
					}
					if ((this.lzv < 19) && (this.vricht == -1)) {
						this.status = 52;
						break;
					}
					einknicken(-8, -6, 0, 0, aufloesung / 233);
					this.hintun();
					break;
				default:
					System.out.println("Fataler Fehler: kein korrekter Status im Sprung");
			}
		}
	}
	
	/**
	 * Aidfunction to modulate the legs for some motions
	 * @param e rotation degree for the {@link Kaempfer#bneig1 bneig1}
	 * @param d rotation degree for the {@link Kaempfer#bneig3 bneig3}
	 * @param z rotation degree for the {@link Kaempfer#bneig2 bneig2}
	 * @param v rotation degree for the {@link Kaempfer#bneig4 bneig4}
	 * @param hf vertical-flight multiplier
	 */
	private void einknicken(double e, double d, double z, double v, double hf) {
		this.bneig1 = this.bneig1 + (e * this.vricht * this.richt);
		this.bneig3 = this.bneig3 + (d * this.vricht * this.richt);
		this.bneig2 = this.bneig2 + (z * this.vricht * this.richt);
		this.bneig4 = this.bneig4 + (v * this.vricht * this.richt);
		this.bu1.loecke.ywert = this.bu1.loecke.ywert - hf * this.vricht * this.sprungf;
		this.lzv = this.lzv + this.vricht;
	}
	
	/**
	 * Method of the "special" jump.<br><br>
	 * In this jump motion the Kaempfer catapults his legs backwards while he stabilizes himself on the Speere.<br>
	 * To execute this jump the Kaempfer needs atleast one Speer at each arm.
	 */
	public void springen2() {
		// diese bewegung wird ueber den Status gesteuert
		// die dauer der einzelnen bewegungsablaeufe werden durch die lzv bestimmt
		if ((this.status >= 60) && (this.status < 64)) {
			switch (this.status) {
				case 60: // does nothing in preparation for the jump - ups auf englisch
					if ((this.lzv > 3) && (this.vricht == 1)) {
						this.status = 61;
						break;
					}
					if ((this.lzv < 5) && (this.vricht == -1)) {
						vorgabe();
						initRicht();
						this.vricht = 1;
						this.hintun();
						this.status = 13;
						break;
					}
					this.lzv = this.lzv + this.vricht;
					break;
				case 61: // koerper nach vorne - beine nach hinten wegfetzen
					if ((this.lzv > 12) && (this.vricht == 1)) {
						this.status = 62;
						break;
					}
					if ((this.lzv < 7) && (this.vricht == -1)) {
						this.status = 60;
					}
					this.coneig = this.coneig - (2 * this.richt * this.vricht);
					this.bu1.loecke.xwert = this.bu1.loecke.xwert - this.vricht * this.richt * (aufloesung / 190);
					einknicken(-11, -12, -11, -12, (aufloesung / 110));
					this.hintun();
					break;
				case 62: // kurz in der luft verweilen und dann wieder runterkommen
					if ((this.lzv > 16) && (this.vricht == 1)) {
						this.vricht = -1;
					}
					if ((this.lzv < 15) && (this.vricht == -1)) {
						this.status = 61;
						break;
					}
					einknicken(-1, 0, 0, 0, 0);
					this.hintun();
					break;
				default:
					System.out.println("Fataler Fehler: kein korrekter Status im Sprung2");
			}
		}
	}
	
	/** to move in the desired direction by the factor of {@link Kaempfer#geschwindigkeitsf geschwindigkeitsf} **/
	public void bewege() {
		apunkt.xwert = apunkt.xwert + (this.beweg.xwert * bxricht * this.geschwindigkeitsf);
		apunkt.ywert = apunkt.ywert + (this.beweg.ywert * byricht * this.geschwindigkeitsf);
		this.hintun();
	}
	
	/** to strain the arm in preparation of the throw **/
	public void spannen() {
			if ((spann1 != 0) && ((this.aneig1 * this.richt) < 175)) {
				spann1 = Math.sqrt(spann1 * (this.wkraftf * 1.1));
				this.aneig1 = this.aneig1 + (2 * this.richt);
				this.hintun();
			}
			if ((spann2 != 0) && ((this.aneig2 * this.richt) < 175)) {
				spann2 = spann2 + (this.wkraftf * 0.9);
				this.aneig2 = this.aneig2 + (2 * this.richt);
				this.hintun();
			}
	}
	
	/** throwing the Speer **/
	public void werfen() {
		if (spann1 != 0) {
			this.a1spL.get(0).flag2 = false;
			this.a1spL.get(0).flieg(spann1, this.richt);
			this.a1spL.clear();
			spann1 = 0;
			this.aneig1 = 80 * this.richt;
		}
		if (spann2 != 0) {
			this.a2spL.get(0).flag2 = false;
			this.a2spL.get(0).flieg(spann2, this.richt);
			this.a2spL.clear();
			this.spverteil();
			spann2 = 0;
			this.aneig2 = 110 * this.richt;
		}
	}
	
	/** aiming by changing the rotation degree of a Speer **/
	public void zielen() {
		if (this.a1spL.size() == 1) {
			double neigneu = this.a1spL.get(0).getNeigung() + (3 * ziel1);
			if ((neigneu * this.richt < 175) && (neigneu * this.richt > 5)) {
				this.a1spL.get(0).setNeigung(neigneu);
			}
			hintun();
		}
		if (this.a2spL.size() == 1) {
			double neigneu = this.a2spL.get(0).getNeigung() + (3 * ziel2);
			if ((neigneu * this.richt < 175) && (neigneu * this.richt > 5)) {
				this.a2spL.get(0).setNeigung(neigneu);
			}
			hintun();
		}
	}
		
	public void setApunkt(Vertex apunkt) {
		this.apunkt = apunkt;
	}

	/** detects body hits --> hits except the lower legs returns distance to ground otherwise 1 **/
	public int getroffenA(Vertex p) {
		for (GeoObjekt g : geoL) {
			if (g.hasWithin(p)) { 
				if (!(g.equals(bu1)) && !(g.equals(bu2))) {
					if (ra.nextInt(100) > 40) {
						return 0;
					}
					return (int)g.loecke.abstandP(this.apunkt);
				}
				if (ra.nextInt(100) > 80) {
					return 0;
				}
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @param i the status to set
	 */
	public void setStatus(int i) {
		this.status = i;
	}
	
	/**
	 * @return the bxricht
	 */
	public int getBxricht() {
		return bxricht;
	}

	/**
	 * @param bxricht the bxricht to set
	 */
	public void setBxricht(int bxricht) {
		this.bxricht = bxricht;
	}

	/**
	 * @return the byricht
	 */
	public int getByricht() {
		return byricht;
	}

	/**
	 * @param byricht the byricht to set
	 */
	public void setByricht(int byricht) {
		this.byricht = byricht;
	}

	/**
	 * @return the spann
	 */
	public double getSpann(int a) {
		if (a == 1)
			return spann1;
		if (a == 2)
			return spann2;
		return 0;
	}

	/**
	 * @param a the spann to set
	 */
	public void setSpann(int a) {
		if (a == 2) {
			if (((spann2 == 0) && (this.a2spL.size() == 1))) {
				this.spann2 = 1;
			}
		} else {
			if ((spann1 == 0) && (this.a1spL.size() == 1)) {
				this.spann1 = 1;
			}
		}
	}

	/**
	 * @return the ziel1
	 */
	public int getZiel1() {
		return ziel1;
	}

	/**
	 * @param ziel1 the ziel11 to set
	 */
	public void setZiel1(int ziel1) {
		this.ziel1 = ziel1;
	}

	/**
	 * @return the ziel2
	 */
	public int getZiel2() {
		return ziel2;
	}

	/**
	 * @param ziel2 the ziel2 to set
	 */
	public void setZiel2(int ziel2) {
		this.ziel2 = ziel2;
	}

	@Override
	public void move() {
		this.apunkt.addi(beweg.xwert * this.richt, beweg.ywert);
		this.hintun();
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

}
