package com.seputar.berita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class berita_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RequestQueue mRequest;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    ArrayList<Model> list;
    public static final String url="http://192.168.43.77/berita/tampil_berita.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_activity);
        list=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.rv);
        pd=new ProgressDialog(berita_activity.this);
        mRequest= Volley.newRequestQueue(getApplicationContext());
        request();
    }
    private void request(){
        data();

        layoutManager= new LinearLayoutManager(berita_activity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterLIst(list,berita_activity.this);
        recyclerView.setAdapter(adapter);
    }
    private void data(){
        pd.setMessage("Mencari Data");
        pd.setCancelable(false);
        pd.show();
        JsonArrayRequest ja = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSONResponse",response.toString());


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jo= response.getJSONObject(i);
                        Model md = new Model();
                        md.setTitle(jo.getString("title"));
                        md.setText(jo.getString("description"));
                        md.setPhoto(jo.getString("photo"));
                        md.setName(jo.getString("name"));
                        list.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();


                }
                pd.cancel();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("ERRORRequest",error.getMessage());

                    }
                });
        mRequest.add(ja);
    }
}
