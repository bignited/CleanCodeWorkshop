package org.example.service;

import java.util.logging.Logger;

public class LoggingService {
    public static void logInfo(String infoMessage, Class<?> sourceClass) {
        Logger.getLogger(sourceClass.getName()).info(infoMessage);
    }
}
