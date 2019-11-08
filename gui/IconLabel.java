package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class IconLabel extends JLabel{
	public IconLabel(){
		this(30, 5);
	}
	public IconLabel(int x, int y) {
		this("OOD Bank", x, y);
	}
	public IconLabel(String s, int x, int y) {
		super(s);
		setBounds(x, y, 300, 100);
		setFont(new Font("calibri",Font.BOLD, 30));
		setForeground(Color.decode("#4169E1"));
		setFocusable(false);
	}
}
