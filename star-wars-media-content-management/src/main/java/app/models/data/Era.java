
package app.models.data;

import app.logic.datastore.DataStore;

/**
 *
 * @author jan.dostal
 */
public enum Era 
{
    DAWN_OF_THE_JEDI("Úsvit Jediů"), //the oldest era
    THE_OLD_REPUBLIC("Stará republika"),
    THE_HIGH_REPUBLIC("Vrcholná republika"),
    FALL_OF_THE_JEDI("Pád Jediů"),
    REIGN_OF_THE_EMPIRE("Vláda impéria"),
    AGE_OF_THE_REBELLION("Věk povstání"),
    THE_NEW_REPUBLIC("Nová republika"),
    RISE_OF_THE_FIRST_ORDER("Vzestup Prvního řádu"),
    NEW_JEDI_ORDER("Nový řád Jedi"); //the latest era
    
    private final String displayName;
    
    private String description;
    
    private Era(String displayName) 
    {
        this.displayName = displayName;
    }
    
    public String getDisplayName() 
    {
        return displayName;
    }
    
    public String getDescription() 
    {
        if (description == null) 
        {
            description = DataStore.loadEraDescription(this.toString());
        }
        
        return description;
    }
}
