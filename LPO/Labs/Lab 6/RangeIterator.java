package lab06_03_16;

import java.util.Iterator;
import java.util.NoSuchElementException;

class RangeIterator implements Iterator<Integer> {

    // object fields and constructors
    private Integer current;
    private final Range range;

    RangeIterator(Range range) {
        this.current = range.start;
        this.range = range;
    }

    @Override
    public boolean hasNext() {
	// ...
        return (this.current < this.range.end);
    }

    @Override
    public Integer next() {
	// ...
        if(!this.hasNext())
            throw new NoSuchElementException();
        return this.current++;
    }

}
