package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<Player> players = new ArrayList<Player>();
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int playerIndex = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(Player player) {
		players.add(player);
	    
	    System.out.println(player.name + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		Player currentPlayer = players.get(playerIndex);
		System.out.println(currentPlayer.name + " is the current currentPlayer");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox) {
			if (roll % 2 != 0) { // impair
				isGettingOutOfPenaltyBox = true;
				System.out.println(currentPlayer.name + " is getting out of the penalty box");
				movePlayer(roll);


				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(currentPlayer.name + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayer(roll);

			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void movePlayer(int roll) {
		Player currentPlayer = players.get(playerIndex);

		currentPlayer.place+=roll;
		if (currentPlayer.place > 11)
			currentPlayer.place -= 12;
		System.out.println(currentPlayer.name
				+ "'s new location is "
				+ currentPlayer.place);
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		int currentPlayerPlace = players.get(playerIndex).place;

		if (currentPlayerPlace == 0) return "Pop";
		if (currentPlayerPlace == 4) return "Pop";
		if (currentPlayerPlace == 8) return "Pop";
		if (currentPlayerPlace == 1) return "Science";
		if (currentPlayerPlace == 5) return "Science";
		if (currentPlayerPlace == 9) return "Science";
		if (currentPlayerPlace == 2) return "Sports";
		if (currentPlayerPlace == 6) return "Sports";
		if (currentPlayerPlace == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		Player currentPlayer = players.get(playerIndex);

		boolean notAWinner;
		if (currentPlayer.isInPenaltyBox){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer.purse++;
				System.out.println(currentPlayer.name
						+ " now has "
						+ currentPlayer.purse
						+ " Gold Coins.");
				
				notAWinner = didPlayerWin();
			} else {
				notAWinner = true;
			}
		} else {
			System.out.println("Answer was correct!!!!");
			currentPlayer.purse++;
			System.out.println(currentPlayer.name
					+ " now has "
					+ currentPlayer.purse
					+ " Gold Coins.");
			
			notAWinner = didPlayerWin();
		}
		nextPlayer();
		return notAWinner;
	}

	private void nextPlayer() {
		playerIndex++;
		if (playerIndex == players.size()) playerIndex = 0;
	}

	public boolean wrongAnswer(){
		Player currentPlayer = players.get(playerIndex);

		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.name + " was sent to the penalty box");
		currentPlayer.isInPenaltyBox = true;

		nextPlayer();
		return true;
	}


	private boolean didPlayerWin() {
		Player currentPlayer = players.get(playerIndex);

		return !(currentPlayer.purse == 6);
	}
}
