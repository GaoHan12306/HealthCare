package com.ivan.healthcare.healthcare_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.ivan.healthcare.healthcare_android.R;
import com.ivan.healthcare.healthcare_android.view.CalendarView.CalendarTheme;
import com.ivan.healthcare.healthcare_android.view.CalendarView.CalendarView;

/**
 * 显示数据的图表fragemnt
 * Created by Ivan on 16/1/24.
 */
public class CalendarFragment extends Fragment {

    private CalendarView mCalendarView;
    private WebView mSuggestWebView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        mCalendarView = (CalendarView) rootView.findViewById(R.id.history_calendar);
        mCalendarView.setCalendarTheme(CalendarTheme.THEME_LIGHT);
        mCalendarView.setOnCalendarItemClickListener(new CalendarView.OnCalendarItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChartActivity.class);
                intent.putExtra(ChartActivity.CHART_DATE, mCalendarView.getDay(position));
                startActivity(intent);
            }
        });

        mSuggestWebView = (WebView) rootView.findViewById(R.id.suggestwebview);
    }

}