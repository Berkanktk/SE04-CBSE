package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;

public class Player extends SpaceObject {

    private final int MAX_BULLETS = 4;
    private float[] flameX;
    private float[] flameY;
    private ArrayList<Bullet> bullets;
    private boolean left;
    private boolean right;
    private boolean up;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;

    public Player(ArrayList<Bullet> bullets) {

        this.bullets = bullets;

        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];
        flameX = new float[3];
        flameY = new float[3];

        radians = 3.1415f / 2;
        rotationSpeed = 3;

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }

    private void setFlame() {
        flameX[0] = x + MathUtils.cos(radians - 5 * 3.1415f / 6) * 5;
        flameY[0] = y + MathUtils.sin(radians - 5 * 3.1415f / 6) * 5;

        flameX[1] = x + MathUtils.cos(radians - 5 * 3.1415f) * (6 + acceleratingTimer * 50);
        flameY[1] = y + MathUtils.sin(radians - 5 * 3.1415f) * (6 + acceleratingTimer * 50);

        flameX[2] = x + MathUtils.cos(radians + 5 * 3.1415f / 6) * 5;
        flameY[2] = y + MathUtils.sin(radians + 5 * 3.1415f / 6) * 5;

    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void shoot() {
        if (bullets.size() == MAX_BULLETS) return;
        bullets.add(new Bullet(x, y, radians));
    }

    public void hit() {
        // Implement if you have some spare time
    }

    public void update(float dt) {

        // turning
        if (left) {
            radians += rotationSpeed * dt;
        } else if (right) {
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (up) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;

            acceleratingTimer += dt;
            if (acceleratingTimer > 0.1f) {
                acceleratingTimer = 0;
            }
        } else {
            acceleratingTimer = 0;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // set flame
        if (up) {
            setFlame();
        }

        // screen wrap
        wrap();

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(0, 1, 0, 1);

        sr.begin(ShapeType.Line);

        // Draw player
        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        // Draw flames
        if (up) {
            for (int i = 0, j = flameX.length - 1; i < flameX.length; j = i++) {

                sr.line(flameX[i], flameY[i], flameX[j], flameY[j]);

            }
        }

        sr.end();

    }

}
