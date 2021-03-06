/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * @description Static methods related to services.
 * 
 */

package usp.ime.line.ivprog.model.utils;

import ilm.framework.assignment.model.AssignmentState;

import java.util.HashMap;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.view.IVPRenderer;
import usp.ime.line.ivprog.view.utils.IVPMouseListener;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class Services {

  private static IVPController controller;
  private static IVPRenderer render;
  private static IVPMouseListener mouseListener = null;
  private static Services instance;
  private static AssignmentState current;

  static {
    controller = new IVPController();
    render = new IVPRenderer();
    mouseListener = new IVPMouseListener();
    }

  public static IVPRenderer getRenderer () {
    return render;
    }

  public static IVPController getController() {
    return controller;
    }

  public static HashMap getModelMapping () {
    return current.getData().getModelHash();
    }

  public static HashMap getViewMapping () {
    return current.getData().getViewHash();
    }

  public static IVPMouseListener getML () {
    return mouseListener;
    }

  public static AssignmentState getCurrentState () {
    return current;
    }

  public static void setCurrentState (AssignmentState current1) {
    current = current1;
    }

  }
