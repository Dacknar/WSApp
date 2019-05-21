package com.software.dunk.wsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.service.autofill.CharSequenceTransformation;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.dunk.wsapp.Models.News;
import com.software.dunk.wsapp.R;
import com.software.dunk.wsapp.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainNewAdapter extends RecyclerView.Adapter<MainNewAdapter.ViewHolder> {

    private List<News.Posts> posts;
    private Context context;

    public MainNewAdapter(List<News.Posts> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_element_news, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(posts.get(position).getThumbnail() != null) {
            Picasso.get().load(posts.get(position).getThumbnail()).into(holder.mImage);
        }else Picasso.get().load("https://picsum.photos/500/500/?random&t=" + posts.get(position)).placeholder(R.drawable.ic_picture).into(holder.mImage);
        holder.mTitle.setText(Html.fromHtml(posts.get(position).getTitle()));
        holder.mDescription.setText(Html.fromHtml(posts.get(position).getTitle()));
        holder.mDate.setText(getDate(posts.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addPosts(List<News.Posts> postsList){
        posts.addAll(postsList);
        notifyDataSetChanged();
    }

    private String getDate(String strDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat.format(date).replace("-","/");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        private TextView mTitle, mDescription, mDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.rv_main_image);
            mTitle = itemView.findViewById(R.id.rv_main_title);
            mDescription = itemView.findViewById(R.id.rv_main_description);
            mDate = itemView.findViewById(R.id.rv_main_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(context, WebViewActivity.class);
                    webIntent.putExtra("SITE_URL", posts.get(getAdapterPosition()).getUrl());
                    context.startActivity(webIntent);
                }
            });
        }
    }
}
