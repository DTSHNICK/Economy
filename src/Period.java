import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by DTSHNICK on 23.09.2015.
 */
public class Period {

    private int end_month;
    private int end_day;
    private int end_year;

    private int start_month;
    private int start_day;

    public Period(int start_day, int start_month, int start_year, int end_day, int end_month, int end_year)
    {
        this.start_day=start_day;
        this.start_month=start_month;
        this.start_year=start_year;
        this.end_day=end_day;
        this.end_month=end_month;
        this.end_year=end_year;
    }

    public Period(String text_period) {
        String[] r = text_period.split(Pattern.quote("."));
        if (r.length>3)
        {

        }
        else
        {
            start_day=Integer.parseInt(r[0]);
            start_month=Integer.parseInt(r[1]);
            start_year=Integer.parseInt(r[2]);
            end_day=Integer.parseInt(r[0]);
            end_month=Integer.parseInt(r[1]);
            end_year=Integer.parseInt(r[2]);
        }
    }

    public Period(String s, String f) {
        String[] start = s.split("\\.");
        start_day= Integer.parseInt(start[0]);
        start_month= Integer.parseInt(start[1]);
        start_year= Integer.parseInt(start[2]);

        String[] end = f.split("\\.");
        end_day= Integer.parseInt(end[0]);
        end_month= Integer.parseInt(end[1]);
        end_year= Integer.parseInt(end[2]);
    }


    @Override
    public String toString() {
        String res = start_day + "." + start_month + "." + start_year + " -- " + end_day + "." + end_month + "." + end_year;
        return res;
    }


    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public int getStart_day() {
        return start_day;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
    }

    public int getStart_month() {
        return start_month;
    }

    public void setStart_month(int start_month) {
        this.start_month = start_month;
    }

    private int start_year;



    public int getEnd_month() {
        return end_month;
    }

    public void setEnd_month(int end_month) {
        this.end_month = end_month;
    }

    public int getEnd_day() {
        return end_day;
    }

    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }

    public int getEnd_year() {
        return end_year;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public void addDay(int a) {
        if (end_day+a>30)
        {


                for (; a >= 0 && end_day <= 30; a--, end_day++) {

                }
                end_day = 1;
                addMonth();
                a--;
                for (; a >= 0; end_day++, a--) {

                }
        }
        else
        {
            end_day+=a;
        }

    }

    private void addMonth() {

        if (end_month==12)
        {
            end_month=1;
            end_year++;
        }
        else
        {
            end_month++;
        }
    }

    public String getEnd() {
        return end_day+"."+end_month+"."+end_year;
    }

    public int getDays() {

        int start = start_day+30*start_month+365*start_year;
        int end=end_day+30*end_month+365*end_year;

        return end-start;
    }

    public String getFinish() {
        String res=end_day+"."+end_month+"."+end_year;
        return res;
    }
}
