package lab04_12_02;

public class Shapes {

	private static void CheckShape(Shape shape) {
		if(shape == null)
			throw new IllegalArgumentException();
	}
	
	/*
	 * restituisce la prima figura maggiore o uguale alle altre in shapes rispetto al comparator comp,
	 * null se shapes e` vuoto
	 * requires shapes != null && comp != null
	 */    
	public static Shape max(Shape[] shapes, ShapeComparator comp) {
	    // completare
		CheckShape(shapes[0]);
		Shape max = shapes[0];
		for(int i=0; i<shapes.length; i++) {
			if(comp.compare(shapes[i], shapes[i+1]) > 0)
				max = shapes[i];
		}
		return max;
	}

	/*
	 * trasla tutte le figure lungo il vettore (dx,dy)
	 * requires shapes != null
	 */
	public static void moveAll(Shape[] shapes, double dx, double dy) {
	    // completare
		for(int i=0; i<shapes.length; i++)
			shapes[i].move(dx, dy);
	}
    
	/*
	 * scala tutte le figure del fattore factor, senza traslare il loro centro
	 * requires shapes != null && factor > 0
	 */
	public static void scaleAll(Shape[] shapes, double factor) {
	    // completare
		if(factor < 0)
			throw new IllegalArgumentException();
		for(int i=0; i<shapes.length; i++)
			shapes[i].scale(factor);
	}
    
	/*
	 * restituisce l'area totale di tutte le figure in shapes
	 * requires shapes != null
	 */
	public static double totalArea(Shape[] shapes) {
	    // completare
		double totalArea = 0;
		for(int i=0; i<shapes.length; i++)
			totalArea += shapes[i].area();
		return totalArea;
	}
}
