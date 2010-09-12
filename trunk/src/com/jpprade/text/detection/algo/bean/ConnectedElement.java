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
	

}
