package com.example.matpil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class select2 extends AppCompatActivity {
    RadioButton rbspy,rbnospy, rbsug, rbnosug, rbsal, rbnosal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);


        rbspy = findViewById(R.id.btn_spy);
        rbnospy = findViewById(R.id.btn_no_spy);
        rbsug = findViewById(R.id.btn_sug);
        rbnosug = findViewById(R.id.btn_no_spy);
        rbsal = findViewById(R.id.btn_sal);
        rbnosal = findViewById(R.id.btn_no_spy);

        View.OnClickListener mrb = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                switch (view.getId()){
                    case R.id.btn_spy:
                        if(checked){
                            Toast.makeText(select2.this,"매움.",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn_no_spy:
                        if(checked) {
                            Toast.makeText(select2.this, "안매움.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn_sug:
                        if(checked) {
                            Toast.makeText(select2.this, "달음.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn_no_sug:
                        if(checked) {
                            Toast.makeText(select2.this, "안담.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn_sal:
                        if(checked) {
                            Toast.makeText(select2.this, "짬.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn_no_sal:
                        if(checked) {
                            Toast.makeText(select2.this, "안짬.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

    }
}