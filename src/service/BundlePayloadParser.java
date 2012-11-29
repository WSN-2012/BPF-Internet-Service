package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		Double ps, t, tmcu, vmcu, rh, vin;
		ps = t = tmcu = vmcu = rh = vin = 4.94065645841246544e-324d;//initialize to first double value to indicate null value
		for (int i=0; i<lines.length; i++){
			try{
			//parse timestamp and convert it to Date to be saved as a parameter to Data object
			String timestampstr = lines[i].substring(0, lines[i].indexOf("UT")).trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
			Date utimestamp = null;
			try {
				utimestamp = dateFormat.parse(timestampstr);
			} catch (ParseException e) {
				System.out.println(e.toString());
				return;
			}
			//parse unix time and convert it to Integer to to be saved as a parameter to Data object
			String utstr = lines[i].indexOf("UT")>0 ? lines[i].substring(lines[i].indexOf("UT")+3, lines[i].indexOf(" ",lines[i].indexOf("UT"))).trim() : null;
			BigInteger ut = new BigInteger(utstr);
			
			//parse string
			String id = lines[i].indexOf("ID")>0 ? lines[i].substring(lines[i].indexOf("ID")+3, lines[i].indexOf(" ",lines[i].indexOf("ID"))).trim() : null;
			
			//parse power save indicator and convert it to Double to to be saved as a parameter to Data object
			String psstr = lines[i].indexOf("PS")>0 ? lines[i].substring(lines[i].indexOf("PS")+3, lines[i].indexOf(" ",lines[i].indexOf("PS"))).trim() : null;
			if(psstr!=null) ps = Double.parseDouble(psstr); else ps = 4.94065645841246544e-324d;//check if null before parsing
			
			//parse temperature and convert it to Double to to be saved as a parameter to Data object
			String tstr = lines[i].indexOf(" T=")>0 ? lines[i].substring(lines[i].indexOf("T", lines[i].indexOf("PS"))+2, lines[i].indexOf(" ",(lines[i].indexOf("T", lines[i].indexOf("PS"))))).trim() : null;
			if(tstr!=null) t = Double.parseDouble(tstr); else t = 4.94065645841246544e-324d;//check if null before parsing
			
			//parse t_mcu and convert it to Double to to be saved as a parameter to Data object
			String tmcustr = lines[i].indexOf("T_MCU")>0 ? lines[i].substring(lines[i].indexOf("T_MCU")+6, lines[i].indexOf(" ",lines[i].indexOf("T_MCU"))).trim() : null;
			if(tmcustr!=null) tmcu = Double.parseDouble(tmcustr); else tmcu = 4.94065645841246544e-324d;//check if null before parsing
			
			//parse microcontroler voltage and convert it to Double to to be saved as a parameter to Data object
			String vmcustr = lines[i].indexOf("V_MCU")>0 ? lines[i].substring(lines[i].indexOf("V_MCU")+6, lines[i].indexOf(" ",lines[i].indexOf("V_MCU"))).trim() : null;
			if(vmcustr!=null) vmcu = Double.parseDouble(vmcustr); else vmcu = 4.94065645841246544e-324d;//check if null before parsing
			
			//parse uptime 
			String up = lines[i].substring(lines[i].indexOf("UP")+3, lines[i].indexOf(" ",lines[i].indexOf("UP"))).trim();
			
			//parse relative humidity in % and convert it to Double to to be saved as a parameter to Data object
			String rhstr = lines[i].indexOf("RH")>0 ? (lines[i].indexOf(" ",lines[i].indexOf("RH"))>0 ? lines[i].substring(lines[i].indexOf("RH")+3, lines[i].indexOf(" ",lines[i].indexOf("RH"))).trim() : lines[i].substring(lines[i].indexOf("RH")+3)) : null;
			if(rhstr!=null) rh = Double.parseDouble(rhstr); else rh = 4.94065645841246544e-324d;//check if null before parsing
			
			//parse voltage input and convert it to Double to to be saved as a parameter to Data object
			String vinstr = lines[i].indexOf("V_IN")>0 ? lines[i].substring(lines[i].indexOf("V_IN")+5, lines[i].indexOf(" ",lines[i].indexOf("V_IN"))).trim() : null;
			if(vinstr!=null) vin = Double.parseDouble(vinstr); else vin = 4.94065645841246544e-324d;//check if null before parsing
			
			//System.out.println(timestampstr + " "+ utstr + " "+ idstr + " "+ psstr + " "+ tstr + " "+ tmcustr + " "+ vmcustr + " "+ up + " "+ rhstr + " "+ vinstr);
			data = new Data(utimestamp, ut, t, ps, tmcu, vmcu, up, rh, vin, "garden");
			gateway = SQLQueries.setGateway(gatewayName);
			sensor = SQLQueries.setSensor(id,gatewayName);
			SQLQueries.setData(sensor.getId(), data);
			}catch(Exception e){
				System.out.println(e);
				e.printStackTrace();
			}
		}		
	}
	
	//Test Parser Statically
	/*public static void main (String args[]){
		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile("src/DBParser/sensors_100.dat", "r");
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


