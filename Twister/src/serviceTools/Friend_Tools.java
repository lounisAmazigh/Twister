package serviceTools;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import BD.Database;

public class Friend_Tools {
	
	/** CREATION D'UN SUIVI ENTRE LES AMIS */
	
	public static JSONObject creationSuivie(String from , String to){
	     
		JSONObject f = new JSONObject();	 
	    f= Tools.testDejaLogue(from);

	    
	    /** TEST SI LOGUE **/
	    if(!Tools.testDejaLogueBool(f)){
	    	 return Tools.creationInfoJSONObject("ko", 22);
	    }
	    
	    if(testFreindship(from,to)){
	    	 return Tools.creationInfoJSONObject("ko", 15);
	    }
	    
	    
	    try{
		    int idf = Tools.getIdOfLogin(from);
		    int idt = Tools.getIdOfLogin(to);
		    java.sql.Timestamp date = Tools.getTimestamap();
		    JSONObject suivie = new JSONObject();
	
		    
		    suivie.put("from", idf);
		    suivie.put("to", idt);
		    suivie.put("date", date );
	
		
		    JSONObject se = insertToFriend(suivie);
		    se.put("idto",idt );
		    se.put("logTo", to);
		    return se;
		    
	    }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Tools.creationJSONObject("creation objet impossible", 100);
		}
	      
	     
	}
	
	
	/** INSERSION DANS LA BASE FREINDS */
    public static JSONObject insertToFriend(JSONObject s){
    	
    	try{
    		
    		Connection co = Database.getMysqlConnection();
    		Statement st = co.createStatement();
    		Tools.udpdateAciv(s.getString("from"));
    		String query = "INSERT INTO friends values(\""+s.getString("from")+"\",\""+s.getString("to")+"\",\""+s.getString("date")+"\")";
    	
    		int res = st.executeUpdate(query);
    		
    		 st.close();
             co.close();
             if (res==1){    
                 return Tools.creationInfoJSONObject("ok", 1);       
             }
             else{
                 return Tools.creationInfoJSONObject("ko", 14);
             }    
             
         }catch(Exception e){
          
             e.getMessage();    
             return Tools.creationInfoJSONObject("ko", 1000);
         }
     }

    
    /** TEST SI ILS SONT AMIS */
    
    public static boolean testFreindship(String from , String to){
    	boolean Ok = false;
    	try{
    		Connection co = Database.getMysqlConnection();
    		Statement st = co.createStatement();
    		int idf = Tools.getIdOfLogin(from);
    		int idt = Tools.getIdOfLogin(to);
    		//mise a jour de session 
    		Tools.udpdateAciv(from);
    		String query = "SELECT date FROM friends WHERE fromm=\""+idf+"\" and too=\""+idt+"\"";
    		System.out.println(query);
    		ResultSet  res =st.executeQuery(query);
    		if(res.next()){
    		//	n = Tools.creationJSONObject(res.getString("ami"), 1);	
    			Ok = true;
    		}
    		//n = Tools.creationJSONObject(res.getString("No relationShip"), 0);	
    		
    		st.close();
			co.close();
			return Ok;
			
		}catch(Exception e ){
			e.getMessage();
			//return Tools.creationJSONObject("PROBLEME DE CONNEXION ",3);
			return Ok;
		}
    	
    }
    
   
    
    public static JSONObject deleteFriend(String from , String to){
    	
    	JSONObject f = new JSONObject();
	    f= Tools.testDejaLogue(from);
	    System.out.println(f);
	    /** TEST SI LOGUE **/
	    if(!Tools.testDejaLogueBool(f)){
	    	 return Tools.creationInfoJSONObject("ko", 22);
	    }
    	
    	if(!testFreindship(from , to)){
    		return Tools.creationInfoJSONObject("ko", 17);
    	}
    	
       	
    	try{
    		Connection co = Database.getMysqlConnection();
    		Statement st = co.createStatement();
    		int idf = Tools.getIdOfLogin(from);
    		int idt = Tools.getIdOfLogin(to);
    		
    		//mise a jour de session 
    		Tools.udpdateAciv(from);
    		
    		String query = "DELETE FROM friends where fromm =\""+idf+"\" and too= \""+idt+"\"";
    		System.out.println(query);
    		int res = st.executeUpdate(query);
 
    		st.close();
            co.close();
 
            if (res==1){    
                return Tools.creationInfoJSONObject("ok", 1);        
            }
            else{
                return Tools.creationInfoJSONObject("ko", 16);
            }    
            
        }catch(Exception e){
         
           return Tools.creationInfoJSONObject("ko", 1000);
        }
    		
    	
    }
}

