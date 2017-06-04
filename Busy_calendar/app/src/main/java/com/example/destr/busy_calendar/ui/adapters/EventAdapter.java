package com.example.destr.busy_calendar.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.ui.activities.EventInfoActivity;
import com.example.destr.busy_calendar.utils.ThemeManager;

public class EventAdapter extends SimpleCursorAdapter {
    private Cursor cursor;
    private Context context;
    public EventAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.cursor=c;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        new ThemeManager(context).setEventItems(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.move(view.getId()+1);
                String[] strings = new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9)};
                Intent intent = new Intent(context, EventInfoActivity.class);
                intent.putExtra(Constants.OtherConstants.STRING_ARRAY_EXTRA,strings);
                context.startActivity(intent);
            }
        });
        return view;
    }

}
