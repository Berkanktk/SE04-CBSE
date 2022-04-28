/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

/**
 * @author Berkan Kütük
 */

public class CollisionDetectorTest {
    private World world = new World();

    public CollisionDetectorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        System.out.println("*******************************");
        System.out.println("| Testing collision detection |");
        System.out.println("*******************************");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("\n\t\tTest Finished!\n");

    }

    /**
     * Test of process method, of class CollisionDetector.
     */
    // @Test
    public void testProcess() {

    }

    /**
     * Test of Collision method, of class CollisionDetector.
     */

    @Test
    public void testCollision() {
        // Creating the first entity
        Entity entity = new Entity();
        entity.setRadius(10);
        entity.add(new PositionPart(0, 0, 0));
        entity.add(new LifePart(3));

        // Creating the second entity
        Entity entity2 = new Entity();
        entity2.setRadius(10);
        entity2.add(new PositionPart(5, 5, 0));
        entity2.add(new LifePart(3));

        // Adding them to the world
        world.addEntity(entity);
        world.addEntity(entity2);

        new CollisionDetector().process(null, world);

        // Getting their x and y coordinates
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);

        System.out.println("Entity 1: " + "X: " + entMov.getX() + ", Y: " + entMov.getY());
        System.out.println("Entity 2: " + "X: " + entMov2.getX() + ", Y: " + entMov2.getY());

        // Getting their life parts to detect collision
        LifePart lifePart = entity.getPart(LifePart.class);
        LifePart lifePart2 = entity2.getPart(LifePart.class);

        // Testing if they get hit
        Assertions.assertTrue(lifePart.isHit());
        Assertions.assertTrue(lifePart2.isHit());
    }

    @Test
    public void testNoCollision() {
        // Creating the first entity
        Entity entity = new Entity();
        entity.setRadius(10);
        entity.add(new PositionPart(0, 0, 0));
        entity.add(new LifePart(3));

        // Creating the second entity
        Entity entity2 = new Entity();
        entity2.setRadius(10);
        entity2.add(new PositionPart(20, 20, 0));
        entity2.add(new LifePart(3));

        // Adding them to the world
        world.addEntity(entity);
        world.addEntity(entity2);

        new CollisionDetector().process(null, world);

        // Getting their x and y coordinates
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);

        System.out.println("Entity 1: " + "X: " + entMov.getX() + ", Y: " + entMov.getY());
        System.out.println("Entity 2: " + "X: " + entMov2.getX() + ", Y: " + entMov2.getY());

        // Getting their life parts to detect collision
        LifePart lifePart = entity.getPart(LifePart.class);
        LifePart lifePart2 = entity2.getPart(LifePart.class);

        // Testing if they get hit
        Assertions.assertFalse(lifePart.isHit());
        Assertions.assertFalse(lifePart2.isHit());
    }
}
