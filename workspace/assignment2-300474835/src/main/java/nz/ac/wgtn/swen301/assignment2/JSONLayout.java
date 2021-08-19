package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.JsonObject;

/**
 *
 * Google "Custom Layout"
 *
 * https://www.wideskills.com/log4j-tutorial/10-custom-appender-and-layout-in-log4j
 * 
 * @author Student#300474835
 *
 */
public class JSONLayout extends Layout {
	@Override
	public String format(LoggingEvent event) {
		return parseJson(event).toString();
	}
	
	/**
	 * Custom method. 
	 * Formats an event, but returns a JSON object instead of a string.
	 * @param event
	 * @return
	 */
	public JsonObject parseJson(LoggingEvent event) {
		JsonObject obj = new JsonObject();

		obj.addProperty("logger", event.getLoggerName());
		obj.addProperty("level", event.getLevel().toString());
		obj.addProperty("starttime", event.getTimeStamp());
		obj.addProperty("thread", event.getThreadName());
		obj.addProperty("message", event.getMessage().toString());
		
		return obj;
	}
	
	/**
	 * Parses a string with better identation.
	 * Made with the express purpose to print into the console
	 * for testing purposes.
	 * @param obj
	 * @return
	 */
	public String jsonToString(JsonObject obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \n");
		sb.append("	\"logger\" 	: " + obj.get("logger") + ",\n");
		sb.append("	\"level\" 	: " + obj.get("level") + ",\n");
		sb.append("	\"starttime\"	: " + obj.get("starttime") + ",\n");
		sb.append("	\"thread\" 	: " + obj.get("thread") + ",\n");
		sb.append("	\"message\" 	: " + obj.get("message") + "\n");
		sb.append("}");
		
		return sb.toString();
	}

	//Unimportant:
	@Override
	public void activateOptions() {}
	

	@Override
	public boolean ignoresThrowable() {
		return false;
	}
	
	
}
