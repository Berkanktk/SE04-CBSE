package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;

public class BulletPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Projectile plugin initialized.");
    }

    @Override
    public void stop(GameData gameData, World world) {
        List<Entity> bullets = new ArrayList<>();
        bullets.addAll(world.getEntities(EnemyBullet.class));
        bullets.addAll(world.getEntities(PlayerBullet.class));

        for (Entity e : bullets) {
            world.removeEntity(e);
        }
    }
}
