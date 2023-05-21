
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
    
    public TVEpisodeOutput(int id, Long runtimeInSeconds, String name, 
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, int tvSeasonId) 
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
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonId = tvSeasonId;
    }
    
    public int getId() 
    {
        return id;
    }
    
    public Long getRuntimeInSeconds() 
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
        return "TVEpisodeOutput{id=" + id + ", runtimeInSeconds=" + runtimeInSeconds + ", name=" + new String(name) + 
                ", percentageRating=" + percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + new String(hyperlinkForContentWatch) + 
                ", orderInTVShowSeason=" + orderInTVShowSeason + ", tvSeasonId=" +
                tvSeasonId + "}";
    }
}
