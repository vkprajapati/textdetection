package com.jpprade.text.detection.algo;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import com.jpprade.text.detection.algo.bean.ConnectedElement;
import com.jpprade.text.detection.algo.bean.Point;

public class ConnectedDetection {
	
	boolean[][] pixels = null; 
	BufferedImage image;
	boolean debug = false;
	
	public ArrayList<ConnectedElement> getConnectedElement(BufferedImage bi){
		ArrayList<ConnectedElement> alce =  new ArrayList<ConnectedElement>();
		image=bi;
		int w = bi.getWidth();
		int h = bi.getHeight();
		pixels = new boolean[w][h];
		for(int i=0;i<w;i++)
			for(int j=0;j<h;j++)
				pixels[i][j]=false;
		
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				if(pixels[i][j]){
					continue;					
				}
				int rgb = bi.getRGB(i, j);
				int b = (rgb >> 0) & 0xFF;
				
				if(b ==0){
					ConnectedElement ce = findConnecte(i,j);
					if(ce != null)
						alce.add(ce);
				}else{
					pixels[i][j]=true;
				}
			}
		}
		
		return alce;
	}

	private ConnectedElement findConnecte(int i, int j) {
		
		//ArrayList<Point> connectedpixels = findPoint(i,j);
		ArrayList<Point> connectedpixels = findPointIter(i,j);
		
		//System.out.println("nb conected size" + connectedpixels.size());
		if(connectedpixels.size() < 25)
			return null;
		else{
			Point topleft =  connectedpixels.get(0).clone();
			Point bottomright =  connectedpixels.get(0).clone();
			
			
			for(Point p : connectedpixels){				
				if(p.getX() < topleft.getX()){
					topleft.setX(p.getX());
				}
				
				if(p.getX() > bottomright.getX()){
					bottomright.setX(p.getX());
				}
				
				if(p.getY() < topleft.getY()){
					topleft.setY(p.getY());
				}
				
				if(p.getY() > bottomright.getY()){
					bottomright.setY(p.getY());
				}
			}
			
			ConnectedElement ce = new ConnectedElement();
			ce.setTopleft(topleft);
			ce.setBottomright(bottomright);
			ce.setPoints(connectedpixels);
			return ce;
		}
	}

	private ArrayList<Point> findPoint(int i, int j) {
		if(i<0 || j<0 || i >= image.getWidth() || j >= image.getHeight() ||  pixels[i][j])
			return new ArrayList<Point>(0);
		int rgb = image.getRGB(i, j);
		int b = (rgb >> 0) & 0xFF;
		if(b == 0){
			pixels[i][j]=true;
			ArrayList<Point> points =new ArrayList<Point>();
			points.add(new Point(i,j));
			
			points.addAll(findPoint(i-1, j-1));
			points.addAll(findPoint(i, j-1));
			points.addAll(findPoint(i+1, j-1));
			points.addAll(findPoint(i+1, j));
			points.addAll(findPoint(i+1, j+1));
			points.addAll(findPoint(i, j+1));
			points.addAll(findPoint(i-1, j+1));
			points.addAll(findPoint(i-1, j));
			return points;
		}else{
			return new ArrayList<Point>(0);
		}
	}
	
	private ArrayList<Point> findPointIter(int i, int j) {
		ArrayList<Point> points =new ArrayList<Point>();
		Stack<Point> stack = new Stack<Point>();
		Point p =new Point(i,j);
		stack.push(p);
		int x=0;
		int y=0;
		while(stack.size() > 0){
			Point tmp = stack.pop();
			x = tmp.getX();
			y = tmp.getY();
			if( x <0 || y<0 || x >= image.getWidth() || y >= image.getHeight() || pixels[x][y])
				continue;
			int rgb = image.getRGB(x, y);
			int b = (rgb >> 0) & 0xFF;
			if(b == 0){				
				points.add(tmp);
				stack.push(new Point(x-1,y-1));
				stack.push(new Point(x,y-1));
				stack.push(new Point(x+1,y-1));
				stack.push(new Point(x+1,y));
				stack.push(new Point(x+1,y+1));
				stack.push(new Point(x,y+1));
				stack.push(new Point(x-1,y+1));
				stack.push(new Point(x-1,y));
			}
			pixels[x][y]=true;
		}
		return points;
	}

	public static BufferedImage getImage(ArrayList<ConnectedElement> elements,int x,int y) {
		BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		for(int j=0;j<y;j++){
			for(int i=0;i<x;i++){
				bi.setRGB(i, j, 0xFFFFFF);
			}
		}
		for(ConnectedElement ce : elements){
			ArrayList<Point> points = ce.getPoints();
			for(Point p : points){
				bi.setRGB(p.getX(), p.getY(), 0x000000);
			}
		}
		return bi;
	}

}
