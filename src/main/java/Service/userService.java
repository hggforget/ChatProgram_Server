package Service;

import DAO.userDAO;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userService {

    public JSONObject QueryNamebyuserID(Integer userID){
        JSONObject object = new JSONObject();
        userDAO userdao=new userDAO();
        String userName=userdao.QueryNamebyuserID(userID);
        object.put("userName",userName);
        return object;
    }
    public JSONObject QueryAlluser(){
        userDAO userdao=new userDAO();
        JSONObject object=userdao.QueryAlluser();
        return object;
    }
}
