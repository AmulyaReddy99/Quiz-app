package com.tech.quizapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import org.w3c.dom.Text;

public class LoginActivity extends BottomSheetDialogFragment {
    private BottomSheetListener mlistener;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        Button okbtn = v.findViewById(R.id.button_ok);
        Button closebtn = v.findViewById(R.id.button_close);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView email = v.findViewById(R.id.email_id);
                TextView password = v.findViewById(R.id.password);
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                if (!TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches() && !TextUtils.isEmpty(p)){
                    mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mlistener.onButtonClicked(e);
                            }else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    email.setError("Failed due to collision");
                                }else{
                                    email.setError(task.getException().getMessage());
                                }
                            }
                            //v.findViewById(R.id.account).setVisibility(View.INVISIBLE);
                            //v.findViewById(R.id.logout).setVisibility(View.VISIBLE);
                        }
                    });
                }else{
                    email.setError("Email or Password is wrong");
                }
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        return v;
    }
    public interface BottomSheetListener{
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mlistener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +"must implement bottom sheet listener");
        }
    }
}

