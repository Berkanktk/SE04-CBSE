package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {

    /**
     * Pre-condition: The game is running
     * Post-condition: The plugins has been implemented to the game
     * @param gameData Starts processing game data on component install
     * @param world Starts processing world data on component install
     */
    void start(GameData gameData, World world);

    /**
     * Pre-condition: Plugins must be installed in the game
     * Post-condition: Plugins is uninstalled from the game
     * @param gameData Stops processing game data on component uninstall
     * @param world Stops processing world data on component uninstall
     */
    void stop(GameData gameData, World world);
}
