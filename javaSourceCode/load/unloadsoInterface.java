package kl.tools.load;

import java.io.File;

public interface unloadsoInterface {
    public final String unloadsoPath=ConfigParse.LibDir+File.separator+"libunload.so";
    public void   reloadso(String path);

    public void   unloadso(String path);

}
