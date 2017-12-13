package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：lease
 * 类名称：Grade 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public class Grade implements Serializable{
    private static final long serialVersionUID = 27671741714734052L;

	private Integer gradeId;

    private String gradeName;

    private Double discounts;

    private Double consumption;

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public Double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Double discounts) {
        this.discounts = discounts;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}