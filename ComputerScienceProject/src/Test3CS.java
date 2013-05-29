import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

public class Test3CS {
   
   private static final int WIDTH = 800, HEIGHT = 600;
   
   public static void main(String[] args){
      
      try{
         Display.setDisplayMode(new DisplayMode(800, 600));
         Display.create();
      }catch(LWJGLException e){
         e.printStackTrace();
      }
      
      glMatrixMode(GL_PROJECTION);
      glOrtho(0, WIDTH, HEIGHT, 0, -1, 1); //2D projection matrix
      glMatrixMode(GL_MODELVIEW);
      
      glClearColor(0, 1, 0, 0); //Green clear color
      
      
      //Generate a small test image by drawing to a BufferedImage
      //It's of course also possible to just load an image using ImageIO.load()
    BufferedImage test = null;
	try {
		test = ImageIO.read(new File("C:\\Users\\rudy.gamberini\\Pictures\\Aaron.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    int textureID = loadTexture(test);
      
      glEnable(GL_TEXTURE_2D); //Enable texturing
      int xbound = 500, ybound = 800;
      
      
      while(!Display.isCloseRequested()){
         glClear(GL_COLOR_BUFFER_BIT);
         
         //Enable blending so the green background can be seen through the texture
         glEnable(GL_BLEND);
         glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
         
         glPushMatrix();
         //glTranslatef(100, 100, 0);
         glBindTexture(GL_TEXTURE_2D, textureID);
         glBegin(GL_QUADS);
         {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            
            glTexCoord2f(1, 0);
            glVertex2f(xbound,0);
            
            glTexCoord2f(1, 1);
            glVertex2f(xbound, ybound);
            
            glTexCoord2f(0, 1);
            glVertex2f(0, ybound);
         }
         glEnd();
         glPopMatrix();
         
         Display.update();
      }
   }
   
   private static final int BYTES_PER_PIXEL = 4;
   public static int loadTexture(BufferedImage image){
      
      int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
        
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using 
        // whatever OpenGL method you want, for example:

      int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
        
        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
      
        //Return the texture ID so we can bind it later again
      return textureID;
   }
}