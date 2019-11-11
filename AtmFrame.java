import javax.swing.*;

@SuppressWarnings("serial")
public class AtmFrame extends JFrame {
    private AtmPanel panel;

    public AtmFrame() {
        setTitle("OOD Bank Atm");
        setSize(800, 600);
        setLocation(200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setPanel(AtmPanel newPanel) {
        panel = newPanel;
        add(panel);
        panel.setVisible(true);
        setVisible(true);
    }

    public void addPanel(AtmPanel newPanel) {//switch panels
        newPanel.reset();
        panel.setVisible(false);
        setPanel(newPanel);
    }
}
