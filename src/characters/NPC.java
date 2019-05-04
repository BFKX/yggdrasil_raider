package characters;

import tools.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NPC
{
    static boolean windowsopen = false;
    Coordinate coordinate;
    private final JButton npc = new JButton();
    public NPC(Coordinate coordinate)
{
    this.coordinate = coordinate;
    double RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    this.npc.setIcon(new ImageIcon("resources/images/waitingCharacter.png"));
    npc.setBounds((int)this.coordinate.getX(), (int)this.coordinate.getY(), (int)RADIUS, (int)RADIUS);
    npc.setBorderPainted(false);
    npc.addActionListener(new creatNewWindow());
}
    class creatNewWindow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!windowsopen) {
                windowsopen = !windowsopen;
               // new Store();  //open a store
            }
        }
    }
    public JButton getButton()
    {
        return this.npc;
    }
}
