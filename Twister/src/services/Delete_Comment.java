package services;

import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;

public class Delete_Comment {

	public static JSONObject suppComment(int idMessage,int idComment){
		
		return Message_Tools.delteComment(idMessage,idComment);		
	}
	
}
