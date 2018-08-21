package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangmanConsole implements HangmanConstants{
	public ArrayList<String> wordlist;
	public String secretWord;
	public String wordState;
	
	public static void main(String[] args) {
		HangmanConsole program = new HangmanConsole();
		program.run();
	}
	
	public HangmanConsole() {
		this.wordlist = new ArrayList<String>();
		loadWords();
	}
	
	public void run() {
		println("Welcome to Hangman ");
		chooseSecretWord();
		initWordState(this.secretWord.length());
//		println("" + this.secretWord);
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
				}
			} else {
				println("Wrong entering. ");
			}
			if(this.wordState.indexOf('-') < 0) {
				println("You win. ");
				println("The word was "+ this.secretWord);
				break;
			}
			
		}
		sc.close();
		if(this.wordState.indexOf('-') > -1) {
			println("You are completely hang. ");
			println("The word was "+ this.secretWord);
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
