package demo.app.com.recyclerviewdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import demo.app.com.recyclerviewdemo.data.ArticleData;
import demo.app.com.recyclerviewdemo.R;

/**
 * Created by mausam.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {


    private ArrayList<ArticleData> feedList = null;
    private Context context = null;

    public RecycleViewAdapter(Context context, ArrayList<ArticleData> feedList) {
        this.feedList = feedList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ArticleData data = feedList.get(position);


        //Download image using picasso library
        Picasso.with(context).load(data.getThumbnail())
                .error(android.R.drawable.ic_menu_info_details)
                .placeholder(android.R.drawable.ic_menu_info_details)
                .into(holder.imgThumb);

        //Setting text view title
        holder.txtTitle.setText(Html.fromHtml(data.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (feedList != null ? feedList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgThumb;
        protected TextView txtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);



        }

    }
}



