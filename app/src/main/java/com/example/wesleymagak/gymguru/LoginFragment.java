package com.example.wesleymagak.gymguru;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    View v;
    EditText l_email,l_password;
    TextView btn_goto_register;
    Button btn_login;
    Button btn_change_lang;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_login, container, false);
        l_email = v.findViewById(R.id.login_email);
        l_password = v.findViewById(R.id.login_password);

        btn_goto_register = v.findViewById(R.id.btn_goto_register);
        btn_login = v.findViewById(R.id.btn_login);
        btn_change_lang = v.findViewById(R.id.btn_change_lang);

        btn_goto_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_change_lang.setOnClickListener(this);


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View view) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        loadLocale();
        if (view == btn_goto_register) {
            fragmentManager.beginTransaction().replace(R.id.login_content, new RegisterFragment()).commit();
        } else if (view == btn_login) {

            final String email=l_email.getText().toString().trim();
            final String pwd=l_password.getText().toString().trim();

            if(email.equals("") || pwd.equals("")){
                Toast.makeText(getActivity(), "Please fill in all details!!!", Toast.LENGTH_SHORT).show();
            }else {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
                    Toast.makeText(getActivity(), "Invalid e-mail format!!!", Toast.LENGTH_SHORT).show();
                }else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("Login success!!!") || response.contains("Error. Login failed!!!")) {
                                        final FragmentManager loginFragmentManager = getActivity().getSupportFragmentManager();
                                        loginFragmentManager.beginTransaction().replace(R.id.login_content, new LoginFragment()).commit();
                                    }
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error instanceof TimeoutError){
                                Toast.makeText(getActivity(), "Timeout Error!!!.", Toast.LENGTH_SHORT).show();
                            }else if (error instanceof NoConnectionError){
                                Toast.makeText(getActivity(), "No Connection Error!!!.", Toast.LENGTH_SHORT).show();
                            }else if (error instanceof AuthFailureError){
                                Toast.makeText(getActivity(), "Authentication Failure Error!!!.", Toast.LENGTH_SHORT).show();
                            }else if (error instanceof NetworkError){
                                Toast.makeText(getActivity(), "Network Error!!!.", Toast.LENGTH_SHORT).show();
                            }else if (error instanceof ServerError){
                                Toast.makeText(getActivity(), "Server Error!!!.", Toast.LENGTH_SHORT).show();
                            }else if (error instanceof ParseError){
                                Toast.makeText(getActivity(), "JSON Parse Error!!!.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(Constants.KEY_EMAIL, email);
                            params.put(Constants.KEY_PASSWORD, pwd);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("User-Agent", "GymGuru");
                            return headers;
                        }
                    };

                    MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }
            }
        }else if(view == btn_change_lang){
            showChangeLanguageDialog();
        }
    }


    private void showChangeLanguageDialog() {
        final String[] listItems={"English","Swahili"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.getContext());
        mBuilder.setTitle("Choose language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    Toast.makeText(v.getContext(),"English selected", Toast.LENGTH_SHORT).show();
                    setLocale("eng");
                }else if(i==1){
                    Toast.makeText(v.getContext(),"Swahili selected", Toast.LENGTH_SHORT).show();
                    setLocale("sw-rKe");
                }
                dialogInterface.dismiss();
            }

        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config =new Configuration();
        config.locale=locale;
        res.updateConfiguration(config, dm);

        SharedPreferences.Editor editor=getActivity().getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs=getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        setLocale(language);
    }


}
