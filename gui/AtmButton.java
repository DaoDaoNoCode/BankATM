package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class AtmButton extends JButton{//general button pattern
	public AtmButton(String s) {
		this(s, 20);
	}
	public AtmButton(String s, int n) {
		super(s);
		setFont(new Font("calibri",Font.BOLD, n));
		setForeground(Color.decode("#FFFFFF"));
		setBackground(Color.decode("#1E90FF"));
		setOpaque(true);
		setBorderPainted(false);
	}
}
