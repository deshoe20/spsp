package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * class to initiate the Speersport Duell game
 * 
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
	ActionListener bstarter = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			spielstarten(0, 0, true);
		}
	};

	/** start-background *000 */
	JLabel startbild = new JLabel(new ImageIcon(getClass().getResource("/bilder/frged.jpg")));
	int reih = 0;

	// config-->

	/** mainframe headline **/
	private static String name = "Speersport Duell";
	/** x-axis resolution **/
	private int breite = 800;
	/** y-axis resolution **/
	private int hoehe = 600;

	// groesse geschwindigkeit sprungkraft wurfkraft name
	/**
	 * config-set for the Kaempfers - according to the pattern
	 * size|speed|jumppower|throwpower|name
	 **/
	Object[] speinst1 = { 1.0, 1.0, 1.0, 0.6, "Faust" };
	/**
	 * config-set for the Kaempfers - according to the pattern
	 * size|speed|jumppower|throwpower|name
	 **/
	Object[] speinst2 = { 1.0, 1.0, 1.0, 0.6, "Olm" };
	/**
	 * config-set for the Kaempfers - according to the pattern
	 * size|speed|jumppower|throwpower|name
	 **/
	Object[] speinst3 = { 0.9, 1.5, 1.0, 2.1, "Droddel" };
	/**
	 * config-set for the Kaempfers - according to the pattern
	 * size|speed|jumppower|throwpower|name
	 **/
	Object[] speinst4 = { 1.1, 1.7, 1.6, 2.4, "Alpaka" };

	/** player1 **/
	Kaempfer spieler1;
	@SuppressWarnings("serial")
	TastenBeleg tastenSp1 = new TastenBeleg(new HashMap<Spielfeld.aktionen, KeyStroke>() {
		{
			put(Spielfeld.aktionen.BEWEG_OBEN, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false));
			put(Spielfeld.aktionen.BEWEG_OBEN_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true));
			put(Spielfeld.aktionen.BEWEG_UNTEN, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false));
			put(Spielfeld.aktionen.BEWEG_UNTEN_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true));
			put(Spielfeld.aktionen.BEWEG_LINKS, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false));
			put(Spielfeld.aktionen.BEWEG_LINKS_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true));
			put(Spielfeld.aktionen.BEWEG_RECHTS, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false));
			put(Spielfeld.aktionen.BEWEG_RECHTS_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true));
			put(Spielfeld.aktionen.BUECKEN_RUNTER, KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false));
			put(Spielfeld.aktionen.BUECKEN_HOCH, KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true));
			put(Spielfeld.aktionen.ZIEL_HOCH, KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, false));
			put(Spielfeld.aktionen.ZIEL_HOCH_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, true));
			put(Spielfeld.aktionen.ZIEL_RUNTER, KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false));
			put(Spielfeld.aktionen.ZIEL_RUNTER_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, true));
			put(Spielfeld.aktionen.SPANNEN, KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, false));
			put(Spielfeld.aktionen.WERFEN, KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, true));
			put(Spielfeld.aktionen.SPRINGEN1, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false));
			put(Spielfeld.aktionen.SPRINGEN2, KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0, false));
		}
	});
	/** player2 **/
	Kaempfer spieler2;
	@SuppressWarnings("serial")
	TastenBeleg tastenSp2 = new TastenBeleg(new HashMap<Spielfeld.aktionen, KeyStroke>() {
		{
			put(Spielfeld.aktionen.BEWEG_OBEN, KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, false));
			put(Spielfeld.aktionen.BEWEG_OBEN_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, true));
			put(Spielfeld.aktionen.BEWEG_UNTEN, KeyStroke.getKeyStroke(KeyEvent.VK_K, 0, false));
			put(Spielfeld.aktionen.BEWEG_UNTEN_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_K, 0, true));
			put(Spielfeld.aktionen.BEWEG_LINKS, KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false));
			put(Spielfeld.aktionen.BEWEG_LINKS_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, true));
			put(Spielfeld.aktionen.BEWEG_RECHTS, KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, false));
			put(Spielfeld.aktionen.BEWEG_RECHTS_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, true));
			put(Spielfeld.aktionen.BUECKEN_RUNTER, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false));
			put(Spielfeld.aktionen.BUECKEN_HOCH, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true));
			put(Spielfeld.aktionen.ZIEL_HOCH, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0, false));
			put(Spielfeld.aktionen.ZIEL_HOCH_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0, true));
			put(Spielfeld.aktionen.ZIEL_RUNTER, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0, false));
			put(Spielfeld.aktionen.ZIEL_RUNTER_STOP, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0, true));
			put(Spielfeld.aktionen.SPANNEN, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0, false));
			put(Spielfeld.aktionen.WERFEN, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0, true));
			put(Spielfeld.aktionen.SPRINGEN1, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0, false));
			put(Spielfeld.aktionen.SPRINGEN2, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0, false));
		}
	});

	int tastensdSp1[] = { KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_LESS,
			KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE, KeyEvent.VK_F, KeyEvent.VK_R };
	int tastesdnSp2[] = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER,
			KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD2 };

	// <--config

	/**
	 * construct a new game
	 */
	public Spielinit() {
		JMenuItem starten = new JMenuItem("Spiel starten");
		JMenuItem bstarten = new JMenuItem("Botspiel starten");
		spiel = new Spielfeldfenster(getName(), breite, hoehe);
		spiel.erzeugen();
		starten.addActionListener(starter);
		bstarten.addActionListener(bstarter);
		spiel.menuu.add(starten);
		spiel.menuu.add(bstarten);
		spiel.menuInit();
		option();
		spiel.add(startbild);
		spiel.pack();
		reih = (reih + reih) - 1;
	}

	/**
	 * start the game with setting the startscore
	 * 
	 * @param pnkt1
	 *            score of player1
	 * @param pnkt2
	 *            score of player2
	 */
	void spielstarten(double pnkt1, double pnkt2) {
		spielstarten(pnkt1, pnkt2, false);
	}

	/**
	 * start the game with setting the startscore and optional rngjesus
	 * controlled bot
	 * 
	 * @param pnkt1
	 *            score of player1
	 * @param pnkt2
	 *            score of player2
	 */
	void spielstarten(double pnkt1, double pnkt2, boolean bot) {
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
		this.reih = this.reih; // TODO: rework and move into feld
		feld = new Spielfeld(getHoehe(), getBreite(), this.reih, bot);
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
	 * listener for a fresh start from inside the game through an event like
	 * strike or passover
	 */
	private void innerneustart() {
		ActionListener innstrt = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				spielstarten(feld.pnktstd1, feld.pnktstd2, feld.isBmode());
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
		Object[] aufloesngn = { "640x480", "800x600", "1024x768" };
		Object gewltauflsgn = JOptionPane.showInputDialog(null,
				"\nAenderungen werden erst bei starten einer neuen Runde wirksam\n\nverfuegbare Aufloesungen:",
				"XxY aendern", JOptionPane.DEFAULT_OPTION, null, aufloesngn, aufloesngn[index]);
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
			JOptionPane.showMessageDialog(spiel, "Party, Party ein unbekannter Fehler -->" + gewltauflsgn, "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 
	 * @param feld
	 *            the playground for Kaempfer (^^)
	 * @param kanz
	 *            the amount of Kaempfer to be placed
	 */
	public void kaepferinit(Spielfeld feld, int kanz) {
		// FIXME: alle dabutt die dönen diamanten
		Vertex linkeSpielerEcke = new Vertex(getBreite() / 10.2, getHoehe() / 2.6);
		Vertex rechteSpielerEcke = new Vertex(getBreite() / 1.11, getHoehe() / 1.1);
		spieler1 = new Kaempfer((Double) this.speinst1[0], (Double) this.speinst1[1], (Double) this.speinst1[2],
				(Double) this.speinst2[3], (String) this.speinst1[4], new Color(((this.reih + 1) / 2) * 200, 0, 200));
		spieler1.tastenBeleg = tastenSp1;
		spieler1.setKaempfer(rechteSpielerEcke, -1, breite + hoehe);
		feld.kaempferL.add(spieler1);
		for (int i = 1; i < kanz; i++) { // so ein blödsinn
			spieler2 = new Kaempfer(this.speinst2, new Color((((this.reih * -1) + 1) / 2) * 200, 0, 200));
			spieler2.tastenBeleg = tastenSp2;
			spieler2.setKaempfer(linkeSpielerEcke, 1, breite + hoehe);
			feld.kaempferL.add(spieler2);
		}
		feld.initSteuerung();
	}

	/**
	 * wizard to cast the Speere at the right positions on the field
	 * 
	 * @param feld
	 *            the field
	 * @param anz
	 *            the amount of Speere
	 * @param fanz
	 *            the amount of fields
	 */
	public void speerzauberer(Spielfeld feld, int anz, int fanz) { // fanz not
																	// yet
																	// implemented
		for (int i = 0; i < anz; i++) {
			Speer sp = new Speer();
			if (i < (anz / 2)) {
				sp.setSpeer(new Vertex(breite / 6.2, (hoehe / 2.67) + ra.nextInt(10)), 85 + ra.nextInt(10), 1,
						breite + hoehe);
			} else if (fanz > 1) {
				sp.setSpeer(new Vertex(breite / 1.21, (hoehe / 1.13) + ra.nextInt(10)), 85 + ra.nextInt(10), -1,
						breite + hoehe);
			}
			feld.speereL.add(sp);
		}
	}

	/**
	 * the {@link Spielinit#hoehe hoehe} to set
	 * 
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
	 * 
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
	 * 
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
