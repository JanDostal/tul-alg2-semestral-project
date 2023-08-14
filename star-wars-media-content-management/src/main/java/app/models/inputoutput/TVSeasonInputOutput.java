package app.models.inputoutput;

/**
 * Represents a input/output data model class for tv season.
 * TVSeasonInputOutput class is used when writing into or parsing tv seasons input/output data files.
 * TVSeasonInputOutput class has attributes bytes sizes and character lengths to be able to 
 * write into and read from input/output binary file.
 * @author jan.dostal
 */
public class TVSeasonInputOutput 
{
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_ORDERTVSHOW_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_TVSHOWID_BYTES = Integer.BYTES;
    
    public static final int TV_SEASON_RECORD_SIZE = ATTRIBUTE_ID_BYTES 
            + ATTRIBUTE_ORDERTVSHOW_BYTES + ATTRIBUTE_TVSHOWID_BYTES;
    
    private final int id;
    
    private final int orderInTVShow;
    
    private final int tvShowId;
    
    /**
     * Creates a new instance representing tv season input/output data model.
     * @param id represents tv season id/primary key as number (should be 1 or greater)
     * @param orderInTVShow represents tv season order in context of parent tv show (should be 1 or greater)
     * @param tvShowId represents tv season parent tv show primary key/id as number (should be 1 or greater)
     */
    public TVSeasonInputOutput(int id, int orderInTVShow, int tvShowId) 
    {
        this.id = id;
        this.orderInTVShow = orderInTVShow;
        this.tvShowId = tvShowId;
    }
    
    /**
     * @return tv season instance id/primary key as number
     */
    public int getId() 
    {
        return id;
    }
    
    /**
     * @return tv season instance order in context of parent tv show
     */
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    /**
     * @return tv season instance parent tv show primary key/id as number
     */
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
