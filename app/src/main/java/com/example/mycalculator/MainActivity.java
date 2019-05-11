package com.example.mycalculator;

//import widgets
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    //OOP: Extends: Inherit all properties (functions, variables, ...) from "AppCompactActivity"

    //for logging
    private static final String TAG = "TAG";


    //Init variables (widgets)
    ConstraintLayout constraintLayout;
    HorizontalScrollView inputScroll;
    TextView input,output,timesTen, tenToThePower;
    Button open,close;
    Button cancel,backspace,exponential,divide;
    Button seven,eight,nine,multiply;
    Button four,five,six,minus;
    Button one,two,three,plus;
    Button zero,point,equal;
    ImageButton changeColor;

    //add an object of Calculator
    final Calculator calculator = new Calculator();

    //see current color
    boolean isBackgroundWhite = false;
    
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
                output.setText("0");
                tenToThePower.setVisibility(View.INVISIBLE);
                timesTen.setVisibility(View.INVISIBLE);
            }
        });
        backspace.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String text=input.getText().toString();
                if(text.length()!=0)
                    input.setText(text.substring(0,text.length()-1));
            }
        });
        changeColor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //OOP: MainActivity.this: In this scope, 'this' refers to the action listener object itself,
                //OOP: so MainActivity.this passes the activity, which is what we want
                //What is toast? A small message which popped up at the bottom part of the screen
                changeActivityColor();
            }
        });
        equal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (input.getText().equals("")){
                    output.setText("0");
                    return;
                }

                double result=0;
                try {
                    result=calculator.calculate(input.getText().toString());
                }catch(Exception e){
                    //Use a toast to show the error msg stated b4
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    output.setText("ERR");
                    tenToThePower.setVisibility(View.INVISIBLE);
                    timesTen.setVisibility(View.INVISIBLE);
                    return;
                }

                //check whether they are integer or double
                if(Math.abs(result)<Math.pow(10,9) && //If larger than 10^10, we would represent our number in exponential form
                        Math.abs(result - Math.floor(result))==0 && //Check whether it is an integer, with no tolerance
                        !Double.isInfinite(result)){ //Check whether double stores "Infinity"
                    output.setText(Integer.toString((int) result));
                    tenToThePower.setVisibility(View.INVISIBLE);
                    timesTen.setVisibility(View.INVISIBLE);
                }else{
                    String text = Double.toString(result);
                    if(text.contains("E")){
                        //Separate the output with the magnitude&sign and exponent using string methods
                        int ePos = text.indexOf('E');

                        output.setText(text.substring(0,Math.min(ePos,result<0 ? 12 : 11)));//A substring of 'text' starting from 0 (INCLUSIVE) to e (EXCLUSIVE)/10
                        //same line if statement, condition ? valueIfTrue : valueIfFalse
                        tenToThePower.setText(text.substring(ePos+1)); //A substring of 'text' starting from the position of e+1 (INCLUSIVE)
                        tenToThePower.setVisibility(View.VISIBLE);
                        timesTen.setVisibility(View.VISIBLE);
                    }else{
                        output.setText(text.substring(0,Math.min(text.length(),11)));
                        tenToThePower.setVisibility(View.INVISIBLE);
                        timesTen.setVisibility(View.INVISIBLE);
                    }


                }


            }

        });



    }

    private void initWidgets(){
        //import widgets
        constraintLayout = findViewById(R.id.constraintLayout);
        
        inputScroll = findViewById(R.id.inputScroll);

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        timesTen = findViewById(R.id.timesTen);

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
                text="(";
            }else if(v==close){//close brackets
                text=")";
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
            //Need time to update the text, so a thread (runnable) is created to handle this event
            inputScroll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputScroll.fullScroll(View.FOCUS_RIGHT);
                }
            },50);
        }
    };
    
    private void changeActivityColor(){
        if(isBackgroundWhite){
            //change to background black

            //ConstraintLayout constraintLayout;
            //HorizontalScrollView inputScroll;
            //TextView input,output,timesTen, tenToThePower;
            constraintLayout.setBackgroundColor(Color.BLACK);
            input.setTextColor(Color.WHITE);
            output.setTextColor(Color.WHITE);
            timesTen.setTextColor(Color.WHITE);
            tenToThePower.setTextColor(Color.WHITE);

            //Button open,close;
            open.setBackgroundResource(R.drawable.round_button);
            open.setTextColor(Color.BLACK);

            close.setBackgroundResource(R.drawable.round_button);
            close.setTextColor(Color.BLACK);

            //Button cancel,backspace,exponential,divide;
            cancel.setBackgroundResource(R.drawable.round_button);
            cancel.setTextColor(Color.BLACK);

            backspace.setBackgroundResource(R.drawable.round_button);
            backspace.setTextColor(Color.BLACK);

            exponential.setBackgroundResource(R.drawable.round_button);
            exponential.setTextColor(Color.BLACK);

            divide.setBackgroundResource(R.drawable.round_button);
            divide.setTextColor(Color.BLACK);

            //Button seven,eight,nine,multiply;
            seven.setBackgroundResource(R.drawable.round_button);
            seven.setTextColor(Color.BLACK);

            eight.setBackgroundResource(R.drawable.round_button);
            eight.setTextColor(Color.BLACK);

            nine.setBackgroundResource(R.drawable.round_button);
            nine.setTextColor(Color.BLACK);

            multiply.setBackgroundResource(R.drawable.round_button);
            multiply.setTextColor(Color.BLACK);

            //Button four,five,six,minus;
            four.setBackgroundResource(R.drawable.round_button);
            four.setTextColor(Color.BLACK);

            five.setBackgroundResource(R.drawable.round_button);
            five.setTextColor(Color.BLACK);

            six.setBackgroundResource(R.drawable.round_button);
            six.setTextColor(Color.BLACK);

            minus.setBackgroundResource(R.drawable.round_button);
            minus.setTextColor(Color.BLACK);

            //Button one,two,three,plus;
            one.setBackgroundResource(R.drawable.round_button);
            one.setTextColor(Color.BLACK);

            two.setBackgroundResource(R.drawable.round_button);
            two.setTextColor(Color.BLACK);

            three.setBackgroundResource(R.drawable.round_button);
            three.setTextColor(Color.BLACK);

            plus.setBackgroundResource(R.drawable.round_button);
            plus.setTextColor(Color.BLACK);

            //Button zero,point,equal;
            zero.setBackgroundResource(R.drawable.round_button);
            zero.setTextColor(Color.BLACK);

            point.setBackgroundResource(R.drawable.round_button);
            point.setTextColor(Color.BLACK);

            equal.setBackgroundResource(R.drawable.round_button);
            equal.setTextColor(Color.BLACK);

            //ImageButton changeColor;
            changeColor.setBackgroundResource(R.drawable.round_button);
            changeColor.setImageResource(R.drawable.palette_white);



        }else{
            //change to background white
            //ConstraintLayout constraintLayout;
            //HorizontalScrollView inputScroll;
            //TextView input,output,timesTen, tenToThePower;
            constraintLayout.setBackgroundColor(Color.WHITE);
            input.setTextColor(Color.BLACK);
            output.setTextColor(Color.BLACK);
            timesTen.setTextColor(Color.BLACK);
            tenToThePower.setTextColor(Color.BLACK);

            //Button open,close;
            open.setBackgroundResource(R.drawable.round_button_black);
            open.setTextColor(Color.WHITE);

            close.setBackgroundResource(R.drawable.round_button_black);
            close.setTextColor(Color.WHITE);

            //Button cancel,backspace,exponential,divide;
            cancel.setBackgroundResource(R.drawable.round_button_black);
            cancel.setTextColor(Color.WHITE);

            backspace.setBackgroundResource(R.drawable.round_button_black);
            backspace.setTextColor(Color.WHITE);

            exponential.setBackgroundResource(R.drawable.round_button_black);
            exponential.setTextColor(Color.WHITE);

            divide.setBackgroundResource(R.drawable.round_button_black);
            divide.setTextColor(Color.WHITE);

            //Button seven,eight,nine,multiply;
            seven.setBackgroundResource(R.drawable.round_button_black);
            seven.setTextColor(Color.WHITE);

            eight.setBackgroundResource(R.drawable.round_button_black);
            eight.setTextColor(Color.WHITE);

            nine.setBackgroundResource(R.drawable.round_button_black);
            nine.setTextColor(Color.WHITE);

            multiply.setBackgroundResource(R.drawable.round_button_black);
            multiply.setTextColor(Color.WHITE);

            //Button four,five,six,minus;
            four.setBackgroundResource(R.drawable.round_button_black);
            four.setTextColor(Color.WHITE);

            five.setBackgroundResource(R.drawable.round_button_black);
            five.setTextColor(Color.WHITE);

            six.setBackgroundResource(R.drawable.round_button_black);
            six.setTextColor(Color.WHITE);

            minus.setBackgroundResource(R.drawable.round_button_black);
            minus.setTextColor(Color.WHITE);

            //Button one,two,three,plus;
            one.setBackgroundResource(R.drawable.round_button_black);
            one.setBackgroundResource(R.drawable.round_button_black);
            one.setTextColor(Color.WHITE);

            two.setBackgroundResource(R.drawable.round_button_black);
            two.setTextColor(Color.WHITE);

            three.setBackgroundResource(R.drawable.round_button_black);
            three.setTextColor(Color.WHITE);

            plus.setBackgroundResource(R.drawable.round_button_black);
            plus.setTextColor(Color.WHITE);

            //Button zero,point,equal;
            zero.setBackgroundResource(R.drawable.round_button_black);
            zero.setTextColor(Color.WHITE);

            point.setBackgroundResource(R.drawable.round_button_black);
            point.setTextColor(Color.WHITE);

            equal.setBackgroundResource(R.drawable.round_button_black);
            equal.setTextColor(Color.WHITE);

            //ImageButton changeColor;
            changeColor.setBackgroundResource(R.drawable.round_button_black);
            changeColor.setImageResource(R.drawable.palette_black);


        }
        isBackgroundWhite = !isBackgroundWhite;
    }


}
