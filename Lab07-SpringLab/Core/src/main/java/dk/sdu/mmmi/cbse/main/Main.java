package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		String packagesToScan = "dk.sdu.mmmi.cbse";

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(packagesToScan);
		ApplicationListener game = context.getBean(ApplicationListener.class);

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Asteroids";
		cfg.width = 500;
		cfg.height = 400;
		cfg.useGL30 = false;
		cfg.resizable = true;
		
		new LwjglApplication(game, cfg);
		
	}
	
}
