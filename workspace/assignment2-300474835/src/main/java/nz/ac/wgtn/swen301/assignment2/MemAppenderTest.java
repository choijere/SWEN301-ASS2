package nz.ac.wgtn.swen301.assignment2;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class MemAppenderTest {
	final static Logger LOGGER = Logger.getLogger("TEST LOGGER");
	private final static JSONLayout JSON_LAYOUT = new JSONLayout();
	
	@Test
	/**
	 * Test to see if the appender is working as intended
	 */
	public void testConstructor() {
		MemAppender appender = new MemAppender("appender");
		LoggingEvent e = new LoggingEvent("test", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "message", 
				"main", null, null, null, null);
		
		//First, add a logging event.
		appender.append(e);
		
		assertTrue(appender.getCurrentLogs().size() == 1);
		
		//Second, test discarding
		appender.setMaxSize(1);
		appender.append(e);
		assertTrue(appender.getCurrentLogs().size() == 1);
		assertTrue(appender.getDiscardedLogCount() == 1);
	}
	
	@Test
	/**
	 * Test the export to json functionality
	 */
	public void testExportToJson() {
		MemAppender appender = new MemAppender("appender");
		LOGGER.setLevel(Level.ALL);
		LoggingEvent e = new LoggingEvent("test", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "message", 
				"main", null, null, null, null);
		
		for(int i = 0; i < 30; i++) {
			appender.append(e);
		}
		appender.exportToJSON("Test");
		
		File f = new File("Test.json");
		assertTrue(f.exists());
	}
}