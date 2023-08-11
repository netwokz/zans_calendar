package com.netwokz.zanscalendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<LocalDate> zanCompleteDays = new ArrayList<>();
    String mUiMode;
    ArrayList<CalendarDay> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mUiModePref = getString(R.string.pref_system);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUiMode = mSharedPreferences.getString(mUiModePref, "-1");

        setUiTheme(mUiMode);
        CalendarUtils.selectedDate = LocalDate.now();
        getZanDates();
        initUi();
    }

    private void initUi() {
        MaterialCalendarView mCalView = findViewById(R.id.calendarView);
        CalendarDay mDay = CalendarDay.today();
        EventDecorator mEventDecorator = new EventDecorator(getColor(R.color.teal_200), dates);
        mCalView.addDecorator(mEventDecorator);
    }

    public void setUiTheme(String mode) {
        if (mode.equals("-1")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (mode.equals("1")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (mode.equals("2")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public ArrayList<LocalDate> getZanDates() {
        LocalDate startDate = CalendarUtils.zanStartDate;
        ArrayList<LocalDate> zansDates = new ArrayList<>();
        zansDates.add(startDate);
        for (int i = 0; i < 100; i++) {
            startDate = startDate.plusDays(14);
            zansDates.add(startDate);
        }

        for (int i = 0; i < zansDates.size(); i++) {
            LocalDate newTemp = zansDates.get(i);
            for (int j = 0; j < 7; j++) {
                zanCompleteDays.add(newTemp);
                newTemp = newTemp.plusDays(1);
            }
        }

        for (int i = 0; i < zanCompleteDays.size(); i++) {
//            System.out.println(zanCompleteDays.get(i));
            CalendarDay day = CalendarDay.from(zanCompleteDays.get(i));
            dates.add(day);
        }

        return zanCompleteDays;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CalendarUtils.selectedDate = LocalDate.now();
//        setMonthView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
