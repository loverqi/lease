package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：lease
 * 类名称：Woker 
 * 创建人：loverqi
 * 创建时间：2017-8-30
 * 类描述： 
 */
public class Woker implements Serializable{
    private static final long serialVersionUID = 6113286464060433669L;

	private String username;

    private String password;

    private Integer shopId;

    private String phone;

    private String remark;

    private Integer state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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