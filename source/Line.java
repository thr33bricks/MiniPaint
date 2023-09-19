package com.yordanyordanov.kursova;

import java.awt.Color;
import java.awt.Graphics;
import java.text.MessageFormat;

public class Line extends TwoPointFigure implements GPrimitive{
	final double error = 0.3;
	
	Line(){}
	
	Line(int x1, int y1, int x2, int y2, Color c){
		super(x1, y1, x2, y2, c);
	}
	
	@Override
	public boolean isInsideDrawArea(){
		//first point is visible or second point is visible or 
		//bottom right point of draw area is on the right of the line
		return (x1 < MiniPaint.currWidth && y1 < MiniPaint.currHeight - MiniPaint.footerHeight) || 
		       (x2 < MiniPaint.currWidth && y2 < MiniPaint.currHeight - MiniPaint.footerHeight) ||
		       isInsideTriangle(x1, y1, x2, y2, MiniPaint.currWidth, MiniPaint.currHeight - MiniPaint.footerHeight);
	}

	@Override
	public void drawPrimitive(Graphics g) {
		Color prevCol = g.getColor();
		
		g.setColor(col);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(prevCol);
	}
	
	@Override
	public Line Copy() {
		Line l = new Line(x1, y1, x2, y2, col);
		return l;
	}
	
	private double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3){
		return Math.abs((x1*((double)y2-y3) + x2*((double)y3-y1)+ x3*((double)y1-y2))/2.0);
	}
	
	// Triangle defined by two points and third is max x and max y of the provided
	boolean isInsideTriangle(int x1, int y1, int x2, int y2, int x, int y){
		int x3 = Math.max(x1, x2);
		int y3 = Math.max(y1, y2);
		
		double A = triangleArea(x1, y1, x2, y2, x3, y3);
	
		double A1 = triangleArea(x, y, x2, y2, x3, y3);
		double A2 = triangleArea(x1, y1, x, y, x3, y3);
		double A3 = triangleArea(x1, y1, x2, y2, x, y);
	    
		
		//System.out.println("A= " + A);
		//System.out.println("res= " + (A - (A1 + A2 + A3)));
		return (Math.abs(A - (A1 + A2 + A3)) < error);
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("Line(x1={0}, y1={1}, x2={2}, y2={3}, Colour=[{4},{5},{6}])", 
				                          x1, y1, x2, y2, col.getRed(), col.getGreen(), col.getBlue());
	}
}
