package com.shopwell;

import com.shopwell.services.CashierService;
import com.shopwell.services.ManagerService;
import com.shopwell.services.servicesimplementation.CashierServiceImpl;
import com.shopwell.services.servicesimplementation.ManagerServiceImpl;
import com.shopwell.staff.Cashier;
import com.shopwell.staff.Designation;
import com.shopwell.staff.Manager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    Manager manager;
    Store store;
    Cashier cashier;
    CashierService cs;
    Customer customer;
    Product product;
    ManagerService ms;

    @BeforeEach
    void init() {
        store = new Store("shopwell", 100000.0);
        manager = new Manager("John Cena", Designation.MANAGER, store);
        product = new Product("Tissue", 120.0, PRODUCTCATEGORY.TOILETRIES, 10);
        cashier = new Cashier("The Undertaker", Designation.CASHIER, store);
        customer = new Customer("Jude King", 120000);
        customer.addProductToCart(product, 2);
        cs = new CashierServiceImpl(cashier, store);
        ms = new ManagerServiceImpl(manager, store);
        ms.addProduct(product);
        ms.hireStaff(cashier);
    }
    @Test
    void checkAccountBalance() {
        assertTrue(store.getAccountBalance() == 100000.0);
    }

    @Test
    void updateStoreAccountBalance() {
        double expected = 100240.0;
        cs.checkOutCustomer(customer);
        ms.addSalesToCompanyAccount(store.getDailySalesAccount());
        double actual = store.getAccountBalance();
        assertEquals(expected, actual);
    }

    @Test
    void addCashier() {
        assertTrue(store.getCashiersList().size() == 1);
    }

    @Test
    void addProducts() {
        ms.addProduct(new Product("Yam", 12.0, PRODUCTCATEGORY.GROCERIES, 10));
        assertEquals(2, store.getProductsList().size());
    }
}