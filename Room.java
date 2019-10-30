import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;   
    // stores exits of this room.
        
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    
    //Stores items in this room
    private ArrayList<Item> roomItems;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        roomItems = new ArrayList<Item>(5);
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Adds and item to this room
     * 
     * @param newItem The new item to be added to this room
     */
    public void addItem(Item newItem)
    {
        roomItems.add(newItem);
    }
    
    /**
     * Removes and item from this room
     * 
     * @param oldItem the item to be removed from this room
     */
    public void removeItem(Item oldItem)
    {
        roomItems.remove(oldItem);
    }
    
    // /**
     // * Returns an ArrayList of the items in this room
     // */
    // public ArrayList<Item> getItems()
    // {
       // if(roomItems.isEmpty())
       // {
           // return null;
       // }
       // else
       // {
           // return roomItems;
       // }
    // }
    
    /**
     * Returns a string of the items in the current room or null if there are no items
     */
    public String getItemString()
    {
        if(roomItems.isEmpty())
        {
            return "Items: None\n";
        }
        else
        {
            String returnItems = "Item(s) at this location.\n";
            for(Item item : roomItems)
            {
                returnItems += item.getLongDescription() + "\n";
            }
            return returnItems;
        }
    }
}

