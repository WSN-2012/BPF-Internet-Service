package main;

import bpf.Logger;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFActionReceiver;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFService;

public class Service implements BPFService {
	
	private static String TAG = "Service";
	
	private Logger logger; 

	public Service(String args[]) {
		// Init some stuff first
		init();
		
		if (args.length == 0) {
			logger.debug(TAG, "No argmunets means listening mode");
		} else if (args.length == 4) {
			
		} else {
			
		}
	}
	
	private void init() {
		logger = new Logger();
	}

	public BPFCommunication getBPFCommunication() {
		return null;
	}

	public BPFDB getBPFDB() {
		return null;
	}

	public BPFLogger getBPFLogger() {
		return null;
	}

	public BPFActionReceiver getBPFActionReceiver() {
		return null;
	}
	
}
