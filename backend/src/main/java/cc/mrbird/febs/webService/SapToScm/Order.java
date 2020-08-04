package cc.mrbird.febs.webService.SapToScm;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private List<Sap_PurchasePlan> orders;

    public List<Sap_PurchasePlan> getOrders() {
        return orders;
    }
    public void setOrders(List<Sap_PurchasePlan> orders) {
        this.orders = orders;
    }
}
