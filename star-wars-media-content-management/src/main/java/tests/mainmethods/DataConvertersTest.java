package tests.mainmethods;

import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.input.MovieInput;
import app.models.inputoutput.MovieInputOutput;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import utils.exceptions.DataConversionException;
import utils.helpers.MovieDataConverter;

/**
 * Represents a custom unit test class for testing data converter of helpers module.
 * @author jan.dostal
 */
public class DataConvertersTest 
{
    //testing main
    public static void main(String[] args) 
    {
//        //MovieDataConverter
//        System.out.println();
//        System.out.println("MovieDataConverter");
//        System.out.println();
//        
//        //convertToOutputDataFrom
//        System.out.println("convertToInputOutputDataFrom");
//        System.out.println();
//        
//        LocalDate localDate = LocalDate.of(2012, 5, 12); 
//        LocalDateTime localDateTime = localDate.atStartOfDay();
//        long epochSeconds = localDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
//        LocalDate date = Instant.ofEpochSecond(1336780800).atZone(ZoneOffset.UTC).toLocalDate();
//        
//        Movie movie = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "movieA", 
//                50, true, "https://www.example01.com", "very pretty movie", 
//                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        System.out.println(movie);
//        
//        MovieInputOutput movieInputOutputNew = MovieDataConverter.convertToInputOutputDataFrom(movie);
//        
//        System.out.println(movieInputOutputNew);
//        
//        System.out.println();
//        System.out.println("convertToDataFrom (inputOutputData)");
//        System.out.println();
//        
//        MovieInputOutput movieInputOutput = new MovieInputOutput(1, 45, "movieA", 
//                50, "https://www.example02.com", "very pretty movie", 11111111L, "FALL_OF_THE_JEDI");
//        
//        System.out.println(movieInputOutput);
//        
//        Movie convertedMovie = null;
//        try 
//        {
//            convertedMovie = MovieDataConverter.convertToDataFrom(movieInputOutput);
//            System.out.println(convertedMovie);
//        }
//        catch (DataConversionException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//                
//        System.out.println();
//        System.out.println("convertToDataFrom (inputData)");
//        System.out.println();
//        
//        MovieInput movieInput = new MovieInput(45, "movieA", 
//                50, "https://www.example01.com", "very pretty movie", 
//                1111111111L, "FALL_OF_THE_JEDI");
//        
//        System.out.println(movieInput);
//        
//        try 
//        {
//            convertedMovie = MovieDataConverter.convertToDataFrom(movieInput);
//            System.out.println(convertedMovie);
//        }
//        catch (DataConversionException e) 
//        {
//            System.out.println(e.getMessage());
//        }
    }
}
