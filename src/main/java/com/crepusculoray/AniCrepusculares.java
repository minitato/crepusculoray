package com.crepusculoray;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;

import java.util.ArrayList;

import static com.jogamp.opengl.GL2.GL_COMPILE;
import static com.jogamp.opengl.glu.GLU.GLU_FILL;
import static com.jogamp.opengl.glu.GLU.GLU_SMOOTH;

public abstract class AniCrepusculares {
    ConfigEarth configEarth = new ConfigEarth();
    boolean ativoControl = false;
    boolean optionCloud = false;
    float width = 0;
    float height = 0;
    float step, stepLua;
    float mrx, mry, mtx, mty, mtz, scroll;
    float size = 0;
    float multiscroll = 1;
    float TPray = configEarth.flatRay();
    Texture[] earth = new Texture[10];
    ArrayList<CloudTP> memoryCloud;

    abstract void lines(GL2 gl, GLU glu, float step);

    void setMouse(float rx, float ry, float tx, float ty, float tz, float scroll) {
        this.mrx = rx;
        this.mry = ry;
        this.mtx = tx;
        this.mty = ty;
        this.mtz = tz;
        this.scroll = scroll;
    }

    void setStep(float step) {
        this.step = step ;
    }

    void setAmbiente(float w, float h, Texture[] earth, ArrayList<CloudTP> cloud, float multiscroll, boolean optionCloud, float stepLua) {
        this.width = w;
        this.height = h;
        this.earth = earth;
        this.memoryCloud = cloud;
        this.multiscroll = multiscroll;
        this.optionCloud = optionCloud;
        this.stepLua = stepLua;
    }

    void luz(GL2 gl, GLU glu, float x, float y, float z) {

        float[] whitish = {0.8f, 0.8f, 0.8f, 1};
        float[] white = {1, 1, 1, 1};
        float[] blackish = {0.2f, 0.2f, 0.2f, 1};
        float[] position = {-x, -y, -z};
        gl.glPushMatrix();

        gl.glShadeModel(gl.GL_TRIANGLE_STRIP);
        gl.glEnable(gl.GL_COLOR_MATERIAL);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, blackish, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whitish, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, whitish, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, white, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, white, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, white, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glPopMatrix();
    }

    void lampada(GL2 gl, GLU glu, float x, float y, float z) {
        gl.glPushMatrix();

        gl.glColor3f(1, 0.8f, 0);
        GLUquadric sQuad = glu.gluNewQuadric();
        glu.gluQuadricOrientation(sQuad, glu.GLU_INSIDE);
        glu.gluQuadricDrawStyle(sQuad, GLU.GLU_LINE);
        gl.glTranslatef(x, y, z);
        glu.gluSphere(sQuad, 0.1, 36, 72);
        glu.gluDeleteQuadric(sQuad);
        gl.glPopMatrix();

    }

    void Sol(GL2 gl, GLU glu, float x, float y, float z, float size) {
        gl.glPushMatrix();
        gl.glColor3f(1, 0.8f, 0);
        GLUquadric sQuad = glu.gluNewQuadric();
        glu.gluQuadricOrientation(sQuad, GLU.GLU_INSIDE);
        glu.gluQuadricDrawStyle(sQuad, GLU.GLU_LINE);
        gl.glTranslatef(x, y, z);
        glu.gluSphere(sQuad, size, 36, 72);
        glu.gluDeleteQuadric(sQuad);
        gl.glPopMatrix();
    }

    void Lua(GL2 gl, GLU glu, float x, float y, float z, float size) {
        gl.glPushMatrix();
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        GLUquadric sQuad = glu.gluNewQuadric();
        glu.gluQuadricOrientation(sQuad, GLU.GLU_INSIDE);
        glu.gluQuadricDrawStyle(sQuad, GLU.GLU_LINE);
        gl.glTranslatef(x, y, z);
        glu.gluSphere(sQuad, size, 36, 72);
        glu.gluDeleteQuadric(sQuad);
        gl.glPopMatrix();
    }

