package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.sound.sampled.Line;

import basic.Game;
import helper.AbilityController;
import helper.TextureLoader;
import helper.creatureController;

public class Enemy extends Creature {

	public static Enemy[] enemiesTemplate = new Enemy[100];
	public static Enemy Orc;

	public BufferedImage[][] texture;
	public Random l = new Random();
	public creatureController cc;
	Stack<Point>  path = new Stack<Point>();
	private int deadcounter = 0;
	public Enemy(Game g, float x, float y, BufferedImage[][] img, Rectangle bounds) {
		super(g, x, y);

		// img = TextureLoader.player[0][1];
		face = l.nextInt(4);
		this.texture = img;
		this.bounds = bounds;
		width = texture[0][0].getWidth();
		height = texture[0][0].getHeight();
		fullhealth = 300;
		health = 300;
		fullenergy = energy = 1000000;
		realbounds = new Rectangle();
		walkcounter = 0;
		speed = 2.1f;
		for (int i = 0; i < 100; i++)
			skill[i] = null;
		skill[0] = new Ability().copy(Ability.claw);
		skill[1] = new Ability().copy(Ability.hit);
		using = skill[0];
	}

	public Enemy copy(Enemy template) {
		this.bounds = new Rectangle(template.bounds);
		this.texture = template.texture.clone();
		this.realbounds = new Rectangle();
		return this;
	}

	public creatureController getCC() {
		return cc;
	}

	public void setTarget(Entity e) {
		target[0] = e;
	}

	public void damage(float damage) {
		health -= damage;
		if (health <= 0 && !dead)
			die();
	}

	public void die() {
		if (!dead)
			camera.getGameScene().getPlayer().gainExp(10);
		dead = true;
	}

	public void deadrender(Graphics g) {
		if (deadcounter / 5 == TextureLoader.animationDead.length) {
			cc.eliminate(this);
			return;
		}
		g.drawImage(TextureLoader.animationDead[deadcounter / 5],
				(int) (x - camera.getCamera().getXoffset() - 96 / 2 + width / 2),
				(int) (y - camera.getCamera().getYoffset() - 96 / 2), 96, 96, null);
		deadcounter++;
	}

	@Override
	public void tick() {
		if (dead)
			return;
		this.realbounds.setBounds((int) (x + bounds.x - camera.getCamera().getXoffset()),
				(int) (y + bounds.y - camera.getCamera().getYoffset()), bounds.width, bounds.height);
		if (Math.pow((target[0].x - x), 2) + Math.pow((target[0].y - y), 2) > 500 * 500)
			return;
		if (target[0].y> y &&target[0].y- y > 1f) {
			move(0, (int) speed);
			face = 1;
		} else if (target[0].y< y &&target[0].y- y < -1f) {
			move(0, (int) (0 - speed));
			face = 0;
		}
		if (target[0].x> x && target[0].x- x > 1f) {
			move((int) speed, 0);
			if (Math.abs(target[0].y- y) < 0.5*Math.abs(target[0].x- x)){
				face = 3;
			}
			if (Math.abs(target[0].y- y) <  2*Math.abs(target[0].x- x)) {
				if (face == 0)
					face = 5;
				else if (face == 1)
					face = 6;
			}
		} else if (target[0].x< x && target[0].x- x < -1f) {
			move((int) (0 - speed), 0);
			if (Math.abs(target[0].y- y) < 0.5*Math.abs(target[0].x- x)){
				face = 2;
			}
			if (Math.abs(target[0].y- y) <  2* Math.abs(target[0].x- x)) {
				if (face == 0)
					face = 4;
				else if (face == 1)
					face = 7;
			}
		}
		if (Math.pow((target[0].x- x), 2) + Math.pow((target[0].y- y), 2) > 500 * 500) {
			switch (face) {
			case 0:
				img = texture[3][1];
				break;
			case 1:
				img = texture[0][1];
				break;
			case 2:
			case 4:
			case 7:
				img = texture[1][1];
				break;
			case 3:
			case 5:
			case 6:
				img = texture[2][1];
				break;
			}
		} else {
			if (face == 0) {
				if (walkcounter > 25)
					img = texture[3][0];
				else
					img = texture[3][2];
				walkcounter++;
			}
			if (face == 1) {
				if (walkcounter > 25)
					img = texture[0][0];
				else
					img = texture[0][2];
				walkcounter++;
			}
			if (face == 2 || face == 4 || face == 7) {
				if (walkcounter > 25)
					img = texture[1][0];
				else
					img = texture[1][2];
				walkcounter++;
			}
			if (face == 3 || face == 5 || face == 6) {
				if (walkcounter > 25)
					img = texture[2][0];
				else
					img = texture[2][2];
				walkcounter++;
			}
		}
		if (walkcounter > 50)
			walkcounter = 0;

		if (Math.pow((target[0].x - x), 2) + Math.pow((target[0].y - y), 2) < Math
				.pow(skill[0].getRange() * 48 + width / 2, 2) || attack) {
			if (Math.pow((target[0].x - x), 2) + Math.pow((target[0].y - y), 2) > 50 * 50
					&& using.anicounter < using.animate.length)
				using = skill[0];
			else if (Math.pow((target[0].x - x), 2) + Math.pow((target[0].y - y), 2) <= 50 * 50
					&& using.anicounter < using.animate.length)
				using = skill[1];
			if (attack) {
				using.tick();
				speed = 2.1f;
			}
			if (Math.pow((target[0].x - x), 2) + Math.pow((target[0].y - y), 2) < Math
					.pow(skill[0].getRange() * 48 + width / 2, 2)) {
				speed = 0;
				attack = true;
			}
		} else
			speed = 2.1f;
		if (attack)
			AbilityController.put(using, this);
	}

	@Override
	public void render(Graphics g) {
		if (dead) {
			deadrender(g);
			return;
		}
		g.drawImage(img, (int) (x - camera.getCamera().getXoffset()), (int) (y - camera.getCamera().getYoffset()),
				null);
		g.setColor(Color.YELLOW);
		g.fillRect((int) (x - camera.getCamera().getXoffset()) - (int) (fullhealth / 20) + 24,
				(int) (y - camera.getCamera().getYoffset() - 3), (int) (health / 10), 3);
		// g.fillRect((int)(x+bounds.x-camera.getCamera().getXoffset()),
		//(int)(y+bounds.y-camera.getCamera().getYoffset()), bounds.width,
		// bounds.height);

	}

}
