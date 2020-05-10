package edu.iastate.shoppinglist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * a object created to store a shopping list of items
 */
public class ShoppingList implements Serializable {

    /**
     * an array list of the items in the shopping list
     */
    private ArrayList<Item> list;

    /**
     * name of the shopping list
     */
    private String listName;


    /**
     * constructor that creates a empty shopping list
     */
    public ShoppingList(){
        list = new ArrayList<>();
    }

    /**
     * constructor that creates an empty shopping list based on the given name
     * @param name
     *      name of the new shopping list
     */
    public ShoppingList(String name){
        listName=name;
        list = new ArrayList<>();
    }

    /**
     * constructor that creates a list based on the given list by making deep copy
     * @param other
     *      the shopping list to be created
     */
    public ShoppingList(ShoppingList other){
        listName=other.getListName();
        list = other.getList();
    }

    /**
     * setter method to set the list's name
     * @param listName
     *          name of the list
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * getter method to get the list name
     * @return
     *      return the list's name
     */
    public String getListName() {
        return listName;
    }

    /**
     * getter method to get the arraylist of items
     * @return
     *      an array list of items
     */
    public ArrayList<Item> getList() {
        return list;
    }

    /**
     * setter method to set the list in the shopping list
     * @param list
     *        the array list that needs to be set to this shopping list
     */
    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    /**
     * adds the given item to the list
     * @param item
     *         an item object
     */
    public void addItem(Item item){
        list.add(item);
    }

    /**
     * removes the give item in a shopping list
     * @param item
     *          the item to be removed
     */
    public void removeItem(Item item){
        list.remove(item);
    }

    /**
     * a method used to write the list to a file whenever necessary
     * @param act
     *          the activity to take as reference
     * @throws IOException
     */
    public void writeToFile(ShoppingListFilesActivity act) throws IOException {
        File f =new File(act.getApplicationContext().getFilesDir(),this.listName);
        FileOutputStream file = new FileOutputStream(f) ;
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(this);
        output.close();
        file.close();

    }
}
