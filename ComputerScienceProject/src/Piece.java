import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

public class Piece {
	int textureID, length, width;
	int pokemon;
	public Piece(int Pokemon) {
		pokemon = Pokemon;
	}
	
	public void init() {
		BufferedImage test = null;
		System.out.println("" + 5 * pokemon % 10 + " " + (int) (5 * (Math.floor(pokemon / 10) + 1)));
		pokemon = 2 *pokemon - 1;
		int pokemonX = (pokemon - 1) % 10, pokemonY = (pokemon - pokemon % 10) / 10;
		try {
			test = ImageIO.read(new File("KantoPokemon.png")).getSubimage(5 + pokemonX * 69, 5 + pokemonY * 69, 64, 64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		length = test.getHeight();
		width = test.getWidth();
		//test.
	    textureID = loadTexture(test);
	}
	int xbound = 500, ybound = 800;
	public void draw() {
		GL11.glColor3f(1f, 1f, 1f);
		glPushMatrix();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glBegin(GL_QUADS);
        {
           glTexCoord2f(0, 0);
           glVertex2f(0, 0);
           
           glTexCoord2f(1, 0);
           glVertex2f(width,0);
           
           glTexCoord2f(1, 1);
           glVertex2f(width, length);
           
           glTexCoord2f(0, 1);
           glVertex2f(0, length);
        }
        glEnd();
        glPopMatrix();
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
