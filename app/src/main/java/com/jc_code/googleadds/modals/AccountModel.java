package com.jc_code.googleadds.modals;

import java.util.Objects;

public class AccountModel {
    String accountNumber;
    String projectName;
    String name;
    String address;
    String mobile1;
    String mobile2;
    String sanctionDate;
    String sanctionAmount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSanctionAmount() {
        return sanctionAmount;
    }

    public void setSanctionAmount(String sanctionAmount) {
        this.sanctionAmount = sanctionAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountModel that = (AccountModel) o;
        return Objects.equals(accountNumber, that.accountNumber) && Objects.equals(projectName, that.projectName) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(mobile1, that.mobile1) && Objects.equals(mobile2, that.mobile2) && Objects.equals(sanctionDate, that.sanctionDate) && Objects.equals(sanctionAmount, that.sanctionAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, projectName, name, address, mobile1, mobile2, sanctionDate, sanctionAmount);
    }
}
