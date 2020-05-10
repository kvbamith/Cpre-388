package edu.iastate.shoppinglist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * a view model class for running the main page activity and shopping list activity
 */
public class ShoppingListFilesViewModel extends ViewModel implements Serializable {


    /**
     * a public LiveData container to store the folder of shopping lists
     */
    public MutableLiveData<ShoppingListFiles> manyLists = new MutableLiveData<>();

    /**
     * a constructor to create a new shopping lists folder
     */
    public ShoppingListFilesViewModel(){
        manyLists.setValue(new ShoppingListFiles());
    }

    /**
     * add a new list into the folder with the given shopping list name
     * @param name
     *      the shopping list name that needs to be created
     */
    public void addListByName(String name){
        ShoppingListFiles temp =manyLists.getValue();
        temp.addByName(name);
        manyLists.setValue(temp);
    }

    /**
     * add the given shopping list to the folder and update it in the local file
     * @param oneList
     *      the shopping list to add
     * @param act
     *      reference to main activity
     */
    public void addList(ShoppingList oneList, MainActivity act){
        if(oneList !=null) {
            ShoppingListFiles temp = manyLists.getValue();
            temp.add(oneList);
            manyLists.setValue(temp);
        }
        try {
            ShoppingListFiles temp =manyLists.getValue();
            temp.writeToFolderMain(act);
            manyLists.setValue(temp);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * remove the given shopping list to the folder and update it in the local file
     * @param i
     *      the shopping list to remove
     * @param act
     *      reference to main activity
     */
    public void removeListMain(ShoppingList i, MainActivity act){
        if( i !=null) {
            ShoppingListFiles temp =manyLists.getValue();
            temp.delete(i.getListName());
            manyLists.setValue(temp);
        }
        try {
            ShoppingListFiles temp =manyLists.getValue();
            temp.writeToFolderMain(act);
            manyLists.setValue(temp);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to get the shopping lists folder from the local file and update the object
     * @param act
     *      reference to MainActivity
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void getFromFolder(MainActivity act) throws IOException, ClassNotFoundException {
        File f =new File(act.getApplicationContext().getFilesDir()+"/ShoppingListsFiles");
        FileInputStream file = new FileInputStream(f);
        ObjectInputStream output = new ObjectInputStream(file);
        manyLists.setValue((ShoppingListFiles) output.readObject());
        output.close();
        file.close();

    }

    /**
     * add an item to the shopping list with the given name
     * @param listName
     *      the name of the shopping list
     * @param name
     *      the name of the new item in the shopping list
     * @param quantity
     *      the quantity of the new item in the shopping list
     * @param unit
     *      the unit of measurement of the new item in the shopping list
     * @param act
     *      reference to main activity
     */
    public void addItem(String listName, String name, double quantity, String unit, ShoppingListFilesActivity act){
        Item i= new Item(name,quantity,unit);

        ShoppingList temp =manyLists.getValue().getShoppingList(listName);
        if (temp == null) {
            temp=new ShoppingList(listName);
        }
        temp.addItem(i);
        if(manyLists.getValue().containsShoppingList(temp)) {
            ShoppingListFiles temp2=manyLists.getValue();
            temp2.setShoppingList(temp);

        }else{
            ShoppingListFiles temp2=manyLists.getValue();
            temp2.add(temp);
            manyLists.setValue(temp2);
        }
        try {
            ShoppingListFiles temp2 =manyLists.getValue();
            temp2.writeToFolder(act);
            manyLists.setValue(temp2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * remove an item to the shopping list with the given name
     * @param listName
     *      the name of the shopping list
     * @param i
     *      the item that needs to be removed from teh given shopping list
     */
    public void removeItem(String listName, Item i){

        ShoppingList oneList= manyLists.getValue().getShoppingList(listName);
        if( i !=null) {
            oneList.removeItem(i);
        }
        ShoppingListFiles temp=manyLists.getValue();
        temp.setShoppingList(oneList);
        manyLists.setValue(temp);
    }

}
