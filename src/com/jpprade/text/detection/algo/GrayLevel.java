package com.jpprade.text.detection.algo;

import java.awt.image.BufferedImage;

public class GrayLevel {
	
	public static BufferedImage averageGreyScale(BufferedImage bi){
		BufferedImage res = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
		int h = bi.getHeight();
		int w = bi.getWidth();
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				int rgb = bi.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 0) & 0xFF;
				int gray = ( r +g + b) / 3;
				int rs = (gray << 16) | (gray << 8) | gray;
				res.setRGB(i, j, rs);
			}
		}
		return res;
	}
	
	public static BufferedImage invert(BufferedImage bi){
		BufferedImage res = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
		int h = bi.getHeight();
		int w = bi.getWidth();
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				int rgb = bi.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 0) & 0xFF;
				r = 255 -r;
				g = 255 - g;
				b = 255 - b;
				int rs = (r << 16) | (g << 8) | b;
				res.setRGB(i, j, rs);
				
			}
		}
		return res;
	}

}
