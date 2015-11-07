package com.asmart.model;

public class PkgLvl {
    private int levelId;
    private int packageId;
    private int starsCount;
    private boolean isUnlocked;

    public PkgLvl() {}

    public PkgLvl(int packageId, int levelId, int starsCount, boolean isUnlocked) {
        this.levelId = levelId;
        this.packageId = packageId;
        this.starsCount = starsCount;
        this.isUnlocked = isUnlocked;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setIsUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }
}
