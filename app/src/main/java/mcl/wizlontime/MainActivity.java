package mcl.wizlontime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{

    public static double GET_WIZLON_TIME()
    {
        Calendar c = Calendar.getInstance();
        return ((c.get(Calendar.HOUR_OF_DAY)*3600000.0)+(c.get(Calendar.MINUTE)*60000.0)+(c.get(Calendar.SECOND)*1000.0)+c.get(Calendar.MILLISECOND))/8640000.0;
    }

    public static String GET_WIZLON_TIME_STR()
    {
        return new DecimalFormat("0.0000").format(GET_WIZLON_TIME());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();

        Thread t = new Thread()
        {
            @Override public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() { @Override public void run() { update(); }});
                    }
                }
                catch (InterruptedException e)
                {
                    ((TextView)findViewById(R.id.E)).setText("-.--");
                    ((TextView)findViewById(R.id.e)).setText("--");
                }
            }
        };

        t.start();
    }


    void update()
    {
        String wt = GET_WIZLON_TIME_STR();
        ((TextView)findViewById(R.id.E)).setText(wt.substring(0,4));
        ((TextView)findViewById(R.id.e)).setText(wt.substring(4,6));
    }

}
