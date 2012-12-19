/*
 * Copyright 2012 KTH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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
		if (args.length == 3) {
			init(args);
			logger.info(TAG, "No argmunets means listening mode");
		} else if (args.length == 4) {
			init(args);
			try {
				BPF.getInstance().send(new DTNEndpointID(args[3]), 10000000,
						"TEEEST".getBytes());
			} catch (DTNOpenException e) {
				logger.error(TAG,
						"There was an error when trying to send the bundle");
				e.printStackTrace();
			}
			logger.info(TAG, "Arguments passed trying to send");
		} else {
			usage();
			System.exit(-1);
		}

		// start thread to wait for kill signal so that we can stop the BPF
		// before dying
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.debug(TAG, "Shutdown signal received, going to stop BPF...");
				BPF.getInstance().stop();
				logger.info(TAG, "Stopping service now");
			}
		});
	}

	private void init(String args[]) {
		// Init a logger first of all
		logger = new Logger(Integer.parseInt(args[1]), args[2]);

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
			logger.error(TAG,
					"Couldn't initialize the BPF, exception: " + e.getMessage());
			System.exit(-1);
		}

	}

	private void usage() {
		// System.out.println("config-file-path <dest eid> <source eid> <payload type> <payload> \n payload type: <f|m> \n payload: <filename|double quoted message>");
		System.out.println("config-file-path log-file <dest eid>");
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
