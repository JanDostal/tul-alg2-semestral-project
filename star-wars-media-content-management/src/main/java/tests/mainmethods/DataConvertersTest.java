/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests.mainmethods;

import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.input.MovieInput;
import app.models.output.MovieOutput;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import utils.helpers.MovieDataConverter;

/**
 *
 * @author Admin
 */
public class DataConvertersTest 
{
    public static void main(String[] args) 
    {
        //MovieDataConverter
        System.out.println();
        System.out.println("MovieDataConverter");
        System.out.println();
        
        //convertToOutputDataFrom
        System.out.println("convertToOutputDataFrom");
        System.out.println();
        
        LocalDate localDate = LocalDate.of(2012, 5, 12); 
        LocalDateTime localDateTime = localDate.atStartOfDay();
        long epochSeconds = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        LocalDate date = Instant.ofEpochSecond(1336774800).atZone(ZoneId.systemDefault()).toLocalDate();
        
        Movie movie = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, true, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println(movie);
        
        MovieOutput movieOutputNew = MovieDataConverter.convertToOutputDataFrom(movie);
        
        System.out.println(movieOutputNew);
        
        System.out.println();
        System.out.println("convertToDataFrom (outputData)");
        System.out.println();
        
        MovieOutput movieOutput = new MovieOutput(1, 45, "filmA", 
                50, "https://www.example02.com", "Velmi krásný film", 11111111L, "FALL_OF_THE_JEDI");
        
        System.out.println(movieOutput);
        
        Movie convertedMovie = MovieDataConverter.convertToDataFrom(movieOutput);
        
        System.out.println(convertedMovie);
        
        System.out.println();
        System.out.println("convertToDataFrom (inputData)");
        System.out.println();
        
        MovieInput movieInput = new MovieInput(45, "filmA", 
                50, "https://www.example01.com", "Velmi krásný film", 
                1111111111L, "FALL_OF_THE_JEDI");
        
        System.out.println(movieInput);
        
        convertedMovie = MovieDataConverter.convertToDataFrom(movieInput);
        
        System.out.println(convertedMovie);
    }
}
