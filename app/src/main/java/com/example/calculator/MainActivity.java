package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG_CALCULATOR = "CALCULATOR";
    private EditText resultEditText = null;
    private double firstNumber = Double.MIN_VALUE;
    private double secondNumber = Double.MIN_VALUE;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("RK - Calculator");
        resultEditText = (EditText)findViewById(R.id.result);
        TableLayout rootView = (TableLayout) findViewById(R.id.root_view);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int rowCount = rootView.getChildCount();
        for(int i=0;i<rowCount;i++)
        {
            View childView = rootView.getChildAt(i);

            if(childView instanceof TableRow) {
                TableRow tableRow = (TableRow)childView;
                int childCount = tableRow.getChildCount();

                for(int j=0;j<childCount;j++) {

                    childView = tableRow.getChildAt(j);
                    if (childView instanceof Button) {
                        childView.setOnClickListener(this);
                    }
                }
            }
        }
    }
    @Override
    public void onClick(View view) {
        if(view instanceof Button)
        {
            Button button = (Button)view;
            String buttonValue = button.getText().toString();
            int buttonNumber = isInteger(buttonValue);

            if(buttonNumber != -1)
            {
                String currentValue = resultEditText.getText().toString();

                if(isInteger(currentValue) > -1)
                {
                    currentValue += buttonValue;
                    resultEditText.setText(currentValue);
                }else
                {
                    resultEditText.setText(buttonValue);
                }

            }else
            {
                if("+".equals(buttonValue) || "-".equals(buttonValue) || "*".equals(buttonValue) || "/".equals(buttonValue))
                {
                    String currentValue = resultEditText.getText().toString();
                    if(isInteger(currentValue) > -1)
                    {
                        firstNumber = Double.parseDouble(currentValue);
                    }else
                    {
                        firstNumber = Double.MIN_VALUE;
                    }
                    operator = buttonValue;
                    resultEditText.setText(buttonValue);
                }else if("c".equals(buttonValue))
                {
                    firstNumber = Double.MIN_VALUE;
                    secondNumber = Double.MIN_VALUE;
                    resultEditText.setText("");
                }else if("=".equals(buttonValue))
                {
                    String currentValue = resultEditText.getText().toString();
                    if(isInteger(currentValue) > -1)
                    {
                        secondNumber = Double.parseDouble(currentValue);
                    }else
                    {
                        secondNumber = Double.MIN_VALUE;
                    }
                    if(firstNumber > Long.MIN_VALUE && secondNumber > Long.MIN_VALUE) {

                        double resultNumber = 0;
                        if ("+".equals(operator)) {
                            resultNumber = firstNumber + secondNumber;
                        }else if("-".equals(operator))
                        {
                            resultNumber = firstNumber - secondNumber;
                        }else if("*".equals(operator))
                        {
                            resultNumber = firstNumber * secondNumber;
                        }else if("/".equals(operator))
                        {
                            resultNumber = firstNumber / secondNumber;
                        }
                        resultEditText.setText(String.valueOf(resultNumber));
                        secondNumber = resultNumber;
                        firstNumber = Double.MIN_VALUE;
                    }
                }
            }

        }
    }
    private int isInteger(String value)
    {
        int ret = -1;
        try {
            ret = Integer.parseInt(value);
        }catch(NumberFormatException ex)
        {
            Log.e(TAG_CALCULATOR, ex.getMessage());
        }finally {
            return ret;
        }
    }
}