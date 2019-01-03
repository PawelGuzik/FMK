import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Faktura {
	private static int licznik = 1;
	private int numerFaktury = 0;
	private Calendar data;
	private String sprzedajacy = "Jan Kowalski Sp. z o. o.";
	private ArrayList<PozycjaFakturowa> listaPozycji = new ArrayList<PozycjaFakturowa>();
	private double sumaBrutto = 0;
	private double sumaNetto = 0;
	private double sumaVat = 0;
	private Klient klient = null;

	public Faktura() {
		setData(new GregorianCalendar());
	}

	public String toString() {
		if (numerFaktury == 0) {
			return "Faktura nr # " + "NIE NADANO NUMERU!" + " z dnia " + getData();
		} else {
			return "Faktura nr # " + numerFaktury + " z dnia " + getData();
		}
	}
	public int ilePozycjiF() {
		return listaPozycji.size();
	}

	public Klient getKlient() {
		return klient;
	}

	public void setKlient(Klient klient) {
		this.klient = klient;
	}

	public void dodajPozycjeF(PozycjaFakturowa a) {
		listaPozycji.add(a);
	}

	public double obliczSumeBrutto() {
		sumaBrutto = 0;
		for (int i = 0; i < listaPozycji.size(); i++) {
			sumaBrutto = sumaBrutto + listaPozycji.get(i).getcJb() * listaPozycji.get(i).getIlosc();
		}
		return sumaBrutto;
	}

	public double obliczSumeNetto() {
		sumaNetto = 0;
		for (int i = 0; i < listaPozycji.size(); i++) {

			sumaNetto = sumaNetto + listaPozycji.get(i).getcJn() * listaPozycji.get(i).getIlosc();
		}
		return sumaNetto;
	}

	public double obliczSumeVat() {
		sumaVat = 0;
		for (int i = 0; i < listaPozycji.size(); i++) {
			sumaVat = sumaVat + (listaPozycji.get(i).getVat() / 100) * listaPozycji.get(i).getIlosc()
					* listaPozycji.get(i).getcJn();
		}
		return sumaVat;
	}

	public PozycjaFakturowa getPozycjaF(int index) {
		return listaPozycji.get(index);
	}
	public boolean usunPozFakt(String nazwa) {
		for(PozycjaFakturowa pf : listaPozycji) {
			if(pf.getNazwa().equalsIgnoreCase(nazwa)) {
				listaPozycji.remove(pf);
				return true;
			}
		}
		return false;
		
	}
	public Calendar getDataField() {
		return data;
	}
	public String getData() {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String requiredDate = df.format(data.getTime());
		return requiredDate;

	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public void zapiszFakture() {
		setNumerFaktury(licznik);
		licznik++;
	}

	public int getNumerFaktury() {
		return numerFaktury;
	}

	public void setNumerFaktury(int numerFaktury) {
		this.numerFaktury = numerFaktury;
	}

}
