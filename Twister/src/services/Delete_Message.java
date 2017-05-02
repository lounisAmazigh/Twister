package services;

import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;
import serviceTools.friendTools;

public class Delete_Message {
	

	
public static JSONObject suppMessage(String log,int idMessage){
		
		//test d'inactivité
		if(!(Tools.activ(log))){
			Tools.desconect(log);
			return Tools.creationInfoJSONObject("ko", 22);	
			
		}
		
		if(!Message_Tools.belongMessage(log, idMessage)){
			return Tools.creationInfoJSONObject("ko",18 );
		}
		
	
		return  Message_Tools.suppMessage(log,idMessage);
		
	}

}
