package model;

import java.util.Random;

import javax.swing.table.AbstractTableModel;

public class GameModel extends AbstractTableModel
{
	private int N; // rows
	private int M; // columns
	private Field[][] f;
	private String[][] str;

	public GameModel(int n, int m)
	{
		N = n;
		M = m;
		f = new Field[N][M];
		str = new String[N][M];
		initBoard();
	}

	public void initBoard()
	{
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				f[i][j] = new Field(State.EMPTY, i, j);
				str[i][j] = "EMPTY";
			}
		}
		int a = 0;
		f[0][0].setState(State.SANTA);
		str[0][0] = "SANTA";
		Random r = new Random();
		while (a < 12)
		{
			int x = r.nextInt(N);
			int y = r.nextInt(M);
			if (f[x][y].getState() == State.EMPTY)
			{
				f[x][y].setState(State.KID);
				str[x][y] = "KID";
				++a;
			}
		}
	}

	public void setBoard()
	{
		// get Santa pos, get Children pos and update
	}

	@Override
	public int getColumnCount()
	{
		return M;
	}

	@Override
	public int getRowCount()
	{
		return N;
	}

	@Override
	public Object getValueAt(int arg0, int arg1)
	{

		return str[arg0][arg1];
	}

	private class Field
	{
		public State s;
		public int x;
		public int y;

		public Field(State s, int x, int y)
		{
			this.s = s;
			this.x = x;
			this.y = y;
		}

		public void setState(State s)
		{
			this.s = s;
		}

		public State getState()
		{
			return s;
		}
	}
}

enum State
{
	EMPTY, SANTA, KID, GIFT
}