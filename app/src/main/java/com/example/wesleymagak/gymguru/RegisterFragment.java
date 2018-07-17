package com.example.wesleymagak.gymguru;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
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
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText r_firstname,r_lastname,r_email,r_password,r_password2;
    TextView btn_goto_login;
    Button btn_register;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        r_firstname = view.findViewById(R.id.register_firstName);
        r_lastname = view.findViewById(R.id.register_lastName);
        r_email = view.findViewById(R.id.register_email);
        r_password = view.findViewById(R.id.register_password);
        r_password2 = view.findViewById(R.id.register_confirmPassword);

        btn_goto_login = view.findViewById(R.id.btn_goto_login);
        btn_register = view.findViewById(R.id.btn_register);

        btn_goto_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        final FragmentManager loginFragmentManager = getActivity().getSupportFragmentManager();

        if(view == btn_goto_login){

            loginFragmentManager.beginTransaction().replace(R.id.login_content, new LoginFragment()).commit();

        }else if(view == btn_register){
            final String f_name=r_firstname.getText().toString().trim();
            final String l_name=r_lastname.getText().toString().trim();
            final String email=r_email.getText().toString().trim();
            final String pwd=r_password.getText().toString().trim();
            String pwd_2=r_password2.getText().toString().trim();

            if(f_name.equals("") || l_name.equals("") || email.equals("") || pwd.equals("") || pwd_2.equals("")){
                Toast.makeText(getActivity(), "Please fill in all details!!!", Toast.LENGTH_SHORT).show();
            }else {
                if (!pwd.equals(pwd_2)){
                    Toast.makeText(getActivity(), "Passwords do not match!!!", Toast.LENGTH_SHORT).show();
                }  else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
                    }else {
                       if (pwd.length() < 6 || pwd.length() > 15){
                            Toast.makeText(getActivity(), "Invalid password!!! Read the password criteria.", Toast.LENGTH_SHORT).show();
                        }else {

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("Registration request received! A link has been send to your email! Please check your e-mail to complete your registration!!!") || response.contains("Registration failed! A user with that e-mail already exists!!!")) {
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
                                    params.put(Constants.KEY_F_NAME, f_name);
                                    params.put(Constants.KEY_L_NAME, l_name);
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
                }
            }

        }

    }
}
