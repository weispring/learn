package com.lxc.learn.remind.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemindEmailTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RemindEmailTemplateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFIdIsNull() {
            addCriterion("f_id is null");
            return (Criteria) this;
        }

        public Criteria andFIdIsNotNull() {
            addCriterion("f_id is not null");
            return (Criteria) this;
        }

        public Criteria andFIdEqualTo(Long value) {
            addCriterion("f_id =", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotEqualTo(Long value) {
            addCriterion("f_id <>", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdGreaterThan(Long value) {
            addCriterion("f_id >", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdGreaterThanOrEqualTo(Long value) {
            addCriterion("f_id >=", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdLessThan(Long value) {
            addCriterion("f_id <", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdLessThanOrEqualTo(Long value) {
            addCriterion("f_id <=", value, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdIn(List<Long> values) {
            addCriterion("f_id in", values, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotIn(List<Long> values) {
            addCriterion("f_id not in", values, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdBetween(Long value1, Long value2) {
            addCriterion("f_id between", value1, value2, "fId");
            return (Criteria) this;
        }

        public Criteria andFIdNotBetween(Long value1, Long value2) {
            addCriterion("f_id not between", value1, value2, "fId");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectIsNull() {
            addCriterion("f_email_subject is null");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectIsNotNull() {
            addCriterion("f_email_subject is not null");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectEqualTo(String value) {
            addCriterion("f_email_subject =", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectNotEqualTo(String value) {
            addCriterion("f_email_subject <>", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectGreaterThan(String value) {
            addCriterion("f_email_subject >", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("f_email_subject >=", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectLessThan(String value) {
            addCriterion("f_email_subject <", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectLessThanOrEqualTo(String value) {
            addCriterion("f_email_subject <=", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectLike(String value) {
            addCriterion("f_email_subject like", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectNotLike(String value) {
            addCriterion("f_email_subject not like", value, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectIn(List<String> values) {
            addCriterion("f_email_subject in", values, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectNotIn(List<String> values) {
            addCriterion("f_email_subject not in", values, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectBetween(String value1, String value2) {
            addCriterion("f_email_subject between", value1, value2, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailSubjectNotBetween(String value1, String value2) {
            addCriterion("f_email_subject not between", value1, value2, "fEmailSubject");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeIsNull() {
            addCriterion("f_email_template_code is null");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeIsNotNull() {
            addCriterion("f_email_template_code is not null");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeEqualTo(String value) {
            addCriterion("f_email_template_code =", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeNotEqualTo(String value) {
            addCriterion("f_email_template_code <>", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeGreaterThan(String value) {
            addCriterion("f_email_template_code >", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("f_email_template_code >=", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeLessThan(String value) {
            addCriterion("f_email_template_code <", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeLessThanOrEqualTo(String value) {
            addCriterion("f_email_template_code <=", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeLike(String value) {
            addCriterion("f_email_template_code like", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeNotLike(String value) {
            addCriterion("f_email_template_code not like", value, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeIn(List<String> values) {
            addCriterion("f_email_template_code in", values, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeNotIn(List<String> values) {
            addCriterion("f_email_template_code not in", values, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeBetween(String value1, String value2) {
            addCriterion("f_email_template_code between", value1, value2, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFEmailTemplateCodeNotBetween(String value1, String value2) {
            addCriterion("f_email_template_code not between", value1, value2, "fEmailTemplateCode");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeIsNull() {
            addCriterion("f_sys_created_time is null");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeIsNotNull() {
            addCriterion("f_sys_created_time is not null");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeEqualTo(Date value) {
            addCriterion("f_sys_created_time =", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeNotEqualTo(Date value) {
            addCriterion("f_sys_created_time <>", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeGreaterThan(Date value) {
            addCriterion("f_sys_created_time >", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("f_sys_created_time >=", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeLessThan(Date value) {
            addCriterion("f_sys_created_time <", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("f_sys_created_time <=", value, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeIn(List<Date> values) {
            addCriterion("f_sys_created_time in", values, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeNotIn(List<Date> values) {
            addCriterion("f_sys_created_time not in", values, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("f_sys_created_time between", value1, value2, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("f_sys_created_time not between", value1, value2, "fSysCreatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeIsNull() {
            addCriterion("f_sys_updated_time is null");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeIsNotNull() {
            addCriterion("f_sys_updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeEqualTo(Date value) {
            addCriterion("f_sys_updated_time =", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeNotEqualTo(Date value) {
            addCriterion("f_sys_updated_time <>", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeGreaterThan(Date value) {
            addCriterion("f_sys_updated_time >", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("f_sys_updated_time >=", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeLessThan(Date value) {
            addCriterion("f_sys_updated_time <", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("f_sys_updated_time <=", value, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeIn(List<Date> values) {
            addCriterion("f_sys_updated_time in", values, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeNotIn(List<Date> values) {
            addCriterion("f_sys_updated_time not in", values, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("f_sys_updated_time between", value1, value2, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("f_sys_updated_time not between", value1, value2, "fSysUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeIsNull() {
            addCriterion("f_sys_delete_time is null");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeIsNotNull() {
            addCriterion("f_sys_delete_time is not null");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeEqualTo(Date value) {
            addCriterion("f_sys_delete_time =", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeNotEqualTo(Date value) {
            addCriterion("f_sys_delete_time <>", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeGreaterThan(Date value) {
            addCriterion("f_sys_delete_time >", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("f_sys_delete_time >=", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeLessThan(Date value) {
            addCriterion("f_sys_delete_time <", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("f_sys_delete_time <=", value, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeIn(List<Date> values) {
            addCriterion("f_sys_delete_time in", values, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeNotIn(List<Date> values) {
            addCriterion("f_sys_delete_time not in", values, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeBetween(Date value1, Date value2) {
            addCriterion("f_sys_delete_time between", value1, value2, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("f_sys_delete_time not between", value1, value2, "fSysDeleteTime");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByIsNull() {
            addCriterion("f_sys_create_by is null");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByIsNotNull() {
            addCriterion("f_sys_create_by is not null");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByEqualTo(Long value) {
            addCriterion("f_sys_create_by =", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByNotEqualTo(Long value) {
            addCriterion("f_sys_create_by <>", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByGreaterThan(Long value) {
            addCriterion("f_sys_create_by >", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("f_sys_create_by >=", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByLessThan(Long value) {
            addCriterion("f_sys_create_by <", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByLessThanOrEqualTo(Long value) {
            addCriterion("f_sys_create_by <=", value, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByIn(List<Long> values) {
            addCriterion("f_sys_create_by in", values, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByNotIn(List<Long> values) {
            addCriterion("f_sys_create_by not in", values, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByBetween(Long value1, Long value2) {
            addCriterion("f_sys_create_by between", value1, value2, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysCreateByNotBetween(Long value1, Long value2) {
            addCriterion("f_sys_create_by not between", value1, value2, "fSysCreateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByIsNull() {
            addCriterion("f_sys_update_by is null");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByIsNotNull() {
            addCriterion("f_sys_update_by is not null");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByEqualTo(Long value) {
            addCriterion("f_sys_update_by =", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByNotEqualTo(Long value) {
            addCriterion("f_sys_update_by <>", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByGreaterThan(Long value) {
            addCriterion("f_sys_update_by >", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("f_sys_update_by >=", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByLessThan(Long value) {
            addCriterion("f_sys_update_by <", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("f_sys_update_by <=", value, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByIn(List<Long> values) {
            addCriterion("f_sys_update_by in", values, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByNotIn(List<Long> values) {
            addCriterion("f_sys_update_by not in", values, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByBetween(Long value1, Long value2) {
            addCriterion("f_sys_update_by between", value1, value2, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysUpdateByNotBetween(Long value1, Long value2) {
            addCriterion("f_sys_update_by not between", value1, value2, "fSysUpdateBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByIsNull() {
            addCriterion("f_sys_delete_by is null");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByIsNotNull() {
            addCriterion("f_sys_delete_by is not null");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByEqualTo(Long value) {
            addCriterion("f_sys_delete_by =", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByNotEqualTo(Long value) {
            addCriterion("f_sys_delete_by <>", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByGreaterThan(Long value) {
            addCriterion("f_sys_delete_by >", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByGreaterThanOrEqualTo(Long value) {
            addCriterion("f_sys_delete_by >=", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByLessThan(Long value) {
            addCriterion("f_sys_delete_by <", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByLessThanOrEqualTo(Long value) {
            addCriterion("f_sys_delete_by <=", value, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByIn(List<Long> values) {
            addCriterion("f_sys_delete_by in", values, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByNotIn(List<Long> values) {
            addCriterion("f_sys_delete_by not in", values, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByBetween(Long value1, Long value2) {
            addCriterion("f_sys_delete_by between", value1, value2, "fSysDeleteBy");
            return (Criteria) this;
        }

        public Criteria andFSysDeleteByNotBetween(Long value1, Long value2) {
            addCriterion("f_sys_delete_by not between", value1, value2, "fSysDeleteBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}