package app.models.output;

/**
 * Represents a output data model class for tv season
 * TVSeasonOutput class is used when writing into or pasing tv seasons output data files
 * TVSeasonOutput class has attributes bytes sizes and character lengths to be able to write into output binary file
 * @author jan.dostal
 */
public class TVSeasonOutput 
{
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_ORDERTVSHOW_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_TVSHOWID_BYTES = Integer.BYTES;
    
    public static final int TV_SEASON_RECORD_SIZE = ATTRIBUTE_ID_BYTES 
            + ATTRIBUTE_ORDERTVSHOW_BYTES + ATTRIBUTE_TVSHOWID_BYTES;
    
    private final int id;
    
    private final int orderInTVShow;
    
    private final int tvShowId;
    
    public TVSeasonOutput(int id, int orderInTVShow, int tvShowId) 
    {
        this.id = id;
        this.orderInTVShow = orderInTVShow;
        this.tvShowId = tvShowId;
    }
            
    public int getId() 
    {
        return id;
    }
    
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public int getTVShowId() 
    {
        return tvShowId;
    }
    
    public @Override String toString() 
    {
        return String.format("TVSeasonOutput{id=%d, orderInTVShow=%d, tvShowId=%d}", 
                id, orderInTVShow, tvShowId);
    }
}
