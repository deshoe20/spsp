package de.kam3l.kosmehl.spsp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * class of the Speersport-Duell GUI - mainly consisting of a JPanel
 * 
 * @author Keks0r
 *
 */
public class Spielfeld extends JPanel implements MouseListener, KeyListener {
	/** variable to suppress a warning **/
	private static final long serialVersionUID = 1L;
	/** the list of the simple geometric objects **/
	List<GeoObjekt> goL = new ArrayList<GeoObjekt>();
	/** the list of the {@link Kaempfer Kaempfer} on the field **/
	List<Kaempfer> kaempferL = new ArrayList<Kaempfer>();
	/** the list for the {@link Speer Speere} **/
	List<Speer> speereL = new ArrayList<Speer>();
	/** the greens fields for the Kaempfers - need for passover detection **/
	private List<GeoObjekt> spielflaeche = new ArrayList<GeoObjekt>();
	/** resolution **/
	int x, y;
	/** the score-count **/
	public double pnktstd1 = 0, pnktstd2 = 0;
	/** score display **/
	JLabel punkte;
	/** message display **/ // not working
	JLabel anzeige;
	/** schummel button **/
	public JButton status = new JButton("13");
	int reih = -1;
	int counter = 1337;
	String aktanzg = "";
	private boolean bmode = false;
	private Vertex botStartP = null;
	private int flv = 0;
	private boolean mussLeerLaufen = false;

