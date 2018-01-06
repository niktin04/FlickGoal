package com.niktin.flickgoal;

/**
 * Created by NikTin on 06/01/18 at 02:05.
 */

class RulesAndScoring {
    static int goals = 0;
    static long initTime = System.currentTimeMillis();
    static int outsides = 0;
    static int hopObstacles = 0;
    static int hopGoalkeeper = 0;

    static void resetScore() {
        goals = 0;
        outsides = 0;
        hopObstacles = 0;
        hopGoalkeeper = 0;
    }
}
