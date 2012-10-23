package bpf;

import java.sql.ResultSet;
import java.util.Map;

import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.exceptions.BPFDBException;

public class DB implements BPFDB {

	public void close() {
		
	}

	public int delete(String arg0, String arg1, String[] arg2)
			throws BPFDBException {
		return 0;
	}

	public void execSQL(String arg0) throws BPFDBException {
		
	}

	public boolean init(String arg0) {
		return false;
	}

	public int insert(String arg0, Map<String, Object> arg1)
			throws BPFDBException {
		return 0;
	}

	public ResultSet query(String arg0, String[] arg1, String arg2,
			String[] arg3, String arg4, String arg5, String arg6, String arg7)
			throws BPFDBException {
		return null;
	}

	public int update(String arg0, Map<String, Object> arg1, String arg2,
			String[] arg3) throws BPFDBException {
		return 0;
	}

}
