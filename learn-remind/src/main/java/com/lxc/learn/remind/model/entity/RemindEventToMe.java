package com.lxc.learn.remind.model.entity;

import java.util.Date;

public class RemindEventToMe {
    private Long fId;

    private String fRemindIntervalType;

    private String fEmailTemplateCode;

    private String fRemindDate;

    private String fEmailTo;

    private String fEventName;

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

    public String getfEmailTemplateCode() {
        return fEmailTemplateCode;
    }

    public void setfEmailTemplateCode(String fEmailTemplateCode) {
        this.fEmailTemplateCode = fEmailTemplateCode == null ? null : fEmailTemplateCode.trim();
    }

    public String getfRemindDate() {
        return fRemindDate;
    }

    public void setfRemindDate(String fRemindDate) {
        this.fRemindDate = fRemindDate == null ? null : fRemindDate.trim();
    }

    public String getfEmailTo() {
        return fEmailTo;
    }

    public void setfEmailTo(String fEmailTo) {
        this.fEmailTo = fEmailTo == null ? null : fEmailTo.trim();
    }

    public String getfEventName() {
        return fEventName;
    }

    public void setfEventName(String fEventName) {
        this.fEventName = fEventName == null ? null : fEventName.trim();
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

    public String getfRemindIntervalType() {
        return fRemindIntervalType;
    }

    public void setfRemindIntervalType(String fRemindIntervalType) {
        this.fRemindIntervalType = fRemindIntervalType;
    }
}