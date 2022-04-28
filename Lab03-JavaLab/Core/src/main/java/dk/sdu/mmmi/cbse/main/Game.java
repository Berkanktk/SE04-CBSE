package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    // private final List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    // private final List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
    // private final List<IGamePluginService> entityPlugins = new ArrayList<>();
    private final World world = new World();
    private ShapeRenderer sr;

    @Override
    public void create() {

        // Game window
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData)
        );

        // SPILocator.locateAll(IGamePluginService.class);
        List<IGamePluginService> igp = SPILocator.locateAll(IGamePluginService.class);

        for (IGamePluginService ig : igp) {
            ig.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // Clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();
        draw();

        gameData.getKeys().update();
    }

    private void update() {
        List<IEntityProcessingService> entityProcessors = SPILocator.locateAll(IEntityProcessingService.class);
        List<IPostEntityProcessingService> postEntityProcessors = SPILocator.locateAll(IPostEntityProcessingService.class);

        // Entity updates
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }

        // Post updates
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessors) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {


            if (entity instanceof Enemy) {
                sr.setColor(255, 0, 0, 1);
            } else if (entity instanceof Player) {
                sr.setColor(0, 255, 0, 1);
            } else if (entity instanceof Asteroid) {
                sr.setColor(255, 160, 0, 1);
            } else if (entity instanceof Bullet) {
                sr.setColor(0, 1, 0, 1);
            }

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                 i < shapex.length;
                 j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
