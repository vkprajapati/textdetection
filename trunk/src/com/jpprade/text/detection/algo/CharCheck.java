package com.jpprade.text.detection.algo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jpprade.text.detection.algo.bean.ConnectedElement;

public class CharCheck {
	
	int maxchar = 100;
	int minchar = 5;
	int maxlines = 20;
	int minlines = 1;
	int xmax =0;
	int ymax =0;
	ArrayList<ConnectedElement> elements;
	
	public CharCheck(BufferedImage bi, ArrayList<ConnectedElement> ces){
		xmax = bi.getWidth();
		ymax = bi.getHeight();
		elements = ces;
	}
	
	public ArrayList<ConnectedElement> getChars(){
		System.out.println("Total elements = " + elements.size());
		ArrayList<ConnectedElement> al = new ArrayList<ConnectedElement>();
		for(ConnectedElement ce : elements){
			if(charOK(ce))
				al.add(ce);
		}
		
		System.out.println("Char ok = " + al.size());		
		/*ArrayList<ConnectedElement> alsoOk = findConnected(al);
		System.out.println("Adjacent = " + alsoOk.size());
		al.addAll(alsoOk);*/
		System.out.println("Total ok = " + al.size());
		return al;
	}
	
	private ArrayList<ConnectedElement> findConnected(ArrayList<ConnectedElement> charOk){
		ArrayList<ConnectedElement> notCharButOk = new ArrayList<ConnectedElement>();
		ArrayList<ConnectedElement> notChar = new ArrayList<ConnectedElement>();
		notChar.addAll(elements);		
		notChar.removeAll(charOk);
		for(ConnectedElement cej : charOk){
			for(ConnectedElement cei : notChar){
				if(notCharButOk.contains(cei))
					continue;
				if(isAdjacent(cei, cej)){
					notCharButOk.add(cei);
					break;
				}
			}
		}
		
		return notCharButOk;
	}
	
	private boolean isAdjacent(ConnectedElement ci,ConnectedElement cj){
		double a = Math.pow(cj.getTopleft().getX() -ci.getTopleft().getX(),2);
		double b = Math.pow(cj.getTopleft().getY() -ci.getTopleft().getY(),2);		
		double r1 = Math.sqrt(a + b);
		
		double c = Math.pow(cj.getBottomright().getX() -ci.getBottomright().getX(),2);
		double d = Math.pow(cj.getBottomright().getY() -ci.getBottomright().getY(),2);
		double r2 = Math.sqrt(c +d);
		
		double e = 4 * (ci.getBottomright().getY() - ci.getTopleft().getY());
		
		if((r1 + r2) < e){
			return true;
		}
		return false;
		
	}
	
	private boolean charOK(ConnectedElement ce){
		boolean t1r = T1(ce);
		if(!t1r)
			return false;
		for(int j=0;j<elements.size();j++){
			if(elements.get(j) == ce)
				continue;
			if(T2(ce,elements.get(j))){
				if(T3(ce,elements.get(j))){
					if(T4(ce,elements.get(j))){
						return true;
					}
				}
			}
				
		}
		
		return false;
	}
	
	private  boolean T1(ConnectedElement ce){
		double maxX = (double) xmax / maxchar;
		double minX = (double) xmax / minchar;
		double maxY = (double) ymax / maxlines;
		double minY = (double) ymax / minlines;
		double width = ce.getBottomright().getX() - ce.getTopleft().getX();
		double height = ce.getBottomright().getY() - ce.getTopleft().getY();
		
		if(maxX < width && width < minX && maxY < height && height < minY)
			return true;
		else
			return false;
	}
	
	private  boolean T2(ConnectedElement ce1, ConnectedElement ce2){
		int c1 = Math.abs(ce2.getTopleft().getX() - ce1.getBottomright().getX());
		int c2 = ce1.getBottomright().getY() - ce1.getTopleft().getY();
		int c3 = Math.abs(ce2.getBottomright().getX() - ce1.getTopleft().getX());
		
		if( c1 < (2 * c2) || c3 < (2 * c2))
			return true;
		else
			return false;
	}
	
	private  boolean T3(ConnectedElement ce1, ConnectedElement ce2){
		int c1 = Math.abs(ce2.getTopleft().getY() - ce1.getTopleft().getY());
		int c2 = ce1.getBottomright().getY() - ce1.getTopleft().getY();
		if(c1 < c2)
			return true;
		else
			return false;
	}
	
	private  boolean T4(ConnectedElement ce1, ConnectedElement ce2){
		int c1 = ce1.getBottomright().getY() - ce1.getTopleft().getY();
		int c2 = ce2.getBottomright().getY() - ce2.getTopleft().getY();
		int de = Math.abs(c1 - c2);
		double res = (double)de / (double)c1;
		if(res < 0.3)
			return true;
		else
			return false;
		
	}

}
