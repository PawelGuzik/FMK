
public class Klient {
	private String nazwisko = "Kowalski";
	private String imie = "Jan";
	private String adres = "Warszawa";
	static int id;
	private int myId;
	
	public Klient(String aNazwisko, String aImie, String aAdres) {
		nazwisko = aNazwisko;
		imie = aImie;
		adres = aAdres;
		id++;
		myId = id;
	}
	
	public String toString() {
		return "<html> Id " + myId + "<br>" + imie + " " + nazwisko + "<br>" + adres + "</html>";
	}
	
	public String getNazwisko() {
		return nazwisko;
	}
	
	public String getImie() {
		return imie;
	}
	public String getAdres() {
		return adres;
	}
	
	
	//"<html> JanKo Sp. z o. o. <br> 00-110 Warszawa <br> ul. Inna 49 </html>"
}
