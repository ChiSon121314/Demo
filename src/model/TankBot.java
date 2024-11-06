package model;

import java.awt.Color;
import java.awt.Graphics;

public class TankBot extends Tank {
	private Tank player;
	private boolean isRunning;

	public TankBot(int startX, int startY, int color, String name, Tank player) {
		super(startX, startY, color, name);
		this.player = player;
		this.isRunning = true;
	}

	public void autoMoveTowardsPlayer() {
		if (!isRunning)
			return;

		if (getX() < player.getX()) {
			moveRight();
		} else if (getX() > player.getX()) {
			moveLeft();
		}

		if (getY() < player.getY()) {
			moveDown();
		} else if (getY() > player.getY()) {
			moveUp();
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void shootTowardsPlayer() {
		if (!isRunning || getBullet() != null)
			return;
		int bulletX = getX() + 15;
		int bulletY = getY() + 15;
		String direction = "";

		if (Math.abs(getX() - player.getX()) > Math.abs(getY() - player.getY())) {
			if (getX() < player.getX()) {
				bulletX += 10;
				direction = "right";
			} else {
				bulletX -= 10;
				direction = "left";
			}
		} else {
			if (getY() < player.getY()) {
				bulletY += 10;
				direction = "down";
			} else {
				bulletY -= 10;
				direction = "up";
			}
		}

		setBullet(new Bullet(bulletX, bulletY, direction));
	}

	@Override
	public void drawTankPvE(Graphics g) {
		super.drawTankPvE(g);
		g.setColor(Color.WHITE);
	}
}
