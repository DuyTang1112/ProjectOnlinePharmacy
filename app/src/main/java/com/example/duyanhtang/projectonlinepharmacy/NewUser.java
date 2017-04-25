package com.example.duyanhtang.projectonlinepharmacy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NewUser extends Activity {
    boolean gender;//male is true, female is false
    RadioButton male,female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("New User Sign Up");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);
        male=(RadioButton) findViewById(R.id.male);
        female=(RadioButton) findViewById(R.id.female);
        View.OnClickListener radioclicked=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch(view.getId()) {
                    case R.id.male:
                        if (checked) {
                            female.setChecked(false);
                            gender=true;
                        }
                            break;
                    case R.id.female:
                        if (checked) {
                            male.setChecked(false);
                            gender=false;
                        }
                            break;
                }
            }
        };
        male.setOnClickListener(radioclicked);
        female.setOnClickListener(radioclicked);
    }

}
