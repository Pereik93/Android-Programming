package com.hfad.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.wunderlist.slidinglayer.SlidingLayer;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConnectionCallbacks, OnConnectionFailedListener {

    ListView lv;
    private static Context context;



    // use the default shared preference file
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    public static final int PREFERENCE_MODE_PRIVATE = 0;

    //Variables to calculate cash earned after app has been closed and re-opened
    private long endTime;
    private long startTime;
    private long timeDifference;

    private SlidingLayer mSlidingLayer;

    //Money variables
    private  long currentCash = 0;
    private long clickDamage = 1;
    private  long cashUpCost = 1;
    private  long clickCashUpCost = 25;
    private  long bigCashUpCost = 100;
    private  long megaCashUpCost = 500;
    private long managerCashUpCost = 10000;
    private long shopCashUpCost = 25000;
    private long bankCashUpCost = 100000;
    private long scienceCahUpCost = 500000;
    private long spaceCashUpCost = 1000000;
    private long alienCashUpCost = 90000000;
    private long universeCashUpCost = 1000000000;
    private  double increase = 1.55;
    private  long damage = 0;
    private long cps = 0;

    //Owned variables -- how many of each item in the shop
    private long cash_per_click = 0;
    private long auto_clicker = 0;
    private long hot_dog = 0;
    private long small_shop = 0;
    private long manager = 0;
    private long shop = 0;
    private long bank = 0;
    private long science = 0;
    private long spaceship = 0;
    private long alien_worker = 0;
    private long universe = 0;


    // Putting images, string names, cash variables and damage into arraylist which then gets used in custom adapter
    public static int [] prgmImages={R.drawable.images,R.drawable.images2,R.drawable.images3,R.drawable.images4,R.drawable.images5,R.drawable.images6,R.drawable.images7,R.drawable.images8,R.drawable.images9,R.drawable.images10,R.drawable.images11};
    public static String [] prgmNameList={" Cash per Click ","Auto Clicker "," Hot dog Stand "," Small Shop "," Manager "," Shop "," Bank "," Science "," Spaceship "," Alien Workers "," Universe "};
    public long [] prgmLongList={cashUpCost, clickCashUpCost, bigCashUpCost, megaCashUpCost,managerCashUpCost,shopCashUpCost, bankCashUpCost, scienceCahUpCost, spaceCashUpCost, alienCashUpCost, universeCashUpCost };
    public long [] prgmOwnedList={cash_per_click, auto_clicker, hot_dog, small_shop, manager, shop, bank, science, spaceship, alien_worker, universe};
    public long [] prgmDmgList={1, 2, 20, 100, 500, 1000, 1750, 5000, 10000, 50000, 100000};


    // Getters and setters so thst variables can be accessed and changed in Custom Adapter
    public long getCurrentCash() {
        return currentCash;
    }
    public long getCash_per_click() {
        return cash_per_click;
    }
    public void setCash_per_click(long cash_per_click) {
        this.cash_per_click = cash_per_click;
    }
    public long getAuto_clicker() {
        return auto_clicker;
    }
    public void setAuto_clicker(long auto_clicker) {
        this.auto_clicker = auto_clicker;
    }
    public long getHot_dog() {
        return hot_dog;
    }
    public void setHot_dog(long hot_dog) {
        this.hot_dog = hot_dog;
    }
    public long getSmall_shop() {
        return small_shop;
    }
    public void setSmall_shop(long small_shop) {
        this.small_shop = small_shop;
    }
    public long getManager() {
        return manager;
    }
    public void setManager(long manager) {
        this.manager = manager;
    }
    public long getShop() {
        return shop;
    }
    public void setShop(long shop) {
        this.shop = shop;
    }
    public long getBank() {
        return bank;
    }
    public void setBank(long bank) {
        this.bank = bank;
    }
    public long getScience() {
        return science;
    }
    public void setScience(long science) {
        this.science = science;
    }
    public long getSpaceship() {
        return spaceship;
    }
    public void setSpaceship(long spaceship) {
        this.spaceship = spaceship;
    }
    public long getAlien_worker() {
        return alien_worker;
    }
    public void setAlien_worker(long alien_worker) {
        this.alien_worker = alien_worker;
    }
    public long getUniverse() {
        return universe;
    }
    public void setUniverse(long universe) {
        this.universe = universe;
    }
    public void setCurrentCash(long aCurrentCash) {
        currentCash = aCurrentCash;
    }
    public long getClickDamage() {
        return clickDamage;
    }
    public void setClickDamage(long aClickDamage) {

        clickDamage = aClickDamage;
    }
    public long getCashUpCost() {

        return cashUpCost;
    }
    public void setCashUpCost(long aCashUpCost) {

        cashUpCost = aCashUpCost;

    }
    public long getBigCashUpCost() {

        return bigCashUpCost;
    }
    public void setBigCashUpCost(long aBigCashUpCost) {

        clickCashUpCost = aBigCashUpCost;
    }
    public long getMegaCashUpCost() {

        return megaCashUpCost;
    }
    public void setMegaCashUpCost(long aMegaCashUpCost) {

        megaCashUpCost = aMegaCashUpCost;
    }
    public long getManagerCashUpCost() {
        return managerCashUpCost;
    }
    public void setManagerCashUpCost(long managerCashUpCost) {
        this.managerCashUpCost = managerCashUpCost;
    }
    public long getShopCashUpCost() {
        return shopCashUpCost;
    }
    public void setShopCashUpCost(long shopCashUpCost) {
        this.shopCashUpCost = shopCashUpCost;
    }
    public long getBankCashUpCost() {
        return bankCashUpCost;
    }
    public void setBankCashUpCost(long bankCashUpCost) {
        this.bankCashUpCost = bankCashUpCost;
    }
    public long getScienceCahUpCost() {
        return scienceCahUpCost;
    }
    public void setScienceCahUpCost(long scienceCahUpCost) {
        this.scienceCahUpCost = scienceCahUpCost;
    }
    public long getSpaceCashUpCost() {
        return spaceCashUpCost;
    }
    public void setSpaceCashUpCost(long spaceCashUpCost) {
        this.spaceCashUpCost = spaceCashUpCost;
    }
    public long getAlienCashUpCost() {
        return alienCashUpCost;
    }
    public void setAlienCashUpCost(long alienCashUpCost) {
        this.alienCashUpCost = alienCashUpCost;
    }
    public long getUniverseCashUpCost() {
        return universeCashUpCost;
    }
    public void setUniverseCashUpCost(long universeCashUpCost) {
        this.universeCashUpCost = universeCashUpCost;
    }
    public double getIncrease() {

        return increase;
    }
    public void setIncrease(double aIncrease) {

        increase = aIncrease;
    }
    public long getDamage() {

        return damage;
    }
    public void setDamage(long aDamage) {

        damage = aDamage;
    }
    public long getClickCashUpCost() {

        return clickCashUpCost;
    }
    public void setClickCashUpCost(long aClickCashUpCost) {

        clickCashUpCost = aClickCashUpCost;
    }
    public long getCps() {

        return cps;
    }
    public void setCps(long aCps) {

        cps = aCps;
    }

    //Setting main content view and basic initializing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.w("myTag", "onCreate");

        //Intent to the music service
        Intent svc=new Intent(this, MusicService.class);
        startService(svc);

        context=this;

        // initializing the custom adapter, assigning it the values in the arrya lists
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages, prgmLongList, prgmOwnedList, prgmDmgList));




        // Retrieving the saved variables from shared preferences
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        endTime = preferenceSettings.getLong("endTime", endTime);
        currentCash = preferenceSettings.getLong("currentCash", 0);
        clickDamage = preferenceSettings.getLong("clickDamage", 1);
        damage = preferenceSettings.getLong("damage", 0);
        cps = preferenceSettings.getLong("cps", 0);
        cashUpCost = preferenceSettings.getLong("cashUpCost", 1);
        clickCashUpCost = preferenceSettings.getLong("clickCashUpCost", 25);
        bigCashUpCost = preferenceSettings.getLong("bigCashUpCost", 100);
        megaCashUpCost = preferenceSettings.getLong("megaCashUpCost", 500);
        cash_per_click = preferenceSettings.getLong("cash_per_click", cash_per_click);
        auto_clicker = preferenceSettings.getLong("auto_clicker", auto_clicker);
        hot_dog = preferenceSettings.getLong("hot_dog", hot_dog);
        small_shop = preferenceSettings.getLong("small_shop", small_shop);
        manager = preferenceSettings.getLong("manager", manager);
        shop = preferenceSettings.getLong("shop", shop);
        bank = preferenceSettings.getLong("bank", bank);
        science = preferenceSettings.getLong("science", science);
        spaceship = preferenceSettings.getLong("spaceship", spaceship);
        alien_worker = preferenceSettings.getLong("alien_worker", alien_worker);
        universe = preferenceSettings.getLong("universe", universe);
        managerCashUpCost = preferenceSettings.getLong("managerCashUpCost", managerCashUpCost);
        shopCashUpCost = preferenceSettings.getLong("shopCashUpCost", shopCashUpCost);
        bankCashUpCost = preferenceSettings.getLong("bankCashUpCost", bankCashUpCost);
        scienceCahUpCost = preferenceSettings.getLong("scienceCahUpCost", scienceCahUpCost);
        spaceCashUpCost = preferenceSettings.getLong("spaceCashUpCost", spaceCashUpCost);
        alienCashUpCost = preferenceSettings.getLong("alienCashUpCost", alienCashUpCost);
        universeCashUpCost = preferenceSettings.getLong("universeCashUpCost", universeCashUpCost);


        // Calculating time since last time application was activated
        // Calculating cash that should have been made
        // Gives the cash.
        startTime = System.currentTimeMillis();

        timeDifference = startTime - endTime;
        timeDifference = timeDifference/1000;

        currentCash = currentCash + (cps * timeDifference);
        //Check if cash made since last time is greater than 0.
        //This is so that it doesnt pop up on orientation change.
        if ((timeDifference * cps) > 0){
            Toast toast = Toast.makeText(context, "You have made " + (timeDifference * cps) + " since last visit", Toast.LENGTH_LONG);
            toast.show();
        }

        bindViews();
        initState();

        //Initializing SoundPool and playing the blop sound stored in the raw folder.
        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundId = sp.load(this, R.raw.blop, 1);

        //content main xml//
        View view = findViewById(R.id.onTouch_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.cash_image_view);

        //setting onTouchListener to content_main.xml (onTouch_layout)
        if (view != null) {
            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        currentCash = currentCash + clickDamage;
                        imageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale));
                        sp.play(soundId, 1,1,0,0,1);
                    }

                    //Method to update text views
                    UpdateTextViews();

                    return false;
                }
            });
        }

        //Method to update text views
        UpdateTextViews();

        //Making the drawer menu at the top left corner of the application
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        startTimerThread();







    }

    public static Context getContext() {
        return context;
    }

    // Called when activity gets visible
    @Override
    public void onStart(){
        super.onStart();
        Log.w("myTag", "onStart");

    }





