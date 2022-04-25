package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollisionControlSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        boolean collision;

        for (Entity entity : world.getEntities()) {
            for (Entity otherEntity : world.getEntities()) {
                if (!entity.getClass().equals(otherEntity.getClass())) {

                    /* Broken
                    LifePart lifePart = entity.getPart(LifePart.class);
                    LifePart otherLifePart = otherEntity.getPart(LifePart.class);

                    collision = checkCollision(entity, otherEntity);

                    lifePart.setIsHit(collision);
                    otherLifePart.setIsHit(collision);
                     */

                }
            }
        }
    }

    private boolean checkCollision(Entity entity, Entity otherEntity) {

        PositionPart entityOne = entity.getPart(PositionPart.class);
        PositionPart entityTwo = otherEntity.getPart(PositionPart.class);

        float x = entityOne.getX() - entityTwo.getX();
        float y = entityOne.getY() - entityTwo.getY();

        float distance = (float) Math.sqrt(x * x + y * y);


        if ((entity.getRadius() + otherEntity.getRadius()) >= distance) {
            System.out.println("Collision!");
            return true;
        }
        return false;

    }
}