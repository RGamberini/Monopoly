import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class Board {
	static int NAME = 0;
	static int COLOR = 1;
	String[][][] bob = new String[8][3][2];
	Tile firstTile;
	UnicodeFont font = null;
	String BoardinfoPath = Board.class.getClassLoader().getResource("Boardinfo").getPath();
	String BoardLayInfoPath = Board.class.getClassLoader().getResource("BoardLayInfo").getPath();
	float[] BGcolor, tileColor;
	float tileLength, tileWidth;
	List<Tile> tileList = new ArrayList<Tile>();
	
	public Board(float[] BGColor, float[] TileColor, float TileLength, float TileWidth) {
		BGcolor = BGColor;
		tileColor = TileColor;
		tileLength = TileLength;
		tileWidth = TileWidth;
		
	}
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
	
	private void createTile(String Name, float[] Color, float X, float Y, int Oreintation) {
		tileList.add(new Tile(X, Y, tileLength, tileWidth, tileColor, Color,  Name, Oreintation));
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
	
	private int countLines(String path) throws IOException {
		int lines = 0;
		BufferedReader br = new BufferedReader(new FileReader(path));
		while (br.readLine() != null) {
		    lines++;
		}
		br.close();
		return lines;
	}
	
	private void setupBoard() {
		BufferedReader in = null;
		String tileName;
		String[] strTileColor;
		float[] tileColor;
		try {
			in = new BufferedReader(new FileReader(BoardLayInfoPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text, twoChar;
		int num1, num2;
		int lineCount = 0, maxLines;
		int oreintation;
		float x, y;
		try {
			maxLines = countLines(BoardLayInfoPath);
			while (in.ready()) {
				text = in.readLine();
				for (int i = 0; i < text.length(); i += 2) {
					twoChar = "" + text.charAt(i) + text.charAt(i + 1);
					if (!twoChar.equals("--")) {
						num1 = Integer.parseInt("" + twoChar.charAt(0));
						num2 = Integer.parseInt("" + twoChar.charAt(1));
						if (lineCount == 0)
							oreintation = Tile.DOWN;
						else if (lineCount == maxLines)
							oreintation = Tile.UP;
						else if(i == 0)
							oreintation = Tile.RIGHT;
						else
							oreintation = Tile.LEFT;
						if (oreintation == 0 || oreintation == 1) {
							x = (i / 2) * tileWidth;
							y = lineCount * tileWidth;
						}
						else {
							x = (i / 2) * tileLength;
							y = lineCount * tileWidth;
						}
						tileName = bob[num1][num2][NAME];
						tileColor = new float[3];
						strTileColor = bob[num1][num2][COLOR].split(" ");
						for (int j = 0; j < strTileColor.length; j++)
							tileColor[j] = Float.parseFloat(strTileColor[j]);
						createTile(tileName, tileColor, x, y, oreintation);
					}
				}
				lineCount++;
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
		firstTile = new Tile(100, 100, 150f, 100f, tileColor, new float[] {.54f, .27f, .75f}, "fuck", 3);
	}
	
	public void draw() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		firstTile.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		firstTile.drawName(font);
	}
}