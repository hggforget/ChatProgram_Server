package Service;

import DAO.messageDAO;

public class messageService {
    public int Insertmessage(Integer userID1,Integer userID2,int timestamp,String message) {
        messageDAO messagedao=new messageDAO();
        return messagedao.Insertmessage(userID1,userID2,timestamp,message);
    }
}
