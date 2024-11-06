package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Tank {
	private int x, y, score, lives;
	private boolean up, down, left, right, shoot;
	private String bulletDirection;
	private Bullet bullet;
	private int color;
	private String name;
	private Brick brick;
	private volatile boolean alive;
	private ImageIcon tankIconUp, tankIconDown, tankIconLeft, tankIconRight;

	public Tank(int startX, int startY, int color, String name) {
		this.x = startX;
		this.y = startY;
		this.color = color;
		this.name = name;
		this.score = 0;
		this.lives = 5;
		this.up = true;
		this.down = false;
		this.left = false;
		this.right = false;
		this.shoot = false;
		this.brick = new Brick();
		this.bulletDirection = "";
		setIcon(color);

	}

	public boolean isUp() {
		return up;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void reset(int startX, int startY) {
		this.x = startX;
		this.y = startY;
		this.score = 0;
		this.lives = 5;
		this.up = true;
		this.down = false;
		this.left = false;
		this.right = false;
		this.shoot = false;
		this.bulletDirection = "";
		this.bullet = null;
		alive = true; // Start tank as alive

	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLives() {
		return lives;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public void setBulletDirection(String direction) {
		this.bulletDirection = direction;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	public void moveUp() {
		up = true;
		down = left = right = false;
		if (y > 10)
			y -= 10;
	}

	public void moveDown() {
		down = true;
		up = left = right = false;
		if (y < 540)
			y += 10;
	}

	public void moveLeft() {
		left = true;
		up = down = right = false;
		if (x > 10)
			x -= 10;
	}

	public void moveRight() {
		right = true;
		up = down = left = false;
		if (x < 590)
			x += 10;
	}
//	public void moveUp() {
//		up = true;
//		down = left = right = false;
//		if (y > 10 && canMoveTo(x, y - 10)) // Kiểm tra xem có thể di chuyển lên trên không
//			y -= 10;
//	}
//
//	public void moveDown() {
//		down = true;
//		up = left = right = false;
//		if (y < 540 && canMoveTo(x, y + 10)) // Kiểm tra xem có thể di chuyển xuống dưới không
//			y += 10;
//	}
//
//	public void moveLeft() {
//		left = true;
//		up = down = right = false;
//		if (x > 10 && canMoveTo(x - 10, y)) // Kiểm tra xem có thể di chuyển sang trái không
//			x -= 10;
//	}
//
//	public void moveRight() {
//		right = true;
//		up = down = left = false;
//		if (x < 590 && canMoveTo(x + 10, y)) // Kiểm tra xem có thể di chuyển sang phải không
//			x += 10;
//	}

	public void drawTankPvP(Graphics g) {
		// Vẽ tank
//		g.setColor(color);
//		g.fillRect(x, y, 30, 30);
//		g.drawString(name, x, y - 5);

		int gunX = x + 15; // Tọa độ x của tâm nòng súng
		int gunY = y + 15; // Tọa độ y của tâm nòng súng

		// Vẽ nòng súng
		if (up) {
			g.drawImage(tankIconUp.getImage(), x, y, null);
//			g.fillRect(gunX - 3, y - 6, 6, 6); // Vẽ nòng súng lên trên
			// Tạo đạn ở trên nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(gunX - 3, y - 6, "up");
			}
		} else if (right) {
			g.drawImage(tankIconRight.getImage(), x, y, null); // Vẽ icon right
//			g.fillRect(x + 30, gunY - 3, 6, 6); // Vẽ nòng súng sang phải
			// Tạo đạn ở phía bên phải nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(x + 30, gunY - 3, "right");
			}
		} else if (down) {
			g.drawImage(tankIconDown.getImage(), x, y, null);
			g.fillRect(gunX - 3, y + 30, 6, 6); // Vẽ nòng súng xuống dưới
			// Tạo đạn ở dưới nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(gunX - 3, y + 30, "down");
			}
		} else if (left) {
			g.drawImage(tankIconLeft.getImage(), x, y, null);
//			g.fillRect(x - 6, gunY - 3, 6, 6); // Vẽ nòng súng sang trái
			// Tạo đạn ở phía bên trái nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(x - 6, gunY - 3, "left");
			}
		}

		if (bullet != null && bulletHitsBrick(bullet)) {
			bullet = null; // Xóa đạn
		}
	}

	public void drawTankPvE(Graphics g) {
//		// Vẽ tank
//		g.setColor();
//		g.fillRect(x, y, 30, 30);
//		g.drawString(name, x, y - 5);

		int gunX = x + 15; // Tọa độ x của tâm nòng súng
		int gunY = y + 15; // Tọa độ y của tâm nòng súng

		// Vẽ nòng súng
		if (up) {
			g.drawImage(tankIconUp.getImage(), x, y, null);
//			g.fillRect(gunX - 3, y - 6, 6, 6); // Vẽ nòng súng lên trên
			// Tạo đạn ở trên nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(gunX - 3, y - 6, "up");
			}
		} else if (right) {
			g.drawImage(tankIconRight.getImage(), x, y, null);
//			g.fillRect(x + 30, gunY - 3, 6, 6); // Vẽ nòng súng sang phải
			// Tạo đạn ở phía bên phải nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(x + 30, gunY - 3, "right");
			}
		} else if (down) {
			g.drawImage(tankIconDown.getImage(), x, y, null);
//			g.fillRect(gunX - 3, y + 30, 6, 6); // Vẽ nòng súng xuống dưới
			// Tạo đạn ở dưới nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(gunX - 3, y + 30, "down");
			}
		} else if (left) {
			g.drawImage(tankIconLeft.getImage(), x, y, null);
//			g.fillRect(x - 6, gunY - 3, 6, 6); // Vẽ nòng súng sang trái
			// Tạo đạn ở phía bên trái nòng súng
			if (shoot && bullet == null) {
				bullet = new Bullet(x - 6, gunY - 3, "left");
			}
		}
	}

	private boolean bulletHitsBrick(Bullet bullet) {
		Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(), 10, 10);

		for (int i = 0; i < brick.getBricksXPos().length; i++) {
			if (brick.getBrickON()[i] == 1 && bulletBounds
					.intersects(new Rectangle(brick.getBricksXPos()[i], brick.getBricksYPos()[i], 50, 50))) {
				brick.getBrickON()[i] = 0; // Đánh dấu brick đã bị hủy
				return true;
			}
		}

		for (int i = 0; i < brick.getSolidBricksXPos().length; i++) {
			if (bulletBounds
					.intersects(new Rectangle(brick.getSolidBricksXPos()[i], brick.getSolidBricksYPos()[i], 50, 50))) {
				return true;
			}
		}

		return false;
	}

	public void setIcon(int playerNumber) {
		try {
			if (playerNumber == 1) {
				tankIconUp = new ImageIcon("D:/Wordspace/TankGame/src/image/tank1_up.png");
				tankIconDown = new ImageIcon("D:/Wordspace/TankGame/src/image/tank1_down.png");
				tankIconLeft = new ImageIcon("D:/Wordspace/TankGame/src/image/tank1_left.png");
				tankIconRight = new ImageIcon("D:/Wordspace/TankGame/src/image/tank1_right.png");
			} else if (playerNumber == 2) {
				tankIconUp = new ImageIcon("D:/Wordspace/TankGame/src/image/tank2_up.png");
				tankIconDown = new ImageIcon("D:/Wordspace/TankGame/src/image/tank2_down.png");
				tankIconLeft = new ImageIcon("D:/Wordspace/TankGame/src/image/tank2_left.png");
				tankIconRight = new ImageIcon("D:/Wordspace/TankGame/src/image/tank2_right.png");
			} else if (playerNumber == 3) {
				tankIconUp = new ImageIcon("D:/Wordspace/TankGame/src/image/tank3_up.png");
				tankIconDown = new ImageIcon("D:/Wordspace/TankGame/src/image/tank3_down.png");
				tankIconLeft = new ImageIcon("D:/Wordspace/TankGame/src/image/tank3_left.png");
				tankIconRight = new ImageIcon("D:/Wordspace/TankGame/src/image/tank3_right.png");
			} else if (playerNumber == 4) {
				tankIconUp = new ImageIcon("D:/Wordspace/TankGame/src/image/tankbot_up.png");
				tankIconDown = new ImageIcon("D:/Wordspace/TankGame/src/image/tankbot_down.png");
				tankIconLeft = new ImageIcon("D:/Wordspace/TankGame/src/image/tankbot_left.png");
				tankIconRight = new ImageIcon("D:/Wordspace/TankGame/src/image/tankbot_right.png");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}