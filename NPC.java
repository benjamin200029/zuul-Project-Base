import java.util.ArrayList;

/**
 * Represents an NPC in the game
 *
 * @author Benjamin Adelson & Erik Cooke
 * @version 2019.11.2
 */
public class NPC
{
    // instance variables - replace the example below with your own
    private String name;
    private String greeting;
    private String message;
    private ArrayList<Item> npcItems;
    private Room npcCurrentRoom;
    

    /**
     * Constructor for objects of class NPC
     * @param name The name for the character
     * @param ncpRoom The room for the character to start in
     */
    public NPC(String name, String greeting, String message)
    {
        this.name = name;
        this.greeting = greeting;
        this.message = message;
        npcItems = new ArrayList<Item>(5);
        //npcCurrentRoom = npcRoom;
    }

    /**
     * Returns the name of the character
     *
     * 
     * @return String characters name
     */
    public String getName()
    {
        return name;
    }
    
    public void setRoom(Room currentRoom)
    {
        npcCurrentRoom = currentRoom;
    }
    
    /**
     * Adds an item to the npc
     * @param tempItem Item to add to inventory
     */
    public void addItem(Item tempItem)
    {
        npcItems.add(tempItem);
    }
    
    /**
     * Removes an item from the npc
     * @param tempItem item to be dropped from inventory
     */
    public void dropItem(Item tempItem)
    {
        npcItems.remove(tempItem);
    }
    
    /**
     * Npc's greeting message
     * @return String
     */
    public String getGreeting()
    {
        return greeting;
    }
    
    /**
     * Returns the npc's message
     * @return String
     */
    public String getMessage()
    {
        if(npcItems.isEmpty())
        {
            return "Sorry, but I am out of apples dear.\n";
        }
        else
        {
            Item tempItem = npcItems.remove(0);
            npcCurrentRoom.addItem(tempItem);
            return message + "\n";
    }
    }
}
