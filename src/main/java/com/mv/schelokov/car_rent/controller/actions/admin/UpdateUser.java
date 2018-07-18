package com.mv.schelokov.car_rent.controller.actions.admin;

import com.mv.schelokov.car_rent.controller.actions.AbstractAction;
import com.mv.schelokov.car_rent.controller.actions.JspForward;
import com.mv.schelokov.car_rent.controller.consts.Jsps;
import com.mv.schelokov.car_rent.controller.exceptions.ActionException;
import com.mv.schelokov.car_rent.model.entities.UserData;
import com.mv.schelokov.car_rent.model.services.UserService;
import com.mv.schelokov.car_rent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class UpdateUser extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(AbstractAction.class);
    private static final String ERROR = "Unable to write data to base.";
    private static final UserService USER_SERVICE = new UserService();


    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        UserData userData = (UserData) pickSessionAttribute(req, "user_data");
        if (isAdmin(req) && userData != null) {
            userData.getUser().setLogin(req.getParameter("login"));
            userData.setName(req.getParameter("name"));
            userData.setAddress(req.getParameter("address"));
            userData.setPhone(req.getParameter("phone"));
            try {
                if (isUserDataNotEmpty(userData)) {
                    if (userData.getId() == 0) {
                        USER_SERVICE.addUserData(userData);
                    } else
                        USER_SERVICE.updateUserData(userData);
                } else {
                    if (userData.getId() != 0) {
                        req.setAttribute("errEmptyParam", 1);
                        req.getSession().setAttribute("user_data", userData);
                        return new JspForward(Jsps.ADMIN_EDIT_USER);
                    } else
                        USER_SERVICE.updateUser(userData.getUser());
                }
                return new JspForward("action/user_list", true);
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }

        } else {
            sendForbidden(res);
            return null;
        }
    }
    
    private boolean isUserDataNotEmpty(UserData userData) {
        return userData.getName().length() > 0 
                && userData.getAddress().length() > 0
                && userData.getPhone().length() > 0;
    }
    
}