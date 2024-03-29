package twitter;

import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DAOFactory;
import db.DAOUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DAOFactory;
import db.DAOUtil;

public class KeywordManager {
	private DAOFactory factory;
    
    
    public KeywordManager() {
        factory = DAOFactory.getInstance();
    }
    
    
    private static final String SQL_CREATE = 
    		"INSERT INTO Keywords(word) " +
			" VALUES (?) ";
    
     
    private static final String SQL_GET = 
    		"SELECT word " +
			" FROM Keywords ";
    
    
    private static final String SQL_DELETE = 
    		"DELETE FROM Keywords " +
			" WHERE word = ? ";
    
    
    List<String> getAll() {
        List<String> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {};
        try {
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, SQL_GET, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String keyword = rs.getString("word");
                result.add(keyword);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps, rs);
        }
        return result;
    }
    

    void addKeyword(String keyword) {
    	keyword = keyword.trim().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = factory.getConnection();                               
            Object[] values = {
                keyword
            };
            ps = DAOUtil.prepareStatement(conn, SQL_CREATE, false, values);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps);
        }
    }
    
    
    void deleteKeyword(String keyword) {
    	keyword = keyword.trim().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = factory.getConnection();                               
            Object[] values = {
                keyword
            };
            ps = DAOUtil.prepareStatement(conn, SQL_DELETE, false, values);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps);
        }
    }
}