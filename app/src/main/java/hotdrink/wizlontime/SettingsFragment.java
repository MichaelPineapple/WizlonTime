package hotdrink.wizlontime;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/** Fragment for manipulating the app settings **/
public class SettingsFragment extends Fragment
{
    SeekBar slider_red;
    SeekBar slider_green;
    SeekBar slider_blue;
    SeekBar slider_alpha;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slider_red = getView().findViewById(R.id.slider_red);
        slider_green = getView().findViewById(R.id.slider_green);
        slider_blue = getView().findViewById(R.id.slider_blue);
        slider_alpha = getView().findViewById(R.id.slider_alpha);

        int savedColor = Settings.WidgetColor.GET(this.getContext());
        slider_red.setProgress(Color.red(savedColor));
        slider_green.setProgress(Color.green(savedColor));
        slider_blue.setProgress(Color.blue(savedColor));
        slider_alpha.setProgress(Color.alpha(savedColor));

        createCallbacks();
        updatePreviewBoxColor();

        Button butt_apply = getView().findViewById(R.id.butt_apply);
        butt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onApplyClick();
            }
        });
    }

    public void onApplyClick()
    {
        boolean result0 = Settings.WidgetColor.SET(this.getContext(), getCurrentColor());

        if (result0)
        {
            Toast.makeText(this.getContext(), "Settings Saved!", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this.getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();

        ClockWidget.REFRESH_WIDGET(this.getContext());
    }

    int getCurrentColor()
    {
        int alpha = slider_alpha.getProgress();
        int red = slider_red.getProgress();
        int green = slider_green.getProgress();
        int blue = slider_blue.getProgress();

        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        if (red < 0) red = 0;
        if (red > 255) red = 255;
        if (green < 0) green = 0;
        if (green > 255) green = 255;
        if (blue < 0) blue = 0;
        if (blue > 255) blue = 255;

        return Color.argb(alpha, red, green, blue);
    }

    void updatePreviewBoxColor()
    {
        View previewBox = getView().findViewById(R.id.colorpreviewbox);
        TextView previewText = getView().findViewById(R.id.colorpreviewtext);
        int currentColor = getCurrentColor();
        previewBox.setBackgroundColor(currentColor);
        previewText.setTextColor(currentColor);
    }

    void createCallbacks()
    {
        slider_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                updatePreviewBoxColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        slider_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                updatePreviewBoxColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        slider_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                updatePreviewBoxColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        slider_alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                updatePreviewBoxColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}