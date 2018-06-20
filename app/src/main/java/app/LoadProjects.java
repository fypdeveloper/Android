package app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.developer.app.ServerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoadProjects extends AsyncTask<Void,Project,Void> {

    private Context context;
    private Activity activity;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Project> arrayList = new ArrayList<Project>();

    public LoadProjects(Context context) {
        this.context = context;
        activity = (Activity)context;
    }

    String urlProject = ServerConfig.ServerUrl + "/ProjectsController.php";

    @Override
    protected void onPreExecute() {
        //recyclerView = (RecyclerView)activity.findViewById(R.id.recyclerViewFeed);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ProjectAdapter(arrayList,context);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(urlProject);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
            httpURLConnection.disconnect();

            String urlProject = stringBuilder.toString().trim();
            Log.d("JSON",urlProject);

            JSONObject jsonObject = new JSONObject(urlProject);
            JSONArray jsonArray = jsonObject.getJSONArray("projects");

            int count= 0;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                count++;
                Project project = new Project(JO.getString("img"),JO.getString("title"),JO.getString("caption"));
                publishProgress(project);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Project... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
