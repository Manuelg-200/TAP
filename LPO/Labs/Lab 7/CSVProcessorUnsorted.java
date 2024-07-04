package lab07_03_30;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CSVProcessorUnsorted implements CSVProcessor {

	protected void merge(Readable readable, Map<Person, Integer> map) {
		try (var sc = new Scanner(readable)) {
			sc.useDelimiter("\\s*,\\s*|\\s*\\n\\s*");
			while (sc.hasNext()) {
			        // da completare
				var P = Person.newOfNameSurnameId(sc.next().toUpperCase(), sc.next().toUpperCase(), Long.parseLong(sc.next()));
				if(map.containsKey(P))
					map.compute(P, (key, val) -> val + Integer.parseInt(sc.next()));
				else
					map.put(P, Integer.parseInt(sc.next()));
				sc.nextLine(); // skips the remaining columns, if any
			}
		}
	}

	@Override
	public Map<Person, Integer> merge(Readable readable) {
		var map = new HashMap<Person, Integer>();
		merge(readable, map);
		return map;
	}

}
