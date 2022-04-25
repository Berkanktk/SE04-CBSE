package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface IEntityProcessingService {

    /**
     * Pre-condition: There should be some entities in the game
     * Post-condition: Entities in the game gets their logic implemented
     * @param gameData Processes game data such as delta and width/height
     * @param world Processes world data such ass vectors in the game
     */
    void process(GameData gameData, World world);
}
