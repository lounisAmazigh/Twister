package serviceTools;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Locale;

import BD.Database;

public class Tools {
	
	public Tools(){
	
	}
	

 /* Test si l'utilisateur existe deja
	 * @param login
	 * @return False si il existe True sinon
	 */
	public static boolean testUserNotExist(String login){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "SELECT login FROM users WHERE login=\""+login+"\"";
			ResultSet res = st.executeQuery(query);

			if(res.next() ){
					st.close();
					co.close();
					return false;
				}				
			else{
				st.close();
				co.close();
				return true;
			}
			
			
			
		}catch(Exception e){
			e.getMessage();
			return false;
		}
		
	}
	/**
	 * TEST SI L'ADRESSE EXISTE DEJA
	 * @param adresse
	 * @return FALSE si elle existe True sinon
	 */
	public static boolean testAdresseNotExist(String adresse){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "SELECT login FROM users WHERE adresse=\""+adresse+"\"";
			ResultSet res = st.executeQuery(query);
		
			if(res.next()) {
				st.close();
				co.close();
				return false;
			}
			
			else{
				st.close();
				co.close();
				return true;
			}
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	

	
	
	/**
	 * RECUPERE ID DU LOGIN
	 * @param login
	 * @return
	 */
	public static boolean activ(String login){
		java.sql.Timestamp last = Tools.getTimestamap();
		java.sql.Timestamp now = Tools.getTimestamap();
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "Select debut from session WHERE id = \""+Tools.getIdOfLogin(login)+"\"";
			ResultSet res = st.executeQuery(query);
			while(res.next()){
				last = res.getTimestamp("debut");	
			}
			if (now.getTime()-last.getTime() < 900000){
				co.close();
				st.close();
				return true;
			}else{
				co.close();
				st.close();
				return false;
			}
		}catch(Exception e ){
			e.getMessage();
			return false;
		}		
}
	public static int desconect(String login){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			int id = Tools.getIdOfLogin(login); 
			String query = "DELETE FROM session WHERE id=\""+id+"\"";
			int res = st.executeUpdate(query);
			st.close();
			co.close();
			if(res == 1){
				return 1;
				
			}
			else
				return 0;
		}
		catch (Exception e) {
			return 0;
			// TODO: handle exception
		}
			
	}
	public static void udpdateAciv(String login){
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "UPDATE session SET debut=\""+Tools.getTimestamap()+"\" WHERE id="+Tools.getIdOfLogin(login);
			int res = st.executeUpdate(query);
			if(res == 0){
	            String quer = "INSERT INTO session values(\""+Tools.keyGenerator()+"\",\""+Tools.getIdOfLogin(login)+"\",\""+Tools.getTimestamap()+"\",\""+0+"\")";
	            int ress = st.executeUpdate(query);
	            st.close();
	            co.close();
			}
			
			co.close();
			st.close();
		}catch(Exception e ){
			e.getMessage();
		}
		
	}
	
	public static int getIdOfLogin(String login){
		int res1=-1;
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "Select idUser from users WHERE login = \""+login+"\"";
			ResultSet res = st.executeQuery(query);
			while(res.next()){
				res1 = res.getInt("idUser");	
			}
			
			co.close();
			st.close();
			return res1;
		}catch(Exception e ){
			e.getMessage();
			return -1;
		}
			
}
	public static String getLogOfId(int Id){
		String res1=null;
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			String query = "Select login from users WHERE idUser = \""+Id+"\"";
			ResultSet res = st.executeQuery(query);
			while(res.next()){
				res1 = res.getString("login");	
			}
			
			co.close();
			st.close();
			return res1;
		}catch(Exception e ){
			e.getMessage();
			return null;
		}
			
}
	
	
	/**
	 *  TEST SI DEJA EN LIGNE 
	 * @param login
	 * @return
	 */
	
	
	public static JSONObject testDejaLogue(String login){
		JSONObject n = new JSONObject();
		try{
			Connection co = Database.getMysqlConnection();
			Statement st = co.createStatement();
			int id = Tools.getIdOfLogin(login);
			
				String query = "SELECT cle FROM session WHERE id = "+id;
				System.out.println(query);
				ResultSet res = st.executeQuery(query);
				if(res.next()){
					n = Tools.creationJSONObject(res.getString("cle"), 1);
					st.close();
					co.close();
					return n;
				}
				else {
					n = Tools.creationJSONObject("ko",0);
					st.close();
					co.close();
					return  n;
				}
		}catch(Exception e ){
			e.getMessage();
			return Tools.creationJSONObject("PROBLEME DE CONNEXION ",3);
		}
	}
	
	public static boolean testDejaLogueBool(JSONObject login){
		  try {
				if(login.getInt("1")== 1){
				    return true;
				}
				else 
					return false;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
	}
	
 
    
    /**
		génerateur de date
     */
    public static java.sql.Timestamp getTimestamap() {
		long time=System.currentTimeMillis();
		return new java.sql.Timestamp(time);
	}
    


    
   
  

	
	/**
 	 *  Creation d'un JSONOBJECT
	 * @param s
	 * @param i
	 * @return
	 */
	
    
    public static JSONObject creationJSONObject(String s , int i){
		JSONObject n = new JSONObject();
		try{
			n.put("0",s);
			n.put("1", i);
		}catch (JSONException e) {
			 // TODO Auto-generated catch block
			e.printStackTrace();
			} 
		return n;
	}
    public static JSONObject creationInfoJSONObject(String s , int i){
  		JSONObject n = new JSONObject();
  		try{
  			n.put(s,i);
  		}catch (JSONException e) {
  			 // TODO Auto-generated catch block
  			e.printStackTrace();
  			} 
  		return n;
  	}
    /**
     * generateur de key
     */
    public static StringBuilder keyGenerator(){
    	String s = "azertyuiopmlkjhgfdsqwxcvbnAZERTYUIOPMLKJHGFDSQWXCVBN0123456789*%$=+*-";
		StringBuilder key = new StringBuilder();
		
		for(int i=0; i<32; i++){
			int alea = (int)(Math.random()*62);
			key.append(s.charAt(alea));
		}
		return key;
    }

	
}
