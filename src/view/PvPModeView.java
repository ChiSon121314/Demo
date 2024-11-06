package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Brick;
import model.Tank;

public class PvPModeView extends JPanel {
	private Tank player1, player2;
	private Brick brick;
	private boolean paused;
	private ImageIcon breakBrickImage;
	private ImageIcon solidBrickImage;

	public PvPModeView(Tank player1, Tank player2, Brick brick) {
		this.player1 = player1;
		this.player2 = player2;
		this.brick = brick;

		breakBrickImage = new ImageIcon("D:/Wordspace/TankGame/src/image/break_brick.jpg");
		solidBrickImage = new ImageIcon("D:/Wordspace/TankGame/src/image/solid_brick.jpg");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawBricks(g); // Add this line to draw bricks
		drawPlayers(g);
		drawBullets(g);
		drawScoresAndLives(g);
		drawGameOver(g);
		drawPause(g);
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 650);
	}

//	private void drawBricks(Graphics g) {
//        g.setColor(Color.ORANGE);
//		for (int i = 0; i < brick.getBricksXPos().length; i++) {
//			if (brick.getBrickON()[i] == 1) {
//                g.fillRect(brick.getBricksXPos()[i], brick.getBricksYPos()[i], 50, 50);
//                g.setColor(Color.WHITE);
//                g.drawRect(brick.getBricksXPos()[i], brick.getBricksYPos()[i], 50, 50);
//                g.setColor(Color.ORANGE);
//			}
//		}
//		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
//			g.fillRect(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50);
//			g.setColor(Color.WHITE);
//			g.drawRect(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50);
//			g.setColor(Color.GRAY);
//		}
//	}
	private void drawBricks(Graphics g) {
		for (int i = 0; i < brick.getBricksXPos().length; i++) {
			if (brick.getBrickON()[i] == 1) {
				breakBrickImage.paintIcon(this, g, brick.getBricksXPos()[i], brick.getBricksYPos()[i]);
			}
		}

		g.setColor(Color.GRAY); // Set color for solid bricks
		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
			solidBrickImage.paintIcon(this, g, brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i]);
		}
	}

	private void drawPlayers(Graphics g) {
		player1.drawTankPvP(g);
		player2.drawTankPvP(g);
	}

	private void drawBullets(Graphics g) {
		if (player1.getBullet() != null) {
			player1.getBullet().draw(g);
		}
		if (player2.getBullet() != null) {
			player2.getBullet().draw(g);
		}
	}

	private void drawScoresAndLives(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 15));
		g.drawString("Scores", 700, 30);
		g.drawString("Player 1:  " + player1.getScore(), 670, 60);
		g.drawString("Player 2:  " + player2.getScore(), 670, 90);

		g.drawString("Lives", 700, 150);
		g.drawString("Player 1:  " + player1.getLives(), 670, 180);
		g.drawString("Player 2:  " + player2.getLives(), 670, 210);
	}

//	private void drawGameOver(Graphics g) {
//		if (player1.getLives() == 0 || player2.getLives() == 0) {
//			g.setColor(Color.white);
//			g.setFont(new Font("serif", Font.BOLD, 60));
//			g.drawString("Game Over", 200, 200);
//			if (player1.getLives() == 0) {
//				g.drawString("Player 2 Won", 180, 280);
//			} else {
//				g.drawString("Player 1 Won", 180, 280);
//
//			}
//			g.setFont(new Font("serif", Font.BOLD, 30));
//			g.drawString("(R to Restart or M to Menu)", 230, 330);
//		}
//	}
	private void drawGameOver(Graphics g) {
		if (player1.getLives() == 0 && player2.getLives() == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Game Over", 200, 200);

			if (player1.getScore() > player2.getScore()) {
				g.drawString("Player 1 Won", 180, 280);
			} else if (player1.getScore() < player2.getScore()) {
				g.drawString("Player 2 Won", 180, 280);
			} else {
				g.drawString("Draw", 250, 280);
			}

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(R to Restart or M to Menu)", 230, 330);
		} else if (player1.getLives() == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Game Over", 200, 200);
			g.drawString("Player 2 Won", 180, 280);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(R to Restart or M to Menu)", 230, 330);
		} else if (player2.getLives() == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Game Over", 200, 200);
			g.drawString("Player 1 Won", 180, 280);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(R to Restart or M to Menu)", 230, 330);
		}
	}

	private void drawPause(Graphics g) {
		if (paused) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Pause", 230, 280);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(P to Resume)", 280, 330);
		}
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
		repaint();
	}

	public void showMenu() {
		setVisible(true);
	}

	public void hideMenu() {
		setVisible(false);
	}

	public void updateScore(String playerName, int score) {
		if (playerName.equals(player1.getName())) {
			player1.setScore(score);
		} else if (playerName.equals(player2.getName())) {
			player2.setScore(score);
		}
		repaint();
	}

	public void updateLives(String playerName, int lives) {
		if (playerName.equals(player1.getName())) {
			player1.setLives(lives);
		} else if (playerName.equals(player2.getName())) {
			player2.setLives(lives);
		}
		repaint();
	}
}
