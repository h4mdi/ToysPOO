package Main;

import Main.DAO.SingletonConnection;

import java.sql.Connection;

public class Testcnx {

	public static void main(String[] args) {
		Connection con = SingletonConnection.getConnexion();

	}
}
