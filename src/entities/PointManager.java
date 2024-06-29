package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import main.App;

public class PointManager {
	
	private Random rand = new Random();
	private App app;
	
	private static ArrayList<PointCreator> points = new ArrayList<>();
	 
	private int totalPoints = 100;
	
	public PointManager() {
		
	}     
	 
	public PointManager(App app) {
		this.app = app;
				
		addPoint();
	} 
	
	public void renderPoints(Graphics2D g2d) {
		
		for(PointCreator point : points) {
			g2d.setColor(Color.green);   
			g2d.fill(new Ellipse2D.Double(point.getX() - 2.5, point.getY() - 2.5, 5, 5));
		}
		
	}	
	
	public int getCurrentX(int i) {
		return points.get(i).getX();
	}
	
	public int getCurrentY(int i) { 
		return points.get(i).getY();
	}
	
	public ArrayList<PointCreator> getPoints() {
		return points;
	}
	
	public int getSize() {
		return points.size();
	}
		
	private void addPoint() {
		
		for(int i = 0; i < totalPoints; i++) {
			int randXCoor = xCoor();
			int randYCoor = yCoor();
			
			PointCreator point = new PointCreator(randXCoor, randYCoor);
			points.add(point);
		}
		
	}
	
	private int xCoor() {	
		return rand.nextInt(App.APP_WIDTH);		
	}
	
	private int yCoor() {
		return rand.nextInt(App.APP_HEIGHT);
	}
	
}
