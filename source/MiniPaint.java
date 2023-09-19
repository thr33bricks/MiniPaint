package com.yordanyordanov.kursova;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;

public class MiniPaint extends Frame {
	final static int initialWindowWidth = 640;
	final static int initialWindowHeight = 480;
	final static int footerHeight = 30;
	
	int currXPos = 0;
	int currYPos = 0;
	
	static int currWidth = initialWindowWidth;
	static int currHeight = initialWindowHeight;
	
	boolean drawing = false;
	boolean mouseDown = false;
	
	Color currColour = Color.black;
	TwoPointFigure currFigure = new Line();
	
	List<TwoPointFigure> figures = new ArrayList<TwoPointFigure>();
	
	MiniPaint(){
		setTitle ("Mini Paint");
		setSize (initialWindowWidth, initialWindowHeight);
		setResizable(true);
		
		addListeners();
		createControls();
		
		setVisible(true);
	}
	
	private void addListeners(){
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
		
		addComponentListener(new WindowResizeHandler());
		
		// Close button handler
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	private void createControls(){
		MenuItemListener menuItemListener = new MenuItemListener();
		MenuBar mb = new MenuBar();
		
		//Menus
		Menu fileMenu = new Menu("File");
		Menu colourMenu = new Menu("Colour");
		Menu figureMenu = new Menu("Figure");
		
		//File menu
        MenuItem fileItem1 = new MenuItem("Open");
        MenuItem fileItem2 = new MenuItem("New");
        MenuItem fileItem3 = new MenuItem("Save");
        MenuItem fileItem4 = new MenuItem("Print figures");
        MenuItem fileItem5 = new MenuItem("Exit");
        fileItem1.addActionListener(menuItemListener);
        fileItem2.addActionListener(menuItemListener);
        fileItem3.addActionListener(menuItemListener);
        fileItem4.addActionListener(menuItemListener);
        fileItem5.addActionListener(menuItemListener);
        fileItem1.setEnabled(false);
        fileItem2.setEnabled(false);
        fileItem3.setEnabled(false);
        fileMenu.add(fileItem1);
        fileMenu.add(fileItem2);
        fileMenu.add(fileItem3);
        fileMenu.add(fileItem4);
        fileMenu.add(fileItem5);
        
        //Colour menu
        MenuItem colItem1 = new MenuItem("Black");
        MenuItem colItem2 = new MenuItem("White");
        MenuItem colItem3 = new MenuItem("Red");
        MenuItem colItem4 = new MenuItem("Green");
        MenuItem colItem5 = new MenuItem("Blue");
        MenuItem colItem6 = new MenuItem("Pick a colour");
        colItem1.addActionListener(menuItemListener);
        colItem2.addActionListener(menuItemListener);
        colItem3.addActionListener(menuItemListener);
        colItem4.addActionListener(menuItemListener);
        colItem5.addActionListener(menuItemListener);
        colItem6.addActionListener(menuItemListener);
        colourMenu.add(colItem1);
        colourMenu.add(colItem2);
        colourMenu.add(colItem3);
        colourMenu.add(colItem4);
        colourMenu.add(colItem5);
        colourMenu.add(colItem6);
        
        //Figure menu
        MenuItem figItem1 = new MenuItem("Line");
        MenuItem figItem2 = new MenuItem("Rectangle");
        MenuItem figItem3 = new MenuItem("Filled rectangle");
        figItem1.addActionListener(menuItemListener);
        figItem2.addActionListener(menuItemListener);
        figItem3.addActionListener(menuItemListener);
        figureMenu.add(figItem1);
        figureMenu.add(figItem2);
        figureMenu.add(figItem3);
        
        mb.add(fileMenu);
        mb.add(colourMenu);
        mb.add(figureMenu);
        setMenuBar(mb);
	}
	
	@Override
	public void paint(Graphics g) {
		drawFigures(g);
		if(drawing) {
			currFigure.drawPrimitive(g);
		}
		
		// clear footer
		g.setColor(getBackground());
		g.fillRect(0, currHeight - footerHeight, currWidth, footerHeight);
		
		// draw footer
		// footer line
		g.setColor(Color.black);
		g.drawLine(0, currHeight - footerHeight, currWidth, currHeight - footerHeight);
		// footer selected colour
		g.setColor(currColour);
		g.fillRect(7, currHeight - footerHeight + 7, 18, 18);
		// footer selected tool
		g.setColor(Color.black);
		g.drawRect(39, currHeight - footerHeight + 7, 18, 18);
		drawTool(g);
		// footer mouse position
		g.drawString(MessageFormat.format("X: {0}, Y: {1}", currXPos, currYPos), currWidth - 100, currHeight - 10);
		
	}
	
	class WindowResizeHandler extends ComponentAdapter{
		@Override
		public void componentResized(ComponentEvent componentEvent) {
			currHeight = getSize().height;
			currWidth = getSize().width;
	        repaint();
	    }
	}
	
	class MouseHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
			if(currYPos > getSize().height - footerHeight)
				return;
			
			drawing = true;
			currFigure.x1 = currXPos;
			currFigure.y1 = currYPos;
			currFigure.col = currColour;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(drawing)
				addCurrFigure();
			drawing = false;
		}
	}
	
	class MouseMotionHandler implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			getMouseCoordinates(e);
			
			currFigure.x2 = currXPos;
			currFigure.y2 = currYPos;
			
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			getMouseCoordinates(e);
			repaint();
		}
		
		private void getMouseCoordinates(MouseEvent e) {
			currXPos = (int)e.getX();
			currYPos = (int)e.getY();
		}
	}
	
	class MenuItemListener implements ActionListener {
		@Override
	    public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "Print figures":
					printFigures();
					break;
				case "Exit":
					System.exit(0);
					break;
				case "Black":
					currColour = Color.black;
					break;
				case "White":
					currColour = Color.white;
					break;
				case "Red":
					currColour = Color.red;
					break;
				case "Green":
					currColour = Color.green;
					break;
				case "Blue":
					currColour = Color.blue;
					break;
				case "Pick a colour":
					Color selCol = JColorChooser.showDialog(null,"Select a color", Color.white);
					if(selCol != null)
						currColour = selCol;
					break;
				case "Line":
					currFigure = new Line();
					break;
				case "Rectangle":
					currFigure = new Rectangle();
					break;
				case "Filled rectangle":
					currFigure = new FillRectangle();
					break;
				default:
					break;
			}
	    }
	}
	
	private void addCurrFigure() {
		figures.add(currFigure.Copy());
	}
	
	private void drawFigures(Graphics g) {
		for(TwoPointFigure f: figures) {
			if(f.isInsideDrawArea()) {
				f.drawPrimitive(g);
			}
		}
	}
	
	private void printFigures() {
		int i = 0;
		for(TwoPointFigure f: figures) {
			i++;
			System.out.println("" + i + " " + f.toString());
		}
		System.out.println();
	}
	
	private void drawTool(Graphics g) {
		//g.drawRect(39, currHeight - footerHeight + 7, 18, 18);
		if(currFigure instanceof Line)
			g.drawLine(43, currHeight - footerHeight + 11, 53, currHeight - footerHeight + 21);
		else if(currFigure instanceof Rectangle) {
			g.drawRect(43, currHeight - footerHeight + 11, 10, 10);
		}
		else if(currFigure instanceof FillRectangle) {
			g.fillRect(43, currHeight - footerHeight + 11, 11, 11);
		}
	}
	
	public static void main (String [] args) {
		new MiniPaint();
	}
}
