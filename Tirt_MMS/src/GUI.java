import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUI extends JFrame {

	GUI(String title) {
		setSize(900, 700);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Panel panel = new Panel();
		panel.setBounds(0, 0, 900, 700);
		panel.setBackground(Color.LIGHT_GRAY);
		add(panel);

		setVisible(true);
	}
}

class Panel extends JPanel implements ActionListener, MouseListener {
	JTextField bts, user, penalty;
	JButton read, count;
	JLabel chooseYourAlgorythm, penaltyFunction;
	JRadioButton algSSP, algFormic, algHungarian;
	ButtonGroup btngroup;
	ArrayList<Bts> readBtses;
	ArrayList<User> readUsers;
	Pharser ph = null;
	Panel panel;
	int number = 0, hight = 0;
	float penaltyCount = 0;
	Formic formic;
	SSP ssp;
	Hungarian hun;
	TreeMap<User, Bts> solve;

	public Panel() {
		setLayout(null);
		readBtses = new ArrayList<>();
		readUsers = new ArrayList<>();

		this.addMouseListener(this);

		bts = new JTextField(
				"C:\\Users\\sonia\\Downloads\\workspace_juno\\Tirt_MMS\\btses2.txt");
		user = new JTextField(
				"C:\\Users\\sonia\\Downloads\\workspace_juno\\Tirt_MMS\\users2.txt");

		bts.setBounds(10, 10, 290, 30);
		user.setBounds(10, 60, 290, 30);

		read = new JButton("READ");
		read.setBounds(320, 60, 110, 30);
		read.addActionListener(this);

		count = new JButton("COUNT");
		count.setBounds(620, 60, 110, 30);
		count.addActionListener(this);

		chooseYourAlgorythm = new JLabel("Choose your algorythm:");
		chooseYourAlgorythm.setBounds(450, 10, 150, 18);

		algSSP = new JRadioButton("SSP");
		algFormic = new JRadioButton("Formic");
		algHungarian = new JRadioButton("Hungarian");

		btngroup = new ButtonGroup();
		btngroup.add(algSSP);
		btngroup.add(algFormic);
		btngroup.add(algHungarian);

		algSSP.setBounds(450, 35, 100, 15);
		algFormic.setBounds(450, 55, 100, 15);
		algHungarian.setBounds(450, 75, 100, 15);

		penaltyFunction = new JLabel("Penalty Function:");
		penaltyFunction.setBounds(620, 10, 110, 18);

		penalty = new JTextField("100000.0");
		penalty.setBounds(620, 35, 110, 20);

		add(bts);
		add(user);
		add(read);
		add(count);
		add(chooseYourAlgorythm);
		add(penaltyFunction);
		add(algSSP);
		add(algFormic);
		add(algHungarian);
		add(penalty);

		formic = new Formic();
		solve = new TreeMap<>();
		setVisible(true);
	}

	public int alg = 0;

	long time;

