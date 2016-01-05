package model;

import gui.GameBoard;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Santa extends Thread
{
	private int x;
	private int y;
	public BufferedImage santaImg;

	public Santa(int x, int y)
	{
		this.x = x;
		this.y = y;
		try
		{
			santaImg = ImageIO.read(new File("./src/santaC.png"));
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public void setPos(int x, int y)
	{
		if (x >= GameBoard.N) x = 0;
		else if (x < 0) x = GameBoard.N - 1;
		if (y >= GameBoard.M) y = 0;
		else if (y < 0) y = GameBoard.M - 1;
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void run()
	{

	}
}
