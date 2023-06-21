package app.models.output;

import java.util.Arrays;

/**
 * Represents a output data model class for movie.
 * MovieOutput class is used when writing into or parsing movies output data files.
 * MovieOutput class has attributes bytes sizes and character lengths to be able to write into output binary file.
 * @author jan.dostal
 */
public class MovieOutput
{
    public static final int ATTRIBUTE_NAME_LENGTH = 60;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH = 60;
    public static final int ATTRIBUTE_SUMMARY_LENGTH = 1000;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RELEASEDATE_BYTES = Long.BYTES;
    
    public static final int MOVIE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_SUMMARY_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RUNTIME_BYTES + ATTRIBUTE_RATING_BYTES
            + ATTRIBUTE_RELEASEDATE_BYTES;
    
    private final int id;
    
    private final long runtimeInSeconds;
    
    private final char[] name;
    
    private final int percentageRating;
        
    private final char[] hyperlinkForContentWatch;
    
    private final char[] shortContentSummary;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] eraCodeDesignation;
    
    
    /**
     * Creates a new instance representing movie output data model.
     * If nullable attributes are null, they transform into empty String.
     * String attributes values are extended into defined string attributes character lengths.
     * @param id represents movie id/primary key as number (should be 1 or greater)
     * @param runtimeInSeconds represents movie runtime/duration in seconds (-infinity, positive number)
     * @param name represents movie name
     * @param percentageRating represents movie percentage rating (-infinity, 100)
     * @param hyperlinkForContentWatch represents URL hyperlink to movie for watching
     * @param shortContentSummary represents movie short summary of its content
     * @param releaseDateInEpochSeconds represents movie release date in epoch seconds (GMT/UTC) (-infinity, positive number)
     * @param eraCodeDesignation represents movie chosen chronological star wars era code designation
     */
    public MovieOutput(int id, long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, long releaseDateInEpochSeconds, String eraCodeDesignation) 
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
                ATTRIBUTE_SUMMARY_LENGTH) : 
                Arrays.copyOf(shortContentSummary.toCharArray(), 
                ATTRIBUTE_SUMMARY_LENGTH);
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
        this.eraCodeDesignation = eraCodeDesignation == null ? 
                Arrays.copyOf("".toCharArray(), ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH) : 
                Arrays.copyOf(eraCodeDesignation.toCharArray(), ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH);
    }
    
    /**
     * @return movie instance id/primary key as number
     */
    public int getId() 
    {
        return id;
    }
    
    /**
     * @return movie instance runtime in seconds
     */
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
    }
    
    /**
     * @return movie instance name
     */
    public String getName() 
    {
        return new String(name);
    }
    
    /**
     * @return movie instance percentage rating
     */
    public int getPercentageRating() 
    {
        return percentageRating;
    }
    
    /**
     * @return movie instance URL hyperlink for content watch
     */
    public String getHyperlinkForContentWatch() 
    {
        return new String(hyperlinkForContentWatch);
    }
    
    /**
     * @return movie instance short content summary
     */
    public String getShortContentSummary() 
    {
        return new String(shortContentSummary);
    }
    
    /**
     * @return movie instance release date in epoch seconds (GMT/UTC)
     */
    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }
    
    /**
     * @return movie instance chosen era code designation
     */
    public String getEraCodeDesignation() 
    {
        return new String(eraCodeDesignation);
    }
    
    public @Override String toString() 
    {
        String nameText = new String(name);
        String hyperlinkText = new String(hyperlinkForContentWatch);
        String eraText = new String(eraCodeDesignation);
        
        return String.format("MovieOutput{id=%d, runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "releaseDateInEpochSeconds=%d, eraCodeDesignation=%s}", id, runtimeInSeconds,
                nameText, percentageRating, hyperlinkText,
                releaseDateInEpochSeconds, eraText);
    }
}