    void SolTP(GL2 gl, GLU glu, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glColor3f(1, 0.8f, 0);
        GLUquadric sQuad = glu.gluNewQuadric();
        glu.gluQuadricOrientation(sQuad, GLU.GLU_INSIDE);
        glu.gluQuadricDrawStyle(sQuad, GLU.GLU_LINE);
        gl.glTranslatef(x, y, z);
        glu.gluSphere(sQuad, 100, 12, 24);
        glu.gluDeleteQuadric(sQuad);
        gl.glPopMatrix();

    }

    void Solo(GL2 gl, GLU glu, float x, float y, float z, float area) {
        gl.glPushMatrix();
        boolean corFirst = true;
        boolean cor = true;
        float t = area;
        float p = 100;
        gl.glTranslatef(x, y, -z);
        for (float i = -t; i < t; i += p) {
            if (corFirst == cor) {
                cor = !cor;
                corFirst = cor;
            }
            for (float j = -t; j < t; j += p) {
                cor = !cor;
                gl.glPushMatrix();
                gl.glTranslatef(i, j, 0);
                if (cor) {
                    gl.glColor3f(0, 0.0f, 1);
                } else {
                    gl.glColor3f(0, 0.0f, 0.5f);
                }
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                gl.glVertex3f(-p, p, 0.0f);
                gl.glVertex3f(p, p, 0.0f);
                gl.glVertex3f(-p, -p, 0.0f);
                gl.glVertex3f(p, -p, 0.0f);
                gl.glEnd();
                gl.glPopMatrix();
            }
        }

        gl.glPopMatrix();
    }

    void init(GL2 gl, GLU glu, float step) {
        setStep(step);
    }

    void Terra(GL2 gl, GLU glu, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glColor3f(0, 0, 1);
        gl.glShadeModel(gl.GL_TRIANGLE_STRIP);
        GLUquadric sQuad = glu.gluNewQuadric();
        glu.gluQuadricOrientation(sQuad, GLU.GLU_INSIDE);
        glu.gluQuadricTexture(sQuad, false);
        glu.gluQuadricDrawStyle(sQuad, GLU_FILL);
        glu.gluSphere(sQuad, configEarth.sphericalRay(), 36, 72);
        glu.gluDeleteQuadric(sQuad);
        gl.glPopMatrix();
    }

    void paralela(GL2 gl, GLU glu, float tx, float ty, float tz,  float sx, float sy, float sz, float size, float denso) {
        gl.glPushMatrix();
        gl.glTranslatef(tx, ty, tz);
        float altura = sz;
        float randomRed = 0;
        gl.glLineWidth(	5.0f);
        gl.glBegin(gl.GL_LINES);
        for (float i = -size; i < size; i += denso) {
            for (float j = -size; j < size; j += denso) {
                randomRed = (float) (0.5+Math.random()* 0.5);
                gl.glColor3f(randomRed, 0, 0);
                gl.glVertex3f( i , j, 0);
                gl.glVertex3f( i , j, altura);
            }
        }
        gl.glColor3f(1, 1, 1);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(sx, sy, altura);

        gl.glEnd();
        gl.glPopMatrix();
    }

