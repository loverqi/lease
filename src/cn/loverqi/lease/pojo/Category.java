package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：Category 
 * 创建人：loverqi
 * 创建时间：2017-9-6
 * 类描述： 
 */
public class Category implements Serializable{
    private static final long serialVersionUID = 7649725852154056034L;

	private Integer categoryId;

    private String categoryName;

    private String describes;

    private String remark;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}