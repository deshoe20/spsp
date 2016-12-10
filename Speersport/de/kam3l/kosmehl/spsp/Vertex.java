package de.kam3l.kosmehl.spsp;


/**
 * class of two points in the 2D-space
 * @author Keks0r
 *
 */
public class Vertex {
	/** to describe a special point's details<br>this parameter is optional **/
	String bezeichnung;
	/** the numeral amount of units on the x-axis **/
	double xwert;
	/** the numeral amount of units on the x-axis **/
	double ywert;
	
	/**
	 * @param xwert
	 * @param ywert
	 * @param bezeichnung (optional)
	 */
	public Vertex(String bezeichnung, double xwert, double ywert) {
		this.bezeichnung = bezeichnung;
		this.xwert = xwert;
		this.ywert = ywert;
	}
	
	/**
	 * @param xwert
	 * @param ywert
	 */
	public Vertex(double xwert, double ywert) {
		this.xwert = xwert;
		this.ywert = ywert;
	}
	
	public String toString() {
		return (int)xwert + ", " + (int)ywert;
	}
	
	/**
	 * The function addi adds a double xwert to the Vertex' xwert and<br>a double ywert to the Vertex' ywert.
	 * @param xwert - the numeral amount of units on the x-axis
	 * @param ywert - the numeral amount of units on the x-axis
	 * @return {@link Vertex Vertex}
	 */
	public Vertex addi(double xwert, double ywert) {
		this.xwert = this.xwert + xwert;
		this.ywert = this.ywert + ywert;
		return this;
	}
	
	/**
	 * The function subt subtracts a double xwert from the Vertex' xwert and<br>a double ywert from the Vertex' ywert.
	 * @param xwert - the numeral amount of units on the x-axis
	 * @param ywert - the numeral amount of units on the x-axis
	 * @return {@link Vertex Vertex}
	 */
	public Vertex subt(double xwert, double ywert) {
		this.xwert = this.xwert - xwert;
		this.ywert = this.ywert - ywert;
		return this;
	}
	
	/**
	 * The function skaaddi adds a double skalar to both Vertex' xwert and ywert.
	 * @param skalar a double constant
	 * @return {@link Vertex Vertex}
	 */
	public Vertex skaaddi(double skalar) {
		this.xwert = this.xwert + skalar;
		this.ywert = this.ywert + skalar;
		return this;
	}
	
	/**
	 * The function skasubt subtracts a double skalar from both Vertex' xwert and ywert.
	 * @param skalar a double constant
	 * @return {@link Vertex Vertex}
	 */
	public Vertex skasubt(double skalar) {
		this.xwert = this.xwert - skalar;
		this.ywert = this.ywert - skalar;
		return this;
	}
	
	/**
	 * The function skamul multiplies a double skalar with both Vertex' xwert and ywert.
	 * @param skalar a double constant
	 * @return {@link Vertex Vertex}
	 */
	public Vertex skamul(double skalar) {
		this.xwert = this.xwert * skalar;
		this.ywert = this.ywert * skalar;
		return this;
	}
	
	/**
	 * The function mulskal returns the inner-product of the point with another Vertex point.
	 * @param p {@link Vertex Vertex}
	 * @return inner-product of this * p
	 */
	public double mulskal(Vertex p) {
		return this.xwert * p.xwert + this.ywert * p.ywert;
	}
	
	/**
	 * The function abstandP calculates the distance between this Vertex point and p.
	 * @param p {@link Vertex Vertex}
	 * @return distance to p
	 */
	public double abstandP(Vertex p) {
		return Math.sqrt(Math.pow((p.xwert - this.xwert), 2) + Math.pow((p.ywert - this.ywert), 2));
	}
	
	/**
	 * The function getSteigungP returns the gradient between this Vertex point and p.
	 * @param p {@link Vertex Vertex}
	 * @return gradient to p
	 */
	public double getSteigungP(Vertex p) {
		return (this.ywert - p.ywert) / (this.xwert - p.xwert); 
	}
	
	/**
	 * not properly working - should return rotation degree
	 * @param p {@link Vertex Vertex}
	 * @return rotation degree
	 */
	public double getDrehPP(Vertex p) {
		if (this.equals(p)) {
			return 0;
		}
		return Math.atan((p.ywert - this.ywert) / (p.xwert - this.xwert)) * 180 / Math.PI;
	}
	
	/**
	 * The function drehenPP rotates this Vertex point around p for grad degrees.
	 * @param p {@link Vertex Vertex}
	 * @param grad in degrees
	 * @return the new Vertex rotated around p for grad degree
	 */
	public Vertex drehenPP(Vertex p, double grad) {
		double x, y;
		if (grad == 0) {
			return this;
		}
		grad = grad * (Math.PI / 180);
		x = (this.xwert - p.xwert) * Math.cos(grad) + (this.ywert - p.ywert) * Math.sin(grad) + p.xwert;
		y = (this.ywert - p.ywert) * Math.cos(grad) - (this.xwert - p.xwert) * Math.sin(grad) + p.ywert;
		return new Vertex(x, y);
	}
	
	@Override
	public boolean equals(Object that) {
		if (that instanceof Vertex) {
			Vertex other = (Vertex)that;
			return ((this.xwert == other.xwert) && (this.ywert == other.ywert));
		}
		return false;
	}
}
