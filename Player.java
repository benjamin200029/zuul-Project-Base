
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
    private int maxMoves = 6;    

    /**
     * Constructor for objects of class Player
     * @param name : Name of player
     */
    public Player(String name)
    {
        this.name = name;
    }

    
    /**
     * Enter the given room
     */
    public void enterRoom(Room room){
        
        moves++;
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
     */
    public boolean gameOver()
    {
        return moves > maxMoves;
    }
    
}
