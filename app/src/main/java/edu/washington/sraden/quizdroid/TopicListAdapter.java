package edu.washington.sraden.quizdroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2/16/15.
 */
public class TopicListAdapter extends ArrayAdapter<Topic> {
    Context mContext;
    public TopicListAdapter(Context context, int resource, List<Topic> topics) {
        super(context, resource, topics);
        mContext = context;

    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent) {
        if (contextView == null) {
            contextView = LayoutInflater.from(mContext).inflate(R.layout.topic_list_adapter_layout, parent, false);
        }
        TextView topicTitle = (TextView) contextView.findViewById(R.id.topic_title);
        topicTitle.setText(getItem(position).getTitle());

        TextView topicShortDesc = (TextView) contextView.findViewById(R.id.topic_short_desc);
        topicShortDesc.setText(getItem(position).getShortDesc());

        return contextView;
    }
}
