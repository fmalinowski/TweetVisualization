package edu.ucsb.ns202.offline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class MySQLAccessor {
	
	private String username;
	private String password;
	private String database;
	private String dbTable;
	private String hashtagColumn = "hashtags";
	
	private Connection connect;
	private Statement statement;
	private ResultSet resultSet;
	
	public MySQLAccessor(String username, String password, String database) {
		this.username = username;
		this.password = password;
		this.database = database;
	}
	
	public void connect() {
		String connectionString;
		
		connectionString = "jdbc:mysql://localhost/";
		connectionString += this.database;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection(connectionString, this.username, this.password);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectDBTable(String dbTable) {
		this.dbTable = dbTable;
	}
	
	public void queryRelatedHashtags(String hashtag) {
		String sqlQuery;
		
		sqlQuery = "SELECT " + this.hashtagColumn + " FROM " + this.database+ "." + this.dbTable + " ";
		sqlQuery += "WHERE UPPER(" + this.hashtagColumn + ") LIKE UPPER('%" + hashtag + "%');";
		try {
			this.statement = connect.createStatement();
			
			this.resultSet = this.statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean hasNext() {
		try {
			return this.resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String[] getHashtags() {
		String[] hashtagsArray;
		String hashtagsString;
		
		try {
			hashtagsString = this.resultSet.getString(this.hashtagColumn);
			return hashtagsString.split(" ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void close() {
		try {
			this.resultSet.close();
			this.statement.close();
			this.connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
