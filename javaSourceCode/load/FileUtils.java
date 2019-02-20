package kl.tools.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static java.lang.Runtime.getRuntime;

public class FileUtils {

    public static void makeDir(String dir){
        File f1=new File (dir);

         f1.mkdir ();
    }
    public static String[] getAllFiles(String dir){
        File file=new File (dir);
        String[]  filePaths=null;
        if(file.exists ()&&file.isDirectory ())
        {
            filePaths=file.list ();
        }
        return filePaths;
    }
    public static String   getAbsolutePath(String dir,String filename) {
        if (filename.startsWith (dir))
            return filename;
        if(dir.endsWith (File.separator))
            return dir+filename;
        else
            return dir+File.separator+filename;

    }

    public static Boolean FileExists(String path){
        return new File (path).exists ();
    }
    public static long getFileModifiedTime(String path){
        File file =new File (path);
        if(!file.exists ())
            return 0;
        long lastchangeTime=file.lastModified ();
        return   lastchangeTime;
    }
    public static String getFileContextbyLineNum(String path,int Linenum){
        String line="";
        File file  = new File (path);
        if(!file.exists ())
        {
            System.out.println ("file not exists:"+path);
            return "";
        }
        try {
            BufferedReader reader= new BufferedReader (new FileReader (file ));
            int counter=0;
            do {
                line=reader.readLine ();
                counter++;
                if(counter==Linenum){
                    break;
                }
            }while(line!=null);
            reader.close ();
        }catch (Exception ex){
        }

        line =   line==null?"":line;

        return line;
    }

    public static void changeFileMode(String path ,int value ){
        Runtime runtime = getRuntime();
        String command = new String (String.format ("chmod %d %s",value,path)) ;
        try {
            Process process = runtime.exec(command);
            process.waitFor();
            int existValue = process.exitValue();
            if(existValue != 0){
                System.out.println ( "Change file permission failed." );
            }
        } catch (Exception e) {
            System.out.println ( "Change file permission failed." );
        }


    }


}
