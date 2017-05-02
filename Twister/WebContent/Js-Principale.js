/**
 * 
 */




function Makeprofil(){
	$("#mes_posts").remove();
}

function MakePanelActu(){

	var p = " <div id=\"mes_posts\"> "+
				"<label for=\"text-area\"> <img src=\"images/logo_p.gif\" alt=\"avatar_login\"/></label>"+
				"<textarea rows=\"7\" cols=\"65\" name=\"text-area\" id=\"text-area\" > what's up ?</textarea>"+
				"<div > <input type=\"submit\" id=\"publier\" value=\"publier\" onclick=\"functionPublier(); return false;\"/>"+
			"</div>"+
			"<div id=\"messages\"> "+
			"</div>";

	
	$("#middle").append(p);
	alert("ok2");
}

function gethtml(message){
	env.id_msg++;
	var s =
		"<div id =\"posts\" class=\"list_post\" >"+
			"<div id=\""+env.id_msg+"\" class=\""+env.id_msg+"\">"+
				"<p id=\"log_friend\">  login_freindss </p>"+ 
				"<img src=\"images/logo_p.gif\" alt=\"avatar login\"/> <spam id=\"date\"> publié le 12/03/2017 à 20h30 </spam>"+
				"<p id=\"post-text\">"+message+" </p>"+
				"<div id=\"separator\"> </div>"+
				"<input id=\"like\" type=\"button\" value=\"j'aime\"/> "+
				"<input id=\"comment\" type=\"button\" value=\"commenter\" onclick=\"functionComment("+env.id_msg+"); return false;\"/>"+ 
				"<input id=\"delete\" type=\"button\" value=\"delete\" onclick=\"functionDeleteMsg("+env.id_msg+"); return false;\"/>"+ 
				"<input id=\"afficher\" type=\"button\" value=\"+\" onclick=\"functionAfficherComs("+env.id_msg+"); return false;\"/>"+
				"<input id=\"cacher\" type=\"button\" value=\"-\" onclick=\"functionCacherComs("+env.id_msg+"); return false;\"/>"+
				"<div id=\"Commentaires_"+env.id_msg+"\">"+
					"<textarea rows=\"1\" cols=\"75\" name=\"text-area-coms\" id=\"text-area-coms\" > comments</textarea> </div> </div>	"+
					
				"</div>"+
			"</div>"+
		"</div>";
	return s;
}

function getcomments(message){
	env.id_Comment++;
	var s = " <div id=\"list_coms\">"+
			"<div id=\"comments_"+env.id_Comment+"\"> <p id=\"log_friend_coms\">  login_freinds :"+message+"</p>"+	
				"<p id=\"date\"> publié le 12/03/2017 à 20h30 </p>"+
				"<input id=\"delete\" type=\"button\" value=\"delete\" onclick=\"functionDeleteCmt("+env.id_Comment+"); return false;\"/>"+"</div></div>"; 
	return s;
	

} 


function functionDeleteMsg(id_msg){
		$("."+id_msg).remove();
}
function functionDeleteCmt(id_Comment){
	$("#comments_"+id_Comment).remove();
}

function functionComment(id_msg){	
	var style = $("#Commentaires_"+id_msg).attr('style' , 'display:block');
	var h =  getcomments($("#text-area-coms").val());
	
	$("#Commentaires_"+id_msg).prepend(h);
	
}

function functionPublier(){
	var h = gethtml($("#text-area").val());
	$("#messages").prepend(h);
}

function functionAfficherComs(id_msg){
	var style = $("#Commentaires_"+id_msg).attr("style");
	if(style =="display:none" || style == undefined){
		 $("#Commentaires_"+id_msg).attr('style' , 'display:block');
	}
}
function functionCacherComs(id_msg){
	var style = $("#Commentaires_"+id_msg).attr("style");
	if(style =="display:block" || style == undefined){
		$("#Commentaires_"+id_msg).attr('style' , 'display:none');
	}
}
	
