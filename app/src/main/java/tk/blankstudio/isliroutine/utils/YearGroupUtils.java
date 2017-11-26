package tk.blankstudio.isliroutine.utils;

import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deadsec on 11/26/17.
 */

public class YearGroupUtils {

    public static void saveGroupId(Context context, int id) {
        List<Integer> yearGroupIds = getYearGroupIds(context);

        if (yearGroupIds.contains(id)) {
            return;
        }

        yearGroupIds.add(id);

        saveIdsInPreferences(context,yearGroupIds);
    }

    public static void removeYearGroupId(Context context,int id) {
        List<Integer> yearGroupIds = getYearGroupIds(context);

        for (int i = 0; i < yearGroupIds.size(); i++) {
            if (yearGroupIds.get(i) == id)
                yearGroupIds.remove(i);
        }

        saveIdsInPreferences(context,yearGroupIds);
    }

    public static List<Integer> getYearGroupIds(Context context){
        List<Integer> ids = new ArrayList<>();
        try {
            JSONArray jsonArray2 = new JSONArray(PreferenceUtils.get(context).getDownloadedGroupYear());

            for (int i = 0; i < jsonArray2.length(); i++) {
                ids.add(jsonArray2.getInt(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }


    public static void saveIdsInPreferences(Context context,List<Integer> listIds) {
        JSONArray jsonArray = new JSONArray();
        for (Integer yearGroupId : listIds) {
            jsonArray.put(yearGroupId);
        }
        PreferenceUtils.get(context).setDownloadedGroupYear(jsonArray.toString());
    }

}
