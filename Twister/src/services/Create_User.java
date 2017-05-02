package services;


import java.sql.SQLException;

import serviceTools.Tools;
import serviceTools.User_Tools;

import org.json.JSONException;
import org.json.JSONObject;


public class Create_User {
	 
	public Create_User()
	{
		
	}
	
	public static JSONObject Create_Users(String nom ,  String prenom , String login , String mdp , String adresse) {
		
		boolean OK=false;
		/**
		 * TEST ATTRIBUTS OK  
		 */
		if(!(OK = User_Tools.testAttributsUsers(nom, prenom, login, mdp, adresse))){
			// Tools.creationJSONObject("ATTRIBUTS VIDE OU NULL", 0);
			 return Tools.creationInfoJSONObject("ko", 7);
		}
		
		/**
		 * TEST LOGIN NOT EXIST
		 */
		if(!(OK = Tools.testUserNotExist(login))){
			//Tools.creationJSONObject("LOGIN EXISTANT", 0);
			return Tools.creationInfoJSONObject("ko", 8);
		}
		
		/**
		 *  TEST ADRESSE NOT EXIST
		 */
		if(!(OK = Tools.testAdresseNotExist(adresse))){
			//Tools.creationJSONObject("ADRESSE EXISTANTE", 0);
			return Tools.creationInfoJSONObject("ko", 9);
		}
		
		/**
		 * CREATION D'UN UTILISATEUR
		 */
		JSONObject obj = User_Tools.createUser(nom, prenom, login, mdp, adresse);
		
		/**
		 * INSERSSION D'UN UTLISATEUR
		 */
		JSONObject re = User_Tools.insertUser(obj);
		return re;
		
	}

}
