package app.models.inputoutput;

import java.util.Arrays;

/**
 * Represents a input/output data model class for tv episode.
 * TVEpisodeInputOutput class is used when writing into or parsing tv episodes input/output data files.
 * TVEpisodeInputOutput class has attributes bytes sizes and character lengths to be able to 
 * write into and read from input/output binary file.
 * @author jan.dostal
 */
public class TVEpisodeInputOutput
{ 
    public static final int ATTRIBUTE_NAME_LENGTH = 60;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_SUMMARY_LENGTH = 1000;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_ORDERTVSEASON_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_TVSEASONID_BYTES = Integer.BYTES;
    
    public static final int TV_EPISODE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_SUMMARY_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RUNTIME_BYTES
            + ATTRIBUTE_RATING_BYTES + ATTRIBUTE_ORDERTVSEASON_BYTES + ATTRIBUTE_TVSEASONID_BYTES;
    
    private final int id;
    
    private final long runtimeInSeconds;
    
    private final char[] name;
    
    private final int percentageRating;
    
    private final char[] hyperlinkForContentWatch;
    
    private final char[] shortContentSummary;
    
    private final int orderInTVShowSeason;
    
    private final int tvSeasonId;
    
    
    /**
     * Creates a new instance representing tv episode input/output data model.
     * If nullable attributes are null, they transform into empty String.
     * String attributes values are extended into defined string attributes character lengths.
     * @param id represents tv episode id/primary key as number (should be 1 or greater)
     * @param runtimeInSeconds represents tv episode runtime/duration in seconds (positive number)
     * @param name represents tv episode name
     * @param percentageRating represents tv episode percentage rating (-infinity, 100)
     * @param hyperlinkForContentWatch represents URL hyperlink to tv episode for watching
     * @param shortContentSummary represents tv episode short summary of its content
     * @param orderInTVShowSeason represents tv episode order int context of parent tv season (should be 1 or greater)
     * @param tvSeasonId represents tv episode parent tv season id/primary key as number (should be 1 or greater)
     */
    public TVEpisodeInputOutput(int id, long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, int tvSeasonId) 
    {
        this.id = id;
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name == null ? 
                Arrays.copyOf("".toCharArray(), ATTRIBUTE_NAME_LENGTH) : 
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
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonId = tvSeasonId;
    }
    
    /**
     * @return tv episode instance id/primary key as number
     */
    public int getId() 
    {
        return id;
    }
    
    /**
     * @return tv episode instance runtime in seconds
     */
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
    }
    
    /**
     * @return tv episode instance name
     */
    public String getName() 
    {
        return new String(name);
    }
    
    /**
     * @return tv episode instance percentage rating
     */
    public int getPercentageRating() 
    {
        return percentageRating;
    }
    
    /**
     * @return tv episode instance URL hyperlink for content watch
     */
    public String getHyperlinkForContentWatch() 
    {
        return new String(hyperlinkForContentWatch);
    }
    
    /**
     * @return tv episode instance short content summary
     */
    public String getShortContentSummary() 
    {
        return new String(shortContentSummary);
    }
    
    /**
     * @return tv episode order in context of parent tv season as number
     */
    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
    /**
     * @return tv episode parent tv season primary key/id as number
     */
    public int getTVSeasonId() 
    {
        return tvSeasonId;
    }
    
    public @Override String toString() 
    {
        String nameText = new String(name);
        String hyperlinkText = new String(hyperlinkForContentWatch);
        
        return String.format("TVEpisodeOutput{id=%d, runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "orderInTVShowSeason=%d, tvSeasonId=%d}", id, runtimeInSeconds,
                nameText, percentageRating, hyperlinkText, 
                orderInTVShowSeason, tvSeasonId);
    }
}
