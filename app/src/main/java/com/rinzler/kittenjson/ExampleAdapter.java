package com.rinzler.kittenjson;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rinzler.kittenjson.Model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context context;
    private ArrayList<Item> itemList;


    private onItemClickListener mListener;

    //setting listener for the onclick methods // the position will be passed here
    public interface onItemClickListener {
        void onItemClick(int position);
    }

    //this method will be called in the main activity and we will use the position passed by the onitemclicklistener interface
    public void setOnItemClickListener(onItemClickListener listener){
        //this method instantiates the onitemclicklistener..
        // so we wil use this method and it will require to implement the interface to the main activity
        mListener = listener;
    }

    //the context and the required wil be passed from the main acitivty
    public ExampleAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.example_item, parent, false);

        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        //binding the view
        Item currentItem = itemList.get(position);

        String imageUrl = currentItem.getmImageUrl();

        String creatorName = currentItem.getmCreator();

        int likes = currentItem.getmLikes();

        //setting the items to the recyclerview
        holder.creatorName.setText(creatorName);
        holder.likes.setText("Likes " + likes);
        //we use piccasso library to get the image from the json
        Picasso.get().load(imageUrl)
                .fit()
                .centerInside()
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        public TextView creatorName;
        public TextView likes;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            creatorName = itemView.findViewById(R.id.text_view_name);
            likes = itemView.findViewById(R.id.text_view_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            //use the interface and access the method it has
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }




    }
}
