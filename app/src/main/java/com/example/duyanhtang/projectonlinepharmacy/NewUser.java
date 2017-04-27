package com.example.duyanhtang.projectonlinepharmacy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewUser extends Activity {
    boolean gender;//male is true, female is false
    RadioButton male,female;
    Button register;
    EditText fname,lname,mname,age,email,address,password,userid;
    SQL sql;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("New User Sign Up");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);
        male=(RadioButton) findViewById(R.id.male);
        female=(RadioButton) findViewById(R.id.female);
        register=(Button) findViewById(R.id.submit);
        fname=(EditText) findViewById(R.id.fname);
        lname=(EditText) findViewById(R.id.lname);
        mname=(EditText) findViewById(R.id.mname);
        age=(EditText) findViewById(R.id.age);
        email=(EditText) findViewById(R.id.email);
        address=(EditText) findViewById(R.id.address);
        password=(EditText) findViewById(R.id.newpass);
        userid=(EditText) findViewById(R.id.userid);
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
                            Log.d("Gender","male");
                        }
                            break;
                    case R.id.female:
                        if (checked) {
                            male.setChecked(false);
                            gender=false;
                            Log.d("Gender","female");
                        }
                            break;
                }
            }
        };
        male.setOnClickListener(radioclicked);
        female.setOnClickListener(radioclicked);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().length()*mname.getText().toString().length()*
                        lname.getText().toString().length()*age.getText().toString().length()
                        *email.getText().toString().length()*address.getText().toString().length()
                        *password.getText().toString().length()*userid.getText().toString().length()==0){
                    Toast.makeText(NewUser.this,"Please fill all info",Toast.LENGTH_SHORT).show();
                    return;
                }
                if( !female.isChecked() && !male.isChecked()){
                    Toast.makeText(NewUser.this,"Please indicate your gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                registerIntoSql();

            }
        });
    }

    private void registerIntoSql() {
        sql=SQL.getInstance(NewUser.this);
        db=sql.getWritableDatabase();
        String username=userid.getText().toString();
        Cursor cur=db.rawQuery("SELECT * from login_info WHERE _id=?",new String[]{username});
        if (cur.getCount()==0){
            ContentValues values = new ContentValues();
            //insert into login_info table

            values.put("_id", username);
            values.put("password", password.getText().toString());

            long check=db.insert("login_info", null, values);

            if (check==-1){
                Log.d("Status sql register","register not successful");
            }
            else {
                Log.d("Status sql register", "register successful");
                //insert into customer info
                values = new ContentValues();
                values.put("_id", username);
                values.put("fname", fname.getText().toString());
                values.put("mname", mname.getText().toString());
                values.put("lname", lname.getText().toString());
                values.put("age", Integer.parseInt(age.getText().toString()));
                values.put("gender", gender ? "Male" :"Female");
                values.put("email", email.getText().toString());
                values.put("address", address.getText().toString());
                check=db.insert("client_info", null, values);
                //sql.close();
                if (check== -1) {
                    Log.d("Status sql info", "info not successful");
                    Toast.makeText(this, "Cannot register new user", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Status sql info", "info successful");
                    Toast.makeText(this, "New user created successfully ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("result", 1);
                    setResult(2, intent);
                    finish();
                }
            }
        }
        else{
            Toast.makeText(this,"UserID is already used",Toast.LENGTH_LONG).show();
        }
    }

}
