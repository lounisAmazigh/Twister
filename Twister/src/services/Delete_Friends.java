package services;

import org.json.JSONObject;

import serviceTools.Friend_Tools;
import serviceTools.Tools;

public class Delete_Friends {

	public static JSONObject deleteFreind(String from, String to){
		//test de temps d'inactivité
		if(!(Tools.activ(from))){
			Tools.desconect(from);
			return Tools.creationInfoJSONObject("ko", 22);	
		}
		//desconect(String login){activ(String login)
		
		JSONObject res = new JSONObject();
		res = Friend_Tools.deleteFriend(from , to);
		return res;
		
	}
	
}