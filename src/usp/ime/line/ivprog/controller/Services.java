package usp.ime.line.ivprog.controller;

import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class Services {
	
	private IVPController controller;
	private IVPRenderer render;
	private IVPMapping map;
	private static Services instance;
	
	public Services(){
		controller = new IVPController();
		render = new IVPRenderer(); 
		map = new IVPMapping();
	}
	
	public static Services getService(){
		if(instance != null){
			return instance;
		} else {
			instance = new Services();
		}
		return instance;
	}
	
	public IVPRenderer renderer(){
		return render;
	}
	
	public IVPController controller(){
		return controller;
	}
	
	public IVPMapping mapping(){
		return map;
	}

}
