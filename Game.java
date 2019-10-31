
//import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 * @author Benjamin Adelson and Erik Cooke
 * @version 2019.10.21
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    //private Stack<Room> roomHistory;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        // old code
        //createRooms();
        //parser = new Parser();
        //roomHistory = new Stack<Room>();
                
        player = new Player("Steve");
        Room startRoom = createRooms();
        player.startRoom(startRoom); // start at the beginning, which is outside of the college
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        // new rooms created after office
        Room outside, theater, studentCenter, computerLab, office, sciLab, guidance, artCenter,
        gym, library, parkingLot1, parkingLot2, sciCenter, cafeteria, collegeCenter;
        
        Item flashlight, textbook,key;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a Lecture Theater");
        studentCenter = new Room("in the Student Center");
        computerLab = new Room("in a Computing Lab");
        office = new Room("in the Computing Admin Office");
        sciLab = new Room("in the Science lab");
        guidance = new Room("in the Guidance Center");
        artCenter = new Room("in the arts center");
        gym = new Room("in the Gym");
        library = new Room("in the Library");
        parkingLot1 = new Room("in the Northern parking lot");
        parkingLot2 = new Room("in the Southern parking lot");
        sciCenter = new Room("in the Science Center");
        cafeteria = new Room("in the Cafeteria");
        collegeCenter = new Room("in the College Center");
        
        // create items
        flashlight = new Item("Flashlight","Small flashlight with batteries", 100);
        textbook = new Item("Textbook", "Object Oriented Programming", 300);
        key = new Item("Key", "Opens Office", 1);
        // add items to rooms
        outside.addItem(flashlight);
        outside.addItem(textbook);
        //create a key item
        //guidance.addItem(key);

        
        // initialise room exits
        outside.setExit("north", parkingLot1);
        outside.setExit("south", parkingLot2);
        outside.setExit("east", collegeCenter);
        
        parkingLot1.setExit("south",outside);
        parkingLot2.setExit("north",outside);

        collegeCenter.setExit("north", artCenter);
        collegeCenter.setExit("south", library);
        collegeCenter.setExit("east", studentCenter);
        collegeCenter.setExit("west", outside);
        
        theater.setExit("south",artCenter);
        
        artCenter.setExit("north",theater);
        artCenter.setExit("south",collegeCenter);
        
        studentCenter.setExit("west",collegeCenter);
        studentCenter.setExit("up",gym);
        studentCenter.setExit("down",cafeteria);
        
        gym.setExit("down",studentCenter);
        
        cafeteria.setExit("up",studentCenter);
        
        library.setExit("north",collegeCenter);
        library.setExit("east",sciCenter);
        library.setExit("up",guidance);
        
       // guidance.setExit("up",office);
        new Door(office,"up",guidance,"down",null);

        guidance.setExit("down",library);
        
        //office.setExit("down", guidance);
        new Door(guidance,"down",office,"up",key);

        
        sciCenter.setExit("west",library);
        sciCenter.setExit("east",sciLab);
        sciCenter.setExit("south",computerLab);

        computerLab.setExit("north", sciCenter);
        
        sciLab.setExit("west",sciCenter);


        currentRoom = outside;  // start game outside
        
        return outside;
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(player.gameOver()){
                printGameOver();
                finished = true;
            }
            if(player.starved()){
                printStarved();
                finished = true;
            }
        }
        System.out.println("\nThank you for playing.  Good bye.");
    }
    
    private void printGameOver(){
        System.out.println("You are out of time, campus is closed!");
    }
  
    /**
     * Prints starved message
     */
    private void printStarved()
    {
        System.out.println("You have starved to death!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the College of Raritan!");
        System.out.println("College of Raritan is a new, incredibly boring educational game that gives you a simulation actully being in college.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());
        look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
                
            case HELP:
                printHelp();
                break;
            
            case LOOK:
                look();
                break;
                
            case EAT:
                System.out.println(player.eat());
                break;
                
            case BACK:
                if(command.hasSecondWord())
                {
                    System.out.println("Back where?");
                    break;
                }
                player.goBack();
                look();
                break;

            case GO:
                goRoom(command);
                break;
                
            case TAKE:
                takeItem(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }
    
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the college.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        Door door = player.getCurrentRoom().getDoor(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            //roomHistory.push(currentRoom);
            player.enterRoom(nextRoom);
            
            //currentRoom = nextRoom;
            //System.out.println(player.getCurrentRoom().getLongDescription() + player.getHunger());
            look();
        }
    }
    
    public void takeItem(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();
        
        player.takeItem(itemName);
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Prints the description of the room and the exits.
     */
    public void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription() +
        player.toStringHunger() + player.toStringMoves());
    }
    
    /**
     * Take item in current room. If the room contains an item,
     * it works, if not an error will occur.
     * (Not complete)
     */
    private void take(Command command){
        System.out.println("What Item do you want to take?");

    }
   
    /**
     * Enters the selected room and prints the description
     */
    private void enterRoom(Room nextRoom){
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
    }
    
    // /**
     // * Go back to the previous room
     // */
    // public void goBack(Command command)
    // {
        // if(command.hasSecondWord()){
            // System.out.println("Back where?");
            // return;
        // }
        // if (roomHistory.isEmpty()){
        // System.out.println("There are no rooms you can backtrack to.");
        // }
        // else{
            // Room previousRoom = roomHistory.pop();
            // enterRoom(previousRoom);
        // }
    // }
}