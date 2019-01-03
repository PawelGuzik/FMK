import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class FormDodajFakture extends JInternalFrame {
	static int savedFrameCount = 0;
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;
	public static ArrayList<String> nazwyTowarow = new ArrayList<String>();
	int charCounter = 0;
	JTable tab;
	public Faktura f;
	JLabel lNrFaktury;
	JLabel lData;
	JTextField tfNazwisko;
	JTextField tfImie;
	JTextField tfAdres;
	JTextField tfNazwa;
	JTextField tfIlosc;
	JTextField tfNetto;
	JTextField tfVat;
	String[] tabLista;

	public void aktListTow() {
		tabLista = new String[nazwyTowarow.size()];
		tabLista = nazwyTowarow.toArray(tabLista);
	}

	public void uzupelnijDane(Faktura f) {
		lData.setText(f.getData());
		tfNazwisko.setText(f.getKlient().getNazwisko());
		tfImie.setText(f.getKlient().getImie());
		tfAdres.setText(f.getKlient().getAdres());
		aktualizujPFwTabeli();
	}

	public void aktualizujPFwTabeli() {
		czyscTabele();
		for (int i = 0; i < f.ilePozycjiF(); i++) {
			tab.setValueAt(f.getPozycjaF(i).getNazwa(), i, 0);
			tab.setValueAt(f.getPozycjaF(i).getIlosc(), i, 1);
			tab.setValueAt(f.getPozycjaF(i).getcJn(), i, 2);
			tab.setValueAt(f.getPozycjaF(i).getVat(), i, 3);

		}

	}

	public void czyscTabele() {
		int i = 0;
		System.out.println("Jestem przed pętli while funkcji czyść tabelę !!!!!!!!!!!!!!!!!!!!!!!!!!!");
	
		while (tab.getValueAt(i, 0) != null) {
			System.out.println("Jestem wewnątrz pętli while funkcji czyść tabelę !!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for (int j = 0; j < 4; j++) {
				tab.setValueAt("", i, j);

			}
			i++;
		}
	}

	public FormDodajFakture(boolean trybEdycji, Faktura doEdycji, int indexNaLiscie) {
		super("Faktura #" + (++openFrameCount), true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		if (trybEdycji) {
			f = doEdycji;
		} else {
			f = new Faktura();
		}
		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(300, 300);

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		// setLayout(new GridLayout(7, 1));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		// Pola Faktura nr: i Data
		// Pierwsza sekcja - wewnętrz jp1 dwie etykiety pionowo. jp2, jp1 i jp3 -
		// wewnętrz jp4 - poziomo
		lNrFaktury = new JLabel("Faktura nr: ");
		lData = new JLabel("Data: " + f.getData());
		JPanel jp1 = new JPanel();
		jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
		jp1.setBackground(Color.yellow);
		jp1.add(lNrFaktury);
		jp1.add(lData);
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		jp4.add(jp2);// pusty
		jp4.add(jp1);
		jp4.add(jp3);// pusty

		// Pola Zamawiający, dane klienta, Sprzedający
		// Druga sekcja - jp5 - pola Zamawiający i dane klienta ułożone pionowo, jp6 -
		// pole Sprzedajęcy. jp5 i jp6 spakowane do jp7 we FolwLayout
		JLabel lKlient = new JLabel("Zamawiający:");
		JLabel lNazwisko = new JLabel("Nazwisko: ");
		JLabel lImie = new JLabel("Imię: ");
		JLabel lAdres = new JLabel("Adres: ");

		tfNazwisko = new JTextField("");
		tfImie = new JTextField("");
		tfAdres = new JTextField("Warszawa");

		/*
		 * Klient klient = new Klient("Kowalski", "Jan", "Warszawa"); JComboBox<Klient>
		 * daneKlienta = new JComboBox<Klient>(); daneKlienta.addItem(klient);
		 */
		JLabel lSprzedajacy = new JLabel("<html> JanKo Sp. z o. o. <br> 00-110 Warszawa <br> ul. Inna 49 </html>");
		JPanel jp5 = new JPanel();
		JPanel jp6 = new JPanel();
		JPanel jp7 = new JPanel();
		jp5.setLayout(new BoxLayout(jp5, BoxLayout.X_AXIS));
		JPanel jp20 = new JPanel();
		jp20.setLayout(new BoxLayout(jp20, BoxLayout.Y_AXIS));
		jp20.add(lNazwisko);
		jp20.add(lImie);
		jp20.add(lAdres);
		JPanel jp21 = new JPanel();
		jp21.setLayout(new BoxLayout(jp21, BoxLayout.Y_AXIS));
		jp21.add(tfNazwisko);
		jp21.add(tfImie);
		jp21.add(tfAdres);
		jp21.setPreferredSize(new Dimension(100, 50));

		jp6.add(lSprzedajacy);
		jp5.add(jp7);
		jp5.add(jp6);

		GroupLayout glk = new GroupLayout(jp7);
		glk.setAutoCreateGaps(true);
		glk.setAutoCreateContainerGaps(true);
		jp7.setLayout(glk);

		glk.setHorizontalGroup(glk.createSequentialGroup()
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lKlient)
						.addComponent(lNazwisko).addComponent(lImie).addComponent(lAdres))
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(tfNazwisko)
						.addComponent(tfImie).addComponent(tfAdres)));

		glk.setVerticalGroup(glk.createSequentialGroup().addComponent(lKlient)
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lNazwisko)
						.addComponent(tfNazwisko))
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lImie)
						.addComponent(tfImie))
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lAdres)
						.addComponent(tfAdres)));
		// Pola do wprowadzania pozycji fakturowych
		// Do wprowadzania danych pola TextFiled i etykiety uporządkowane przez
		// GroupLayout
		// Następnie JTable do wyświetlania teo co w fakturze zapisano
		JLabel lNazwa = new JLabel("Nazwa");
		JLabel lIlosc = new JLabel("Ilość");
		JLabel lNetto = new JLabel("Cena netto");
		JLabel lVat = new JLabel("VAT");
		tfNazwa = new JTextField();
		tfIlosc = new JTextField("10");
		tfNetto = new JTextField("100");
		tfVat = new JTextField("23");
		JButton dodaj = new JButton("Dodaj do faktury");

		JPanel jp8 = new JPanel();
		GroupLayout gl = new GroupLayout(jp8);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		jp8.setLayout(gl);

		JList<String> jlp = new JList<String>();
		aktListTow();
		jlp.setListData(tabLista);
		JScrollPane scroll = new JScrollPane(jlp);
		JButton usun = new JButton("Usuń");
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lNazwa)
						.addComponent(tfNazwa))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lIlosc)
						.addComponent(tfIlosc))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lNetto)
						.addComponent(tfNetto))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lVat).addComponent(tfVat))
				.addGroup(
						gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(dodaj).addComponent(usun)));

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lNazwa)
						.addComponent(lIlosc).addComponent(lNetto).addComponent(lVat).addComponent(dodaj))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(tfNazwa)
						.addComponent(tfIlosc).addComponent(tfNetto).addComponent(tfVat).addComponent(usun)));
		scroll.setVisible(true);
		String[] columnNames = { "Nazwa", "Ilość", "Cena netto", " VAT" };
		Object[][] data = { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, };

		tab = new JTable(data, columnNames);
		tab.setEnabled(true);
		tab.setColumnSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setFillsViewportHeight(true);
		scrollPane.setPreferredSize(new Dimension(100, 50));

		// Podsumowanie
		JLabel lSumaB = new JLabel("Suma brutto");
		JLabel lSumaN = new JLabel("Suma netto");
		JLabel lSumaVat = new JLabel("Suma vat");
		JPanel jp9 = new JPanel();
		jp9.add(lSumaB);
		jp9.add(lSumaN);
		jp9.add(lSumaVat);
		jp9.setLayout(new BoxLayout(jp9, BoxLayout.PAGE_AXIS));
		lSumaB.setHorizontalAlignment(SwingConstants.RIGHT);

		JButton zapisz = new JButton("Zapisz fakturę");
		JButton zapamiętaj = new JButton("Zapisz bez numeru");
		JPanel jp10 = new JPanel();
		jp10.add(zapisz);
		jp10.add(zapamiętaj);

		dodaj.addActionListener(new ActionListener() {
			int index = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				int ilosc = 0;
				double netto = 0;
				double vat = 0;
				try {
					ilosc = Integer.parseInt(tfIlosc.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(jp5, "Ilość musi być liczbą całkowitą", "Zły format w polu [Ilość]",
							JOptionPane.ERROR_MESSAGE);
				}
				try {
					netto = Double.parseDouble(tfNetto.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(jp5,
							"Proszę podać wartość netto z dokładnościa do dwóch miejsc po przecinku",
							"Zły format w polu [Cena netto]", JOptionPane.ERROR_MESSAGE);
				}
				try {
					vat = Double.parseDouble(tfVat.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(jp5, "Watrość podatku vat musi być liczbą", "Zły format w polu [VAT]",
							JOptionPane.ERROR_MESSAGE);
				}

				if (tfNazwa.getText().length() > 0) {
					nazwyTowarow.add(tfNazwa.getText());
					aktListTow();
					jlp.setListData(tabLista);
					PozycjaFakturowa pf = new PozycjaFakturowa(tfNazwa.getText(), ilosc, netto, vat);

					tab.setValueAt(tfNazwa.getText(), f.ilePozycjiF(), 0);
					tab.setValueAt(ilosc, f.ilePozycjiF(), 1);
					tab.setValueAt(netto, f.ilePozycjiF(), 2);
					tab.setValueAt(vat, f.ilePozycjiF(), 3);
					f.dodajPozycjeF(pf);
					index++;
					lSumaB.setText("Suma brutto: " + f.obliczSumeBrutto() + ".");
					lSumaN.setText("Suma netto: " + f.obliczSumeNetto() + ".");
					lSumaVat.setText("Suma vat: " + f.obliczSumeVat() + ".");
					tfNazwa.setText("");
					charCounter = 0;
				}

			}
		});
		usun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int wierszWybrany = tab.getSelectedRow();
				int dec = JOptionPane.showConfirmDialog(getContentPane(),
						"Czy chcesz usunąć " + tab.getValueAt(wierszWybrany, 0).toString() + " z faktury ?",
						"Zapisano fakturę", JOptionPane.YES_NO_OPTION);
				if (dec == JOptionPane.OK_OPTION) {
					if(f.usunPozFakt(tab.getValueAt(wierszWybrany, 0).toString())) {JOptionPane.showMessageDialog(getContentPane(),
							"poprawnie usunęto pozycje fakturową" +tab.getValueAt(wierszWybrany, 0).toString() + " z listy PF wewnątrz faktury", "Udane usuniecie",
							JOptionPane.ERROR_MESSAGE);};
					aktualizujPFwTabeli();
					repaint();
				}
			}
		});
		zapisz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dec = JOptionPane.showConfirmDialog(getContentPane(),
						"Uwaga! Po zapisanu faktury, edycja danych nie bedzie możliwa. Czy chcesz zapisac fakturę i nadać jej kolejny numer?",
						"Czy zapisać fakturę?", JOptionPane.YES_NO_OPTION);
				if (dec == JOptionPane.OK_OPTION) {
				if (trybEdycji) {
					Manager.lf.aktualizujFakture(f, indexNaLiscie);
				}
				Klient k = new Klient(tfNazwisko.getText(), tfImie.getText(), tfAdres.getText());
				f.setKlient(k);
				f.zapiszFakture();
				Manager.listaKlientów.add(k);
				if (trybEdycji == false) {
					Manager.lf.dodajFakture(f);
				}
				Manager.listaFaktur.setListData(Manager.lf.getFaktury());
				;
				JOptionPane.showMessageDialog(getContentPane(),
						"Faktura nr " + f.getNumerFaktury() + " została zapisana w systemie", "Zapisano fakturę",
						JOptionPane.ERROR_MESSAGE);
				doDefaultCloseAction();

			}
			}
		});
		zapamiętaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dec = JOptionPane.showConfirmDialog(getContentPane(),
						"Uwaga! Po zapisanu faktury bez nadania numeru, edycja danych bedzie możliwa. Czy chcesz zapisac fakturę??",
						"Czy zapisać fakturę bez numeru?", JOptionPane.YES_NO_OPTION);
				if (dec == JOptionPane.OK_OPTION) {
				if (trybEdycji) {
					Manager.lf.aktualizujFakture(f, indexNaLiscie);
				}
				Klient k = new Klient(tfNazwisko.getText(), tfImie.getText(), tfAdres.getText());
				f.setKlient(k);
				Manager.listaKlientów.add(k);
				if (trybEdycji == false) {
					Manager.lf.dodajFakture(f);
				}
				Manager.listaFaktur.setListData(Manager.lf.getFaktury());
				;
				JOptionPane.showMessageDialog(getContentPane(),
						"Faktura nr " + f.getNumerFaktury() + " została zapisana w systemie", "Zapisano fakturę",
						JOptionPane.ERROR_MESSAGE);
				doDefaultCloseAction();
				}
			}
		});
		nazwyTowarow.add("ok");

		nazwyTowarow.add("woda");
		nazwyTowarow.add("mleko");
		nazwyTowarow.add("miód");
		nazwyTowarow.add("sok");
		nazwyTowarow.add("mlekowproszku");
		tfNazwa.setToolTipText(
				"<html>Tu wpisz nazwę towaru/usługi,<br>którą chcesz dodać do faktury.<br>Jeżeli towar był już wprowadzany,<br> jego nazwa zostanie uzupełniona automatycznie</html>");
		tfNazwa.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

				System.out.println(e.getKeyChar());
				charCounter = tfNazwa.getCaretPosition();
				String wyrazWpr = tfNazwa.getText().substring(0, charCounter);
				System.out.println("Wyraz wprowadzony przed if: " + wyrazWpr);
				if (tfNazwa.getText().length() == 0) {
					wyrazWpr = Character.toString(e.getKeyChar());
					charCounter = 0;
				} else if (e.getKeyChar() == '\b' || e.getKeyCode() == KeyEvent.VK_DELETE) {
					charCounter--;
				} else {

					wyrazWpr = wyrazWpr + Character.toString(e.getKeyChar());
					System.out.println("Wyraz wprowadzony wewn else: " + wyrazWpr);
				}
				// char znakWpr = e.getKeyChar();

				// wyrazWpr = wyrazWpr + znakWpr;
				if (!wyrazWpr.isEmpty() && czyListaZawiera(wyrazWpr)) {
					tfNazwa.setText(znajdzWyraz(wyrazWpr));
					System.out.println("wyraz znaleziony na liście: " + tfNazwa.getText());
					e.consume();
					charCounter++;
					tfNazwa.setCaretPosition(charCounter);
					// tfNazwa.setCaretPosition(charCounter);
				} else {
					tfNazwa.setText(wyrazWpr);

					charCounter++;
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		tfNazwisko.setToolTipText(
				"<html>Tu wpisz nazwisko klienta <br> Jeżeli klient był już wprowadzony w systemie,<br> jego dane zostaną uzupelnione automatycznie</html>");

		tfNazwisko.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println(e.getKeyChar());
				charCounter = tfNazwisko.getCaretPosition();
				String wyrazWpr = tfNazwisko.getText().substring(0, charCounter);
				System.out.println("Wyraz wprowadzony przed if: " + wyrazWpr);
				if (tfNazwisko.getText().length() == 0) {
					wyrazWpr = Character.toString(e.getKeyChar());
					charCounter = 0;
				} else if (e.getKeyChar() == '\b' || e.getKeyCode() == KeyEvent.VK_DELETE) {
					charCounter--;
				} else {

					wyrazWpr = wyrazWpr + Character.toString(e.getKeyChar());
					System.out.println("Wyraz wprowadzony wewn else: " + wyrazWpr);
				}
				// char znakWpr = e.getKeyChar();

				// wyrazWpr = wyrazWpr + znakWpr;
				System.out.println("Czy lista klientów zawiera ?" + wyrazWpr + " " + czyLKlientowzawiera(wyrazWpr));
				if (!wyrazWpr.isEmpty() && czyLKlientowzawiera(wyrazWpr)) {
					String[] daneklienta = znajdzKlienta(wyrazWpr);
					tfNazwisko.setText(daneklienta[0]);
					tfImie.setText(daneklienta[1]);
					tfAdres.setText(daneklienta[2]);
					System.out.println("wyraz znaleziony na liście: " + tfNazwisko.getText());
					e.consume();
					charCounter++;
					tfNazwisko.setCaretPosition(charCounter);
					// tfNazwa.setCaretPosition(charCounter);
				} else {
					tfNazwisko.setText(wyrazWpr);

					charCounter++;
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		if (trybEdycji) {
			uzupelnijDane(doEdycji);
		}

		add(jp4);
		add(jp5);
		add(jp8);

		// add(jlp);
		add(scrollPane);
		// scroll.setLocation(0, 0);
		add(jp9);
		add(jp10);
		setVisible(true);

	}

	public boolean czyLKlientowzawiera(String s) {
		if (Manager.listaKlientów.size() > 0) {
			System.out.println("Lista klientów zawiera " + Manager.listaKlientów.get(0).getNazwisko());
		}
		for (Klient e : Manager.listaKlientów) {
			int pasujaceZnaki = 0;
			String nazwisko = e.getNazwisko();
			if (s.length() <= nazwisko.length()) {
				if (s.equalsIgnoreCase(nazwisko.substring(0, s.length()))) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean czyListaZawiera(String s) {

		for (String e : nazwyTowarow) {
			int pasujaceZnaki = 0;
			for (int i = 0; i < s.length() && i < e.length(); i++) {
				if (e.charAt(i) == s.charAt(i)) {
					pasujaceZnaki++;
				} else {
					break;
				}
			}
			if (pasujaceZnaki == s.length()) {
				return true;
			}

		}
		return false;
	}

	public String znajdzWyraz(String s) {
		for (String e : nazwyTowarow) {
			if (e.length() >= s.length()) {
				String f = e.substring(0, s.length());

				if (f.equalsIgnoreCase(s)) {
					return e;
				}
			}
		}
		return "";
	}

	public String[] znajdzKlienta(String s) {
		for (Klient e : Manager.listaKlientów) {
			String nazwisko = e.getNazwisko();
			System.out.println("GetNazwisko:" + nazwisko);
			if (nazwisko.length() >= s.length()) {
				String f = nazwisko.substring(0, s.length());

				if (f.equalsIgnoreCase(s)) {
					String[] n = new String[3];
					n[0] = nazwisko;
					n[1] = e.getImie();
					n[2] = e.getAdres();
					return n;
				}
			}
		}
		return null;
	}

}
