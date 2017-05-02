package serviceTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import BD.Database;

public class friendTools {
	
	public static JSONObject add(JSONObject obj){
		
		JSONObject rep = new JSONObject();
		try{
        	
        	Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			
			//mise a jour de session 
    		Tools.udpdateAciv(obj.getString("from"));
    		
	        String query = "INSERT INTO friends values(\""+obj.getString("from")+"\",\""+obj.getString("to")+"\",\""+obj.getString("date")+"\")";
            int res = st.executeUpdate(query);
            st.close();
            co.close();
            if (res==1){    
                return Tools.creationJSONObject("demande envoyée",1);        
            }
            else{
                return Tools.creationJSONObject("demande non envoyée", 0);
            }    
            
        }catch(Exception e){
         
            e.getMessage();    
            return Tools.creationJSONObject("bdd", 1000);
        }
	}
	
public static JSONObject remove(JSONObject obj){
		
		JSONObject rep = new JSONObject();
		try{
        	
        	Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			
			//mise a jour de session 
    		Tools.udpdateAciv(obj.getString("from"));
    		
	        String query = "DELETE FROM friends where (fromm="+obj.getString("from")+" AND too="+obj.getString("to")+")";
            System.out.println(query);
	        int res = st.executeUpdate(query);
            System.out.println(res);
            if (res==1){    
                return Tools.creationJSONObject("ami supprimer",1);        
            }
            else{
                return Tools.creationJSONObject("ami non supprimer", 0);
            }    
            
        }catch(Exception e){
         
            e.getMessage();    
            return Tools.creationJSONObject("bvdd", 1000);
        }
	}
public static boolean enLigne(String log){
	try{
		Connection co = Database.getMysqlConnection();
		Statement st = co.createStatement();
		//mise a jour de session 
		Tools.udpdateAciv(log);
	    String query = "SELECT * FROM session WHERE id=\""+Tools.getIdOfLogin(log)+"\"";
	    System.out.println(query);
	    ResultSet res = st.executeQuery(query);
	    if(res.next()){
	    	st.close();
		    co.close();
	    	return true;
	    }else{
	    	st.close();
		    co.close();
	    	return false;
	    }
	}
	catch(Exception e){
        
        e.getMessage();    
        return false;
    }
}
public static JSONObject listMessage(String log,String logAmi){
	JSONObject objr = new JSONObject();
	try {
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db =  m.getDB("gr1_2017_berrabah_cher");
		DBCollection col = db.getCollection("msg");
		
		//mise a jour de session 
		Tools.udpdateAciv(log);
		
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("id_author", Tools.getIdOfLogin(log)));
		obj.add(new BasicDBObject("id_friend", Tools.getIdOfLogin(logAmi)));
		query.put("$and", obj);
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

}
