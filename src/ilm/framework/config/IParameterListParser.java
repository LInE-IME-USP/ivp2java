/*
 * iLM - interactive Learning Modules in the Internet
 * 
 * @see ilm.framework.config.AppletParameterListParser
 * @see ilm.framework.config.ParameterListParser
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework.config;

import java.util.Map;

public interface IParameterListParser {

  Map Parse (String [] parameterList);

  }
