package usp.ime.line.ivprog;

import java.util.HashMap;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.utils.IVPMouseListener;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class Services {
    private IVPController    controller;
    private IVPRenderer      render;
    private HashMap          modelHash;
    private HashMap          viewHash;
    private IVPMouseListener mL = null;
    private static Services  instance;
    
    private Services() {
        controller = new IVPController();
        render = new IVPRenderer();
        modelHash = new HashMap();
        viewHash = new HashMap();
        mL = new IVPMouseListener();
    }
    
    public static Services getService() {
        if (instance != null) {
            return instance;
        } else {
            instance = new Services();
        }
        return instance;
    }
    
    public IVPRenderer getRenderer() {
        return render;
    }
    
    public IVPController getController() {
        return controller;
    }
    
    public HashMap getModelMapping() {
        return modelHash;
    }
    
    public HashMap getViewMapping() {
        return viewHash;
    }
    
    public IVPMouseListener getML() {
        return mL;
    }
}
