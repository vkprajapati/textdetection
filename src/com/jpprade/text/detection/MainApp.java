package com.jpprade.text.detection;
import com.jpprade.text.detection.algo.CharCheck;
import com.jpprade.text.detection.algo.ConnectedDetection;
import com.jpprade.text.detection.algo.GrayLevel;
import com.jpprade.text.detection.algo.OtsuBinary;
import com.jpprade.text.detection.algo.Upscale;
import com.jpprade.text.detection.algo.bean.ConnectedElement;
import com.jpprade.text.detection.panel.ImagePanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainApp extends javax.swing.JFrame {

	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JMenuItem connectedMenuItem;
	private AbstractAction binariseAction;
	private AbstractAction grayScaleAction;
	private JMenuItem binarise;
	private JMenuItem grayScale;
	private AbstractAction invertAction;
	private JMenuItem invertMenuItem;
	private AbstractAction ostuBinaryAction;
	private JMenuItem otsubinary;
	private AbstractAction findConnectedAction;
	private JMenu menuFilter;
	private ImagePanel imagePanel;
	private AbstractAction openFile;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private AbstractAction scaleAction;
	private JMenuItem scaleMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainApp inst = new MainApp();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainApp() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().add(getImagePanel(), BorderLayout.CENTER);
			this.setSize(600, 500);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText("File");
					{
						newFileMenuItem = new JMenuItem();
						jMenu3.add(newFileMenuItem);
						newFileMenuItem.setText("New");
					}
					{
						openFileMenuItem = new JMenuItem();
						jMenu3.add(openFileMenuItem);
						openFileMenuItem.setText("Open");
						openFileMenuItem.setAction(getOpenFile());
					}
					{
						saveMenuItem = new JMenuItem();
						jMenu3.add(saveMenuItem);
						saveMenuItem.setText("Save");
					}
					{
						saveAsMenuItem = new JMenuItem();
						jMenu3.add(saveAsMenuItem);
						saveAsMenuItem.setText("Save As ...");
					}
					{
						closeFileMenuItem = new JMenuItem();
						jMenu3.add(closeFileMenuItem);
						closeFileMenuItem.setText("Close");
					}
					{
						jSeparator2 = new JSeparator();
						jMenu3.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						jMenu3.add(exitMenuItem);
						exitMenuItem.setText("Exit");
					}
				}
				{
					jMenu4 = new JMenu();
					jMenuBar1.add(jMenu4);
					jMenu4.setText("Edit");
					{
						cutMenuItem = new JMenuItem();
						jMenu4.add(cutMenuItem);
						cutMenuItem.setText("Cut");
					}
					{
						copyMenuItem = new JMenuItem();
						jMenu4.add(copyMenuItem);
						copyMenuItem.setText("Copy");
					}
					{
						pasteMenuItem = new JMenuItem();
						jMenu4.add(pasteMenuItem);
						pasteMenuItem.setText("Paste");
					}
					{
						jSeparator1 = new JSeparator();
						jMenu4.add(jSeparator1);
					}
					{
						deleteMenuItem = new JMenuItem();
						jMenu4.add(deleteMenuItem);
						deleteMenuItem.setText("Delete");
					}
				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenuBar1.add(getMenuFilter());
					jMenu5.setText("Help");
					{
						helpMenuItem = new JMenuItem();
						jMenu5.add(helpMenuItem);
						helpMenuItem.setText("Help");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AbstractAction getOpenFile() {
		if(openFile == null) {
			openFile = new AbstractAction("Open Image...", null) {
				public void actionPerformed(ActionEvent evt) {
					JFileChooser fc = new JFileChooser();
					Object  s= evt.getSource();
					int returnVal = fc.showOpenDialog((Component)s);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	File file = fc.getSelectedFile();
			        	System.out.println(file.getAbsolutePath());
			        	try {
							BufferedImage image = ImageIO.read(file);
							ImagePanel ip = getImagePanel();
							ip.setImage(image);
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }

				}
			};
		}
		return openFile;
	}
	
	public ImagePanel getImagePanel() {
		if(imagePanel == null) {
			imagePanel = new ImagePanel();
			FlowLayout imagePanelLayout = new FlowLayout();
			imagePanel.setLayout(imagePanelLayout);
		}
		return imagePanel;
	}
	
	public JMenu getMenuFilter() {
		if(menuFilter == null) {
			menuFilter = new JMenu();
			menuFilter.setText("Filter");
			menuFilter.add(getGrayScale());
			menuFilter.add(getInvertMenuItem());
			menuFilter.add(getBinarise());
			menuFilter.add(getConnectedMenuItem());
			menuFilter.add(getOtsubinary());
			menuFilter.add(getScaleMenuItem());
		}
		return menuFilter;
	}
	
	private JMenuItem getGrayScale() {
		if(grayScale == null) {
			grayScale = new JMenuItem();
			grayScale.setText("Gray level");
			grayScale.setAction(getGrayScaleAction());
		}
		return grayScale;
	}
	
	public JMenuItem getBinarise() {
		if(binarise == null) {
			binarise = new JMenuItem();
			binarise.setText("Binarise");
			binarise.setAction(getBinariseAction());
		}
		return binarise;
	}
	
	public AbstractAction getGrayScaleAction() {
		if(grayScaleAction == null) {
			grayScaleAction = new AbstractAction("gray scale", null) {
				public void actionPerformed(ActionEvent evt) {
					BufferedImage bf = GrayLevel.averageGreyScale(getImagePanel().getImage());
					getImagePanel().setImage(bf);
				}
			};
		}
		return grayScaleAction;
	}
	
	public AbstractAction getBinariseAction() {
		if(binariseAction == null) {
			binariseAction = new AbstractAction("binarise", null) {
				public void actionPerformed(ActionEvent evt) {
					//BufferedImage bf = GrayLevel.averageGreyScale(getImagePanel().getImage());
					BufferedImage bf = OtsuBinary.averageGreyScale(getImagePanel().getImage());
					getImagePanel().setImage(bf);
				}
			};
		}
		return binariseAction;
	}
	
	public JMenuItem getConnectedMenuItem() {
		if(connectedMenuItem == null) {
			connectedMenuItem = new JMenuItem();
			connectedMenuItem.setText("Find connected");
			connectedMenuItem.setAction(getFindConnectedAction());
		}
		return connectedMenuItem;
	}
	
	public AbstractAction getFindConnectedAction() {
		if(findConnectedAction == null) {
			findConnectedAction = new AbstractAction("Find connected", null) {
				public void actionPerformed(ActionEvent evt) {
					ConnectedDetection cd = new ConnectedDetection();
					ArrayList<ConnectedElement> elements = cd.getConnectedElement(getImagePanel().getImage());
					System.out.println("nb connected = "+ elements.size());
					CharCheck cc = new CharCheck(getImagePanel().getImage(), elements);
					ArrayList<ConnectedElement> elements2 = cc.getChars();
					System.out.println("nb connected 2 = "+ elements2.size());
					
					ArrayList<ConnectedElement> lines = ConnectedDetection.findLines(elements);
					
					System.out.println("nb lines = "+ lines.size());
					
					//getImagePanel().setConnectedElements(elements);
					BufferedImage bi1 = ConnectedDetection.getImage(elements,getImagePanel().getImage().getWidth(),getImagePanel().getImage().getHeight());
					
					//getImagePanel().setConnectedElements(elements2);
					//BufferedImage bi1 = ConnectedDetection.getImage(elements2,getImagePanel().getImage().getWidth(),getImagePanel().getImage().getHeight());
					
					//lines.addAll(elements);
					getImagePanel().setConnectedElements(lines);
					
					getImagePanel().setImage(bi1);
				}
			};
		}
		return findConnectedAction;
	}
	
	public JMenuItem getOtsubinary() {
		if(otsubinary == null) {
			otsubinary = new JMenuItem();
			otsubinary.setText("Otsu binarisation");
			otsubinary.setAction(getOstuBinaryAction());
		}
		return otsubinary;
	}
	
	public AbstractAction getOstuBinaryAction() {
		if(ostuBinaryAction == null) {
			ostuBinaryAction = new AbstractAction("otsuBinaryAction", null) {
				public void actionPerformed(ActionEvent evt) {
					BufferedImage bf = OtsuBinary.getOtsuBinary(getImagePanel().getImage());
					getImagePanel().setImage(bf);
				}
			};
		}
		return ostuBinaryAction;
	}
	
	public JMenuItem getInvertMenuItem() {
		if(invertMenuItem == null) {
			invertMenuItem = new JMenuItem();
			invertMenuItem.setText("Invert");
			invertMenuItem.setAction(getInvertAction());
		}
		return invertMenuItem;
	}
	
	public AbstractAction getInvertAction() {
		if(invertAction == null) {
			invertAction = new AbstractAction("Invert", null) {
				public void actionPerformed(ActionEvent evt) {
					BufferedImage bi = GrayLevel.invert(getImagePanel().getImage());
					getImagePanel().setImage(bi);
				}
			};
		}
		return invertAction;
	}
	
	public JMenuItem getScaleMenuItem() {
		if(scaleMenuItem == null) {
			scaleMenuItem = new JMenuItem();
			scaleMenuItem.setText("Scale 2x");
			scaleMenuItem.setAction(getScaleAction());
		}
		return scaleMenuItem;
	}
	
	public AbstractAction getScaleAction() {
		if(scaleAction == null) {
			scaleAction = new AbstractAction("Scale 2x", null) {
				public void actionPerformed(ActionEvent evt) {
					BufferedImage bi = Upscale.upscaleDouble(getImagePanel().getImage(), 2.0);
					getImagePanel().setImage(bi);
				}
			};
		}
		return scaleAction;
	}

}
