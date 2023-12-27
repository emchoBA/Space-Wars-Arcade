import java.awt.Graphics;

public abstract class Alien {
	int health;
	int Alien_x;
	int Alien_y;
	int Alien_width;
	int Alien_height;
	int Alien_speed;
	boolean isDead;
	public int score;
	public Alien(int x, int y, int width, int height, int health, int speed) {
		this.Alien_x = x;
		this.Alien_y = y;
		this.Alien_width = width;
		this.Alien_height = height;
		this.health = health;
		this.Alien_speed = speed;	
		this.score = (speed*5) + (health*5);
	}
	public void setAlienX(int x) {
		this.Alien_x = x;
	}
	public void setAlienY(int y) {
		this.Alien_y = y;
	}
	public void setAlienWidth(int w) {
		this.Alien_width = w;
	}
	public void setAlienHeight(int h) {
		this.Alien_height = h;
	}
	public int getAlienX() {
		return Alien_x;
	}
	public int getAlienY() {
		return Alien_y;
	}
	public int getAlienWidth() {
		return Alien_width;
	}
	public int getAlienHeight() {
		return Alien_height;
	}
	public int getScore() {
		return score;
	}
	public void getDamaged() {
		health -= 1;
	}
	public abstract void draw(Graphics g);
	public void move( int borderX, int borderY){

		if(Alien_y <= 0) {
			Alien_y += 1;
		}
		if(Alien_x >= borderX-100 || Alien_x <= 0) {
			Alien_speed *= -1;
			Alien_y += 40;
		}

		Alien_x += Alien_speed;
		
		if(Alien_y > 600) {
			isDead = true;
		}
	}
}
