package gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AtmTextField extends JTextField{
	private String text;
	public AtmTextField(String s) {
		super(s);
		text = s;
		setForeground(Color.gray);
		setBackground(Color.decode("#F7F7F7"));
		setHorizontalAlignment(JTextField.CENTER);
		setText(s);
		FocusAdapter newListener = new FocusAdapter() {
			public void focusGained(FocusEvent e){
				if (getText().equals(s))
					setText("");
			}
			public void focusLost(FocusEvent e){
				if (getText().equals(""))
					setText(s);
			}
		};
		addFocusListener(newListener);
	}
	public boolean blank() {
		return getText().equals("")||getText().equals(text);
	}
	public void reset() {
		setText(text);
	}
}
