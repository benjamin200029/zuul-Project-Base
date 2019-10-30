
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
     *   Item: Flashlight, a small flashlight with batteries
     *   Weight: 100
     */
    public String getLongDescription()
    {
        return "" + name + "-" + description + ", " + weight;
    }
}
