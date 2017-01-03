package com.hfad.myapplication;

/**
 * Created by perol on 20.04.2016.
 */
import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CustomAdapter extends BaseAdapter{

    // initializing variables.
    String [] result;
    long [] result2;
    long [] result3;
    long[] result4;
    MainActivity mainActivity;
    int [] imageId;



    //Adding variables from MainActivity to CustomAdapter
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages, long[] prgmLongList, long[] prgmOwnedList, long[] prgmDmgList) {
        // Setting initialized variables to a value
        result=prgmNameList;
        this.mainActivity=mainActivity;
        imageId=prgmImages;
        result2=prgmLongList;
        result3 = prgmOwnedList;
        result4 = prgmDmgList;

        inflater = ( LayoutInflater )mainActivity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // Getting the length of result.
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // getting item position
        return position;
    }

    @Override
    public long getItemId(int position) {
        // getting item ID
        return position;
    }

// Holder for the TextViews and ImageViews
    public class Holder
    {
        TextView tv;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        ImageView img;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Initializing holder.
        final Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        final Context c = mainActivity.getApplicationContext();
        //Setting values to the textViews and ImageViews in the holder.
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.tv2=(TextView) rowView.findViewById(R.id.textView2);
        holder.tv3=(TextView) rowView.findViewById(R.id.textView3);
        holder.tv4=(TextView) rowView.findViewById(R.id.textView4);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.tv2.setText(String.valueOf(c.getString(R.string.cost)+ " " + result2[position]));
        holder.tv3.setText(String.valueOf(c.getString(R.string.owned)+ " " + result3[position]));
        holder.tv4.setText(String.valueOf("Dmg: " + result4[position]));

        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting variables from MainActivity and assigning them to variables in CustomAdapter.
                long currentCash = mainActivity.getCurrentCash();
                long cashUpCost = mainActivity.getCashUpCost();
                long clickDamage = mainActivity.getClickDamage();
                double increase = mainActivity.getIncrease();
                long cps = mainActivity.getCps();
                long clickCashUpCost = mainActivity.getClickCashUpCost();
                long megaCashUpCost = mainActivity.getMegaCashUpCost();
                long bigCashUpCost = mainActivity.getBigCashUpCost();



                 long managerCashUpCost = mainActivity.getManagerCashUpCost();
                 long shopCashUpCost = mainActivity.getShopCashUpCost();
                 long bankCashUpCost = mainActivity.getBankCashUpCost();
                 long scienceCahUpCost = mainActivity.getScienceCahUpCost();
                 long spaceCashUpCost = mainActivity.getSpaceCashUpCost();
                 long alienCashUpCost = mainActivity.getAlienCashUpCost();
                 long universeCashUpCost = mainActivity.getUniverseCashUpCost();

                 long cash_per_click = mainActivity.getCash_per_click() ;
                 long auto_clicker = mainActivity.getAuto_clicker();
                 long hot_dog = mainActivity.getHot_dog();
                 long small_shop = mainActivity.getSmall_shop();
                 long manager = mainActivity.getManager();
                 long shop = mainActivity.getShop();
                 long bank = mainActivity.getBank();
                 long science = mainActivity.getScience();
                 long spaceship = mainActivity.getSpaceship();
                 long alien_worker = mainActivity.getAlien_worker();
                 long universe = mainActivity.getUniverse();

                //Variables for color flash on item click
                  final int RED = 0xffFF8080;
                  final int GREEN = 0xff00BB00;

                //Switch case for what happens when items are clicked.
                switch (position) {
                    case 0:
                        //Checking if you got enough cash
                        if (currentCash < cashUpCost) {
                            Toast.makeText(mainActivity.getApplicationContext(), "You need " + (cashUpCost - currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            //Updating the information in Holder.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + cashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + cash_per_click);

                        } else {
                            //try /catch for playing sound when item is bought.
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback - Green: Item successfully bought.
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            // Incrementing cash_per_click by 1 per purchase.
                            mainActivity.setCash_per_click(cash_per_click + 1);
                            Log.w("myTag", cash_per_click + "");

                            //Incrementing clickDamage
                            mainActivity.setClickDamage(clickDamage + 1);

                            //Updating currentCash variable.
                            mainActivity.setCurrentCash(currentCash - cashUpCost);

                            //calculating increased cost after purchase.
                            Double d = cashUpCost * increase;
                            mainActivity.setCashUpCost(Math.round(d));

                            //updating textViews in MainActivity
                            mainActivity.UpdateTextViews();

                            //Updating values in Holder.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + cashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + cash_per_click);

                        }
                        break;
                    case 1:
                        if (currentCash < clickCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (clickCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();

                            //Static string because you cant use Context if you do not extend Activity.
                            //CustomAdapted extends BaseAdapter.
                            //Example code that would word in activity:
                            // context.getString(R.string.cost);
                            // generic strings only return an INT at the moment.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + clickCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + auto_clicker);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setAuto_clicker(auto_clicker + 1);
                            mainActivity.setCps(cps + 2);
                            mainActivity.setCurrentCash(currentCash - clickCashUpCost);
                            Double d = clickCashUpCost * increase;
                            mainActivity.setClickCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();

                            //Static string because you cant use Context if you do not extend Activity.
                            //CustomAdapted extends BaseAdapter.
                            //Example code that would word in activity:
                            // context.getString(R.string.cost);
                            // generic strings only return an INT at the moment.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + clickCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + auto_clicker);
                        }
                        break;
                    case 2:
                        if (currentCash < bigCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (bigCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();

                            //Static string because you cant use Context if you do not extend Activity.
                            //CustomAdapted extends BaseAdapter.
                            //Example code that would word in activity:
                            // context.getString(R.string.cost);
                            // generic strings only return an INT at the moment.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + bigCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + hot_dog);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setHot_dog(hot_dog + 1);
                            mainActivity.setCps(cps + 20);
                            mainActivity.setCurrentCash(currentCash - bigCashUpCost);;
                            Double d = bigCashUpCost * increase;
                            mainActivity.setBigCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();

                            //Static string because you cant use Context if you do not extend Activity.
                            //CustomAdapted extends BaseAdapter.
                            //Example code that would word in activity:
                            // context.getString(R.string.cost);
                            // generic strings only return an INT at the moment.
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + bigCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + hot_dog);
                        }
                        break;
                    case 3:
                        if (currentCash < megaCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (megaCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + megaCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + small_shop);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setSmall_shop(small_shop + 1);
                            mainActivity.setCps(cps + 100);
                            mainActivity.setCurrentCash(currentCash - megaCashUpCost);
                            Double d = megaCashUpCost * increase;
                            mainActivity.setMegaCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + megaCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + small_shop);
                        }
                        break;
                    case 4:
                        if (currentCash < managerCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (managerCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + managerCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + manager);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setManager(manager + 1);
                            mainActivity.setCps(cps + 500);
                            mainActivity.setCurrentCash(currentCash - managerCashUpCost);
                            Double d = managerCashUpCost * increase;
                            mainActivity.setManagerCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + managerCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + manager);
                        }
                        break;
                    case 5:
                        if (currentCash < shopCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (shopCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + shopCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + shop);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setShop(shop + 1);
                            mainActivity.setCps(cps + 1000);
                            mainActivity.setCurrentCash(currentCash - shopCashUpCost);
                            Double d = shopCashUpCost * increase;
                            mainActivity.setShopCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + shopCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + shop);
                        }
                        break;
                    case 6:
                        if (currentCash < bankCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (bankCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + bankCashUpCost);
                            holder.tv3.setText("Owned: " + bank);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setBank(bank + 1);
                            mainActivity.setCps(cps + 1750);
                            mainActivity.setCurrentCash(currentCash - bankCashUpCost);
                            Double d = bankCashUpCost * increase;
                            mainActivity.setBankCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + bankCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + bank);
                        }
                        break;
                    case 7:
                        if (currentCash < scienceCahUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (scienceCahUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + scienceCahUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + science);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setScience(science + 1);
                            mainActivity.setCps(cps + 5000);
                            mainActivity.setCurrentCash(currentCash - scienceCahUpCost);
                            Double d = scienceCahUpCost * increase;
                            mainActivity.setScienceCahUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + scienceCahUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + science);
                        }
                        break;
                    case 8:
                        if (currentCash < spaceCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (spaceCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + spaceCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + spaceship);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setSpaceship(spaceship + 1);
                            mainActivity.setCps(cps + 10000);
                            mainActivity.setCurrentCash(currentCash - spaceCashUpCost);
                            Double d = bankCashUpCost * increase;
                            mainActivity.setSpaceCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + spaceCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + spaceship);
                        }
                        break;
                    case 9:
                        if (currentCash < alienCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (alienCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + alienCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + alien_worker);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setAlien_worker(alien_worker + 1);
                            mainActivity.setCps(cps + 50000);
                            mainActivity.setCurrentCash(currentCash - alienCashUpCost);
                            Double d = alienCashUpCost * increase;
                            mainActivity.setAlienCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + alienCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + alien_worker);
                        }
                        break;
                    case 10:
                        if (currentCash < universeCashUpCost){
                            //Animation feedback - RED: not enough money
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", RED, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            Toast.makeText(mainActivity.getApplicationContext(), "You need "+ (universeCashUpCost- currentCash) + " more money",
                                    Toast.LENGTH_SHORT).show();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + universeCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + universe);
                        }
                        else {
                            try {
                                shopSound();
                            }
                            catch (Exception e){
                                System.out.println("Something went wrong: " + e);
                            }
                            //Animation feedback
                            ValueAnimator colorAnim = ObjectAnimator.ofInt(rowView, "backgroundColor", GREEN, 1, 0);
                            colorAnim.setDuration(300);
                            colorAnim.setEvaluator(new ArgbEvaluator());
                            colorAnim.start();

                            mainActivity.setUniverse(universe + 1);
                            mainActivity.setCps(cps + 100000);
                            mainActivity.setCurrentCash(currentCash - universeCashUpCost);
                            Double d = bankCashUpCost * increase;
                            mainActivity.setUniverseCashUpCost(Math.round(d));
                            mainActivity.UpdateTextViews();
                            holder.tv2.setText(c.getString(R.string.cost)+ " " + universeCashUpCost);
                            holder.tv3.setText(c.getString(R.string.owned) + " " + universe);
                        }
                        break;

                }
            }


        });
        return rowView;
    }

    //Function for shop sound. Using SoundPool for a more responsive sound.
    public void shopSound(){
        //Initializing SoundPool and playing the cash_register sound stored in the raw folder.
        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundId = sp.load(mainActivity, R.raw.cash_register, 1);
        sp.play(soundId, 1,1,0,0,1);
    }
}