package com.example.hp.gohigh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.sql.BatchUpdateException;


public class MainActivity extends AppCompatActivity {
//array for numbers
private int[] numericButtons={R.id.btnone,R.id.btntwo,R.id.btnthree,R.id.btnfour,R.id.btnfive,
        R.id.btnsix,R.id.btnSeven,R.id.btneight,R.id.btnnine,R.id.btnzero};
private int[] oprtButton={R.id.btnplus,R.id.btnmin,R.id.btnslash,R.id.btnmul};

private boolean lastNumeric;
private boolean stateError;
private boolean lastdot;
//create text view
    private TextView txtscreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtscreen=findViewById(R.id.textView);
        //assign onclick
        setNumericonClickListener();
        setOperatoronClickListener();
    }
    private void setNumericonClickListener(){
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button=(Button) v;
                //checking error
                if(stateError){
                    txtscreen.setText(button.getText());
                    stateError=false;
                }else {
                    txtscreen.append(button.getText());
                }
                lastNumeric=true;
            }
        };
        //add for each rule
        for(int id:numericButtons){
            findViewById(id).setOnClickListener(listener);
        }
    }
    private  void setOperatoronClickListener(){
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric &&! stateError){
                    Button button=(Button) v;
                    txtscreen.append(button.getText());
                    lastNumeric=false;
                    lastdot=false;
                }
            }
        };
        //for each loop
        for(int id:oprtButton){
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.btndot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric &&! stateError &&! lastdot){
                    txtscreen.append(".");
                    lastNumeric=false;
                    lastdot=true;
                }
            }
        });
        //clear button
        findViewById(R.id.btnclr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtscreen.setText(" ");
                lastNumeric=false;
                lastdot=false;
                stateError=false;
            }
        });
        findViewById(R.id.btnexit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        findViewById(R.id.btnequl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      onEqual();
            }
        });

    }
    private void onEqual(){
        if(lastNumeric &&! stateError){
            String txt= txtscreen.getText().toString();
            Expression expression=new ExpressionBuilder(txt).build();

            //try catch block
            try{
                double result = expression.evaluate();
                txtscreen.setText(Double.toString(result));//Double is class name and double is datatype
                lastdot=true;
            }
            //catch block
            catch (ArithmeticException ex){
                txtscreen.setText("error");
                stateError=true;
                lastNumeric=false;

            }
        }
    }
}