    void triangulo(GL2 gl, GLU glu, float x, float y, float z, float dx, float dy, float altura, float area) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        float p = area / 10;
        float randomRed = 0;
        gl.glColor3f(1, 0, 0);
        gl.glLineWidth(	5.0f);
        gl.glBegin(gl.GL_LINES);
        for (float i = -area; i < area; i += p) {
            for (float j = -area; j < area; j += p) {
                randomRed = (float) (0.5+Math.random()* 0.5);
                gl.glColor3f(randomRed, 0, 0);
                gl.glVertex3f(i, j, 0);
                gl.glVertex3f(dx, dy, altura);
            }
        }
        gl.glEnd();
        gl.glPopMatrix();

    }

    void TBnuvem(GL2 gl, GLU glu, float x, float y, float z, float raio, float altura, float area_angulo) {
        if (this.optionCloud) {
            gl.glPushMatrix();
            gl.glTranslatef(x, y, z);
            float p = 2 * raio / 360;
            gl.glColor3f(1, 1, 1);
            for (float i = -area_angulo; i < area_angulo; i += 1) {
                for (float j = -area_angulo; j < area_angulo; j += 1) {
                    gl.glPushMatrix();
                    gl.glRotatef(i, 1.0f, 0.0f, 0.0f);
                    gl.glRotatef(j, 0.0f, 1.0f, 0.0f);
                    gl.glTranslatef(0, 0, raio + altura);
                    gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                    gl.glVertex3f(-p, p, 0.0f);
                    gl.glVertex3f(p, p, 0.0f);
                    gl.glVertex3f(-p, -p, 0.0f);
                    gl.glVertex3f(p, -p, 0.0f);
                    gl.glEnd();
                    gl.glPopMatrix();
                }
            }
            gl.glPopMatrix();
        }
    }

    void TPnuvem(GL2 gl, GLU glu, float x, float y, float altura, float area) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, 0);
        float p = area / 100;
        gl.glColor3f(1, 1, 1);
        for (float i = -area; i < area; i += p * 4) {
            for (float j = -area; j < area; j += p * 4) {
                gl.glPushMatrix();
                gl.glTranslatef(i , j , altura);
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                gl.glVertex3f(-p, p, 0.0f);
                gl.glVertex3f(p, p, 0.0f);
                gl.glVertex3f(-p, -p, 0.0f);
                gl.glVertex3f(p, -p, 0.0f);
                gl.glEnd();
                gl.glPopMatrix();
            }
        }
        gl.glPopMatrix();
    }

    void TBterraTexture(GL2 gl, GLU glu, float x, float y, float z) {
        Texture globo = this.earth[0];
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            GLUquadric sphere = glu.gluNewQuadric();
            glu.gluQuadricDrawStyle(sphere, GLU_FILL);
            glu.gluQuadricTexture(sphere, true);
            glu.gluQuadricNormals(sphere, GLU_SMOOTH);
            //Making a display list
            int mysphereID = gl.glGenLists(1);
            gl.glNewList(mysphereID, GL_COMPILE);
            globo.enable(gl);
            globo.bind(gl);
            glu.gluSphere(sphere, configEarth.sphericalRay() , 180, 360);
            globo.disable(gl);
            gl.glEndList();
            glu.gluDeleteQuadric(sphere);
        gl.glPopMatrix();
        gl.glCallList(mysphereID);
        gl.glPopMatrix();
    }

    void TPterraTexture(GL2 gl, GLU glu, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1);
            gl.glTranslatef(x, y, z);
            gl.glEnable(GL2.GL_TEXTURE_2D);
            Texture text = this.earth[1];
            gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
            gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            text.bind(gl);
            text.enable(gl);
            gl.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            //gl.glEnable(GL2.GL_COLOR_MATERIAL);
            float sizeX = TPray, sizeY = TPray;
            gl.glBegin(GL2.GL_QUADS);
            // create crosshair square
                gl.glTexCoord2d(0, 0);
                gl.glVertex2d(-sizeX, -sizeY);
                gl.glTexCoord2d(1, 0);
                gl.glVertex2d(sizeX, -sizeY);
                gl.glTexCoord2d(1, 1);
                gl.glVertex2d(sizeX, sizeY);
                gl.glTexCoord2d(0, 1);
                gl.glVertex2d(-sizeX, sizeY);
            gl.glEnd();
            text.disable(gl);
        gl.glPopMatrix();
    }

    void TPcloud(GL2 gl, GLU glu, float x, float y, float z, float radius) {
        if(this.optionCloud) {
            gl.glPushMatrix();
            gl.glTranslatef(x, y, z);
            gl.glColor3f(1, 1, 1);
            float p = 100;
            int index = this.memoryCloud.size();
            for (int i = 0; i < index; i++) {
                gl.glPushMatrix();
                gl.glTranslatef(0, 0, 0);
                gl.glRotatef(memoryCloud.get(i).getAngle(), 0.0f, 0.0f, 1.0f);
                gl.glTranslatef(memoryCloud.get(i).getRadius(), 0, 0);
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                gl.glVertex3f(-p, p, 0.0f);
                gl.glVertex3f(p, p, 0.0f);
                gl.glVertex3f(-p, -p, 0.0f);
                gl.glVertex3f(p, -p, 0.0f);
                gl.glEnd();
                gl.glPopMatrix();
            }
            gl.glPopMatrix();
        }
    }
    void TPsunFollow(GL2 gl, GLU glu, float localx, float localy, float localz, float sunx, float suny, float sunz, float radius, float densy) {
        double pi = 3.1415926 / 180;
        double d = Math.sqrt(sunx*sunx+suny*suny);
        float dx =(float) (Math.sin((270-step)*pi) * d);
        float dy =(float) (Math.cos((270-step)*pi) * d);
        trianguloCircle(gl, localx, localy, localz, dx, dy, sunz, radius, densy);
    }

    void TPsunFollowFoot(GL2 gl, GLU glu, float sunx, float suny, float sunz, float radius, float densy) {
        trianguloCircle(gl, sunx, suny, 0, 0, 0, sunz, radius, densy);
    }

    private void trianguloCircle(GL2 gl,  float localx, float localy, float localz, float sunx, float suny, float sunz, float radius, float densy) {
        double pi = 3.1415926 ;
        double pis = pi / 180;
        gl.glPushMatrix();
        gl.glTranslatef(localx, localy, localz);
        if (densy == 0 ) densy = 1;
        float randomRed;
        gl.glColor3f(1, 0, 0);
        gl.glLineWidth(	5.0f);
        gl.glBegin(gl.GL_LINES);
        for (float i = 1; i < radius; i += densy) {
            float aspect = (float) (Math.asin(densy/i)*180/pi);
            for (float j = 0; j < 360; j += aspect) {
                float dx =(float) (Math.sin(j*pis) * i);
                float dy =(float) (Math.cos(j*pis) * i);
                randomRed = (float) (0.5+Math.random()* 0.5);
                gl.glColor3f(randomRed, 0, 0);
                gl.glVertex3f(dx, dy, 0);
               // gl.glVertex3f(dx, dy, 100);
                gl.glVertex3f(sunx, suny, sunz);
            }
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    void updateScrolltoSize(GL2 gl) {
        float s = this.scroll;
        if (s > 0) {
            this.size = s;
        } else {
            this.size = 1 / (-s);
        }
        gl.glScalef(this.size + 0.0001f, this.size + 0.0001f, this.size + 0.0001f);
    }

    float less(float m) {
        return m / 1;
    }

    public void setButton(boolean control) {
        this.ativoControl = control;
    }
}

class TB_Terra extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step)  {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
        setStep(step);
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -8);
        gl.glTranslatef((-mtx + this.width / 2) / 100, (mty - this.height / 2) / 100, 0);
        float s = 0.0005f;
        gl.glScalef(s, s, s);

        gl.glScalef(this.size, this.size, this.size);
        gl.glRotatef(this.mry , 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.mrx , 0.0f, 1.0f, 0.0f);
        //paralela(gl, glu, 0, 0, 0, 696340);
        Sol(gl, glu, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun());
        TBterraTexture(gl, glu, 0, 0, 0);
        gl.glPopMatrix();
    }
}
    class TB_Sol extends AniCrepusculares {
        public void lines(GL2 gl, GLU glu, float step) {
            gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
            // 1 unidade = 1 km
            setStep(step);
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -8);
            gl.glTranslatef((-mtx + this.width / 2) / 100, (mty - this.height / 2) / 100, 0);
            float s = 0.0005f;
            gl.glScalef(s, s, s);
            gl.glScalef(this.size, this.size, this.size);
            gl.glRotatef(this.mry , 1.0f, 0.0f, 0.0f);
            gl.glRotatef(this.mrx , 0.0f, 1.0f, 0.0f);
            //luz(gl, glu, 4, 4, this.step-18);
            //paralela(gl, glu, 0, 0, 0,696340);
            Sol(gl, glu, 0, 0, 0, configEarth.sphericalRaySun());
            Terra(gl, glu, 0, 0, configEarth.sphericalDistanceSun());

            gl.glPopMatrix();
        }
    }

