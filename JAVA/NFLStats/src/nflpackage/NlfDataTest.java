package nflpackage;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class NlfDataTest {

    nflpackage.NlfData nflData;
    
    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    public void tearDown() throws Exception{
        
    }

    @Test
    public void testNflData(){
        try{
            nflData = new nflpackage.NlfData();
        } 
        catch(Exception e) {
            fail("NflData failed construction");
        }
    }
    
    @Test
    public void testPlayerList(){
        List<String> players = new ArrayList<String>();
        String firstPlayer = new String("A.J. Bouye");
        String lastPlayer = new String("Zurlon Tipton");
        
        try{
            LocalDate startYear = LocalDate.of(2014, 1, 1);
            LocalDate endYear = LocalDate.of(2014, 1, 1);
            
            nflData = new nflpackage.NlfData();
            
            players = nflData.GetPlayerList(startYear, endYear);
            
            assertEquals(players.get(0), firstPlayer);
            assertEquals(players.get(6416), lastPlayer);
        }
        catch(Exception e){
            fail("NflData.GetPlayerList threw exception");
        }
        
    }

}
