package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Present
{
	public int x;
	public int y;
	public static BufferedImage presentImg;

	static
	{
		try
		{
			presentImg = ImageIO.read(new File("./src/present.png"));
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public Present(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
