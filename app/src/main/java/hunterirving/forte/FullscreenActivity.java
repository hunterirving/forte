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
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FullscreenActivity extends AppCompatActivity {

    //private TextView UI_container;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        final TextView UI_container = (TextView) findViewById(R.id.UI_container);


        final String[][] appPairs =
                {
                 {"ATLAS"},{"com.google.android.apps.maps"},
                 {"CAMERA"},{"com.google.android.GoogleCamera"},
                 {"TELEPHONE"},{"com.google.android.dialer"},
                 {"TELEGRAPH"},{"com.google.android.apps.messaging"}
                };



        UI_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_MOVE){

                    return true;
                }

                return false;
            }
        });

        //TODO:

        //improved dragging based on touch deltas..?
        //consider re-implementing volume controls
        //add sounds and vibration to inform navigation


        //DONE:
        //use TextView instead of ImageView (faster)
        //changed green title bar to black
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
