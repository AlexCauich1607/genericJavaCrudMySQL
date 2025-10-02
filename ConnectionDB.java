import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionDB {

    private final String base = "database";
    private final String user = "root";
    private final String password = "password";
    private final String url = "jdbc:mysql://localhost:3306/" + base;
    private Connection con = null;

    public Connection getConnection() {
        try {   
            con = DriverManager.getConnection(this.url,this.user, this.password);
        }catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}





