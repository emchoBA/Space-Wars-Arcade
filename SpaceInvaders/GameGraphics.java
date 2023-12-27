import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameGraphics extends JPanel implements Runnable ,MouseListener, MouseMotionListener{
	Ship ship;
//	Game game;
//	String name = game.getName();
	BasicEnemy basicEnemy;
	private boolean running;
	Boolean mouseUp, mouseDown, mouseRight, mouseLeft;
	Boolean mousePressed, isDragging;
	String filePath = "/Users/emcho/Desktop/eclipse workspace/SpaceInvaders/scoreboard.txt";

	private List<Laser> bullets;
	private static List<Alien> enemies;
	private List<Laser> alienBullets;
	int screen_X = 1200;
	int screen_Y = 800;
	int Level;
	int Score;
	Clip shootSound;
	Clip alienDeath;
	private JLabel scoreboard;
	private JLabel health;
	private JLabel level;
	private JLabel gameOver;
	public int boot;
	public boolean gameRunning;
	public GameGraphics() {
		ship = new Ship(250,500,10);
		running = true;
		setPreferredSize(new Dimension(screen_X, screen_Y));
		setFocusable(true);

		addMouseListener(this);
		addMouseMotionListener(this);

		setLayout(null);
		
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameGraphics.class.getResourceAsStream("shoot.wav"));
	        shootSound = AudioSystem.getClip();
	        shootSound.open(audioInputStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameGraphics.class.getResourceAsStream("invaderkilled.wav"));
	        alienDeath = AudioSystem.getClip();
	        alienDeath.open(audioInputStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		bullets = new ArrayList<>();
		enemies = new ArrayList<>();
		alienBullets = new ArrayList<>();
		Score = 0;
		gameRunning = false;
		scoreboard = new JLabel("SCORE: " + Score);
		scoreboard.setBounds(10, 10, 100, 20);
		scoreboard.setForeground(Color.RED);
		
		health = new JLabel("Health:" + ship.health);
		health.setBounds(110, 10, 100, 20);
		health.setForeground(Color.RED);
		
		level = new JLabel("LEVEL: " + Level);
		level.setBounds(210,10,100,20);
		level.setForeground(Color.RED);
		
		gameOver = new JLabel("GAME OVER");
		gameOver.setBounds(400,300,400,200);
		gameOver.setForeground(Color.ORANGE);
		Font font = gameOver.getFont();
		Font newFont = font.deriveFont(font.getSize() + 50f);
		gameOver.setFont(newFont);
		bindKeys();
		Thread starter = new Thread(this);
		starter.start();
			add(scoreboard);
			add(health);
			add(level);


			setBackground(Color.BLACK);
			

			alienLaser.start();

			timer.schedule(addBasicEnemy, 0, 2000);

			alienSlider.start();
		
	}
	public GameGraphics(Dimension dimension) {
		setSize(dimension);
		setPreferredSize(dimension);
		addMouseListener(this);
		setFocusable(true);
	}

	
	
	Timer timer = new Timer();

	TimerTask addBasicEnemy = new TimerTask() {
		public void run() {
			int randomX = 10 + (int) (Math.random()*(screen_X-100));
			int randomY = (int) (Math.random()*(-100));
			Alien enemy = new BasicEnemy(randomX,randomY,74,70,3,1);
			if(Level == 1) {
				if(enemies.size() < 7) {	
					enemies.add(enemy);
					if(alienBullets.size() < 10) {
						AlienShoot(enemy);
					}
				}
			}
			if(Level == 2) {
				timer.schedule(addMediumEnemy, 0, 2000);
				addBasicEnemy.cancel();
			}
		}
	};
	TimerTask addMediumEnemy = new TimerTask() {
		
		public void run() {
			int randomX = 10 + (int) (Math.random()*(screen_X-100));
			int randomY = (int) (Math.random()*(-100));
			Alien enemy = new BasicEnemy(randomX,randomY,74,70,4,3);
			if(enemies.size() < 10) {	
				enemies.add(enemy);
				if(alienBullets.size() < 10) {
					AlienShoot(enemy);
				}
			}
			if(Level == 3) {
				timer.schedule(addHardEnemy,0 , 2000);
				addMediumEnemy.cancel();
			}
		}
	};
	TimerTask addHardEnemy = new TimerTask() {
		
		public void run() {
			int randomX = 10 + (int) (Math.random()*(screen_X-100));
			int randomY = (int) (Math.random()*(-100));
			Alien enemy = new BasicEnemy(randomX,randomY,74,70,4,5);
			if(enemies.size() < 15) {	
				enemies.add(enemy);
				if(alienBullets.size() < 10) {
					AlienShoot(enemy);
				}
			}

		}
	};
	
	
	private void AlienShoot(Alien alien) {
		TimerTask alienShoot = new TimerTask() {
			public void run() {
				alienBullets.add(new Laser(alien.getAlienX()-6, alien.getAlienY()+ 10,47,12));
			}
		};
		int delay = 9000 + (int) (Math.random() * 15000);
		timer.schedule(alienShoot, 0, delay);
		
	}

	
	@Override
	protected void paintComponent(Graphics g) {

		ship.draw(g);
		scoreboard.setText("SCORE: " + Score);
		health.setText("Health:" + ship.health);
		level.setText("LEVEL: " + Level);
		gameOver.setText("GAME OVER");
		g.setColor(Color.BLUE);
		for(Laser bullet : bullets) {
			g.fillRect(bullet.laser_x, bullet.laser_y, bullet.width, bullet.height);
		}
		g.setColor(Color.RED);
		for(Laser laser : alienBullets) {
			g.fillRect(laser.laser_x, laser.laser_y, laser.width, laser.height);
		}
		g.setColor(Color.ORANGE);
		for(Alien enemy : enemies) {
			enemy.draw(g);
		}

	}
	
	private void bindKeys() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "moveUpPressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "moveUpReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "moveDownPressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "moveDownReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "moveRightPressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRightReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "moveLeftPressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeftReleased");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shootPressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "shootReleased");

        actionMap.put("moveUpPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveUp(true);
            }
        });
        actionMap.put("moveUpReleased", new AbstractAction() {
            private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
                ship.setMoveUp(false);
            }
        });
        actionMap.put("moveDownPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveDown(true);
            }
        });
        actionMap.put("moveDownReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveDown(false);
            }
        });
        actionMap.put("moveRightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveRight(true);
            }
        });
        actionMap.put("moveRightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveRight(false);
            }
        });
        actionMap.put("moveLeftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveLeft(true);
            }
        });
        actionMap.put("moveLeftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ship.setMoveLeft(false);
            }
        });
        actionMap.put("shootPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                bullets.add(new Laser(ship.getShip_x()-6, ship.getShip_y()-76, 47, 12));
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
		bullets.add(new Laser(ship.getShip_x()-6, ship.getShip_y()-76, 47, 12));

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		isDragging = true;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(isDragging == true) {
			if(x < ship.getShip_x()) {
				if(ship.getShip_x() >= 50) {
					ship.setShip_x(ship.getShip_x() - ship.getShip_speed());
					repaint();
				}
			}
			if(ship.getShip_x() < (screen_X-46)) {	
				if(x > ship.getShip_x()) {
					ship.setShip_x(ship.getShip_x() + ship.getShip_speed());
					repaint();
				}
			}
			if(ship.getShip_y() >= 40) {
				if(y < ship.getShip_y()) {
					ship.setShip_y(ship.getShip_y() - ship.getShip_speed());
					repaint();
				}
			}
			if(ship.getShip_y() <= (screen_Y-46)) {
				if(y > ship.getShip_y()) {
					ship.setShip_y(ship.getShip_y()+ship.getShip_speed());
					repaint();
				}
			}
		}
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {

			ship.setMoveDown(false);
			ship.setMoveLeft(false);
			ship.setMoveRight(false);
			ship.setMoveUp(false);
			isDragging = false;
	}
	
	public void run() {
		while (running) {
			if(ship.health == 0) {
				add(gameOver);

					try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
						writer.append(" " + Score);
			            writer.newLine();
			        } catch (IOException e) {
			            System.err.println("Error writing to the file: " + e.getMessage());
			        }

				running = false;
			}
			
			if(Score < 100 ) {
				Level = 1;
			}
			if(Score >= 100) {
				Level = 2;
			}
			if(Score >= 300) {
				Level = 3;
			}

			Iterator<Laser> bulletIterator = bullets.iterator();
			
			while(bulletIterator.hasNext()) {
				Laser bullett = bulletIterator.next();
				bullett.laser_y -= 5;
				if(bullett.laser_y < 0) {
					bulletIterator.remove();
					break;
				}
				Iterator<Alien> enemyIterator = enemies.iterator();
				while(enemyIterator.hasNext()) {
					Alien enemmy = enemyIterator.next();

					if(bullett.intersects(enemmy.Alien_x, enemmy.Alien_y, enemmy.Alien_width, enemmy.Alien_height)) {
						if(enemmy.Alien_speed == 1) {
							Score += 10;
						}
						if(enemmy.Alien_speed == 3){
							Score += 20;
						}
						if(enemmy.Alien_speed == 5) {
							Score += 30;
						}
						enemmy.getDamaged();
						if (!shootSound.isRunning()) {
						shootSound.setFramePosition(0);
				        shootSound.start();
						}
						bulletIterator.remove();
						
						break;
					}
					else {
						
					}
				}
			}
			
			repaint();
			try {				
					if(ship.getMoveDown() == true) {
						if(ship.getShip_y() <=(screen_Y-46)) {  
							ship.setShip_y(ship.getShip_y()+ship.getShip_speed());
						}
					}
					if(ship.getMoveUp() == true) {
						if(ship.getShip_y() >= 40) {
							ship.setShip_y(ship.getShip_y() - ship.getShip_speed());
						}
					}
					if(ship.getMoveLeft() == true) {
						if(ship.getShip_x() >= 50) {
							ship.setShip_x(ship.getShip_x() - ship.getShip_speed());
						}
					}
					if(ship.getMoveRight() == true) {
						if(ship.getShip_x() < (screen_X-46)) { 
							ship.setShip_x(ship.getShip_x() + ship.getShip_speed());
						}
					}
					
				Thread.sleep(10);
			}
			
				catch(Exception e){
					
				}
		}
	}
	Thread alienLaser = new Thread(() -> {
		while(running) {
			Iterator<Laser> alienlasers = alienBullets.iterator();
			
			while(alienlasers.hasNext()) {
				Laser laser = alienlasers.next();
				laser.laser_y += 5;
				if(laser.laser_y > 800) {
					alienlasers.remove();
					break;
				}

				
					if(laser.intersects(ship.getShip_x()-50, ship.getShip_y() + 20, ship.getShip_width(), ship.getShip_height())) {
						ship.health -=1;
						alienlasers.remove();

						break;
					
				}
			}

			try {
				Thread.sleep(50);
			}
			catch(Exception e){
				
			}
		}
	});

	Thread alienSlider = new Thread(() -> {
		while(running) {
			Iterator<Alien> please = enemies.iterator();
			while(please.hasNext()) {
				Alien alien = please.next();
				alien.move(screen_X, screen_Y);
				if(alien.getAlienY()>800) {
					please.remove();
				}
				if(alien.health <= 0) {
					
					Score += alien.getScore();
					if (!alienDeath.isRunning()) {
						alienDeath.setFramePosition(0);
				        alienDeath.start();
						}
					please.remove();
				}

			}
			try {
				Thread.sleep(20);
			}
			catch(Exception e){
				
			}
		}
	});



}
	

