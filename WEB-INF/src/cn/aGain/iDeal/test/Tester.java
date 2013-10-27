package cn.aGain.iDeal.test;

import cn.aGain.iDeal.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tester {
	public static void main(String[] args) {
		testDatabaseManager();
	}

	public static void testDatabaseManager() {
		DatabaseManager dm = DatabaseManager.create();

		try {
			ResultSet rs = dm.query("SELECT * FROM user;");
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("Fail to exectue query !");
			e.printStackTrace();
		}

		dm.close();
	}
}