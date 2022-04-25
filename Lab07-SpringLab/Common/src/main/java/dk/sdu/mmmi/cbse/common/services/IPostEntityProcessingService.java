package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.springframework.stereotype.Service;


public interface IPostEntityProcessingService  {
        /**
         * Pre-condition: Entities must exist in the game and
         * Post-condition: Entities in the game should already have processed their logic
         * @param gameData Runs when game data related entities has been processed
         * @param world Runs when world data related entities has been processed.
         *              So collision colliding should be made here
         */
        void process(GameData gameData, World world);
}
