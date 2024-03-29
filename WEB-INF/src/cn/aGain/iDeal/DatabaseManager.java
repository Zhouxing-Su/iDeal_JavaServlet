package cn.aGain.iDeal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Use create() to get this object and close() after finish.
 * 
 * @author Kirk
 */
public class DatabaseManager {
	static final String driverName = "com.mysql.jdbc.Driver";
	static final String dbName = "iDeal";
	static final String dbUrl = "jdbc:mysql://localhost:3306/iDeal";
	static final String dbUser = "Kirk";
	static final String dbPassword = "toy456852";

	private Connection connection;
	private Statement statement;

	static { // load the MySQL JDBC driver.
		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			System.err.println("Error loading " + driverName + " !");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		initDatabase();
	}

	/**
	 * Execute update to database (exceptions will be caught).
	 * 
	 * @param updateStmt
	 *            a SQL statement in string
	 */
	public void update(String updateStmt) {
		try {
			this.statement.executeUpdate(updateStmt);
		} catch (Exception e) {
			System.err.println("Fail to exectue update \"" + updateStmt + "\" !");
			//e.printStackTrace();
		}
	}

	/**
	 * Execute query to database (throw exceptions)
	 * 
	 * @param queryStmt
	 *            a SQL statement in string
	 * @return result set
	 * @throws SQLException
	 */
	public ResultSet query(String queryStmt) throws SQLException {
		return this.statement.executeQuery(queryStmt);
	}

	/**
	 * Connect to MySQL database and create a statement.
	 * <p>
	 * MUST call close() to release the resources allocated by this method
	 * 
	 * @return a DatabaseManager object for database operation
	 */
	public static DatabaseManager create() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.err.println("Fail to connect " + dbUrl + " !");
			//e.printStackTrace();
			return null;
		}

		return new DatabaseManager(conn, stmt);
	}

	/**
	 * Close connection and statement.
	 * <p>
	 * MUST be called after the operation to database
	 */
	public void close() {
		if (this.statement != null) {
			try {
				this.statement.close();
			} catch (Exception e) {
				System.err.println("Fail to close DatabaseManager.stmt !");
				//e.printStackTrace();
			}
			this.statement = null;
		}

		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				System.err.println("Fail to close DatabaseManager.coonection !");
				//e.printStackTrace();
			}
			this.connection = null;
		}
	}

	/**
	 * Called in main() when deploying the project to create tables.
	 */
	private static void initDatabase() {
		DatabaseManager dm = create();

		// create user table
		dm.update(
				"CREATE TABLE user ( "
						+ "id INT PRIMARY KEY, "
						+ "name VARCHAR(32)"
						+ "email VARCHAR(32) UNIQUE, "
						+ "password VARCHAR(32), "
						+ "rank INT "
						+ ");");
		// create idea table
		dm.update(
				"CREATE TABLE idea ( "
						+ "id INT PRIMARY KEY, "
						+ "name VARCHAR(64), "
						+ "requirment VARCHAR(1024), "
						+ "example VARCHAR(1024), "
						+ "keyword VARCHAR(64), "
						+ "lastCommit DATETIME"
						+ ");");
		// create developer group table
		dm.update(
				"CREATE TABLE group ( "
						+ "id INT PRIMARY KEY, "
						+ "name VARCHAR(64), "
						+ "repoURL VARCHAR(256), "
						+ "qqGroup VARCHAR(16), "
						+ "description VARCHAR(64), "
						+ "foundTime DATETIME"
						+ ");");
		// create user concern idea table
		dm.update(
				"CREATE TABLE UserConcernIdea ( "
						+ "userid INT, "
						+ "ideaid INT ,"
						+ "PRIMARY KEY (userid,ideaid)"
						+ ");");
		// create user write idea draft table
		dm.update(
				"CREATE TABLE IdeaDraft ( "
						+ "userid INT, "
						+ "ideaid INT ,"
						+ "PRIMARY KEY (userid,ideaid)"
						+ ");");
		// create user write idea comment table
		dm.update(
				"CREATE TABLE IdeaComment ( "
						+ "userid INT, "
						+ "ideaid INT ,"
						+ "comment VARCHAR(256),"
						+ "PRIMARY KEY (userid,ideaid)"
						+ ");");
		// create user join group table
		dm.update(
				"CREATE TABLE UserJoinGroup ( "
						+ "userid INT, "
						+ "groupid INT ,"
						+ "role VARCHAR(32), "
						+ "PRIMARY KEY (userid,groupid)"
						+ ");");
		// create group implement idea table
		dm.update(
				"CREATE TABLE GroupImplementIdea ( "
						+ "groupid INT ,"
						+ "ideaid INT, "
						+ "process VARCHAR(1024), "
						+ "isFinished BOOLEAN,"
						+ "PRIMARY KEY (groupid,ideaid)"
						+ ");");

		dm.close();
	}

	/**
	 * use the factory method create() instead of this constructor
	 * 
	 * @param conn
	 * @param stmt
	 */
	private DatabaseManager(Connection conn, Statement stmt) {
		this.connection = conn;
		this.statement = stmt;
	}
}