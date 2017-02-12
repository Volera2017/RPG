package com.volerainc.game;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{
    public Boolean running = false;

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 160;

    public static final int HEIGTH = WIDTH / 12 * 9;

    public static final int SCALE = 3;

    public static String NAME = "Game";
    
    public int tickCount = 0;
    
    private BufferedImage image = new BufferedImage(WIDTH,HEIGTH,BufferedImage.TYPE_INT_RGB);

    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    
    private JFrame frame;

    public Game()
    {
        setMinimumSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));
        setMaximumSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));
        setPreferredSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));

        frame  = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void tick()
    {
    	tickCount++;
    }
    
    public void render()
    {
    	BufferStrategy bs = getBufferStrategy();
    	if(bs == null)
    	{
    		createBufferStrategy(3);
    		return;
    	}
    	
    	Graphics g = bs.getDrawGraphics();
    	
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0,  getWidth(), getHeight());
    	
    	g.dispose();
    	bs.show();
    }

    public void run()
    {
    	long lastTime = System.nanoTime();
    	double nsPerTick = 1E9D/60D;
    	
    	int frames = 0;
    	int ticks = 0;
    	
    	long lastTimer  = System.currentTimeMillis();
    	double delta = 0;
    	
        while(running)
        {
        	long now = System.nanoTime();
        	delta += (now - lastTime) / nsPerTick;
        	lastTime = now;
        	boolean shouldRender = true;
        	
        	while(delta>=1)
        	{
        		ticks++;
        		tick();
        		delta--;
        		shouldRender = true;
        	}
        	
        	
        	try 
        	{
				Thread.sleep(2);
			} 
        	catch (InterruptedException e) 
        	{
				e.printStackTrace();
			}
        	
        	if(shouldRender)
        	{
        		frames++;
        		render();
        	}
        	
        	render();
        	
        	if(System.currentTimeMillis() - lastTimer >= 1000)
        	{
        		lastTimer += 1000;
        		System.out.println(frames + ", " + ticks);
        		frames = 0;
        		ticks = 0;
        	}
        }
    }

    public synchronized void start()
    {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop()
    {
        running = false;
    }

    public static void main(String[] args)
    {
        new Game().start();
    }
}