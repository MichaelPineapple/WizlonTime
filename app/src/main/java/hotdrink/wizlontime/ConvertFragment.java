package hotdrink.wizlontime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.DecimalFormat;

/** Fragment for converting between wizlon time and standard time */
public class ConvertFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_convert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        wizlonInput = getView().findViewById(R.id.wizlonTime);
        stupidInput = getView().findViewById(R.id.stupidTime);

        wizlonInput.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (wizlonInput.hasFocus()) stupidInput.setText(handleStupid(wizlonInput.getText().toString()));
            }
        });

        stupidInput.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (stupidInput.hasFocus())
                {
                    String txt = stupidInput.getText().toString();
                    if (txt.length() == 1 && "3456789".contains(txt))  stupidInput.getText().insert(0, "0");
                    else if (txt.length() == 3 && areCharsDigits(txt)) stupidInput.getText().insert(2, ":");
                    else if (txt.length() == 6 && areCharsDigits(txt.substring(0, 2)) && areCharsDigits(txt.substring(3))) stupidInput.getText().insert(5, ":");
                    wizlonInput.setText(handleWizlon(stupidInput.getText().toString()));
                }
            }
        });
    }

    boolean areCharsDigits(String str)
    {
        boolean $return = true;
        for (int i = 0 ; i < str.length(); i++) if (!Character.isDigit(str.charAt(i))) $return = false;
        return $return;
    }

    EditText wizlonInput, stupidInput;

    enum TimeType
    {
        AM,
        PM,
        MI,
    }

    String handleWizlon(String stupid)
    {
        String $return = "--";
        try
        {
            if (stupid.length() > 0)
            {
                stupid = stupid.toLowerCase().replace(" ", "");
                TimeType timetype = TimeType.MI;
                if (stupid.contains("am")) timetype = TimeType.AM;
                else if (stupid.contains("pm")) timetype = TimeType.PM;

                int endIndex = stupid.length();
                if (timetype != TimeType.MI) endIndex = stupid.length()-2;

                String hStr = "[null]", mStr = "[null]", sStr = "[null]";

                int c1 = stupid.indexOf(':');
                if (c1 != -1)
                {
                    int c2 = stupid.indexOf(':', c1+1);
                    if (c2 != -1)
                    {
                        hStr = stupid.substring(0, c1);
                        mStr = stupid.substring(c1+1, c2);
                        sStr = stupid.substring(c2+1, endIndex);
                    }
                    else
                    {
                        hStr = stupid.substring(0, c1);
                        mStr = stupid.substring(c1+1, endIndex);
                        sStr = "0";
                    }
                }
                else
                {
                    hStr = stupid.substring(0, endIndex);
                    mStr = "0";
                    sStr = "0";
                }

                int h = Integer.parseInt(hStr);
                int m = Integer.parseInt(mStr);
                int s = Integer.parseInt(sStr);

                if (timetype == TimeType.PM)
                {
                    if (h != 12) h += 12;
                }
                else if (timetype == TimeType.AM)
                {
                    if (h == 12) h = 0;
                }

                if (h >= 0 && h <= 23 && m >= 0 && m <= 59 && s >= 0 && s <= 59)
                {
                    double wt = WizlonTools.GET_WIZLON_TIME(h, m, s, 0);
                    String wts = wt+"";
                    if (wts.length() < 4) wts = new DecimalFormat("0.00").format(wt);
                    $return = wts;
                }
            }
            else $return = "";
        }
        catch (Exception ex) { System.out.println("WT ERROR: "+ex.getMessage()); }
        return $return;
    }


    String handleStupid(String wizlon)
    {
        String $return = "--";
        try
        {
            if (wizlon.length() > 0)
            {
                double val = Double.parseDouble((wizlon));
                if (val >= 0 && val < 9.9999999) $return = convertToStupid(val);
            }
            else $return = "";
        }
        catch (Exception ex) { System.out.println("WT ERROR: "+ex.getMessage()); }
        return $return;
    }

    String convertToStupid(double w)
    {
        double a = 8640.0*w;
        int h = (int)(a/3600.0);
        int m = (int)((a-(3600.0*h))/60.0);
        int s = (int)((a-(3600.0*h))-(60.0*m));
        return convertTo12hour(h, m, s);
    }

    String convertTo12hour(int h, int m, int s)
    {
        String tmp = "AM";
        if (h > 11) tmp = "PM";
        if (h > 12) h -= 12;
        String h_str = pad(h);
        if (h == 0) h_str = "12";
        String sec = ":"+pad(s);
        if (s == 0) sec = "";
        return h_str+":"+pad(m)+sec+" "+tmp;
    }

    String pad(int val) { return String.format("%02d",val); }
}
