package renhe.cn.demotestapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    private MenuPopupWindow menuPopupWindow;
    private RelativeLayout rootRl;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootRl = (RelativeLayout) findViewById(R.id.root_rl);
        menuPopupWindow = new MenuPopupWindow(this, rootRl);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                long startMs = System.currentTimeMillis();
                Blurry.with(MainActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 0, 255, 255))
                        .async()
                        .capture(findViewById(R.id.right_top))
                        .into((ImageView) findViewById(R.id.right_top));

                Blurry.with(MainActivity.this)
                        .radius(10)
                        .sampling(8)
                        .async()
                        .capture(findViewById(R.id.right_bottom))
                        .into((ImageView) findViewById(R.id.right_bottom));

                Blurry.with(MainActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 255, 255, 0))
                        .async()
                        .capture(findViewById(R.id.left_bottom))
                        .into((ImageView) findViewById(R.id.left_bottom));

                Log.d(getString(R.string.app_name),
                        "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
            }
        });

        findViewById(R.id.button).setOnLongClickListener(new View.OnLongClickListener() {

            private boolean blurred = false;

            @Override public boolean onLongClick(View v) {
                if (blurred) {
                    Blurry.delete((ViewGroup) findViewById(R.id.content));
                } else {
                    long startMs = System.currentTimeMillis();
                    Blurry.with(MainActivity.this)
                            .radius(25)
                            .sampling(2)
                            .async()
                            .animate(500)
                            .onto((ViewGroup) findViewById(R.id.content));
                    Log.d(getString(R.string.app_name),
                            "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
                }

                blurred = !blurred;
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (null != menuPopupWindow) {
                menuPopupWindow.show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
