package repository;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Properties;

public class Database {
    
    public static Connection con;
    
    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
                                    String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        Session session = jsch.getSession(strSshUser, strSshHost, 22);
        session.setPassword(strSshPassword);
        
        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }
    
    
    public void connect() throws Exception{
        if(con != null) return;
        
        try {
            // SSH loging username
            String strSshUser = "236605_admin";
            // SSH login password
            String strSshPassword = "Password1";
            // SSH host
            String strSshHost = "ssh.binero.se";
            // remote SSH host port number
            int nSshPort = 22;
            // hostname
            String strRemoteHost = "my62b.sqlserver.se";
            // local port number use to bind SSH tunnel
            //int nLocalPort = 3306;
            int nLocalPort = 3307;
            // remote port number of your database
            int nRemotePort = 3306;
            doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/236605-candy?user=236605_dj83154&password=password");
            System.out.println("Connected");
            //getAllCreatureCards();
        } catch (ClassNotFoundException e) {
            throw new Exception("No database");
        }
    }
    
    public void close(){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}