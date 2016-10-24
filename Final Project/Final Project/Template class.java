package kunj.pong;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;



public class MainActivity extends Activity {

    MainView mainView;
    public static float WIDTH, HEIGHT;
    public static float DEPTH;
    public static boolean running = true;
    static Thread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        mainView = new MainView(this);

        WIDTH = mainView.getWidth();
        HEIGHT = mainView.getHeight();
        DEPTH = HEIGHT*2;




        layout.addView(mainView);
        setContentView(layout);
        immersive();//sets the app into full screen Immersive FullScreen Mode.
    }
    @SuppressLint("InlinedApi")
    public void immersive(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void run(final MainView sView){
        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())

                    try{
                        Thread.sleep(50);
                        runOnUiThread(new Runnable(){ // start actions in UI thread

                            @Override
                            public void run(){
                                if(running){
                                    Canvas canvas = sView.getHolder().lockCanvas();
                                    mainView.update(canvas);
                                }
                            }
                        });
                    }
                    catch (InterruptedException e){}
            }
        }); // the while thread will start in BG thread
        thread.start();
    }

}

class MainView extends SurfaceView implements SurfaceHolder.Callback{

    public static float WIDTH;
    public static float HEIGHT;

    public static Paint paint = new Paint();
    public Canvas canvas = new Canvas();
    public static MainActivity context;


   
    public MainView(Context context) {
        super(context);
        this.context = (MainActivity) context;
        this.setBackgroundColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        getHolder().addCallback(this);
    }
    boolean init = false;


    public void init(){

        


        WIDTH = getWidth();
        HEIGHT = getHeight();

        Log.d("init", "init happened");
    }


        public void update(Canvas canvas) {

        this.getHolder().unlockCanvasAndPost(canvas);
        postInvalidate();
        //	Log.d("update", "Updating");
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        return true;
    }


    
    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);

        this.canvas = canvas;
        if(!init){
            init();
            init = true;
        }

    }


    //DO NOT TOUCH!!!----------------------------------------------------------------------------------------------------------
    @Override
    protected void onDraw(Canvas canvas){
        draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.setWillNotDraw(false);
        context.run(this);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

        context.run(this);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            MainActivity.running = false;
            MainActivity.thread.join();
        } catch (InterruptedException e) {
        }
    }
}
