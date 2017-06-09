import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBconnect {
	private String getConnect;

	//on create set up getConnect string for connecting to specific schema
	public DBconnect(String schema, String user, String pass) {
		String format = "jdbc:mysql://localhost/%s?user=%s&password=%s";
		getConnect = String.format(format, schema, user, pass);
	}
	//default root : password
	public DBconnect(String schema) {
		String format = "jdbc:mysql://localhost/%s?user=root&password=password";
		getConnect = String.format(format, schema);
	}
	
	public void addRecord(String table, String column, String toAdd) {
		try {
			Connection con = null;
			PreparedStatement statement = null;
			con = DriverManager.getConnection(getConnect);
			String sql = String.format("insert into %s (%s) values (?)", table, column);
			statement = con.prepareStatement(sql);
			statement.setString(1, toAdd);
			statement.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	//overloaded addReccords to take multiple records
	public void addRecord(String table, ArrayList<String> columns, ArrayList<String> toAdds) {
		try {
			Connection con = null;
			PreparedStatement statement = null;
			con = DriverManager.getConnection(getConnect);
			String columnsArray = "";
			String commaArray = "";
			//build strings "string,string,string.." from array lists
			for (int i = 0; i < columns.size(); i++) {
				columnsArray += String.format("%s,", columns.get(i));
				commaArray += String.format("?,", toAdds.get(i));
			}
			//trim the last comma out
			columnsArray = columnsArray.substring(0, columnsArray.length() -1);
			commaArray = commaArray.substring(0, commaArray.length() -1);
			
			//build query
			String sql = String.format("insert into %s (%s) values (%s)", table, columnsArray, commaArray);
			statement = con.prepareStatement(sql);
			
			for (int i = 1; i <= columns.size(); i++){
				statement.setString(i, toAdds.get(i - 1));
			}
			
			statement.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public void updateRecord(String table, String column, String whereColumn, String columnEquals, String toUpdate){
		try{
			Connection con = null;
			PreparedStatement statement = null;
			con = DriverManager.getConnection(getConnect);
			String sql = String.format("update %s set %s = ? where %s = '%s'",table, column, whereColumn, columnEquals);
			statement = con.prepareStatement(sql);
			statement.setString(1, toUpdate);
			statement.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public void deleteRecord(String table, String whereColumn, String toDelete) {
		try {
			Connection con = null;
			PreparedStatement statement = null;
			con = DriverManager.getConnection(getConnect);
			String sql = String.format("delete from %s where %s = ?", table, whereColumn);
			statement = con.prepareStatement(sql);
			statement.setString(1, toDelete);
			statement.executeUpdate();
			
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public ArrayList<ArrayList<String>> getData(String table, String whereColumn, String equals, String... columns){
		ArrayList<ArrayList<String>> results = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			//turns array of Strings into "string,string,string..."
			String colString = "";
			for(String col: columns) colString += col + ",";
			colString = colString.substring(0, colString.length() -1);
			
			//connect to DB and run query
			con = DriverManager.getConnection(getConnect);
			String sql = String.format("select %s from %s where %s = '%s'", colString, table, whereColumn, equals);
			statement = con.prepareStatement("");
			rs = statement.executeQuery(sql);
			
			//while there is a next row
			while(rs.next()){
				ArrayList<String> row = new ArrayList<>();
				//add all columns to row
				for(String col: columns) row.add(rs.getString(col));
				//add row to results
				results.add(row);
			}		
		} catch (Exception e) {e.printStackTrace();}
		return results;
	}

	
	
	
}
