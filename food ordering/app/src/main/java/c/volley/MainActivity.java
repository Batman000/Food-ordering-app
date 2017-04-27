package c.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView txt;
    LinearLayout linearMain;
    String url="http://192.168.43.109:8000/";//Set this to your ip before installing this app
    public String[] parts;
    CheckBox cb[];
    int cbno,i,k;
    //String[] ordered_items=new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearMain = (LinearLayout) findViewById(R.id.linearMain);
        button = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.textView2);
        final RequestQueue req = Volley.newRequestQueue(MainActivity.this);

        StringRequest streq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parts = response.split("\"");
                        //txt.setText(parts[1]);

                        cb = new CheckBox[parts.length/2];

                        for (k = 1, i = 0; k < parts.length; k = k + 2, i++) {
                            cb[i] = new CheckBox(MainActivity.this);
                            // CheckBox cb = new CheckBox(getApplicationContext());
                            cb[i].setText(parts[k]);
                            linearMain.addView(cb[i]);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("Something went wrong");
                error.printStackTrace();
                req.stop();
            }
        });
        req.add(streq);



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                                txt.setText(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        for (int j = 0; j <=cb.length-1; j++) {
                            if (cb[j].isChecked())
                                params.put("Hello["+(j+1)+"]",cb[j].getText().toString());
                        }

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });


    }
}


