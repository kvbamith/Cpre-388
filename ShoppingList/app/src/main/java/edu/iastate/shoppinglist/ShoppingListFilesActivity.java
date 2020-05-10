package edu.iastate.shoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * the activity to display the items in a shopping list
 */
public class ShoppingListFilesActivity extends AppCompatActivity {

    /**
     * the shopping lists folder view model object
     */
    private ShoppingListFilesViewModel shoppingListFilesViewModel;

    /**
     * the table that displays the items
     */
    private TableLayout table;
    /**
     * the shopping lists folder view model object
     */
    final String TAG = "MainActivity";

    /**
     * a variable to the store the item name entered by the user
     */
    private String item_Name="";

    /**
     * a variable to the store the item quantity entered by the user
     */
    private double item_quantity=0.0;

    /**
     * a variable to the store the item unit of measurement entered by the user
     */
    private String item_units="";

    /**
     * a variable to the get the specific list from the folder based on the name of the list
     */
    private static String listName;

    /**
     * a button to return from the shopping list view to the main page view
     */
    private Button returnToMain;


    /**
     * the first method called when the this activity is called to initialize the screen and the instance variable for this activity
     * @param savedInstanceState
     *      current instance state of the app
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglistfiles);

        table = findViewById(R.id.item_table);
        returnToMain = findViewById(R.id.back_to_main);

        shoppingListFilesViewModel=new ShoppingListFilesViewModel();

        if(savedInstanceState == null){
                shoppingListFilesViewModel.manyLists.setValue(new ShoppingListFiles());
        }
            shoppingListFilesViewModel.addListByName(listName);


            ShoppingListFiles brandNew = (ShoppingListFiles) getIntent().getSerializableExtra("ShoppingListObject");
            ShoppingListFiles reOpened = (ShoppingListFiles) getIntent().getSerializableExtra("reopenShoppingListObject");
            String brandNewName= getIntent().getStringExtra("ShoppingList");
            String reOpenedName= getIntent().getStringExtra("reopenShoppingList");
            if(reOpened !=null && reOpenedName !=null ){
                shoppingListFilesViewModel.manyLists.setValue(reOpened);
                listName=reOpenedName;
            }else{
                shoppingListFilesViewModel.manyLists.setValue(brandNew);
                listName=brandNewName;
            }

        ShoppingList oneList = new ShoppingList();
        if(shoppingListFilesViewModel.manyLists.getValue().getShoppingList(listName)!=null){
            oneList = shoppingListFilesViewModel.manyLists.getValue().getShoppingList(listName);
        }
            for (Item item : oneList.getList()) {
                final TableRow row = new TableRow(this);
                TextView name = new TextView(this);
                name.setText(getString(R.string.item_name_display, item.getItemName()));
                name.setTextSize(20);
                name.setPadding(0, 0, 30, 0);
                TextView quantity = new TextView(this);
                quantity.setText(getString(R.string.item_quantity_display, item.getQuantity(), item.getUnits()));
                quantity.setTextSize(20);
                quantity.setPadding(0, 0, 30, 0);

                TextView textView = new TextView(this);
                textView.setText("            ");

                Button removeButton = new Button(this);
                removeButton.setText(R.string.remove_button);
                removeButton.setTag(item);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeEntry(view);
                        table.removeView(row);
                    }
                });

                row.addView(name);
                row.addView(quantity);
                row.addView(textView);
                row.addView(removeButton);

                table.addView(row);
            }
        EditText nameEdit = findViewById(R.id.Item_Name);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    item_Name = charSequence.toString();
                } catch(Exception e) {
                    item_Name = "";
                }
                Log.d(TAG, "onTextChanged called");
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        EditText quantityEdit = findViewById(R.id.Item_quantity);
        quantityEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    item_quantity = Double.parseDouble(charSequence.toString());
                } catch(Exception e) {
                    item_quantity = 0.0;
                }
                Log.d(TAG, "onTextChanged called");
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        EditText unitEdit = findViewById(R.id.Item_units);
        unitEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    item_units = charSequence.toString();
                } catch(Exception e) {
                    item_units = "";
                }
                Log.d(TAG, "onTextChanged called");
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    /**
     * adds an item into the list and updates the view accordingly
     * @param view
     *      a reference to the current view
     */
    public void addEntry(View view) {
        Item temp = new Item(item_Name,item_quantity,item_units);
        shoppingListFilesViewModel.addItem(listName,item_Name,item_quantity,item_units, this);
        final TableLayout table = findViewById(R.id.item_table);
        final TableRow row = new TableRow(this);
        TextView name = new TextView(this);
        name.setText(getString(R.string.item_name_display,temp.getItemName()));
        name.setTextSize(20);
        name.setPadding(0, 0, 30, 0);
        TextView quantity = new TextView(this);
        quantity.setText(getString(R.string.item_quantity_display, temp.getQuantity(), temp.getUnits()));
        quantity.setTextSize(20);
        quantity.setPadding(0, 0, 30, 0);

        TextView textView = new TextView(this);
        textView.setText("            ");

        final Button removeButton = new Button(this);
        removeButton.setText(R.string.remove_button);
        removeButton.setTag(temp);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeEntry(view);
                table.removeView(row);
            }
        });
        row.addView(name);
        row.addView(quantity);
        row.addView(textView);
        row.addView(removeButton);

        table.addView(row);
    }

    /**
     * a method created to remove the item from the folder object with a reference to the item when the remove item button is clicked
     * @param view
     */
    public void removeEntry(View view) {
        shoppingListFilesViewModel.removeItem(listName,(Item)view.getTag());
    }

    /**
     * creates an intent for MainActivity to store the folder object and the shopping list name when swapping to this activity
     * @param context
     *          reference to context
     * @param listName
     *          the name of the list to store in the intent
     * @param files
     *          the folder object to store in the intent
     * @return
     *      return the intent created
     */
    public static Intent createIntent(Context context, String listName,ShoppingListFiles files) {
        Intent intent = new Intent(context, ShoppingListFilesActivity.class);

        intent.putExtra("ShoppingList", listName);
        intent.putExtra("ShoppingListObject", files);
        return intent;
    }

    /**
     * does what the createintent method does, but creates new references to store when we reopen an existing list
     * @param context
     *          reference to context
     * @param listName
     *          the name of the list to store in the intent
     * @param files
     *          the folder object to store in the intent
     * @return
     *      return the intent created
     */
    public static Intent recreateIntent(Context context, String listName,ShoppingListFiles files) {
        Intent intent = new Intent(context, ShoppingListFilesActivity.class);

        intent.putExtra("reopenShoppingList", listName);
        intent.putExtra("reopenShoppingListObject", files);
        return intent;
    }

    /**
     * a on click method for the return to main button creating in this activity
     * @param view
     *      reference to the current view
     */
    public void onReturnToMainClicked(View view){
        Intent myIntent = new Intent(ShoppingListFilesActivity.this,MainActivity.class);
        myIntent.putExtra("returnShoppingListObject", shoppingListFilesViewModel.manyLists.getValue());
        startActivity(myIntent);
    }



}
