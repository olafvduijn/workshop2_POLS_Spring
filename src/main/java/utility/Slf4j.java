package utility;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author FeniksBV
 */
public class Slf4j {

    private static final Logger logger = initLogger();

    protected static Logger initLogger() {
        // Verwijder een eventueel bestaand log-bestand
        
        
        File logFile = new File("workshop1_slf4j.log");
        logFile.delete();
        return LoggerFactory.getLogger(Slf4j.class.getName());
    }

    public static Logger getLogger() {
        return logger;
    }

    
}