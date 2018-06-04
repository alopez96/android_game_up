package ca.alexbalt.gameup2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventList extends ArrayAdapter<event> {
    private Activity context;
    private List<event> events;

    public EventList(Activity context, List<event> eventList){
        super(context, R.layout.activity_events, eventList);
        this.context= context;
        this.events = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        event Event = events.get(position);
        textViewName.setText(Event.getTitle());
        textViewDate.setText(Event.getDate());

        return listViewItem;
    }
}
