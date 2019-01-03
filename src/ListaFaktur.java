import java.util.ArrayList;

public class ListaFaktur extends ArrayList {
	private ArrayList<Faktura> listaF = new ArrayList<>();

	public Faktura[] getFaktury() {
		return listaF.toArray(new Faktura[listaF.size()]);
	}

	public void dodajFakture(Faktura f) {
		listaF.add(f);
	}

	public void usun(Faktura selected) {
		listaF.remove(selected);
	}

	public Faktura getNew() {
		return listaF.get(listaF.size() - 1);
	}

	public Faktura getFaktura(int index) {
		return listaF.get(index);
	}

	public void aktualizujFakture(Faktura doAkt, int index) {

		listaF.set(index, doAkt);
	}
}
