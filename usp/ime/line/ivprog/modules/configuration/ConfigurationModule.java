
/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.modules.configuration;

import ilm.framework.modules.IlmModule;

public class ConfigurationModule extends IlmModule {

  // Called by: ilm.framework.assignment.AssignmentControl.initModuleList(): addModule(new ConfigurationModule());
  public ConfigurationModule () {
    _gui = new ConfigurationToolBar();
    }

  public void print() {
    }

  }
