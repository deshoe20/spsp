/**
 * 
 */
package de.kam3l.kosmehl.spsp;

import java.util.Map;
import java.util.logging.Logger;

import javax.swing.KeyStroke;

/**
 * @author Hanz
 *
 */
public class TastenBeleg {

	private final static Logger LOGGER = Logger.getLogger(TastenBeleg.class.getName());

	public Map<Spielfeld.aktionen, KeyStroke> tastenKarte;

	@SuppressWarnings("unused")
	private TastenBeleg() {
	}

	public TastenBeleg(Map<Spielfeld.aktionen, KeyStroke> tasten) {
		if (tasten.size() != Spielfeld.aktionen.values().length) {
			LOGGER.severe("Tastenbelegung unvollständig!");
		} else {
			this.tastenKarte = tasten;
		}
	}

}
