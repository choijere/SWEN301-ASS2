package nz.ac.wgtn.swen301.assignment2;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.google.gson.JsonObject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class JSONLayoutTest {
	final static Logger LOGGER = Logger.getLogger("TEST LOGGER");
	private final static JSONLayout JSON_LAYOUT = new JSONLayout();
	
	@Test
	/**
	 * Test to see if the Format's return result is the same as Json's toString result.
	 */
	public void testCustomToString() {
		LOGGER.setLevel(Level.ALL);
		
		//The jsonToString result
		LoggingEvent e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		String formatStr = JSON_LAYOUT.jsonToString(jsonObj);
		
		//The expected result
		StringBuilder sb = new StringBuilder();
		sb.append("{ \n");
		sb.append("	\"logger\" 	: " + jsonObj.get("logger") + ",\n");
		sb.append("	\"level\" 	: " + jsonObj.get("level") + ",\n");
		sb.append("	\"starttime\"	: " + jsonObj.get("starttime") + ",\n");
		sb.append("	\"thread\" 	: " + jsonObj.get("thread") + ",\n");
		sb.append("	\"message\" 	: " + jsonObj.get("message") + "\n");
		sb.append("}");
		
		assertTrue(sb.toString().equals(formatStr));
	}
	
	@Test
	/**
	 * Test to see if the Format's return result is the same as Json's toString result.
	 */
	public void testJsonString() {
		LOGGER.setLevel(Level.ALL);
		
		LoggingEvent e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		String formatStr = JSON_LAYOUT.format(e);
		
		assertTrue(jsonObj.toString().equals(formatStr));
	}
	
	@Test
	/**
	 * Test if the LOGGER and the json's return result contains the same LOGGER name.
	 */
	public void testLogger() {
		LOGGER.setLevel(Level.ALL);
		
		LoggingEvent e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("logger").getAsString().equals(LOGGER.getName()));
	}
	
	@Test
	/**
	 * Test to see if the eight levels stored in the json are always the exact same as the logging event.
	 */
	public void testLevel() {
		//Testing ALL
		LOGGER.setLevel(Level.ALL);
		LoggingEvent e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));

		//Testing DEBUG
		LOGGER.setLevel(Level.DEBUG);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing ERROR
		LOGGER.setLevel(Level.ERROR);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing FATAL
		LOGGER.setLevel(Level.FATAL);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing INFO
		LOGGER.setLevel(Level.INFO);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing OFF
		LOGGER.setLevel(Level.OFF);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing TRACE
		LOGGER.setLevel(Level.TRACE);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
		
		//Testing WARN
		LOGGER.setLevel(Level.WARN);
		e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "test", null);
		jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("level").getAsString().equals(LOGGER.getLevel().toString()));
	}
	
	@Test
	/**
	 * Test the time stamp is the same as the starttime stored in the JSON.
	 */
	public void testStartTime() {
		LOGGER.setLevel(Level.ALL);
		long timeStamp = System.currentTimeMillis();
		
		LoggingEvent e = new LoggingEvent("test1", LOGGER, timeStamp, LOGGER.getLevel(), "test", null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		
		assertTrue(jsonObj.get("starttime").getAsLong() == timeStamp);
	}
	
	@Test
	/**
	 * Test to see if the thread name is the same as the one stored in the JSON.
	 */
	public void testThread() {
		LOGGER.setLevel(Level.ALL);
		
		LoggingEvent e = new LoggingEvent("test", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), "firin' up test2", 
				"main", null, null, null, null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		
		System.out.println(jsonObj);
		
		assertTrue(jsonObj.get("thread").getAsString().equals("main"));
	}
	
	@Test
	/**
	 * Test to see if the message sent to the logging Event is the same as the one stored in the JSON.
	 */
	public void testMessage() {
		LOGGER.setLevel(Level.ALL);
		String testMessage = "S3RL";
		
		LoggingEvent e = new LoggingEvent("test1", LOGGER, System.currentTimeMillis(), LOGGER.getLevel(), testMessage, null);
		JsonObject jsonObj = JSON_LAYOUT.parseJson(e);
		assertTrue(jsonObj.get("message").getAsString().equals(testMessage));
	}
	
}
