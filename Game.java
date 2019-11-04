
//import java.util.Stack;
/**
 *  This class is the main class of the "College of Raritan" application. 
 *  "College of Raritan" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. Pick up and drop items, eat some items, use a personal transporter.
 * 
 *  To play this game, create an instance of the class GameMain.
 * 
 *  This class creates and initialises all the others: it creates all
 *  rooms, items, player, and doors, creates the parser and starts the game.  It also evaluates and
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
    //private Room currentRoom;
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
        gym, library, parkingLot1, parkingLot2, sciCenter, cafeteria, collegeCenter, offCampus;
        
        Item flashlight, textbook, apple, mushroom, key, transporter;
        
        NPC appleannie;
      
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
        offCampus = new Room("off campus and heading home (type back to escape)");
        
        // create items
        flashlight = new Item("Flashlight","Small flashlight with batteries", 100);
        textbook = new Item("Textbook", "Object Oriented Programming", 300);
        apple = new Item("Apple", "Red and juicy", 100, 20);
        mushroom = new Item("Mushroom", "White, strange looking", 50, -30);
        transporter = new Item("Transporter", "A personal transporter. Use: charge transporter, use transporter", 100);                                
                
        key = new Item("Key", "Opens Office", 1);
        
        // add items to rooms
        outside.addItem(flashlight);
        outside.addItem(textbook);
        outside.addItem(mushroom);
        outside.addItem(transporter);
        parkingLot1.addItem(apple);
        cafeteria.addItem(apple);
        cafeteria.addItem(apple);
        cafeteria.addItem(apple);
        collegeCenter.addItem(apple);
        
        //create a key item
        studentCenter.addItem(key);
        //guidance.addItem(key);

        
        // initialise room exits
        
        new Door(outside,"north",parkingLot1,"south",null);
        new Door(outside,"south",parkingLot2,"north",null);
        new Door(outside,"east",collegeCenter,"west",null);
        
        new Door(parkingLot1,"east",offCampus,"",null);
        
        new Door(collegeCenter,"north",artCenter,"south",null);
        new Door(collegeCenter,"south",library,"north",null);
        new Door(collegeCenter,"east",studentCenter,"west",null);

        
        new Door(artCenter,"north",theater,"south",null);
        
        new Door(studentCenter,"up",gym,"down",null);
        new Door(studentCenter,"down",cafeteria,"up",null);
        
        new Door(library,"east",sciCenter,"west",null);
        new Door(library,"up",guidance,"down",null);
        
        new Door(guidance,"up",office,"down",key);
        
        new Door(sciCenter,"south",computerLab,"north",null);
        new Door(sciCenter,"east",sciLab,"west",null);
        
        // NPC's
        appleannie = new NPC("Annie", "Hello, I am Apple Annie and I give out apples to those who are nice",
                               "Oh, you are so nice dear, here is an apple");
        
        appleannie.addItem(apple);
        appleannie.addItem(apple);
        appleannie.addItem(apple);
        
        collegeCenter.addNpc(appleannie);
        appleannie.setRoom(collegeCenter);
        
        //currentRoom = outside;
        // start game outside
        return outside;
        
        
        //old code that was replaced
        //outside.setExit("north", parkingLot1);
        //outside.setExit("south", parkingLot2);
        //outside.setExit("east", collegeCenter);
        //parkingLot1.setExit("south",outside);
        //parkingLot2.setExit("north",outside);
        //collegeCenter.setExit("north", artCenter);
        //collegeCenter.setExit("south", library);
        //collegeCenter.setExit("east", studentCenter);
        //collegeCenter.setExit("west", outside);
        //theater.setExit("south",artCenter);
        //artCenter.setExit("north",theater);
        //artCenter.setExit("south",collegeCenter);
        //studentCenter.setExit("west",collegeCenter);
        //studentCenter.setExit("up",gym);
        //studentCenter.setExit("down",cafeteria);
        //gym.setExit("down",studentCenter);
        //cafeteria.setExit("up",studentCenter);
        //library.setExit("north",collegeCenter);
        //library.setExit("east",sciCenter);
        //library.setExit("up",guidance);
        // guidance.setExit("up",office);
        //guidance.setExit("down",library);
        //office.setExit("down", guidance);
       // new Door(guidance,"down",office,"up",null);
        //sciCenter.setExit("west",library);
        //sciCenter.setExit("east",sciLab);
        //sciCenter.setExit("south",computerLab);
        //computerLab.setExit("north", sciCenter);
        //sciLab.setExit("west",sciCenter);
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
    
    /**
     * Prints out of time message
     */
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
                eat(command);
                break;
                
            case BACK:
                goBack(command);
                break;

            case GO:
                goRoom(command);
                break;
                
            case TAKE:
                takeItem(command);
                break;
                
            case DROP:
                dropItem(command);
                break;
                
            case ITEMS:
                listPlayerItems();
                break;
                
            case CHARGE:
                chargeTransporter(command);
                break;
                
            case USE:
                useTransporter(command);
                break;
                
            case SAY:
                say(command);
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
        //Room nextRoom = player.getCurrentRoom().getExit(direction);
        Door door = player.getCurrentRoom().getDoor(direction);

        if (door == null) {
            System.out.println("There is no door!");
        }
        else {
            if(player.access(direction)){
                //currentRoom = nextRoom;
                //currentRoom = door;
                System.out.println(player.getLongDescription());
            }else{
                System.out.println("The door is locked! Find the key.");
            }
            //player.enterRoom(door);
            //look();
        }
    }
    
    /**
     * Takes the player back to previous room
     * @param command
     */
    public void goBack(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Back where?");
            return;
        }
        player.goBack();
        look();
    }
    
    /**
     * Picks up an item from the room if it is there.
     * @param command : what item to take?
     * 
     */
    private void takeItem(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();        
        System.out.println(player.takeItem(itemName));
    }
    
    /**
     * Drops an item from players item list into current room
     * @param command : What item to drop?
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        System.out.println(player.dropItem(itemName));
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
    private void look()
    {
         System.out.println(player.getLongDescription());
    }
    
    /**
     * List the items the player is carrying
     */
    private void listPlayerItems()
    {
        System.out.println(player.listItems());
    }
   
    // /**
     // * Enters the selected room and prints the description
     // * @param nextRoom 
     // */
    // private void enterRoom(Room nextRoom){
        // currentRoom = nextRoom;
        // look();
    // }
    
    public void eat(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Eat what?");
            return;
        }
        String eatString = command.getSecondWord();
        System.out.println(player.eat(eatString));
    }
    
    /**
     * Charges the transporter if player has it
     */
    public void chargeTransporter(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Charge what?");
            return;
        }
        if(command.getSecondWord().equalsIgnoreCase("transporter"))
        {
            System.out.println(player.chargeTransporter());
        }
        else
        {
            System.out.println("You can't charge that item");
        }
    }
    
    /**
     * Uses the transporter
     * @param command
     */
    public void useTransporter(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Use what?");
            return;
        }
        if(command.getSecondWord().equalsIgnoreCase("transporter"))
        {
            System.out.println(player.useTransporter());
            look();
        }
        else
        {
            System.out.println("You can't use that item");
        }
    }
    
    /**
     * The player is talking
     * @param command
     */
    public void say(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Say what?");
            return;
        }
        if(command.getSecondWord().equalsIgnoreCase("please"))
        {
            if(player.getCurrentRoom().isThereNpc())
            {
            NPC tempNpc = player.getCurrentRoom().getNpc();
            System.out.println(tempNpc.getMessage());
            look();
            }
            else
            {
                System.out.println("You are talking to yourself");
            }
        }
        else
        {
            System.out.println("Maybe you need to use the magic word");
        }
    }
    
    /**
     * Enters the selected room and prints the description
     */
    // private void openDoor(Door nextDoor){
        // currentRoom = nextDoor;
        // System.out.println(currentRoom.getLongDescription());
    // }
}