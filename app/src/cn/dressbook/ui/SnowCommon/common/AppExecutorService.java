package cn.dressbook.ui.SnowCommon.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutorService {
	public static ExecutorService executor;
	public static ExecutorService singleExecutor;
	public static final int POOL_SIZE=4;
	
	public static ExecutorService getExecutor(){
		if(executor==null){
			executor=Executors.newFixedThreadPool(POOL_SIZE);
		}
		return executor;
	}
	
	public static ExecutorService getSingleExecutor(){
		if(singleExecutor==null)
			singleExecutor=Executors.newSingleThreadExecutor();
		return singleExecutor;
	}
}
