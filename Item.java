import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Item - an item in an adventure game.
 *
 * @author Benjamin Adelson & Erik Cooke
 * @version 2019.10.23
 */
public class Item
{
    // Name of the item.
    private String name;
    // Description of the item.
    private String description;
    // holds how much the item weighs.
    private int weight;
    
    //location of the item key
    private HashMap<String, Item> items;

    /**
     * Constructor for objects of class Item
     * @param description - descibes the item
     * @param weight - how much does it weigh?
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        items = new HashMap<String,Item>();

    }
    
    /**
     * Returns an Iterator over the items
     * @return a Iterator
     */
    
    public Iterator<Item> iterator()
    {
        return items.values().iterator();
    }

    /**
     * Returns the name of this item.
     * 
     * @return a String of the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the description of this item.
     *
     * @return  a String of the description.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Return the weight of this item
     * 
     * @return an int of the weight of this item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns a description of the item in form:
     *   Flashlight-a small flashlight with batteries, 100
     */
    public String getLongDescription()
    {
        return name + "-" + description + ", " + weight;
    }
}
