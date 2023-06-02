
package utils.helpers;

import app.models.data.Movie;
import app.models.output.MovieOutput;

/**
 * meziclanek mezi databazi a soubory
 * @author Admin
 */
public final class MovieDataConverter 
{
    private MovieDataConverter()
    {
    }
    
    public static MovieOutput convertToOutputDataFrom(Movie data) 
    {
        int id = data.getPrimaryKey().getId();
        long runtime = data.getRuntime().toSeconds();
        String name = data.getName();
        int percentage = data.getPercentageRating();
        String hyperlink = data.getHyperlinkForContentWatch();
        String content = data.getShortContentSummary();
        long epochSeconds = data.getReleaseDate().
    }
}
