package model;

import java.awt.Point;
import java.util.Random;

public class GameM
{
	private int N; // rows
	private int M; // columns

	private int[][] field;
	private Random r;
	private int kidCount;
	private int kidsAlive;

	private volatile boolean running = false;
	private volatile boolean win = false;
	private volatile boolean loss = false;

	public GameM(int n, int m)
	{
		N = n;
		M = m;
		kidCount = 0;
		r = new Random();
		field = new int[N][M]; // 0 = empty, -1 = present, 1 = kid, 2 = santa
		initBoard();
	}

	public void initBoard()
	{
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				field[i][j] = 0;
			}
		}
		field[0][0] = 2; // mikołaj w 0 0
	}

	public boolean isRunning()
	{
		return running;
	}

	public synchronized void setRunning(boolean run)
	{
		running = run;
	}

	public synchronized int getKidsAlive()
	{
		return kidsAlive;
	}

	public void resetKids()
	{
		kidCount = 0;
	}

	public synchronized void killKid()
	{
		if (kidsAlive > 0) kidsAlive--;
		else if (kidsAlive <= 0)
		{
			setWin();
			System.out.println("You win");
		}
	}

	public Point addKid(int bound)
	{
		boolean inserted = false;
		Point p = null;
		if (kidCount < bound)
		{
			while (!inserted)
			{
				int x = r.nextInt(N);
				int y = r.nextInt(M);
				if (field[x][y] == 0)
				{
					field[x][y] = 1;
					inserted = true;
					p = new Point(x, y);
					kidCount++;
					kidsAlive++;
				}
			}
		}
		return p;
	}

	public synchronized void changeState(int x, int y, int state)
	{
		field[x][y] = state;
		/*
		 * for (int i = 0; i < N; i++) { for (int j = 0; i < M; j++) { System.out.print(field[x][y]); } System.out.println(); }
		 */
	}

	public synchronized boolean isWin()
	{
		// check if win
		return win;
	}

	public synchronized void setWin()
	{
		win = true;
		running = false;
		loss = false;
	}

	public synchronized boolean isLoss()
	{
		// check if loss
		return loss;
	}

	public synchronized void setLoss()
	{
		loss = true;
		win = false;
		running = false;
	}

	public synchronized void restart()
	{
		loss = false;
		win = false;
		running = true;
		kidCount = 0;
		kidsAlive = 0;
		initBoard();
	}

	public synchronized int getState(int x, int y)
	{
		if (x < 0) return field[N - 1][y];
		else if (y < 0) return field[x][M - 1];
		else if (x >= N) return field[0][y];
		else if (y >= M) return field[x][0];
		else
			return field[x][y];
	}
}
