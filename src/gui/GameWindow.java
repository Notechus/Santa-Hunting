package gui;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Kid;

public class GameWindow extends JFrame
{
	public static final int WIDTH = 754; // inside + 4 for bar
	public static final int HEIGHT = 799; // inside + 49 for bar
	private int N;
	private int M;
	private int dx = 50;
	private int dy = 50;
	private JMenuBar menuBar;
	private GameBoard board;

	public GameWindow(int n, int m)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex)
		{
			ex.printStackTrace();
		}
		N = n;
		M = m;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(dx * N + 4, dy * M + 49);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setTitle("Santa Hunting");
		createMenu();
		board = new GameBoard(this, N, M);
		Kid.setSize(N, M);
		this.add(board);
		this.setResizable(false);

		this.setVisible(true);
	}

	public void exit()
	{
		this.dispose();
	}

	public void createMenu()
	{
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnGra = new JMenu("Gra");

		JMenuItem nowaGra = new JMenuItem("Nowa Gra");
		nowaGra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		nowaGra.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				board.reset();
			}

		});
		mnGra.add(nowaGra);
		JMenuItem exit = new JMenuItem("Wyjście");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		mnGra.add(exit);

		JMenu mnOpcje = new JMenu("Opcje");

		JMenuItem ustawienia = new JMenuItem("Ustawienia");
		ustawienia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK));
		ustawienia.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				new Settings();
			}
		});
		mnOpcje.add(ustawienia);

		JMenu mnPomoc = new JMenu("Pomoc");
		JMenuItem informacje = new JMenuItem("Informacje");
		informacje.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		String infoMsg = "Na planszy jest rozgrywana gra, w której Mikołaj rozdaje prezenty śpiącym dzieciakom,\n"
				+ "jednocześnie uciekając przed dziką gromadą szukających go i goniących za nim (a może raczej za\n"
				+ "prezentami, które posiada w swojej magicznej torbie) rozbudzonych dzieciaków. Celem gry jest\n"
				+ "obdarowanie wszystkich dzieciaków prezentami w taki sposób, aby każdy dzieciak dostał jeden\n"
				+ "prezent i aby Mikołaj nie został przyłapany na podrzucaniu paczki.\n";
		informacje.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(GameWindow.this, infoMsg);
			}

		});
		mnPomoc.add(informacje);

		JMenuItem autor = new JMenuItem("O autorze");
		String appMsg = "Santa Hunting\nAutor: Sebastian Paulus\nWersja: 1.1 beta\nData wydania: 03.01.2016";
		autor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(GameWindow.this, appMsg);
			}
		});
		mnPomoc.add(autor);

		menuBar.add(mnGra);
		menuBar.add(mnOpcje);
		menuBar.add(mnPomoc);
	}
}