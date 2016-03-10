package demo.app.com.recyclerviewdemo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import demo.app.com.recyclerviewdemo.data.ArticleData;
import demo.app.com.recyclerviewdemo.R;

/**
 * @author mausam
 * This class shows detail of the particular article clicked from list screen.
 */
public class ArticleDetailActivity extends AppCompatActivity {


    private ArticleData articleData = null;
    private ImageView imgFull = null;
    private TextView txtDesc = null;
    private TextView txtTitle=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout for this activity
        setContentView(R.layout.activity_article_detail);


        //receive article data passed from list activity.
        articleData = (ArticleData) getIntent().getSerializableExtra("articleData");


        //Find the Views from layout to populate data.
        imgFull = (ImageView) findViewById(R.id.imgFull);
        txtDesc = (TextView) findViewById(R.id.txtDescription);
        txtTitle = (TextView) findViewById(R.id.txtTitle);


        txtTitle.setText(articleData.getTitle());

        //set the data in the views.

        txtTitle.setText(articleData.getTitle());

        if (articleData.getDescription() != null && !articleData.getDescription().equals(""))
            txtDesc.setText(Html.fromHtml(articleData.getDescription()));


        //Download and display full size article image using picasso library.
        Picasso.with(ArticleDetailActivity.this).load(articleData.getFullImageUrl())
                .error(android.R.drawable.ic_menu_info_details)
                .placeholder(android.R.drawable.ic_menu_info_details)
                .into(imgFull);


    }

}
