package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import model.Bullet;
import model.Tank;
import model.TankBot;
import view.PvEModeView;

public class PvEModeController implements ActionListener, KeyListener {
	private Tank player1;
	private TankBot bot;
	private PvEModeView view;
	private Timer timer;
	private Set<Integer> keysPressed;
	private boolean paused;

	public PvEModeController(Tank player1, TankBot bot, PvEModeView view) {
		this.player1 = player1;
		this.bot = bot;
		this.view = view;
		this.keysPressed = new HashSet<>();
		this.paused = false;

		timer = new Timer(8, this);
		timer.start();

		startBotAutoActions();
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            view.repaint();
            moveBullets();
        }
    }

	private boolean bulletOutOfBound(Bullet bullet) {
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();
		return bulletX < 0 || bulletX > 650 || bulletY < 0 || bulletY > 600;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysPressed.add(e.getKeyCode());
		handleKeyPresses();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void handleKeyPresses() {

		if (player1.getLives() == 0) {
			if (keysPressed.contains(KeyEvent.VK_R)) {
				resetGame();
			}
			return;
		}
		// Handle player 1 movement
		if (keysPressed.contains(KeyEvent.VK_W)) {
			player1.moveUp();
		}
		if (keysPressed.contains(KeyEvent.VK_A)) {
			player1.moveLeft();
		}
		if (keysPressed.contains(KeyEvent.VK_S)) {
			player1.moveDown();
		}
		if (keysPressed.contains(KeyEvent.VK_D)) {
			player1.moveRight();
		}
		if (keysPressed.contains(KeyEvent.VK_SPACE)) {
			if (player1.getBullet() == null) {
				createBullet(player1);
			}
		}
// Handle game reset
		if (keysPressed.contains(KeyEvent.VK_R)) {
			if (player1.getLives() == 0) {
				resetGame();
			}
		}

		// Handle game pause
		if (keysPressed.contains(KeyEvent.VK_P)) {
			paused = !paused;
			view.setPaused(paused);
		}
	}

	private void moveBullets() {
		if (player1.getBullet() != null) {
			player1.getBullet().move();
			if (bulletOutOfBound(player1.getBullet())) {
				player1.setBullet(null);
			} else if (bot != null) {
				checkCollision(player1, bot);
			}
		}
		if (bot != null && bot.getBullet() != null) {
			bot.getBullet().move();
			if (bulletOutOfBound(bot.getBullet())) {
				bot.setBullet(null);
			} else {
				checkCollision(bot, player1);
			}
		}
	}

	private void checkCollision(Tank shootingTank, Tank targetTank) {
		Bullet bullet = shootingTank.getBullet();
		int bulletX = bullet.getX();
		int bulletY = bullet.getY();
		int targetX = targetTank.getX();
		int targetY = targetTank.getY();

		if (bulletX >= targetX && bulletX <= targetX + 30 && bulletY >= targetY && bulletY <= targetY + 30) {
			System.out.println("Collision Detected!");
			if (targetTank instanceof TankBot) {
				player1.setScore(player1.getScore() + 1000); // Điểm +1000 khi bắn trúng bot
				removeBot();
			} else {
				targetTank.setLives(targetTank.getLives() - 1);
				if (targetTank instanceof Tank && targetTank.getLives() == 0) {
					bot.setRunning(false);
				}
			}
			shootingTank.setBullet(null);
		}
	}

	private void createBullet(Tank player) {
		int bulletX = player.getX() + 15; // Tọa độ x của viên đạn mặc định nằm ở giữa nòng súng
		int bulletY = player.getY() + 15; // Tọa độ y của viên đạn mặc định nằm ở giữa nòng súng
		String direction = "";

		if (player.isUp()) {
			bulletY -= 10;
			direction = "up";
		} else if (player.isDown()) {
			bulletY += 10;
			direction = "down";
		} else if (player.isLeft()) {
			bulletX -= 10;
			direction = "left";
		} else if (player.isRight()) {
			bulletX += 10;
			direction = "right";
		}

		player.setBullet(new Bullet(bulletX, bulletY, direction));
	}

	private void resetGame() {
		player1.reset(210, 550);
		respawnBot();
		view.repaint();
		timer.start();
	}

	private void removeBot() {
		bot = null;
		view.setBot(null);
		respawnBot();
	}

	private void respawnBot() {
		int respawnX = (int) (Math.random() * 600);
		int respawnY = (int) (Math.random() * 500);
		bot = new TankBot(respawnX, respawnY, 4, "Bot", player1);
		view.setBot(bot);
	}

	private void startBotAutoActions() {
		new Thread(() -> {
			while (true) {
				bot.autoMoveTowardsPlayer();
				bot.shootTowardsPlayer();
				try {
					Thread.sleep(setBotSpeed()); // Mỗi giây bot sẽ có một hành động
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private int setBotSpeed() {
		int spd = 1000;
		int playerScore = player1.getScore();

		if (playerScore >= 5000 && playerScore < 5000) {
			spd = 800;
		} else if (playerScore >= 10000) {
			spd = 400;
		}
		return spd;
	}

}
