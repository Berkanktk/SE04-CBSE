package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    private Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    private Entity createAsteroid(GameData gameData) {

        float deceleration = 10;
        float acceleration = 200;
        float maxSpeed = 100;
        float rotationSpeed = 5;

        // Create random start position
        int x = random.nextInt(gameData.getDisplayWidth());
        int y = random.nextInt(gameData.getDisplayHeight());
        float radians = 3.1415f * random.nextFloat();

        // Add new asteroid entity with defined properties
        Entity asteroid = new Asteroid();
        asteroid.setRadius(20);
        asteroid.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(3,0));

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(asteroid);
    }
}