package gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AtmPWField extends JPasswordField{
	private String text;
	public AtmPWField(String s) {
		text = s;
		setForeground(Color.gray);
		setBackground(Color.decode("#F7F7F7"));
		setHorizontalAlignment(JTextField.CENTER);
		setText(s);
		setEchoChar((char)0);
		FocusAdapter newListener = new FocusAdapter() {
			public void focusGained(FocusEvent e){
				setEchoChar('●');
				if (getPW().equals(s)) {
					setText("");
				}
			}
			public void focusLost(FocusEvent e){
				if (getPW().equals("")) {
					setText(s);
					setEchoChar((char)0);
				}
			}
		};
		addFocusListener(newListener);
	}
	public String getPW() {
		return String.valueOf(getPassword());
	}
	public boolean blank() {
		return getPW().equals("")||getPW().equals(text);
	}
	public void reset() {
		setText(text);
		setEchoChar((char)0);
	}
}
