package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * class to initiate the Speersport Duell game
 * @author Keks0r
 *
 */
public class Spielinit {
	/** variable to suppress a warning **/
	private static final long serialVersionUID = 1L;
	/** randomize-object used for the Speere placement **/
	public static java.util.Random ra = new java.util.Random();
	/** game's mainframe **/
	Spielfeldfenster spiel;
	/** the gamepanel **/
	Spielfeld feld;
	/** a CPU-controlled opponent **/
	boolean zielai = false; // noch nicht implementiert
	/** game starter **/
	ActionListener starter = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			spielstarten(0, 0);
		}
    };
    /** start-background *000*/
    JLabel startbild = new JLabel(new ImageIcon(getClass().getResource("/bilder/hintergrund.jpg")));
    int reih = new java.util.Random().nextInt(2);
    
    
	// config-->
    
    /** mainframe headline **/
	private static String name = "Speersport Duell";
	/** x-axis resolution **/
	private int breite = 800;
	/** y-axis resolution **/
	private int hoehe = 600;
	
	// groesse geschwindigkeit sprungkraft wurfkraft name
	/** config-set for the Kaempfers - according to the pattern size|speed|jumppower|throwpower|name **/
	Object[] speinst1 = {1.0, 1.0, 1.0, 1.0, "Faust"};
	/** config-set for the Kaempfers - according to the pattern size|speed|jumppower|throwpower|name **/
	Object[] speinst2 = {1.0, 1.3, 1.0, 1.5, "Olm"};
	/** config-set for the Kaempfers - according to the pattern size|speed|jumppower|throwpower|name **/
	Object[] speinst3 = {0.9, 1.5, 1.0, 2.1, "Droddel"};
	/** config-set for the Kaempfers - according to the pattern size|speed|jumppower|throwpower|name **/
	Object[] speinst4 = {1.1, 1.7, 1.6, 2.4, "Alpaka"};
	
	/** player1 **/
	Kaempfer spieler1;
	/** player2 **/
	Kaempfer spieler2;
	
	// <--config
	
	/**
	 * construct a new game
	 */
	public Spielinit() {
		JMenuItem starten = new JMenuItem("Spiel starten");
		spiel = new Spielfeldfenster(getName(), breite, hoehe);
		spiel.erzeugen();
		starten.addActionListener(starter);
		spiel.menuu.add(starten);
		spiel.menuInit();
		option();
		spiel.add(startbild);
		spiel.pack();
		reih = (reih + reih) - 1;
	}
	
	/**
	 * start the game with setting the startscore
	 * @param pnkt1 score of player1
	 * @param pnkt2 score of player2
	 */
	void spielstarten(double pnkt1, double pnkt2) {
		spiel.remove(startbild);
		if (feld != null) {
			try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    		}
    		Spielfeld.t.stop();
    		Spielfeld.t.removeActionListener(feld.ticc);
    		spiel.remove(feld);
		}
		this.reih =  this.reih * -1;
		feld = new Spielfeld(getHoehe(), getBreite(), this.reih);
		innerneustart();	
		feld.pnktstd1 = pnkt1;
		feld.pnktstd2 = pnkt2;
		feld.erzeugen();
		this.speerzauberer(feld, 10, 2);
		this.kaepferinit(feld, 2);
		Spielfeld.t.restart();
		spiel.add(feld);
		spiel.pack();
		feld.requestFocus();
	}
	
	/**
	 * listener for a fresh start from inside the game through an event like strike or passover
	 */
	private void innerneustart() {
		ActionListener innstrt = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
				spielstarten(feld.pnktstd1, feld.pnktstd2);
			}
	    };
		feld.status.addActionListener(innstrt);
	}
	
	/**
	 * the settings menu
	 */
	public void option() {
		JMenuItem aufloesung = new JMenuItem("Aufloesung");
		ActionListener aufloes = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    		auflfen();
	    		spiel.setAufl(breite, hoehe);
	    		if (feld != null) {
	    			spielstarten(feld.pnktstd1, feld.pnktstd2);
	    		} else {
	    			spielstarten(0, 0);
	    		}
		    }
	    };
	    aufloesung.addActionListener(aufloes);
	    spiel.optio.add(aufloesung);
	}
	
	/**
	 * dialog to change the resolution
	 */
	public void auflfen() {
		int index = 1;
		if (feld != null) {
			index = (feld.x - 640) / 160;
		}
		Object[] aufloesngn = {"640x480", "800x600", "1024x768"};
		Object gewltauflsgn = JOptionPane.showInputDialog(null, "\nAenderungen werden erst bei starten einer neuen Runde wirksam\n\nverfuegbare Aufloesungen:", "XxY aendern",
				JOptionPane.DEFAULT_OPTION, null, aufloesngn, aufloesngn[index]);
		if (gewltauflsgn == "640x480") {
			setBreite(640);
			setHoehe(480);
		} else if (gewltauflsgn == "800x600") {
			setBreite(800);
			setHoehe(600);
		} else if (gewltauflsgn == "1024x768") {
			setBreite(1024);
			setHoehe(768);
		} else if (gewltauflsgn != null) {
			JOptionPane.showMessageDialog(spiel, "Party, Party ein unbekannter Fehler -->" + gewltauflsgn, "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @param feld the playground for Kaempfer (^^)
	 * @param kanz the amount of Kaempfer to be placed
	 */
	public void kaepferinit(Spielfeld feld, int kanz) { //kanz not yet implemented
		spieler1 = new Kaempfer((Double)this.speinst1[0], (Double)this.speinst1[1], (Double)this.speinst1[2], 
				(Double)this.speinst2[3], (String)this.speinst1[4], new Color(((this.reih + 1) / 2) * 200, 0, 200));
		spieler1.setKaempfer(new Vertex(getBreite() / 10.2, getHoehe() / 2.6), 1, breite + hoehe);
		feld.kaempferL.add(spieler1);
		for (int i = 1; i < kanz; i++) {
			spieler2 = new Kaempfer(this.speinst2, new Color((((this.reih * -1) + 1) / 2) * 200, 0, 200));
			spieler2.setKaempfer(new Vertex(getBreite() / 1.11, getHoehe() / 1.1), -1, breite + hoehe);
			feld.kaempferL.add(spieler2);
		}
	}
	
	/**
	 * wizard to cast the Speere at the right positions on the field
	 * @param feld the field
	 * @param anz the amount of Speere
	 * @param fanz the amount of fields
	 */
	public void speerzauberer(Spielfeld feld, int anz, int fanz) { //fanz not yet implemented
		for (int i = 0; i < anz; i++) {
			Speer sp = new Speer();
			if (i < (anz / 2)) {
				sp.setSpeer(new Vertex(breite / 6.2, (hoehe / 2.67) + ra.nextInt(10)), 85 + ra.nextInt(10), 1, breite + hoehe);
			} else if (fanz > 1) {
				sp.setSpeer(new Vertex(breite / 1.21, (hoehe / 1.13)  + ra.nextInt(10)), 85 + ra.nextInt(10), -1, breite + hoehe);
			}
			feld.speereL.add(sp);
		}
	}
	
	/**
	 * the {@link Spielinit#hoehe hoehe} to set
	 * @param hoehe
	 */
	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}
	
	/**
	 * 
	 * @return the {@link Spielinit#hoehe hoehe}
	 */
	public int getHoehe() {
		return hoehe;
	}
	
	/**
	 * the {@link Spielinit#breite breite} to set
	 * @param breite
	 */
	public void setBreite(int breite) {
		this.breite = breite;
	}
	
	/**
	 * 
	 * @return the {@link Spielinit#breite breite}
	 */
	public int getBreite() {
		return breite;
	}
	
	/**
	 * the {@link Spielinit#name name} to set
	 * @param name
	 */
	public static void setName(String name) {
		Spielinit.name = name;
	}
	
	/**
	 * 
	 * @return the {@link Spielinit#name name}
	 */
	public static String getName() {
		return name;
	}
}