package com.vt.vthacks;

// -------------------------------------------------------------------------
/**
 *
 *  Class represents a Schedule object
 *
 *  @author Brandon Potts
 *  @version Mar 23, 2014
 */
public class Schedule
{

    private String name;
    private String description;
    private String time;


    // ----------------------------------------------------------
    /**
     * Create a new Schedule object.
     * @param nm
     * @param descrip
     * @param t
     */
    public Schedule(String nm , String descrip , String t)
    {
        name = nm;
        description = descrip;
        time = t;
    }


    // ----------------------------------------------------------
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }


    // ----------------------------------------------------------
    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }


    // ----------------------------------------------------------
    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }


    // ----------------------------------------------------------
    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }


    // ----------------------------------------------------------
    /**
     * @param time the time to set
     */
    public void setTime(String time)
    {
        this.time = time;
    }

}
