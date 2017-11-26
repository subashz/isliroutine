package tk.blankstudio.isliroutine.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tk.blankstudio.isliroutine.loader.TimeTableLoader;
import tk.blankstudio.isliroutine.utils.CustomDecoration;
import tk.blankstudio.isliroutine.model.Day;
import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.model.ClassModel;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.List;

public class DailyClassFragment extends Fragment {
    public static final String TAG = "DailyClassFragment";
    public static final String ARG_DAY = "daily_class_day";
    public static final String ARG_DAY_ID = "daily_class_day_id";
    public static final String ARG_GROUP_INDEX = "group_index";
    private int dayId;
    private int groupId;
    private String day;
    CalendarDayView dayView;
    ProgressDialog progressDoalog;
    ConstraintLayout mConstraintLayout;
    ProgressBar downloadingProgressBar;
    TextView noClassText;


    public static final DailyClassFragment newInstance(String day,int dayId,int groupId) {
        final DailyClassFragment instance = new DailyClassFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        args.putInt(ARG_DAY_ID, dayId);
        args.putInt(ARG_GROUP_INDEX, groupId);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = this.getArguments();
        if (args != null) {
            day = args.getString(ARG_DAY);
            dayId = args.getInt(ARG_DAY_ID);
            groupId=args.getInt(ARG_GROUP_INDEX);
        }
        Log.d(TAG, "onCreate: group Id is: "+groupId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_routine_daily, container, false);
        dayView = (CalendarDayView) view.findViewById(R.id.calendar);
        dayView.setDecorator(new CustomDecoration(getActivity()));
        mConstraintLayout = view.findViewById(R.id.progressContainer);
        downloadingProgressBar=view.findViewById(R.id.downloading_progress_bar);
        noClassText=view.findViewById(R.id.no_class_text);


        ((CustomDecoration) (dayView.getDecoration())).setOnEventClickListener(
                new EventView.OnEventClickListener() {
                    @Override
                    public void onEventClick(EventView view, IEvent data) {
                        Log.e("TAG", "onEventClick:" + data.getName());
                        Toast.makeText(getActivity(), "[e]You clicked: " + data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEventViewClick(View view, EventView eventView, IEvent data) {
                        Log.e("TAG", "onEventViewClick:" + data.getName());
                        if (data instanceof ClassModel) {
                            // change event (ex: set event color)
                            //dayView.setEvents(ClassDataLab.get(getActivity()).getEvents(day));
                        }
                        Toast.makeText(getActivity(), "[ev]You clicked " + data.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

        loadData();
        Log.d(TAG, "onCreateView: loading data of: "+groupId+" of day "+day);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void loadData() {
        Log.d(TAG, "loadData: called with group Id: "+groupId);
        LoaderManager.LoaderCallbacks<List<IEvent>> mLoaderCallbacks=new LoaderManager.LoaderCallbacks<List<IEvent>>() {
            @Override
            public Loader<List<IEvent>> onCreateLoader(int id, Bundle args) {
                Log.d(TAG, "onCreateLoader: received args: "+id+" day is: "+day+" groupId : "+groupId);
                return new TimeTableLoader(getActivity(),day,groupId);
            }

            @Override
            public void onLoadFinished(Loader<List<IEvent>> loader, List<IEvent> data) {
                Log.d(TAG, "onLoadFinished: loaded data of:"+groupId);
                dayView.setEvents(data);
                dayView.refresh();
                if(!data.isEmpty()) {
                    dayView.setLimitTime(data.get(0).getStartTime().getTime().getHours() - 2, data.get(data.size() - 1).getEndTime().getTime().getHours() + 2);
                    mConstraintLayout.setVisibility(View.INVISIBLE);
                }else {
                    downloadingProgressBar.setVisibility(View.INVISIBLE);
                    noClassText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<IEvent>> loader) {

            }
        };
        getLoaderManager().initLoader(dayId,null,mLoaderCallbacks);

    }

}
