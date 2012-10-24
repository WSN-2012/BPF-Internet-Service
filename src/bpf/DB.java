package bpf;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.exceptions.BPFDBException;

public class DB implements BPFDB {

	private static final String TAG = "DB";
	
	private Connection connection = null;
	private Logger logger;
	
	public DB(File dbFile, Logger _logger) throws FileNotFoundException {
		if (!dbFile.exists()) {
			throw new FileNotFoundException();
		}
		
		// Continue to load the SQLite driver
	    try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    
	    try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    // Init the logger
	    logger = _logger;
	    logger.debug(TAG, "The DB class has been initialized properly");
	}

	
	private String getSQLFromMapKey(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder(100);
		int c = 0;
		for (String key : map.keySet()) {
			if (c < map.size()) {
				sql.append(String.format("{0}, ", key));
			} else {
				sql.append(String.format("{0}", key));
			}
		}
		
		return sql.toString();
	}
	
	private String getSQLFromMapValue(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder(100);
		int c = 0;
		for (Object value : map.values()) {
			if (c < map.size()) {
				sql.append(String.format("{0}, ", value.toString()));
			} else {
				sql.append(String.format("{0}", value.toString()));
			}
		}
		
		return sql.toString();
	}
	
	
	/* *************************** */
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int delete(String table, String whereClause, String[] whereArgs)
			throws BPFDBException {

		PreparedStatement statement = null;

		try {
			if (whereClause != null && !whereClause.isEmpty()) {
				statement = connection.prepareStatement(String.format(
						"DELETE FROM {0} WHERE {1}", table, whereClause));
				for (int i=0; i < whereArgs.length; i++) {
					statement.setString(i + 1, whereArgs[i]);
				}
			} else {
				statement = connection.prepareStatement(String.format(
						"DELETE FROM {0}", table));
			}
			logger.debug(TAG, String.format("Deleting with SQL: {0}", statement));

			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public void execSQL(String sql) throws BPFDBException {
		try {
			Statement statement = connection.createStatement();
			logger.debug(TAG, String.format("Executing SQL: {0}", sql));
			statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(String table, Map<String, Object> values)
			throws BPFDBException {
				
		PreparedStatement statement = null;
		StringBuffer sql = new StringBuffer(150);
		
		// Add the basics to the sql
		sql.append(String.format("INSERT INTO {0} (", table));
		
		// Add the column names
		sql.append(getSQLFromMapKey(values));
		
		// Add the values
		sql.append(") VALUES (");
		sql.append(getSQLFromMapValue(values));
		sql.append(")");
		
		logger.debug(TAG, String.format("INSERT SQL: {0}", sql.toString()));
		
		try {
			statement = connection.prepareStatement(sql.toString());
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new BPFDBException(
					String.format("Unable to insert the new row, reason: {0}",
							e.getMessage()));
		}
	}

	public ResultSet query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) throws BPFDBException {
		
		
		
		return null;
	}

	public int update(String table, Map<String, Object> values, String where,
			String[] whereArgs) throws BPFDBException {
		return 0;
	}
}