class TB_Altura_Sol extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step) {

        gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
        // gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        // 1 unidade = 1 km
        setStep(step);
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -8);
        gl.glTranslatef((-mtx + this.width / 2) / 100, (mty - this.height / 2) / 100, 0);
        float s = 0.0005f;
        gl.glScalef(s, s, s);
        gl.glScalef(this.size, this.size, this.size);
        gl.glRotatef(this.mry , 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.mrx , 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0, 0, 15000);
        luz(gl, glu, 0, 0, configEarth.sphericalDistanceSun());
        Sol(gl, glu, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun());
        TBterraTexture(gl, glu, 0, 0, 0);
        //paralela(gl, glu, 0, 0, 0, 696340);
        gl.glPopMatrix();
    }
}
    class TB_meio extends AniCrepusculares {
        public void lines(GL2 gl, GLU glu, float step) {
            gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
            // gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
            // 1 unidade = 1 km
            setStep(step);
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -8);
            gl.glTranslatef((-mtx + this.width / 2) / 100, (mty - this.height / 2) / 100, 0);
            float s = 0.0005f;
            gl.glScalef(s, s, s);
            gl.glScalef(this.size, this.size, this.size);
            gl.glRotatef(this.mry , 1.0f, 0.0f, 0.0f);
            gl.glRotatef(this.mrx , 0.0f, 1.0f, 0.0f);
            //luz(gl, glu, 4, 4, this.step-18);
            gl.glTranslatef(0, 0, configEarth.sphericalDistanceSun()/2);
            Sol(gl, glu, 0, 0, 0, configEarth.sphericalRaySun());
            Terra(gl, glu, 0, 0, configEarth.sphericalDistanceSun());
            //paralela(gl, glu, 0, 0, 0,696340);
            gl.glPopMatrix();
        }
}

