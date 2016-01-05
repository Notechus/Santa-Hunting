package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Settings extends JFrame
{

	private final int width = 300;
	private final int height = 400;

	private JPanel contentPane;
	private JTextField wiersze, kolumny;
	private JButton play;

	public Settings()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		setTitle("Settings");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblSantaHunting = new JLabel("Santa Hunting");
		lblSantaHunting.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblSantaHunting.setBounds(new Rectangle(50, 50, 300, 40));
		// getContentPane().add(lblSantaHunting);

		JLabel lblWymiaryPlanszy = new JLabel("Wymiary planszy:");
		lblWymiaryPlanszy.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWymiaryPlanszy.setBounds(new Rectangle(90, 150, 300, 40));

		getContentPane().add(lblWymiaryPlanszy);

		JLabel lblKolumny = new JLabel("Kolumny:");
		lblKolumny.setBounds(new Rectangle(28, 192, 50, 40));
		getContentPane().add(lblKolumny);

		JLabel lblWiersze = new JLabel("Wiersze:");
		lblWiersze.setBounds(new Rectangle(152, 192, 50, 40));
		getContentPane().add(lblWiersze);

		kolumny = new JTextField("10");
		kolumny.setBounds(new Rectangle(75, 200, 50, 25));
		getContentPane().add(kolumny);

		wiersze = new JTextField("10");
		wiersze.setBounds(new Rectangle(200, 200, 50, 25));
		getContentPane().add(wiersze);

		play = new JButton("Graj");
		play.setBounds(new Rectangle(110, 300, 80, 40));
		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int n = Integer.parseInt(wiersze.getText());
				int m = Integer.parseInt(kolumny.getText());
				if (n < 10) n = 10;
				if (m < 10) m = 10;
				dispose();
				new GameWindow(n, m);
			}

		});
		getContentPane().add(play);
		setVisible(true);
	}
}
