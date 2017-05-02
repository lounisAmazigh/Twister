package services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import serviceTools.Message_Tools;
import serviceTools.Tools;
import serviceTools.friendTools;

public class Liste_Message {

	
	public static JSONArray mesMessage(String login){
		System.out.println(friendTools.enLigne(login));
		//test d'inactivité
		if(!(Tools.activ(login))){
			Tools.desconect(login);
			JSONArray r =new JSONArray();
			return r;
		}
		return  Message_Tools.mesMessage(login);
	}
	
	
}
