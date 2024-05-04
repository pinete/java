package com.obbutcheryproyecto.dao;

import com.obbutcheryproyecto.entities.User;

import java.util.List;

public interface UserDAO {
    List<User> findAllActive();
}
