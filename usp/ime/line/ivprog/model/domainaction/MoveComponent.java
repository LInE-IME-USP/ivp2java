/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 *
 * @see : ilm/framework/assignment/model/DomainAction.java
 * 
 */

package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class MoveComponent extends DomainAction {

  private IVPProgram model;
  private String component;
  private String destiny;
  private String destinyContext;
  private String originContext;
  private String origin;
  private int originIndex;
  private int dropIndex = 0;

  public MoveComponent (String name, String description) {
    super(name, description);
    }

  public void setDomainModel (DomainModel m) {
    model = (IVPProgram) m;
    }

  protected void executeAction () {
    originIndex = model.moveChild(component, origin, destiny, originContext, destinyContext, dropIndex, _currentState);
    }

  protected void undoAction () {
    model.moveChild(component, destiny, origin, destinyContext, originContext, originIndex, _currentState);
    }

  public boolean equals (DomainAction a) {
    return false;
    }

  public String getComponent () {
    return component;
    }

  public void setComponent (String component) {
    this.component = component;
    }

  public String getDestiny () {
    return destiny;
    }

  public void setDestiny (String destiny) {
    this.destiny = destiny;
    }

  public String getOrigin () {
    return origin;
    }

  public void setOrigin (String origin) {
    this.origin = origin;
    }

  public int getDropY () {
    return dropIndex;
    }

  public void setDropY (int dropY) {
    this.dropIndex = dropY;
    }

  public String getDestinyContext () {
    return destinyContext;
    }

  public void setDestinyContext (String destinyContext) {
    this.destinyContext = destinyContext;
    }

  public String getOriginContext () {
    return originContext;
    }

  public void setOriginContext (String originContext) {
    this.originContext = originContext;
    }

  public int getOriginIndex () {
    return originIndex;
    }

  public void setOriginIndex (int originIndex) {
    this.originIndex = originIndex;
    }

  public int getDropIndex () {
    return dropIndex;
    }

  public void setDropIndex (int dropIndex) {
    this.dropIndex = dropIndex;
    }

  public String toString () {
    String str = "";
    str += "<movecomponent>\n" + "   <component>" + component + "</component>\n" + "   <destiny>" + destiny + "</destiny>\n"
            + "   <destinycontext>" + destinyContext + "</destinycontext>\n" + "   <origincontext>" + originContext
            + "</origincontext>\n" + "   <origin>" + origin + "</origin>\n" + "   <originindex>" + originIndex + "</originindex>\n"
            + "   <dropindex>" + dropIndex + "</dropindex>\n" + "</movecomponent>\n";
    return str;
    }

  }
