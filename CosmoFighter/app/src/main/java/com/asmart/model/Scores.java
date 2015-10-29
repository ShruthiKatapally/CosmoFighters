package com.asmart.model;

public class Scores {
    private int playerId;
    private int score;
    private int packageId;
    private int levelId;

    public Scores() {}

    public Scores(int playerId, int score, int packageId, int levelId) {
        this.playerId = playerId;
        this.score = score;
        this.packageId = packageId;
        this.levelId = levelId;
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

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
}
