package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：lease
 * 类名称：Shop 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public class Shop implements Serializable{
    private static final long serialVersionUID = -1513265930644880509L;

	private Integer shopId;

    private String name;

    private String adderss;

    private String phone;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAdderss() {
        return adderss;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss == null ? null : adderss.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}