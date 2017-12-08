package com.example.taewoonglim.nusobo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class ChartFragment extends AppCompatActivity {


    private BarChart barChart;
    // final String[] quaters=new String[]{"Jan","Feb","Mar","April","May","June"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chart);


        barChart = (BarChart)findViewById(R.id.fragment_chart_barChart);


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(44.f, 0));
        barEntries.add(new BarEntry(88.f, 1));
        barEntries.add(new BarEntry(66.f, 2));
        barEntries.add(new BarEntry(33.f, 3));
        barEntries.add(new BarEntry(12.f, 4));
        barEntries.add(new BarEntry(91.f, 5));

    }
    public void AddValuesToBARENTRY(){


    }

    public void AddValuesToBarEntryLabels(){

    }



}
