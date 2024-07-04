import java.util.HashSet;
import java.util.Set;

public class Person {
    private final String name;
    private final String surname;
    private final long SocialSN;
    private static Set<Long> SocialSNList = new HashSet<>();
    private Person spouse = null;

    private void CheckSocialSN(long SocialSN) {
        if(SocialSN < 0)
            throw new IllegalArgumentException();
        if(!SocialSNList.add(SocialSN))
            throw new IllegalArgumentException();
    }

    private static void CheckString(String s) {
        if (!s.matches("[A-Z][a-z]+([A-Z][a-z]+)*"))
            throw new IllegalArgumentException();
    }

    private Person(String Name, String Surname, long SocialSN) {
        CheckString(Name);
        CheckString(Surname);
        CheckSocialSN(SocialSN);
        this.name = Name;
        this.surname = Surname;
        this.SocialSN = SocialSN;        
    }

    private Person(String Name, String Surname, long SocialSN, Person Spouse) {
        this(Name, Surname, SocialSN);
        this.spouse = Spouse;
    }

    public static Person valueOf(String Name, String Surname, long SocialSN) {
        Person tmp = new Person(Name, Surname, SocialSN);
        return tmp;
    }

    public static Person valueOf(String Name, String Surname, long SocialSN, Person Spouse) {
        Person tmp = new Person(Name, Surname, SocialSN, Spouse);
        return tmp;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public long getSocialSN() {
        return this.SocialSN;
    }

    public Person getSpouse() {
        return this.spouse;
    }

    public Boolean isSingle() {
        return (this.spouse != null);
    }

    public void join(Person p1, Person p2) {
        if(p1.getSpouse() != null || p2.getSpouse() != null || p1 == p2)
            throw new IllegalArgumentException();
        p1.spouse = p2;
        p2.spouse = p1;
    }

    public void Divorce(Person p1, Person p2) {
        if(p1.getSpouse() != p2 || p2.getSpouse() != p1)
            throw new IllegalArgumentException();
        p1.spouse = null;
        p2.spouse = null;
    }
}

class TestPerson {
    public static void main(String[] args) {
        Person p1 = Person.valueOf("Fabio", "Rossi", 28982939);
        Person p2 = Person.valueOf("Carla", "Bianchi", 49039323);
        assert (p1.getName() == "Fabio");
        assert (p2.getSurname() == "Bianchi");
        assert (p2.getSocialSN() == 49039323);
        assert (p1.getSpouse() == null);
        p1.join(p1, p2);
        assert (p1.getSpouse() == p2);
        p2.Divorce(p1, p2);
        assert (p2.getSpouse() == null);
        Person p3 = Person.valueOf("Lionel", "Messi", 28982939);
    }
}
