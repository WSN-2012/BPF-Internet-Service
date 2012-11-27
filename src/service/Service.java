package service;

import java.io.File;

import se.kth.ssvl.tslab.wsn.general.bpf.BPF;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFActionReceiver;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFService;
import se.kth.ssvl.tslab.wsn.general.bpf.exceptions.BPFException;
import se.kth.ssvl.tslab.wsn.general.dtnapi.exceptions.DTNOpenException;
import se.kth.ssvl.tslab.wsn.general.dtnapi.types.DTNEndpointID;
import se.kth.ssvl.tslab.wsn.general.servlib.storage.Stats;
import bpf.ActionReceiver;
import bpf.Communication;
import bpf.DB;
import bpf.Logger;

public class Service implements BPFService {
	
	private static String TAG = "Service";
	
	private Logger logger; 
	private ActionReceiver action;
	private Communication comm;
	private DB db;
	
	public static void main(String args[]) {
		new Service(args);
	}
	
	public Service(String args[]) {
		if (args.length == 2) {
			init(args);
			logger.info(TAG, "No argmunets means listening mode");
		} else if (args.length == 3) {
			init(args);
			try {
				BPF.getInstance().send(new DTNEndpointID(args[2]), 10000000, "TEEEST".getBytes());
			} catch (DTNOpenException e) {
				logger.error(TAG, "There was an error when trying to send the bundle");
				e.printStackTrace();
			}
			logger.info(TAG, "Arguments passed trying to send");
		} else {
			usage();
			System.exit(-1);
		}
	}
	
	private void init(String args[]) {
		// Init a logger first of all
		logger = new Logger(Integer.parseInt(args[1]));
		
		// Init the action receiver
		action = new ActionReceiver(logger);
		
		// Init the communications object
		comm = new Communication();
		
		// Init the DB object
		db = new DB(new File("build/database.db"), logger);

		// Try to init the BPF
		try {
			BPF.init(this, args[0]);
			BPF.getInstance().start();
		} catch (BPFException e) {
			logger.error(TAG, "Couldn't initialize the BPF, exception: " + e.getMessage());
			System.exit(-1);
		}
		
	}
	
	private void usage() {
		//System.out.println("config-file-path <dest eid> <source eid> <payload type> <payload> \n payload type: <f|m> \n payload: <filename|double quoted message>");
		System.out.println("config-file-path <dest eid>");
	}

	/* ***************************** */
	
	public BPFCommunication getBPFCommunication() {
		return comm;
	}

	public BPFDB getBPFDB() {
		return db;
	}

	public BPFLogger getBPFLogger() {
		return logger;
	}

	public BPFActionReceiver getBPFActionReceiver() {
		return action;
	}

	public void updateStats(Stats arg0) {
		
	}
}
