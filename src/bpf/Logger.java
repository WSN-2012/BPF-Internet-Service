package bpf;

import jlibs.core.lang.Ansi;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;

public class Logger implements BPFLogger {

	private int logLevel;
	
	public Logger(int logLevel) {
		this.logLevel = logLevel;
	}

	private String log(String text, Ansi ansi) {
		ansi.out(text + "\n");
		return text;
	}

	public String debug(String TAG, String text) {
		if (logLevel >= 3) {
			return log("[DEBUG] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.CYAN, null));
		} else {
			return "[DEBUG] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String info(String TAG, String text) {
		if (logLevel >= 2) {
			return log("[INFO] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.GREEN, null));
		} else {
			return "[INFO] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String warning(String TAG, String text) {
		if (logLevel >= 1) {
			return log("[WARN] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.YELLOW, null));
		} else {
			return "[WARN] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String error(String TAG, String text) {
		if (logLevel >= 0) {
			return log("[ERROR] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.RED, null));
		} else {
			return "[ERROR] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}
}
