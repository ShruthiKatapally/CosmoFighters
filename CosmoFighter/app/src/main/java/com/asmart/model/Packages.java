package com.asmart.model;

public class Packages {
    private String packageName;
    private boolean packageUnlocked;
    private int starsCount;
    private int levelsUnlocked;

    public Packages(){}

    public Packages(String packageName, boolean packageUnlocked, int starsCount, int levelsUnlocked){
        this.packageName = packageName;
        this.packageUnlocked = packageUnlocked;
        this.starsCount = starsCount;
        this.levelsUnlocked = levelsUnlocked;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isPackageUnlocked() {
        return packageUnlocked;
    }

    public void setPackageUnlocked(boolean packageUnlocked) {
        this.packageUnlocked = packageUnlocked;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getLevelsUnlocked() {
        return levelsUnlocked;
    }

    public void setLevelsUnlocked(int levelsUnlocked) {
        this.levelsUnlocked = levelsUnlocked;
    }
}
