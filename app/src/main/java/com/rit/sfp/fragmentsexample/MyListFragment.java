package com.rit.sfp.fragmentsexample;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sfp on 9/20/2016.
 */
    public class MyListFragment extends ListFragment {

        public ArrayList<String> itemsArrayList; //list of items
        private ArrayAdapter<String> itemsArrayAdapter;
        private ItemChangedListener itemChangedListener;

        //interface describing listener for changes to selected item
        public interface ItemChangedListener {
            //the selected item is changed
            public void onSelectedItemChanged(String itemNameString);
        }

        //set the ItemChangedListener
        public void setItemChangedListener(ItemChangedListener listener) {
            itemChangedListener = listener;
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //create ArrayList to save item names
        itemsArrayList = new ArrayList<String>() {{ //can use command-D to duplicate each line below to change
            add("A");      //NOTE: after typing, command-D will duplicate the line, then change each letter
            add("B");
            add("C");
            add("D");
            add("E");
            add("F");
            add("G");
            add("H");
            add("I");
            add("J");
        }};

        //set the Fragment's ListView adapter

        setListAdapter(new ItemsArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                itemsArrayList));

        ListView thisListView = getListView(); //get the Fragment's listview

        //allow one item to be selected at a time
        thisListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        thisListView.setBackgroundColor(Color.WHITE);
        thisListView.setOnItemClickListener(itemsOnItemClickListener);
    }

    //define the GUI components for each ListView item
    private static class ViewHolder {
        TextView itemTextView; //refers to the ListView item's TextView
    }

    //customer ArrayAdapter
    private class ItemsArrayAdapter<T> extends ArrayAdapter<String> {
        private Context context; //this Fragment's Activity's Context
        private LayoutInflater inflater;
        private List<String> items; //list of items

        //public constructor
        public ItemsArrayAdapter(Context context, int textViewResourceId,
                                 List<String> objects) {
            super(context,-1,objects); //-1 indicates we're customizing view
            this.context = context;
            this.items = objects;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


    //get ListView item for the given position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; //holds reference to current item's GUI
        //if convertView is null, inflate GUI and create ViewHolder otherwise, get an existing ViewHolder
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item,null);
            //set up ViewHOlder for this item
            viewHolder = new ViewHolder();
            viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            //get the ViewHolder from the convertView's tag
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String item = items.get(position); //get current item
        viewHolder.itemTextView.setText(item);

        return convertView;
    } //getView
}//arrayadapter

    private AdapterView.OnItemClickListener itemsOnItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view,
                                        int position,
                                        long id) {
                    itemChangedListener.onSelectedItemChanged(
                            ((TextView)view).getText().toString()
                    );
                }
            };

}
