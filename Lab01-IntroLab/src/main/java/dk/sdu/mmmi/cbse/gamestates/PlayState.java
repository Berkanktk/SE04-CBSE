package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;
	private Enemy enemy;

	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		bullets = new ArrayList<Bullet>();
		
		player = new Player(bullets);

		enemy = new Enemy(bullets);

		asteroids = new ArrayList<Asteroid>();
		// asteroids.add(new Asteroid(100,100, Asteroid.LARGE));
		// asteroids.add(new Asteroid(200,100, Asteroid.MEDIUM));
		// asteroids.add(new Asteroid(300,100, Asteroid.SMALL));

		level = 1;
		spawnAsteroids();
	}

	private void splitAsteroids(Asteroid a) {
		numAsteroidsLeft--;
		if (a.getType() == Asteroid.LARGE) {
			asteroids.add(
					new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
			asteroids.add(
					new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
		} if (a.getType() == Asteroid.MEDIUM) {
			asteroids.add(
					new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
			asteroids.add(
					new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
		}
	}

	private void spawnAsteroids() {
		 asteroids.clear();

		 int numToSpawn = 4 + level -1;
		 totalAsteroids = numToSpawn * 7;
		 numAsteroidsLeft = totalAsteroids;

		for (int i = 0; i < numToSpawn; i++) {
			float x = MathUtils.random(Game.WIDTH);
			float y = MathUtils.random(Game.HEIGHT);

			// Don't spawn the asteroid near the player
			float dx = x - player.getX();
			float dy = y - player.getY();
			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			while (dist < 100) {
				x = MathUtils.random(Game.WIDTH);
				y = MathUtils.random(Game.HEIGHT);
				dx = x - player.getX();
				dy = y - player.getY();
				dist = (float) Math.sqrt(dx * dx + dy * dy);
			}
			asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
			asteroids.add(new Asteroid(x, y, Asteroid.MEDIUM));
			asteroids.add(new Asteroid(x, y, Asteroid.SMALL));
		}
	}

	public void update(float dt) {

		// Get user input
		handleInput();

		// Update player
		player.update(dt);

		// Update enemy
		enemy.update(dt);

		// Update player bullets
		for (int i  = 0; i < bullets.size(); i++){
			bullets.get(i).update(dt);
			if (bullets.get(i).shouldRemove()){
				bullets.remove(i);
				i--;
			}
		}

		// Update Asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).update(dt);
			if (asteroids.get(i).shouldRemove()){
				asteroids.remove(i);
				i--;
			}
		}

		// Check collision
		checkCollisions();
		
	}

	private void checkCollisions() {
		// Player-asteroid collision
		for(int i = 0; i < asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);
			if(a.intersects(player)) {
				player.hit();
				asteroids.remove(i);
				i--;
				splitAsteroids(a);
				break;
			}
		}

		// Bullet-asteroid collision
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(j);
				if (a.contains(b.getX(), b.getY())){
					bullets.remove(i);
					i--;
					asteroids.remove(j);
					j--;
					splitAsteroids(a);
					break;
				}
			}
		}
	}

	public void draw() {
		// Draw player
		player.draw(sr);

		// Draw player
		enemy.draw(sr);

		// Draw bullets
		for (int i  = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
		}

		// Draw asteroids
		for (int i  = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(sr);
		}
	}

	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if (GameKeys.isPressed(GameKeys.SPACE)){
			player.shoot();
		}
	}
	
	public void dispose() {}
	
}









