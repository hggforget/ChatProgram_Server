package me.gacl.websocket;

import Service.*;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public class WebSocketUtils {
    public void query_Friendlist(Session session, Integer userID){
        friendlistService query=new friendlistService();
        JSONObject ret=query.QueryFriendListbyuserID(userID);
        ret.put("type","FriendList");
        try {
            System.out.println(ret.toJSONString());
            session.getBasicRemote().sendText(ret.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void query_MyData(Session session,Integer userID)
    {
        userService query=new userService();
        JSONObject ret=query.QueryNamebyuserID(userID);
        ret.put("type","MyData");
        try {
            System.out.println(ret.toJSONString());
            session.getBasicRemote().sendText(ret.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg2Friend(Session session,JSONObject object)
    {
       Integer srcID=Integer.valueOf(object.getString("userID"));
       Integer dstID=Integer.valueOf(object.getString("friendID"));
        userService query=new userService();
        JSONObject ret=query.QueryNamebyuserID(srcID);
       String srcName= ret.getString("userName");
       String msg=object.getString("tosend");
        JSONObject pack=new JSONObject();
        pack.put("srcName",srcName);
        pack.put("srcID",srcID);
        pack.put("msg",msg);
        pack.put("type","txMsg");
       WebSocketTest dstWs=WebSocketTest.queryWs(dstID);
        try {
            dstWs.sendMessage(pack.toJSONString());
            Insertmessage(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryAlluser(Session session){
        userService query=new userService();
        JSONObject object=query.QueryAlluser();
        object.put("type","Alluser");
        try {
            session.getBasicRemote().sendText(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addFriend(Session session,JSONObject object){
        friendlistService service=new friendlistService();
        Integer userID=Integer.valueOf(object.getString("userID"));
        Integer friendID=Integer.valueOf(object.getString("friendID"));
        int ret=service.addFriend(userID,friendID);
        JSONObject pack=new JSONObject();
        pack.put("type","addRet");
        pack.put("ret",ret);
        try {
            session.getBasicRemote().sendText(pack.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Insertmessage(JSONObject object){
        messageService messageservice=new messageService();
        Integer userID1=Integer.valueOf(object.getString("userID"));
        Integer userID2=Integer.valueOf(object.getString("friendID"));
        int timestamp=object.getInteger("timestamp").intValue();
        String message= object.getString("tosend");
        int ret= messageservice.Insertmessage(userID1,userID2,timestamp,message);
        System.out.println("Insertmessage Result:"+Integer.toString(ret));
    }
    public void createGroup(JSONObject object){
        grouplistService grouplistservice=new grouplistService();
        Integer userID=Integer.valueOf(object.getString("userID"));
        String groupName=object.getString("groupName");
        String members=object.getString("members");
        int ret= grouplistservice.createGroup(userID,groupName,members);
        System.out.println("createGroup Result:"+Integer.toString(ret));
    }

    public void queryGroup(Session session,Integer userID){
        membersService query=new membersService();
        JSONObject object=query.queryGroup(userID);
        object.put("type","groupList");
        try {
            session.getBasicRemote().sendText(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void groupTalk(WebSocketTest myWs,JSONObject object)
    {
        Integer srcID=Integer.valueOf(object.getString("userID"));
        Integer dstID=Integer.valueOf(object.getString("groupID"));
        userService query=new userService();
        JSONObject ret=query.QueryNamebyuserID(srcID);
        String srcName= ret.getString("userName");
        String msg=object.getString("tosend");
        JSONObject pack=new JSONObject();
        pack.put("srcName",srcName);
        pack.put("srcID",srcID);
        pack.put("msg",msg);
        pack.put("type","txMsg");
        membersService membersservice =new membersService();
        List<String>userlist=membersservice.queryMembers(dstID);
        for (int i=0;i<userlist.size();i++)
        {
            WebSocketTest dstWs=WebSocketTest.queryWs(Integer.valueOf(userlist.get(i)));
            if(dstWs==null)
            {
                System.out.println(userlist.get(i)+" is not online now!");
                continue;
            }
            if(dstWs==myWs)
                continue;
            try {
                dstWs.sendMessage(pack.toJSONString());
                System.out.println(dstID.toString()+" groupTalk message send to "+userlist.get(i));
               // Insertmessage(object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
