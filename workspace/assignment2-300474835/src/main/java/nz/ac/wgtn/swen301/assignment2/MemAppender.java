package nz.ac.wgtn.swen301.assignment2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.JsonObject;

/**
 * This "memory" appender remembers LoggingEvents.
 * The "Append" function appends new LoggingEvents to the existing list of LoggingEvents.
 * @author Student#300474835
 *
 */
public class MemAppender extends AppenderSkeleton {
	private long maxSize;
	private long discardedLogCount;
	private List<LoggingEvent> logEvents;
	
	public MemAppender(String name) {
		this.name = name;
		this.maxSize = 1000;
		this.discardedLogCount = 0;
		this.logEvents = new ArrayList<>();
	}
	
	@Override
	public void append(LoggingEvent event) {
		//Check size
		if (logEvents.size() >= maxSize) {
			//Discard oldest one.
			logEvents.remove(0);
			discardedLogCount++;
		}
		logEvents.add(event);
	}
	
	public List<LoggingEvent> getCurrentLogs() {
		return Collections.unmodifiableList(logEvents);
	}
	
	public void setMaxSize(long newSize) {
		maxSize = newSize;
	}
	
	
	//MBeans functions:
	public String[] getLogs() {
		return logEvents.toArray(new String[logEvents.size()]);
	}

	public long getLogCount() {
		return logEvents.size();
	}
	
	public long getDiscardedLogCount() {
		return discardedLogCount;
	}
	
	public void exportToJSON(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName + ".json");
			JSONLayout layout = new JSONLayout();
			
			writer.write("[\n");
			for(int i = 0; i < logEvents.size(); i++) {
				LoggingEvent e = logEvents.get(i);
				JsonObject obj = layout.parseJson(e);
				writer.write(layout.jsonToString(obj));
				
				if(i != logEvents.size()-1)
					writer.write(",\n");
			}
			writer.write("]\n");
			
			writer.flush();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Unimportant:
	@Override
	public void close() {}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	public String getObjectName() {
		return "Monitor_of_" + this.getName();
	}

}
