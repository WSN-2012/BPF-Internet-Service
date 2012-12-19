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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import database.Data;
import database.Gateway;
import database.SQLQueries;
import database.Sensor;

public class BundlePayloadParser {

	private byte[] buffer;

	public BundlePayloadParser(byte[] buffer) {
		super();
		this.buffer = buffer;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public void dataparser(String gatewayName){
		String lines[] = new String(buffer).split("\\r?\\n");
		Data data;
		Gateway gateway;
		Sensor sensor;
		Map<String, String> mdata;
		for (int i=0; i<lines.length; i++){
			try{
				//HashMap to contain data types and values for a specific sensor	
				mdata = new HashMap<String, String>();
				
				Date utimestamp = null;//timestamp of data to be sent
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
				//parse timestamp and convert it to Date to be saved as a parameter to Data object
				if(lines[i].indexOf("UT=") != -1){//if data contain info about timestamp parse them
					String timestampstr = lines[i].substring(0, lines[i].indexOf("UT")).trim();
					try {
						utimestamp = dateFormat.parse(timestampstr);
					} catch (ParseException e) {
						System.out.println(e.toString());
						return;
					}
				}
				
				//parse unix time and store it to map object
				if(lines[i].indexOf("UT")>0){
					mdata.put("ut",lines[i].substring(lines[i].indexOf("UT")+3, lines[i].indexOf(" ",lines[i].indexOf("UT"))).trim());
				}
				
				//parse id 
				String id;
				if(lines[i].indexOf("ID")>0){
					id = lines[i].substring(lines[i].indexOf("ID")+3, lines[i].indexOf(" ",lines[i].indexOf("ID"))).trim();
				}
				else{
					id = "No id";
				}
					
				//parse power save indicator and store it to map object
				if(lines[i].indexOf("PS")>0){
					mdata.put("ps",lines[i].substring(lines[i].indexOf("PS")+3, lines[i].indexOf(" ",lines[i].indexOf("PS"))).trim());
				}
				
				//parse temperature and store it to map object
				if(lines[i].indexOf(" T=")>0){
					mdata.put("t",lines[i].substring(lines[i].indexOf("T", lines[i].indexOf("PS"))+2, lines[i].indexOf(" ",(lines[i].indexOf("T", lines[i].indexOf("PS"))))).trim());
				}
				
				//parse t_mcu and store it to map object
				if(lines[i].indexOf("T_MCU")>0){
					mdata.put("t_mcu",lines[i].substring(lines[i].indexOf("T_MCU")+6, lines[i].indexOf(" ",lines[i].indexOf("T_MCU"))).trim());
				}
				
				//parse microcontroler voltage and store it to map object
				if(lines[i].indexOf("V_MCU")>0){
					mdata.put("v_mcu",lines[i].substring(lines[i].indexOf("V_MCU")+6, lines[i].indexOf(" ",lines[i].indexOf("V_MCU"))).trim());
				}
				
				//parse uptime and store it to map object
				if(lines[i].indexOf("UP")>0){
					mdata.put("up",lines[i].substring(lines[i].indexOf("UP")+3, lines[i].indexOf(" ",lines[i].indexOf("UP"))).trim());
				}
				
				//parse relative humidity in % and store it to map object
				if(lines[i].indexOf("RH")>0){
					mdata.put("rh",(lines[i].indexOf(" ",lines[i].indexOf("RH"))>0 ? lines[i].substring(lines[i].indexOf("RH")+3, lines[i].indexOf(" ",lines[i].indexOf("RH"))).trim() : lines[i].substring(lines[i].indexOf("RH")+3)));
				}
				
				//parse voltage input and store it to map object
				if(lines[i].indexOf("V_IN")>0){
					mdata.put("v_in",lines[i].substring(lines[i].indexOf("V_IN")+5, lines[i].indexOf(" ",lines[i].indexOf("V_IN"))).trim());
				}
				
				if(mdata.isEmpty()){
					mdata.put("general", lines[i]);
				}
				
				if(id!="No id"){
					data = new Data(utimestamp, "garden", mdata);
				}else{
					data = new Data(utimestamp, "No sensor name", mdata);
				}
				gateway = SQLQueries.setGateway(gatewayName);
				sensor = SQLQueries.setSensor(id, data.getSensorName(),gatewayName);
				SQLQueries.setData(sensor.getId(), data);
			}catch(Exception e){
				System.out.println(e);
				e.printStackTrace();
			}
		}		
	}

	// Test Parser Statically
	/*public static void main(String args[]) {
		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile("src/service/general.dat", "r");
			byte[] buffer = new byte[(int) f.length()];
			f.read(buffer);
			BundlePayloadParser b = new BundlePayloadParser(buffer);
			b.dataparser("dtn://natty.dtn");
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
			return;
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		} finally {
			try {
				f.close();
			} catch (IOException e) {
				System.out.println(e.toString());
				return;
			}
		}
	}*/

}
