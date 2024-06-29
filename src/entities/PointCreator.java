package entities;

public class PointCreator {
	
	private int x;
	private int y;
	
	public PointCreator(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	public int getX() {
		return x; 
	}
	
	public int getY() {
		return y; 
	}

	@Override
	public String toString() {
		return "Point(" + x + ", " + y + ")";
	}
	
}
