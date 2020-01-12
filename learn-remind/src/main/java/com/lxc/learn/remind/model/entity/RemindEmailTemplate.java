package com.lxc.learn.remind.model.entity;

import java.util.Date;

public class RemindEmailTemplate {
    private Long fId;

    private String fEmailSubject;

    private String fEmailTemplateCode;

    private Date fSysCreatedTime;

    private Date fSysUpdatedTime;

    private Date fSysDeleteTime;

    private Long fSysCreateBy;

    private Long fSysUpdateBy;

    private Long fSysDeleteBy;

    private String fEmailTemplateContent;

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public String getfEmailSubject() {
        return fEmailSubject;
    }

    public void setfEmailSubject(String fEmailSubject) {
        this.fEmailSubject = fEmailSubject == null ? null : fEmailSubject.trim();
    }

    public String getfEmailTemplateCode() {
        return fEmailTemplateCode;
    }

    public void setfEmailTemplateCode(String fEmailTemplateCode) {
        this.fEmailTemplateCode = fEmailTemplateCode == null ? null : fEmailTemplateCode.trim();
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

    public String getfEmailTemplateContent() {
        return fEmailTemplateContent;
    }

    public void setfEmailTemplateContent(String fEmailTemplateContent) {
        this.fEmailTemplateContent = fEmailTemplateContent == null ? null : fEmailTemplateContent.trim();
    }
}