package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Kid implements Runnable
{
	private int x;
	private int y;
	private static int N;
	private static int M;
	private int prevX, prevY;
	public static BufferedImage kidImg;
	Random r;
	private GameM parent;
	private boolean alive;

	static
	{
		try
		{
			kidImg = ImageIO.read(new File("./src/kid.png"));
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public Kid(GameM parent_ref, int x, int y)
	{
		parent = parent_ref;
		alive = true;
		r = new Random();
		this.x = x;
		this.y = y;
	}

	public static void setSize(int n, int m)
	{
		N = n;
		M = m;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void validatePosition()
	{
		if (x < 0) x = N - 1;
		if (y < 0) y = M - 1;
		if (x > N - 1) x = 0;
		if (y > M - 1) y = 0;
		parent.changeState(x, y, 1);
		parent.changeState(prevX, prevY, 0);
	}

	public boolean validateState(int xx, int yy)
	{
		int state = parent.getState(xx, yy);
		// 0 = empty, -1 = present, 1 = kid, 2 = santa
		if (state < 0) return false;
		else
			return true;
	}

	public boolean checkPresent(int direction)
	{
		boolean exists = false;
		switch (direction)
		{
			case 1:
				if (parent.getState(x, y - 1) == -1) exists = true;
				break;
			case 2:
				if (parent.getState(x, y + 1) == -1) exists = true;
				break;
			case 3:
				if (parent.getState(x - 1, y) == -1) exists = true;
				break;
			case 4:
				if (parent.getState(x + 1, y) == -1) exists = true;
				break;
		}
		return exists;
	}

	public void move()
	{
		prevX = x;
		prevY = y;
		if (checkPresent(1))
		{
			// move up to present and stay there
			y--;
			validatePosition();
			parent.killKid();
			alive = false;
			return;
		}
		if (checkPresent(2))
		{
			// move down to present and stay there
			y++;
			validatePosition();
			parent.killKid();
			alive = false;
			return;
		}
		if (checkPresent(3))
		{
			// move left to present and stay there
			x--;
			validatePosition();
			parent.killKid();
			alive = false;
			return;
		}
		if (checkPresent(4))
		{
			// move right to present and stay there
			x++;
			validatePosition();
			parent.killKid();
			alive = false;
			return;
		}
		// check if next to santa
		if (parent.getState(x, y - 1) == 2)
		{
			parent.setLoss();
			return;
		} else if (parent.getState(x, y + 1) == 2)
		{
			parent.setLoss();
			return;
		} else if (parent.getState(x - 1, y) == 2)
		{
			parent.setLoss();
			return;
		} else if (parent.getState(x + 1, y) == 2)
		{
			parent.setLoss();
			return;
		}
		boolean state = false;
		// seek for santa
		if (checkSanta(1))
		{
			state = validateState(x, y - 1);
			if (state) y--;
			validatePosition();
			return;
		} else if (checkSanta(2))
		{
			state = validateState(x, y + 1);
			if (state) y++;
			validatePosition();
			return;
		} else if (checkSanta(3))
		{
			state = validateState(x - 1, y);
			if (state) x--;
			validatePosition();
			return;
		} else if (checkSanta(4))
		{
			state = validateState(x + 1, y);
			if (state) x++;
			validatePosition();
			return;
		} else
		{
			if (r.nextInt(3) == 0) // up
			{
				state = validateState(x, y - 1);
				if (state) y--;
				validatePosition();
				return;
			} else if (r.nextInt(3) == 1) // down
			{
				state = validateState(x, y + 1);
				if (state) y++;
				validatePosition();
				return;
			} else if (r.nextInt(3) == 2) // left
			{
				state = validateState(x - 1, y);
				if (state) x--;
				validatePosition();
				return;
			} else if (r.nextInt(3) == 3) // right
			{
				state = validateState(x + 1, y);
				if (state) x++;
				validatePosition();
				return;
			}
		}
	}

	public boolean checkSanta(int direction) // 1-up 2-down 3-left 4-right
	{
		boolean exists = false;
		switch (direction)
		{
			case 1:
				if (parent.getState(x, y - 1) == 2 || parent.getState(x, y - 2) == 2 || parent.getState(x, y - 3) == 2
						|| parent.getState(x, y - 4) == 2)
					exists = true;
				break;
			case 2:
				if (parent.getState(x, y + 1) == 2 || parent.getState(x, y + 2) == 2 || parent.getState(x, y + 3) == 2
						|| parent.getState(x, y + 4) == 2)
					exists = true;
				break;
			case 3:
				if (parent.getState(x - 1, y) == 2 || parent.getState(x - 2, y) == 2 || parent.getState(x - 3, y) == 2
						|| parent.getState(x - 4, y) == 2)
					exists = true;
				break;
			case 4:
				if (parent.getState(x + 1, y) == 2 || parent.getState(x + 2, y) == 2 || parent.getState(x + 3, y) == 2
						|| parent.getState(x + 4, y) == 2)
					exists = true;
				break;
		}
		return exists;
	}

	public void run()
	{
		while (parent.isRunning() && alive)
		{
			try
			{
				move();
				Thread.currentThread().sleep((int) (Math.random() * 2000));
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
				return;
			}
		}
	}
}
