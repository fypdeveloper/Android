package app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.developer.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.RecyclerViewHolder> {

    ArrayList<Project> arrayList = new ArrayList<Project>();
    ImageLoader imageLoader;
    static Context context;

    Bitmap btmp;

    public FeedAdapter(ArrayList<Project> arrayList, Context context) {
        this.arrayList = arrayList;
        FeedAdapter.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Log.d("Mthod-AA","onCreateViewHolder");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed,parent,false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        //Log.d("Mthod-A","onBindViewHolder");

        Project project = arrayList.get(position);

        /*imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(project.getImageUrl(), ImageLoader.getImageListener(holder.imageaaa,R.drawable.logo,android.R.drawable.ic_dialog_alert));*/

        //holder.imageaaa.setImageUrl(project.getImageUrl(),imageLoader);

//        new GetImage().execute(project.getImageUrl());

        //Log.d("Mthod-A","onBindViewHolder: "+ .toString());
/*
        holder.url.setText(project.getImageUrl());
        holder.caption.setText(project.getCaption());
*/

        holder.title.setText(project.getTitle());
        Picasso.get().load(project.getImageUrl()).into(holder.imageP);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView url,title,caption;
        ImageView imageP;
        //NetworkImageView imageaaa;
        public RecyclerViewHolder(View itemView) {

            super(itemView);

            imageP = (ImageView) itemView.findViewById(R.id.ImgFeed);
            title = (TextView) itemView.findViewById(R.id.title);

/*
            caption = (TextView) itemView.findViewById(R.id.caption);
            url = (TextView) itemView.findViewById(R.id.url);
*/

        }
    }


        /*class GetImage extends AsyncTask<String, Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Loading Images...", "Please wait...", true, true);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                URL url = null;
                Bitmap image = null;

                String urlToImage = params[0];
                try {
                    url = new URL(urlToImage);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                Log.d("Mthod-C","onPostExecute: "+btmp.toString());
                super.onPostExecute(bitmap);
                loading.dismiss();
                //btmp = bitmap;

            }
        }*/

}
