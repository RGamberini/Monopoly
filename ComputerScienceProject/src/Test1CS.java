import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Test1CS {
 
    public void start() {
    	try {
    	    Display.setDisplayMode(new DisplayMode(800,600));
    	    Display.create();
    	} catch (LWJGLException e) {
    	    e.printStackTrace();
    	    System.exit(0);
    	}
     
    	// init OpenGL
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(0, 800, 0, 600, 1, -1);
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	Texture texture = null;
    	try {
    		InputStream In = new FileInputStream("C:\\Users\\rudy.gamberini\\Pictures\\Aaron.png");
    		texture = TextureLoader.getTexture("PNG", In);
    	        // Replace PNG with your file extension
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		Display.destroy();
    		System.exit(1);
    	} catch (IOException e) {
    		e.printStackTrace();
    		Display.destroy();
    		System.exit(1);
    	}
     
    	while (!Display.isCloseRequested()) {
    	    // Clear the screen and depth buffer
    	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    	    GL11.glBegin(GL11.GL_TRIANGLES);
    	    
    	    GL11.glTexCoord2f(0.9f, 0);
    	    GL11.glVertex2i(texture.getTextureWidth(), 10 + texture.getTextureHeight());
    	    GL11.glTexCoord2f(0, 0);
    	    GL11.glVertex2i(10 + texture.getTextureWidth(), 10 + texture.getTextureHeight());
    	    GL11.glTexCoord2f(0, 0.9f);
    	    GL11.glVertex2i(texture.getTextureWidth(), 450 + texture.getTextureHeight());
    	     
    	    GL11.glTexCoord2f(0, 0.9f);
    	    GL11.glVertex2i(10 + texture.getTextureWidth(), 450 + texture.getTextureHeight());
    	    GL11.glTexCoord2f(0.9f, 0.9f);
    	    GL11.glVertex2i(450 + texture.getTextureWidth(), 450 + texture.getTextureHeight());
    	    GL11.glTexCoord2f(0.9f, 0);
    	    GL11.glVertex2i(450 + texture.getTextureWidth(), 10 + texture.getTextureHeight());
    	     
    	    GL11.glEnd();
    	    // set the color of the quad (R,G,B,A)
     
    	    Display.update();
    	}
     
    	Display.destroy();

    	
    }
 
    public static void main(String[] argv) {
        Test1CS quadExample = new Test1CS();
        quadExample.start();
    }
}