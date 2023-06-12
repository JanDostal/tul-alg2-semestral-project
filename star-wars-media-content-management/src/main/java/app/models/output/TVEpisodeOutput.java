
package app.models.output;

import java.util.Arrays;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeOutput
{ 
    public static final int ATTRIBUTE_NAME_LENGTH = 100;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_CONTENT_LENGTH = 1000;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_ORDERTVSEASON_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_TVSEASONID_BYTES = Integer.BYTES;
    
    public static final int TV_EPISODE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_CONTENT_LENGTH * Character.BYTES
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
    
    public TVEpisodeOutput(int id, long runtimeInSeconds, String name, 
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
                ATTRIBUTE_CONTENT_LENGTH) : 
                Arrays.copyOf(shortContentSummary.toCharArray(), 
                ATTRIBUTE_CONTENT_LENGTH);
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonId = tvSeasonId;
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

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
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
