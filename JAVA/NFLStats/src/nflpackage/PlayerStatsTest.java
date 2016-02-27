package nflpackage;


import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;


public class PlayerStatsTest {

    @Test
    public void testPlayerStats() {
        nflpackage.PlayerStats playerStats = new nflpackage.PlayerStats(2014, 2014, "Aaron Rodgers") ;
        
        Assert.assertNotNull(playerStats);
          
    }
    
    @Test
    public void testGetFunctions(){
        nflpackage.PlayerStats playerStats = new nflpackage.PlayerStats(2013, 2014, "Aaron Rodgers") ;
        
        Assert.assertNotNull(playerStats);
        
        assertEquals(333, playerStats.GetPassingYards(2013, 1));  
        assertEquals(1,playerStats.GetPassingTDs(2014,1 ));
        assertEquals(17,playerStats.GetPassingCompletions(2014,17 ));
        assertEquals(22,playerStats.GetPassingAttempts(2014,17 ));
        assertEquals(2,playerStats.GetPassingInterceptions(2014,15 ));
        assertEquals(0,playerStats.GetPassing2Pts(2013,17 ));
        assertEquals(16.7,playerStats.GetFantasyPoints(2013,17),0.1); 
    }

}
