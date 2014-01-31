import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

appender("STDOUT", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		//	pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
		pattern = "%-5level %logger{36} - %msg%n"
	}
}

//root(ALL, ["STDOUT"])
//root(DEBUG, ["STDOUT"])
//root(INFO, ["STDOUT"])
root(WARN, ["STDOUT"])

// control jaudiotagger output
logger("org.jaudiotagger", ERROR)
