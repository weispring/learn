package com.lxc.learn.remind.model.entity;

import java.util.Date;

public class RemindUser {
    private Long fId;

    private String fBirthDay;

    private String fQqNumber;

    private String fNickName;

    private Date fSysCreatedTime;

    private Date fSysUpdatedTime;

    private Date fSysDeleteTime;

    private Long fSysCreateBy;

    private Long fSysUpdateBy;

    private Long fSysDeleteBy;

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public String getfBirthDay() {
        return fBirthDay;
    }

    public void setfBirthDay(String fBirthDay) {
        this.fBirthDay = fBirthDay == null ? null : fBirthDay.trim();
    }

    public String getfQqNumber() {
        return fQqNumber;
    }

    public void setfQqNumber(String fQqNumber) {
        this.fQqNumber = fQqNumber == null ? null : fQqNumber.trim();
    }

    public String getfNickName() {
        return fNickName;
    }

    public void setfNickName(String fNickName) {
        this.fNickName = fNickName == null ? null : fNickName.trim();
    }

    public Date getfSysCreatedTime() {
        return fSysCreatedTime;
    }

    public void setfSysCreatedTime(Date fSysCreatedTime) {
        this.fSysCreatedTime = fSysCreatedTime;
    }

    public Date getfSysUpdatedTime() {
        return fSysUpdatedTime;
    }

    public void setfSysUpdatedTime(Date fSysUpdatedTime) {
        this.fSysUpdatedTime = fSysUpdatedTime;
    }

    public Date getfSysDeleteTime() {
        return fSysDeleteTime;
    }

    public void setfSysDeleteTime(Date fSysDeleteTime) {
        this.fSysDeleteTime = fSysDeleteTime;
    }

    public Long getfSysCreateBy() {
        return fSysCreateBy;
    }

    public void setfSysCreateBy(Long fSysCreateBy) {
        this.fSysCreateBy = fSysCreateBy;
    }

    public Long getfSysUpdateBy() {
        return fSysUpdateBy;
    }

    public void setfSysUpdateBy(Long fSysUpdateBy) {
        this.fSysUpdateBy = fSysUpdateBy;
    }

    public Long getfSysDeleteBy() {
        return fSysDeleteBy;
    }

    public void setfSysDeleteBy(Long fSysDeleteBy) {
        this.fSysDeleteBy = fSysDeleteBy;
    }
}