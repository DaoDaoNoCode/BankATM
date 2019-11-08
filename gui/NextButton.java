package gui;

//forward button
@SuppressWarnings("serial")
public class NextButton extends AtmButton{
	public NextButton(String s, int size) {
		super(s, size);
		setBounds(620, 500, 175, 75);
	}
	public NextButton(String s) {
		super(s);
		setBounds(620, 500, 175, 75);
	}
	public NextButton() {
		this("Next");
	}
}
