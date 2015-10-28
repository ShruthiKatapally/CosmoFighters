package com.asmart.model;

public class Player {
    private int playerId;
    private String playerName;

    public Player() {}

    public Player(int playerId, String name) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
