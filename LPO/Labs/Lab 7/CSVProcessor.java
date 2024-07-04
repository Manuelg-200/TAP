package lab07_03_30;

import java.util.Map;
import java.util.Set;

public interface CSVProcessor {

	Map<Person, Integer> merge(Readable readable);

	default Map<Person, Integer> mergeAndSort(Readable readable) {
		throw new UnsupportedOperationException();
	}
}
