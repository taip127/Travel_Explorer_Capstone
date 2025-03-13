package com.tai.travel2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "admin_user")
@PrimaryKeyJoinColumn(name = "id")
public class AdminUser extends User {
    public AdminUser() {}
}
