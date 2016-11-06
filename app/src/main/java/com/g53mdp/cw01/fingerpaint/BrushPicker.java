package com.g53mdp.cw01.fingerpaint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class BrushPicker extends AppCompatActivity {

    final private String ACT = "Act03 BrushPicker";
    final private int BRUSH_MIN_SIZE = 5;
    private RadioButton radioBtn11, radioBtn21;
    private int brushShape, brushSize;
    private TextView selectedSize;
    private SeekBar seekBar;
    private final String
        TAG_BRUSH_SHAPE = "bShape",
        TAG_BRUSH_SIZE = "bSize";
    private final String
        BRUSH_NAME_ROUND = "Round",
        BRUSH_NAME_SQUARE = "Square";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        Log.d(ACT,"onCreate");

        setWidgetsOpts();

        Bundle bundle = getIntent().getExtras();
        int receivedType = bundle.getInt(TAG_BRUSH_SHAPE);
        int receivedSize = bundle.getInt(TAG_BRUSH_SIZE);

        setBrushShape(receivedType);
        setBrushSize(receivedSize);
    }

    // ## Event methods ## //

    public void onRadioBtns(View view){
        switch(view.getId()) {
            case R.id.rbtn_round:
                setBrushShape(1);
            break;
            case R.id.rbtn_square:
                setBrushShape(2);
            break;
        }
    }

    public void onOkBrush(View v){
        Log.d(ACT,"onOkBrush");

        Bundle bundle = new Bundle();
        bundle.putInt(TAG_BRUSH_SHAPE, getBrushShape());
        bundle.putInt(TAG_BRUSH_SIZE, getBrushSize());

        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK,result);

        finish();
    }

    // ## Class methods ## //
    private int getBrushShape() {
        return brushShape;
    }

    private void setBrushShape(int brushShape) {
         Log.d(ACT,"Current brush shape = " + brushShape + " for [1= round | 2=square]");

        this.brushShape = brushShape;

        if(brushShape ==1){this.radioBtn11.setChecked(true);}
        if(brushShape ==2){this.radioBtn21.setChecked(true);}
    }

    private int getBrushSize() {
        return brushSize;
    }

    private void setBrushSize(int brushSize) {
        Log.d(ACT,"Current brush size:" + brushSize);

        this.brushSize = brushSize;

        String msg = "Brush size: " + brushSize;
        this.selectedSize.setText(msg);

        this.seekBar.setProgress(brushSize);
    }

    private void setWidgetsOpts(){
        radioBtn11 = (RadioButton) findViewById(R.id.rbtn_round);
        radioBtn11.setText(BRUSH_NAME_ROUND);

        radioBtn21 = (RadioButton) findViewById(R.id.rbtn_square);
        radioBtn21.setText(BRUSH_NAME_SQUARE);


        selectedSize = (TextView) findViewById(R.id.tvSize);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBrushSize(progress);
                if (progress< BRUSH_MIN_SIZE){setBrushSize(BRUSH_MIN_SIZE);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}
