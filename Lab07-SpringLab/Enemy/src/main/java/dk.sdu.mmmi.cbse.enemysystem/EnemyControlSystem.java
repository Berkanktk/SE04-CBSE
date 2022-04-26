package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.bullet.EnemyBullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EnemyControlSystem implements IEntityProcessingService {

    private Random random = new Random();
    private boolean direction_right;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            if (random.nextDouble() > 0.8) {
                direction_right = (direction_right == false) ? true : false;
            }

            movingPart.setLeft(!direction_right);
            movingPart.setRight(direction_right);
            movingPart.setUp(true);

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            if ((int) (Math.random() * 40) == 0) {
                //System.out.println("Enemy is shooting!");
                world.addEntity(shootProjectile(positionPart));
            }

            if (lifePart.isIsHit()) {
                lifePart.setLife(lifePart.getLife() - 1);
            }

            if (lifePart.getLife() <= 0) {
                world.removeEntity(enemy);
            }

            updateShape(enemy);
        }
    }

    private Entity shootProjectile(PositionPart enemyPos) {
        float deacceleration = 0;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 0;
        float x = enemyPos.getX();
        float y = enemyPos.getY();
        float radians = enemyPos.getRadians();

        Entity bullet = new EnemyBullet();
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bullet.add(new PositionPart(x, y, radians));

        return bullet;
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}