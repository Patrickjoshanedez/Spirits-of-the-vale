package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		// Title state
		if (gp.gameState == gp.titleState) {
			titleState(code);
			
		}

		//playstate
		else if(gp.gameState == gp.playState) {
			playState(code);
			
		}
		//OPTIONSTATE
		else if(gp.gameState == gp.optionState) {
			optionState(code);
				
		}
		
		//dialogue state
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		
		//character state
		else if(gp.gameState == gp.characterState) {
			characterState(code);
		}	
			
		}
		

	public void titleState (int code) {
		if(gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) { // Navigate up
		        gp.ui.commandNum--;
		        if (gp.ui.commandNum < 0) {
		            gp.ui.commandNum = 2; // Wrap to the last menu option
		        }
		    }

		    if (code == KeyEvent.VK_S) { // Navigate down
		        gp.ui.commandNum++;
		        if (gp.ui.commandNum > 2) {
		            gp.ui.commandNum = 0; // Wrap to the first menu option
		        }
		    }

		    if (code == KeyEvent.VK_ENTER) { // Execute the selected option
		    	if (gp.ui.commandNum == 0) { // "New Game" selected
		    	    gp.setUpGame(); // Ensure game setup
		    	    gp.gameState = gp.playState; // Switch to play state
		    	    gp.playMusic(0); // Start music
		    	}

		        if (gp.ui.commandNum == 1) {
		            // Add functionality for "New Game" later
		        }
		        if (gp.ui.commandNum == 2) {
		            System.exit(0); // Exit the game
		        }
		}
		    
	    
	    }
	}
	public void playState (int code) {
		if(code == KeyEvent.VK_W ) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S ) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.optionState;		
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;		
		}
		if(code == KeyEvent.VK_0) {
			shotKeyPressed = true;		
		}
	    if (gp.gameState == gp.gameOverState) {
	        if (code == KeyEvent.VK_ENTER) {
	            // Maybe return to title screen or restart
	            gp.gameState = gp.titleState;
	        }
	    }
	}
	public void optionState (int code) {
		
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			
			int maxCommandNum = 0;
			switch(gp.ui.subState) {
			case 0: maxCommandNum = 5; break;
			case 3: maxCommandNum = 1; break;
			}
			
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(10);
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = maxCommandNum;
				}
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(10);
				if(gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}
				
				if (code == KeyEvent.VK_A) {
					if(gp.ui.subState == 0) {
						if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
							gp.music.volumeScale--;
							gp.music.checkVolume();
						}
					}
				}
				if (code == KeyEvent.VK_A) {
					if(gp.ui.subState == 0) {
						if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
							gp.se.volumeScale--;
						}
					}
				}
				if (code == KeyEvent.VK_D) {
					if(gp.ui.subState == 0) {
						if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
							gp.music.volumeScale++;
							gp.music.checkVolume();
						}
					}
				}
				if (code == KeyEvent.VK_D) {
					if(gp.ui.subState == 0) {
						if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
							gp.se.volumeScale++;
						}
					}
				}
				
			}
		
	public void dialogueState (int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	public void characterState (int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		//character state
		
					
					if (code == KeyEvent.VK_W) {
						if(gp.ui.slotRow != 0) {
							gp.ui.slotRow--;
							gp.playSE(10);
						}				
					}
					if (code == KeyEvent.VK_A) {
						if(gp.ui.slotCol != 0) {
							gp.ui.slotCol--;
							gp.playSE(10);
						}
						
					}
					if (code == KeyEvent.VK_S) {
						if(gp.ui.slotRow != 3) {
						gp.ui.slotRow++;
						gp.playSE(10);
						}
					}
					if (code == KeyEvent.VK_D) {
						if(gp.ui.slotCol != 4) {
						gp.ui.slotCol++;
						gp.playSE(10);
						}
					}
					if(code == KeyEvent.VK_ENTER) {
						gp.player.selectItem();
					}
				}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W ) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S ) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_0) {
			shotKeyPressed = false;		
		}
	}

}