class TB_de_pe extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
        setStep(step);
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        gl.glScalef(this.size, this.size, this.size);
        gl.glRotatef(this.mry , 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.mrx , 0.0f, 0.0f, 1.0f);
        gl.glTranslatef(mtx,mty,mtz);
        float s = 0.0005f;
        gl.glScalef(s, s, s);
        if (this.scroll > 0) {
            this.size = this.scroll+0.1f;
        } else {
            this.size = 1 / (-this.scroll/10);
        }
        //luz(gl, glu, 0, 0, -10000);
        Sol(gl, glu, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun());
        Solo(gl, glu, 0, 0, 0, configEarth.flatRay());
        //paralela(gl, glu, 0, 0, 0,696340);
        gl.glPopMatrix();
    }
}

class TP_de_pe extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 21.0f);
        setStep(step);
        float dens = 100;
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        gl.glRotatef(this.mry/10 , 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.mrx/10 , 0.0f, 0.0f, 1.0f);
        // gl.glTranslatef((-mtx + this.width / 2) / 100, (mty - this.height / 2) / 100, mtz/100);
        gl.glTranslatef(-mtx/dens,-mty/dens,mtz/dens);

        this.updateScrolltoSize(gl);
        Sol(gl, glu, 0, 0, configEarth.flatHeightSun(), configEarth.flatRaySun());
        Solo(gl, glu, 0, 0, 0, configEarth.flatRay());
        triangulo(gl, glu, 0, 0, 0,0,0, configEarth.flatHeightSun(), configEarth.flatRaySun());
        gl.glPopMatrix();
    }
}

class TB_ver_globo_fora extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step) {
        setStep(step);
        gl.glPushMatrix();
            gl.glTranslatef(less(mtx), less(mty), less(mtz));
            this.updateScrolltoSize(gl);
            gl.glRotatef(this.mry, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(this.mrx, 0.0f, 1.0f, 0.0f);
            paralela(gl, glu,0,0,0, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun(), 10000);
            gl.glPushMatrix();
                gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
                TBterraTexture(gl, glu, 0, 0, 0);
            gl.glPopMatrix();
            Sol(gl, glu, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun());
        gl.glPopMatrix();
    }
}

