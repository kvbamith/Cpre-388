package edu.iastate.shoppinglist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * a object to store multiple shopping lists
 */
public class ShoppingListFiles implements Serializable {

    /**
     * an arry list of the shopping list objects
     */
   private ArrayList<ShoppingList> folder;

    /**
     * constructor to create a new empty shopping lists folder
     */
    public ShoppingListFiles(){
        folder=new ArrayList<>();

    }

    /**
     * adds a new empty shopping list to the folder with the given list name
     * @param name
     *      name of the new shopping list to add
     */
    public void addByName(String name){
        ShoppingList temp = new ShoppingList(name);
        folder.add(temp);
    }

    /**
     * adds the given shopping list to the folder
     * @param l
     *      a shopping list object
     */
    public void add(ShoppingList l){
        ShoppingList temp = new ShoppingList(l.getListName());
        temp.setList(l.getList());
        folder.add(temp);
    }

    /**
     * deletes the the shopping list with the given name in the folder
     * @param name
     *          the name of the shopping list that needs to be a removed
     */
    public void delete(String name){
        for(ShoppingList i:folder){
            if(i.getListName().equals(name)){
                folder.remove(i);
                break;
            }
        }
    }

    /**
     * getter method to return the folder of shopping lists
     * @return
     *         arraylist of shopping lists objects
     */
    public ArrayList<ShoppingList> getFolder() {
        return folder;
    }

    /**
     * getter method to get a shopping list at a given name if it exists
     * @param name
     *      name of the shopping list to return
     * @return
     *      the shopping list with the given name
     */
    public ShoppingList getShoppingList(String name){
        for(ShoppingList i:folder){
            if(i.getListName().equals(name)){
                return i;
            }
        }
        return null;
    }

    /**
     * setter method to set an existing shopping list based on the given shopping list
     * @param list
     *      the shopping list that the folder needs to update to
     */
    public void setShoppingList(ShoppingList list){
        for(ShoppingList i:folder){
            if(i.getListName().equals(list.getListName())){
                i.setList(list.getList());
            }
        }

    }

    /**
     * method to write the folder into a files whenever necessary
     * @param act
     *      with reference to the ShoppingListFilesActivity
     * @throws IOException
     */
    public void writeToFolder(ShoppingListFilesActivity act) throws IOException {
        File f =new File(act.getApplicationContext().getFilesDir(),"ShoppingListsFiles");
        FileOutputStream file = new FileOutputStream(f) ;
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(this);
        output.close();
        file.close();

    }

    /**
     * another method to write the folder to a file but using the MainActivity
     * @param act
     *          the reference of MainActivity
     * @throws IOException
     */
    public void writeToFolderMain(MainActivity act) throws IOException {
        File f =new File(act.getApplicationContext().getFilesDir(),"ShoppingListsFiles");
        FileOutputStream file = new FileOutputStream(f) ;
        ObjectOutput output = new ObjectOutputStream(file);
        output.writeObject(this);
        output.close();
        file.close();

    }

    /**
     * checks if th given list exists in the folder
     * @param list
     *          the list to check if it exsists
     * @return
     *      a boolean on whether the given list exists
     */
    public boolean containsShoppingList(ShoppingList list){
        for(ShoppingList i:folder){
            if(i.getListName().equals(list.getListName())){
                return true;
            }
        }
        return false;
    }
}
