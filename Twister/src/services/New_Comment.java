package services;

import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;

public class New_Comment {
public static JSONObject New_Comments(String login ,int idmsg, String comment) {
		
	
	//test de temps d'inactivité
		if(!(Tools.activ(login))){
			Tools.desconect(login);
			return Tools.creationInfoJSONObject("ko", 22);		
		}
		
		if((!Message_Tools.testMessage(comment))){
			return Tools.creationInfoJSONObject("ko", 21);
		}
		
		return Message_Tools.creationComment(login , idmsg, comment);
			
	}

}
