package lab06_03_16;

import java.util.Iterator;

public class RangeTest {

    public static void main(String[] args) {
	Range r = new Range(3);
	for (int x : r)
	    for (int y : r)
		System.out.println(x + " " + y);
	/* prints
	 * 0 0
	 * 0 1
	 * 0 2
	 * 1 0
	 * 1 1
	 * 1 2
	 * 2 0
	 * 2 1
	 * 2 2
	 */
	RangeIterator iterator_x = r.iterator();
	while(iterator_x.hasNext()) {
		Integer x = iterator_x.next();
		RangeIterator iterator_y = r.iterator();
		while(iterator_y.hasNext()) {
			Integer y = iterator_y.next();
			System.out.println(x + " " + y);
		}
	}
    }
}
