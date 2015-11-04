package ar.tuc.pequecrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

	public Connection conn;
    private Statement statement;
    public static DBConnect db;
    private DBConnect() {
        String url= "jdbc:mysql://www.pequecrack.com.ar:3306/";
        String dbName = "pequecra_wpcrk";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pequecra_adm";
        String password = "TR1306FLM";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    
    
    /**
     *
     * @return DBConnect Database connection object
     */
    public static synchronized DBConnect getDbCon() {
        if ( db == null ) {
            db = new DBConnect();
        }
        return db;
 
    }
    
    
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    
    
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
}
