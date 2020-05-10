package edu.iastate.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.IOException;

/**
 * the main activity for the shopping list app
 */
public class MainActivity extends AppCompatActivity implements Observer<ShoppingListFiles>{

    /**
     * the shopping lists folder view model object
     */
    private ShoppingListFilesViewModel shoppingListFilesViewModel;

    /**
     * the shopping lists folder view model object
     */
    final String TAG = "MainActivity";

    /**
     * the name of a specific list that needs to be sent to the ShoppingListFilesActivity
     */
    private String list_Name = "";

    /**
     * the new name that a list should be renamed to
     */
    private String rename_Name="";

    /**
     * the display for the rename feature of the app
     */
    private LinearLayout renameArea;

    /**
     * a button to confirm whether one wants to rename it to the given name
     */
    private Button renameConfirmButton;

    /**
     * the first method called when the this activity is called to initialize the screen and the instance variable for this activity
     * @param savedInstanceState
     *      current instance state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renameArea = findViewById(R.id.Rename_View);
        renameConfirmButton = findViewById(R.id.Rename_Confirm);
        shoppingListFilesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ShoppingListFilesViewModel.class);
        shoppingListFilesViewModel.manyLists.observe(this, this);

        renameArea.setVisibility(View.GONE);
        renameConfirmButton.setVisibility(View.GONE);

        ShoppingListFiles allFiles = (ShoppingListFiles) getIntent().getSerializableExtra("returnShoppingListObject");
        if (allFiles != null) {
            shoppingListFilesViewModel.manyLists.setValue(allFiles);
        }

        if(savedInstanceState ==null) {
            try {
                shoppingListFilesViewModel.getFromFolder(this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        EditText listNameEdit = findViewById(R.id.List_Name);
        listNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    list_Name = charSequence.toString();
                } catch (Exception e) {
                    list_Name = "";
                }
                Log.d(TAG, "onTextChanged called");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



    }

    /**
     * adds an shopping list into the viewmodel, swaps to the ShoppingListFilesActivity to add items to the new list, and updates the view
     * @param view
     *      a reference to the current view
     */
    public void addEntry(View view) {

        Intent myIntent;
                myIntent = ShoppingListFilesActivity.createIntent(view.getContext(), list_Name, shoppingListFilesViewModel.manyLists.getValue());
                startActivity(myIntent);

        try {
            ShoppingListFiles temp =shoppingListFilesViewModel.manyLists.getValue();
            temp.writeToFolderMain(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * a method created to remove the list from the folder object with a reference to the item when the remove list button is clicked
     * @param view
     */
    public void removeEntry (View view){
        shoppingListFilesViewModel.removeListMain((ShoppingList) view.getTag(), this);
    }

    /**
     * a method that is linked to the observable interface, which updates the view and the viewmodel when anything is changed in the app
     * @param filesList
     *      reference to the current object in the view model
     */
    @Override
    public void onChanged (final ShoppingListFiles filesList){
            final TableLayout table = findViewById(R.id.list_table);
            table.removeAllViews();
            for(final ShoppingList list: filesList.getFolder()) {
                final TableRow row = new TableRow(this);

                final Button listButton = new Button(this);
                listButton.setText(list.getListName());
                listButton.setTag(list);
                listButton.setTextSize(20);
                listButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent;
                        myIntent = ShoppingListFilesActivity.recreateIntent(view.getContext(), list.getListName(), shoppingListFilesViewModel.manyLists.getValue());
                        startActivity(myIntent);
                    }
                });

                TextView textView = new TextView(this);
                textView.setText("            ");
                textView.setTag(list);


                final Button renameButton = new Button(this);
                renameButton.setText(R.string.rename_button);
                renameButton.setTag(list);
                renameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        renameArea.setVisibility(View.VISIBLE);
                        renameConfirmButton.setVisibility(View.VISIBLE);
                        EditText listNameEdit = findViewById(R.id.Rename_Text);
                        listNameEdit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                try {
                                    rename_Name = charSequence.toString();
                                } catch (Exception e) {
                                    rename_Name = "";
                                }
                                Log.d(TAG, "onTextChanged called");
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                            }
                        });
                        renameConfirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    list.setListName(rename_Name);
                                    listButton.setText(rename_Name);
                                    listButton.setTag(list);
                                    renameArea.setVisibility(View.GONE);
                                    renameConfirmButton.setVisibility(View.GONE);
                                }
                        });


                    }
                });

                Button removeButton = new Button(this);
                removeButton.setText(R.string.remove_button);
                removeButton.setTag(list);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeEntry(view);
                        table.removeView(row);
                    }
                });

                Button duplicateButton = new Button(this);
                duplicateButton.setText(R.string.duplicate_button);
                duplicateButton.setTag(list);
                duplicateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShoppingList temp = new ShoppingList(list);
                        temp.setListName(temp.getListName()+"_copy");
                        writeDuplicatesToFolder(temp);
                    }
                });

                row.addView(listButton);
                row.addView(textView);
                row.addView(renameButton);
                row.addView(removeButton);
                row.addView(duplicateButton);

                table.addView(row);
            }
        }

    /**
     * a method created to add a duplicate to the folder when the duplicate button is clicked
     * @param addDuplicates
     *      the new duplicated shopping list that needs to be added
     */
    public void writeDuplicatesToFolder(ShoppingList addDuplicates){
            shoppingListFilesViewModel.addList(addDuplicates,this);
        }

    }


