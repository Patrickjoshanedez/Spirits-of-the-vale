package Main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();//JFrame object
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close window when x is pressed
		window.setResizable(false);//so it can't be resized
		window.setTitle("The Faraway Land");//game title
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
		

	}

}
