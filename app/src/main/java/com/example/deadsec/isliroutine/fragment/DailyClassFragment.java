package com.example.deadsec.isliroutine.fragment;

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
    private Day day;
    private int groupIndex;


    CalendarDayView dayView;

    public static final DailyClassFragment newInstance(Day day,int groupId) {
        final DailyClassFragment instance = new DailyClassFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_DAY, day.getValue());
        args.putInt(GROUP_INDEX,groupId);
        Log.d("day.name():",day.getValue()+"");

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
            day = Day.getByValue(args.getInt(ARG_DAY));
            groupIndex=args.getInt(GROUP_INDEX);
        }

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
                            dayView.setEvents(ClassDataLab.getEvents(getActivity(), day));
                        }
                    }
                });


        List<IEvent> eventList = ClassDataLab.getEvents(getActivity(),day);
        dayView.setEvents(eventList);
        dayView.setLimitTime(eventList.get(0).getStartTime().getTime().getHours()-1, eventList.get(eventList.size()-1).getEndTime().getTime().getHours()+1);
        //dayView.setLimitTime(6,13);
        return view;
    }
}
