package com.crepusculoray;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Board extends JPanel implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
    ConfigEarth configEarth = new ConfigEarth();
    int select = 49;
    int ASCII = 48;
    int number = 19;
    float startRX = 0;
    float startRY = 0;
    float startTX = 0;
    float startTY = 0;
    float startTZ = 0;
    float step,scroll, stepLua;
    float multiscroll =1;
    float  mrx  ,mry, mtx=0, mty=0, mtz=0;
    Texture[] earth = new Texture[10];
    boolean firstAtive = false;
    boolean control = false;
    boolean optionCloud = false;
    boolean optionScreen = false;
    JFrame frame;
    private GLU glu;
    private GL2 gl;
    private ArrayList<CloudTP> memoryCLoud;
    private final AniCrepusculares[] aniCrepusculares = new AniCrepusculares[] {
            new TB_ver_globo_fora(),
            new TB_ver_globo_dentro(),
            new TP_ver_plana_fora(1),
            new TP_ver_plana_dentro(1),
            new TP_ver_plana_fora(2),
            new TP_ver_plana_dentro(2)};

    private void init() {
        glu = new GLU();
        setFocusable(true);
        step = 0;
        this.mtx= this.frame.getWidth()/2;
        this.mty= this.frame.getHeight()/2;
        this.mtz=0;
        this.firstAtive = true;
        this.memoryCLoud = createCloudTP();

    }
    public Board(JFrame frame)  {
        this.frame = frame;
        this.init();
    }

    public void openAni(final AniCrepusculares aniCrepusculares, final GL2 gl, GLU glu, final float step, float mrx, float mry,  float mtx, float mty, float mtz,  float scroll)  {
        aniCrepusculares.setButton(this.control);
        aniCrepusculares.setAmbiente(this.frame.getWidth(),this.frame.getHeight(),  this.earth, this.memoryCLoud, multiscroll, this.optionCloud, this.stepLua);
        aniCrepusculares.setMouse(mrx , mry, -mtx, mty, -mtz, scroll);
        aniCrepusculares.lines(gl, glu, step);
        System.out.print("\r " + step + " steps. Rotate(" + mrx + ", " + mry + ")  Translation(" + mtx + ", " + mty + ", " + mtz + ")  Scroll:" + this.scroll + " Screen(" + this.frame.getWidth() + "," + this.frame.getHeight()+ ") Zoom x"+this.multiscroll);
    }

    private void run(GL2 gl, GLU glu)  {
        int option = 0;
        if (select > ASCII && select <= ASCII + number) {
            option = select - ASCII ; }
        if (select >=65) {
            option = select - ASCII -7;
        }
        if (0 <= option && option <= aniCrepusculares.length) {
            runFirstAtive(option);
            openAni(aniCrepusculares[option-1], gl, glu, step, mrx, mry, mtx, mty, mtz, this.scroll);
        }
        this.step+=1;
        this.stepLua +=1.1f;
        if (this.step >= 360 +1) this.step = 0;
        if (this.stepLua >= 360 +1) this.stepLua = 0;
    }
    private void observer(float rx, float ry, float tx, float ty, float tz, float zoom) {
        this.scroll = zoom;
        this.mrx=rx;
        this.mry=ry;
        this.mtx= tx;
        this.mty= ty;
        this.mtz = tz;
    }
    private void runFirstAtive(int option) {
        if(this.firstAtive) {
            switch (option) {
                case 1: { observer(360.0f, -180.0f, 0, 0, 105657.0f, 1.0f); break; }
                case 2: { observer(360.0f, -180.0f, -57.0f,  - 2, 0.0f, 1.0f); break; }
                case 3: { observer(-362.5f, -39.0f, -49, 355, 34820, 1.0f); break; }
                case 4: { observer(795.5f, -82.5f, -7932.0f, 4584.7f, configEarth.heightObserver(), 1.0f); break; }
                case 5: { observer(-362.5f, -39.0f, -49, 355, 34820, 1.0f); break; }
                case 6: { observer(795.5f, -82.5f, -7932.0f, 4584.7f, configEarth.heightObserver(), 1.0f); break; }
            }
            newEarth();
        }
        this.firstAtive = false;
    }

    private void newEarth() {
        try {
            InputStream is;
            is = this.getClass().getResourceAsStream("/EarthMap_2500x1250.jpg");
            this.earth[0] = TextureIO.newTexture(is, false, "jpg");
            is = this.getClass().getResourceAsStream("/flatearth1.png");
            this.earth[1] = TextureIO.newTexture(is, false, "png");
        }
        catch (IOException e) {
            System.out.println("\n Texture not find.");
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    private void newScreen(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = clear(drawable.getGL().getGL2());
        if( height <= 0 )
            height = 1;
        float fix = 1.05f;
        final float wh = ( float ) width / ( float ) height;
        if(this.optionScreen)
            gl.glViewport( x, y, width , height );
        else
            gl.glViewport( x, y, (int)(width * fix), (int)(height * fix));
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();
        glu.gluPerspective( 45.0f, wh, 1, 200000000.0 );
        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc( GL2.GL_LEQUAL );
        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glDepthRange(0.0f, 1.0f);
        gl.glClearDepthf(1.0f);
        gl.glDepthMask(true);
        gl.glLoadIdentity();
    }

    private ArrayList<CloudTP> createCloudTP() {
        ArrayList<CloudTP> cloudTP = new ArrayList<>();
        float radius = configEarth.flatRay();
        float angle = 360;
        float size = 1000;
        for(int i=0; i<10000;i++) {
            cloudTP.add(new CloudTP((float)Math.random()*radius, (float)Math.random()*angle, (float)Math.random()*size));
        }
        return cloudTP;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display( GLAutoDrawable drawable ) {
        this.gl = clear(drawable.getGL().getGL2());
        glu = new GLU();
        run(this.gl, glu);
        gl.glFlush();
        this.gl.glLoadIdentity();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        newScreen(drawable, x, y, width, height);
    }

    private GL2 clear(GL2 gl) {
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        return gl;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int put = e.getKeyCode();
        System.out.println("\nPut:"+put);
        switch (put) {
            case 27:
                System.exit(0);
                break;
            case 38:
                this.multiscroll *= 10;
                break;
            case 40:
                this.multiscroll /= 10;
                break;
            case 67:
                this.optionCloud = !this.optionCloud;
                break;
            case 83: {
                this.optionScreen = !this.optionScreen;

                break;
            }
        }

        if(put>=49 && put<58) {
            this.step = 0;
            this.stepLua = 0;
            this.multiscroll = 1;
            this.select = put;
            this.init();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.startRX = e.getX()* 0.5f + this.mrx;
        this.startRY = e.getY()* 0.5f + this.mry;
        this.startTX = e.getX()* this.multiscroll + this.mtx;
        this.startTY = e.getY()* this.multiscroll + this.mty;
        this.startTZ = e.getY()* this.multiscroll + this.mtz;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(SwingUtilities.isRightMouseButton(e)) {
            this.mrx = this.startRX - ((float)e.getX() * 0.5f);
            this.mry = this.startRY - ((float)e.getY() * 0.5f);
        }
        if(SwingUtilities.isLeftMouseButton(e)) {
            this.mtx = this.startTX - ((float)e.getX() * this.multiscroll);
            this.mty = this.startTY - ((float)e.getY() * this.multiscroll);
        }
        if(SwingUtilities.isMiddleMouseButton(e)) {
            this.mtx = this.startTX - ((float)e.getX() * this.multiscroll);
            this.mtz = this.startTZ - ((float)e.getY() * this.multiscroll);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() < 0) {
                this.scroll++;
            } else {
                this.scroll--;
            }
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

}
