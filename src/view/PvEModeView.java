package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Tank;
import model.TankBot;

public class PvEModeView extends JPanel {
	private Tank player1;
	private TankBot bot;
	private boolean paused;

	public PvEModeView(Tank player1, TankBot bot) {
		super();
		this.player1 = player1;
		this.bot = bot;
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 600);
	}

	private void drawPlayers(Graphics g) {
		player1.drawTankPvE(g);
		bot.drawTankPvE(g);
	}

	private void drawBullets(Graphics g) {
		if (player1.getBullet() != null) {
			player1.getBullet().draw(g);

		}
		if (bot.getBullet() != null) {
			bot.getBullet().draw(g);
		}

	}

	public void setBot(TankBot bot) {
		this.bot = bot;
	}

	private void drawScoresAndLives(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 15));
		g.drawString("Scores", 700, 30);
		g.drawString("Player 1:  " + player1.getScore(), 670, 60);

		g.drawString("Lives", 700, 150);
		g.drawString("Player 1:  " + player1.getLives(), 670, 180);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawPlayers(g);
		drawBullets(g);
		drawScoresAndLives(g);
		drawGameOver(g);
		drawPause(g);
	}

	private void drawGameOver(Graphics g) {
		if (player1.getLives() == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			if (player1.getLives() == 0) {
				g.drawString("Game Over", 180, 280);
			} else {
				g.drawString("Winner", 180, 280);
			}
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

	public void updateScore(String playerName, int score) {
		if (playerName.equals(player1.getName())) {
			player1.setScore(score);
		} else if (playerName.equals(bot.getName())) {
			bot.setScore(score);
		}
		repaint();
	}

	public void updateLives(String playerName, int lives) {
		if (playerName.equals(player1.getName())) {
			player1.setLives(lives);
		} else if (playerName.equals(bot.getName())) {
			bot.setLives(lives);
		}
		repaint();
	}
}
