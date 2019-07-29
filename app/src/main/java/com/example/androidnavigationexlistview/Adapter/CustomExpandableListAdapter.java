package com.example.androidnavigationexlistview.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.androidnavigationexlistview.MainActivity;
import com.example.androidnavigationexlistview.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listTitle;
    private Map<String, List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return listItem.size();

        if (this.listItem.get(this.listTitle.get(groupPosition)) == null)
            return 0;
        else
            return this.listItem.get(this.listTitle.get(groupPosition))
                    .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
       /* if (listItem.get(listTitle.get(groupPosition)) == null) {
            return 0;
        }
        else {
            return listItem.get(listTitle.get(groupPosition))
                    .size();
        }*/
        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group, null);

        }

        /*if (title == "Test"){
//            getExpandableListView().setGroupIndicator(null);
                MainActivity mainActivity = (MainActivity) context;
                ExpandableListView expandableListView = mainActivity.getExpandableListView();
                expandableListView.setGroupIndicator(null);

        }*/

        TextView txtTitle = (TextView) convertView.findViewById(R.id.listTitle);
        txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) getChild(groupPosition,childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

        }

        TextView txtChild = (TextView) convertView.findViewById(R.id.expandableListItem);
        txtChild.setText(title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
