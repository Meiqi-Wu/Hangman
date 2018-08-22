package hangman;

public interface HangmanConstants {
	/* The name of lexicon file*/
	public static final String LEXICON_NAME = "HangmanLexicon.txt";
	/* The name of shorter lexicon file. */ 
	public static final String SHORT_LEXICON_NAME = "ShorterLexicon.txt";
	/* The name of the background image. */ 
	public static final String BKGND_NAME = "background.jpg";
	/* The name of the karel image. */
	public static final String KAREL_NAME = "karel.png";
	/* The name of the flipped karel image. */
	public static final String KAREL_FLIPPED_NAME = "karelFlipped.png";
	/* The name of the parachute image. */
	public static final String PARACHUTE_NAME = "parachute.png";
	/* The number of guesses in one game of Hangman */
	public static final int N_GUESSES = 7;
	/* The width of window. */
	public static final int CANVAS_WIDTH = 400;
	/* The height of window. */
	public static final int CANVAS_HEIGHT = 600;
	/* The width and the height to make the karel image */
	public static final int KAREL_SIZE = 150;
	/* The y-location to display karel */
	public static final int KAREL_Y = 230;
	/* The width and the height to make the parachute image */
	public static final int PARACHUTE_WIDTH = 300;
	public static final int PARACHUTE_HEIGHT = 130;
	/* The y-location to display the parachute */
	public static final int PARACHUTE_Y = 50;
	/* The y-location to display the partially guessed string */
	public static final int PARTIALLY_GUESSED_Y = 430;
	/* The y-location to display the incorrectly guessed letters */
	public static final int INCORRECT_GUESSES_Y = 460;
	/* The fonts of both labels */
	public static final String PARTIALLY_GUESSED_FONT = "Courier-36";
	public static final String INCORRECT_GUESSES_FONT = "Courier-26";
	/* The seperation of each lines. */
	public static final int LINE_SEP = 30;
}
