package com.yordanyordanov.kursova;

import java.awt.Color;
import java.awt.Graphics;
import java.text.MessageFormat;

public class TwoPointFigure implements GPrimitive {
	int x1 = 0;
	int y1 = 0;
	int x2 = 0;
	int y2 = 0;
	Color col = Color.black;
	
	TwoPointFigure(){}
	
	TwoPointFigure(int x1, int y1, int x2, int y2, Color c){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		col = c;
	}
	
	public boolean isInsideDrawArea() {
		return false;
	}
	
	@Override
	public void drawPrimitive(Graphics g) {}
	
	public TwoPointFigure Copy() {
		TwoPointFigure f = new TwoPointFigure(x1, y1, x2, y2, col);
		return f;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("TwoPointFigure(x1={0}, y1={1}, x2={2}, y2={3}, Colour=[{4},{5},{6}])", 
				                             x1, y1, x2, y2, col.getRed(), col.getGreen(), col.getBlue());
	}
}
