package com.emrekose.pinchzoom;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by emrekose on 19.07.2016.
 */
public class Touch implements View.OnTouchListener {

    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private PointF start = new PointF();
    private PointF mid = new PointF();

    private int mode = Constants.NONE;
    private float oldDist = 1f;


    public static float maxZoom;
    public static float minZoom;
    private float dx; // postTranslate X distance
    private float dy; // postTranslate Y distance
    private float[] matrixValues = new float[9];


    float matrixX = 0; // X coordinate of matrix inside the ImageView
    float matrixY = 0; // Y coordinate of matrix inside the ImageView
    float width = 0; // width of drawable
    float height = 0; // height of drawable

    public Touch() {
        this.maxZoom = Constants.MAX_ZOOM;
        this.minZoom = Constants.MIN_ZOOM;
    }

    public Touch(float minZoom,float maxZoom) {
        this.maxZoom = maxZoom;
        this.minZoom = minZoom;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);


        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());

                Log.d(Constants.TAG, "mode = DRAG" );

                mode = Constants.DRAG;

                break;

            case MotionEvent.ACTION_UP: //first finger lifted

            case MotionEvent.ACTION_POINTER_UP: //second finger lifted
                mode = Constants.NONE;

                Log.d(Constants.TAG, "mode = NONE" );

                break;

            case MotionEvent.ACTION_POINTER_DOWN: //second finger down
                oldDist = spacing(event);

                Log.d(Constants.TAG, "oldDist = " + oldDist);

                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = Constants.ZOOM;

                    Log.d(Constants.TAG, "mode = ZOOM" );
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == Constants.DRAG) {
                    matrix.set(savedMatrix);

                    matrix.getValues(matrixValues);
                    matrixX = matrixValues[2];
                    matrixY = matrixValues[5];
                    width = matrixValues[0] * (view.getDrawable().getIntrinsicWidth());
                    height = matrixValues[4] * (view.getDrawable().getIntrinsicHeight());

                    dx = event.getX() - start.x;
                    dy = event.getY() - start.y;

                    //if image will go outside left bound
                    if (matrixX + dx  > 0){
                        Log.e("dx","left bound " + dx);
                        dx = -matrixX;
                    }
                    //if image will go outside right bound
                    if(matrixX + dx + width < view.getWidth()){
                        dx = view.getWidth() - matrixX - width;
                    }
                    //if image will go oustside top bound
                    if (matrixY + dy > 0){
                        dy = -matrixY;
                    }
                    //if image will go outside bottom bound
                    if(matrixY + dy + height < view.getHeight()){
                        dy = view.getHeight() - matrixY - height;
                    }

                    matrix.postTranslate(dx, dy);
                }
                else if (mode == Constants.ZOOM) {
                    Float newDist = spacing(event);

                    Log.d(Constants.TAG, "newDist = " + newDist);

                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float currentScale = values[Matrix.MSCALE_X];

                        if(scale * currentScale > maxZoom)
                            scale = maxZoom / currentScale;

                        else if (scale * currentScale < minZoom)
                            scale = minZoom / currentScale;

                        matrix.postScale(scale, scale, mid.x, mid.y);

                    }
                    break;
                }
        } //perform the transformation.

        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }

    private float spacing(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
