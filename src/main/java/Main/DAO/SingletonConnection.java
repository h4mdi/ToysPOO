package Main.DAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnection {

	 private static Connection connection ;
			  
			  static {
		try {
			Class.forName("org.postgresql.Driver");
//			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/XE?serverTimezone=UTC","postgres","xe");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/toys?serverTimezone=UTC","root","");

			if (connection!=null)
			System.out.println("Connexion établie");

		}
		catch (Exception e) {
			System.out.println("connexion impossible");
			javax.swing.JOptionPane.showMessageDialog(null,"Erreur de connection","Erreur", JOptionPane.ERROR_MESSAGE);
	}
	}public static Connection getConnexion() {return connection;}}

	
	
	
