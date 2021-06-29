package hotdrink.wizlontime;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.Calendar;

/** Collection of global tools for use throughout the entire app */
public class WizlonTools
{
    /** Calculate wizlon time from the provided hours, minutes, seconds, and milliseconds **/
    public static double GET_WIZLON_TIME(int h, int m, int s, int ms)
    {
        return ((h*3600000.0)+(m*60000.0)+(s*1000.0)+ms)/8640000.0;
    }

    /** Returns the user's current wizlon time (as a double) **/
    public static double GET_WIZLON_TIME()
    {
        Calendar c = Calendar.getInstance();
        return GET_WIZLON_TIME(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND));
    }

    /** Return the user's current wizlon time as a string */
    public static String GET_WIZLON_TIME_STR(int h, int m, int s, int ms, String format)
    {
        return new DecimalFormat(format).format(GET_WIZLON_TIME(h, m, s, ms));
    }
    public static String GET_WIZLON_TIME_STR(int h, int m, int s, int ms)
    {
        return new DecimalFormat("0.0000").format(GET_WIZLON_TIME(h, m, s, ms));
    }
    public static String GET_WIZLON_TIME_STR()
    {
        return new DecimalFormat("0.0000").format(GET_WIZLON_TIME());
    }
    public static String GET_WIZLON_TIME_STR(String _format)
    {
        return new DecimalFormat(_format).format(GET_WIZLON_TIME());
    }

    /** Display a toast and also print to log **/
    public static void TOAST(Context _context, String _str)
    {
        Toast.makeText(_context, _str, Toast.LENGTH_SHORT).show();
        LOG(_str);
    }

    /** print to log with the '!MCL>' tag **/
    public static void LOG(String _str)
    {
        Log.d("MCL", "!MCL> "+_str);
    }
}
