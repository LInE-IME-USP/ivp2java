package usp.ime.line.ivprog.modules.configuration;

import java.io.Serializable;

import ilm.framework.modules.IlmModule;

public class ConfigurationModule extends IlmModule implements Serializable{
    public ConfigurationModule() {
        _gui = new ConfigurationToolBar();
    }
    
    public void print() {
    }
}
