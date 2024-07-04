package lab04_12_02;

import javax.lang.model.util.ElementScanner14;

/*
 * 
 * Confronta le figure basandosi sulle loro aree.
 */

public class AreaComparator implements ShapeComparator {
	private static void shapeCheck(Shape shape) {
		if(shape == null)
			throw new IllegalArgumentException();
	}
	/* requires shape1 != null && shape2 != null */
	public int compare(Shape shape1, Shape shape2) {
	    // completare
		shapeCheck(shape1);
		shapeCheck(shape2);
		double area1 = shape1.area();
		double area2 = shape2.area();
		if(area1 < area2)
			return -1;
		else if(area1 == area2)
			return 0;
		else
			return 1;
	}

}
