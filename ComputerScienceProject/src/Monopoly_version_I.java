import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 
public class Monopoly_version_I {
 
    public static void start() {
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
    GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	
	//Tile firstTile = new Tile(100, 100, 150f, 100f, new float[] {1, 0, 0}, new float[] {.54f, .27f, .75f}, "fuck");

	Board Monopoly = new Board();
	Monopoly.init();
 
	while (!Display.isCloseRequested()) {
	    // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	    
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    //firstTile.draw();
	    
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    //firstTile.drawName(font);
	    Monopoly.draw();
	    Display.update();
	}
 
	Display.destroy();
    }
 
    public static void main(String[] argv) {
    	String BoardinfoPath = Monopoly_version_I.class.getClassLoader().getResource("Boardinfo").getPath();
    	String BoardLayInfoPath = Monopoly_version_I.class.getClassLoader().getResource("BoardLayInfo").getPath();
        start();
    }
}