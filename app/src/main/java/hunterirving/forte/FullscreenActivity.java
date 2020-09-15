package hunterirving.forte;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
    private static int position; //selected UI element

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            updateUI(0);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            updateUI(1);
            return true;
        }

        return false;
    }

    private void updateUI(int direction){
        switch (position) {
            case 0: //atlas
                switch (direction) {
                    case 0:
                        //play error sfx and vibrate
                        return;
                    case 1:
                        //increment pos and update bg image
                        position++;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.camera);
                        return;
                }
            case 1: //camera
                switch (direction) {
                    case 0:
                        //decrement pos and update bg image
                        position--;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.atlas);
                        return;
                    case 1:
                        position++;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.telephone);
                        return;
                }

            case 2: //telephone
                switch (direction) {
                    case 0:
                        position--;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.camera);
                        return;
                    case 1:
                        position++;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.telegraph);
                        return;

                }

            case 3: //telegraph
                switch (direction) {
                    case 0:
                        position--;
                        findViewById(R.id.UI_container).setBackgroundResource(R.drawable.telephone);
                        return;
                    case 1:
                        //play error sfx and vibrate
                        return;
                }

        }
    }



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

        position = 0;
        final ImageView UI_container = (ImageView) findViewById(R.id.UI_container);



        //TODO:
        //add onTouchListener to view to listen for MotionEvents
        //update menu when dragged away from starting point passed a threshold
        //make text smaller (inkscape)

        //add sounds and vibration to inform navigation
        //make it actually launch the appropriate apps... touchdown, drag, release
        //export an APK !!! c:

        //DONE
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
