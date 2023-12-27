
public class Laser {
	int width;
	int height;
	int laser_x;
	int laser_y;
	public Laser(int x, int y, int h, int w) { //should be 47x12
		this.laser_x = x;
		this.laser_y = y;
		this.height = h;
		this.width = w;
	}
	public int getLaserY() {
		return laser_y;
	}
	public void setLaserY(int k) {
		this.laser_y = k;
	}
	public boolean intersects(int x, int y, int h, int w) {
		int thisLeft = this.laser_x;
		int thisRight = this.laser_x + this.width;
		int thisTop = this.laser_y;
		int thisBottom = this.laser_y + this.height;
		
		int otherLeft = x;
		int otherRight = x + w;
		int otherTop = y;
		int otherBottom = y + h;
		
		boolean intersectsX = thisLeft < otherRight && thisRight > otherLeft;
		boolean intersectsY = thisTop < otherBottom && thisBottom > otherTop;
		
		return intersectsX && intersectsY;
	}
}
