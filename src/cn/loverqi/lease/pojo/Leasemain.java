package cn.loverqi.lease.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：lease
 * 类名称：Leasemain 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public class Leasemain implements Serializable{
    private static final long serialVersionUID = 5808783092168368157L;

	private Integer id;

    private Integer goodsId;

    private Integer state;

    private String cardId;

    private Date startTime;

    private Date endTime;

    private Long consuming;

    private Double cost;

    private Integer borrowId;

    private Integer alsoId;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getConsuming() {
        return consuming;
    }

    public void setConsuming(long l) {
        this.consuming = l;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Integer getAlsoId() {
        return alsoId;
    }

    public void setAlsoId(Integer alsoId) {
        this.alsoId = alsoId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}