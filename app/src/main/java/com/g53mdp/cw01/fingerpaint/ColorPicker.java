package com.g53mdp.cw01.fingerpaint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rrs27 on 2016-10-31.
 */

public class ColorPicker extends AppCompatActivity {

    private final String ACT = "Act02 ColorPicker";
    private final String TAG_COLOR = "color";
    private TextView selectedColor;
    private Button btn11, btn12, btn13, btn14;
    private int
        DEFAULT_COLOR_11 = Color.BLACK,
        DEFAULT_COLOR_12 = Color.BLUE,
        DEFAULT_COLOR_13 = Color.GREEN,
        DEFAULT_COLOR_14 = Color.RED;
    private int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        Log.d(ACT,"onCreate");

        Bundle bundle = getIntent().getExtras();
        int receivedColor = bundle.getInt(TAG_COLOR);

        selectedColor = (TextView) findViewById(R.id.tv_selected_color);
        setCurrentColor(receivedColor);

        setButtons();
    }

    // ## Event methods ## //

    public void onColorBtn11(View v){
        Log.d(ACT,"onColorBtn11");
        setCurrentColor(DEFAULT_COLOR_11);
    }
    public void onColorBtn12(View v){
        Log.d(ACT,"onColorBtn12");
        setCurrentColor(DEFAULT_COLOR_12);
    }
    public void onColorBtn13(View v){
        Log.d(ACT,"onColorBtn13");
        setCurrentColor(DEFAULT_COLOR_13);
    }
    public void onColorBtn14(View v){
        Log.d(ACT,"onColorBtn14");
        setCurrentColor(DEFAULT_COLOR_14);
    }
    public void onOkColor(View v){
        Log.d(ACT,"onOkColor");

        Bundle bundle = new Bundle();
        bundle.putInt(TAG_COLOR,getCurrentColor());

        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK,result);

        finish();
    }

    // ## Class methods ## //

    private int getCurrentColor() {
        return currentColor;
    }

    private void setCurrentColor(int currentColor) {
        Log.d(ACT, "New selected color: " + currentColor);
        this.currentColor = currentColor;
        this.selectedColor.setBackgroundColor(currentColor);
    }

    private void setButtons(){
        btn11 = (Button) findViewById(R.id.btn11);
        btn11.setBackgroundColor(DEFAULT_COLOR_11);

        btn12 = (Button) findViewById(R.id.btn12);
        btn12.setBackgroundColor(DEFAULT_COLOR_12);

        btn13 = (Button) findViewById(R.id.btn13);
        btn13.setBackgroundColor(DEFAULT_COLOR_13);

        btn14 = (Button) findViewById(R.id.btn14);
        btn14.setBackgroundColor(DEFAULT_COLOR_14);
    }

}
