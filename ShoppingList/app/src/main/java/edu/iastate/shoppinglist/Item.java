package edu.iastate.shoppinglist;

import java.io.Serializable;

/**
 *  A object used to store one item in a shopping list
 */
public class Item implements Serializable {

    /**
     * used to store the name of the item created
     */
    private String itemName;

    /**
     * used to store the quantity of the item
     */
    private double quantity;

    /**
     * stores the unit of measurement for the quantity added
     */
    private String units;

    /**
     * constructor of item used to create an empty item
     */
    public Item(){
        itemName= "";
        quantity=0.0;
        units="";
    }

    /**
     * constructor of item object, creates item based on the given parameters
     * @param name
     *          name of the item
     * @param quantity
     *          quantity of the item
     * @param unit
     *          unit of measurement of the item
     */
    public Item(String name, double quantity, String unit){
        this.itemName=name;
        this.quantity=quantity;
        this.units=unit;
    }

    /**
     * a getter method to get the item name
     * @return
     *      string which is the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * getter method to get the item quantity
     * @return
     *      a double which is the item's quantity
     */
    public double getQuantity() {
        return quantity;
    }


    /**
     * a getter method to get the units of the item
     * @return
     *      a string that returns the unit of measurement
     */
    public String getUnits() {
        return units;
    }
}

