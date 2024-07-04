package lab07_03_30;

import java.util.Comparator;

public class ComparePersonBySurname implements Comparator<Person> {

	// for people with the same surname the natural order on id is considered
	@Override
	public int compare(Person p1, Person p2) {
	    // da completare
		if(p1.equals(p2))
			return 0;
		int i = p1.getSurname().compareTo(p2.getSurname());
		if( i == 0)
		{
			if(p1.getId() < p2.getId())
				return -1;
			else
				return 1;
		}
		return i;
	}

}
