package com.example.mycalculator;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;


class Calculator {
    //for logging
    private static final String TAG = "TAG";
    //Declare a dynamic array of type operation (an object)
    private ArrayList<operation> operations;

    Calculator(){
        //OOP: empty constructor
    }

    double calculate(String expression)
    throws ArithmeticException,IllegalArgumentException,NullPointerException, ArrayIndexOutOfBoundsException {

        operations = new ArrayList<operation>();

        //Step 0:
        if(expression.equals(""))
            return 0;
        //Step 1: Check whether the number of brackets are balanced

        //Step 2: Separate the expression into the list
        //If there is a bracket, immediate recursion
        StringBuffer accumulatedNumber= new StringBuffer("");
        for(int i=0;i<expression.length();i++){
            char character = expression.charAt(i);
            if(character=='+'||character=='-'||character=='×'||character=='÷'||character=='^'){
                if(!accumulatedNumber.toString().equals("")) {
                    operations.add(new operation(false, accumulatedNumber.toString()));
                    accumulatedNumber = new StringBuffer("");
                }
                operations.add(new operation(true,Character.toString(character)));
            }else{
                accumulatedNumber.append(character);
            }
        }
        //Add the last number
        if(!accumulatedNumber.toString().equals("")) {
            operations.add(new operation(false, accumulatedNumber.toString()));
        }

        //Step 3: Do 'IDMAS'
        int finalValue;

        //searching for ^ (5)
        finalValue = operations.size();
        for(int i=0;i<finalValue;i++){
            if(operations.get(i).getCommand()==5) {
                power(i);
                i--;
                finalValue -= 2;
                continue;
            }
        }
        //searching for /,* (4,3)
        finalValue = operations.size();
        for(int i=0;i<finalValue;i++){
            if(operations.get(i).getCommand()==3) {
                multiply(i);
                i--;
                finalValue -= 2;
                continue;
            }else if(operations.get(i).getCommand()==4){
                divide(i);
                i--;
                finalValue -= 2;
                continue;
            }

        }
        //searching for -,+ (2,1)
        finalValue = operations.size();
        for(int i=0;i<finalValue;i++){
            if(operations.get(i).getCommand()==1) {
                add(i);
                i--;
                finalValue -= 2;
                continue;
            }else if(operations.get(i).getCommand()==2){
                subtract(i);
                i--;
                finalValue -= 2;
                continue;
            }

        }

        if(operations.size()==1&&!operations.get(0).isOperator()){
            return operations.get(0).getValue();
        }else{
            for(int i=0;i<operations.size();i++){
                Log.d(TAG,operations.get(i).toString());
            }
            throw new IllegalArgumentException("PROGRAM ERROR: Please report to developer, this should not appear... (final length != 1)");
        }
    }



    //IDMAS
    void add(int pos){
        double result = 0.0;
        try {
            //add the numbers
            result = operations.get(pos - 1).getValue()+operations.get(pos + 1).getValue();
        }catch(NullPointerException e){
            //To make sure that the number is not null
            throw new NullPointerException("PROGRAM ERROR: Please report to developer, this should not appear... (NullPointerException)");
        }
        if (Double.isInfinite(result))
            //To make sure that result is not "Infinity"
            throw new ArithmeticException("MATH ERROR: Out of range");
        try{
            operations.set(pos-1,new operation(result));
            operations.remove(pos);
            operations.remove(pos);
        }catch(ArrayIndexOutOfBoundsException e){
            //To check whether the syntax are wrong
            throw new ArrayIndexOutOfBoundsException("SYNTAX ERROR");
        }
    }
    void subtract(int pos){
        double result = 0.0;
        try {
            result = operations.get(pos - 1).getValue()-operations.get(pos + 1).getValue();
        }catch(NullPointerException e){
            throw new NullPointerException("PROGRAM ERROR: Please report to developer, this should not appear... (NullPointerException)");
        }
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        try{
            operations.set(pos-1,new operation(result));
            operations.remove(pos);
            operations.remove(pos);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("SYNTAX ERROR");
        }
    }
    void multiply(int pos){
        double result = 0.0;
        try {
            result = operations.get(pos - 1).getValue()*operations.get(pos + 1).getValue();
        }catch(NullPointerException e){
            throw new NullPointerException("PROGRAM ERROR: Please report to developer, this should not appear... (NullPointerException)");
        }
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        try{
            operations.set(pos-1,new operation(result));
            operations.remove(pos);
            operations.remove(pos);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("SYNTAX ERROR");
        }
    }
    void divide(int pos){
        double result = 0.0;
        if(operations.get(pos + 1).getValue()==0)
            throw new ArithmeticException("MATH ERROR: Division by 0");
        try {
            result = operations.get(pos - 1).getValue()/operations.get(pos + 1).getValue();
        }catch(NullPointerException e){
            throw new NullPointerException("PROGRAM ERROR: Please report to developer, this should not appear... (NullPointerException)");
        }
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        try{
            operations.set(pos-1,new operation(result));
            operations.remove(pos);
            operations.remove(pos);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("SYNTAX ERROR");
        }
    }
    void power(int pos){
        double result = 0.0;
        try {
            result = Math.pow(operations.get(pos - 1).getValue(),operations.get(pos + 1).getValue());
        }catch(NullPointerException e){
            throw new NullPointerException("PROGRAM ERROR: Please report to developer, this should not appear... (NullPointerException)");
        }
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        try{
            operations.set(pos-1,new operation(result));
            operations.remove(pos);
            operations.remove(pos);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("SYNTAX ERROR");
        }
    }





    private class operation{
        private boolean isOperator=false;
        private double value=0;
        private int command=0;

        //constructor
        operation(double value){
            isOperator=false;
            this.value = value;
        }
        operation(boolean isOperator, String expression)
        throws IllegalArgumentException{
            this.isOperator = isOperator;
            if(isOperator){
                command = expressionToCommand(expression.charAt(0));
            }else{
                try {
                    value = Double.parseDouble(expression);
                    Log.d(TAG,Double.toString(value));
                }catch(IllegalArgumentException e){
                    throw new IllegalArgumentException("SYNTAX ERROR: Invalid value");
                }



            }
        }

        private int expressionToCommand(char character)
        throws IllegalArgumentException{
            switch(character){
                case '+':
                    return 1;
                case '-':
                    return 2;
                case '×':
                    return 3;
                case '÷':
                    return 4;
                case '^':
                    return 5;
                default:
                    throw new IllegalArgumentException("SYNTAX ERROR: Invalid command");
            }
        }

        boolean isOperator(){
            return isOperator;
        }
        int getCommand(){
            return command;
        }
        double getValue(){
            return value;
        }

        public String toString(){
            return "isOperator: " + isOperator+
                    "\ngetCommand: " + command +
                    "\ngetValue: " + value;
        }
    }
}
