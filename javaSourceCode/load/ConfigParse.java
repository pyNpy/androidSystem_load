package kl.tools.load;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ConfigParse {
static public class SoStruct {
    public String mTargetPackage;
    public String mFileName;
    public String mAbsolutePath;
    public int mDelay;/*秒*/
    public int mLevel;

    public SoStruct () {
        this.mDelay = 0;
    }

    @Override
    public String toString () {
        return "SoStruct{" +
                "mTargetPackage='" + mTargetPackage + '\'' +
                ", mFileName='" + mFileName + '\'' +
                ", mAbsolutePath='" + mAbsolutePath + '\'' +
                ", mDelay=" + mDelay +
                ", mLevel=" + mLevel +
                '}';
    }
}
static public class  DexStruct {
    public String mTargetPackage;
    public String mFileName;
    public String mAbsolutePath;
    public int mDelay;/*秒*/
    public int mLevel;
    public String execClass;
    public String execMethod; //public static void main()
    public int execTimes;
    public List<String> soList=null;
    public DexStruct () {
        mDelay=0;
        execTimes=1;
        soList=new ArrayList<String> ();
    }

    @Override
    public String toString () {
        return "DexStruct{" +
                "mTargetPackage='" + mTargetPackage + '\'' +
                ", mFileName='" + mFileName + '\'' +
                ", mAbsolutePath='" + mAbsolutePath + '\'' +
                ", mDelay=" + mDelay +
                ", mLevel=" + mLevel +
                ", execClass='" + execClass + '\'' +
                ", execMethod='" + execMethod + '\'' +
                ", execTimes=" + execTimes +
                ", soList=" + soList.toString () +
                '}';
    }
}

    public static final String mStoragePath= Environment.getExternalStorageDirectory ().getAbsolutePath () ;
    public static final String ConfigDir=mStoragePath+File.separator+"config";
    public static final String LibDir=ConfigDir+File.separator+"lib";
    public static final String soConfigTxT ="config-so.txt";
    public static final String dexConfigTxT="config-dex.txt";
    public static final String TGA="AndroidTaskThread";

    public static List<SoStruct>  ParseSoConfig(){
        List<SoStruct> list =new ArrayList<SoStruct> ();
        if(!FileUtils.FileExists (ConfigDir+File.separator+ soConfigTxT))
            return  list;

        File configFile=new File (ConfigDir+File.separator+ soConfigTxT);

        try{
            BufferedReader reader =new BufferedReader (new FileReader (configFile));
            String line=null;
            do {
                line=reader.readLine ();
                if(line!=null&&line.startsWith ("Tpackage"))
                {
                    SoStruct so= new SoStruct ();
                    so.mTargetPackage=line.split (":")[1];

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("so"))
                        so.mFileName=line.split (":")[1];

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("so-level"))
                        so.mLevel=Integer.valueOf (line.split (":")[1]);

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("so-delay"))
                        so.mDelay=Integer.valueOf (line.split (":")[1]);

                    so.mAbsolutePath= LibDir+File.separator+ so.mFileName;

                    if(so.mFileName==null||so.mTargetPackage==null||
                            so.mAbsolutePath==null)
                        continue;
                    else
                        list.add (so);
                }
            }while(line!=null);

            reader.close ();
        }catch (Exception ex){
            System.out.println ( "[!!!!!]parse so config error");
            ex.printStackTrace ();
        }
        return list;
    }

    public static List<DexStruct>  ParseDexConfig(){
        List<DexStruct> list =new ArrayList<DexStruct> ();
       if(!FileUtils.FileExists (ConfigDir+File.separator+ dexConfigTxT))
            return  list;

        File configFile=new File (ConfigDir+File.separator+ dexConfigTxT);
        BufferedReader reader=null;
        try{
            reader=new BufferedReader (new FileReader (configFile));
            String line=null;
            do {
                line=reader.readLine ();
                if(line!=null&&line.startsWith ("Tpackage"))
                {
                    DexStruct dex= new DexStruct ();
                    dex.mTargetPackage=line.split (":")[1];

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex"))
                        dex.mFileName=line.split (":")[1];

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex-level"))
                        dex.mLevel=Integer.valueOf (line.split (":")[1]);

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex-delay"))
                        dex.mDelay=Integer.valueOf (line.split (":")[1]);

                    line=reader.readLine ();
                    if(line.startsWith ("dex-run-so")){
                        String sopath =line.split (":")[1];
                        if(!sopath.toLowerCase ().equals ("null")&&!dex.soList.contains (LibDir+File.separator+sopath))
                            dex.soList.add (LibDir+File.separator+sopath);
                    }

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex-run-class"))
                        dex.execClass=line.split (":")[1];

                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex-run-static-method"))
                        dex.execMethod=line.split (":")[1];
                    line=reader.readLine ();
                    if(line!=null&&line.startsWith ("dex-run-times"))
                        dex.execTimes=Integer.valueOf (line.split (":")[1]);

                    dex.mAbsolutePath= LibDir+File.separator+ dex.mFileName;

                    if(dex.mFileName==null||dex.mTargetPackage==null||
                            dex.mAbsolutePath==null)
                        continue;
                    else
                        list.add (dex);
                }
            }while(line!=null);
            if(reader!=null)
                reader.close ();
        }catch (Exception ex){
            System.out.println ( "[!!!!!]parse dex config error");
        }finally {

        }

        return list;
    }




}
