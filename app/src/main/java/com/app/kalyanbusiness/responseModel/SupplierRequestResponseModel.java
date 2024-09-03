package com.app.kalyanbusiness.responseModel;

import java.io.Serializable;

public class SupplierRequestResponseModel implements Serializable {
    String km_userid,
            leader_code,
            shop_name,
            shop_address,
            owner_name;

    public String getKm_userid() {
        return km_userid;
    }

    public void setKm_userid(String km_userid) {
        this.km_userid = km_userid;
    }

    public String getLeader_code() {
        return leader_code;
    }

    public void setLeader_code(String leader_code) {
        this.leader_code = leader_code;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }
}
