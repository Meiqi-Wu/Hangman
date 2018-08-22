package hangman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.OverlayLayout;

public class Hangman extends JFrame implements HangmanConstants{
	public ArrayList<String> wordlist;
	public String secretWord;
	public String wordState;
	public String guesslist;
	public ArrayList<GLine> strings;
	private BufferedImage bkgndImage;
	private BufferedImage parachuteImage;
	private BufferedImage karelImage;
	private BufferedImage karelFlippedImage;
	private JComponent bkgnd;
	private JComponent parachute;
	private JComponent karel;
	private JComponent karelFlipped;
	private GLabel guessLabel;
	private GLabel stateLabel;
	
	public static void main(String[] args) {
		Hangman program = new Hangman();
		program.run();
	}
	
	public Hangman() {
		this.setTitle("CS106A Hangman");
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.setLayout(new OverlayLayout(this.getContentPane()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		this.wordlist = new ArrayList<String>();
		this.strings = new ArrayList<GLine>();
		loadWords();
		chooseSecretWord();
		initWordState(this.secretWord.length());
		this.guesslist = "";
		
		initWindow();
	}
	
	private void initWindow() {
		/* Add the parachute image. */
		try {
			this.parachuteImage = ImageIO.read(new File(PARACHUTE_NAME));
		} catch(IOException e) {
			println("Cannot load background image :( ");
		}
		parachute = new JComponent() {
			public void paintComponent(Graphics g) {
				Graphics g2 = g.create();
				super.paintComponent(g2);
				g2.drawImage(parachuteImage, (CANVAS_WIDTH-PARACHUTE_WIDTH)/2, PARACHUTE_Y, 
						PARACHUTE_WIDTH, PARACHUTE_HEIGHT, null);
				g2.dispose();
			}
		};
		this.getContentPane().add(parachute);
		/* Add the karel image. */
		try {
			this.karelImage = ImageIO.read(new File(KAREL_NAME));
		} catch(IOException e) {
			println("Cannot load background image :( ");
		}
		karel = new JComponent() {
			public void paintComponent(Graphics g) {
				Graphics g2 = g.create();
				super.paintComponent(g2);
				g2.drawImage(karelImage, (CANVAS_WIDTH-KAREL_SIZE)/2, KAREL_Y, KAREL_SIZE, KAREL_SIZE, null);
				g2.dispose();
			}
		};
		this.getContentPane().add(karel);
		
		/* Add the strings holding the karel. */
		for(int i = 0; i < N_GUESSES; i++) {
			GLine string = new GLine(CANVAS_WIDTH/2-3*LINE_SEP+i*LINE_SEP, PARACHUTE_Y+PARACHUTE_HEIGHT, CANVAS_WIDTH/2, KAREL_Y);
			this.strings.add(string);
			this.getContentPane().add(string);
		}
		/* Add the label of incorrect guess history and word state. */
		this.guessLabel = new GLabel(this.guesslist);
		this.stateLabel = new GLabel(this.wordState);
		this.stateLabel.setLocation(CANVAS_WIDTH/2, PARTIALLY_GUESSED_Y);
		this.guessLabel.setLocation(CANVAS_WIDTH/2, INCORRECT_GUESSES_Y);
		this.stateLabel.setFont(new Font("TimesRoman", Font.BOLD, 36));
		this.guessLabel.setFont(new Font("Courier", Font.PLAIN, 36));
		this.getContentPane().add(this.guessLabel);
		this.getContentPane().add(this.stateLabel);

//		JLabel bkgnd = new JLabel();
//		bkgnd.setIcon(new ImageIcon(BKGND_NAME));
//		this.getContentPane().add(bkgnd);
		/* Add the background image. */
		try {
			this.bkgndImage = ImageIO.read(new File(BKGND_NAME));
		} catch(IOException e) {
			println("Cannot load background image :( ");
		}
		bkgnd = new JComponent() {
			public void paintComponent(Graphics g) {
				Graphics g2 = g.create();
				super.paintComponent(g2);
				g2.drawImage(bkgndImage, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT, null);
				g2.dispose();
			}
		};
		this.getContentPane().add(bkgnd);

		this.revalidate();
		this.repaint();
	}
	
	public void run() {
		println("Welcome to Hangman ");	
		println("" + this.secretWord);
		Scanner sc = new Scanner(System.in);
		for(int i = N_GUESSES; i > 0; i--) {
			println("Your word now looks like: " + wordState);
			println("You have " + i + " guesses left. ");
			System.out.print("Your guess: ");
			String line = sc.nextLine();
			if(isOneLetter(line)) {
				char guess = Character.toUpperCase(line.charAt(0));
				if(this.secretWord.indexOf(guess) > -1) {
					updateWordState(guess);
					i += 1;
					
				} else {
					println("There is no " + guess + "'s in the word");
					this.guesslist += guess;
					GLine string = this.strings.get(i-1);
					this.getContentPane().remove(string);
				}
			} else {
				println("Wrong entering. ");
				GLine string = this.strings.get(i-1);
				this.getContentPane().remove(string);
			}
			if(this.wordState.indexOf('-') < 0) {
				println("You win. ");
				println("The word was "+ this.secretWord);
				this.stateLabel.setText(this.wordState);
				this.guessLabel.setText(this.guesslist);
				this.revalidate();
				this.repaint();
				break;
			}	
			this.stateLabel.setText(this.wordState);
			this.guessLabel.setText(this.guesslist);
			this.revalidate();
			this.repaint();
		}
		sc.close();
		if(this.wordState.indexOf('-') > -1) {
			println("You are completely hang. ");
			println("The word was "+ this.secretWord);
			
			this.getContentPane().remove(karel);

			try {
				this.karelFlippedImage = ImageIO.read(new File(KAREL_FLIPPED_NAME));
			} catch(IOException e) {
				println("Cannot load background image :( ");
			}
			karelFlipped = new JComponent() {
				public void paintComponent(Graphics g) {
					Graphics g2 = g.create();
					super.paintComponent(g2);
					g2.drawImage(karelFlippedImage, (CANVAS_WIDTH-KAREL_SIZE)/2, KAREL_Y, KAREL_SIZE, KAREL_SIZE, null);
					g2.dispose();
				}
			};
			this.getContentPane().add(karelFlipped);
			
//			try {
//				this.bkgndImage = ImageIO.read(new File(BKGND_NAME));
//			} catch(IOException e) {
//				println("Cannot load background image :( ");
//			}
//			bkgnd = new JComponent() {
//				public void paintComponent(Graphics g) {
//					Graphics g2 = g.create();
//					super.paintComponent(g2);
//					g2.drawImage(bkgndImage, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT, null);
//					g2.dispose();
//				}
//			};
			this.getContentPane().remove(bkgnd);
			this.getContentPane().add(bkgnd);

			this.revalidate();
			this.repaint();
		}
	}
	
	private void updateWordState(char guess) {
		String newWordState = "";
		for(int i=0; i<this.secretWord.length();i++) {
			if(guess == this.secretWord.charAt(i)) {
				newWordState += guess;
			} else if(this.wordState.charAt(i) != '-'){
				newWordState += this.wordState.charAt(i);
			} else {
				newWordState += '-';
			}
		}
		this.wordState = new String(newWordState);
	}
	
	private void chooseSecretWord() {
		Random rand = new Random();
		int wordidx = rand.nextInt(this.wordlist.size());
		this.secretWord = this.wordlist.get(wordidx).toUpperCase();
	}
	
	private boolean isOneLetter(String str) {
		if(str.length() != 1) return false;
		if(Character.isLetter(str.charAt(0))) return true;
		return false;
	}
	
	private void initWordState(int num) {
		this.wordState = "";
		for(int i=0; i<num; i++) {
			wordState += "-";
		}
	}
	
	public void loadWords() {
		try {
			Scanner sc = new Scanner(new File(SHORT_LEXICON_NAME));
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				this.wordlist.add(str);
			}
			sc.close();
		}catch(FileNotFoundException e) {
			println("Can not find the lexicon file :( ");
		}
	}
	
	private void println(String str) {
		System.out.println(str);
	}	
}
