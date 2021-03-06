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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

import java.util.Map;
import java.util.Set;


public class accountActivity extends AppCompatActivity implements incomeDialog.incomeDialogListener,expenseDialog.expenseDialogListener {


    @Override
    public void applyTextsExpense(String year, String month, String day, String money, String content) {

    }

    @Override
    public void applyTexts(String year, String month, String day, String money, String content) {
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
    FloatingActionButton fab_plus, fab_income, fab_expense;
    Animation FabOpen, FabClose, FabRClockingwise, FabRAnticlockingwise;
    boolean isOpen = false;
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


        //fab버튼 애니메이션에 대한 설정도 함께 되어있습니다.
        //fab 플러스 버튼을 누를시 돌아가면 추가적으로 다른 수입지출을 선택할 수 있는
        // 버튼이 보이게 됩니다.

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monty = cal.get(Calendar.MONTH);
        int dat = cal.get(Calendar.DAY_OF_MONTH);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fab_expense.startAnimation(FabClose);
                    fab_income.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRAnticlockingwise);
                    fab_expense.setClickable(false);
                    fab_income.setClickable(false);
                    isOpen = false;
                } else {
                    fab_expense.startAnimation(FabOpen);
                    fab_income.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockingwise);
                    fab_expense.setClickable(true);
                    fab_income.setClickable(true);
                    isOpen = true;
                }
            }
        });
        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpenseDialog();
            }
        });
        fab_income.setOnClickListener(new View.OnClickListener() {
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

    //income dialog 불러오는 function 입니다.
    public void openDialog(){
        incomeDialog incomeDialog= new incomeDialog();
        incomeDialog.show(getSupportFragmentManager(),"income dialog");
    }
    //expense dialog 불러오는 function 입니다.
    public void openExpenseDialog(){
        expenseDialog expenseDialog=new expenseDialog();
        expenseDialog.show(getSupportFragmentManager(),"expense dialog");
    }
    //달력 dialog를 불러오는 function 입니다.
    public void calendarDialog() {
        calendarDialog calendDialog = new calendarDialog();
        calendDialog.show(getSupportFragmentManager(), " dialog");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class OneDayDecorator implements DayViewDecorator {

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

    public class RoundedBackgroundSpan extends ReplacementSpan {

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
            paint.setColor(Color.BLUE);
            canvas.drawRoundRect(rect, 10f, 10f, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(text, start, end, x, y, paint);
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return Math.round(measureText(paint, text, start, end));
        }

        private float measureText(Paint paint, CharSequence text, int start, int end) {
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


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        //태웅

        private RecyclerView recyclerView;
        private AccountRecyclerViewAdapter accountRecyclerViewAdapter;


        private List<AccountDTO> accountDTOs = new ArrayList<>();
        private HashMap<String, Integer> map_account = new HashMap<String, Integer>();
        //     private List<AccountDTO> bar_accountDTOs


        //현재시간
        private long now = System.currentTimeMillis();
        private Date date = new Date(now);
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        private String formatDate = simpleDateFormat.format(date);

        //년 월 일로 쪼개기
        private String[] dateArr = formatDate.split("/");

        private int _year = Integer.parseInt(dateArr[0]);
        private int _month = Integer.parseInt(dateArr[1]);
        private int _day = Integer.parseInt(dateArr[2]);

        Calendar ___cal = Calendar.getInstance();


        private ImageButton accountFragmentRight_ImageButton;
        private ImageButton accountFragmentLeft_ImageButton;


        private TextView accountFragmentMainDate_TextView;

        private FirebaseDatabase mDatabase;
        private FirebaseAuth mAuth;

        private List<User> userList = new ArrayList<>();


        private GridView mGridView;
        private DateAdapter adapter;
        private ArrayList arrData = new ArrayList();

        private Calendar mCal;

        private GridView day_GirdView;
        private DateDayOfWeekAdapter day_adapter;
        private Calendar mCalToday; //오늘 요일을 받아온다.

        private Button preBtn;
        private Button nextBtn;
        private TextView mainText;
        private int thisYear;
        private int thisMonth;

        private List<AccountDTO> cal_accountDTOs = new ArrayList<>();
        private HashMap<String, Integer> cal_map_account = new HashMap<String, Integer>();


        //파이차트 컨탠츠
        HashMap<String, Integer> map_pichart_content = new HashMap<>();
        Iterator<String> map_pichart_content_iterator;
        PieChart pieChart;


        RecyclerView __recyclerView;
        AccountContentRecyclerViewAdapter contentRecyclerViewAdapter;
        List<AccountContentDescriptionDTO> a = new ArrayList<AccountContentDescriptionDTO>();


        //태웅 끝

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

        BarChart chart;
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        ArrayList<String> BarEntryLabels;
        BarDataSet Bardataset;
        BarData BARDATA;


        public void AddValuesToBARENTRY() {


            //final List<AccountDTO>[] aaatemp_accountDTOs = new List<AccountDTO>;

            Log.i("asdljf;lkasdjf2", accountDTOs.size() + "");


        }

        public void AddValuesToBarEntryLabels() {

            /*
            BarEntryLabels.add("January");
            BarEntryLabels.add("February");
            BarEntryLabels.add("March");
            BarEntryLabels.add("April");
            BarEntryLabels.add("May");
            BarEntryLabels.add("June");
*/
        }


        public void setCalendarDate(int month, final View view) {
            // 요일은 +1해야 되기때문에 달력에 요일을 세팅할때에는 -1 해준다.
            mCal.set(Calendar.MONTH, month - 1);
            cal_accountDTOs.clear();
            arrData.clear();

            //오늘 요일을 받아온다.
            mCalToday = Calendar.getInstance();
            mCalToday.set(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), 1);
            int startday = mCalToday.get(Calendar.DAY_OF_WEEK);
            if (startday != 1) {
                AccountDTO temp_AccountDTO = new AccountDTO();
                temp_AccountDTO.date = "";
                temp_AccountDTO.money = "";
                for (int i = 0; i < startday - 1; i++) {
                    arrData.add("");
                    cal_accountDTOs.add(temp_AccountDTO);
                }
            }

            String myEmail = mAuth.getCurrentUser().getEmail();
            //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
            myEmail = myEmail.replace("@", "");
            myEmail = myEmail.replace(".", "");

            mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cal_map_account.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        AccountContentDescriptionDTO tempAccountContent = snapshot.getValue(AccountContentDescriptionDTO.class);


                        if (cal_map_account.containsKey(tempAccountContent.date)) {
                            //기존에 있으면 값을 빼서 더한다.
                            int temp = cal_map_account.get(tempAccountContent.date);
                            cal_map_account.put(tempAccountContent.date, temp + Integer.valueOf(tempAccountContent.money));

                        } else {
                            //값이 없으면 대입.
                            cal_map_account.put(tempAccountContent.date, Integer.valueOf(tempAccountContent.money));

                        }

                    }

                    for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                        AccountDTO temp_AccountDTO = new AccountDTO();

                        //일 수는 2자리로 표현
                        String temp_day = String.format("%02d", i + 1);
                        //날 2자리로 표현
                        String temp_month = String.format("%02d", thisMonth);
                        temp_AccountDTO.date = String.valueOf(thisYear) + "." + temp_month + "." + temp_day;

                        String myKeyDate = thisYear + "_" + temp_month + "_" + temp_day;
                        if (cal_map_account.containsKey(myKeyDate)) {

                            temp_AccountDTO.money = cal_map_account.get(myKeyDate) + "원";

                        } else {

                            temp_AccountDTO.money = "0원";
                        }

                        cal_accountDTOs.add(temp_AccountDTO);
                    }

                    for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                        arrData.add(i + 1);
                    }

                    adapter = new DateAdapter(getActivity(), arrData, cal_accountDTOs);
                    mGridView = (GridView) view.findViewById(R.id.calGrid);
                    mGridView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {

            MaterialCalendarView mcv;
            MaterialCalendarView mcv2;
            mDatabase = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                // View rootView = inflater.inflate(R.layout.fragment_income, container, false);

                final View rootView = inflater.inflate(R.layout.fragment_custom_calendarview, container, false);
                //태웅 custom calendar


                ///Calendar 객체 생성
                mCal = Calendar.getInstance();
                // thisYear = mCal.get(Calendar.YEAR);
                // thisMonth = mCal.get(Calendar.MONTH) + 1;


                String myEmail = mAuth.getCurrentUser().getEmail();
                //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
                myEmail = myEmail.replace("@", "");
                myEmail = myEmail.replace(".", "");

                //갱신을 위해 ..but 에러가 많을 것 같음.... 무서움 ㅋㅋㅋㅋ
                mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        thisYear = _year;
                        thisMonth = _month;

                        // 달력 세팅
                        //  setCalendarDate(mCal.get(Calendar.MONTH)-1);
                        setCalendarDate(thisMonth, rootView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                preBtn = (Button) rootView.findViewById(R.id.preBtn);
                nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
                mainText = (TextView) rootView.findViewById(R.id.date_title);

                preBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (thisMonth > 1) {
                            thisMonth--;
                            mainText.setText(thisYear +" / " + thisMonth);
                            setCalendarDate(thisMonth, rootView);

                        } else {
                            thisYear--;
                            thisMonth = 12;
                            mainText.setText(thisYear + " / " + thisMonth);
                            setCalendarDate(thisMonth, rootView);
                        }
                    }
                });

                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (thisMonth < 12) {
                            thisMonth++;
                            mainText.setText(thisYear +" / " + thisMonth);
                            setCalendarDate(thisMonth, rootView);
                        } else {
                            thisYear++;
                            thisMonth = 1;
                            mainText.setText(thisYear+ " / " + thisMonth);
                            setCalendarDate(thisMonth, rootView);
                        }

                    }
                });

                day_GirdView = (GridView) rootView.findViewById(R.id.title_gridview);
                day_adapter = new DateDayOfWeekAdapter(getActivity());
                day_GirdView.setAdapter(day_adapter);


                //태웅 custom caledar 끝

                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {

                final View rootView = inflater.inflate(R.layout.activity_char_fragment_pi_chart, container, false);

                pieChart = (PieChart) rootView.findViewById(R.id.piechart);



                final ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                String myEmail = mAuth.getCurrentUser().getEmail();
                //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
                myEmail = myEmail.replace("@", "");
                myEmail = myEmail.replace(".", "");

                //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
                mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        pieChart.setUsePercentValues(true);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setExtraOffsets(5, 10, 5, 5);

                        pieChart.setDragDecelerationFrictionCoef(0.95f);

                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.setTransparentCircleRadius(61f);

                        map_pichart_content.clear();
                        yValues.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            AccountContentDescriptionDTO temp_accountContent = snapshot.getValue(AccountContentDescriptionDTO.class);


                            if (map_pichart_content.containsKey(temp_accountContent.store)) {

                                int remainM = map_pichart_content.get(temp_accountContent.store);
                                remainM += Integer.parseInt(temp_accountContent.money);
                                map_pichart_content.put(temp_accountContent.store, remainM);

                            } else {

                                int firstM = Integer.parseInt(temp_accountContent.money);
                                map_pichart_content.put(temp_accountContent.store, firstM);

                            }

                        }

                        Log.i("asdfjlaskdjflkasjdf1", "dfsfds");
                        //map_pichart_content
                        map_pichart_content_iterator = map_pichart_content.keySet().iterator();

                        while (map_pichart_content_iterator.hasNext()) {
                            String key = (String) map_pichart_content_iterator.next();
                            Log.i("asdfjlaskdjflkasjdf", key + " : " + map_pichart_content.get(key));

                            yValues.add(new PieEntry(map_pichart_content.get(key), key));
                        }

                        Description description = new Description();
                        description.setText("(단위 : %)"); //라벨
                        description.setPosition(rootView.getWidth() - 40, 100);
                        description.setTextSize(15);
                        pieChart.setDescription(description);

                        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

                        PieDataSet dataSet = new PieDataSet(yValues, "Store");
                        dataSet.setSliceSpace(3f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setValueTextColor(Color.BLACK);
                        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                        PieData data = new PieData((dataSet));
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.YELLOW);

                        pieChart.setData(data);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return rootView;

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

                chart = (BarChart) rootView.findViewById(R.id.fragment_chart_barChart);

                ___cal.set(_year, _month - 1, _day);
                String myEmail = mAuth.getCurrentUser().getEmail();
                //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
                myEmail = myEmail.replace("@", "");
                myEmail = myEmail.replace(".", "");

                //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
                mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //    userList.clear();// 수정될 때 데이터가 날라오기 때문에 clear()를 안해주면 쌓인다.
                        BARENTRY.clear();
                        map_account.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            AccountContentDescriptionDTO tempAccountContent = snapshot.getValue(AccountContentDescriptionDTO.class);
                            if (map_account.containsKey(tempAccountContent.date)) {
                                //기존에 있으면 값을 빼서 더한다.
                                int temp = map_account.get(tempAccountContent.date);
                                map_account.put(tempAccountContent.date, temp + Integer.valueOf(tempAccountContent.money));

                            } else {
                                //값이 없으면 대입.
                                map_account.put(tempAccountContent.date, Integer.valueOf(tempAccountContent.money));

                            }

                        }

                        accountDTOs.clear();

                        for (int i = 0; i < ___cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

                            AccountDTO temp_AccountDTO = new AccountDTO();

                            //일 수는 2자리로 표현
                            String temp_day = String.format("%02d", i + 1);
                            //날 2자리로 표현
                            String temp_month = String.format("%02d", _month);

                            temp_AccountDTO.date = String.valueOf(_year) + "." + temp_month + "." + temp_day;


                            String myKeyDate = _year + "_" + temp_month + "_" + temp_day;
                            if (map_account.containsKey(myKeyDate)) {

                                temp_AccountDTO.money = map_account.get(myKeyDate) + "";

                            } else {

                                temp_AccountDTO.money = "0";
                            }
                            accountDTOs.add(temp_AccountDTO);
                        }

                        //차트에 데이터 넣기
                        for (int i = 0; i < accountDTOs.size(); i++) {

                            //String temp_date[] = accountDTOs.get(i).date.split(".");
                            //int __day = Integer.parseInt(temp_date[2]);
                            int __money = Integer.parseInt(accountDTOs.get(i).money);
                            BARENTRY.add(new BarEntry(i + 1, __money));

                        }

                        BarEntryLabels = new ArrayList<String>();
                        AddValuesToBARENTRY();
                        AddValuesToBarEntryLabels();
                        Bardataset = new BarDataSet(BARENTRY, "월별");
                        BARDATA = new BarData(Bardataset);
                        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        chart.setData(BARDATA);
                        chart.animateY(3000);

                        //차트에 데이터 넣기 끝

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return rootView;
            } else {

                //태웅 여기서 하면됨 수입 fragment_account
                //여기서부터 태웅 건들지말 것 !!

                final View rootView = inflater.inflate(R.layout.fragment_account, container, false);
                Calendar cal = Calendar.getInstance();
                cal.set(_year, _month - 1, _day);

                recyclerView = (RecyclerView) rootView.findViewById(R.id.account_recycleView);

                setExpenseMoney(rootView);
                accountFragmentMainDate_TextView = (TextView) rootView.findViewById(R.id.account_date_textview);
                accountFragmentMainDate_TextView.setText(_year + "." + _month);

                accountFragmentRight_ImageButton = (ImageButton) rootView.findViewById(R.id.account_right_button);
                accountFragmentLeft_ImageButton = (ImageButton) rootView.findViewById(R.id.account_left_button);

                accountFragmentRight_ImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (_month == 12) {
                            _month = 1;
                            _year++;
                        } else {
                            _month++;
                        }

                        //갱신
                        accountFragmentMainDate_TextView.setText(_year + "." + String.format("%02d", _month));
                        setExpenseMoney(rootView);
                        //accountRecyclerViewAdapter.notifyDataSetChanged(); //갱신이 안됨..ㅠㅠ

                    }
                });

                accountFragmentLeft_ImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (_month == 1) {
                            _month = 12;
                            _year--;
                        } else {
                            _month--;
                        }

                        //갱신
                        accountFragmentMainDate_TextView.setText(_year + "." + String.format("%02d", _month));
                        setExpenseMoney(rootView);
                        // accountRecyclerViewAdapter.notifyDataSetChanged(); //갱신안됨...ㅠㅠ

                    }
                });

                return rootView;
            }
        }

        public void setExpenseMoney(final View view) {

            Calendar mmCal = Calendar.getInstance();
            mmCal.set(_year, _month - 1, _day);
            int _maxDayOfEnd = mmCal.getActualMaximum(Calendar.DAY_OF_MONTH);

            accountRecyclerViewAdapter = new AccountRecyclerViewAdapter(getActivity(), _maxDayOfEnd, _year, _month, _day);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(accountRecyclerViewAdapter);
        }


        public void setPiChartData() {

            String myEmail = mAuth.getCurrentUser().getEmail();
            //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
            myEmail = myEmail.replace("@", "");
            myEmail = myEmail.replace(".", "");

            //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
            mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    map_pichart_content.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        AccountContentDescriptionDTO temp_accountContent = snapshot.getValue(AccountContentDescriptionDTO.class);


                        if (map_pichart_content.containsKey(temp_accountContent.store)) {


                            int remainM = map_pichart_content.get(temp_accountContent.store);
                            remainM += Integer.parseInt(temp_accountContent.money);
                            map_pichart_content.put(temp_accountContent.store, remainM);

                        } else {

                            int firstM = Integer.parseInt(temp_accountContent.money);
                            map_pichart_content.put(temp_accountContent.store, firstM);

                        }


                    }


                    Log.i("asdfjlaskdjflkasjdf2", "dfsfds");
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

        //가계부 tab 이름 설정 부분입니다.
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
                public int getItemPosition(Object item) {
                    return POSITION_NONE;
                }

                @Override
                public int getCount() {
                    // Show 4 total pages.
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return "지출";
                        case 1:
                            return "캘린더";
                        case 2:
                            return "차트1";
                        case 3:
                            return "차트2";
                    }
                    return null;
                }

            }


    /*
    @Override
    protected  void onResume(){
        super.onResume();
        if(PlaceholderFragment.accountRecyclerViewAdapter == null){

        }else {
            PlaceholderFragment.accountRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
*/}

