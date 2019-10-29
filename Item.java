
/**
 * Class Item - an item in an adventure game.
 *
 * @author Benjamin Adelson & Erik Cooke
 * @version 2019.10.23
 */
public class Item
{
    // Name of the item
    private String description;
    // holds how much the item weighs.
    private int weight;

    /**
     * Constructor for objects of class Item
     * @param description - descibes the item
     * @param weight - how much does it weigh?
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
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
     * @return a float of the weight of this item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns a description of the item in form:
     *   Item: Flashlight
     *   Weight: .5
     */
    public String getLongDescription()
    {
        return "Item: " + description + "\nWeight: " + weight;
    }
}
