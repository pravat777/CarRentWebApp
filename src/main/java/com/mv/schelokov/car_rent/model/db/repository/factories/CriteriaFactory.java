package com.mv.schelokov.car_rent.model.db.repository.factories;

import com.mv.schelokov.car_rent.model.db.repository.RoleRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserDataRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CriteriaFactory {
    
    public static Criteria getUserFindLoginPassword(User user) {
        return new UserRepository.FindLoginPassword(user);
    }
    
    public static Criteria getUserFindLogin(String login) {
        return new UserRepository.FindLogin(login);
    }
    
    public static Criteria getUserFindLoginPassword(String login, 
            String password) {
        return new UserRepository.FindLoginPassword(login, password);
    }
    
    public static Criteria getAllUsers() {
        return UserRepository.SELECT_ALL;
    }
    
    public static Criteria getAllUsersData() {
        return UserDataRepository.SELECT_ALL;
    }
    
    public static Criteria getUserDataById(int id) {
        return new UserDataRepository.FindByUser(id);
    }
    
    public static Criteria getAllRoles() {
        return RoleRepository.SELECT_ALL;
    }
    
}
