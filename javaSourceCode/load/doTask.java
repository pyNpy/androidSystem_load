package kl.tools.load;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class doTask {
    public static List<String> gSoList=new ArrayList<String> ();
    public static List<String> gDexList=new ArrayList<String> ();

    public static void runTask(Context context){
        printconfigInfo();
        ExecDexTask (context,ConfigParse.ParseDexConfig () );
        LoadSoTask (context,ConfigParse.ParseSoConfig ());

    }
    public static void printconfigInfo(){
        List<ConfigParse.DexStruct> dexlist=ConfigParse.ParseDexConfig ();
        List<ConfigParse.SoStruct>soList=ConfigParse.ParseSoConfig ();
        for (ConfigParse.DexStruct d :dexlist)
        {
            System.out.println ( "dex:"+d.toString ());
            if(FileUtils.FileExists (d.mAbsolutePath))
                System.out.println ( "dex exists:"+d.mAbsolutePath);
        }
        for (ConfigParse.SoStruct s :soList ) {
            System.out.println ( "so:"+s.toString ());
            if(FileUtils.FileExists (s.mAbsolutePath))
                System.out.println ( "so exists:"+s.mAbsolutePath);
        }
    }
    public static void printconfigUsage(){
        System.out.println (Build.soConfigBaseUnit);
        System.out.println (Build.dexConfigBaseUnit);

    }
    public static void LoadSoTask(Context context , List<ConfigParse.SoStruct> list){
       if(list==null) return ;
       if(list.size ()==0)
       {
           System.out.println ("no so to load,check /sdcard/config/config-so.txt");
           System.out.println (Build.soConfigBaseUnit);
           return;
       }
        for ( ConfigParse.SoStruct s:list){
            try {
                if(!context.getPackageName ().equals (s.mTargetPackage))
                    continue;
                if(!FileUtils.FileExists (s.mAbsolutePath))
                {
                    System.out.println ("no so to load,check!!!");
                    continue;
                }
                new SoLoader ().loadso (s.mAbsolutePath);
            }catch (Exception e){
                e.printStackTrace ();
            }
        }
    }

    public static void ExecDexTask(Context context , List<ConfigParse.DexStruct> list){
        if(list==null) return;
        if(list.size ()==0)
        {
            System.out.println ("no dex to load,check /sdcard/config/config-dex.txt");
            System.out.println (Build.dexConfigBaseUnit);
            return;
        }
        for (  ConfigParse.DexStruct d:list){
            try {
                if(!context.getPackageName ().equals (d.mTargetPackage))
                    continue;
                 if(!new File (d.mAbsolutePath).exists ())
                 {
                     System.out.println ("no dex to load,check!!!");
                     continue;
                 }

                 DexLoader.ExecMethod (context,d);
                 Thread.sleep (10);

            }catch (Exception e){}

        }
    }






}
