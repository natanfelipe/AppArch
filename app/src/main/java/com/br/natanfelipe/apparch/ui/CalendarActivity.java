package com.br.natanfelipe.apparch.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.br.natanfelipe.apparch.R;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int newMonth = month+1;
                calendar.set(year,month,dayOfMonth);
                calendarView.setDate(calendar.getTimeInMillis(),true,true);
                Toast.makeText(CalendarActivity.this, dayOfMonth+"/"+newMonth+"/"+year, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
