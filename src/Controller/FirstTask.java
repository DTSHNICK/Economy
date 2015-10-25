package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DTSHNICK on 25.10.2015.
 */
public class FirstTask {

    public static String[][] calculateDays(String[][] data) {

        for (int i=0;i<data.length;i++)
        {
            String performers_list[]=data[i][2].split("\\s*,\\s*");
            Float Q=Float.parseFloat(data[i][1]);
            int R=performers_list.length;
            data[i][3]= String.valueOf(Q/R);
        }
        return data;

    } // посчитать дни

    public static ArrayList<String> findJob(String[][] data) {

        ArrayList<String> Actors = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {

            ArrayList<String> Performers_list = new ArrayList<>(Arrays.asList(data[i][2].toString().split("\\s*,\\s*")));
            Actors.addAll(Performers_list);
        }
        Set<String> set = new HashSet<>(Actors);

        ArrayList<String> jobs = new ArrayList<>();
        jobs.addAll(set);
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println(jobs.get(i));
        }
        return jobs;
    }
}
