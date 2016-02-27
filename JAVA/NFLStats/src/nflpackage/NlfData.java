package nflpackage;
import java.time.*;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class NlfData{
    Connection c = null;
  
    /**
     * Constructs an NflData object and creates a connection to be used by further calls.
     * 
     * @return NflData object
     * @throws Exception
     */
    NlfData() throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
            this.c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nfldb",
                    "postgres", "Canon7D");  
        } 
        catch (final Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return;
    }
    
    /**
     * This method returns a list of player full names that can be used by other
     * methods when a player list is required.
     * 
     * @param   startYear LocalDate for the year acquisition starts
     * @param   endYear   LocalDate for the ending year
     * @return  List<String> where each contains the full names (first,last) 
     */
    List<String> GetPlayerList(LocalDate startYear, LocalDate endYear) {
        Statement stmt = null;
        List<String> players = new ArrayList<String>();
        
        try {
             stmt = this.c.createStatement();
             ResultSet rs = stmt.executeQuery( "SELECT full_name FROM player order by full_name asc;" );
             while ( rs.next() ) {
                 
                 String  player = rs.getString("full_name");
                 players.add(player);
              }
              rs.close();
              stmt.close();
              
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
         
        return players;
    }
    
    
    
}
