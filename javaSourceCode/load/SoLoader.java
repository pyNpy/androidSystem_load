package kl.tools.load;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoLoader implements unloadsoInterface  {
    public static List<String> soloadedList=new ArrayList<String>();
    static {
        if(FileUtils.FileExists (unloadsoPath))
            System.load (unloadsoPath);
    }

    @Override
    public void reloadso (String path) {
        try {
            unloadso (path);
            System.load (path);

        }catch (Exception e){}
    }
    @Override
    public  void unloadso (String path) {
        if(true)
            return;
        if(!FileUtils.FileExists (unloadsoPath))
            return;
        try{
            dlclose (path);
        }catch (Exception ex){
            ex.printStackTrace ();
        }
    }
    public native void dlclose(String path );

    public void loadso(String path){
        if (!FileUtils.FileExists (path))
            return;
        if(soloadedList.contains (path))
            reloadso (path);
        else
        {
            System.load (path);
            soloadedList.add (path);
        }
        System.out.println ("load so:"+path );

    }

}
