// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package bsh;


// Referenced classes of package bsh:
//            CallStack, SimpleNode, NameSpace

public class EvalError extends Exception
{

    public EvalError(String s, SimpleNode simplenode, CallStack callstack1)
    {
        setMessage(s);
        node = simplenode;
        if(callstack1 != null)
            callstack = callstack1.copy();
    }

    public String toString()
    {
        String s;
        if(node != null)
            s = " : at Line: " + node.getLineNumber() + " : in file: " + node.getSourceFile() + " : " + node.getText();
        else
            s = ": <at unknown location>";
        if(callstack != null)
            s = s + "\n" + getScriptStackTrace();
        return getMessage() + s;
    }

    public void reThrow(String s)
        throws EvalError
    {
        prependMessage(s);
        throw this;
    }

    SimpleNode getNode()
    {
        return node;
    }

    void setNode(SimpleNode simplenode)
    {
        node = simplenode;
    }

    public String getErrorText()
    {
        if(node != null)
            return node.getText();
        else
            return "<unknown error>";
    }

    public int getErrorLineNumber()
    {
        if(node != null)
            return node.getLineNumber();
        else
            return -1;
    }

    public String getErrorSourceFile()
    {
        if(node != null)
            return node.getSourceFile();
        else
            return "<unknown file>";
    }

    public String getScriptStackTrace()
    {
        if(callstack == null)
            return "<Unknown>";
        String s = "";
        for(CallStack callstack1 = callstack.copy(); callstack1.depth() > 0;)
        {
            NameSpace namespace = callstack1.pop();
            SimpleNode simplenode = namespace.getNode();
            if(namespace.isMethod)
            {
                s = s + "\nCalled from method: " + namespace.getName();
                if(simplenode != null)
                    s = s + " : at Line: " + simplenode.getLineNumber() + " : in file: " + simplenode.getSourceFile() + " : " + simplenode.getText();
            }
        }

        return s;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String s)
    {
        message = s;
    }

    protected void prependMessage(String s)
    {
        if(s == null)
            return;
        if(message == null)
            message = s;
        else
            message = s + " : " + message;
    }

    SimpleNode node;
    String message;
    CallStack callstack;
}
