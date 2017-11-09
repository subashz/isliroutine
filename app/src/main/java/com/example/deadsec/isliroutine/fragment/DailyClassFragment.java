package com.example.deadsec.isliroutine.fragment;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deadsec.isliroutine.utils.CustomDecoration;
import com.example.deadsec.isliroutine.model.Day;
import com.example.deadsec.isliroutine.R;
import com.example.deadsec.isliroutine.loader.ClassDataLab;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.List;

public class DailyClassFragment extends Fragment {
    public static final String ARG_DAY = "daily_class_day";
    public static final String GROUP_INDEX = "group_index";
    private int dayId;
    private String day;
    private int groupIndex;
    CalendarDayView dayView;
    ProgressDialog progressDoalog;

    public static final DailyClassFragment newInstance(String day,int groupId) {
        final DailyClassFragment instance = new DailyClassFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        args.putInt(GROUP_INDEX,groupId);
        instance.setArguments(args);
        return instance;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = this.getArguments();
        if (args != null) {
            day = args.getString(ARG_DAY);
            groupIndex=args.getInt(GROUP_INDEX);
        }
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage("This usually takes less than a second ");
        progressDoalog.setTitle("Downloading Classes");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_routine_daily, container, false);
        dayView = (CalendarDayView) view.findViewById(R.id.calendar);
        dayView.setDecorator(new CustomDecoration(getActivity()));

        ((CustomDecoration) (dayView.getDecoration())).setOnEventClickListener(
                new EventView.OnEventClickListener() {
                    @Override
                    public void onEventClick(EventView view, IEvent data) {
                        Log.e("TAG", "onEventClick:" + data.getName());
                        Toast.makeText(getActivity(), "clicked course: " + data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEventViewClick(View view, EventView eventView, IEvent data) {
                        Log.e("TAG", "onEventViewClick:" + data.getName());
                        if (data instanceof ClassModel) {
                            // change event (ex: set event color)
                            dayView.setEvents(ClassDataLab.get(getActivity()).getEvents(day));
                        }
                    }
                });


        List<IEvent> eventList = ClassDataLab.get(getActivity()).getEvents(day);
        dayView.setEvents(eventList);
        progressDoalog.dismiss();
        dayView.setLimitTime(eventList.get(0).getStartTime().getTime().getHours()-1, eventList.get(eventList.size()-1).getEndTime().getTime().getHours()+1);
        return view;
    }
}
