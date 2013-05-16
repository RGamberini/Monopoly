import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class Board {
	String[][][] bob = new String[8][3][2];
	Tile firstTile;
	UnicodeFont font = null;
	String BoardinfoPath = Board.class.getClassLoader().getResource("Boardinfo").getPath();
	String BoardLayInfoPath = Board.class.getClassLoader().getResource("BoardLayInfo").getPath();
	
	private void setupFonts() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		try {
			font = new UnicodeFont(awtFont);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());  // Create a default white color effect
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupArrayBoard() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(BoardinfoPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text;
		String[] neo = new String[4];
		try {
			while (in.ready()) { 
				text = in.readLine();
				neo = text.split(",");
				bob[Integer.parseInt(neo[0])][Integer.parseInt(neo[1])][0] = neo[2];
				bob[Integer.parseInt(neo[0])][Integer.parseInt(neo[1])][1] = neo[3];
			}
			in.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupBoard() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(BoardLayInfoPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text, twoChar;
		try {
			while (in.ready()) {
				text = in.readLine();
				for (int i = 0; i < text.length(); i += 2) {
					twoChar = "" + text.charAt(i) + text.charAt(i + 1);
					if (!twoChar.equals("--")) {
						
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init() {
		setupFonts();
		setupArrayBoard();
		setupBoard();
		firstTile = new Tile(100, 100, 150f, 100f, new float[] {1, 0, 0}, new float[] {.54f, .27f, .75f}, "fuck");
	}
	
	public void draw() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		firstTile.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		firstTile.drawName(font);
	}

}