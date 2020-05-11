package de.kam3l.kosmehl.spsp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * class of the mainframe of the Speersport Duell game
 * @author Keks0r
 *
 */
public class Spielfeldfenster extends JFrame{
	/** variable to suppress a warning **/
	private static final long serialVersionUID = 1L;
	/** the headline of the frame **/
	String name;
	/** menu **/
	public JMenuBar menub = new JMenuBar();
	/** menu **/
	public JMenu menuu = new JMenu("menu");
	/** menu **/
	public JMenu optio = new JMenu("options");
	/** menu **/
	public JMenuItem uebera = new JMenuItem("UeberKasten");
	/** menu **/
	public JMenuItem ueberspsp = new JMenuItem("UeberDasSpiel");
	/** menu **/
	public JMenuItem uebertast = new JMenuItem("Steuerung");
	/** menu **/
	public JMenuItem aendschronik = new JMenuItem("AenderungsChronik");
	/** resolution **/
	private int xaufl, yaufl;

	/**
	 * @param name frame-headline
	 * @param auflsngx x-axis resolution
	 * @param auflsngy y-axis resolution
	 */
	public Spielfeldfenster(String name, int auflsngx, int auflsngy) {
		this.name = name;
		setAufl(auflsngx, auflsngy);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/** 
	 * set/reset of the resolution
	 * @param auflx x-axis resolution
	 * @param aufly y-axis resolution
	 */
	public void setAufl(int auflx, int aufly) {
		int x, y;
		this.xaufl = auflx;
		this.yaufl = aufly;
		x = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		y = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		x = (x / 2) - (xaufl / 2);
		y = (y / 2) - (yaufl / 2);
		this.setLocation(x, y);
	}	
	
	/**
	 * initialization of the frames fields
	 */
	public void erzeugen() {
		menub.add(menuu);
		menub.add(optio);
		ActionListener uberspsp = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				javax.swing.ImageIcon uberspspicon = new javax.swing.ImageIcon(getClass().getResource("/bilder/duell_pikt.gif"));
				String ubertext = "Speersport-Duell:\n\n" +
						"Beim Speerduell stehen sich zwei Kaempfer in zwei,\n" +
						"direkt aneinanderangrenzenden, 4m auf 4m grossen, Feldern\n" +
						"gegenueber und versuchen sich gegenseitig mit den Speeren\n" +
						"Kniescheibe abwaerts zu treffen.\n\n" +
						"Als Treffer zaehlt nur eine Beruehrung mit der Spitze des Speeres,\n" +
						"Schaft zaehlt also nicht, auerdem muss der Speer noch in Bewegung\n" +
						"gewesen sein.\n\n" +
						"Zusaetzlich gibt es noch den Linienuebertritt, dieser zaehlt aber nur\n" +
						"einen halben Punkt.\n\n" +
						"Im Tunier gewinnt derjenige, welcher zuerst 2 oder mehr Punkte hat\n" +
						"- im Finale der mit 3.\n\n" +
						"";
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    		javax.swing.JOptionPane.showMessageDialog(null, ubertext, "die UberSpSp Kiste",
	    				javax.swing.JOptionPane.PLAIN_MESSAGE, uberspspicon);
		    }
	    };
	    ActionListener ubertast = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				javax.swing.ImageIcon steuicon = null;
				Object steuerung[][] = {
						{"NachOben", "W", "PfeilHoch"},
						{"NachUnten", "S", "PfeilRunter"},
						{"NachLinks", "A", "PfeilLinks"},
						{"NachRechts", "D", "PfeilRechts"},
						{"Aufheben", "E", "Enter"},
						{"zielHoch", "2", "NUM 4"},
						{"zielRunter", "1", "NUM 1"},
						{"Spannen/Werfen", "3", "NUM 5"},
						{"Springen1", "F", "NUM 0"},
						{"Springen2", "R", "NUM 2"}
					};
				String steueruber[] = {
						"Funktion", "Spieler1", "Spieler2"
				};
				javax.swing.JTable tasten = new javax.swing.JTable(steuerung, steueruber);
				tasten.getColumn("Funktion").setPreferredWidth(110);
				tasten.getColumn("Spieler1").setPreferredWidth(110);
				tasten.getColumn("Spieler2").setPreferredWidth(110);
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    		javax.swing.JOptionPane.showMessageDialog(null, tasten, "Steuerung",
	    				javax.swing.JOptionPane.PLAIN_MESSAGE, steuicon);
		    }
	    };
	    ActionListener ubera = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				javax.swing.ImageIcon ubericon = new javax.swing.ImageIcon(getClass().getResource("/bilder/ubericon.gif"));
				String ubertext = "Achtung hier koennten Kekse drin sein!\n\n" +
						"Speersport-Duell\nvon Benjamin Kosmehl\nbkosm001 - MtrklNr.: 459639\n\n" +
						"dies ist ein JavaProg-lernen-Projekt - SS09 - FH-WI\n\n\nwenn ihr keine Fehler findet\n" +
						"koennt ihr mir gerne Bescheid geben\n- dann ist sicher was kaputt\n\nund Kaefer duerft ihr behalten" +
						"\n\nepost: deshoe20_at_gmail.com";
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    		javax.swing.JOptionPane.showMessageDialog(null, ubertext, "die UberA Kiste",
	    				javax.swing.JOptionPane.PLAIN_MESSAGE, ubericon);
		    }
	    };
	    ActionListener andschronik = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				javax.swing.ImageIcon aendicon = null;
				String aendtext = 
						"speere koennen abprallen\n" +
						"- nonHit mit P(50) am koerper und mit P(20) an den beinen\n\n" +
						"wurfgeschwindigkeit bei beiden gleich\n" +
						"der rechte bewegt sich aber schneller\n\n" +
						"flugverhalten der speere angepasst (werden langsamer)\n" +
						"\n" +
						"tastaturbelegung geaendert:\n" +
						"\n" +
						"anzeigen eingerichtet\n\n" +
						"immo optimiert fuer 800x600 --> wird aber noch gefixt";
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    		javax.swing.JOptionPane.showMessageDialog(null, aendtext, "AenderungsChronik",
	    				javax.swing.JOptionPane.PLAIN_MESSAGE, aendicon);
		    }
	    };
	    ueberspsp.addActionListener(uberspsp);
	    uebertast.addActionListener(ubertast);
	    uebera.addActionListener(ubera);
	    aendschronik.addActionListener(andschronik);
	    menub.add(ueberspsp);
	    menub.add(uebertast);
		menub.add(uebera);
		menub.add(aendschronik);
		this.setTitle(this.name);
		this.setJMenuBar(menub);
		this.setVisible(true);
	}
	
	/**
	 * initialization of the menu
	 */
	public void menuInit() {
		JMenuItem pausieren = new JMenuItem("Pausieren");
		JMenuItem weiter = new JMenuItem("Weiter");
		ActionListener start = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (!(Spielfeld.t.isRunning())) {
					Spielfeld.t.restart();
				}
			}
	    };
	    ActionListener stop = new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
	    		try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
	    	}
	    };
	    pausieren.addActionListener(stop);
	    weiter.addActionListener(start);
	    ActionListener schliesser = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
	    			Thread.sleep(10);
	    		} catch (InterruptedException e) {
	    		}
	    		Spielfeld.t.stop();
				dispose();
			}
	    };
		JMenuItem schliessen = new JMenuItem("Schliessen");
		schliessen.addActionListener(schliesser);
		menuu.add(pausieren);
	    menuu.add(weiter);
		menuu.add(schliessen);
	}
}