	/**
	 * Wybiera akcjê w zale¿noœci od wciœniêtego buttona.
	 * 
	 * Sprawdza, który algorytm jest wybrany i odpala odpowiedni¹ metodê.
	 * 
	 * */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == read)
			read();
		else if (source == count) {
			if (algSSP.isSelected()) {
				alg = 1;
			} else if (algFormic.isSelected()) {
				alg = 2;
			} else if (algHungarian.isSelected()) {
				alg = 3;
			}

			penaltyCount = Float.parseFloat(penalty.getText());
			formic.setPenalty(penaltyCount);

			time = System.currentTimeMillis();
			switch (alg) {
			case 1:
				SSPAlg();
				break;
			case 2:
				formicAlg();
				break;
			case 3:
				hungarianAlg();
				break;
			}
		}

	}

	String btses = " ";
	String users = " ";

	public void read() {

		btses = bts.getText();
		users = user.getText();

		ph = new Pharser(btses, users);
		readBtses = ph.readBtses();
		readUsers = ph.readUsers();
		number = 1;
		hight = this.getHeight();
		// System.out.println("read");
		repaint();
	}

	public void hungarianAlg() {
		hun = new Hungarian(btses, users);
		System.out.println("Hungarian:");
		hun.compute();
		
		solve = hun.path;
		
//		for(User u:solve.keySet()){
//			System.out.print(u.getId()+"->"+solve.get(u)+"  ");
//		}
		formic.printPath(solve);
		System.out.println(formic.getTotalDistance(formic.getSolve()));
		System.out.println("Time: " + (System.currentTimeMillis() - time)
				+ "ms");
		number = 2;
		repaint();

//		for (MatrixPoint m : hun.matrixPoint) {
//			if(m.located.equals("unlocated"))
//				System.out.print(m.user.getId() + "->~UNALLOCATED  ");
//			else System.out.print(m.user.getId() + "->" + m.bts.getId()+"  ");
//		}
//		System.out.println("Time: " + (System.currentTimeMillis() - time)
//				+ "ms");
//		
	}

	public void formicAlg() {
		formic.setPharser(ph);
		formic.readBtses();
		formic.readUsers();
		formic.compute(2, 10);
		System.out.println("Formic:");
		// System.out.println(formic.getSolve());
		solve = formic.getSolve();
		formic.printPath(solve);
		System.out.println(formic.getTotalDistance(formic.getSolve()));
		System.out.println("Time: " + (System.currentTimeMillis() - time)
				+ "ms");
		number = 2;
		repaint();
	}

	public void SSPAlg() {
		ssp = new SSP(btses, users);
		ssp.setPenalty(penaltyCount);
		System.out.println("SSP:");
		solve = ssp.getSolve();
		formic.printPath(solve);
		System.out.println(ssp.getTotalDistance(ssp.getSolve()));
		System.out.println("Time: " + (System.currentTimeMillis() - time)
				+ "ms");
		number = 2;
		repaint();
	}

	// œrodek w punkcie 400, 395

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = 10, high = 10;
		g.setColor(Color.black);
		g.drawLine(450, 90, 450, 700);
		g.drawLine(0, 395, 900, 395);

		switch (number) {
		case 1:
			// Rysuje rozmieszczenie przed uruchomieniem algorytmu
			// Na ¿ó³to zasiêg stacji, czarny kwadrat to stacja, czerwony
			// kwadrat u¿ytkownik

			// Legenda
			g.setColor(Color.black);
			g.drawString("Key :", 10, 110);
			g.fillRect(15, 120, 10, 10);
			g.drawString(" - BTS", 30, 129);
			g.setColor(Color.red);
			g.fillRect(15, 140, 10, 10);
			g.drawString(" - USER", 30, 149);

			for (Bts i : readBtses) {
				g.setColor(Color.yellow);
				g.drawArc((int) (i.getX() - i.getRange()),
						(int) (i.getY() - i.getRange()),
						(int) i.getRange() * 2, (int) i.getRange() * 2, 0, 360);
				g.setColor(Color.black);
				g.fillRect((int) (i.getX() - 0.5 * width),
						(int) (i.getY() - 0.5 * high), width, high);
			}

			for (User j : readUsers) {
				g.setColor(Color.red);
				g.fillRect((int) (j.getX() - 0.5 * width),
						(int) (j.getY() - 0.5 * high), width, high);
			}
			break;
		case 2:

			// Legenda
			g.setColor(Color.black);
			g.drawString("Key :", 10, 110);
			g.setColor(Color.blue);
			g.fillArc(15, 120, 10, 10, 0, 360);
			g.drawString(" - COLORED BTS", 30, 129);
			g.fillRect(15, 140, 10, 10);
			g.drawString(" - COLORED USER ASSIGNED TO BTS", 30, 149);
			g.setColor(Color.white);
			g.fillRect(15, 160, 10, 10);
			g.drawString(" - UNALLOCATED USER", 30, 169);

			List<User> t[] = new List[readBtses.size()];
			for (int i = 0; i < t.length; i++)
				t[i] = new LinkedList<User>();

			User temp[][] = new User[readBtses.size()][readUsers.size()];
			ArrayList<User> tempU = new ArrayList<>();
			for (User u : solve.keySet()) {

				if (solve.get(u) == null
						|| solve.get(u).getId().equals("~UNALLOCATED")) {
					tempU.add(u);
				} else
					for (Bts b : readBtses)
						// System.out.println(readBtses.size());
						if (solve.get(u).getId().equals(b.getId()))
							t[Integer.parseInt(b.getId().substring(1)) - 1]
									.add(u);
			}

			Color col[] = { Color.green, Color.magenta, Color.black,
					Color.blue, Color.cyan, Color.darkGray, Color.gray,
					Color.orange, Color.pink, Color.red, Color.yellow,
					Color.getHSBColor(106, 164, 232),
					Color.getHSBColor(75, 214, 43),
					Color.getHSBColor(106, 14, 62) };
			int colNr = 0;

			// na bia³o rysuje userów, którzy nie s¹ przyporz¹dkowani do ¿adnego
			// btsa
			for (User r : tempU) {
				// g.setColor(col[colNr]);
				g.setColor(Color.white);
				g.fillRect((int) (r.getX() - 0.5 * width),
						(int) (r.getY() - 0.5 * high), width, high);
				g.drawString(r.getId(), (int) (r.getX() - 0.5 * width),
						(int) (r.getY() - 0.5 * high));
				// colNr++;
			}

			int bt = 0;
			for (List<User> j : t) {
				g.setColor(col[colNr]);
				// rysuje zasiêg
				g.drawArc((int) (readBtses.get(bt).getX() - readBtses.get(bt)
						.getRange()),
						(int) (readBtses.get(bt).getY() - readBtses.get(bt)
								.getRange()), (int) readBtses.get(bt)
								.getRange() * 2, (int) readBtses.get(bt)
								.getRange() * 2, 0, 360);
				// rysuje Btsa
				g.fillArc((int) (readBtses.get(bt).getX() - 0.5 * width),
						(int) (readBtses.get(bt).getY() - 0.5 * high), width,
						high, 0, 360);
				g.drawString(
						readBtses.get(bt).getId(),
						(int) (readBtses.get(bt).getX() - 0.5 * readBtses.get(
								bt).getRange()),
						(int) (readBtses.get(bt).getY() - 0.5 * readBtses.get(
								bt).getRange()));
				bt++;
				// kwadratem rysowani userzy
				for (User u : j) {
					g.fillRect((int) (u.getX() - 0.5 * width),
							(int) (u.getY() - 0.5 * high), width, high);
					g.drawString(u.getId(), (int) (u.getX() - 0.5 * width),
							(int) (u.getY() - 0.5 * high));
				}

				colNr++;
			}
			break;
			
		case 3:
			
			// Legenda
						g.setColor(Color.black);
						g.drawString("Key :", 10, 110);
						g.setColor(Color.blue);
						g.fillArc(15, 120, 10, 10, 0, 360);
						g.drawString(" - COLORED BTS", 30, 129);
						g.fillRect(15, 140, 10, 10);
						g.drawString(" - COLORED USER ASSIGNED TO BTS", 30, 149);
						g.setColor(Color.white);
						g.fillRect(15, 160, 10, 10);
						g.drawString(" - UNALLOCATED USER", 30, 169);
						
			break;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
