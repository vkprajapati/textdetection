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
		File file = new File("/home/jpprade/Images/test/test41.png");
		try {
			BufferedImage image = ImageIO.read(file);
			OtsuBinary.averageGreyScale(image);
			OtsuBinary.getOtsuBinaryV2(image);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
