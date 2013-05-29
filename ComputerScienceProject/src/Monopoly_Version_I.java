import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 
public class Monopoly_Version_I {
	static int border = 5;
	
    public static void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(1600,900));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
 
	// init OpenGL
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	//Tile firstTile = new Tile(100, 100, 150f, 100f, new float[] {1, 0, 0}, new float[] {.54f, .27f, .75f}, "fuck");
    float[] BGcolor = new float[] {.63f, .45f, .27f};
	Board Monopoly = new Board(new float[] {.63f, .45f, .27f}, new float[]{.05f,  .56f, .58f}, 100 + border, 50);
	Monopoly.init();
 
	while (!Display.isCloseRequested()) {
	    // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	    
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    GL11.glColor3f(BGcolor[0], BGcolor[1], BGcolor[2]);
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(0,0);
		GL11.glVertex2f(1600,0);
		GL11.glVertex2f(1600,900);
		GL11.glVertex2f(0,900);
	    GL11.glEnd();
	    //firstTile.drawName(font);
	    Monopoly.draw();
	    Display.update();
	}
 
	Display.destroy();
    }
 
    public static void main(String[] argv) {
    	String BoardinfoPath = Monopoly_Version_I.class.getClassLoader().getResource("Boardinfo").getPath();
    	String BoardLayInfoPath = Monopoly_Version_I.class.getClassLoader().getResource("BoardLayInfo").getPath();
        start();
    }
}