package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.GameM;
import model.Kid;
import model.Present;
import model.Santa;

public class GameBoard extends JPanel implements Runnable, ActionListener
{
	public static int N; // rows
	public static int M; // columns
	private final int speed = 50;
	private int dx;
	private int dy;
	private int kidN = 2;
	private Santa s;
	private Kid[] kids;
	private ArrayList<Present> presents;
	private GameM gameModel;
	Thread timerTh;
	private Timer timer;
	private GameWindow parent;

	public GameBoard(GameWindow parent_ref, int n, int m)
	{
		parent = parent_ref;
		N = n;
		M = m;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex)
		{
			ex.printStackTrace();
		}
		this.addKeyListener(new KeyboardCallback());
		this.setFocusable(true);
		presents = new ArrayList<>();
		gameModel = new GameM(N, M);
		initGame();
		timer = new Timer(speed, this);
		timerTh = new Thread("timer")
		{
			public void run()
			{
				timer.start();
			}
		};
		timerTh.start();

	}

	public void initGame()
	{
		s = new Santa(0, 0);
		dx = dy = 50;
		kids = new Kid[kidN];
		gameModel.setRunning(true);
		if (presents != null) presents.clear();
		for (int i = 0; i < kidN; i++)
		{
			Point p = gameModel.addKid(kidN);
			kids[i] = new Kid(gameModel, p.x, p.y);
		}
		run();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintBoard(g);
		paintSanta(g);
		paintKids(g);
		for (int i = 0; i < presents.size(); i++)
		{
			Present p = presents.get(i);
			g.drawImage(Present.presentImg.getScaledInstance(dx - 10, dy - 10, Image.SCALE_SMOOTH), (p.x * dx) + 5,
					(p.y * dy) + 5, null);
		}
	}

	public void paintSanta(Graphics g)
	{
		g.drawImage(s.santaImg.getScaledInstance(dx - 10, dy - 10, Image.SCALE_SMOOTH), ((s.getX()) * dx) + 5,
				((s.getY()) * dy) + 5, null);
	}

	public void paintPresent(Graphics g)
	{
		g.drawImage(Present.presentImg.getScaledInstance(dx - 10, dy - 10, Image.SCALE_SMOOTH), ((s.getX()) * dx) + 5,
				((s.getY()) * dy) + 5, null);
		presents.add(new Present(s.getX(), s.getY()));
		gameModel.changeState(s.getX(), s.getY(), -1);
	}

	public void paintBoard(Graphics g)
	{
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				g.drawRect(i * dx, j * dy, dx, dy);
			}
		}
	}

	public void paintKids(Graphics g)
	{
		for (int i = 0; i < kidN; i++)
		{
			g.drawImage(Kid.kidImg.getScaledInstance(dx - 10, dy - 10, Image.SCALE_SMOOTH), ((kids[i].getX()) * dx) + 5,
					((kids[i].getY()) * dy) + 5, null);
		}
	}

	public void changeSanta(int x, int y) // 0 = empty, -1 = present, 1 = kid, 2 = santa
	{
		int state = gameModel.getState(s.getX() + x, s.getY() + y);
		if (state == 0)
		{
			if (gameModel.getState(s.getX(), s.getY()) == -1) gameModel.changeState(s.getX(), s.getY(), -1);
			else if (gameModel.getState(s.getX(), s.getY()) == 1) gameModel.changeState(s.getX(), s.getY(), 1);
			else
				gameModel.changeState(s.getX(), s.getY(), 0);
			s.setPos(s.getX() + x, s.getY() + y);
			gameModel.changeState(s.getX(), s.getY(), 2);
		}
	}

	public void run()
	{
		for (int i = 0; i < kidN; i++)
		{
			new Thread(kids[i]).start();
		}
		// timer.start();
	}

	public void reset()
	{
		gameModel.restart();
		initGame();
	}

	private class KeyboardCallback implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if (gameModel.isRunning()) changeSanta(0, -1);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if (gameModel.isRunning()) changeSanta(0, 1);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (gameModel.isRunning()) changeSanta(-1, 0);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (gameModel.isRunning()) changeSanta(1, 0);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				if (gameModel.isRunning()) paintPresent(GameBoard.this.getGraphics());
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{

		}

		@Override
		public void keyTyped(KeyEvent e)
		{

		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (gameModel.isRunning()) repaint();
		if (gameModel.getKidsAlive() <= 0) gameModel.setWin();
		if (gameModel.isLoss())
		{
			int res = JOptionPane.showConfirmDialog(this, "Przegrałeś/aś. Chcesz zacząć jeszcze raz?");
			if (res == 0)
			{
				reset();
			} else
			{
				parent.exit();
				System.exit(0);
			}
		} else if (gameModel.isWin())
		{
			int res = JOptionPane.showConfirmDialog(this, "Przegrałeś/aś. Chcesz zacząć jeszcze raz?");
			if (res == 0)
			{
				reset();
			} else
			{
				parent.exit();
				System.exit(0);
			}
		}
	}
}
