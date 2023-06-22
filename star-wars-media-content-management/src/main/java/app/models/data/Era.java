package app.models.data;

import app.logic.datastore.DataStore;

/**
 * Represents an enum type for specifying various Star wars chronological eras.
 * <p>
 * Chronological eras are used to classify data (movies and TV shows).
 * <p>
 * Chronological eras originate from this
 * <a href="https://www.screengeek.net/2023/04/07/star-wars-timeline-eras/">source</a>.
 * <p>
 * Each era has default name for usage in data files and display name with description for usage in UI.
 * @author jan.dostal
 */
public enum Era 
{
    DAWN_OF_THE_JEDI("Úsvit Jediů"), //the oldest era
    THE_OLD_REPUBLIC("Stará republika"),
    THE_HIGH_REPUBLIC("Vrcholná republika"),
    FALL_OF_THE_JEDI("Pád Jediů"),
    REIGN_OF_THE_EMPIRE("Vláda impéria"),
    AGE_OF_REBELLION("Věk povstání"),
    THE_NEW_REPUBLIC("Nová republika"),
    RISE_OF_THE_FIRST_ORDER("Vzestup Prvního řádu"),
    NEW_JEDI_ORDER("Nový řád Jedi"); //the latest era
    
    private final String displayName;
    
    private String description;
    
    /**
     * Creates a new instance of Era
     * @param displayName represents name for displaying in UI
     */
    private Era(String displayName) 
    {
        this.displayName = displayName;
    }
    
    /**
     * @return era instance display name for usage in UI
     */
    public String getDisplayName() 
    {
        return displayName;
    }
    
    /**
     * Loads era instance description from datastore by era default name
     * @return Era instance description loaded from datastore. 
     * If description is already loaded, just returns existing description
     */
    public String getDescription() 
    {
        if (description == null) 
        {
            description = DataStore.loadEraDescription(this.toString());
        }
        
        return description;
    }
}
