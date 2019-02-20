package kl.tools.load;

import java.io.File;
import java.sql.Statement;

public class Build {
    public static final Boolean ControlOff =false;
    public static final Boolean ControlOn =true;

    public static final String MkDir1 = ConfigParse.ConfigDir ;
    public static final String MkDir2 = ConfigParse.LibDir ;
    public static final String MkDir3 = ConfigParse.ConfigDir+File.separator+"dump";

    public static final String TAG ="AndroidTaskThread";
    public static final String ThreadName="AndroidTaskThread";
    public static final String $So=".so";
    public static final String $Dex=".dex";
    public static final String $zip=".zip";
    public static final String $Jar=".jar";
    public static final String soConfigBaseUnit="config-so.txt base unit:\n"+
            "Tpackage:example.demo.ndkdemo\n" +
            "so:libtestAsmCode.so\n" +
            "so-level:0\n" +
            "so-delay:0";
    public static final String dexConfigBaseUnit="config-dex.txt base unit:\n"+
            "Tpackage:example.demo.ndkdemo\n" +
            "dex:ddd.dex\n" +
            "dex-level:0\n" +
            "dex-delay:0\n" +
            "dex-run-class:ddd\n" +
            "dex-run-static-method:main\n" +
            "dex-run-times:1";

}
