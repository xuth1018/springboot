package com.company.project.model.dict;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Entity
@Table(name = "T_DICT")
public class Dict implements Serializable {

    private static final long serialVersionUID = -5521788246125421239L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parentCode;
    private String Code;
    private String value;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String createBy;
    private String updateBy;
    private Date updateDate;
    private String isDelete;

    public Dict() {
    }

    public Dict(String parentCode, String code, String value) {
        this.parentCode = parentCode;
        Code = code;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dict dict = (Dict) o;
        return Objects.equals(id, dict.id) &&
                Objects.equals(parentCode, dict.parentCode) &&
                Objects.equals(Code, dict.Code) &&
                Objects.equals(value, dict.value) &&
                Objects.equals(createDate, dict.createDate) &&
                Objects.equals(createBy, dict.createBy) &&
                Objects.equals(updateDate, dict.updateDate) &&
                Objects.equals(updateBy, dict.updateBy) &&
                Objects.equals(isDelete, dict.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap();
        map.put("飞撒","sds");
        map.put("刚发","sf");
        Map<String,String> map1 = new TreeMap((o1, o2) -> {
            String param1 = (String)o1;
            String param2 = (String)o2;
            return param1.compareTo(param2);
        });
        map1.putAll(map);
        System.out.println(map);
        System.out.println(map1);
    }


}

