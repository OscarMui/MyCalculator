package com.example.mycalculator;

//import widgets
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //OOP: Extends: Inherit all properties (functions, variables, ...) from "AppCompactActivity"

    //Init variables (widgets)
    TextView input;
    TextView output;
    Button open;
    Button close;
    Button cancel;
    Button backspace;
    Button exponential;
    Button divide;
    Button seven;
    Button eight;
    Button nine;
    Button multiply;
    Button four;
    Button five;
    Button six;
    Button minus;
    Button one;
    Button two;
    Button three;
    Button plus;
    Button changeColor;
    Button zero;
    Button point;
    Button equal;




    @Override
    //Put code that you want to run before the screen launches here
    protected void onCreate(Bundle savedInstanceState) {
        //OOP: call onCreate function of super class
        super.onCreate(savedInstanceState);

        //set layout for activity (from XML file)
        setContentView(R.layout.activity_main);

        //import widgets
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
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


    }
}
