package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class AppPanel extends JPanel {

	private App app;
	private Dimension size;
	
	public AppPanel(App app) {
		this.app = app;
		
		setPanelSize();
		this.setBackground(Color.DARK_GRAY);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		app.render(g2d);
	}
	
	public App getApp() {
		return app;
	}
	
	private void setPanelSize() {
		size = new Dimension(App.APP_WIDTH, App.APP_HEIGHT);
		setMaximumSize(size);
		setMinimumSize(size);
		setPreferredSize(size);
	}
	
	
}
