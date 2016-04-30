/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Preprosesing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Komando
 */
public class toSql {
    Statement statmen;
    public toSql(){
        try { 
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://127.0.0.1/db_svm", "root", "");
            statmen = koneksi.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Databse-Off", "erorr", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Databse-Off", "erorr", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    public ResultSet getTable(String q) throws SQLException{
        return statmen.executeQuery(q);
    }
    public boolean updateTable(String q) throws SQLException{
        return statmen.execute(q);
    }
    public boolean isExists(String q) throws SQLException{
        ResultSet rs = statmen.executeQuery(q);
        int i=0;
        while(rs.next())
            i++;
        return (i!=0);
    }
}