// Making shop menu show with an offset value on bottom.
    private void initState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setupLayerOffset(prefs.getBoolean("layer_has_offset", true));
    }

    // Binding the RelativeLayout (shop view) to the bottom of the screen.
    private void bindViews() {
        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSlidingLayer.getLayoutParams();
        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        rlp.width = LayoutParams.MATCH_PARENT;
        rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_size);
    }

    //Thread to update cash each second accoring to cash per second variable (cps).
    private void startTimerThread() {
        Thread th = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentCash = currentCash+cps;
                            UpdateTextViews();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }

    //function for back button in android. Close drawer if drawer is open.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // setting the offset on the shop view (RelativeLayout)
    private void setupLayerOffset(boolean enabled) {
        int offsetDistance;
        if (enabled)
            offsetDistance = getResources().getDimensionPixelOffset(R.dimen.offset_distance);
        else offsetDistance = 0;
        mSlidingLayer.setOffsetDistance(offsetDistance);
    }

    // function to close shop view if you swipe down.
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mSlidingLayer.isOpened()) {
                    mSlidingLayer.closeLayer(true);
                    return true;
                }

            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    // This function takes a screenshot of the view.
    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    //Saving the screenshot
    private static File saveBitmap(Bitmap bm, String fileName){
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //method to make and show notification in android
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Cash Farm notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.shop_cash);

        return builder.build();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                scheduleNotification(getNotification("5 second delay"), 5000);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("10 second delay"), 10000);
                return true;
            case R.id.action_30:
                scheduleNotification(getNotification("30 second delay"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_achievements) {
        //Not yet implemented

        } else if (id == R.id.nav_statistics) {
            // Throwing an intent to the statistics class
            startActivity((new Intent(this, StatisticsActivity.class)));
        }
        else if (id == R.id.nav_share) {
            //Taking screenshot and saving it so that it can be shared online.
            Bitmap bm = screenShot(getWindow().getDecorView().findViewById(R.id.onTouch_layout));
            File file = saveBitmap(bm, "application_image.png");
            Log.i("chase", "filepath: "+file.getAbsolutePath());
            Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this application");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "share via"));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   //called whenever an activity goes out of focus.


    //Happens just before the activity is made visible and becomes ready to take
    //user inputs once agan. Only after onPause()
    @Override
    protected void onResume(){
        Log.w("myTag", "onResume");
        //Starting the music service again.
        Intent svc=new Intent(this, MusicService.class);
        startService(svc);


        super.onResume();
    }

    @Override
    protected  void onPause(){
        Log.w("myTag", "onPause");

        //setting currentTimeMillis to endTime variable.
        endTime = System.currentTimeMillis();

        //putting variables that needs to be stored into shared preferences.
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putLong("endTime", endTime);
        preferenceEditor.putLong("currentCash", currentCash);
        preferenceEditor.putLong("clickDamage", clickDamage);
        preferenceEditor.putLong("damage", damage);
        preferenceEditor.putLong("cps", cps);
        preferenceEditor.putLong("cashUpCost", cashUpCost);
        preferenceEditor.putLong("clickCashUpCost", clickCashUpCost);
        preferenceEditor.putLong("bigCashUpCost", bigCashUpCost);
        preferenceEditor.putLong("megaCashUpCost", megaCashUpCost);
        preferenceEditor.putLong("cash_per_click", cash_per_click);
        preferenceEditor.putLong("auto_clicker", auto_clicker);
        preferenceEditor.putLong("hot_dog", hot_dog);
        preferenceEditor.putLong("small_shop", small_shop);
        preferenceEditor.putLong("manager", manager);
        preferenceEditor.putLong("shop", shop);
        preferenceEditor.putLong("bank", bank);
        preferenceEditor.putLong("science", science);
        preferenceEditor.putLong("spaceship", spaceship);
        preferenceEditor.putLong("alien_worker", alien_worker);
        preferenceEditor.putLong("universe", universe);
        preferenceEditor.putLong("managerCashUpCost", managerCashUpCost);
        preferenceEditor.putLong("shopCashUpCost", shopCashUpCost);
        preferenceEditor.putLong("bankCashUpCost", bankCashUpCost);
        preferenceEditor.putLong("scienceCahUpCost", scienceCahUpCost);
        preferenceEditor.putLong("spaceCashUpCost", spaceCashUpCost);
        preferenceEditor.putLong("alienCashUpCost", alienCashUpCost);
        preferenceEditor.putLong("universeCashUpCost", universeCashUpCost);
        preferenceEditor.commit();
        Log.w("myTag", "onStop");

        super.onPause();
    }

    //onStop() is executed whenever an activity becomes invisible.
    //If you have activity started and click on the home button, you call onStop().
    @Override
    protected void onStop() {
        //Stopping the music service
        stopService(new Intent(this, MusicService.class));
        super.onStop();
    }

    //onDestroy() is called whenever the activity is destroyed.
    //If you start an activity and then press the back button,
    //the activity will be destroyed.
    @Override
    protected void onDestroy(){
        Log.w("myTag", "onDestroy");
        //Stopping the music service
        stopService(new Intent(this, MusicService.class));

        super.onDestroy();
    }

    //Function to update the required textViews to avoid code duplication.
    public void UpdateTextViews(){

        //tried updating textViews in shop_view
       // ((BaseAdapter)CustomAdapter).notifyDataSetChanged();
        //CustomAdapter.notifyDataSetChanged();


        TextView total_clicks;
        total_clicks = (TextView) findViewById(R.id.total_clicks);
        total_clicks.setText(String.valueOf(context.getString(R.string.cash) + " " + currentCash));

        TextView cdv;
        cdv = (TextView) findViewById(R.id.clickDamage_view);
        cdv.setText(String.valueOf(context.getString(R.string.cpc) + " " + clickDamage));

        TextView cpsView;
        cpsView = (TextView) findViewById(R.id.cps_view);
        cpsView.setText(String.valueOf(context.getString(R.string.cps) + " " + cps));


    }


    //Functions needed for Google plus API --------> NOT IMPLEMENTED YET
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}