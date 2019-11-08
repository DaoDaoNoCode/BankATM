package gui;

import java.awt.Color;

//backward button
@SuppressWarnings("serial")
public class CancelButton extends AtmButton{
	public CancelButton() {
		super("Cancel");
		setBounds(2, 500, 175, 75);
		setBackground(Color.decode("#EE5C42"));
	}
}
