package hunterirving.forte;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView UI_container;
    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        final ImageView UI_container = (ImageView) findViewById(R.id.UI_container);
        position = 0;
        UI_container.setBackgroundResource(R.drawable.atlas);

        UI_container.setOnTouchListener(new View.OnTouchListener() {
            float chunkSize = 80;
            //initialize values
            float Y_start = 0;
            float Y_end = Y_start + 4*chunkSize;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    Y_start = event.getY();
                    return true;


                    /*
                    IDEA:
                    a custom lil coord grid that maps between 0(inclusive)-4*chunk size (exclusive)
                    and we dont worry about original touchdown point
                    just start out system at 0, listen to DELTAS (changes in motion from last reading)
                    and update UI based on this custom coordinate system.
                     */



                }
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    float Y_offset = Y_start - event.getY();


                    //update UI based on position
                    if(Y_offset > 0 && Y_offset < chunkSize){
                        if (position != 0){
                            position = 0;
                            UI_container.setBackgroundResource(R.drawable.atlas);
                        }
                    }
                    else if(Y_offset >= chunkSize && Y_offset < 2*chunkSize) {
                        if (position != 1){
                            position = 1;
                            UI_container.setBackgroundResource(R.drawable.camera);
                        }
                    }
                    else if(Y_offset >= 2*chunkSize && Y_offset < 3*chunkSize) {
                        if (position != 2){
                            position = 2;
                            UI_container.setBackgroundResource(R.drawable.telephone);
                        }
                    }
                    else if(Y_offset >= 3*chunkSize && Y_offset < 4*chunkSize) {
                        if (position != 3){
                            position = 3;
                            UI_container.setBackgroundResource(R.drawable.telegraph);
                        }
                    }

                    return true;
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if(position == 0){
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                        startActivity(launchIntent);
                    }
                    else if(position == 1){
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.GoogleCamera");
                        startActivity(launchIntent);
                    }
                    else if(position ==2){
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.dialer");
                        startActivity(launchIntent);
                    }
                    else if(position ==3){
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.messaging");
                        startActivity(launchIntent);
                    }
                    return true;
                }
                return false;
            }
        });

        //TODO:
        //add sounds and vibration to inform navigation


        //DONE:
        //add onTouchListener to view to listen for MotionEvents
        //update menu when dragged away from starting point passed a threshold
        //make text smaller (inkscape)
        //make it actually launch the appropriate apps... touchdown, drag, release
        //export an APK !!!
        //make it "a launcher"
        //always start on ATLAS (call finish() in onPause())
        //make image go behind the nav bar
        //hide status bar (and nav bar? if possible)
        //add listeners for up and down volume buttons
        //ignore the default behavior of those buttons
        //make it change the bg image when those buttons are hit
    }



    protected void onPause() {
        super.onPause();
        finish();
    }




}
