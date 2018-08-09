ivp2java
========

Working directory of iVProg 2 (version Java). It works under Java 6 (or newer).
Repositório de trabalho do IVProg 2 na versão Java.

Home: http://www.matematica.br/ivprog2/
New version in HTML5 (in preparation): http://www.matematica.br/ivprogh5/

---

Since 2014 we do not have any one dedicated to improve iVProg2. Perhaps you can advance the iVProg2 project.
This version demands the auxiliary free package "BeanShell", but it is a working version without it (this one runs with Java 4).

The iVProg2 can be used as application (that could register files) or even as "applet" (in this case, you can use the free browser PaleMoon
since Firefox and Chrome abolished Java from them...).

If you want to generate the iVProg2 JAR package manually (by command lines), you can follow these steps:

 1. Please, get the package 'https://github.com/LInE-IME-USP/ivp2java/auxilliary_ivprog2.tgz'. It contains:<pre>
    compile_all                                                  # script to compile iVProg2
    make_jar                                                     # script to prepare JAR
    mainClass                                                    # pointer to iVProg2 main class
    bin/bsh/*                                                    # auxiliary package to run (from http://www.beanshell.org/)
    bin/ilm/framework/config/defaultConfig.properties            # configuration
    bin/usp/ime/line/ivprog/view/utils/language/ptBR.properties  # messages in Portuguese language
    bin/usp/ime/line/ivprog/view/utils/language/enUS.properties  # messages in English language (you can prepare others - please send it to us)
    bin/usp/ime/line/resources                                   # auxiliary resources (as images)</pre>
 2. Make a clone of iVProg2 master directory (usually you will register it as 'ivp2java-master.zip');

 3. Go to the target directory (where you intend to generate iVProg2.jar);<pre>
    $ mkdir ivp
    $ cd ivp
    $ mv /directory/ivp2java-master.zip .
    $ unzip -x ivp2java-master.zip</pre>

 4. To generate an English version of iVProg2 (for now it is not simple as by menu or command line 'lang=enUS')
    4.1 Edit the file 'usp/ime/line/ivprog/view/utils/language/ResourceBundleIVP.java'
    4.2 Change the following line<br/>
         'private static final String BUNDLE_NAME = "usp.ime.line.ivprog.view.utils.language.ptBR";<br/>
        to<br/>
         "private static final String BUNDLE_NAME = "usp.ime.line.ivprog.view.utils.language.enUS";
    4.3 You can prepare your other language file as 'usp/ime/line/ivprog/view/utils/language/xxYY.properties'<br/>
        using 'usp/ime/line/ivprog/view/utils/language/enUS.properties' as model.<br/>
	In this case, if you feel confortable to distribute it, please send it to me 'leo@ime.usp.br' ;)		  

 5. Get the auxiliary binaries (from 'https://github.com/LInE-IME-USP/ivp2java/auxilliary_ivprog2.tgz')
    and place it at the same directory ('ivp')

 6. Unpack 'auxilliary_ivprog2.tgz'<br/>
    $ tar xvfz auxilliary_ivprog2.tgz'

 7. Compile all iVprog2 JAVA files (see the 'java' command line, adapt it to your 'javac' compiler - that must be from version 6 to up):<br/>
    $ sh compile_all

 8. Pack all CLASS files in a single JAR file ('iVProg2.jar')<br/>
    $ sh make_jar

 9. Put the 'iVPro2.jar' file generated in any place to run<br/>
    $ java -jar iVProg2.jar [your_file.ivp2]<br/>
    or (ir you have any prepared file from iVProg2)<br/>
    $ java -jar iVProg2.jar your_file.ivp2 lang=enUS
 
Then you must place the 'iVProg2.jar' in you preferable directory to run as you wish.<br/>
There are lot of thinks to adjust, but this version is ready to be used in several situations.<br/>
Enjoy it.

Leo^nidas de Oliveira Branda~o<br/>
leo@ime.usp.br<br/>
http://www.matematica.br<br/>
http://line.ime.usp.br