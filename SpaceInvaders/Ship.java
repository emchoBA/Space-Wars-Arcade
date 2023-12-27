import java.awt.Image;
import java.net.URL;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

	public class Ship {
	private static boolean png = true;
	private Image icon;
	private int height, width;
	public int health;
	private int ship_x;
	private int ship_y;
	private int ship_speed;
	private Boolean moveRight;
	private Boolean moveLeft;
	private Boolean moveUp;
	private Boolean moveDown; 
	public Ship(int ship_x, int ship_y, int ship_speed) {
		timer.schedule(switchIcon, 0 ,200);
		this.ship_x = ship_x;
		this.ship_y = ship_y;
		this.ship_speed = ship_speed;
		this.moveRight = false;
		this.moveLeft = false;
		this.moveUp = false;
		this.moveDown = false;
		this.health = 3;
	}
	public void setShip_x(int x) {
		this.ship_x = x;
	}
	public int getShip_x() {
		return ship_x;
	}
	public void setShip_y(int y) {
		this.ship_y = y;
	}
	public int getShip_y() {
		return ship_y;
	}
	public int getShip_speed() {
		return ship_speed;
	}
	public int getShip_width() {
		return width;
	}
	public int getShip_height() {
		return height;
	}
	public void setMoveUp(Boolean k) {
		this.moveUp = k;
	}
	public Boolean getMoveUp() {
		return moveUp;
	}
	public void setMoveDown(Boolean k) {
		this.moveDown = k;
	}
	public Boolean getMoveDown() {
		return moveDown;
	}
	public void setMoveRight(Boolean k) {
		this.moveRight = k;
	}
	public Boolean getMoveRight() {
		return moveRight;
	}
	public void setMoveLeft(Boolean k) {
		this.moveLeft = k;
	}
	public Boolean getMoveLeft() {
		return moveLeft;
	}
	public void draw(Graphics window) {
		int playerX = getShip_x() - 43; 
		int playerY = getShip_y() - 28; 
		window.drawImage(icon, playerX, playerY,height,width,null);
	}

	Timer timer = new Timer();
	TimerTask switchIcon = new TimerTask() {
		public void run() {
			png = !png;
			if(png) {
				try {
					URL path = getClass().getResource("spaceship.png");
					icon = ImageIO.read(path);
					height = 86;
					width = 56;
				}
				catch(Exception e) {
					
				}
				
			}
			else {
				try {
					URL path = getClass().getResource("spaceship2.png");
					icon = ImageIO.read(path);
					height = 86;
					width = 71;
				}
				catch(Exception e) {
					
				}
			}
		}
	};
}
