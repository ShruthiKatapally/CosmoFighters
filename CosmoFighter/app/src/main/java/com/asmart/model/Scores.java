package com.asmart.model;

public class Scores {
    private int playerId;
    private int score;

    public Scores() {}

    public Scores(int playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
