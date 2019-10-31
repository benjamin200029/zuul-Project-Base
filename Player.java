
/**
 * represenation of the player in the game Zuul
 *
 * @author Benjamin Adelson and Erik Cooke
 * @version 2019.10.29
 */
public class Player
{
    // the players name
    private String name;
    // The current room the player is in
    private Room currentRoom;
    
    //starting limit of moves
    private int moves = 0;
    //stoping point
    private int maxMoves = 15;
    
    // maximum hunger value
    private int maxHunger;
    // current hunger value
    private int currentHunger;

    /**
     * Constructor for objects of class Player
     * @param name : Name of player
     */
    public Player(String name)
    {
        this.name = name;
        maxHunger = 100;
        currentHunger = 110;
    }
    
    /**
     * Enter the given room
     */
    public void enterRoom(Room room){        
        moves++;
        currentHunger -= 10;
        currentRoom = room;
    }
    
    /**
     * Gets the location of the player
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    /**
     * Gets the name of the player
     */
    public String getName(){
        return name;
    }
    
    /**
     * Checks if the player has lost
     * @return Boolean
     */
    public boolean gameOver()
    {
         return moves > maxMoves;
    }
    
    /**
     * Checks if the player has staved to death
     * @return Boolean
     */
    public boolean starved()
    {
        return currentHunger <= 0;
    }
    
    
    /**
     * Returns the maximum hunger level for player
     * @return Maximum hunger level
     */
    public int getMaxHunger()
    {
        return maxHunger;
    }
    
    /**
     * Returns the current hunger level.
     * @return The current hunger level.
     */
    public int getCurrentHunger()
    {
        return currentHunger;
    }
    
    /**
     * Returns the current hunger level like this:
     * Current hunger level: 50/100
     * @return String of the current hunger level
     */
    public String toStringHunger()
    {
        return "Current hunger level: " + currentHunger + "/" + maxHunger;
    }
    
    /**
     * The player has eaten something.
     * 
     */
    public String eat()
    {
        currentHunger += 20;
        if(currentHunger > maxHunger)
        {
            currentHunger = maxHunger;
        }
        return "Yum Yum Yum, that was good. Current Hunger is: " + currentHunger + "/" + maxHunger;
    }
}