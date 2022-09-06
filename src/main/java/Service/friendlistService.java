package Service;

import DAO.friendlistDAO;
import DAO.userDAO;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class friendlistService {
    public JSONObject QueryFriendListbyuserID(Integer userID){
        JSONObject object = new JSONObject();
        friendlistDAO friendlistdao=new friendlistDAO();
        userDAO userdao=new userDAO();
        List<String> namelist=new ArrayList<String>();
        List<String> imglist=new ArrayList<String>();
        List<String>idlist=(List<String>)friendlistdao.QueryFriendListbyuserID(userID).get("idlist");
        System.out.println(idlist);
        for(int i=0;i<idlist.size();i++)
        {
            Integer friendID=Integer.valueOf(idlist.get(i));
            JSONObject tmp=userdao.QueryDatabyuserID(friendID);
            namelist.add(tmp.getString("userName"));
            imglist.add(tmp.getString("img"));
        }
        object.put("friendsID",idlist);
        object.put("friendsName",namelist);
        object.put("friendsimg",imglist);
        return object;
    }
    public int addFriend(Integer userID,Integer friendID){
        friendlistDAO friendlistdao=new friendlistDAO();
        return friendlistdao.addFriend(userID,friendID);
    }
}
