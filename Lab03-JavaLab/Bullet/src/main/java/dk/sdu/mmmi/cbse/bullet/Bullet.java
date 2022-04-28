package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Bullet extends Entity {
    private int maxDuration = 150;
    private int duration = 0;

    public boolean isExpired() {
        return duration == maxDuration;
    }

    public void increaseDuration() {
        duration++;
    }
}
