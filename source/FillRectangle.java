package com.yordanyordanov.kursova;

import java.awt.Color;
import java.awt.Graphics;
import java.text.MessageFormat;

public class FillRectangle extends TwoPointFigure implements GPrimitive{
	FillRectangle(){}
	
	FillRectangle(int x1, int y1, int x2, int y2, Color c){
		super(x1, y1, x2, y2, c);
	}
	
	@Override
	public boolean isInsideDrawArea(){
		//first point is visible
		return (x1 < MiniPaint.currWidth && y1 < MiniPaint.currHeight - MiniPaint.footerHeight) || 
			   (x2 < MiniPaint.currWidth && y2 < MiniPaint.currHeight - MiniPaint.footerHeight);
	}

	@Override
	public void drawPrimitive(Graphics g) {
		Color prevCol = g.getColor();
		
		g.setColor(col);
		g.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
		g.setColor(prevCol);
	}
	
	@Override
	public FillRectangle Copy() {
		FillRectangle r = new FillRectangle(x1, y1, x2, y2, col);
		return r;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("FillRectangle(x1={0}, y1={1}, x2={2}, y2={3}, Colour=[{4},{5},{6}])", 
				                             x1, y1, x2, y2, col.getRed(), col.getGreen(), col.getBlue());
	}
}
