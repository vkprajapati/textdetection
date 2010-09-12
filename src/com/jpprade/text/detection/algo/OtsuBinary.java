package com.jpprade.text.detection.algo;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class OtsuBinary {
	public static BufferedImage averageGreyScale(BufferedImage bi){
		BufferedImage res = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
		int h = bi.getHeight();
		int w = bi.getWidth();
		double  N = h * w;
		int[] histogramme = new int[256];
		for(int i=0;i<255;i++)
			histogramme[i]=0;
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				int rgb = bi.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 0) & 0xFF;
				histogramme[b]++;
				
			}
		}
		
		/*for(int i=0;i<256;i++)
			System.out.println("histogramme["+i+"]="+histogramme[i]);*/
		
		double[] pi = new double[256];
		for(int i=0;i<255;i++){
			pi[i]=histogramme[i]/N;
			//System.out.println("pi["+i+"]="+pi[i]);
		}
		
		double[] omega = new double[256];
		double[] myu = new double[256];
		omega[0]=0;
		myu[0]=0;
		for(int i=1;i<256;i++){
			omega[i]=omega[i-1] + pi[i];
			myu[i]=myu[i-1] + i*pi[i];
			//System.out.println("myu["+i+"]="+myu[i]);
			//System.out.println("omega["+i+"]="+omega[i]);
		}
		int seuil = 0;
		double max_sigma=0;
		double sigma[] = new double[256];
		for(int i=0;i<255;i++){
			sigma[i] = Math.pow(myu[255] * omega[i] - myu[i], 2) / (omega[i] * (1-omega[i]));
			//System.out.println("sigma["+i+"]="+sigma[i]);
			if(sigma[i] > max_sigma){
				max_sigma=sigma[i];
				seuil=i;
			}
		}
		System.out.println("seuil="+seuil);
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				int rgb = bi.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 0) & 0xFF;
				
				int black =0;
				int white =0xFFFFFF;
				if(b < seuil)				
					res.setRGB(i, j, black);
				else
					res.setRGB(i, j, white);
			}
		}
		
		return res;
	}
	
	public static BufferedImage getOtsuBinary(BufferedImage bi){
		int width = bi.getWidth();
		int height = bi.getHeight();
		Raster raster = bi.getData();
		DataBuffer buffer = raster.getDataBuffer();
		DataBufferByte byteBuffer = (DataBufferByte) buffer;
		byte[] srcData = byteBuffer.getData(0);
		
		byte[] dstData = new byte[srcData.length];
		OtsuThresholder thresholder = new OtsuThresholder();
		int threshold = thresholder.doThreshold(srcData, dstData);
		
		DataBufferByte dataBuffer = new DataBufferByte(dstData, dstData.length, 0);

		PixelInterleavedSampleModel sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE, width, height, 1, width, new int[] {0});
		ColorSpace colourSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ComponentColorModel colourModel = new ComponentColorModel(colourSpace, new int[] {8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

		WritableRaster wraster = Raster.createWritableRaster(sampleModel, dataBuffer, null);

		BufferedImage image = new BufferedImage(colourModel, wraster, false, null);
		return image;
	}

}
