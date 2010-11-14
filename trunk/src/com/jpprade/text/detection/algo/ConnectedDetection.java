package com.jpprade.text.detection.algo;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
		//remove element at on the border
		Iterator<ConnectedElement> it = alce.iterator();
		while(it.hasNext()){
			ConnectedElement c =it.next();
			if(c.getTopleft().getX() ==0 || c.getTopleft().getY() == 0 || c.getBottomright().getX() == (w-1) || c.getBottomright().getY() == (h-1)){
				it.remove();
			}
		}
		//
		it = alce.iterator();
		while(it.hasNext()){
			ConnectedElement c =it.next();
			if(! c.isRealRatio()){
				it.remove();
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
	
	public static ArrayList<ConnectedElement> findLines(ArrayList<ConnectedElement> elements){
		//java.util.HashSet<ConnectedElement> hs = new HashSet<ConnectedElement>();
		HashSet<ConnectedElement> hs = new HashSet<ConnectedElement>();
		
		for(ConnectedElement ce1 : elements){
			boolean overlaping = false;
			boolean isleftest = true;
			for(ConnectedElement ce2 : elements){
				if(ce1 !=ce2){
					/*if(!overlaping && ce1.isOverlaping(ce2) ){
						overlaping = true;
					}*/
					if(ce1.isOverlaping(ce2)){
						overlaping = true;
						if(ce1.isRight(ce2)){					
							isleftest=false;	
						}
					}
				}
			}
			if(!overlaping || isleftest){
				hs.add(ce1);
			}
			
		}
		if(true && false)
			return new ArrayList<ConnectedElement>(hs);
		
		HashMap<ConnectedElement,HashSet<ConnectedElement>> lines = new HashMap<ConnectedElement,HashSet<ConnectedElement>>(); 
		for(ConnectedElement ce1 : hs){
			for(ConnectedElement ce2 : elements){
				if(ce1==ce2 || ce1.isOverlaping(ce2)){
					HashSet<ConnectedElement> h = lines.get(ce1);
					if(h==null){
						h = new HashSet<ConnectedElement>();
						h.add(ce2);
						lines.put(ce1, h);
					}else{
						h.add(ce2);
					}
				}
			}
		}
		ArrayList<ConnectedElement> alines = getLines(lines);
		Iterator<ConnectedElement> iter1 = alines.iterator();
		Iterator<ConnectedElement> iter2 = alines.iterator();
		System.out.println("avant =" + alines.size());
		while(iter1.hasNext()){
			ConnectedElement ce1 = iter1.next();
			/*while(iter2.hasNext()){
				ConnectedElement ce2 = iter2.next();
				if(ce1==ce2)
					continue;
				if(ce1.isContained(ce2)){
					System.out.println("is contained");
					iter1.remove();
					break;
				}
			}*/
			for(ConnectedElement ce2 : alines){
				if(ce1==ce2)
					continue;
				if(ce1.isContained(ce2)){
					System.out.println("is contained");
					iter1.remove();
					break;
				}
			}
		}
		System.out.println("apres =" + alines.size());
		
		return alines;
	}
	
	protected static ArrayList<ConnectedElement> getLines(HashMap<ConnectedElement,HashSet<ConnectedElement>> linesce){
		ArrayList<ConnectedElement> lines = new ArrayList<ConnectedElement>();
		Set<ConnectedElement> firsts = linesce.keySet();
		for(ConnectedElement ce : firsts){
			HashSet<ConnectedElement> ces = linesce.get(ce);
			int topLeftX = ce.getTopleft().getX();
			int topLeftY = ce.getTopleft().getY();
			int bottomRightX = ce.getBottomright().getX();
			int bottomRightY = ce.getBottomright().getY();
			for(ConnectedElement ce1 : ces){
				if(ce1.getTopleft().getX() < topLeftX)
					topLeftX = ce1.getTopleft().getX();
				if(ce1.getTopleft().getY() < topLeftY)
					topLeftY = ce1.getTopleft().getY();
				
				if(ce1.getBottomright().getX() > bottomRightX)
					bottomRightX = ce1.getBottomright().getX();
				if(ce1.getBottomright().getY() > bottomRightY)
					bottomRightY = ce1.getBottomright().getY();
			}
			Point tl = new Point(topLeftX, topLeftY);
			Point br = new Point(bottomRightX, bottomRightY);
			ConnectedElement c = new ConnectedElement();
			c.setTopleft(tl);
			c.setBottomright(br);
			lines.add(c);
			
		}
		return lines;
	}

}
