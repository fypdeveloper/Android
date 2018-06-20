package com.example.developer.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simplify.android.sdk.CardEditor;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Simplify simplify;
    String donate_url = "http://payatedhionline.000webhostapp.com/";
    String updateDB_url = ServerConfig.ServerUrl+"addDonation";
    private SessionHandler session;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        simplify = new Simplify();
        simplify.setApiKey("sbpb_MWUyM2Q0NTEtZTZmOS00YmI1LWE2MzctYWQyZmQ0Zjk4MmE5");

        final CardEditor cardEditor = (CardEditor) findViewById(R.id.card_editor);
        final Button checkoutButton = (Button) findViewById(R.id.checkout_button);
        final EditText amount = (EditText) findViewById(R.id.amount);
// add state change listener
        cardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                checkoutButton.setEnabled(cardEditor.isValid());
            }
        });
// add checkout button click listener
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a card token
                simplify.createCardToken(cardEditor.getCard(), new CardToken.Callback() {
                    @Override
                    public void onSuccess(CardToken cardToken) {
                        Log.d("TOKEN",cardToken.getId());
                        donate(((Integer.parseInt(amount.getText().toString()))*100),cardToken.getId());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText( getApplicationContext(),"Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void donate(final int amount, final String token) {

        StringRequest sr = new StringRequest(Request.Method.POST, donate_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("TAG",response);
                try {
                    JSONObject res = new JSONObject(response);
                    if (res.getString("status").equals("APPROVED")){
                        Toast.makeText(getApplicationContext(),
                                "Successfully Donated!", Toast.LENGTH_SHORT).show();
                        updateDB(amount);
                    }
                    else if (res.getString("status").equals("DECLINED")){
                        Toast.makeText(getApplicationContext(),
                                "Problem with Your Card!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!res.getString("message").equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Request!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),
                                    "Unknown Error!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error",error.toString());
                Toast.makeText(getApplicationContext(),
                        "Error, Try Again Later!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("simplifyToken",token);
                params.put("amount",String.valueOf(amount));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Accept","application/json");
                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);

        //MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    public void updateDB(final int amount){

        StringRequest sr2 = new StringRequest(Request.Method.POST, updateDB_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("DB-Updated",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DB-Update-Error",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",user.getemail());
                params.put("amount",String.valueOf(amount));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Accept","application/json");
                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr2);

    }

}
