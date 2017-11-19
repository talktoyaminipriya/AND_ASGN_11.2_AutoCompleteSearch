package com.example.priya.autocompletesearch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends Activity {


     //Change to type CustomAutoCompleteView instead of AutoCompleteTextView

    CustomAutoCompleteView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;

    // for database operations
    DatabaseHandler databaseH;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            // instantiate database handler
            databaseH = new DatabaseHandler(MainActivity.this);

            // put sample data to database
            insertSampleData();

            // auto complete textview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new AutoListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSampleData(){

        // CREATE
        databaseH.create( new MyObject("HP Injet Printer") );
        databaseH.create( new MyObject("Samsung Scanner") );
        databaseH.create( new MyObject("Lg TV") );
        databaseH.create( new MyObject("Panasonic TV") );
        databaseH.create( new MyObject("Lg Microwave") );
        databaseH.create( new MyObject("ipod") );
        databaseH.create( new MyObject("iphone 6s") );
        databaseH.create( new MyObject("iphone 6") );
    }

    // this function is used in AutoListener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = databaseH.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

}
