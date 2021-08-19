package nz.ac.wgtn.swen301.assignment2.example;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import nz.ac.wgtn.swen301.assignment2.AppenderMonitor;
import nz.ac.wgtn.swen301.assignment2.MemAppender;

public class LogRunner {
	private Logger logger;
	private AppenderMonitor appenderMonitor;
	private LogEventGenerator logEventGenerator;
	private Timer timer;
	private int secondsElapsed;
	private static final int MAX_SECONDS = 120;
	
	public LogRunner() {
		BasicConfigurator.configure();
		this.logger = Logger.getLogger("Logger");
		this.appenderMonitor = new AppenderMonitor(new MemAppender("Appender"));
		this.secondsElapsed = 0;
	}
	
	public AppenderMonitor getAppenderMonitor() { return appenderMonitor; }
	
	private static String generateRandomMessage() {
		byte[] array = new byte[8];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
	
	private static Priority generateRandomLogLevel() {
		int selection = new Random().nextInt(8);
		switch (selection) {
			case 0:
				return Level.ALL;
			case 1:
				return Level.DEBUG;
			case 2:
				return Level.ERROR;
			case 3:
				return Level.FATAL;
			case 4:
				return Level.INFO;
			case 5:
				return Level.OFF;
			case 6:
				return Level.TRACE;
			default: //So it stops complaining about the lacking return statement.
				return Level.WARN;
		}
	}
	
	private static LoggingEvent generateRandomLogEvent(Logger logger) {
		return new LoggingEvent("Testing_Logger", logger, generateRandomLogLevel(), generateRandomMessage(), null);
	}
	
	public class LogEventGenerator extends TimerTask {
		@Override
		public void run() {
			appenderMonitor.getMemAppender().append(generateRandomLogEvent(logger));
			System.out.println("Log Event Created: Seconds Elapsed: " + secondsElapsed);
			secondsElapsed++;
			if (secondsElapsed >= MAX_SECONDS) {
				timer.cancel();
				timer.purge();
			}
		}
	}
	
	public void scheduleLogEventGenerator() {
		//Every 1 second, run the logEventGenerator.
		timer = new Timer();
		timer.schedule(new LogEventGenerator(), (long)0, 100);
	}
	
    public static void main(String[] args) 
    		throws MalformedObjectNameException, NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException {
    	
    	LogRunner logRunner = new LogRunner();
    	AppenderMonitor monitor = logRunner.getAppenderMonitor();
    	
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    	ObjectName name = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=" + monitor.getObjectName());
    	mbs.registerMBean(monitor, name);
    	
    	//Begin the loop!
    	logRunner.scheduleLogEventGenerator();
    	
    }
}
