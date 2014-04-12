package usp.ime.line.ivprog.model.utils;

import ilm.framework.assignment.model.AssignmentState;

import java.util.HashMap;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.utils.IVPMouseListener;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class Services {
    private IVPController    controller;
    private IVPRenderer      render;
    private IVPMouseListener mL = null;
    private static Services  instance;
    private AssignmentState  current;
    
    private Services() {
        controller = new IVPController();
        render = new IVPRenderer();
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
        return current.getData().getModelHash();
    }
    
    public HashMap getViewMapping() {
        return current.getData().getViewHash();
    }
    
    public IVPMouseListener getML() {
        return mL;
    }
    
    public AssignmentState getCurrentState() {
        return current;
    }
    
    public void setCurrentState(AssignmentState current) {
        this.current = current;
    }
}
