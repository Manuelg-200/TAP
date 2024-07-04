package lab05_12_16.accounts;

public interface Client {

	String getName();

	String getSurname();

	long getSocialSN();

	Person getSpouse();

	boolean isSingle();

}