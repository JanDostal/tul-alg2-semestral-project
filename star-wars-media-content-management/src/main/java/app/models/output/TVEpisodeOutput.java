
package app.models.output;

import java.util.Arrays;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeOutput
{ 
    public static final int ATTRIBUTE_NAME_LENGTH = 40;
    public static final int ATTRIBUTE_HYPERLINK_LENGTH = 180;
    public static final int ATTRIBUTE_CONTENT_LENGTH = 1000;
    
    private static final int ATTRIBUTE_WATCHED_BYTES = 1;
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RUNTIME_BYTES = Long.BYTES;
    private static final int ATTRIBUTE_RATING_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_ORDERTVSEASON_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_TVSEASONID_BYTES = Integer.BYTES;
    
    public static final int TV_EPISODE_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_HYPERLINK_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_CONTENT_LENGTH * Character.BYTES
            + ATTRIBUTE_WATCHED_BYTES + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RUNTIME_BYTES
            + ATTRIBUTE_RATING_BYTES + ATTRIBUTE_ORDERTVSEASON_BYTES + ATTRIBUTE_TVSEASONID_BYTES;
    
    private int id;
    
    private long runtimeInSeconds;
    
    private char[] name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private char[] hyperlinkForContentWatch;
    
    private char[] shortContentSummary;
    
    private int orderInTVShowSeason;
    
    private int tvSeasonId;
    
    public TVEpisodeOutput(int id, long runtimeInSeconds, String name, 
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, int tvSeasonId) 
    {
        this.id = id;
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name == null || name.trim().isEmpty() ? null : 
                Arrays.copyOf(name.toCharArray(), ATTRIBUTE_NAME_LENGTH);
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch == null || 
                hyperlinkForContentWatch.trim().isEmpty() ? 
                null : Arrays.copyOf(hyperlinkForContentWatch.toCharArray(), 
                ATTRIBUTE_HYPERLINK_LENGTH);
        this.shortContentSummary = shortContentSummary == null || 
                shortContentSummary.trim().isEmpty() ? 
                null : Arrays.copyOf(shortContentSummary.toCharArray(), 
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
        return name == null ? null : new String(name);
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
        return hyperlinkForContentWatch == null ? null : new String(hyperlinkForContentWatch);
    }

    public String getShortContentSummary() 
    {
        return shortContentSummary == null ? null : new String(shortContentSummary);
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
        String nameText = name == null ? null : new String(name);
        String hyperlinkText = hyperlinkForContentWatch == null ? null : new String(hyperlinkForContentWatch);
        
        return String.format("TVEpisodeOutput{id=%d, runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, wasWatched=%s, hyperlinkForContentWatch=%s, "
                + "orderInTVShowSeason=%d, tvSeasonId=%d}", id, runtimeInSeconds,
                nameText, percentageRating, wasWatched, hyperlinkText, 
                orderInTVShowSeason, tvSeasonId);
    }
}
