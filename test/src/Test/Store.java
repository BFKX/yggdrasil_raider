package Test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Test.BusinessmanandEnemy;

public class Store 
{
	final int WIDTH = 600;
    final int HEIGHT = 600;
     
    JFrame frame;
    Canvas canvas;
    JButton button1 = new JButton("");
    JButton button2 = new JButton("");
    JButton button3 = new JButton("");
    BufferStrategy bufferStrategy;
    public Store(){
        frame = new JFrame("Store");
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.addWindowListener(new WindowAdapter()
        { 
	        public void windowClosing(WindowEvent e)
	        { 
	        	BusinessmanandEnemy.windowsopen = !BusinessmanandEnemy.windowsopen;
	        } 
	    }
        ); 
        button1.setIcon(new ImageIcon("echarpe.jpg"));
        button1.setBounds(0, 0, 200, 200);
  	    button1.addActionListener(new creatNewWindow());
  	    button2.setIcon(new ImageIcon("epee.jpg"));
        button2.setBounds(200, 0, 200, 200);
	    button2.addActionListener(new creatNewWindow());
	    button3.setIcon(new ImageIcon("cure.jpg"));
        button3.setBounds(400, 0, 200, 200);
  	    button3.addActionListener(new creatNewWindow());
  	    
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setBackground(Color.gray);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);
     
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
         
        canvas.requestFocus();
    }
    class creatNewWindow implements ActionListener
  		{
  		public void actionPerformed(ActionEvent e)
  		{
  		  // si le joueur clique sur un produit, il faut l'ajouter ¨¤ la barre d'¨¦quipement
  		  //	 if(e.getSource()== ... )
  		}
  		}
}
