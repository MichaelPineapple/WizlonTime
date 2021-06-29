package hotdrink.wizlontime;

import android.content.Context;
import android.graphics.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/** Collection of global static classes which allow for easy manipulation of settings data */
public class Settings
{
    /** Save a file to internal storage **/
    private static boolean SAVE_DATA(Context _context, String _filename, String _data)
    {
        boolean output = true;
        try
        {
            File f = new File(_context.getFilesDir(), _filename);
            FileWriter writer = new FileWriter(f);
            writer.append(_data);
            writer.flush();
            writer.close();
        }
        catch (Exception e) { output = false; }
        return output;
    }

    /** Reads a file from internal storage **/
    private static String LOAD_DATA(Context _context, String _filename)
    {
        String output = "";
        try
        {
            FileInputStream fis = _context.openFileInput(_filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) sb.append(line);
            output = sb.toString();
        }
        catch (Exception ex) { output = null; }
        return output;
    }
    public static class WidgetColor
    {
        private static final String FILE = "widgetdata.mcl";
        public static int GET(Context _context)
        {
            int output = Color.WHITE;
            String savedColorData = LOAD_DATA(_context, FILE);
            if (savedColorData != null)
            {
                try
                {
                    output = Integer.parseInt(savedColorData);
                }
                catch (Exception ex)
                {
                    WizlonTools.TOAST(_context, "Error: Parse Failed!");
                }
            }
            return output;
        }
        public static boolean SET(Context _context, int _data)
        {
            return SAVE_DATA(_context, FILE, _data+"");
        }
    }

        /*
    public static class UseGalactic
    {
        private static final String FILE = "usegalactic.mcl";
        public static boolean GET(Context _context)
        {
            boolean output = false;
            String data = LOAD_DATA(_context, FILE);
            if (data != null) output = data.equals("T");
            return output;
        }
        public static boolean SET(Context _context, boolean _data)
        {
            String dataStr = "F";
            if (_data) dataStr = "T";
            return SAVE_DATA(_context, FILE, dataStr);
        }
    }

     */
}