class TB_ver_globo_dentro extends AniCrepusculares {
    public void lines(GL2 gl, GLU glu, float step) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        setStep(step);
        gl.glPushMatrix();
        gl.glTranslatef(less(mtx), less(mty), less(mtz));
        this.updateScrolltoSize(gl);
        gl.glRotatef(this.mry, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.mrx, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0, configEarth.sphericalRay(), 0);
        gl.glPushMatrix();
        gl.glRotatef(45, 1.0f, 0.0f, 0.0f);
        TBterraTexture(gl, glu, 0, 0, 0);
        gl.glPopMatrix();
            gl.glPushMatrix();
                gl.glRotatef(this.step-10, 1.0f, 0.0f, 0.0f);
                Sol(gl, glu, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRaySun());
                paralela(gl, glu,0, 0,0, 0, 0, configEarth.sphericalDistanceSun(), configEarth.sphericalRay(), 30);
            gl.glPopMatrix();
            gl.glPushMatrix();
                gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
                TBnuvem(gl, glu, 0, 0,  0,   configEarth.sphericalRay(), configEarth.heightCloud(), 30);
             gl.glPopMatrix();
        gl.glPopMatrix();
    }
}

class TP_ver_plana_fora extends AniCrepusculares {
    int option = 0;
    public TP_ver_plana_fora(int option) {
        this.option = option;
    }
    public void lines(GL2 gl, GLU glu, float step) {
        setStep(step);
        gl.glPushMatrix();
            gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            gl.glTranslatef(less(mtx), less(mty), less(mtz));
            gl.glRotatef(this.mry, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(this.mrx, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(0,0,0);
            TPterraTexture(gl, glu, 0, 0, 0);
            TPcloud(gl, glu, 0, 0, 8, 2000);
            gl.glPushMatrix();
                gl.glRotatef(step, 0.0f, 0.0f, 1.0f);
                Sol(gl, glu, -TPray/2, 0.0f, configEarth.flatHeightSun(), configEarth.flatRaySun());
                if(this.option == 2)
                    TPsunFollowFoot(gl,glu, -TPray/2,0,configEarth.flatHeightSun(),TPray/2, 500);
            gl.glPopMatrix();
            gl.glPushMatrix();
                gl.glRotatef(this.stepLua, 0.0f, 0.0f, 1.0f);
                Lua(gl, glu, TPray/2, 0.0f, configEarth.flatHeightMoon(), configEarth.flatRayMoon());
            gl.glPopMatrix();
            if(this.option == 1)
                TPsunFollow(gl,glu, 0,0,0,-TPray/2,0,configEarth.flatHeightSun(),TPray, 2000);
        gl.glPopMatrix();
    }
}

class TP_ver_plana_dentro extends AniCrepusculares {
    int option = 0;
    public TP_ver_plana_dentro(int option) {
        this.option = option;
    }
    public void lines(GL2 gl, GLU glu, float step) {
        setStep(step);
        gl.glPushMatrix();
            gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            gl.glTranslatef(0, 0, 0);
            gl.glRotatef(this.mry, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(this.mrx, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(less(mtx), less(mty), less(mtz));
            TPterraTexture(gl, glu, 0, 0, 0);
            TPcloud(gl, glu, 0, 0, 8, 2000);
            gl.glPushMatrix();
                gl.glRotatef(step, 0.0f, 0.0f, 1.0f);
                Sol(gl, glu, -TPray/2, 0.0f, configEarth.flatHeightSun(), configEarth.flatRaySun());
                if(this.option == 2)
                    TPsunFollowFoot(gl,glu, -TPray/2,0,configEarth.flatHeightSun(),TPray/2, 500);
            gl.glPopMatrix();
            gl.glPushMatrix();
                gl.glRotatef(this.stepLua, 0.0f, 0.0f, 1.0f);
                Lua(gl, glu, TPray/2, 0.0f, configEarth.flatHeightSun(), configEarth.flatRaySun());
            gl.glPopMatrix();
            if(this.option == 1)
                TPsunFollow(gl,glu, 0,0,0,-TPray/2,0,configEarth.flatHeightSun(),TPray, 500);
        gl.glPopMatrix();
    }
}
