package Service;

import DAO.*;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class messageService {
    public int Insertmessage(Integer userID1,Integer userID2,Integer timestamp,String message) {
        messageDAO messagedao=new messageDAO();
        return messagedao.Insertmessage(userID1,userID2,timestamp,message);
    }
    public JSONObject queryConversation(Integer userID1, Integer userID2) {
        messageDAO messagedao=new messageDAO();
        userDAO userdao=new userDAO();
        JSONObject object=messagedao.queryConversation(userID1,userID2);
        JSONObject friend=userdao.QueryDatabyuserID(userID2);
        String friendName=friend.getString("userName");
        String friendimg=friend.getString("img");
        object.put("fromName",friendName);
        object.put("friendimg",friendimg);
        return object;
    }
    public JSONObject queryGroupMsg(Integer groupID) {
        messageDAO messagedao=new messageDAO();
        userDAO userdao=new userDAO();
        List<String>friendName=new ArrayList<String>();
        List<String>friemdimg=new ArrayList<String>();
        JSONObject object=messagedao.queryGroupMsg(groupID);
        List<Integer>friendID=(List<Integer>) object.get("from");
        for (int i=0;i<friendID.size();i++)
        {
            JSONObject tmp=userdao.QueryDatabyuserID(friendID.get(i));
            friendName.add(tmp.getString("userName"));
            friemdimg.add(tmp.getString("img"));
        }
        object.put("fromName",friendName);
        object.put("friendimg",friemdimg);
        return object;
    }
    public int InsertGroupmessage(Integer userID,Integer groupID,Integer timestamp,String message) {
        messageDAO messagedao=new messageDAO();
        return messagedao.InsertGroupmessage(userID,groupID,timestamp,message);
    }
}
