package com.jpprade.text.detection.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.WindowConstants;
import javax.swing.JFrame;

import com.jpprade.text.detection.algo.bean.ConnectedElement;

public class ImagePanel extends javax.swing.JPanel {
	
	private BufferedImage image=null;
	
	private ArrayList<ConnectedElement> elements=null;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
		validate();
		repaint();
	}
	
	public void setConnectedElements(ArrayList<ConnectedElement> el){
		elements = el;
		validate();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*System.out.println("paintComponent");
		System.out.println(this.getWidth());
		System.out.println(this.getHeight());*/
		if(image != null){
			//g.drawImage(image, 0,0,this.getWidth(),this.getHeight(), this);
			g.drawImage(image, 0,0, this);
		}
		//g.drawString("Un texte", 0, 0);
		if(elements !=null){
			g.setColor(new Color(255,10,0));
			for(ConnectedElement ce : elements){
				int x1 =ce.getTopleft().getX();
				int y1 =ce.getTopleft().getY();
				int x2 =ce.getBottomright().getX();
				int y2 =ce.getBottomright().getY();
				
				g.drawRect(x1, y1, (x2-x1), (y2-y1));
			}
		}
		
	}
	
	

	
/*
	@Override
	public void update(Graphics g) {
		if(image != null)
			g.drawImage(image, 0,0,this.getWidth(),this.getHeight(), this);
		super.update(g);
	}*/

	@Override
	public Dimension getPreferredSize() {
		Insets insets = getInsets();
		/*System.out.println("left "+insets.left);
		System.out.println("right "+insets.right);
		System.out.println("top "+insets.top);
		System.out.println("bottom "+insets.bottom);*/
		if(image !=null){
			int w = insets.left + insets.right + image.getWidth();
			int h = insets.top + insets.bottom + image.getHeight();
			return new Dimension(w,h);
		}else{
			int w = insets.left + insets.right ;
			int h = insets.top + insets.bottom ;
			return new Dimension(w,h);
		}

	}

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ImagePanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ImagePanel() {
		super();
		initGUI();
		validate();
		repaint();
	}
	
	public ImagePanel(BufferedImage c) {
		super();
		image=c;
		initGUI();
		validate();
		repaint();
	}
	
	private void initGUI() {
		setOpaque(true);
		try {
			setPreferredSize(new Dimension(400, 300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
