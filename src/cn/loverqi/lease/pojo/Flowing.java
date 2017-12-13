package cn.loverqi.lease.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：Flowing 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public class Flowing implements Serializable{
    private static final long serialVersionUID = -7470423975155623260L;

	private Integer id;

    private Integer state;

    private String cardId;

    private Double cost;

    private Integer shopId;

    private Integer goodsId;

    private String remark;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}