package zavrsni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Recnik {

	private HashMap<String, ArrayList<String>> recnik;
	private String connString = "jdbc:sqlite:Dictionary.db";

	public Recnik() {
		recnik = new HashMap<String, ArrayList<String>>();
		napraviMissingTabelu();
	}

	private void napraviMissingTabelu() {
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm = con.createStatement();
			
			String upit = "create table if not exists missing(word varchar(25))";
			stm.executeUpdate(upit);
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void popuniRecnik() {
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm = con.createStatement();
			String upit = "select word, definition from entries";
			ResultSet rezultat = stm.executeQuery(upit);
			while (rezultat.next()) {
				String rec = rezultat.getString("word").toLowerCase();
				ArrayList<String> definitions = recnik.get(rec);
				if (definitions == null) {
					definitions = new ArrayList<String>();
					recnik.put(rec, definitions);
				}
				definitions.add(rezultat.getString("definition"));
			}
			rezultat.close();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dodajNovuRec(String s) {
		s = s.toLowerCase();
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm1 = con.createStatement();
	
			String upit1 = "select word from missing where word = '" + s + "'";
			ResultSet rezultat = stm1.executeQuery(upit1);

			if (rezultat.next() == false) {
	
				Statement stm2 = con.createStatement();
				String upit2 = "insert into missing (word) values ('" + s + "')";
				stm2.executeUpdate(upit2);
				stm2.close();
			}
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean nadjiRec(String s) {
		s = s.toLowerCase();
		if (recnik.containsKey(s))
			return true;
		else
			return false;
	}

}
