package nflpackage;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class PlayerStats {
    static Connection c = null;
    
    private HashMap<String,WeeklyStat> weeklyStats = new HashMap<String,WeeklyStat>();
    
    //private List<WeeklyStat> weeklyStats = new ArrayList<WeeklyStat>();
    
    // FIX ME: 
    // Hard code for now.  Should become a set and user should express which 
    // point protocol should be used.
    static double PassingPointsPerYard = 0.04;
    static double PassingPointsPerTD = 4;
    static double PointsPerInterceptions = -2;
    static double PassingPointsPer2Point = 2;
    
    private class WeeklyStat { 
       
        int passingYards = 0;
        int passingTDs = 0;
        int passingCompletions= 0;
        int passingAttempts = 0;
        int passingInterceptions = 0;
        int passing2Pts = 0;
        
        // Currently one simple 
        double passingPoints = 0;

        int puntRtnTDs = 0;
        float puntRtnYards = 0;
        
        int receivingReceptions = 0;
        int receivingTDs = 0;
        int receiving2Pts = 0;
        float receivingYards = 0;
        
        int rushingAttempts = 0;
        int rushingTDs = 0;
        int rushing2Pts = 0;
        float rushingYards = 0;  
    }
    
    private static class QueryBuilder{
        static String baseQuery = "SELECT play_player.gsis_id, player.full_name as Name, player.team, sum(passing_tds) as Passing_Tds,\n" 
                + "sum(passing_yds) as Passing_Yds,\n"
                + "sum(passing_att) as PassingAttempts, sum(passing_cmp) as PassingCompletions,\n"
                + "sum(passing_int) as PassingIntercepts,\n"
                + "sum(passing_twoptm) as Passing2Pts,\n"
                + "sum(puntret_tds) as PuntRtnTDs, sum(puntret_yds) as PuntRtnYds,\n" 
                + "sum(receiving_rec) as ReceivingReceptions, Sum(receiving_tds) as ReceivingTDs,\n"  
                + "sum(receiving_twoptm) as Receiving2Pts, sum(receiving_yds) as ReceivingYds,\n" 
                + "sum(rushing_att) as RushingAttempts, sum(rushing_tds) as RushingTDs, sum(rushing_twoptm) as Rushing2Pt,\n" 
                + "sum(rushing_yds) as RushingYds,\n"
                + "season_year, week\n"
                + "FROM play_player\n"
                + "join game on game.gsis_id = play_player.gsis_id\n"
                + "join player on player.player_id = play_player.player_id\n";
               
        
        static String GetQuery(int startYear, int endYear, String player ){
             String query = baseQuery  
                    + "where game.season_year >= " + startYear + " and game.season_year <= " + endYear  
                    + "and player.full_name = "
                    + "'" + player + "' and \n"
                    + "season_type = 'Regular'\n"
                    + "group by Name, season_year, week, player.team, play_player.gsis_id";
            return query;
        }
    }
    
    /**
     * Construct a set of weekly player stats for each regular season week during the requested years. 
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     * @param player the player for which the stats are to be found.
     */
    PlayerStats(int startYear, int endYear, String player){
        
        Statement stmt = null;
        
        try {
            if(c == null){
                Class.forName("org.postgresql.Driver");
                PlayerStats.c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nfldb",
                        "postgres", "Canon7D");  
            }
            stmt = PlayerStats.c.createStatement();
            ResultSet rs = stmt.executeQuery(PlayerStats.QueryBuilder.GetQuery(startYear,endYear, player) );
            while ( rs.next() ) {
                WeeklyStat ws = new WeeklyStat();
                
                int seasonYear = rs.getInt("season_year");
                int seasonWeek = rs.getInt("week");
                
                
                this.weeklyStats.put(MakeKey(seasonYear, seasonWeek), ws);
                ws.passingYards = rs.getInt("Passing_Yds");
                ws.passingTDs = rs.getInt("Passing_Tds");
                ws.passingAttempts = rs.getInt("PassingAttempts");
                ws.passingCompletions = rs.getInt("PassingCompletions");
                ws.passingInterceptions = rs.getInt("PassingIntercepts");
                ws.passing2Pts = rs.getInt("Passing2Pts");
                ws.passingPoints = ws.passingYards * PlayerStats.PassingPointsPerYard +
                        ws.passingTDs * PlayerStats.PassingPointsPerTD +
                        ws.passingInterceptions * PlayerStats.PointsPerInterceptions +
                        ws.passing2Pts * PlayerStats.PassingPointsPer2Point;
            }
            rs.close();
            stmt.close();
              
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    String MakeKey(int seasonYear, int seasonWeek){
     // Create Map key using season year and week as a combo
        StringBuilder aStrDateKey = new StringBuilder();  
        aStrDateKey.append(seasonYear);
        aStrDateKey.append(".");
        aStrDateKey.append(seasonWeek);
        
        return aStrDateKey.toString();
    }
    
    /**
     * Get passing yards for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassingYards(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingYards;
    }
    
    /**
     * Get passing TDs for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassingTDs(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingTDs;
    }
    
    /**
     * Get passing completions for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassingCompletions(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingCompletions;
    }
    
    /**
     * Get passing attempts for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassingAttempts(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingAttempts;
    }
    
    /**
     * Get passing interceptions for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassingInterceptions(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingInterceptions;
    }
    
    /**
     * Get passing 2 point conversions for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    int GetPassing2Pts(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passing2Pts;
    }
    
    /**
     * Get fantasy points for selected season year and regular season week.
     * @param startYear  a LocalDate with the desired start year of the stats.
     * @param endYear a LocalDate with the desired start year of the stats.
     */
    double GetFantasyPoints(int seasonYear, int seasonWeek){
        
        return this.weeklyStats.get(MakeKey(seasonYear,seasonWeek)).passingPoints;
    }
}
