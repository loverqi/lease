package cn.loverqi.lease.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 项目名称：lease 类名称：User 创建人：loverqi 创建时间：2017-8-30 类描述：
 */
public class User implements Serializable {
	private static final long serialVersionUID = -6628066291509193794L;

	private String cardId;

	private String name;

	private String phone;

	private String adderss;

	private Double balance;

	private Integer level;

	private Double cost;

	private String remark;

	private Integer state;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId == null ? null : cardId.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getAdderss() {
		return adderss;
	}

	public void setAdderss(String adderss) {
		this.adderss = adderss == null ? null : adderss.trim();
	}

	public Double getBalance() {
		return balance == null ? null : new BigDecimal(balance).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getCost() {
		return cost == null ? null : new BigDecimal(cost).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}