package com.crepusculoray;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class showGL extends JFrame {

    private static final long serialVersionUID = 1L;
    final private int width = 1024;
    final private int height = 768;

    public showGL()  {
        super("Minimal OpenGL");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        //capabilities.setHardwareAccelerated(true);
        //capabilities.setDoubleBuffered(true);
        //capabilities.setNumSamples(2);
        //capabilities.setSampleBuffers(true);
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        System.out.println("WELCOME ANIMATOR CREPUSCULARES RAY 1.1");
        System.out.println(" PRESS KEY: ");
        System.out.println("  1) Globe: Earth in space");
        System.out.println("  2) Globe: Earth in solo");
        System.out.println("  3) Flat: Earth in Domo - All Observe");
        System.out.println("  4) Flat: Earth in solo - All Observe");
        System.out.println("  5) Flat: Earth in Domo - Only Vertical");
        System.out.println("  6) Flat: Earth in solo - Only Vertical");
        System.out.println("  ");

        System.out.println("  MOUSE de botão Esquerda - Rotação ");
        System.out.println("  MOUSE de botão Direita - Translação XY");
        System.out.println("  MOUSE de botão Meio - Translação Z e girar zoom");
        System.out.println("  C - Cloud - Enable / Disable ");

        System.out.println("  ESC    - Exit.");
        System.out.println("---------------------------------------");
        Board b = new Board(this);
        this.setName("WELCOME ANIMATOR CREPUSCULARES RAY 1.1");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);

        glcanvas.addComponentListener(b);
        glcanvas.addMouseWheelListener(b);
        glcanvas.addGLEventListener(b);
        glcanvas.addKeyListener(b);
        glcanvas.addMouseListener(b);
        glcanvas.addMouseMotionListener(b);
        glcanvas.setSize(width, height);
        glcanvas.requestFocusInWindow();
        add(glcanvas);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 60,true);
        animator.start();
    }

}

