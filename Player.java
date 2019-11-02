import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Represenation of the player in the game College of Raritan
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
    
    // the current weight player is carrying
    private int currentWeight;
    // the maximum weight player can carry
    private int maxWeight;   

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
        currentWeight = 0;
        maxWeight = 600;
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
     * Access the door in the given direction
     * if correct, will return true, if false, 
     * there is no door or it is locked and needs a key
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
            //tryed this attempt but facing errors
            //Iterator iter = Item.iterator();
            //while(iter.hasNext() && !door.unlock((Item) iter.next())); 
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
     * @return String The name of player
     */
    public String getName(){
        return name;
    }
    
    /**
     * Checks if the player has lost based on number of moves
     * @return Boolean
     */
    public boolean gameOver()
    {
         return moves >= maxMoves;
    }
    
    /**
     * Checks if the player has starved to death
     * @return Boolean
     */
    public boolean starved()
    {
        return currentHunger <= 0;
    }
    
    
    /**
     * Returns the maximum hunger level for player
     * @return int Maximum hunger level
     */
    public int getMaxHunger()
    {
        return maxHunger;
    }
    
    /**
     * Returns the current hunger level.
     * @return int The current hunger level.
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
        return "\nCurrent hunger level: " + currentHunger + "/" + maxHunger;
    }
    
    /**
     * Returns a string of current moves.
     * @return current moves made.
     */
    public String toStringMoves()
    {
        return "\nMoves: " + moves + "/" + maxMoves;
    }
    
    /**
     * The player has eaten something.
     * 
     */
    public String eat(String eatString)
    {
        if(currentHunger == maxHunger)
        {
            return "You are full, stop trying to eat! " + toStringHunger();
        }
        int index = 0;
        Item tempItem;
        while(index < playerItems.size())
        {
            tempItem = playerItems.get(index);
            if(eatString.equalsIgnoreCase(tempItem.getName()))
            {
                if(tempItem.getEdible())
                {
                    currentHunger += tempItem.getHungerValue();
                    removeItem(tempItem);
                    dropWeight(tempItem);
                    if(tempItem.getHungerValue() < 0)
                    {
                        return "That didn't taste good. Oh no! " + toStringHunger();
                    }
                    if(currentHunger > maxHunger)
                    {
                        currentHunger = maxHunger;                    
                    }
                        return "Yum Yum Yum, that was good. " + toStringHunger();
                }
                else
                {
                    return "You can not eat that item.";
                }
            }
            index++;
        }
        return "You do not have that item.";        
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
     * @return String
     */
    public String takeItem(String newItem)
    {
        Item tempItem = currentRoom.containsItem(newItem);        
        if(tempItem == null)
        {
            return "This location does not have that item";
        }
        else if(tempItem.getWeight() <= (maxWeight - currentWeight))
        {
            addItem(tempItem);
            currentRoom.removeItem(tempItem);
            addWeight(tempItem);
            return "You picked up the " + tempItem.getName() + toStringWeight();
        }
        else
        {
            return("You can't carry the " + tempItem.getName());
        }
    }
    
    /**
     * Adds the weight of the item to currentWeight
     * @param tempItem
     */
    private void addWeight(Item tempItem)
    {
        currentWeight += tempItem.getWeight();
    }
    
    /**
     * Drops an item from the players item list
     * @param itemDrop : item name to be dropped
     * @return String
     */
    public String dropItem(String itemDrop)
    {
        int index = 0;
        Item tempItem;
        while(index < playerItems.size())
        {
            tempItem = playerItems.get(index);
            if(itemDrop.equalsIgnoreCase(tempItem.getName()))
            {
                removeItem(tempItem);
                dropWeight(tempItem);
                currentRoom.addItem(tempItem);
                return "You dropped the " + tempItem.getName() + toStringWeight();
            }
            index++;
        }
        return "You do not have that item";
    }
    
    /**
     * Removes the weight of the item from currentWeight
     * @param tempItem
     */
    private void dropWeight(Item tempItem)
    {
        currentWeight -= tempItem.getWeight();
    }
    
    /**
     * Returns player weight information in the form:
     * Carry weight: 300/600
     * @return String
     */
    public String toStringWeight()
    {
        return "\nCarry weight: " + currentWeight + "/" + maxWeight;
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
        itemsList += toStringWeight();
        return itemsList;
    }
    
    /**
     * Returns a formatted string like the following:
     * 
     * You are outside the main entrance of the university.
     * Door Exits: east south north
     * Item(s) at this location.
     * -Flashlight-Small flashlight with batteries, 100
     * 
     * Your Status
     * Current hunger level: 100/100
     * Carry weight: 0/600
     * Moves: 0/15
     * 
     * @return String     * 
     */
    public String getLongDescription(){
        String returnString = currentRoom.getLongDescription() + "\nYour Status" +
                              toStringHunger() + toStringWeight() + toStringMoves();
        return returnString;
    }
    
    /**
     * Charges the transporter to the current room if the player has it
     * @return String
     */
    public String chargeTransporter()
    {
        int index = 0;
        Item tempItem;
        while(index < playerItems.size())
        {
            tempItem = playerItems.get(index);
            if(tempItem.getName().equalsIgnoreCase("transporter"))
            {
                tempItem.setChargedRoom(currentRoom);
                return "The transporter has been set to: " + currentRoom.getShortDescription();
            }
            index ++;
        }
        return "You do not have the transporter";
    }
    
    /**
     * Moves the user to the chargedRoom if they have the transporter
     * @return String
     */
    public String useTransporter()
    {
        int index = 0;
        Item tempItem;
        while(index < playerItems.size())
        {
            tempItem = playerItems.get(index);
            if(tempItem.getName().equalsIgnoreCase("transporter"))
            {
                if(tempItem.getChargedRoom() == null)
                {
                    return "\nThe transporter is not charged with a room\n";
                }
                else if(tempItem.getChargedRoom().equals(currentRoom))
                {
                    return "\nYou are already in the charged room\n";
                }
                else
                {
                    currentRoom = tempItem.getChargedRoom();
                    return "\nYou are being transported to: " + currentRoom.getShortDescription() + "\n";
                }
            }
            index++;
        }
        return "You do not have the transporter";
    }

}