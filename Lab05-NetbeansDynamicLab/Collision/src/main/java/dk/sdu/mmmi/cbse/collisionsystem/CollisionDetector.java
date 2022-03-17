package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // Iterate through all entities in the world
        for (Entity entityOne : world.getEntities()) {
            for (Entity entityTwo : world.getEntities()) {
                // Get life of all entities
                LifePart entityLife = entityOne.getPart(LifePart.class);

                // If the two entities are identical, skip the iteration
                if (entityOne.getID().equals(entityTwo.getID())) {
                    continue;
                }

                // CollisionDetection logic
                if (checkCollision(entityOne, entityTwo)) {
                    // If an entity gets hit, it will lose one health
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        entityLife.setIsHit(true);

                        // The entity should be removed when out of life
                        if (entityLife.getLife() <= 0) {
                            world.removeEntity(entityOne);
                        }
                    }
                }
            }
        }
    }

    // A function that checks collisions
    public Boolean checkCollision(Entity entity, Entity entity2) {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);

        float dx = entMov.getX() - entMov2.getX();
        float dy = entMov.getY() - entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Calculates collision between two entities
        if (distance < (entity.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

}
