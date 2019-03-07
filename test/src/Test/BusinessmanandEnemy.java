package Test;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

	 
	 
	public class BusinessmanandEnemy extends JFrame implements Runnable{
	     
	    /**
		 * 
		 */
		private static final long serialVersionUID = 3570941532862942675L;
		
		final int WIDTH = 1000;
	    final int HEIGHT = 700;
	     
	    JFrame frame;
	    Canvas canvas;
	    JButton button = new JButton("");
	    BufferStrategy bufferStrategy;
	    static boolean windowsopen = false;
	     
	    public BusinessmanandEnemy(){
	        frame = new JFrame("Test");
	        button.setIcon(new ImageIcon("NPG.gif")); //la photo de NPC
			button.setBounds(90, 500, 90, 110);
			button.setBackground(Color.white);
			button.setBorderPainted(false);
		  	button.addActionListener(new creatNewWindow());
		  	frame.add(button);
	        JPanel panel = (JPanel) frame.getContentPane();
	        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        panel.setLayout(null);
	        canvas = new Canvas();
	        canvas.setBounds(0, 0, WIDTH, HEIGHT);
	        canvas.setBackground(Color.white);
	        canvas.setIgnoreRepaint(true);
	        panel.add(canvas);
	     
	        canvas.addMouseListener(new MouseControl());
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setResizable(false);
	        frame.setVisible(true);
	        canvas.createBufferStrategy(2);
	        bufferStrategy = canvas.getBufferStrategy();
	         
	        canvas.requestFocus();
	    }
	     
	         
	    private class MouseControl extends MouseAdapter{
	         
	    }
	     
	    long desiredFPS = 120;
	    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
	     
	    boolean running = true;
	     
	    public void run(){
	         
	        long beginLoopTime;
	        long endLoopTime;
	        long currentUpdateTime = System.nanoTime(); 
	        long lastUpdateTime;
	        long deltaLoop;
	         
	        while(running){
	            beginLoopTime = System.nanoTime();
	             
	            render();
	             
	            lastUpdateTime = currentUpdateTime;
	            currentUpdateTime = System.nanoTime();
	            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
	             
	            endLoopTime = System.nanoTime();
	            deltaLoop = endLoopTime - beginLoopTime;
	             
	            if(deltaLoop > desiredDeltaLoop){
	                     //Do nothing. We are already late.
	            }else{
	                try{
	                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
	                }catch(InterruptedException e){
	                    //Do nothing
	                }
	            }
	        }
	    }
	     
	    private void render() {
	        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
	        g.clearRect(0, 0, WIDTH, HEIGHT);
	        render(g);
	        g.dispose();
	        bufferStrategy.show();
	    }
	     
	    //TESTING
	    private double x = 0;
	    private double y = 0;
	    private double c = 600;
	    private double d = 200;
	   
	    protected void update(int deltaTime){
	    	if(y<=0 && x<=500)
	    	{
	    		y = 0;
	    		x += deltaTime * 0.05;// la vitesse de monstre
	    	}
	    	else if(x>=500 && y<=300)
	    	{
	    		x = 500;
	    		y += deltaTime * 0.05;
	    	}
	    	else if(y>=300 && x>=0)
	    	{
	    		y = 300;
	    		x -= deltaTime * 0.05;
	    	}
	    	else if(x<=0 && y>=0)
	    	{
	    		x = 0;
	            y -= deltaTime * 0.05;
	    	}
	    	if(d<=0 && c<=500)
	    	{
	    		d = 0;
	    		c += deltaTime * 0.05;
	    	}
	    	else if(c>=500 && d<=300)
	    	{
	    		c = 500;
	    		d += deltaTime * 0.05;
	    	}
	    	else if(d>=300 && c>=0)
	    	{
	    		d = 300;
	    		c -= deltaTime * 0.05;
	    	}
	    	else if(c<=0 && d>=0)
	    	{
	    		c = 0;
	            d -= deltaTime * 0.05;
	    	}
	    }
	     
	    
	    protected void render(Graphics2D g){
	       Graphics2D g2d = (Graphics2D)g;
	       Toolkit tk = getToolkit();
	       Image img2 = tk.getImage("monstre2.gif"); //la photo de monstre
	       Image img22 = tk.getImage("monstre22.gif");
	  	   Image img = tk.getImage("monstre.gif");
	  	   Image img1 = tk.getImage("monstre11.gif");
	  	   if(y == 0 || x == 500)
	  	   g2d.drawImage(img, (int)x, (int)y, 120, 120,this);
	  	   else if(y == 300 || x == 0)
	  	   g2d.drawImage(img1, (int)x, (int)y, 120, 120,this);
	  	   
	  	   if(d == 0 || c == 500)
		  	   g2d.drawImage(img22, (int)c, (int)d, 120, 120,this);
		   else if(d == 300 || c == 0)
		  	   g2d.drawImage(img2, (int)c, (int)d, 120, 120,this);
	    }
	    class creatNewWindow implements ActionListener
		{
		public void actionPerformed(ActionEvent e)
		{
			if(!windowsopen)
			{
			   windowsopen = !windowsopen; 
		       new Store();  //ouvrir un magazin
			}
		}
		}
	    
	     
	   public static void main(String [] args)
	    {
	    	BusinessmanandEnemy ex1 = new BusinessmanandEnemy();
	        new Thread(ex1).start();
	    } 
	}
	
