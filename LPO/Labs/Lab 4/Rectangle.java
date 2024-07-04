package lab04_12_02;

/*
 * Implementa rettangoli con lati paralleli agli assi
 */
public class Rectangle implements Shape {
	/* invariant width > 0 && height > 0 */
	public static final double defaultSize = 1;
	private double width = Rectangle.defaultSize;
	private double height = Rectangle.defaultSize;

	private final Point center = new Point();

	private static void checkSide(double Side) {
		if(Side <= 0)
			throw new IllegalArgumentException();
	}
	/*
	 * Rettangolo con centro sull'origine degli assi
	 */
	private Rectangle(double width, double height) {
	    // completare
		checkSide(width);
		checkSide(height);
		this.width = width;
		this.height = height;
	}

	private Rectangle(double width, double height, Point center) {
	    // completare
		checkSide(width);
		checkSide(height);
		this.width = width;
		this.height = height;
		this.center.move(center.getX(), center.getY());
	}

	/*
	 * Rettangolo con dimensioni di default e centro sull'origine degli assi
	 */
	public Rectangle() {
	}

	/*
	 * Factory method
	 */
	public static Rectangle ofWidthHeight(double width, double height) {
	    // completare
		return new Rectangle(width, height);
	}

	/*
	 * Factory method
	 */
	public static Rectangle ofWidthHeightCenter(double width, double height, Point center) {
	    // completare
		return new Rectangle(width, height, center);
	}

	public void move(double dx, double dy) {
	    // completare
		this.center.move(dx, dy);
	}

	public void scale(double factor) {
	    // completare
		this.height *= factor;
		this.width *= factor;
	}

	public Point getCenter() {
	    // completare
		return this.center;
	}

	public double perimeter() {
	    // completare
		return (this.width*2)+(this.height*2);
	}

	public double area() {
	    // completare
		return (this.height*this.width)/2;
	}
}
