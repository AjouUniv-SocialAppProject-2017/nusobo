package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
    public void calendarDialog(){
        calendarDialog calendDialog=new calendarDialog();
        calendDialog.show(getSupportFragmentManager()," dialog");
    }


    /**
     * A placeholder fragment containing a simple view.
     */public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));

        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }
    public class RoundedBackgroundSpan extends ReplacementSpan{

        @Override
        public  void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
        {
            RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
            paint.setColor(Color.BLUE);
            canvas.drawRoundRect(rect, 10f, 10f, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(text, start, end, x, y, paint);
        }
        @Override
        public  int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
        {
            return Math.round(measureText(paint, text, start, end));
        }

        private float measureText(Paint paint, CharSequence text, int start, int end)
        {
            return paint.measureText(text, start, end);
        }

    }
    public static class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
             //   view.addSpan(new RoundedBackgroundSpan);
          //  SpannableString styledString = new SpannableString("Hi");
          //  styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, 0);
          //  styledString.setSpan(styledString,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          //  view.addSpan(styledString);
          view.addSpan(new DotSpan(5, color));
          //  Spannable s=;
            //textView.setMovementMethod(Link);
           // view.addSpan(new BulletSpan());
       //     TextSpan tx=new TextSapn();

    //        word.setSpan(new ForegroundColorSpan(color),5,5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      //      view.addSpan(word);
        }
    }


    public static class PlaceholderFragment extends Fragment{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */





        private RecyclerView recyclerView;
        private AccountRecyclerViewAdapter accountRecyclerViewAdapter;
        private List<AccountDTO> accountDTOs = new ArrayList<>();



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

            MaterialCalendarView mcv;
            MaterialCalendarView mcv2;



            if(getArguments().getInt(ARG_SECTION_NUMBER)==2) {
                View rootView = inflater.inflate(R.layout.fragment_income, container, false);
                //@Bind(R.id.calendarView)

                mcv=(MaterialCalendarView) rootView.findViewById(R.id.calendarView);
                mcv.state().edit()
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setMinimumDate(CalendarDay.from(2017, 0, 1))
                        .setMaximumDate(CalendarDay.from(2030, 11, 31))
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
                Calendar calendar=Calendar.getInstance();

                int year=2017;
                int Month=12;
                int day3= 5;
                calendar.set(year,Month-1,day3);
                ArrayList<CalendarDay> dates = new ArrayList<>();
                CalendarDay day=CalendarDay.from(calendar);
                dates.add(day);
//                calendar.add(Calendar.MONTH, -2);
//                ArrayList<CalendarDay> dates = new ArrayList<>();
//                for (int i = 0; i < 30; i++) {
//                    CalendarDay day = CalendarDay.from(calendar);
//                    dates.add(day);
//                    calendar.add(Calendar.DATE, 5);
//                }
//                       public EventDecorator(int color, Collection<CalendarDay> dates) {
//                    this.color = color;
//                    this.dates = new HashSet<>(dates);
//                }
                List<CalendarDay> calenderDays=dates;
                mcv.addDecorator(new EventDecorator(Color.RED,calenderDays));

                mcv.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        calendarDialog calendarDialog=new calendarDialog();
                        calendarDialog.show(getFragmentManager(),"Calendar Dialog");
                    }
                });

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

                //태웅 여기서 하면됨 수입 fragment_account
                View rootView = inflater.inflate(R.layout.fragment_account, container, false);

                //여기서부터 태웅 건들지말 것 !!

                recyclerView = (RecyclerView)rootView.findViewById(R.id.account_recycleView);
                recyclerView.setHasFixedSize(true);


                accountRecyclerViewAdapter = new AccountRecyclerViewAdapter(getActivity(), accountDTOs);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(accountRecyclerViewAdapter);


                //태웅 끝
                    /*    mcv2.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    }
                }*/
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
