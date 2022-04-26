package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.stereotype.Service;

@Service
public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            if (lifePart.isIsHit()) {
                splitAsteroid(asteroid, world);
            }

            updateShape(asteroid);
        }
    }

    private void splitAsteroid(Entity entity, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        LifePart lifePart = entity.getPart(LifePart.class);

        // Health
        float radians = positionPart.getRadians();
        int radius = 0;
        float speed = 5;
        int life = lifePart.getLife() - 1;

        if (life == 1) {
            radius = 6;
            speed = (float) Math.random() * 30 + 70;
        } else if (life == 2) {
            radius = 10;
            speed = (float) Math.random() * 10 + 50;
        } else if (life <= 0) {
            world.removeEntity(entity);
        }

        // Asteroid 1
        Entity asteroidOne = new Asteroid();
        asteroidOne.setRadius(radius);

        float radians1 = radians - 0.5f;

        float ax1 = (float) Math.sin(radians1) * entity.getRadius();
        float ay1 = (float) Math.cos(radians1) * entity.getRadius();

        PositionPart astPositionPart1 = new PositionPart(positionPart.getX() + ax1, positionPart.getY() + ay1, radians1);
        asteroidOne.add(astPositionPart1);
        asteroidOne.add(new LifePart(life, 0));
        world.addEntity(asteroidOne);

        // Asteroid 2
        Entity asteroid2 = new Asteroid();

        asteroid2.setRadius(radius);
        float radians2 = radians + 0.5f;

        float by2 = (float) Math.sin(radians2) * entity.getRadius();
        float bx2 = (float) Math.cos(radians2) * entity.getRadius();
        PositionPart astPositionPart2 = new PositionPart(positionPart.getX() + bx2, positionPart.getY() + by2, radians2);

        asteroid2.add(astPositionPart2);
        asteroid2.add(new LifePart(life, 0));
        world.addEntity(asteroid2);

        world.removeEntity(entity);
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 12);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 2 * 3.1415f / 5) * 21);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 19);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 23);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 11);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
