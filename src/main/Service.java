package main;

import se.kth.ssvl.tslab.wsn.general.bpf.BPFActionReceiver;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFService;

public class Service implements BPFService {

	public static void main(String args[]) {
		/* Listening mode only */
		if (args.length == 0) {
			
		} else if (args.length == 4) {
			
		} else {
			
		}
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
