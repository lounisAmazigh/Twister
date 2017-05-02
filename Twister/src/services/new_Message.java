package services;

import org.json.JSONException;
import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;
import serviceTools.friendTools;

public class new_Message {
	
	
	/**
	 * creation de message
	 * @param id
	 * @param id_ami
	 * @param message
	 * @return
	 */
	
	public static JSONObject NewMesssage(String log , String message) {
		//test de temps d'inactivité
		if(!(Tools.activ(log))){
			Tools.desconect(log);
			return Tools.creationInfoJSONObject("ko", 22);	
		}
		
		if(!Message_Tools.testLog(log)){
			return Tools.creationInfoJSONObject("ko", 3);
		}
		
		if((!Message_Tools.testMessage(message))){
			return Tools.creationInfoJSONObject("ko", 20);
		}
		
		return Message_Tools.creationMessge(log , message);
			
	}

	

}
