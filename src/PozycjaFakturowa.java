
public class PozycjaFakturowa {
	private String nazwa = "";
	private double cJb = 0;
	private double cJn = 0;
	private double vat = 0; // w %
	private int ilosc = 0;
	private static int index = -1;
	public PozycjaFakturowa() {
		setNazwa("AAAAAAAA");
		setcJb(12);
		setcJn(2);
		setVat(32);
		setIlosc(20);
	}
	public PozycjaFakturowa(String anazwa,int ailosc, double acJnetto, double avat) {
		++index;
		setNazwa(anazwa);
		setcJn(acJnetto);
		setVat(avat);
		setIlosc(ailosc);
		setcJb(obliczBrutto(acJnetto, avat));
		
	}
	public static int getIndex() {
		return index;
	}
	
	public double obliczBrutto(double acJn, double avat) {
		return acJn * (1+(avat/100));
	}
	public double obliczNetto(double acJb, double avat) {
		return acJb / (1+(avat/100));
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public double getcJb() {
		return cJb;
	}
	public void setcJb(double cJb) {
		this.cJb = cJb;
	}
	public double getcJn() {
		return cJn;
	}
	public void setcJn(double cJn) {
		this.cJn = cJn;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public int getIlosc() {
		return ilosc;
	}
	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}

}
