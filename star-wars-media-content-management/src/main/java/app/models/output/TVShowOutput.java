package app.models.output;

import java.util.Arrays;

/**
 * Represents a output data model class for tv show
 * TVShowOutput class is used when writing into or pasing tv shows output data files
 * TVShowOutput class has attributes bytes sizes and character lengths to be able to write into output binary file
 * @author jan.dostal
 */
public class TVShowOutput 
{   
    public static final int ATTRIBUTE_NAME_LENGTH = 60;
    public static final int ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH = 60;
    
    private static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    private static final int ATTRIBUTE_RELEASEDATE_BYTES = Long.BYTES;
        
    public static final int TV_SHOW_RECORD_SIZE = 2 * ATTRIBUTE_NAME_LENGTH * Character.BYTES
            + 2 * ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH * Character.BYTES
            + ATTRIBUTE_ID_BYTES + ATTRIBUTE_RELEASEDATE_BYTES;
    
    private final int id;
    
    private final char[] name;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] eraCodeDesignation;
    
    public TVShowOutput(int id, String name, long releaseDateInEpochSeconds, String eraCodeDesignation) 
    {
        this.id = id;
        this.name = name == null ? 
                Arrays.copyOf("".toCharArray(), ATTRIBUTE_NAME_LENGTH) : 
                Arrays.copyOf(name.toCharArray(), ATTRIBUTE_NAME_LENGTH);
        this.eraCodeDesignation = eraCodeDesignation == null ? 
                Arrays.copyOf("".toCharArray(), ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH) : 
                Arrays.copyOf(eraCodeDesignation.toCharArray(), ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH);
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
    }
    
    public int getId() 
    {
        return id;
    }
    
    public String getName() 
    {
        return new String(name);
    }

    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }

    public String getEraCodeDesignation() 
    {
        return new String(eraCodeDesignation);
    }
    
    public @Override String toString() 
    {
        String nameText = new String(name);
        String eraText = new String(eraCodeDesignation);
        
        return String.format("TVShowOutput{id=%d, name=%s, releaseDateInEpochSeconds=%d, eraCodeDesignation=%s}", 
                id, nameText, releaseDateInEpochSeconds, eraText);
    }
}
