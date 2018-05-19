package io.github.shaquu.memory.game.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
    private static final Logger LOGGER = Logger.getLogger( GameLogger.class.getName() );

    public GameLogger(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new SimpleFormatter());

        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);
    }

    public static void log(String message){
        LOGGER.log( Level.FINE, message);
    }
}
