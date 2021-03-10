package mcl.wizlontime;

import java.text.DecimalFormat;
import java.util.Calendar;

public class WizlonTools
{

    public static double GET_WIZLON_TIME(int h, int m, int s, int ms)
    {
        return ((h*3600000.0)+(m*60000.0)+(s*1000.0)+ms)/8640000.0;
    }
    public static double GET_WIZLON_TIME()
    {
        Calendar c = Calendar.getInstance();
        return GET_WIZLON_TIME(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND));
    }

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


}
