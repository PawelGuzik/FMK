
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormPodgladu extends JInternalFrame {
	static int savedFrameCount = 0;
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;
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
	JLabel lSumaB = new JLabel("Suma brutto");
	JLabel lSumaN = new JLabel("Suma netto");
	JLabel lSumaVat = new JLabel("Suma vat");
	public void uzupelnijDane(Faktura f) {
		lData.setText(f.getData());
		tfNazwisko.setText(f.getKlient().getNazwisko());
		tfImie.setText(f.getKlient().getImie());
		tfAdres.setText(f.getKlient().getAdres());
		for (int i = 0; i < f.ilePozycjiF(); i++) {
			tab.setValueAt(f.getPozycjaF(i).getNazwa(), i, 0);
			tab.setValueAt(f.getPozycjaF(i).getIlosc(), i, 1);
			tab.setValueAt(f.getPozycjaF(i).getcJn(), i, 2);
			tab.setValueAt(f.getPozycjaF(i).getVat(), i, 3);

		}

	}

	public FormPodgladu( Faktura doPodladu, int indexNaLiscie) {
		super("Faktura #" + (++openFrameCount), true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		
			f = doPodladu;
		
		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(300, 300);

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		// Pola Faktura nr: i Data
		// Pierwsza sekcja - wewnętrz jp1 dwie etykiety pionowo. jp2, jp1 i jp3 -
		// wewnętrz jp4 - poziomo
		lNrFaktury = new JLabel("Faktura nr: " + f.getNumerFaktury());
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
		JLabel lNazwisko = new JLabel("Nazwisko: " );
		JLabel lImie = new JLabel("Imię: ");
		JLabel lAdres = new JLabel("Adres: " );
		JLabel ulNazwisko = new JLabel( f.getKlient().getNazwisko());
		JLabel ulImie = new JLabel(f.getKlient().getImie());
		JLabel ulAdres = new JLabel(f.getKlient().getAdres());

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
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(ulNazwisko)
						.addComponent(ulImie).addComponent(ulAdres)));

		glk.setVerticalGroup(glk.createSequentialGroup().addComponent(lKlient)
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lNazwisko)
						.addComponent(ulNazwisko))
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lImie)
						.addComponent(ulImie))
				.addGroup(glk.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lAdres)
						.addComponent(ulAdres)));

		String[] columnNames = { "Nazwa", "Ilość", "Cena jedn. netto","Cena jedn. brutto", "VAT %", "Suma netto", "Suma Brutto", "Suma VAT" };
		Object[][] data = { { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				};

		tab = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setFillsViewportHeight(true);
		tab.setEnabled(false);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		wypelnijTab();
		
		
		
	
		JPanel jp9 = new JPanel();
		jp9.add(lSumaB);
		jp9.add(lSumaN);
		jp9.add(lSumaVat);
		jp9.setLayout(new BoxLayout(jp9, BoxLayout.PAGE_AXIS));
		lSumaB.setHorizontalAlignment(SwingConstants.RIGHT);

		add(jp4);
		add(jp5);
		add(scrollPane);
		add(jp9);
		setVisible(true);
	}
	public void wypelnijTab() {
		for(int i = 0; i < f.ilePozycjiF(); i++) {
			PozycjaFakturowa pozFakt = f.getPozycjaF(i);
			tab.setValueAt(pozFakt.getNazwa(), i, 0);
			tab.setValueAt(pozFakt.getIlosc(), i, 1);
			tab.setValueAt(pozFakt.getcJn(), i, 2);
			tab.setValueAt(pozFakt.getcJb(), i, 3);
			tab.setValueAt(pozFakt.getVat(), i, 4);
			tab.setValueAt(pozFakt.getcJn()*pozFakt.getIlosc(), i, 5);
			tab.setValueAt(pozFakt.getcJb()*pozFakt.getIlosc(), i, 6);
			tab.setValueAt((pozFakt.getcJn()*(pozFakt.getVat()/100))*pozFakt.getIlosc(), i, 7);
		}
		lSumaB.setText("Suma brutto: " + f.obliczSumeBrutto());
		lSumaN.setText("Suma netto: " + f.obliczSumeNetto());
		lSumaVat.setText("Suma VAT: " + f.obliczSumeVat());
	}

}
