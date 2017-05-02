package serviceTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import BD.Database;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Locale;

public class User_Tools {
	
	/**
	 * Test les attributs create login CreateUSer
	 * 
	 * @return true si tout se passe bien false sinon
	 */
	public static boolean testAttributsUsers(String nom ,  String prenom , String login , String mdp , String adresse){
		if((nom ==" "||nom == null)||(prenom ==" "||prenom == null)||(login ==" " || login== null)||(mdp==" " || mdp == null) ||(adresse ==" " || adresse == null)){
			return false;
		}
		else
			return true;
	}
	
	/**
	 * test attributs null login
	 * @return
	 */
	public static boolean testAttributsLogin(String login , String mdp ){
		if((login ==" " || login== null)||(mdp==" " || mdp == null)){
			System.out.println("ERROR ATTRIBUTS NUL OU VIDE");
			return false;
		}else return true;
	}
	/**
	/**
	 * test attribut null logout
	 */
	public static boolean testAttributsLogout(String login ){
		if(login ==" " || login== null){
			System.out.println("ERROR ATTRIBUTS NUL OU VIDE");
			return false;
		}else return true;
		
	}
	
	/**
	 * TEST LA VALIDITE DU MOT DE PASSE


	 * @param login
	 * @param mdp
	 * @return
	 */
	
	public static JSONObject test_mdp(String login , String mdp){
		JSONObject n = new JSONObject();
		Connection co=null;
		Statement st=null;
		try{
			co = Database.getMysqlConnection();
			st = co.createStatement();
			
			String query = "SELECT pwd FROM users WHERE login=\""+login+"\"";
			ResultSet res = st.executeQuery(query);
			String pwd ="";
			if(res.next()){
				pwd = res.getString("pwd"); 
			}
			if(pwd.equals(mdp)){
				n = Tools.creationJSONObject("ok", 10);
			}
			
			else{
				n = Tools.creationJSONObject("ko", -10);
			}			 
			
		
			if(st!=null) st.close();
			if(st!=null) co.close();
			return n;
			
		}catch(Exception e){
			e.getMessage();
		}
		return n;
	
		
	}
	
	/**
	 * INSERER DANS LA TABLE USERS

	 * @param obj
	 * @return
	 */
	
