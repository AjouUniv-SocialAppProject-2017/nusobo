package com.example.taewoonglim.nusobo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class accountActivity extends AppCompatActivity implements incomeDialog.incomeDialogListener,expenseDialog.expenseDialogListener {
    @Override
    public void applyTextsExpense(String year, String month, String day, String money, String content) {

    }

    @Override
    public void applyTexts(String year,String month,String day,String money, String content) {
        //textViewuserName.setText(userName);
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    FloatingActionButton fab_plus,fab_income,fab_expense;
    Animation FabOpen,FabClose,FabRClockingwise,FabRAnticlockingwise;
    boolean isOpen=false;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ImageView account_backBtn_imageView;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        fab_plus=(FloatingActionButton)findViewById(R.id.fab_plus);
        fab_income=(FloatingActionButton)findViewById(R.id.fab_income);
        fab_expense=(FloatingActionButton)findViewById(R.id.fab_expense);
        FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockingwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRAnticlockingwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        Calendar cal= Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int monty=cal.get(Calendar.MONTH);
        int dat=cal.get(Calendar.DAY_OF_MONTH);

        fab_plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isOpen){
                    fab_expense.startAnimation(FabClose);
                    fab_income.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRAnticlockingwise);
                    fab_expense.setClickable(false);
                    fab_income.setClickable(false);
                    isOpen=false;
                }
                else{
                    fab_expense.startAnimation(FabOpen);
                    fab_income.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockingwise);
                    fab_expense.setClickable(true);
                    fab_income.setClickable(true);
                    isOpen=true;
                }
            }
        });
        fab_expense.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openExpenseDialog();
            }
        });
        fab_income.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        //뒤로가기 버튼
        account_backBtn_imageView = (ImageView) findViewById(R.id.account_backBtn_imageView);
        account_backBtn_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(accountActivity.this, BoardActivity.class);
                accountActivity.this.startActivity(i);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openDialog(){
        incomeDialog incomeDialog= new incomeDialog();
        incomeDialog.show(getSupportFragmentManager(),"income dialog");
    }
    public void openExpenseDialog(){
        expenseDialog expenseDialog=new expenseDialog();
        expenseDialog.show(getSupportFragmentManager(),"expense dialog");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        BarChart chart ;
        ArrayList<BarEntry> BARENTRY ;
        ArrayList<String> BarEntryLabels ;
        BarDataSet Bardataset ;
        BarData BARDATA ;
        public void AddValuesToBARENTRY(){

            BARENTRY.add(new BarEntry(2f, 0));
            BARENTRY.add(new BarEntry(4f, 1));
            BARENTRY.add(new BarEntry(6f, 2));
            BARENTRY.add(new BarEntry(8f, 3));
            BARENTRY.add(new BarEntry(7f, 4));
            BARENTRY.add(new BarEntry(3f, 5));

        }

        public void AddValuesToBarEntryLabels(){

            BarEntryLabels.add("January");
            BarEntryLabels.add("February");
            BarEntryLabels.add("March");
            BarEntryLabels.add("April");
            BarEntryLabels.add("May");
            BarEntryLabels.add("June");

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER)==2) {
                View rootView = inflater.inflate(R.layout.fragment_income, container, false);

                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==3) {
                View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

                chart = (BarChart) rootView.findViewById(R.id.chart1);

                BARENTRY = new ArrayList<>();

                BarEntryLabels = new ArrayList<String>();

                AddValuesToBARENTRY();

                AddValuesToBarEntryLabels();

                Bardataset = new BarDataSet(BARENTRY, "Projects");
                BARDATA = new BarData(Bardataset);

                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                chart.setData(BARDATA);

                chart.animateY(3000);

                return rootView;
            }
            else {
                View rootView = inflater.inflate(R.layout.fragment_account, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "지출";
                case 1:
                    return "수입";
                case 2:
                    return "차트";
            }
            return null;
        }
    }
}
