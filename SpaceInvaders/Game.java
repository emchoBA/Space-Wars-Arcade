import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Game {

	public static String name;
	private static String password;
	private static String checkName;
	private static String checkPassword;
	private static boolean registered;


	public String getName() {
		return name;
	}
	public static void main(String args[]) {
		String filePath = "/Users/emcho/Desktop/eclipse workspace/SpaceInvaders/scoreboard.txt";
		JTextArea highscore = new JTextArea(1000,800);
		Font font = highscore.getFont();
		highscore.setFont(font.deriveFont(font.getSize() + 10f));
		registered = false;
		JLabel background = new JLabel(new ImageIcon("/Users/emcho/Desktop/eclipse workspace/SpaceInvaders/background.png"));
		
		
		JFrame frame = new JFrame("Space Invaders");
		
		
		background.setBackground(Color.BLACK);
		frame.add(background);
		frame.setPreferredSize(new Dimension(1200,800));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JMenuBar menu = new JMenuBar();        
		JMenu file_menu = new JMenu("File");
        JMenu help_menu = new JMenu("Help");
        
        JMenuItem register_item = new JMenuItem("Register");
        JMenuItem playGame_item = new JMenuItem("Play Game");
        JMenuItem highScore_item = new JMenuItem("High Score");
        JMenuItem quit_item = new JMenuItem("Quit");
        JMenuItem about_item = new JMenuItem("About");
        
        file_menu.add(register_item);
        file_menu.add(playGame_item);
        file_menu.add(highScore_item);
        file_menu.add(quit_item);
        help_menu.add(about_item);
        
        menu.add(file_menu);
        menu.add(help_menu);
        
        
        frame.setJMenuBar(menu);

		playGame_item.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		if(registered == true) {
        			checkName = JOptionPane.showInputDialog("Confirm Name:");
        			checkPassword = JOptionPane.showInputDialog("Confirm Password:");
        			if(checkName.equals(name) && checkPassword.equals(password)) {
        				frame.getContentPane().removeAll();
        				frame.add(new GameGraphics());
            			frame.validate();
        		}

    			}
        		if(registered == false){
        			frame.getContentPane().removeAll();
        			GameGraphics game = new GameGraphics();
        			frame.add(game);
        			frame.validate();

        		}

        	}
        });
		quit_item.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		System.exit(0);

        	}
        });
		about_item.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
    			JOptionPane.showMessageDialog(null, "Emir Emri\n20210702029\nemir.emri@std.yeditepe.edu.tr");

        	}
        });

		register_item.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		name = JOptionPane.showInputDialog("Enter Name:");
        		password = JOptionPane.showInputDialog("Enter Password:");
        		registered = true;
        		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
					writer.append(name);
		        } catch (IOException e) {
		            System.err.println("Error writing to the file: " + e.getMessage());
		        }
        	}
        });
		highScore_item.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		frame.getContentPane().removeAll();
        		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
					StringBuilder data = new StringBuilder();
					String line;
					
					while((line = reader.readLine()) != null) {
						data.append(line).append("\n");
					}
					highscore.setText(data.toString());
					JScrollPane displayHS = new JScrollPane(highscore);
					frame.add(displayHS);
        			frame.validate();

		        } catch (IOException e) {
		            System.err.println("Error writing to the file: " + e.getMessage());
		        }
        	}
        });
		
		frame.pack();
        frame.setVisible(true);
        
        
		
	}

}
