import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultDesktopManager;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Manager extends JFrame {
	public static ListaFaktur lf = new ListaFaktur();
	public static JList<Faktura> listaFaktur;
	public static ArrayList<Klient> listaKlientów = new ArrayList<Klient>();
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	JDesktopPane desktop;

	public Manager() {
		super("Manager faktur");

		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
		desktop = new JDesktopPane();

		setContentPane(desktop);

		listaFaktur = new JList<Faktura>();
		listaFaktur.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listaFaktur.setLayoutOrientation(JList.VERTICAL);
		// listaFaktur.setListData(lf.getFaktury());

		JScrollPane listScroller = new JScrollPane(listaFaktur);
		listScroller.setPreferredSize(new Dimension(250, 80));

		JLabel fakturyListaL = new JLabel("Lista zapisanych faktur");

		JPanel up = new JPanel();
		up.setLayout(new BoxLayout(up, BoxLayout.Y_AXIS));
		up.add(fakturyListaL);
		up.add(listScroller);

		JPanel down = new JPanel();

		GroupLayout gl = new GroupLayout(down);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		down.setLayout(gl);
		down.setAlignmentX(LEFT_ALIGNMENT);
		JButton usun = new JButton("Usuń fakturę");
		JButton edytuj = new JButton("Edytuj");
		JButton przegladaj = new JButton("Przeglądaj");
		JButton dodaj = new JButton("Dodaj");
		przegladaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Faktura doPodgladu = lf.getFaktura(listaFaktur.getSelectedIndex());
				createView(doPodgladu,listaFaktur.getSelectedIndex());
			}
		});
		edytuj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Faktura edytowanaFaktura = lf.getFaktura(listaFaktur.getSelectedIndex());
				createFrame(true, edytowanaFaktura, listaFaktur.getSelectedIndex());

			}
		});
		dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				createFrame(false, null, -1);

			}
		});
		listaFaktur.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(listaFaktur.isFocusOwner()) {
				Faktura wybrana = lf.getFaktura(listaFaktur.getSelectedIndex());
				if (wybrana.getNumerFaktury() != 0) {
					edytuj.setEnabled(false);
					usun.setEnabled(false);
				} else {
					edytuj.setEnabled(true);
					usun.setEnabled(true);
				}
				JOptionPane.showMessageDialog(getContentPane(),
						"Faktura nr " + wybrana.getNumerFaktury() + " została odczytana" + "data: " + wybrana.getData()
								+ "klient: " + wybrana.getKlient() + wybrana.getPozycjaF(0).getNazwa(),
						"Odczyt faktury", JOptionPane.ERROR_MESSAGE);

			}}
		});
		
		usun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int dec = JOptionPane.showConfirmDialog(getContentPane(),
						"Uwaga! Czy chcesz bezpowrotnie skasować fakturę??",
						"Czy zapisać fakturę bez numeru?", JOptionPane.YES_NO_OPTION);
				if (dec == JOptionPane.OK_OPTION) {
				lf.usun(listaFaktur.getSelectedValue());
				listaFaktur.setListData(lf.getFaktury());
				}
			}
		});
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(edytuj)
						.addComponent(przegladaj))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(dodaj).addComponent(usun)));// .addComponent(xxx)));

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(
						gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(edytuj).addComponent(dodaj))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(przegladaj).addComponent(usun)));// .addComponent(xxx)));
	
		up.setBounds(10, 10, getWidth() / 3, getHeight() / 3);
		down.setBounds(10 + up.getWidth(), 10, 200, 100);
		up.setBackground(Color.RED);
		down.setBackground(Color.cyan);
		down.setSize(300, 150);
		desktop.setBackground(Color.yellow);
		desktop.add(up);
		desktop.add(down);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/*public static void repaintList() {
		listaFaktur.repaint();
	}*/
	protected void createView(Faktura doPodgladu, int indexNaLiscie) {
		FormPodgladu fp = new FormPodgladu(doPodgladu, indexNaLiscie);
		fp.setVisible(true); // necessary as of 1.3
		desktop.add(fp);
		try {
			fp.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	protected void createFrame(boolean trybEdycji, Faktura doEdycji, int index) {
		FormDodajFakture frame = new FormDodajFakture(trybEdycji, doEdycji, index);
		frame.setVisible(true); // necessary as of 1.3
		desktop.add(frame);
		try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}

	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		Manager manager = new Manager();
		manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		manager.setVisible(true);
	}

}
