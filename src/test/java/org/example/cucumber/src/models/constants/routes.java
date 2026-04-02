package org.example.cucumber.src.models.constants;

public enum routes {
    homepage("/"),
    products("/products"),
    cart("/view_cart"),
    login("/login"),
    logout("/logout"),
    signup("/signup"),
    checkout("/checkout"),
    payment("/payment"),
    paymentDone("/payment_done/2000");

    public final String path;

    routes(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
