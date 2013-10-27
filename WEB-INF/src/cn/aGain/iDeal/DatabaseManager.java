package cn.aGain.iDeal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("Error loading com.mysql.jdbc.Driver!");
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
			System.out.println("Fail to exectue update \"" + updateStmt + "\" !");
			e.printStackTrace();
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iDeal", "Kirk", "toy456852");
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("Fail to connect jdbc:mysql://localhost:3306/iDeal!");
			e.printStackTrace();
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
				e.printStackTrace();
			}
			this.statement = null;
		}

		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.connection = null;
		}
	}

	/**
	 * Called in main() when deploying the project to create tables.
	 */
	private static void initDatabase() {
		DatabaseManager dm = create();
		dm.update("CREATE TABLE user ( id INT PRIMARY KEY, email VARCHAR(64) UNIQUE );");
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