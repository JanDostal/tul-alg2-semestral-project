
package app.models.output;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

/**
 *
 * @author jan.dostal
 */
public class MovieOutput
{
    public static final int ATTRIBUTE_NAME_LENGTH = 40;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_ERA_LENGTH = 30;
    public static final int ATTRIBUTE_CONTENT_LENGTH = 1000;
    
    private static final int ATTRIBUTE_WATCHED_BYTES = 1;
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RELEASEDATE_BYTES = Long.BYTES;
    
    public static final int MOVIE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_ERA_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_CONTENT_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RUNTIME_BYTES + ATTRIBUTE_RATING_BYTES
            + ATTRIBUTE_WATCHED_BYTES + ATTRIBUTE_RELEASEDATE_BYTES;
    
    private int id;
    
    private long runtimeInSeconds;
    
    private char[] name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private char[] hyperlinkForContentWatch;
    
    private char[] shortContentSummary;
    
    private long releaseDateInEpochSeconds;
    
    private char[] era;
        
    public MovieOutput(int id, Long runtimeInSeconds, String name, 
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, Long releaseDateInEpochSeconds, String era) 
    {
        this.id = id;
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = Arrays.copyOf(name.toCharArray(), ATTRIBUTE_NAME_LENGTH);
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = Arrays.copyOf(hyperlinkForContentWatch.toCharArray(), 
                ATTRIBUTE_HYPERLINK_LENGTH);
        this.shortContentSummary = Arrays.copyOf(shortContentSummary.toCharArray(), 
                ATTRIBUTE_CONTENT_LENGTH);
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
        this.era = Arrays.copyOf(era.toCharArray(), 
                ATTRIBUTE_ERA_LENGTH);
    }
    
    public int getId() 
    {
        return id;
    }
        
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
    }

    public String getName() 
    {
        return new String(name);
    }

    public int getPercentageRating() 
    {
        return percentageRating;
    }

    public boolean getWasWatched() 
    {
        return wasWatched;
    }

    public String getHyperlinkForContentWatch() 
    {
        return new String(hyperlinkForContentWatch);
    }

    public String getShortContentSummary() 
    {
        return new String(shortContentSummary);
    }
    
    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }
    
    public String getEra() 
    {
        return new String(era);
    }
    
    public @Override String toString() 
    {       
        return "MovieOutput{id=" + id + ", runtimeInSeconds=" + runtimeInSeconds + 
                ", name=" + new String(name) + ", percentageRating=" + percentageRating + 
                ", wasWatched=" + wasWatched + ", hyperlinkForContentWatch=" + 
                new String(hyperlinkForContentWatch) + ", releaseDateInEpochSeconds=" + 
                releaseDateInEpochSeconds + ", era=" + new String(era) + "}";
    }
}
