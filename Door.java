/**
 * Class Door - A door that connects with two rooms.
 * Some doors will have locks and require a key to open.
 *
 * @author Benjamin Adelson & Erik Cooke
 * @version 2019.10.30
 */
public class Door
{
    // instance variables
    private Room area1;
    private Room area2;
    private String direction1;
    private String direction2;
    private Item key;
    private boolean locked = false;
    
    /**
     * Constructor for objects of class Door
     * creates a new door between the two room areas.
     * The two doors can also be locked with the given key
     */
    public Door(Room area1,String direction1, Room area2, String direction2, Item key)
    {
        // initialise instance variables
        this.area1 = area1;
        this.area2 = area2;
        this.direction1 = direction1;
        this.direction2 = direction2;
        area1.setDoor(direction1,this);
        area2.setDoor(direction2,this);
        this.key = key;
        lock(key);
    }
    
    /**
     * Trying to lock the door with the key.
     * will return true if the door was locked correctly
     */
    public boolean lock(Item key)
    {
       if(this.key == key && key != null){
           locked = true;
        }
        return locked;
    }
    
    /**
     * Trying to unlock the door with the key.
     * will return true if the door was unlocked correctly
     */
    public boolean unlock(Item key)
    {
       if(this.key == key && key != null){
           locked = false;
        }

        return !locked;
    }
    
    public Room open(Room fromRoom)
    {
        if(locked){
            return null;
        }
        if(fromRoom == area1){
            return area2;
        }
        else if (fromRoom == area2){
            return area1;
        }
        else {
            return null;
        }
    }

   
}
