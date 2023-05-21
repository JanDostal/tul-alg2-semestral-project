/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tests.mainmethods;

import app.models.output.*;

/**
 *
 * @author Admin
 */
public class OutputModelsTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //movie test
        System.out.println();
        System.out.println("movie test");
        System.out.println();
        
        System.out.println("Movie record size: " + MovieOutput.MOVIE_RECORD_SIZE + " B");
        
        //tv episode test
        System.out.println();
        System.out.println("tv episode test");
        System.out.println();
        System.out.println("TV episode record size: " + TVEpisodeOutput.TV_EPISODE_RECORD_SIZE + " B");
        
        //tv season test
        System.out.println();
        System.out.println("tv season test");
        System.out.println();
        System.out.println("TV season record size: " + TVSeasonOutput.TV_SEASON_RECORD_SIZE + " B");
        
        //tv show test
        System.out.println();
        System.out.println("tv show test");
        System.out.println();
        System.out.println("TV show record size: " + TVShowOutput.TV_SHOW_RECORD_SIZE + " B");
    }
    
}
