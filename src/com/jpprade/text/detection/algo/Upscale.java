package com.jpprade.text.detection.algo;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Upscale {
	
	public static BufferedImage upscale(BufferedImage bi, int w, int h){
		BufferedImage bicubic = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D bg = bicubic.createGraphics();
	    bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    
	    bg.scale(2, 2);
	    bg.drawImage(bi, 0, 0, null);
	    bg.dispose(); 
	    
		return bicubic;
	}
	
	public static BufferedImage upscaleDouble(BufferedImage bi,double ratio){
		BufferedImage bicubic = new BufferedImage( (int)(ratio * bi.getWidth()), (int)(ratio * bi.getHeight()), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D bg = bicubic.createGraphics();
	    bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    
	    bg.scale(2, 2);
	    bg.drawImage(bi, 0, 0, null);
	    bg.dispose(); 
	    
		return bicubic;
	}

}