	public static JSONObject insertUser(JSONObject obj)  {
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "INSERT INTO users values(null,\""+obj.getString("login")+"\",\""+obj.getString("mdp")+"\",\""+obj.getString("nom")+"\",\""+obj.getString("prenom")+"\",\""+obj.getString("adresse")+"\")";
			int res = st.executeUpdate(query);
			st.close();
			co.close();
			if (res==1){
				return Tools.creationInfoJSONObject("ok", 1);
				
			}
			
			else{
				return Tools.creationInfoJSONObject("ko", 6);
			}
		}catch(Exception e){
			System.out.println("chargement dbb impossible !");
			e.getMessage();	
			return Tools.creationInfoJSONObject("ko", 1000);
		
		}
	}

	/**
     * CREATION D'UNE SESSION 
     * @throws JSONException
     */

    public static JSONObject CreateSession(String login){
        JSONObject n = new JSONObject();
        n= Tools.testDejaLogue(login);
        try {
			if(n.getInt("1")== 1){
				int id = Tools.getIdOfLogin(login);
				String key = n.getString("0");
				JSONObject nn = new JSONObject();
				
				nn.append("follows", follows(id));
				nn.append("id", id);
				nn.append("login", login);
				nn.append("key",key);
				nn.append("abonne", abonne(id));
				nn.append("twit", Message_Tools.nombreTwit(id));
			    return nn;
			}else{
				StringBuilder key = Tools.keyGenerator();
			    int id = Tools.getIdOfLogin(login);
			    java.sql.Timestamp date = Tools.getTimestamap();
			    JSONObject session = new JSONObject();
			    
			    
			    session.put("key", key);
			    session.put("id", id);
			    session.put("date", date );
			    session.put("root", 0);
			
			    JSONObject se = insertInSession(session);
			    se.append("id", id);
			    se.append("login", login);
			    se.append("follows",follows(id) );
			    se.append("abonne", abonne(id));
				se.append("twit", Message_Tools.nombreTwit(id));
			    return se;
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return Tools.creationInfoJSONObject("ko", 1000);
		}
       }
    
    /**
     * insert in session
     */
    public static JSONObject insertInSession(JSONObject session){
        
        try{
        	JSONObject n = new JSONObject();
        	Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
            String query = "INSERT INTO session values(\""+session.getString("key")+"\",\""+session.getString("id")+"\",\""+session.getString("date")+"\",\""+session.getString("root")+"\")";
            int res = st.executeUpdate(query);
            st.close();
            co.close();
            if (res==1){ 
            	n.append("key",session.getString("key"));
                return n;        
            }
            else{
                return Tools.creationJSONObject("ko", 1000);
            }    
            
        }catch(Exception e){
         
            e.getMessage();    
            return Tools.creationJSONObject("bdd", 1000);
        }
    }
    
    
    /*
		CREATION D'UN UTILISATEUR 
	*/
	
    public static JSONObject createUser(String nom ,  String prenom , String login , String mdp , String adresse){
    	JSONObject obj = new JSONObject();
	 	try {	
	 		obj.put("nom", nom);
	 		obj.put("prenom", prenom);
	 		obj.put("login", login);
	 		obj.put("mdp", mdp);
	 		obj.put("adresse", adresse);
	 		return obj;
			
	 	} catch (JSONException e) {
		 // TODO Auto-generated catch block
		e.printStackTrace();
	}
	 	return obj;
    }
    
    /**
     * LOGOUT UN UTILISATEUR D'UNE SESSION
     * A TESTER
     * 
     */
	public static JSONObject logOut_user(String login){
		int id = Tools.getIdOfLogin(login);
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "DELETE FROM session WHERE id=\""+id+"\"";
			int res = st.executeUpdate(query);
			st.close();
			co.close();
			if(res == 1){
				return Tools.creationInfoJSONObject("ok", 1);
				
			}
			else{
				return Tools.creationInfoJSONObject("ko", 5);
				  
			}
	}catch(Exception e){
		System.out.println("chargement dbb impossible !");
		e.getMessage();	
		return Tools.creationInfoJSONObject("ko", 1000);
	
	}
}
    /*
     * permet de recupérer la liste des follows de l'utilisateur X
     */
	public static JSONArray follows(int id)  {
		JSONArray follows = new JSONArray();
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "SELECT * FROM friends WHERE fromm = "+id;
			ResultSet res = st.executeQuery(query);
			
			while(res.next()){
				JSONObject follow = new JSONObject();
				follow.put("loginfriend",Tools.getLogOfId(res.getInt("too")));
				follow.put("idFriend",res.getInt("too"));
				follows.put(follow);
			}
			
			st.close();
			co.close();
			return follows;
			
		}catch(Exception e ){
			e.getMessage();
			return null;//Tools.creationInfoJSONObject("ko", 1000);
		}
	}
	public static int abonne(int id)  {
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "SELECT * FROM friends WHERE too = "+id;
			ResultSet res = st.executeQuery(query);
			int i=0;
			while(res.next()){
				i++;
			}
			
			st.close();
			co.close();
			return i;
			
		}catch(Exception e ){
			e.getMessage();
			return 0;//Tools.creationInfoJSONObject("ko", 1000);
		}
	}
   
	/** RECUPERER TOUT LES LOGIN DES UTILISATEURS **/
	
	public static JSONObject tout_les_users(){
		JSONObject n = new JSONObject();
		int i =0;
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "Select login from users";
			ResultSet res = st.executeQuery(query);
			while(res.next()){
				n.put(""+i, res.getString("login"));
				i++;
			}
			n.put("taille", i);
			n.put("ko", 8);
			co.close();
			st.close();
			return n;
		}catch(Exception e ){
			e.getMessage();
			Tools.creationInfoJSONObject("ko", 1000);
		}
		
		
		return n;
	}


}
