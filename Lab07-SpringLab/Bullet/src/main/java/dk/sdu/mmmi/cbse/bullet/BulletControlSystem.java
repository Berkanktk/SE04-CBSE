package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        ArrayList<Entity> expiredProjectiles = new ArrayList<>();
        ArrayList<Entity> bullets = new ArrayList<>();
        bullets.addAll(world.getEntities(EnemyBullet.class));
        bullets.addAll(world.getEntities(PlayerBullet.class));

        for (Entity projectile : bullets) {
            PositionPart positionPart = projectile.getPart(PositionPart.class);
            MovingPart movingPart = projectile.getPart(MovingPart.class);
            LifePart lifePart = projectile.getPart(LifePart.class);

            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(true);

            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);

            /* Broken
            if (lifePart.isIsHit()){
                world.removeEntity(projectile);
            }

            lifePart.reduceExpiration(gameData.getDelta());
             */

            updateShape(projectile);

            if (checkExpiration(projectile)) {
                expiredProjectiles.add(projectile);
            }
        }
        for (Entity e : expiredProjectiles) {
            world.removeEntity(e);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        Entity bullet = new Bullet();
        bullet.setRadius(2);

        // Player position
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 3);
        shapey[0] = (float) (y + Math.sin(radians) * 3);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 3);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 3);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 2);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 2);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 3);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 3);

        bullet.add(new LifePart(1, 1));
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private boolean checkExpiration(Entity entity) {
        if (entity instanceof Bullet) {
            Bullet e = (Bullet) entity;
            if (e.isExpired()) {
                return true;
            } else {
                e.increaseDuration();
            }
        }
        return false;
    }
}