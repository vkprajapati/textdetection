package com.jpprade.text.detection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jpprade.text.detection.algo.OtsuBinary;

public class TestNoUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//File file = new File("/home/jpprade/Images/test/test44.png");
		File file = new File("/home/jpprade/Images/test/ada.jpg");
		try {
			BufferedImage image = ImageIO.read(file);
			BufferedImage res1=OtsuBinary.averageGreyScale(image);
			BufferedImage res2=OtsuBinary.getOtsuBinaryV2(image);
			
			File filer = new File("/home/jpprade/Images/test/resultv1.png");
			File filer2 = new File("/home/jpprade/Images/test/resultv2.png");
			
			ImageIO.write(res1, "png", filer);
			ImageIO.write(res2, "png", filer2);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
