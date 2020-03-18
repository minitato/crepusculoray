package com.crepusculoray;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

class KeyBoard implements KeyListener
{
    private Hashtable keysPressed;
    static final int UP_KEY = 38;
    static final int LEFT_KEY = 37;
    static final int RIGHT_KEY = 39;
    static final int DOWN_KEY = 40;
    static final int ESCAPE_KEY = 27;
    static final int SPACE_KEY = 32;
    public static final int ENTER_KEY = 10;
    public static final int CONTROL_KEY = 17;
//    public static final int ALT_KEY = 18;
//    public static final int Z_KEY = 90;
//    public static final int X_KEY = 88;
//    public static final int C_KEY = 67;

    private boolean enterPressionado;
    private boolean spacePressionado;
    private boolean escapePressionado;

    public KeyBoard()
    {
        enterPressionado = false;
        escapePressionado = false;
        spacePressionado = false;
        keysPressed = new Hashtable();

    }

    public boolean enterPresLib( )
    {
        if ( keyDown(KeyBoard.ENTER_KEY) )
            this.enterPressionado = true;

        if ( enterPressionado && (keyDown(KeyBoard.ENTER_KEY) == false) )
        {
            this.enterPressionado = false;
            return true;
        }

        return false;
    }

    public boolean spacePresLib( )
    {
        if ( keyDown(KeyBoard.SPACE_KEY) )
            this.spacePressionado = true;

        if ( spacePressionado && (keyDown(KeyBoard.SPACE_KEY) == false))
        {
            this.spacePressionado = false;
            return true;
        }

        return false;
    }

    public boolean escapePresLib( )
    {
        if ( keyDown(KeyBoard.ESCAPE_KEY) )
            this.escapePressionado = true;

        if ( escapePressionado && (keyDown(KeyBoard.ESCAPE_KEY) == false) )
        {
            this.escapePressionado = false;
            return true;
        }

        return false;
    }


    public synchronized boolean keyDown(int keyCode)
    {
        return keysPressed.contains(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public synchronized void keyPressed(KeyEvent e)
    {
        if(keysPressed.contains(e.getKeyCode()) == false)
        {
            keysPressed.put(e.getKeyCode(), e.getKeyCode());
        }
    }

    public synchronized void keyReleased(KeyEvent e)
    {
        keysPressed.remove(e.getKeyCode());
    }
}


