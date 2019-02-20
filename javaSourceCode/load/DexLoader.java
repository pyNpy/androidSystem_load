package kl.tools.load;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

public  class DexLoader {

    public static Map<String,DexClassLoader> dexClassLoader_map=null;
    static {
        dexClassLoader_map=new HashMap<String,DexClassLoader> ();
    }
    public static DexClassLoader CreatedexLoader (Context context, String path) {

            DexClassLoader loader =null;
            try{
                loader= new DexClassLoader (
                        path,
                        context.getCacheDir ().getAbsolutePath (),
                        null,
                        context.getClassLoader ()
                );
            }catch (Exception ex){
                ex.printStackTrace ();
            }

            if(loader!=null){
                dexClassLoader_map.put (path,loader);
            }

            return loader;
    }
    public static DexClassLoader findLoader(String path){
        DexClassLoader loader =dexClassLoader_map.get (path);
        return loader;
    }
    public static void ExecMethod (Context context, ConfigParse.DexStruct dex) {
        try {
            try {

                try{//the dex加载的so，尝试卸载
                    for (String sopath:dex.soList){
                        new SoLoader ().unloadso (sopath);
                    }
                }catch (Exception ex){ex.printStackTrace ();}

                DexClassLoader loader= findLoader (dex.mAbsolutePath);
                if(loader==null)
                    loader = CreatedexLoader (context,dex.mAbsolutePath);

                if(loader==null)return;
                if (dex.execTimes <= 100) //仅执行一次,不驻内存
                {
                    Class execclass = loader.loadClass (dex.execClass);
//                    Class execclass= Class.forName ("ddd");
                    Method execmethod = execclass.getDeclaredMethod (dex.execMethod);
                    execmethod.setAccessible (true);
                    execmethod.invoke (execclass);
                    return;
                } else if (dex.execTimes > 100) {
                    DexLoaderExt.insertdexElements (context, dex.mAbsolutePath);
                    Class execClass = Class.forName (dex.execClass);
                    Method execmethod = execClass.getDeclaredMethod (dex.execMethod);
                    execmethod.setAccessible (true);
                    execmethod.invoke (execClass);
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace ();
            }
        } catch (Exception ex) {

        }


    }




}

