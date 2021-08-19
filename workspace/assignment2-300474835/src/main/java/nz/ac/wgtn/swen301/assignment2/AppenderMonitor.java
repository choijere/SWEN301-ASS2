package nz.ac.wgtn.swen301.assignment2;

/**
 * Originally, the interface was in the MemAppender class.
 * However, the logrunner refused to run, as the class was not compliant to the naming conventions.
 * Therefore, this wrapper class was created, and it runs perfectly.
 * 
 * @author STUDENT#300474835
 *
 */
public class AppenderMonitor implements AppenderMonitorMBean {
	private MemAppender memAppender;
	
	public AppenderMonitor (MemAppender appender) {
		this.memAppender = appender;
	}
	
	@Override
	public String[] getLogs() {
		return memAppender.getLogs();
	}

	@Override
	public long getLogCount() {
		return memAppender.getLogCount();
	}

	@Override
	public long getDiscardedLogCount() {
		return memAppender.getDiscardedLogCount();
	}

	@Override
	public void exportToJSON(String fileName) {
		memAppender.exportToJSON(fileName);
	}
	
	public String getObjectName() {
		return memAppender.getObjectName();
	}
	
	public MemAppender getMemAppender() {
		return memAppender;
	}

}
