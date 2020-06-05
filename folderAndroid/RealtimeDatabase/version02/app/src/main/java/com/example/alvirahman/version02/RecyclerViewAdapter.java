package com.example.alvirahman.version02;

/**
 * Created by Juned on 8/9/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Post> Info_List;

    public RecyclerViewAdapter(Context context, List<Post> TempList) {

        this.Info_List = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post post = Info_List.get(position);

        holder.title_view.setText(post.body);

        holder.description_view.setText(post.title);



    }

    @Override
    public int getItemCount() {

        return Info_List.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_view;
        public TextView description_view;
        public TextView author_view;

        public ViewHolder(View itemView) {

            super(itemView);

            title_view = (TextView) itemView.findViewById(R.id.title_view);

            description_view = (TextView) itemView.findViewById(R.id.description_view);

            author_view=(TextView)itemView.findViewById(R.id.author_view);
        }
    }
}