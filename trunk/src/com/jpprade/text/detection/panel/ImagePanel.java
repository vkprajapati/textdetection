package com.jpprade.text.detection.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class ImagePanel extends javax.swing.JPanel {
	
	private BufferedImage image=null;

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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("paintComponent");
		System.out.println(this.getWidth());
		System.out.println(this.getHeight());
		if(image != null){
			//g.drawImage(image, 0,0,this.getWidth(),this.getHeight(), this);
			g.drawImage(image, 0,0, this);
		}
		g.drawString("Un texte", 0, 0);
		
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
		System.out.println("left "+insets.left);
		System.out.println("right "+insets.right);
		System.out.println("top "+insets.top);
		System.out.println("bottom "+insets.bottom);
		int w = insets.left + insets.right + image.getWidth();
		int h = insets.top + insets.bottom + image.getHeight();
		return new Dimension(w,h);

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
