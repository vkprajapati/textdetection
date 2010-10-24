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
	
	public static BufferedImage getOtsuBinaryV2(BufferedImage bi){
		System.out.println("bi type="+bi.getType());
		System.out.println("type="+BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage res = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());		
		Raster raster = bi.getData();
		DataBuffer buffer = raster.getDataBuffer();
		System.out.println("buffer class="+buffer.getClass().getName());
		DataBufferByte byteBuffer = (DataBufferByte) buffer;
		
		byte b[] = byteBuffer.getData(0);
		System.out.println("b size="+b.length);
		
		int[] histogramme = new int[255];
		for(int i=0;i<255;i++)
			histogramme[i]=0;
		for(int j=0;j<b.length;j=j+4){			
			//System.out.println("b("+"0"+")["+j+"]="+b[j]);
			//System.out.println("b("+"0"+")["+j+"]="+ (b[j] & 0xFF));
			int blue = (b[j+1] & 0xFF);
			int green = (b[j+2] & 0xFF);
			int red = (b[j+3] & 0xFF);			
			int gray = ( red +green + blue) / 3;
			histogramme[gray]++;
			
		}
		int all = getAll(histogramme);
		System.out.println("all="+all);
		int threshold=0;
		double maxVB=0;
		for(int i=0;i<histogramme.length;i++){
			double wb=getWeightBackground(histogramme, i, all);
			double wf= getWeightForeground(histogramme, i, all);
			double ub=getMeanBackground(histogramme, i);
			double uf=getMeanForeground(histogramme, i);
			if(ub == 0 || uf ==0 )
				continue;
			double udiff = (ub-uf);
			double vb = wb * wf * udiff * udiff ;
			if(vb > maxVB){
				maxVB=vb;
				threshold=i;
			}
		}
		System.out.println("seuil="+threshold);
		
		
		
		return res;
	}
	
	protected static int getAll(int[] hist){
		int total=0;
		for(int i =0;i < hist.length;i++ ){
			total += hist[i];
		}
		return total;
	}
	
	protected static double getWeightBackground(int[] hist,int threshold,int all){
		int total =0;
		for(int i =0;i < threshold;i++ ){
			total += hist[i];
		}
		return total / all;		
	}
	
	protected static double getWeightForeground(int[] hist,int threshold, int all){
		int total =0;
		for(int i =threshold;i < hist.length;i++ ){
			total += hist[i];
		}
		return total / all;		
	}
	
	protected static double getMeanBackground(int[] hist, int threshold){
		int mean = 0;
		int all = 0;
		for(int i =0;i < threshold;i++ ){
			mean += i * hist[i];
			all += hist[i];
		}
		if(all == 0)
			return 0;
		return mean / all;	
	}
	
	protected static double getMeanForeground(int[] hist, int threshold){
		int mean = 0;
		int all = 0;
		for(int i = threshold;i <hist.length;i++ ){
			mean += i * hist[i];
			all += hist[i];
		}
		if(all == 0)
			return 0;
		return mean / all;	
	}

}
