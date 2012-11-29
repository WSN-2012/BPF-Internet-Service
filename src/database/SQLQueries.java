package database;

import java.util.List;


public class SQLQueries {
	private static String mConnectionURL = "jdbc:postgresql://localhost:5432/serverdb";
	private static String mUser = "postgres";
	private static String mPassword = "postgres";
	private static String mDriver = "org.postgresql.Driver";
	
	public static User login(String username, String password)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a user instance
		User user = database.login(username, password);
		database.Close();
		return user;

	}
	
	public static User register(String username, String password, String email, String name)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, password, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a user instance
		User user = database.register(username, password, email, name);
		database.Close();
		return user;

	}
	
	//Check if user already exists in database
	public static boolean usernameExistance(String username)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a boolean indicating user existence 
		boolean flag = database.exist(username);
		database.Close();
		return flag;

	}
	
	//Change user account settings without password field
	public static User changeAccountSettings(String name, String oldUsername, String newUsername, String email)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
			
		//Open the database connection
		database.Open();
			
		//call changeAccountSettings method in the DB 
		User user = database.changeAccountSettings(name, oldUsername, newUsername, email);
		database.Close();
		return user;
	}
	
	//Change user account settings with password field
	public static User changeAccountSettings(String name, String oldUsername, String newUsername, String email, String password)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, password, mDriver);
					
		//Open the database connection
		database.Open();
				
		//call changeAccountSettings method in the DB 
		User user = database.changeAccountSettings(name, oldUsername, newUsername, email,password);
		database.Close();
		return user;
	}
	
	
	
	public static List<Gateway> getAllGateway()
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Gateway> listGateway =  database.getAllGateways();
		database.Close();
		return listGateway;

	}
	
	public static List<Sensor> getAllSensor(int gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Sensor> listGateway =  database.getAllSensors(gatewayID);
		database.Close();
		return listGateway;

	}
	
	public static Gateway getGateway(int gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		Gateway gateway =  database.getGateway(gatewayID);
		database.Close();
		return gateway;

	}
	
	public static Gateway setGateway(String gatewayName)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		Gateway gateway =  database.setGateway(gatewayName);
		database.Close();
		return gateway;

	}
	
	public static Sensor setSensor(String sensorID, String gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		Sensor sensor =  database.setSensor(sensorID, gatewayID);
		database.Close();
		return sensor;

	}
	
	public static List<Data> getSensorData(String sensorID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Data> listData =  database.getSensorData(sensorID);
		database.Close();
		return listData;

	}
	
	public static List<Data> getAllData()
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Data> listData =  database.getAllData();
		database.Close();
		return listData;

	}
	
	public static void setData(String id, Data data)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		database.setData(id, data);
		database.Close();

	}
}
