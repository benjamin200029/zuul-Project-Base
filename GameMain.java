/**
 * GameMain starts a new game.
 *
 * @author Benjamin Adelson & Erik Cooke
 * @version (2019.10.30)
 */
public class GameMain
{
    /**
     * Main method for the game. creates a new game and runs the play method
     *
     * @param  args[]
     */
    public static void main(String args[])
    {
        Game game = new Game();
        game.play();
    }
}
