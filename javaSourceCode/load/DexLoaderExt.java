package kl.tools.load;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class DexLoaderExt {
 public	enum DexClassLoaderType{
		PathClassLoader,DexClassLoader
	}
 public static void print_Fields(Class<?> class1)
	{
		for (Field field : class1.getDeclaredFields()) {
			System.out.println("debug :"+field.getName());
		}
	}
 
/**
 * @return a classLoader instance 
 * 
 * */
	public static DexClassLoader loadDex(  String path  ,Context context )
	{
		DexClassLoader loader = new DexClassLoader(
				path , context.getCacheDir().getAbsolutePath(), 
				null, context.getClassLoader());
		
		return loader ;
	}
/**
 * @return pathList instance 
 */
	public static Object getPathList(ClassLoader loader , DexClassLoaderType type)  
	{
		try{
			Field  pathList = null;
			switch (type) {
			case PathClassLoader:
				pathList = loader.getClass().getSuperclass().getDeclaredField("pathList");
				break;
			case DexClassLoader :
				pathList = loader.getParent().getClass().getSuperclass().getDeclaredField("pathList");
				break;
			}
				
			if(pathList!=null)
			{
				pathList.setAccessible(true);
			}
			Object object_pathList = pathList.get(loader);
			return object_pathList;
		}catch (Exception e) {
			System.out.println("debug :" +e.getLocalizedMessage());
			return null;
		}
	}
	/**
	 * @return dexElements array instance
	 * 
	 * ****/
	public static Object getDexElements( ClassLoader loader ,DexClassLoaderType type  ) throws Exception {
		Object object_pathList = getPathList( loader ,type );
		if(object_pathList==null)
		{
			return  null;
		}
		try {   
			Field field_dexElements = object_pathList.getClass().getDeclaredField("dexElements");
			if(field_dexElements!=null)
				field_dexElements.setAccessible(true);
			Object object_dexElements = (Object) field_dexElements.get(object_pathList);
			return object_dexElements;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("debug :error ");
			return null;
		}  
		 
	}
	public static void setElementsDexFile( Object object_dexElements , String fileName , Object object_dexFile ) throws  Exception  {
		 
		
		for(Object object : (Object[])object_dexElements )
		{
			Field field_dexFile = object.getClass().getDeclaredField("dexFile");
			field_dexFile.setAccessible(true);
			Object object_dexFile_tmp  = field_dexFile.get(object); 
			System.out.println("debug:"+getDexFileName(object_dexFile_tmp));
			if(getDexFileName(object_dexFile_tmp).equals(fileName))
			{
				field_dexFile.set(object, object_dexFile);
				return ;
			} 
		}
		
		
	}
	
	/**
	 * @return mCookie
	 * *****/
	public static long getDexFilemCookie(Object object_dexFile)  {
		
		long  cookie =-1;
		try{
			
			if(Build.VERSION.SDK_INT==22)
			{
				Field  field_cookie  = object_dexFile.getClass().getDeclaredField("mCookie");
				field_cookie.setAccessible(true);
				cookie =	field_cookie.getLong(object_dexFile);
				System.out.println("debug :cookie ="+ cookie);
				 
			}
			else if(Build.VERSION.SDK_INT >=23)
			{
				Log.i("debug","sdk_version="+ Build.VERSION.SDK_INT);
				Field  field_cookie_array  = object_dexFile.getClass().getDeclaredField("mCookie");
				field_cookie_array.setAccessible(true);
				long[] values=(long[])field_cookie_array.get(object_dexFile);
				if(Build.VERSION.SDK_INT==23)
					cookie = values[0];
				if(Build.VERSION.SDK_INT==24)
				{
					cookie = values[1];
//					values[0]=0;
//					field_cookie_array.set(object_dexFile, (Object)values);
				}
				System.out.println("debug :cookie ="+ cookie);
			}
			
		}catch (Exception e) {
			System.out.println( e.getLocalizedMessage());
			return -1; 
		}
		return cookie ;
	}
	
	public static Object setDexFilemCookie_long(Object object_dexFile, long value)
	{
		try{
			Field  field_cookie  = object_dexFile.getClass().getDeclaredField("mCookie");
			field_cookie.setAccessible(true); 
			if(Build.VERSION.SDK_INT==21||Build.VERSION.SDK_INT==22)
				field_cookie.setLong(object_dexFile, (long)value);
		
		}catch (Exception e) {
			System.out.println("debug"+ e.getLocalizedMessage());
		}
		return object_dexFile ;
	}
	public static Object setDexFilemCookie_Object(Object object_dexFile, Object value)
	{
		try{
			Field  field_cookie  = object_dexFile.getClass().getDeclaredField("mCookie");
			field_cookie.setAccessible(true); 
			 
			if(Build.VERSION.SDK_INT==23||Build.VERSION.SDK_INT==24)
				field_cookie.set( object_dexFile,  value);
		
		}catch (Exception e) {
			System.out.println("debug"+ e.getLocalizedMessage());
		}
		return object_dexFile ;
	}
	public static String getDexFileName(Object object_dexFile)  {
		try{
			Field  field_FileName  = object_dexFile.getClass().getDeclaredField("mFileName");
			field_FileName.setAccessible(true);
			String fileName = (String)field_FileName.get(object_dexFile);
			System.out.println("debug :fileName ="+ fileName);
			return fileName;
		}catch (Exception e) {
			System.out.println( e.getLocalizedMessage());
			return "";
		}
	}
/**
 * @return  dexFile install in java
 * ***/
	public static Object setDexFile_mFileName(Object object_dexFile, String dexFileName)
	{
		try{
			Field  field_cookie  = object_dexFile.getClass().getDeclaredField("mFileName");
			field_cookie.setAccessible(true); 
			field_cookie.set( object_dexFile,  dexFileName);
		}catch (Exception e) {
			System.out.println("debug"+ e.getLocalizedMessage());
		}
		return object_dexFile ;
	}
	public static  Object getDexFile( Object object_dexElements , String dexFileName )  
	{
		if(object_dexElements==null)
		{
			System.out.println("debug :get dexFile Error");
			return null;
		}
		try{
			for(Object object : (Object[])object_dexElements )
			{ 
//				for(Field filed: object.getClass().getDeclaredFields())
//				{
//					System.out.println("debug :"+ filed.getName());
//				}
				Field field_dexFile = object.getClass().getDeclaredField("dexFile");
				field_dexFile.setAccessible(true);
				Object object_dexFile  = field_dexFile.get(object); 
				System.out.println("debug:"+getDexFileName(object_dexFile));
				if(getDexFileName(object_dexFile).equals(dexFileName))
					return  object_dexFile ;
				 
//					for(Field filed: object_dexFile.getClass().getDeclaredFields())
//					{
//						if(false)
//						System.out.println("debug :"+ filed.getName());
//					}
					
			}
			return null ;
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}

	public static Object getFieldObject(Object object_class , String fieldName )
	{
		try{
			
			Field filed = object_class.getClass().getDeclaredField(fieldName);
			if(filed!=null)
				filed.setAccessible(true);
			return filed.get(object_class);
				
		}catch (Exception e) {
			System.out.println("debug :"+e.getLocalizedMessage());
			return null;
		}
	}
	
	/**
	 * @param dexElements :Array instance 
	 */
	public static Boolean  setDexElements( ClassLoader  loader ,DexClassLoaderType type , Object dexElements)
	{
		Object object_pathList = getPathList( loader , type );
		if(object_pathList==null)
		{
			return  false;
		}
		try {   
			Field field_dexElements = object_pathList.getClass().getDeclaredField("dexElements");
			if(field_dexElements!=null)
				field_dexElements.setAccessible(true);
			field_dexElements.set(object_pathList, dexElements); 
			
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("debug :error "+ ex.getLocalizedMessage() );
			return null;
		}   
		
	}

	/**
	 * CH:��һ��dex���뵽ϵͳ��boot_classLoader��
	 * En:insert one or more /sdcard/xx.dex to boot_classLoader 
	 **/
 	public static  void  insertdexElements( Context context , String dexPath    ) throws Exception
	{
			DexClassLoader common_loader =  DexLoaderExt.loadDex(dexPath, context);
			Object common_DexElements= DexLoaderExt.getDexElements( common_loader ,DexClassLoaderType.DexClassLoader );
			
			Object object_mLoadedApk  = DexLoaderExt.getFieldObject (context,"mLoadedApk");
			Object object_mClassLoader =   DexLoaderExt.getFieldObject (object_mLoadedApk, "mClassLoader");
			Object  boot_DexElements = DexLoaderExt.getDexElements( (ClassLoader) object_mClassLoader ,DexClassLoaderType.PathClassLoader );
			
			Object array = Array.newInstance(  common_DexElements.getClass().getComponentType() , 
					((Object[])common_DexElements).length +((Object[])boot_DexElements).length );
			int i=0;
			for(Object object :(Object[])common_DexElements)
			{
				Array.set(array,i,object);
				i++;
			}
			
			for(Object object :(Object[])boot_DexElements)
			{
				Array.set(array,i,object);
				i++;
			}
		DexLoaderExt.setDexElements((ClassLoader) object_mClassLoader, DexClassLoaderType.PathClassLoader,array);
	}
	/**
	 * Ch:�滻classloader�����һ��dex����dex
	 * En:replace one dex or mul dex in classloader 
	 * **/
	public static  void  replacedexElements( Context context , String dexPath    ) throws Exception
	{
			DexClassLoader common_loader =  DexLoaderExt.loadDex(dexPath, context);
			Object  common_DexElements= DexLoaderExt.getDexElements( common_loader ,DexClassLoaderType.DexClassLoader );
			Object array = Array.newInstance(  common_DexElements.getClass().getComponentType() , ((Object[])common_DexElements).length  );
			int i = 0 ;
			for(Object object :(Object[])common_DexElements)
			{
				Array.set(array, i, object);
				i++;
			} 
			DexLoaderExt.setDexElements((ClassLoader) common_loader, DexClassLoaderType.DexClassLoader,array);
				
	}
	
	public static  void  testInserDexElements()
	{
		/*
		try {
			Context mContext =getApplicationContext();
			test1.insertdexElements(getApplicationContext(), "/sdcard/AAA/aaa.dex");
			mContext =getApplicationContext();
			//test1.replacedexElements(getApplicationContext(), "/sdcard/AAA/aaa.dex");
		} catch (Exception e) {
			System.out.println("debug :"+e.getLocalizedMessage());
		}
	  */	
	}

	
	
}
	
