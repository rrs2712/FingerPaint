package com.g53mdp.cw01.fingerpaint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FingerPainterView paintView;
    private final String ACT = "Act01 MainActivity";
    private final String
        SHARED_PREF = "com.g53mdp.cw01.fingerpaint.MainActivity",
        TAG_COLOR = "color",
        TAG_BRUSH_SHAPE = "bShape",
        TAG_BRUSH_SIZE = "bSize";
    private final int
        COLOR_PICKER_REQUEST_CODE=2,
        BRUSH_PICKER_REQUEST_CODE=3;
    private final int
        DEFAULT_COLOR = Color.BLACK,
        DEFAULT_BRUSH_SIZE = 10;
    private final Paint.Cap
        DEFAULT_BRUSH_SHAPE = Paint.Cap.ROUND;


    // ## Activity	lifecycle management ## //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(ACT,"onCreate");

        paintView = (FingerPainterView) findViewById(R.id.fpaint_view);
        setPaintSettings();

        if(getIntent().getData()!=null){paintView.load(getIntent().getData());}
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ACT,"onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ACT,"onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ACT,"onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveSettings();
        Log.d(ACT,"onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ACT,"onDestroy");
    }

    // ## Event methods ## //

    public void onColor(View v){
        int currentColor = paintView.getColour();

        Bundle bundle = new Bundle();
        bundle.putInt(TAG_COLOR,currentColor);

        Intent intent = new Intent(MainActivity.this,ColorPicker.class);
        intent.putExtras(bundle);

        Log.d(ACT,"onColor pressed");

        startActivityForResult(intent,COLOR_PICKER_REQUEST_CODE);
    }

    public void onBrush(View v){
        int currentShape = brushAdaptor(paintView.getBrush());
        int currentSize = paintView.getBrushWidth();

        Bundle bundle = new Bundle();
        bundle.putInt(TAG_BRUSH_SHAPE,currentShape);
        bundle.putInt(TAG_BRUSH_SIZE,currentSize);

        Intent intent = new Intent(MainActivity.this,BrushPicker.class);
        intent.putExtras(bundle);

        Log.d(ACT,"onBrush pressed");

        startActivityForResult(intent,BRUSH_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_CANCELED){
            Log.d(ACT,"Result canceled, hence no changes");
        }else if (resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();

            switch (requestCode){
                case COLOR_PICKER_REQUEST_CODE:
                    int newColor = bundle.getInt(TAG_COLOR);
                    setPaintSettings(newColor);
                    break;
                case BRUSH_PICKER_REQUEST_CODE:
                    Paint.Cap newShape = brushAdaptor(bundle.getInt(TAG_BRUSH_SHAPE));
                    int newSize = bundle.getInt(TAG_BRUSH_SIZE);
                    setPaintSettings(newShape,newSize);
                    break;
                default:
                    setPaintSettings();
                    Log.w(ACT,"Result is ok but request code doesn't match");
                    break;
            }
        }
    }

    // ## Class methods ## //

    private Paint.Cap brushAdaptor(int brush){
        return Paint.Cap.values()[brush];
    }
    private int brushAdaptor(Paint.Cap brush){return brush.ordinal();}

    /*
    * Color settings*/
    private void setPaintSettings(int color){
        paintView.setColour(color);
        Log.d(ACT,"setPaintSettings - new color added");
    }

    /*
    * Brush settings*/
    private void setPaintSettings(Paint.Cap brushShape, int brushSize){
        paintView.setBrush(brushShape);
        paintView.setBrushWidth(brushSize);
        Log.d(ACT,"setPaintSettings - new brush settings added");
    }

    /*
    * Default ini / saved settings*/
    private void setPaintSettings(){
        SharedPreferences settings = getSharedPreferences(SHARED_PREF, 0);

        Paint.Cap def_bru_sha = DEFAULT_BRUSH_SHAPE;
        if (settings.contains(TAG_BRUSH_SHAPE)){
            int back_to_def_brush = brushAdaptor(DEFAULT_BRUSH_SHAPE);
            int saved_brush = settings.getInt(TAG_BRUSH_SHAPE,back_to_def_brush);
            def_bru_sha = brushAdaptor(saved_brush);
        }

        paintView.setColour(settings.getInt(TAG_COLOR,DEFAULT_COLOR));
        paintView.setBrush(def_bru_sha);
        paintView.setBrushWidth(settings.getInt(TAG_BRUSH_SIZE,DEFAULT_BRUSH_SIZE));

        Log.d(ACT,"Paint settings loaded / init");
    }

    private void saveSettings(){
        SharedPreferences settings = getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();

        int currentShape = brushAdaptor(paintView.getBrush());
        editor.putInt(TAG_COLOR, paintView.getColour());
        editor.putInt(TAG_BRUSH_SHAPE,currentShape);
        editor.putInt(TAG_BRUSH_SIZE, paintView.getBrushWidth());

        editor.commit();

        Log.d(ACT,"Paint settings saved");
    }
}
