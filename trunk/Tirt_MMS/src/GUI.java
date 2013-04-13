import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

class Panel extends JPanel implements ActionListener {
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
	Formic formic;
	SSP ssp;
	TreeMap<User, Bts> solve;

	public Panel() {
		setLayout(null);
		readBtses = new ArrayList<>();
		readUsers = new ArrayList<>();

		bts = new JTextField(
				"C:\\Users\\oem\\workspace_juno\\Tirt_MMS\\btses1.txt");
		user = new JTextField(
				"C:\\Users\\oem\\workspace_juno\\Tirt_MMS\\users2.txt");

		bts.setBounds(10, 10, 250, 30);
		user.setBounds(10, 60, 250, 30);

		read = new JButton("READ");
		read.setBounds(300, 60, 110, 30);
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

		penalty = new JTextField("1.0");
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (algSSP.isSelected()) {
			alg = 1;
		} else if (algFormic.isSelected()) {
			alg = 2;
		}

		Object source = arg0.getSource();
		if (source == read)
			read();
		else if (source == count)
			time = System.currentTimeMillis();
		switch (alg) {
		case 1:
			SSPAlg();
			break;
		case 2:
			formicAlg();
			break;
		case 3:
			break;
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
		System.out.println("read");
		repaint();
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
		System.out.println("SSP:");
		solve = ssp.getSolve();
		formic.printPath(solve);
		System.out.println(ssp.getTotalDistance(ssp.getSolve()));
		System.out.println("Time: " + (System.currentTimeMillis() - time)
				+ "ms");
		number = 2;
		repaint();
	}

	// �rodek w punkcie 400, 395

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = 10, high = 10;
		g.setColor(Color.black);
		g.drawLine(450, 90, 450, 700);
		g.drawLine(0, 395, 900, 395);

		switch (number) {
		case 1:
			for (Bts i : readBtses) {
				g.setColor(Color.yellow);
				g.fillArc((int) (450 + i.getX() - 5 * i.getRange()),
						(int) (395 + i.getY() - i.getRange() * 5),
						(int) i.getRange() * 10, (int) i.getRange() * 10, 0,
						360);
			}

			for (Bts i : readBtses) {
				g.setColor(Color.black);
				g.fillRect((int) (450 + i.getX() - 0.5 * width),
						(int) (395 + i.getY() - 0.5 * high), width, high);
			}

			for (User j : readUsers) {
				g.setColor(Color.red);
				g.fillRect((int) (450 + j.getX() - 0.5 * width),
						(int) (395 + j.getY() - 0.5 * high), width, high);
			}
			break;
		case 2:
			List<User> t[] = new List[readBtses.size()];
			for (int i = 0; i < t.length; i++)
				t[i] = new LinkedList<User>();

			User temp[][] = new User[readBtses.size()][readUsers.size()];
			ArrayList<User> tempU = new ArrayList<>();
			for (User u : solve.keySet()) {
				if (solve.get(u) == null)
					tempU.add(u);
				else
					for (Bts b : readBtses)
						// System.out.println(readBtses.size());
						if (solve.get(u).getId().equals(b.getId()))
							t[Integer.parseInt(b.getId().substring(1)) - 1]
									.add(u);
			}

			Color col[] = { Color.black, Color.blue, Color.cyan,
					Color.darkGray, Color.gray, Color.green, Color.magenta,
					Color.orange, Color.pink, Color.red, Color.yellow,
					Color.getHSBColor(106, 164, 232),
					Color.getHSBColor(75, 214, 43),
					Color.getHSBColor(106, 14, 62) };
			int colNr = 0;

			for (User r : tempU) {
				g.setColor(col[colNr]);
				g.fillRect((int) (450 + r.getX() - 0.5 * width),
						(int) (395 + r.getY() - 0.5 * high), width, high);
				colNr++;
			}

			int bt = 0;
			for (List<User> j : t) {
				g.setColor(col[colNr]);
				g.fillArc((int) (450 + readBtses.get(bt).getX() - 0.5 * width),
						(int) (395 + readBtses.get(bt).getY() - 0.5 * high),
						width, high, 0, 360);
				bt++;
				for (User u : j) {
					g.fillRect((int) (450 + u.getX() - 0.5 * width),
							(int) (395 + u.getY() - 0.5 * high), width, high);
				}

				colNr++;
			}
			break;

		}
	}

}
