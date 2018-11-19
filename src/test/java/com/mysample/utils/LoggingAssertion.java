package com.mysample.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Works with logback-spring.xml under /test/resources.
 */
public class LoggingAssertion {

    public static Appender givenLoggingMonitored() {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        final Appender<ILoggingEvent> mockAppender = mock(Appender.class);
        root.addAppender(mockAppender);

        return mockAppender;
    }

    public static LogAssertion assertLogging(Appender appender) {
        return new LogAssertion(appender);
    }

    public static class LogAssertion {
        private List<ILoggingEvent> logEvents;

        public LogAssertion(Appender appender) {
            ArgumentCaptor<ILoggingEvent> argument = ArgumentCaptor.forClass(ILoggingEvent.class);
            verify(appender, atLeastOnce()).doAppend(argument.capture());
            logEvents = argument.getAllValues();
        }

        public LogAssertion hasMessage(String logMessage) {
            logEvents = logEvents.stream()
                    .filter(o -> o.getFormattedMessage().contains(logMessage))
                    .collect(Collectors.toList());

            if (logEvents.isEmpty()) {
                fail(String.format("Log message '%s' not found", logMessage));
            }
            return this;
        }

        public LogAssertion hasMDCEntry(String key, String value) {
            logEvents = logEvents.stream()
                    .filter(o -> value.equals(o.getMDCPropertyMap().get(key)))
                    .collect(Collectors.toList());

            if (logEvents.isEmpty()) {
                fail(String.format("MDC entry for %s not found", key));
            }
            return this;
        }
    }
}
