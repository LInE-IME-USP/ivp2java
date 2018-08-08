/*
 * iLM - interactive Learning Modules in the Internet
 * 
 * @see ilm.framework.config.IParameterListParser
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework.config;

import java.util.HashMap;
import java.util.Map;

public class ParameterListParser implements IParameterListParser {

  public Map Parse (String[] parameterList) {
    //D System.out.println("ParameterListParser.java: Parse");
    if (parameterList != null) {
      Map result = new HashMap();
      for (int i = 0; i < parameterList.length; i++) {
        if ((parameterList[i].startsWith("-")) && (i + 1 < parameterList.length)) {
          String key = parameterList[i].substring(1);
          String value = parameterList[i + 1];
          result.put(key, value);
          i++;
          }
        }
      return result;
      }
    return null;
    }

  }
