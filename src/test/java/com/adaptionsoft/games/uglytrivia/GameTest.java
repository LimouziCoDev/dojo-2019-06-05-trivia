package com.adaptionsoft.games.uglytrivia;

import org.junit.Before;
import org.junit.Test;

import javax.swing.text.PlainDocument;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        player1 = new Player("Player One");
        player2 = new Player("Player Two");
        game.add(player1);
        game.add(player2);
    }


    @Test
    public void GoInPenaltyBoxWhenWrongAnswerAndSwitchToNextPlayer() {
        game.wrongAnswer();
        assertTrue(player1.isInPenaltyBox);
        assertEquals(1, game.playerIndex);
    }

    @Test
    public void doIWinWith6Coins() {
        player1.purse = 5;
        boolean notAWinner = game.wasCorrectlyAnswered();
        assertFalse(notAWinner);
    }

    @Test
    public void switchToNextPlayerWhenCorrectAnswerAndGettingOutOfPenaltyBox() {
        player1.isInPenaltyBox = true;
        game.isGettingOutOfPenaltyBox = true;
        game.wasCorrectlyAnswered();
        assertEquals(1, game.playerIndex);
    }

    @Test
    public void switchToNextPlayerWhenCorrectAnswerAndNotGettingOutOfPenaltyBox() {
        player1.isInPenaltyBox = true;
        game.isGettingOutOfPenaltyBox = false;
        game.wasCorrectlyAnswered();
        assertEquals(1, game.playerIndex);
    }

    @Test
    public void goesOutOfPenaltyBoxAndMoveForwardWhenRollingOdd() {
        player1.isInPenaltyBox = true;
        game.roll(1);
        assertTrue(game.isGettingOutOfPenaltyBox);
        assertEquals(1, player1.place);
    }

    @Test
    public void stayInPenaltyBoxAndDoesntMoveForwardWhenRollingEven() {
        player1.isInPenaltyBox = true;
        game.roll(2);
        assertFalse(game.isGettingOutOfPenaltyBox);
        assertEquals(0, player1.place);
    }

    @Test
    public void MoveForwardWhenNotInPenaltyBoxandRoll() {
        player1.isInPenaltyBox = false;
        game.roll(2);
        assertEquals(2, player1.place);
    }

    @Test
    public void MoveToOneWhenIRollTwoAt11() {

        player1.place = 11;
        game.roll(2);
        assertEquals(1, player1.place);
    }
}