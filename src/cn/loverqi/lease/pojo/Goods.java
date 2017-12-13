package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：Goods 
 * 创建人：loverqi
 * 创建时间：2017-9-6
 * 类描述： 
 */
public class Goods implements Serializable {
	private static final long serialVersionUID = -7410487547970230173L;

	private Integer goodsId;

	private String goodsName;

	private String describes;

	private String remark;

	private String bar;

	private Integer categoryId;

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
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

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar == null ? null : bar.trim();
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}