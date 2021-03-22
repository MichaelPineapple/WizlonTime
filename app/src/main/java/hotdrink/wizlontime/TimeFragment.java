package hotdrink.wizlontime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimeFragment extends Fragment
{
    Thread t;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        update();

        t = new Thread()
        {
            @Override public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        Thread.sleep(100);
                        getActivity().runOnUiThread(new Runnable() { @Override public void run() { update(); }});
                    }
                }
                catch (InterruptedException e)
                {
                }
            }
        };

        t.start();

    }


    void update()
    {
        try
        {
            String wt = WizlonTools.GET_WIZLON_TIME_STR();
            ((TextView) getView().findViewById(R.id.E)).setText(wt.substring(0, 4));
            ((TextView) getView().findViewById(R.id.e)).setText(wt.substring(4, 6));
        }
        catch (Exception ex)
        {

        }
    }



    @Override
    public void onPause()
    {
        super.onPause();
        t.interrupt();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        t.interrupt();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        t.interrupt();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        t.interrupt();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        t.interrupt();
    }



}
