package model;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	private double x, y;
	private String direction;

	public Bullet(double x, double y, String direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void move() {
		switch (direction) {
		case "right":
			x += 5;
			break;
		case "left":
			x -= 5;
			break;
		case "up":
			y -= 5;
			break;
		case "down":
			y += 5;
			break;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval((int) x, (int) y, 10, 10);
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}
}