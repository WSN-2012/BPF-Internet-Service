package bpf;

import jlibs.core.lang.Ansi;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;

public class Logger implements BPFLogger {

	public Logger() {
	}

	private String log(String text, Ansi ansi) {
		ansi.out(text + "\n");
		return text;
	}

	public String debug(String TAG, String text) {
		return log("[DEBUG] " + TAG + ": " + text, new Ansi(
				Ansi.Attribute.BRIGHT, Ansi.Color.CYAN, null));
	}

	public String error(String TAG, String text) {
		return log("[ERROR] " + TAG + ": " + text, new Ansi(
				Ansi.Attribute.BRIGHT, Ansi.Color.RED, null));
	}

	public String info(String TAG, String text) {
		return log("[INFO] " + TAG + ": " + text, new Ansi(
				Ansi.Attribute.BRIGHT, Ansi.Color.GREEN, null));
	}

	public String warning(String TAG, String text) {
		return log("[WARN] " + TAG + ": " + text, new Ansi(
				Ansi.Attribute.BRIGHT, Ansi.Color.YELLOW, null));
	}

}
