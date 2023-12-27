import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
//import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class BasicEnemy extends Alien {
	int BasicEnemy_x;
	int BasicEnemy_y;
	int BasicEnemy_height;
	int BasicEnemy_width;
	int BasicEnemy_speed;
	int BasicEnemy_health;
	int iconHeight, iconWidth;
	Image icon;
	Boolean png = true;
	public BasicEnemy(int x, int y, int w, int h, int health, int speed) {
		super(x, y, w, h, health, speed);
		timer.schedule(switchIcon, 0, 200);
		this.BasicEnemy_x = x;
		this.BasicEnemy_y = y;
		this.BasicEnemy_height = h;
		this.BasicEnemy_width = w;
	}
	public int getWidth() {
		return BasicEnemy_width;
	}
	@Override
	public void draw(Graphics g) {
		
		g.drawImage(icon, getAlienX(), getAlienY(), 70, 74, null);
		
	}
	public void move() {
		
	}

	Timer timer = new Timer();
	TimerTask switchIcon = new TimerTask() {
		public void run() {
			png = !png;
			if(png) {
				try {
					URL path = getClass().getResource("alien1_1.png");
					icon = ImageIO.read(path);

				}
				catch(Exception e) {
					
				}
				
			}
			else {
				try {
					URL path = getClass().getResource("alien1_2.png");
					icon = ImageIO.read(path);

				}
				catch(Exception e) {
					
				}
			}
		}
	};
	
}
