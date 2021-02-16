package com.wzh.fruitgo.Bean;

public class Mission {
    private Long id;
    private Long userId;
    private String missionName;
    private String missionDuration;
    private Integer compNum;

    public Mission() {
    }

    public Mission(String name, String duration){
        this.missionName =name;
        this.missionDuration =duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionDuration() {
        return missionDuration;
    }

    public void setMissionDuration(String missionDuration) {
        this.missionDuration = missionDuration;
    }

    public Integer getCompNum() {
        return compNum;
    }

    public void setCompNum(Integer compNum) {
        this.compNum = compNum;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", userId=" + userId +
                ", missionName='" + missionName + '\'' +
                ", missionDuration='" + missionDuration + '\'' +
                ", compNum=" + compNum +
                '}';
    }
}
