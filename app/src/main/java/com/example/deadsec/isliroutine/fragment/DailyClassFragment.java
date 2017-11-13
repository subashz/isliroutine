package com.example.deadsec.isliroutine.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
    public static final String TAG="DailyClassFragment";
    public static final String ARG_DAY = "daily_class_day";
    public static final String GROUP_INDEX = "group_index";
    private int dayId;
    private String day;
    CalendarDayView dayView;
    ProgressDialog progressDoalog;
    ConstraintLayout mConstraintLayout;

    public static final DailyClassFragment newInstance(String day) {
        final DailyClassFragment instance = new DailyClassFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = this.getArguments();
        if (args != null) {
            day = args.getString(ARG_DAY);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_routine_daily, container, false);
        dayView = (CalendarDayView) view.findViewById(R.id.calendar);
        dayView.setDecorator(new CustomDecoration(getActivity()));
        mConstraintLayout=view.findViewById(R.id.progressContainer);


        ((CustomDecoration) (dayView.getDecoration())).setOnEventClickListener(
                new EventView.OnEventClickListener() {
                    @Override
                    public void onEventClick(EventView view, IEvent data) {
                        Log.e("TAG", "onEventClick:" + data.getName());
                        Toast.makeText(getActivity(), "on event course: " + data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEventViewClick(View view, EventView eventView, IEvent data) {
                        Log.e("TAG", "onEventViewClick:" + data.getName());
                        if (data instanceof ClassModel) {
                            // change event (ex: set event color)
                            //dayView.setEvents(ClassDataLab.get(getActivity()).getEvents(day));
                        }
                        Toast.makeText(getActivity(), "on event view clicked " + data.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

        new GetTimeTableData().execute(day);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class GetTimeTableData extends AsyncTask<String,Integer,List<IEvent>> {

        @Override
        protected void onPreExecute() {
        super.onPreExecute();

        }

        @Override
        protected List<IEvent> doInBackground(String... strings) {
            return ClassDataLab.get(getActivity()).getEvents(strings[0]);
        }

        @Override
        protected void onPostExecute(List<IEvent> iEvents) {
            super.onPostExecute(iEvents);
            dayView.setEvents(iEvents);
             dayView.setLimitTime(iEvents.get(0).getStartTime().getTime().getHours()-2, iEvents.get(iEvents.size()-1).getEndTime().getTime().getHours()+2);
             mConstraintLayout.setVisibility(View.GONE);
        }
    }
}
