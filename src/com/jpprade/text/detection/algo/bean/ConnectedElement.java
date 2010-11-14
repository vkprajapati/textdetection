package com.jpprade.text.detection.algo.bean;

import java.util.ArrayList;


public class ConnectedElement {
	
	private Point topleft;	
	private Point bottomright;
	
	private ArrayList<Point> points;
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	public Point getTopleft() {
		return topleft;
	}
	public void setTopleft(Point topleft) {
		this.topleft = topleft;
	}
	public Point getBottomright() {
		return bottomright;
	}
	public void setBottomright(Point bottomright) {
		this.bottomright = bottomright;
	}
	
	public boolean isRight(ConnectedElement ce){
		if(ce.getBottomright().getX() <= topleft.getX()){
			return true;
		}else
			return false;
	}
	
	public boolean isLeft(ConnectedElement ce){
		if(ce.getTopleft().getX() >= bottomright.getX()){
			return true;
		}else
			return false;
	}
	
	public boolean isOverlaping(ConnectedElement ce){
		if((topleft.getY() <= ce.getBottomright().getY()) && (topleft.getY() >= ce.getTopleft().getY()) ){
			return true;
		}else if((bottomright.getY() <= ce.getBottomright().getY()) && (bottomright.getY() >= ce.getTopleft().getY()) ){
			return true;
		}else if((bottomright.getY() >= ce.getBottomright().getY()) && (topleft.getY() <= ce.getTopleft().getY()) ){
			return true;
		}else if((bottomright.getY() <= ce.getBottomright().getY()) && (topleft.getY() >= ce.getTopleft().getY()) ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isRealRatio(){
		int w = bottomright.getX() - topleft.getX();
		int l = bottomright.getY() - topleft.getY();
		double ratio = (double) l / w;
		System.out.println("ratio" + ratio);
		if(ratio < 0.4 || ratio > 1.6){
			return false;
		}else{
			return true;
		}
		
	}
	
	public boolean contains(ConnectedElement ce){
		if(topleft.getX() <= ce.getTopleft().getX() 
				&& topleft.getY() <= ce.getTopleft().getY()
				&& bottomright.getX() >= ce.getBottomright().getX()
				&& bottomright.getY() >= ce.getBottomright().getY()){
			return true;
		}
		return false;
	}
	
	public boolean isContained(ConnectedElement ce){
		if(topleft.getX() >= ce.getTopleft().getX() 
				&& topleft.getY() >= ce.getTopleft().getY()
				&& bottomright.getX() <= ce.getBottomright().getX()
				&& bottomright.getY() <= ce.getBottomright().getY()){
			return true;
		}
		return false;
	}
	

}
