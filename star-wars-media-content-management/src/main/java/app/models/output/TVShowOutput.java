
package app.models.output;

import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class TVShowOutput 
{   
    public static final int ATTRIBUTE_NAME_LENGTH = 40;
    public static final int ATTRIBUTE_ERA_LENGTH = 30;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RELEASEDATE_BYTES = Long.BYTES;
        
    public static final int TV_SHOW_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_ERA_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RELEASEDATE_BYTES;
    
    private int id;
    
    private char[] name;
    
    private long releaseDateInEpochSeconds;
    
    private char[] era;
    
    public TVShowOutput(int id, String name, long releaseDateInEpochSeconds, String era) 
    {
        this.id = id;
        this.name = name == null || name.trim().isEmpty() ? null : Arrays.copyOf(name.toCharArray(), ATTRIBUTE_NAME_LENGTH);
        this.era = era == null || era.trim().isEmpty() ? null : Arrays.copyOf(era.toCharArray(), ATTRIBUTE_ERA_LENGTH);
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
    }
    
    public int getId() 
    {
        return id;
    }
    
    public String getName() 
    {
        return name == null ? null : new String(name);
    }

    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }

    public String getEra() 
    {
        return era == null ? null : new String(era);
    }
    
    public @Override String toString() 
    {
        String nameText = name == null ? null : new String(name);
        String eraText = era == null ? null : new String(era);
        
        return "TVShowOutput{id=" + id + ", name=" + nameText + ", releaseDateInEpochSeconds=" + 
                releaseDateInEpochSeconds + ", era=" + eraText + "}";
    }
}
