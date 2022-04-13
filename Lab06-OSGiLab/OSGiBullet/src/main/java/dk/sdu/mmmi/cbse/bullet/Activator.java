package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {

        BulletProcessor bp = new BulletProcessor();
        context.registerService(IEntityProcessingService.class, bp, null);
        context.registerService(BulletSPI.class, bp, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
