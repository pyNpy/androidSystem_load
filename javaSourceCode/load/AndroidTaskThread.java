package kl.tools.load;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
/**
 * 在 ActivityThread中 创建该类
 * **/
public class AndroidTaskThread extends Thread {
    private static Context mContext;
    private static final String ControlFlag_TxT_Path =ConfigParse.ConfigDir+File.separator+"ControlFlag.txt";
    private static long ControlFlag_TxT_LastModifiedTime=0;
    private static Boolean ControlFlag =false;
    static private Handler AndroidTaskThreadHandler=new Handler (  ){
        @Override
        public void dispatchMessage (Message msg) {
            super.dispatchMessage (msg);
        }
    };

    static {
        FileUtils.makeDir (Build.MkDir1);
        FileUtils.makeDir (Build.MkDir2);
        FileUtils.makeDir (Build.MkDir3);
        if(!FileUtils.FileExists (ControlFlag_TxT_Path))
            System.out.println ("[AndroidTaskThread]    should create Control File:"+ControlFlag_TxT_Path);

    }

    public AndroidTaskThread (Context context){
        mContext=context;
    }
    private static Runnable mTaskRunable=new Runnable () {
        @Override
        public void run () {
            CheckFileModifed ();
            if(ControlFlag ==true){
                System.out.println("[AndroidTaskThread]  do task start...");

                doTask.runTask( mContext );

                System.out.println("[AndroidTaskThread]  do task over...");
                ControlFlag=Build.ControlOff;
            }
            //loop:
            AndroidTaskThreadHandler.postDelayed (mTaskRunable,3000 );

        }
    };

    private static void CheckFileModifed(){
        if(!FileUtils.FileExists (ControlFlag_TxT_Path))
        {
          //not exists
            ControlFlag = Build.ControlOff;
            return;
        }
        long  time = FileUtils.getFileModifiedTime (ControlFlag_TxT_Path);
        String context_line=FileUtils.getFileContextbyLineNum (ControlFlag_TxT_Path,1);
        if(TextUtils.isEmpty (context_line)){
           return;
        }
        //exists && empty
        if(  AndroidTaskThread.ControlFlag_TxT_LastModifiedTime==0)
        {
            AndroidTaskThread.ControlFlag_TxT_LastModifiedTime = time ;
            return ;
        }
        if(ControlFlag_TxT_LastModifiedTime!=time )//time1 = time2 ,not modifed file
        {
            if(context_line.toLowerCase ().equals ("on")){
                ControlFlag_TxT_LastModifiedTime=time;
                ControlFlag=Build.ControlOn;
                return ;
            }
        }

    }
    @Override
    public void run () {
        super.run ();
        AndroidTaskThreadHandler.postDelayed (mTaskRunable,3000 );
    }
}
