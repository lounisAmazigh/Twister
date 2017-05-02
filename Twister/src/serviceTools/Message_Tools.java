package serviceTools;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

import BD.Database;
import services.Liste_Message;

public class Message_Tools {
	
	public static boolean testLog(String log ){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			System.out.println(log);
			//mise a jour de session 
    		Tools.udpdateAciv(log);
			String query = "SELECT idUser FROM users WHERE login=\""+log+"\"";
			System.out.println(query);
			ResultSet res = st.executeQuery(query);
			int nbr=0;
			while(res.next()){
				nbr+=1;	
			}
			if (nbr>=1){
				return true;
			}else return false;
		}catch(Exception e ){
		e.getMessage();
		return false;
	}
	}
	
	public static boolean testMessage(String message){
		if ((message.length() != 0) && message !=" "){
			return true;
		}
		return false;
	}

	public static JSONObject insertMessage(String log ,String message)  {
		JSONObject n = new JSONObject();
		int idMessage = 0;
		
		try {
			//Mongo m = new Mongo("localhost", 27017);
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			
			//mise a jour de session 
    		Tools.udpdateAciv(log);
			
			//recuperation de la valeur max de l'idMessage dans la base mongo
			BasicDBObject maxId = new BasicDBObject();
			maxId.put("id", -1); 
			
			DBCursor cursor = col.find().sort(maxId).limit(1);
			while(cursor.hasNext()){
				DBObject g = cursor.next();
				idMessage=(Integer) g.get("id");
				System.out.println("je sonde mongo et le max est "+idMessage);
				// Affectation d'un id au message				
				idMessage = idMessage + 1;
				break;
			}
			
			//je crée mon object auteur qui contien son id et son login
			BasicDBObject author = new BasicDBObject();
			author.put("idAuthor" ,Tools.getIdOfLogin(log));
			author.put("login", log);
			System.out.println(author);
			/*
			BasicDBObject popular = new BasicDBObject();
			popular.put("like", 0);
			popular.put("dislike",0);
			BasicDBObject com = new BasicDBObject();
			*/
			
			//creation d'un tableau d'object qui fera office de conteneur pour tout les commentaire d'un post
			BasicDBList comments = new BasicDBList();
			
			Timestamp date = Tools.getTimestamap();
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String dateS=dateFormat.format(date);
			
			//je crée mon object a inserer a ma base mongoDB
			BasicDBObject obj = new BasicDBObject();
			obj.put("author",author);
			obj.put("id", idMessage);
			obj.put("texte", message);
			obj.put("date", dateS);
			obj.put("comments", comments);
			
			
			System.out.println(obj);
			System.out.println("innnnnnnnnnnnnnnn"+col.insert(obj).getN());
			n.put("code", 1);
			n.put("idMessage",idMessage);
			n.put("texte", message);
			n.put("date", Tools.getTimestamap());
			n.put("comments", comments);
			return n;
			
		}catch(Exception e){
			e.getMessage();	
			return Tools.creationInfoJSONObject("ko", 1000);
		}
	}
	
	public static JSONArray mesMessage(String log){
		JSONObject objr = new JSONObject();
		
		JSONArray liste = new JSONArray();

		try {
			//Mongo m = new Mongo("localhost", 27017);
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			//mise a jour de session 
    		Tools.udpdateAciv(log);
			
			BasicDBObject query = new BasicDBObject();
			
			BasicDBObject auteur = new BasicDBObject();
			auteur.put("idAuthor", Tools.getIdOfLogin(log));
			auteur.put("login", log);
			query.put("author", auteur);
			System.out.println("l'obejct de recherche est : "+auteur);
			DBCursor cursor = col.find(query);
			int i=0;
		
			while (cursor.hasNext()) {
				DBObject g = cursor.next();
				liste.put(g);
			}
			
		}catch(Exception e){
			System.out.println("erreur "+ e.getMessage());
			e.getMessage();	
		}
		return liste;
	}
	
	public static int nombreTwit(int id){
		int i=0;
		try {
			//Mongo m = new Mongo("localhost", 27017);
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			
			BasicDBObject query = new BasicDBObject();
			
			BasicDBObject auteur = new BasicDBObject();
			auteur.put("idAuthor", id);
			auteur.put("login", Tools.getLogOfId(id));
			query.put("author", auteur);
			System.out.println("l'obejct de recherche est : "+auteur);
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject g = cursor.next();
				i++;
			}
			
		}catch(Exception e){
			System.out.println("erreur "+ e.getMessage());
			e.getMessage();	
		}
		return i;
	}
	
	
	public static JSONObject suppMessage(String log,int idMessage){
		try {
			Mongo m = new Mongo("li328.lip6.fr",27130);
			//Mongo m = new Mongo("localhost", 27017);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			
			//mise a jour de session 
    		Tools.udpdateAciv(log);
			
			//creation de l'object auteur
			BasicDBObject auteur = new BasicDBObject();
			auteur.put("idAuthor", Tools.getIdOfLogin(log));
			auteur.put("login", log);
			
			BasicDBObject query = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			
			obj.add(new BasicDBObject("author", auteur));
			obj.add(new BasicDBObject("id",idMessage ));
			
			query.put("$and", obj);
			System.out.println(query);
			WriteResult cursor = col.remove(query);
			if (cursor.getN() ==1){
				return Tools.creationInfoJSONObject("ok", 1);
			}else{
				return Tools.creationInfoJSONObject("ko", 19);
			}	
		}catch(Exception e){
			e.getMessage();	
			return Tools.creationInfoJSONObject("ko", 1000);
		}	
	}
	
	
	
	
	
	
	public static JSONObject creationMessge(String log , String message){
	
		try {
			
			JSONObject a = new JSONObject();
			a = Message_Tools.insertMessage(log ,message);
			if(a.getInt("code")==1){
				return a;	
			}
			else	
				return Tools.creationInfoJSONObject("ko", 21);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return Tools.creationInfoJSONObject("ko", 21);
		}
		}
	
	public static boolean UsersFriends(String log,String AmiLog){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			int id = Tools.getIdOfLogin(log);
			int id_ami = Tools.getIdOfLogin(AmiLog);
			
			//mise a jour de session 
    		Tools.udpdateAciv(log);
    		
			String query = "SELECT * FROM friends WHERE (fromm="+id+" AND too="+id_ami+")";
			System.out.println(query);
			ResultSet res = st.executeQuery(query);
			
			if(res.next() ){
					st.close();
					co.close();
					return true;
				}				
			else{
				st.close();
				co.close();
				return false;
			}
			
			
			
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	public static JSONObject Friends_Message(String log){
		JSONObject objr = new JSONObject();
		try {
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			
			BasicDBObject query = new BasicDBObject();
			query.put("id_author", Tools.getIdOfLogin(log));
			System.out.println(query.toString());
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
				DBObject g = cursor.next();
				objr.put("", g);
				System.out.println(g);
			}
			
		}catch(Exception e){
			e.getMessage();	
		}
		return objr;
	}
	
	public static boolean belongMessage(String log, int idMessage){
		try {
			//Mongo m = new Mongo("localhost", 27017);
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			
			//mise a jour de session 
    		Tools.udpdateAciv(log);
    		
			BasicDBObject query = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			
			BasicDBObject auteur = new BasicDBObject();
			auteur.put("idAuthor", Tools.getIdOfLogin(log));
			auteur.put("login", log);
			System.out.println(auteur);
			obj.add(new BasicDBObject("author", auteur));
			obj.add(new BasicDBObject("id", idMessage));
			query.put("$and", obj);
			System.out.println(query);
			DBCursor cursor = col.find(query);
			if(cursor.hasNext()){
				return true;
			}
			
		}catch(Exception e){
			e.getMessage();	
		}
		return false;
	}
	
	public static JSONObject creationComment(String log , int idMessage , String comment){
		int idCom = 0;
		try {
			//**********************************************************************
			
			//Mongo m = new Mongo("localhost", 27017);
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			DBCollection com = db.getCollection("comment");
			
			//mise a jour de session 
    		Tools.udpdateAciv(log);
			
			//recuperation de la valeur max de l'idMessage dans la base mongo
			BasicDBObject maxId = new BasicDBObject();
			maxId.put("id", -1); 
			
			DBCursor cursor = com.find().sort(maxId).limit(1);
			while(cursor.hasNext()){
				DBObject g = cursor.next();
				idCom=(Integer) g.get("id");
				System.out.println("je sonde mongo et le max est "+idCom);
				// Affectation d'un id au message				
				idCom = idCom + 1;
				break;
			}
			
			//je crée mon object auteur qui contien son id et son login
			BasicDBObject author = new BasicDBObject();
			author.put("idAuthor" ,Tools.getIdOfLogin(log));
			author.put("login", log);
			System.out.println(author);
			
			Timestamp date = Tools.getTimestamap();
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String dateS=dateFormat.format(date);
			
			//je crée mon object a inserer a ma base mongoDB
			BasicDBObject obj = new BasicDBObject();
			obj.put("author",author);
			obj.put("id", idCom);
			
			obj.put("texte", comment);
			obj.put("date", dateS);

			System.out.println(com.insert(obj).getN());
			
			
			//recuperation de la valeur max de l'idMessage dans la base mongo *********************************************
			/*BasicDBObject message = new BasicDBObject();
			message.put("id", idMessage);
			DBCursor cursor = col.find(message);
			while(cursor.hasNext()){
				DBObject x = cursor.next();
				System.out.println(x);
			}
			*/
			BasicDBObject auteur = new BasicDBObject();
			auteur.put("idAuthor",Tools.getIdOfLogin(log) );
			auteur.put("login", log);
			//creation de comment
			BasicDBObject comments = new BasicDBObject();
			comments.put("id", idCom);
			comments.put("author", auteur);
			comments.put("idMessage", idMessage);
			comments.put("texte", comment);
			comments.put("date", dateS);
			
			//je crée mon object pour la selection du message a qui on doit ajouter le commentaire
			BasicDBObject idmessage1 = new BasicDBObject();
			idmessage1.put("id", idMessage);
			//creation de l'objet qui cotient la requete mongo avec le mot clef $push 
			//db.col.update({"id": "12"{$push: { "comment": {"id": "123","idUser":12, ....}}
			BasicDBObject push = new BasicDBObject();
			BasicDBObject comm = new BasicDBObject();
			comm.put("comments", comments);
			push.put("$push",comm);
			
			
			int res = col.update(idmessage1,push).getN();
			
			System.out.println("ici le commentaire "+res);
			if(res ==0){
				return Tools.creationInfoJSONObject("ko", 24);
			}else{
				JSONObject Moncoms = new JSONObject();
				try {
					Moncoms.put("idCommentaire", idCom);
				Moncoms.put("texte", comment);
				Moncoms.put("date", dateS);
				Moncoms.put("idMessage", idMessage);
				Moncoms.put("author", auteur);
				}catch (JSONException e) {
					return Tools.creationInfoJSONObject("ko", 24);
				}
				return Moncoms;
			}
			//**********************************************************************
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Tools.creationJSONObject(e.getMessage(), 0);
		}
}

	public static JSONObject delteComment(int idMessage,int idComment){
		
		try {
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
  
			BasicDBObject query = new BasicDBObject("id", idMessage);
		    BasicDBObject fields = new BasicDBObject("comments", new BasicDBObject( "id", idComment));
		    BasicDBObject update = new BasicDBObject("$pull",fields);

		    int res = col.update( query, update).getN();
		    if(res ==1){
		    	return Tools.creationInfoJSONObject("ok", 1);
		    }else
		    	return Tools.creationInfoJSONObject("ko", 19);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Tools.creationInfoJSONObject("ko", 1000);
		}
		
	}
	
	public static JSONArray getMessage(String log,int id_min){
		JSONArray message = new JSONArray();
		ArrayList<Integer> amis = new ArrayList<Integer>();
		try {
			try{
				Connection co = Database.getMysqlConnection();
				Statement st = co.createStatement();
				String query = "Select too from friends WHERE fromm = \""+Tools.getIdOfLogin(log)+"\"";
				System.out.println(query);
				ResultSet res = st.executeQuery(query);
				while(res.next()){
					System.out.println(res.getInt("too"));
					amis.add(res.getInt("too"));	
				}
				co.close();
				st.close();
			}catch(Exception e ){
				e.getMessage();
			}
			List<Integer> friends = new ArrayList<Integer>();
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db =  m.getDB("gr1_2017_berrabah_cher");
			DBCollection col = db.getCollection("msg");
			for (Integer ami : amis) {
				BasicDBObject author = new BasicDBObject();
				author.append("author.idAuthor", ami);
				friends.add(ami);
			
			}
			friends.add(Tools.getIdOfLogin(log));
			if(id_min<0){
				BasicDBObject in = new BasicDBObject("$in",friends);
				BasicDBObject author = new BasicDBObject("$in",friends);
				BasicDBObject auteur = new BasicDBObject("author.idAuthor",in);
				//{"$and",{{"idAuthor",x},{"login",log}
				
				//friends.add(min);
				BasicDBObject sort = new BasicDBObject("date",-1);

				System.out.println(auteur.toString());
				DBCursor cursor = col.find(auteur).sort(sort).limit(7);
				while (cursor.hasNext()) {
					DBObject g = cursor.next();
					message.put(g);
				}
				return message;
			}
			BasicDBObject in = new BasicDBObject("$in",friends);
			BasicDBObject author = new BasicDBObject("$in",friends);
			BasicDBObject auteur = new BasicDBObject("author.idAuthor",in);
			BasicDBObject id = new BasicDBObject("$lt", id_min);
			BasicDBObject min = new BasicDBObject("id", id);
			List<BasicDBObject> condition = new ArrayList<BasicDBObject>();
			condition.add(auteur);
			condition.add(min);
			BasicDBObject and = new BasicDBObject("$and",condition);
			
			
			//{"$and",{{"idAuthor",x},{"login",log}
			
			//friends.add(min);
			BasicDBObject sort = new BasicDBObject("date",-1);

			System.out.println(and.toString());
			DBCursor cursor = col.find(and).sort(sort).limit(10);
			while (cursor.hasNext()) {
				DBObject g = cursor.next();
				message.put(g);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.put(Tools.creationInfoJSONObject("ko", 1000));
		}
		return message;
	}
	
}
