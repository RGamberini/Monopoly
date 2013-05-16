import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
 
public class Test1 {
 
    public void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(800,600));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
 
	// init OpenGL
	
	Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	UnicodeFont font = null;
	try {
		font = new UnicodeFont(awtFont);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect());  // Create a default white color effect
		font.loadGlyphs();
	} catch (SlickException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	while (!Display.isCloseRequested()) {
	    // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		
	    // set the color of the quad (R,G,B,A)
	    GL11.glColor3f(0.5f,0.5f,1.0f);
	    	
	    // draw quad
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(100,100);
		GL11.glVertex2f(100+200,100);
		GL11.glVertex2f(100+200,100+200);
		GL11.glVertex2f(100,100+200);
	    GL11.glEnd();
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    
	    font.drawString(100, 100, "LAAAAWL");
 
	    Display.update();
	}
 
	Display.destroy();
    }
 
    public static void main(String[] argv) {
        Test1 quadExample = new Test1();
        quadExample.start();
    }
}