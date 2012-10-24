package bpf;

import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;

public class Logger implements BPFLogger {

	private String log(String text) {
		System.out.println(text);
		return text;
	}
	
	public String debug(String TAG, String text) {
		return log(String.format("[DEBUG] {0}:{1}", TAG, text));
	}

	public String error(String TAG, String text) {
		return log(String.format("[ERROR] {0}:{1}", TAG, text));
	}

	public String info(String TAG, String text) {
		return log(String.format("[INFO] {0}:{1}", TAG, text));
	}

	public String warning(String TAG, String text) {
		return log(String.format("[WARN] {0}:{1}", TAG, text));
	}
	
}
