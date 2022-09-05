package Service;

import DAO.*;
import com.alibaba.fastjson.JSONObject;

public class grouplistService {

    public int createGroup(Integer userID, String groupName, String members) {
        grouplistDAO grouplistdao=new grouplistDAO();
        return grouplistdao.createGroup(userID,groupName,members);
    }
}
