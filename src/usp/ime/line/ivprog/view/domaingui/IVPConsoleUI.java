package usp.ime.line.ivprog.view.domaingui;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;

import javax.swing.JTextArea;

import bsh.ConsoleInterface;
import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.FlatUIColors;

public class IVPConsoleUI extends JTextArea implements ConsoleInterface {

    private OutputStream outPipe;
    private InputStream  inPipe;
    private InputStream  in;
    private PrintStream  out;

    public InputStream getInputStream() {
        return in;
    }

    public IVPConsoleUI() {
        this(null, null);
    }

    public IVPConsoleUI(InputStream cin, OutputStream cout) {
        setFont(new Font("Consolas", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setBackground(FlatUIColors.CONSOLE_COLOR);
        setEditable(false);
        Services.getService().getController().setConsole(this);

        outPipe = cout;
        if (outPipe == null) {
            outPipe = new PipedOutputStream();
            try {
                in = new PipedInputStream((PipedOutputStream) outPipe);
            } catch (IOException e) {
                print("Console internal error (1)...");
            }
        }

        inPipe = cin;
        if (inPipe == null) {
            PipedOutputStream pout = new PipedOutputStream();
            out = new PrintStream(pout);
            try {
                inPipe = new BlockingPipedInputStream(pout);
            } catch (IOException e) {
                print("Console internal error: " + e);
            }
        }

    }

    public void error(Object arg0) {
    }

    public PrintStream getErr() {
        return out;
    }

    public Reader getIn() {
        return new InputStreamReader(in);
    }

    public PrintStream getOut() {
        return out;
    }

    public void println(Object o) {
        print(String.valueOf(o) + "\n");
        repaint();
    }

    public void print(final Object o) {
        append(String.valueOf(o));
    }

    public void printError(Object o) {
        setForeground(Color.red);
        print(o);
        setForeground(Color.white);
    }

    public static class BlockingPipedInputStream extends PipedInputStream {
        boolean closed;

        public BlockingPipedInputStream(PipedOutputStream pout) throws IOException {
            super(pout);
        }

        public synchronized int read() throws IOException {
            if (closed)
                throw new IOException("stream closed");

            while (super.in < 0) { // While no data */
                notifyAll(); // Notify any writers to wake up
                try {
                    wait(750);
                } catch (InterruptedException e) {
                    throw new InterruptedIOException();
                }
            }
            // This is what the superclass does.
            int ret = buffer[super.out++] & 0xFF;
            if (super.out >= buffer.length)
                super.out = 0;
            if (super.in == super.out)
                super.in = -1; /* now empty */
            return ret;
        }

        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }

    public void clean() {
        setText("");
    }

}
