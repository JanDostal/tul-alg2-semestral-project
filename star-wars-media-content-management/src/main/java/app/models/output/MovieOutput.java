
package app.models.output;

import java.util.Arrays;

/**
 *
 * @author jan.dostal
 */
public class MovieOutput
{
    public static final int ATTRIBUTE_NAME_LENGTH = 100;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_ERA_LENGTH = 60;
    public static final int ATTRIBUTE_CONTENT_LENGTH = 1000;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RELEASEDATE_BYTES = Long.BYTES;
    
    public static final int MOVIE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_ERA_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_CONTENT_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RUNTIME_BYTES + ATTRIBUTE_RATING_BYTES
            + ATTRIBUTE_RELEASEDATE_BYTES;
    
    private final int id;
    
    private final long runtimeInSeconds;
    
    private final char[] name;
    
    private final int percentageRating;
        
    private final char[] hyperlinkForContentWatch;
    
    private final char[] shortContentSummary;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] era;
        
    public MovieOutput(int id, long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, long releaseDateInEpochSeconds, String era) 
    {
        this.id = id;
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name == null ? Arrays.copyOf("".toCharArray(), ATTRIBUTE_NAME_LENGTH) : 
                Arrays.copyOf(name.toCharArray(), ATTRIBUTE_NAME_LENGTH);
        this.percentageRating = percentageRating;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch == null ? 
                Arrays.copyOf("".toCharArray(), 
                ATTRIBUTE_HYPERLINK_LENGTH) : 
                Arrays.copyOf(hyperlinkForContentWatch.toCharArray(), 
                ATTRIBUTE_HYPERLINK_LENGTH);
        this.shortContentSummary = shortContentSummary == null ? 
                Arrays.copyOf("".toCharArray(), 
                ATTRIBUTE_CONTENT_LENGTH) : 
                Arrays.copyOf(shortContentSummary.toCharArray(), 
                ATTRIBUTE_CONTENT_LENGTH);
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
        this.era = era == null ? 
                Arrays.copyOf("".toCharArray(), ATTRIBUTE_ERA_LENGTH) : 
                Arrays.copyOf(era.toCharArray(), ATTRIBUTE_ERA_LENGTH);
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
        String nameText = new String(name);
        String hyperlinkText = new String(hyperlinkForContentWatch);
        String eraText = new String(era);
        
        return String.format("MovieOutput{id=%d, runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "releaseDateInEpochSeconds=%d, era=%s}", id, runtimeInSeconds,
                nameText, percentageRating, hyperlinkText,
                releaseDateInEpochSeconds, eraText);
    }
}