	/** an event-listener to do nothing **/ // yeah
	static ActionListener tuenix = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		}
	};
	/** an event-listener to trigger all changes on the playground **/
	ActionListener ticc = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			tick();
		}
	};
	/** the timer to control the flow of the game **/
	public static Timer t = new Timer(20, tuenix);

	/**
	 * construct the field with the resolution
	 * 
	 * @param hoehe
	 *            y-axis resolution
	 * @param breite
	 *            x-axis resolution
	 */
	public Spielfeld(int hoehe, int breite, int anderreihe) {
		this(hoehe, breite, anderreihe, false);
	}

	/**
	 * construct the field with the resolution
	 * 
	 * @param hoehe
	 *            y-axis resolution
	 * @param breite
	 *            x-axis resolution
	 */
	public Spielfeld(int hoehe, int breite, int anderreihe, boolean bmode) {
		x = breite;
		y = hoehe;
		this.setLayout(null);
		this.reih = anderreihe;
		this.setPreferredSize(new Dimension(x, y));
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.initHintergrund();
		this.setFocusable(true);
		this.bmode = bmode;
		t.addActionListener(ticc);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (GeoObjekt go : goL) {
			go.paintMeTo(g);
		}
		for (Speer sp : speereL) {
			sp.paintMeTo(g);
		}
		for (Kaempfer k : kaempferL) {
			k.paintMeTo(g);
		}
	}

	/**
	 * initialization
	 */
	public void erzeugen() {
		counter = 1337;
		anzeige = new JLabel("", javax.swing.SwingConstants.CENTER);
		anzeige.setFont(new Font("Verdana", Font.BOLD, (x + y) / 58));
		anzeige.setSize((int) (x / 1.5), (int) (y / 10));
		anzeige.setLocation((int) ((x / 2) - (anzeige.getWidth() / 2)), (int) (y / 3.8));
		anzeige.setVisible(true);
		punkte = new JLabel(" -  |  - ", javax.swing.SwingConstants.CENTER);
		punkte.setFont(new Font("Verdana", Font.BOLD, (x + y) / 58));
		punkte.setSize((int) (x / 4), (int) (y / 10));
		punkte.setLocation(x / 2 - (punkte.getWidth() / 2), y / 9 - (punkte.getHeight() / 2));
		punkte.setBorder(BorderFactory.createLineBorder((new Color(0, 0, 0))));
		punkte.setVisible(true);
		anzeig("");
		pnktnstln();
		this.add(anzeige);
		this.add(punkte);
	}

	/**
	 * displays the score
	 */
	public void pnktnstln() {
		java.text.DecimalFormat enkst = new java.text.DecimalFormat("0.0");
		String aktpkt = " " + enkst.format(pnktstd1) + "  |  " + enkst.format(pnktstd2) + " ";
		if ((punkte == null) && (pnktstd1 == 0) && (pnktstd2 == 0)) {
			punkte.setText(" -  |  - ");
		} else {
			punkte.setText(aktpkt);
		}
		if ((pnktstd1 >= 10) || (pnktstd2 >= 10)) {
			ende();
		}
	}

	/**
	 * displays a message given by anzg
	 * 
	 * @param anzg
	 *            a message String
	 */
	public void anzeig(String anzg) {
		anzeige.setText(anzg);
	}

	/**
	 * end the round
	 */
	public void ende() {
		int knr = 1;
		if (pnktstd2 > pnktstd1) {
			knr = 2;
		}
		javax.swing.JOptionPane.showMessageDialog(null, "Spieler " + knr + " hat gewonnen", "Spiel vorbei",
				javax.swing.JOptionPane.INFORMATION_MESSAGE, null);
		pnktstd1 = 0;
		pnktstd2 = 0;
	}

	/**
	 * initialization of the background
	 */
	public void initHintergrund() {
		Hintergrund h = new Hintergrund(new Vertex(x, y));
		goL.add(h);
		this.spielflaeche = h.getGeoL();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int klickX, klickY;
		klickX = arg0.getX();
		klickY = arg0.getY();
		System.out.println("\n" + klickX + ", " + klickY);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int aktspieler = (this.reih + 1) / 2;
		switch (arg0.getKeyCode()) {

		// spieler1

		case KeyEvent.VK_F:
			aktionsmanager(50, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_E:
			aktionsmanager(40, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_R:
			aktionsmanager(60, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_W:
			bewegsmanager(1, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_S:
			bewegsmanager(2, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_A:
			bewegsmanager(3, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_D:
			bewegsmanager(4, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_3:
			this.kaempferL.get(1 - aktspieler).setSpann(2);
			break;
		case KeyEvent.VK_2:
			this.kaempferL.get(1 - aktspieler).setZiel2(1);
			break;
		case KeyEvent.VK_1:
			this.kaempferL.get(1 - aktspieler).setZiel2(-1);
			break;

		// spieler2

		case KeyEvent.VK_NUMPAD0:
			if (!this.bmode)
				aktionsmanager(50, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_NUMPAD2:
			if (!this.bmode)
				aktionsmanager(60, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_UP:
			if (!this.bmode)
				bewegsmanager(1, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_DOWN:
			if (!this.bmode)
				bewegsmanager(2, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_LEFT:
			if (!this.bmode)
				bewegsmanager(3, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_RIGHT:
			if (!this.bmode)
				bewegsmanager(4, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_NUMPAD4:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).setZiel2(-1);
			break;
		case KeyEvent.VK_NUMPAD1:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).setZiel2(1);
			break;
		case KeyEvent.VK_ENTER:
			if (!this.bmode)
				aktionsmanager(40, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_NUMPAD5:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).setSpann(2);
			break;

		default:

			System.out.println("taste gedrueckt dies net gibbet: " + arg0.getKeyCode());

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int aktspieler = (this.reih + 1) / 2;
		switch (arg0.getKeyCode()) {

		// spieler1

		case KeyEvent.VK_E:
			aktionsmanager(41, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_W:
			bewegsmanager(-1, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_S:
			bewegsmanager(-2, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_A:
			bewegsmanager(-3, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_D:
			bewegsmanager(-4, this.kaempferL.get(1 - aktspieler));
			break;
		case KeyEvent.VK_3:
			this.kaempferL.get(1 - aktspieler).werfen();
			break;
		case KeyEvent.VK_2:
			this.kaempferL.get(1 - aktspieler).setZiel2(0);
			break;
		case KeyEvent.VK_1:
			this.kaempferL.get(1 - aktspieler).setZiel2(0);
			break;

		// spieler2

		case KeyEvent.VK_UP:
			if (!this.bmode)
				bewegsmanager(-1, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_DOWN:
			if (!this.bmode)
				bewegsmanager(-2, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_LEFT:
			if (!this.bmode)
				bewegsmanager(-3, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_RIGHT:
			if (!this.bmode)
				bewegsmanager(-4, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_NUMPAD4:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).setZiel2(0);
			break;
		case KeyEvent.VK_NUMPAD1:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).setZiel2(0);
			break;
		case KeyEvent.VK_ENTER:
			if (!this.bmode)
				aktionsmanager(41, this.kaempferL.get(aktspieler));
			break;
		case KeyEvent.VK_NUMPAD5:
			if (!this.bmode)
				this.kaempferL.get(aktspieler).werfen();
			break;

		default:

			System.out.println("taste gedrueckt dies net gibbet: " + arg0.getKeyCode());

		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * method to control some action-requests
	 * 
	 * @param wstatus
	 *            desired state for the Kaempfer
	 * @param k
	 *            specific Kaempfer
	 */
	private void aktionsmanager(int wstatus, Kaempfer k) {
		if (((k.getStatus() / 10) < 4) || (((wstatus / 10) == 4)) && ((k.getStatus() / 10) == 4)) {
			if ((wstatus == 60) && (k.a1spL.isEmpty() || k.a2spL.isEmpty())) {

				System.out.println("in jeder hand speere zum springen");

				return;
			}
			k.setStatus(wstatus);
		}
	}

	/**
	 * method to control the movement
	 * 
	 * @param rich
	 *            desired direction
	 * @param k
	 *            specific Kaempfer
	 */
	private void bewegsmanager(int rich, Kaempfer k) {
		switch (rich) {
		case 1:
			k.setByricht(-1);
			break;
		case 2:
			k.setByricht(1);
			break;
		case 3:
			k.setBxricht(-1);
			break;
		case 4:
			k.setBxricht(1);
			break;
		case -1:
			k.setByricht(0);
			break;
		case -2:
			k.setByricht(0);
			break;
		case -3:
			k.setBxricht(0);
			break;
		case -4:
			k.setBxricht(0);
			break;
		default:
			System.out.println("keine korrekte richtung");
		}
	}

	/**
	 * passover detection method
	 * 
	 * @param k
	 *            specific Kaempfer
	 * @return whether a passover has happend or not
	 */
	public boolean uebertritt(Kaempfer k) {
		int knr = 0;
		if (k.getRicht() == -1) {
			knr = 1;
		}
		if ((spielflaeche.get(knr).hasWithin(k.bu1.loecke)) && (spielflaeche.get(knr).hasWithin(k.bu1.roecke))
				&& (spielflaeche.get(knr).hasWithin(k.bu2.luecke)) && (spielflaeche.get(knr).hasWithin(k.bu2.ruecke))
				&& !(spielflaeche.get(5).hasWithin(k.bu1.loecke)) && !(spielflaeche.get(5).hasWithin(k.bu1.roecke))
				&& !(spielflaeche.get(5).hasWithin(k.bu2.luecke)) && !(spielflaeche.get(5).hasWithin(k.bu2.ruecke))) {
			return false;
		}
		if ((k.getStatus() / 10) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * the triggering function for all actions on the field
	 */
	public void tick() {
		if (counter == 1337) {

			/**
			 * bot actions
			 */
			if (this.bmode) {
				int aktspieler = (this.reih + 1) / 2;
				Kaempfer bot = this.kaempferL.get(aktspieler);
				int knr = 0;
				boolean ruecklauf = false;
				if (this.botStartP == null) {
					this.botStartP = new Vertex(bot.getApunkt());
				}
				if (bot.getRicht() == -1) {
					knr = 1;
				}
				// speere holen?
				if (bot.a1spL.isEmpty() && bot.a2spL.isEmpty() && !mussLeerLaufen) {
					if (bot.getApunkt().equals(this.botStartP, 15))
						aktionsmanager(40, bot);
					else {
						ruecklauf = true;
						if (Math.abs(bot.getApunkt().xwert - this.botStartP.xwert) >= 15) {
							if (bot.getApunkt().xwert > this.botStartP.xwert)
								bot.setBxricht(-1);
							else
								bot.setBxricht(1);
						}
						if (Math.abs(bot.getApunkt().ywert - this.botStartP.ywert) >= 15) {
							if (bot.getApunkt().ywert > this.botStartP.ywert)
								bot.setByricht(-1);
							else
								bot.setByricht(1);
						}
					}
				} else {
					// am buecken?
					if (bot.getStatus() == 40) {
						aktionsmanager(41, bot);
					} else {
						// bewegen & springen?
						if ((((bot.getBxricht() == 0) || (bot.getByricht() == 0))
								&& (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 20))
								|| (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 90)) {
							bot.setBxricht(ThreadLocalRandom.current().nextInt(-1, 1 + 1));
							bot.setByricht(ThreadLocalRandom.current().nextInt(-1, 1 + 1));
						} else if (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 80) {
							aktionsmanager(50, bot);
						} else if (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 92) {
							aktionsmanager(60, bot);
						}
						// werfen?
						if (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 96 && !mussLeerLaufen) {
							bot.setSpann(2);
							if (bot.a1spL.size() == 1) {
								bot.setSpann(1);
							}
						}
						if ((bot.getSpann(2) > 0) && (ThreadLocalRandom.current().nextInt(0, 1000 + 1) > 985)
								&& !mussLeerLaufen) {
							bot.werfen();
						}
						// zielen?
						if (ThreadLocalRandom.current().nextInt(0, 100 + 1) > 85 && !mussLeerLaufen) {
							bot.setZiel2(ThreadLocalRandom.current().nextInt(-1, 1 + 1));
							if (bot.a1spL.size() == 1) {
								bot.setZiel1(ThreadLocalRandom.current().nextInt(-1, 1 + 1));
							}
						}
					}
				}
				if (ruecklauf) {
					if (((bot.getApunkt().xwert <= (this.botStartP.xwert)) && bot.getRicht() == 1)
							|| ((bot.getApunkt().xwert >= (this.botStartP.xwert)) && bot.getRicht() == -1))
						bot.setBxricht(0);
					if (((bot.getApunkt().ywert <= (this.botStartP.ywert)) && bot.getRicht() == 1)
							|| ((bot.getApunkt().ywert >= (this.botStartP.ywert)) && bot.getRicht() == -1))
						bot.setByricht(0);
				} else if ((bot.getBxricht() != 0) || (bot.getByricht() != 0)) {
					Vertex nRcht = spielflaeche.get(knr).isCloseOut(bot.getApunkt(), this.getHeight() / 11,
							bot.getBxricht(), bot.getByricht());
					bot.setBxricht((int) nRcht.xwert);
					bot.setByricht((int) nRcht.ywert);
				}
				if (bot.isMaxGebueckt()) {
					this.flv++;
					if (this.flv > 3) {
						mussLeerLaufen = true;
						this.flv = 0;
					}
				}
				if (mussLeerLaufen && (ThreadLocalRandom.current().nextInt(0, 1000 + 1) > 997)) {
					mussLeerLaufen = false;
				}
			}
			/**
			 * 
			 */

			for (Kaempfer k : this.kaempferL) {
				if ((k.getStatus() / 10) == 4) {
					k.spaufheben(this.speereL);
				}
				if ((k.getStatus() / 10) == 5) {
					k.springen1();
				}
				if ((k.getStatus() / 10) == 6) {
					k.springen2();
				}
				if (((k.getStatus() / 10) != 4) && ((k.getStatus() / 10) != 6)) {
					k.bewege();
				}
				k.spannen();
				if ((k.getZiel1() != 0) || (k.getZiel2() != 0)) {
					k.zielen();
				}
				if (uebertritt(k)) {
					counter = 80;
					if (k.getRicht() == 1) {
						aktanzg = "Uebertritt links";
					} else {
						aktanzg = "Uebertritt rechts";
					}
					if (k.getRicht() * this.reih == 1) {
						this.pnktstd2 = this.pnktstd2 + 0.5;
						this.pnktnstln();
					} else {
						this.pnktstd1 = this.pnktstd1 + 0.5;
						this.pnktnstln();
					}
				}
			}
			for (Speer sp : this.speereL) {
				if ((sp.beweg.xwert != 0) || (sp.beweg.ywert != 0)) {
					sp.move();
					for (Kaempfer k : this.kaempferL) {
						if (sp.getroffen(k)) {
							counter = 80;
							if (k.getRicht() == 1) {
								aktanzg = "Treffer links";
							} else {
								aktanzg = "Treffer rechts";
							}
							if (k.getRicht() * this.reih == 1) {
								this.pnktstd2 = this.pnktstd2 + 1;
								this.pnktnstln();
							} else {
								this.pnktstd1 = this.pnktstd1 + 1;
								this.pnktnstln();
							}
						}
					}
				}
			}
		} else if (counter > 0) {
			counter--;
		} else {
			this.status.doClick(); // finstere magie
		}
		this.repaint();
		if (counter != 1337) {
			anzeig(aktanzg + " " + counter);
		}
	}
	// leider keine zeit mehr fuer ausfuehrliche kommentare - aber bei dem gut
	// strukturiereten code gar net noetig (rofl)

	public boolean isBmode() {
		return bmode;
	}

	public void setBmode(boolean bmode) {
		this.bmode = bmode;
	}
}
