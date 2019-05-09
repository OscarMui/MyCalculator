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

        //Step 1:
        if (expression.equals("")){
            throw new ArithmeticException("SYNTAX ERROR: No work to do within the bracket:)");
        }

        //Step 2: Separate the expression into the list
        StringBuffer accumulatedNumber= new StringBuffer("");
        boolean accumulatedSign = false; //TRUE: Negative //FALSE: 0 or Positive

        for(int i=0;i<expression.length();i++){
            char character = expression.charAt(i);
            if(character=='(') {
                //If there is a bracket, immediate recursion
                int j;

                //Add all existing numbers to the operations
                if(!accumulatedNumber.toString().equals("")) {
                    operations.add(new operation(false, accumulatedNumber.toString()));
                    if(accumulatedSign){
                        operation x = operations.get(operations.size()-1);
                        x.setValue(-x.getValue());
                    }
                    accumulatedNumber = new StringBuffer("");
                    accumulatedSign = false;
                }

                //Choose the appropriate close bracket
                int numberOfInnerBrackets=0;
                for(j=i+1;j<expression.length();j++){
                    if(expression.charAt(j)=='(') {
                        numberOfInnerBrackets++;
                    }else if(expression.charAt(j)==')') {
                        if (numberOfInnerBrackets>0)
                            numberOfInnerBrackets--;
                        else
                            break;
                    }
                }

                //If there are no signs b4 the brackets, add a multiplication command to it
                if(operations.size()>0){
                    Log.d(TAG, operations.get(operations.size()-1).toString());
                    if(!operations.get(operations.size()-1).isOperator()){
                        operations.add(new operation(true,"×"));
                        Log.d(TAG,"x added in front");
                    }
                }

                //Main operation in brackets
                Calculator innerCalculator = new Calculator();
                //Do recursion here
                //Add the result from the recursion
                operations.add(new operation(innerCalculator.calculate(expression.substring(i+1,Math.min(j,expression.length()))))); //exclusive j

                //Point the for loop index to after the bracket
                i=j;

            }else if(character==')'){
                throw new IllegalArgumentException("SYNTAX ERROR: Imbalanced parentheses");
            }else if(character=='+'||character=='-'||character=='×'||character=='÷'||character=='^'){
                if(accumulatedNumber.toString().equals("")){
                    if((operations.size()==0||operations.get(operations.size()-1).isOperator())){//If the previous operation is a sign
                        //if it is +- then combine the sign into the next number else throw error
                        if(character=='+'){
                            //NTH
                        }else if(character=='-'){
                            accumulatedSign=!accumulatedSign;
                        }else {
                            throw new IllegalArgumentException("SYNTAX ERROR: improper signs placed together");
                        }
                    }else{
                        throw new IllegalArgumentException("PROGRAM ERROR: Please report to developer (accumulated number.equals('')&&previous operation is not a sign");
                    }
                }else{//If accumulatedNumber contains number

                    operations.add(new operation(false, accumulatedNumber.toString()));
                    if(accumulatedSign){
                        operation x = operations.get(operations.size()-1);
                        x.setValue(-x.getValue());
                    }
                    //reset these variables
                    accumulatedNumber = new StringBuffer("");
                    accumulatedSign = false;

                    operations.add(new operation(true,Character.toString(character)));
                }

            }else{
                accumulatedNumber.append(character);
            }
        }
        //Add the last number
        if(!accumulatedNumber.toString().equals("")) {
            operations.add(new operation(false, accumulatedNumber.toString()));
            if(accumulatedSign){
                operation x = operations.get(operations.size()-1);
                x.setValue(-x.getValue());
            }
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
            Log.d(TAG,"SUCCESSFUL: " + operations.get(0).getValue());
            return operations.get(0).getValue();

        }else{
            for(int i=0;i<operations.size();i++){
                Log.d(TAG,operations.get(i).toString());
            }
            throw new IllegalArgumentException("SYNTAX ERROR: Failed to calculate final answer (final length != 1)");
        }
    }



    //IDMAS
    void add(int pos){
        //Can throw ArrayIndexOutOfBoundsException
        double result = operations.get(pos - 1).getValue()+operations.get(pos + 1).getValue();
        if (Double.isInfinite(result))
            //To make sure that result is not "Infinity"
            throw new ArithmeticException("MATH ERROR: Out of range");
        //Can throw ArrayIndexOutOfBoundsException
        operations.set(pos-1,new operation(result));
        operations.remove(pos);
        operations.remove(pos);
    }
    void subtract(int pos){
        //Can throw ArrayIndexOutOfBoundsException
        double result = operations.get(pos - 1).getValue()-operations.get(pos + 1).getValue();
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        //Can throw ArrayIndexOutOfBoundsException
        operations.set(pos-1,new operation(result));
        operations.remove(pos);
        operations.remove(pos);
    }
    void multiply(int pos){
        //Can throw ArrayIndexOutOfBoundsException
        double result = operations.get(pos - 1).getValue()*operations.get(pos + 1).getValue();
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        //Can throw ArrayIndexOutOfBoundsException
        operations.set(pos-1,new operation(result));
        operations.remove(pos);
        operations.remove(pos);
    }
    void divide(int pos){

        if(operations.get(pos + 1).getValue()==0)
            throw new ArithmeticException("MATH ERROR: Division by 0");
        //Can throw ArrayIndexOutOfBoundsException
        double result = operations.get(pos - 1).getValue()/operations.get(pos + 1).getValue();
        if (Double.isInfinite(result))
            throw new ArithmeticException("MATH ERROR: Out of range");
        //Can throw ArrayIndexOutOfBoundsException
        operations.set(pos-1,new operation(result));
        operations.remove(pos);
        operations.remove(pos);
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
            //debug
            Log.d(TAG,
                    "EXPONENTIAL: \n"+
                    "PARAM 1: " + operations.get(pos - 1).getValue() + "\n"+
                    "PARAM 2: " + operations.get(pos + 1).getValue() + "\n" +
                    "RESULT: " + result
            );

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

        void setCommand(int command){
            this.command = command;
        }
        void setValue(double value){
            this.value = value;
        }

        //for logging
        public String toString(){
            return "isOperator: " + isOperator+
                    "\ngetCommand: " + command +
                    "\ngetValue: " + value;
        }
    }
}
