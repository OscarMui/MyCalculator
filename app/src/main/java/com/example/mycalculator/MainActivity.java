package com.example.mycalculator;

//import widgets
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    //OOP: Extends: Inherit all properties (functions, variables, ...) from "AppCompactActivity"

    //for logging
    private static final String TAG = "TAG";


    //Init variables (widgets)
    TextView input,output,tenToThePower;
    Button open,close;
    Button cancel,backspace,exponential,divide;
    Button seven,eight,nine,multiply;
    Button four,five,six,minus;
    Button one,two,three,plus;
    Button changeColor,zero,point,equal;

    //add an object of Calculator
    final Calculator calculator = new Calculator();

    @Override
    //Put code that you want to run before the screen launches here
    protected void onCreate(Bundle savedInstanceState) {
        //OOP: call onCreate function of super class
        super.onCreate(savedInstanceState);

        //set layout for activity (from XML file)
        setContentView(R.layout.activity_main);

        initWidgets();

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                input.setText("");
            }
        });
        backspace.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String text=input.getText().toString();
                input.setText(text.substring(0,text.length()-1));
            }
        });
        changeColor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //OOP: MainActivity.this: In this scope, 'this' refers to the action listener object itself,
                //OOP: so MainActivity.this passes the activity, which is what we want
                //What is toast? A small message which popped up at the bottom part of the screen
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();
            }
        });
        equal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                double result=0;
                try {
                    result=calculator.calculate(input.getText().toString());
                }catch(Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }

                //check whether they are integer or double

                if(Math.abs(result)<Math.pow(10,9) && //If larger than 10^9, we would represent our number in exponential form
                        Math.abs(result - Math.floor(result))<0.0000000001 && //Check whether it is an integer, with a little tolerance
                        !Double.isInfinite(result)){ //Check whether double stores "Infinity"
                    output.setText(Integer.toString((int) result));
                }else{
                    Log.d(TAG,Double.toString(result));
                    output.setText(Double.toString(result));

                }


            }

        });



    }

    private void initWidgets(){
        //import widgets
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        tenToThePower = findViewById(R.id.tenToThePower);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        cancel = findViewById(R.id.cancel);
        backspace = findViewById(R.id.backspace);
        exponential = findViewById(R.id.exponential);
        divide = findViewById(R.id.divide);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        multiply = findViewById(R.id.multiply);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        minus = findViewById(R.id.minus);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        plus = findViewById(R.id.plus);
        changeColor = findViewById(R.id.changeColor);
        zero = findViewById(R.id.zero);
        point = findViewById(R.id.point);
        equal = findViewById(R.id.equal);

        //onClickListener: To handle the 'click' event of the numbers
        //Using a variable of type 'View.OnClickListener' to 'listen' for that click
        open.setOnClickListener(addNumber);
        close.setOnClickListener(addNumber);
        exponential.setOnClickListener(addNumber);
        divide.setOnClickListener(addNumber);
        seven.setOnClickListener(addNumber);
        eight.setOnClickListener(addNumber);
        nine.setOnClickListener(addNumber);
        multiply.setOnClickListener(addNumber);
        four.setOnClickListener(addNumber);
        five.setOnClickListener(addNumber);
        six.setOnClickListener(addNumber);
        minus.setOnClickListener(addNumber);
        one.setOnClickListener(addNumber);
        two.setOnClickListener(addNumber);
        three.setOnClickListener(addNumber);
        plus.setOnClickListener(addNumber);
        zero.setOnClickListener(addNumber);
        point.setOnClickListener(addNumber);
    }

    //Function triggered when certain buttons are clicked, as stated in initWidgets function
    private View.OnClickListener addNumber = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            String text = "";
            if(v==open){//open brackets
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();
            }else if(v==close){//close brackets
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();
            }else if(v==exponential){
                text="^";
            }else if(v==divide){
                text="÷";
            }else if(v==seven){
                text="7";
            }else if(v==eight){
                text="8";
            }else if(v==nine){
                text="9";
            }else if(v==multiply){
                text="×";
            }else if(v==four){
                text="4";
            }else if(v==five){
                text="5";
            }else if(v==six){
                text="6";
            }else if(v==minus){
                text="-";
            }else if(v==one){
                text="1";
            }else if(v==two){
                text="2";
            }else if(v==three){
                text="3";
            }else if(v==plus){
                text="+";
            }else if(v==zero){
                text="0";
            }else if(v==point){
                text=".";
            }else{
                return;
            }
            input.setText(input.getText()+text);
        }
    };


}
