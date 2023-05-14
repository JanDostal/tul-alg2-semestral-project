
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class InputFileMovie extends InputFileMediaContent
{
    private String releaseDate;
    
    private String era;
    
    public InputFileMovie(String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, String releaseDate, String era) 
    {
        super(runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    public String getReleaseDate() 
    {
        return releaseDate;
    }
    
    public String getEra() 
    {
        return era;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", releaseDate=" + releaseDate + ", era=" + era;
    }
}
