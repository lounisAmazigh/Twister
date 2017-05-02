package services;

import org.json.JSONArray;
import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;

public class Get_Messages {
	
	public static JSONArray getMessage(String log ,int id_min){
		
		//test d'inactivité
		System.out.println(Tools.activ(log));
		/*if(!(Tools.activ(log))){
			Tools.desconect(log);
			JSONArray r = new JSONArray();
			r.put(Tools.creationInfoJSONObject("ko", 22));
			return r;			
		}
		*/
	
		return Message_Tools.getMessage(log,id_min);
		
		
	}

}
