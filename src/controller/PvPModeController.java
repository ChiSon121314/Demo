package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

import model.Brick;
import model.Bullet;
import model.Tank;
import view.PvPModeView;

public class PvPModeController implements ActionListener, KeyListener {
	private Tank player1, player2;
	private PvPModeView view;
	private Timer timer;
	private boolean paused;
	private boolean gameRunning;
	private Set<Integer> keysPressed;
	private Brick brick;

	public PvPModeController(Tank player1, Tank player2, PvPModeView view, Brick brick) {
		this.player1 = player1;
		this.player2 = player2;
		this.view = view;
		this.brick = brick;
		this.keysPressed = new HashSet<>();
		this.paused = false;
		this.gameRunning = true;

		List<Rectangle> brickBounds = getBrickBounds();

		timer = new Timer(8, this);
		timer.start();
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
		if (e.getKeyCode() == KeyEvent.VK_R && (player1.getLives() == 0 || player2.getLives() == 0)) {
			resetGame();
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_P) {
			paused = !paused;
			view.setPaused(paused);
			return;
		}

		if (player1.getLives() == 0 || player2.getLives() == 0 || paused)
			return;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			if (canMoveTo(player1.getX(), player1.getY() - 5)) {
				player1.moveUp();
			}
			break;
		case KeyEvent.VK_A:
			if (canMoveTo(player1.getX() - 5, player1.getY())) {
				player1.moveLeft();
			}
			break;
		case KeyEvent.VK_S:
			if (canMoveTo(player1.getX(), player1.getY() + 5)) {
				player1.moveDown();
			}
			break;
		case KeyEvent.VK_D:
			if (canMoveTo(player1.getX() + 5, player1.getY())) {
				player1.moveRight();
			}
			break;
		case KeyEvent.VK_SPACE:
			if (player1.getBullet() == null) {
				createBullet(player1);
			}
			break;
		case KeyEvent.VK_UP:
			if (canMoveTo(player2.getX(), player2.getY() - 5)) {
				player2.moveUp();
			}
			break;
		case KeyEvent.VK_LEFT:
			if (canMoveTo(player2.getX() - 5, player2.getY())) {
				player2.moveLeft();
			}
			break;
		case KeyEvent.VK_DOWN:
			if (canMoveTo(player2.getX(), player2.getY() + 5)) {
				player2.moveDown();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (canMoveTo(player2.getX() + 5, player2.getY())) {
				player2.moveRight();
			}
			break;
		case KeyEvent.VK_ENTER:
			if (player2.getBullet() == null) {
				createBullet(player2);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void moveBullets() {
		if (player1.getBullet() != null) {
			player1.getBullet().move();
			if (bulletOutOfBound(player1.getBullet())) {
				player1.setBullet(null);
			} else {
				checkCollision(player1, player2);
			}
		}
		if (player2.getBullet() != null) {
			player2.getBullet().move();
			if (bulletOutOfBound(player2.getBullet())) {
				player2.setBullet(null);
			} else {
				checkCollision(player2, player1);
			}
		}
	}

	private void checkCollision(Tank shootingTank, Tank targetTank) {
		Bullet bullet = shootingTank.getBullet();
		if (bullet != null) {
			int bulletX = bullet.getX();
			int bulletY = bullet.getY();

			// Check if bullet hits a brick
			if (bulletHitsBrick(bullet)) {
				shootingTank.setScore(shootingTank.getScore() + 1000);
				shootingTank.setBullet(null);
			}

			// Check if bullet hits a tank
			int targetX = targetTank.getX();
			int targetY = targetTank.getY();
			if (bulletX >= targetX && bulletX <= targetX + 30 && bulletY >= targetY && bulletY <= targetY + 30) {
				targetTank.setLives(targetTank.getLives() - 1);
				shootingTank.setScore(shootingTank.getScore() + 3000);
				shootingTank.setBullet(null);
			}
		}
	}

	private boolean bulletHitsBrick(Bullet bullet) {
		Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(), 10, 10);

		// Check regular bricks
		for (int i = 0; i < brick.getBricksXPos().length; i++) {
			if (brick.getBrickON()[i] == 1 && bulletBounds
					.intersects(new Rectangle(brick.getBricksXPos()[i], brick.getBricksYPos()[i], 50, 50))) {
				brick.getBrickON()[i] = 0;
				return true;
			}
		}

		// Check solid bricks
		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
			if (bulletBounds
					.intersects(new Rectangle(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50))) {
				return false;
			}
		}

		return false;
	}

	private boolean canMoveTo(int newX, int newY) {
		Rectangle tankBounds = new Rectangle(newX, newY, 30, 30);

		// Kiểm tra va chạm với solid bricks
		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
			if (tankBounds
					.intersects(new Rectangle(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50))) {
				return false; // Không được phép di chuyển
			}

			// Kiểm tra va chạm với regular bricks
			for (int j = 0; j < brick.getBricksXPos().length; j++) {
				if (brick.getBrickON()[j] == 1 && tankBounds
						.intersects(new Rectangle(brick.getBricksXPos()[j], brick.getBricksYPos()[j], 50, 50))) {
					return false; // Không được phép di chuyển
				}
			}
		}

		return true; // Được phép di chuyển
	}

	private void createBullet(Tank player) {
		int bulletX = player.getX() + 15; // Default bullet position in the middle of the tank
		int bulletY = player.getY() + 15; // Default bullet position in the middle of the tank
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
		player2.reset(410, 550);
		for (int i = 0; i < brick.getBrickON().length; i++) {
			brick.getBrickON()[i] = 1;
		}
		paused = false;
		gameRunning = true;
	}

	private List<Rectangle> getBrickBounds() {
		List<Rectangle> bounds = new ArrayList<>();
		for (int i = 0; i < brick.getBricksXPos().length; i++) {
			if (brick.getBrickON()[i] == 1) {
				bounds.add(new Rectangle(brick.getBricksXPos()[i], brick.getBricksYPos()[i], 50, 50));
			}
		}
		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
			bounds.add(new Rectangle(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50));
		}
		return bounds;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public boolean isPaused() {
		return paused;
	}
}
