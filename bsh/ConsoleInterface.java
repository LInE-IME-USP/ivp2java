// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package bsh;

import java.io.PrintStream;
import java.io.Reader;

public interface ConsoleInterface
{

    public abstract Reader getIn();

    public abstract PrintStream getOut();

    public abstract PrintStream getErr();

    public abstract void println(Object obj);

    public abstract void print(Object obj);

    public abstract void error(Object obj);
}
