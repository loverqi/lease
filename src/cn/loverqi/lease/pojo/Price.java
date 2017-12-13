package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：Price 
 * 创建人：loverqi
 * 创建时间：2017-9-8
 * 类描述： 
 */
public class Price implements Serializable{
    private static final long serialVersionUID = 296006525275988061L;

	private Integer priceId;

    private Integer goodsId;

    private Integer shopId;

    private Double price;

    private Integer duration;

    private Integer pattern;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }
}