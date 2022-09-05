package Service;

import DAO.*;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class membersService {
    public JSONObject queryGroup(Integer userID) {
        membersDAO membersdao=new membersDAO();
        grouplistDAO grouplistdao=new grouplistDAO();
        JSONObject object= new JSONObject();
        List<String> namelist=new ArrayList<String>();
        List<String>idlist=(List<String>)membersdao.queryGroup(userID).get("groupIDlist");
        for (int i=0;i<idlist.size();i++)
        {
            Integer groupID=Integer.valueOf(idlist.get(i));
            namelist.add(grouplistdao.queryNamebyID(groupID));
        }
        object.put("groupIDlist",idlist);
        object.put("groupNamelist",namelist);
        return object;
    }
    public List<String> queryMembers(Integer groupID) {
            membersDAO membersdao=new membersDAO();
            return membersdao.queryMembers(groupID);
    }
}
