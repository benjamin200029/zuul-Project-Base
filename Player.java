import java.util.Stack;
import java.util.ArrayList;
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
    // room history tracker
    private Stack<Room> roomHistory;
    
    //starting limit of moves
    private int moves = 0;
    //stopping point
    private int maxMoves = 15;
    
    // maximum hunger value
    private int maxHunger;
    // current hunger value
    private int currentHunger;
    // how much to change currentHunger each move
    private int hungerChange;
    
    // holder for player items
    private ArrayList<Item> playerItems;

    /**
     * Constructor for objects of class Player
     * @param name : Name of player
     */
    public Player(String name)
    {
        this.name = name;
        maxHunger = 100;
        currentHunger = 100;
        hungerChange = -10;
        roomHistory = new Stack<Room>();
        playerItems = new ArrayList<Item>();
    }
    
    /**
     * Enter room for starting game and after player moves back
     * @param room Room to move into
     */
        public void startRoom(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * Enter the given room
     * @param room The Room to move into
     */
    public void enterRoom(Room room){        
        moves++;
        currentHunger += hungerChange;
        roomHistory.push(currentRoom);
        currentRoom = room;        
    }
    
    /**
     * Goes through the door in the given direction
     * if correct, will return true, if false, 
     * there is no door or it is lcoked and need a key
     */
    
    public boolean access(String direction){
        Door door = currentRoom.getDoor(direction);
        if(door == null){
            return false;
        }
        Room nextRoom = door.open(currentRoom);
        
        nextRoom = door.open(currentRoom);
        if(nextRoom != null){
            enterRoom(nextRoom);
            return true;
        }
        else{
            return false;
        }
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
     * Returns a string of current moves.
     * @return current moves made.
     */
    public String toStringMoves()
    {
        return "\nMoves: " + moves;
    }
    
    /**
     * The player has eaten something.
     * 
     */
    public String eat()
    {
        if(currentHunger == maxHunger)
        {
            return "You are full, stop trying to eat! Current Hunger is: " + 
                   currentHunger + "/" + maxHunger;
        }
        currentHunger += 20;
        if(currentHunger > maxHunger)
        {
            currentHunger = maxHunger;
        }
        return "Yum Yum Yum, that was good. Current Hunger is: " + currentHunger +
               "/" + maxHunger;
    }
    
    /**
     * Moves player to previous room(s).
     */
    public void goBack()
    {
        if(roomHistory.isEmpty())
        {
            System.out.println("There are no rooms you can backtrack to.\n");
        }
        else
        {
            currentRoom = roomHistory.pop();
            moves ++;
            currentHunger += hungerChange;
            //currentRoom = previousRoom;
        }
    }
    
    /**
     * Add an item to the players item list
     * @param newItem : Item to be added to list
     */
    private void addItem(Item newItem)
    {
        playerItems.add(newItem);
    }
    
    /**
     * Removes and item from the players item list
     * @param itemDrop : the item to be removed
     */
    private void removeItem(Item itemDrop)
    {
        playerItems.remove(itemDrop);
    }
    
    /**
     * Pickup an Item from a Room.
     * @param Item : Item to be picked up
     */
    public void takeItem(String newItem)
    {
        Item tempItem = currentRoom.containsItem(newItem);
        if(tempItem != null)
        {
            addItem(tempItem);
            currentRoom.removeItem(tempItem);
            System.out.println("You picked up the " + tempItem.getName());
        }
        else
        {
            System.out.println("This location does not have that item");
        }
    }
    
    /**
     * Drops an item from the players item list
     * @param itemDrop : item name to be dropped
     */
    public void dropItem(String itemDrop)
    {
        int index = 0;
        Item tempItem;
        while(index < playerItems.size())
        {
            tempItem = playerItems.get(index);
            if(itemDrop.equals(tempItem.getName()))
            {
                removeItem(tempItem);
                currentRoom.addItem(tempItem);
                System.out.println("You dropped the " + itemDrop);
                return;
            }
            index++;
        }
        System.out.println("You do not have that item");
    }
    
    /**
     * Prints out a list of the items the player is holding
     */
    public String listItems()
    {
        if(playerItems.isEmpty())
        {
            return "You are not carrying any items";
        }
        String itemsList = "You are carrying:\n";
        for(Item item : playerItems)
        {
            itemsList += "-" + item.getLongDescription() + "\n";
        }
        return itemsList;
    }
    
    public String getLongDescription(){
        String returnString = currentRoom.getLongDescription();
        returnString += "\n" + listItems();
        return returnString;
        
    }
}