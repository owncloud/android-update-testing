/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.support.log;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    public static Logger logger = Logger.getLogger(Log.class.getName());
    static Handler fileHandler = null;

    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public static void init() {
        try {
            new File("logs").mkdirs();
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
            fileHandler = new FileHandler("logs/logs_" + timestamp + ".log", 5 * 1024000, 1, true);
            fileHandler.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public synchronized String format(LogRecord logRecord) {
                    return String.format(format,
                            new Date(logRecord.getMillis()),
                            logRecord.getLevel().getLocalizedName(),
                            logRecord.getMessage()
                    );
                }
            });
            logger.setLevel(Level.FINE);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            log(Level.SEVERE, "Exception in FileHandler: " + e.getMessage());
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
