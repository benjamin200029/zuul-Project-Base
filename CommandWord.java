/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author Benjamin Adelson and Erik Cooke
 * @version 2019.10.21
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), EAT("eat"), BACK("back"),
    TAKE("take"),DROP("drop"),ITEMS("items"),CHARGE("charge"),USE("use"), SAY("say");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
