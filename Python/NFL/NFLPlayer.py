"""
NFLData.py

Created by Don Hescht on 13-Dec-2015
Copyright (c) 2015 DAHConsulting. All rights reserved.
"""
import datetime
import nfldb

class NFLPlayer:

    PtPassingYard = 0.04
    PtPassingTD = 4
    PtPerIntercept = -2
    PtPerRunYds =  0.1
    PtPerRunTD = 6
    PtPerRecYds = 0.1
    PtPerRecTD =  6
    FumbleRecoverTD = 6
    PATD2 = 2
    FumbleLost = -2


    def __init__(self, name, seasonyear):
        self.__name = name
        self.__seasonYear = seasonyear
        __nfldbConnect = nfldb.connect()

        q = nfldb.Query(__nfldbConnect).game(season_year=self.__seasonYear, season_type='Regular')
        for pp in q.as_aggregate():
            print pp.player
        return


if __name__ == '__main__':
    unittest.main()




