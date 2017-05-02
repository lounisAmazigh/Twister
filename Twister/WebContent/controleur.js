	/**
 *
 */

var urlServeur = "http://li328.lip6.fr:8280/111T/";

function Message(id , auteur , texte , date , comments){

	this.id = id;
	this.auteur = auteur;
	this.texte = texte;
	this.date = date;
	if(comments == undefined){
		comments=[];
	}
	this.comments = comments;

}

function Commentaire(id , auteur , texte , date,idMessage){

	this.id = id;
	this.auteur = auteur;
	this.texte = texte;
	this.date = date;
	this.idMessage = idMessage;

}

Message.prototype.getHtml = function(){


	var s= "<div id =\"posts\" class=\"list_post\" >"+
		"<div id=\"message_"+this.id+"\" class=\"Message\">"+
			"<p id=\"log_friend\" onclick = \"functionGoToProfil('"+this.auteur.login+"');\">"+this.auteur.login+"</p>"+
			"<img src=\"images/logo_p.gif\" alt=\"avatar login\"/> <spam id=\"date\"> publié le"+this.date+"</spam>"+
			"<p id=\"post-text\">"+this.texte+" </p>"+
			"<div id=\"separator\"> </div>"+
			"<input id=\"comment\" type=\"button\" value=\"commenter\" onclick=\"functionComment("+this.id+"); return false;\"/>"+
			"<input id=\"delete\" type=\"button\" value=\"delete\" onclick=\"functionDeleteMsg("+this.id+",'"+this.auteur.login+"'); return false;\"/>"+
			"<input id=\"afficher\" type=\"button\" value=\"+\" onclick=\"functionAfficherComs("+this.id+"); return false;\"/>"+
			"<input id=\"cacher\" type=\"button\" value=\"-\" onclick=\"functionCacherComs("+this.id+"); return false;\"/>"+
			"<div id=\"Commentaires_"+this.id+"\">"+
				"<textarea rows=\"1\" cols=\"75\" name=\"text-area-coms\" id=\"text-area-coms\" > comments</textarea> <button onclick=\"functionComment("+this.id+"); return false;\"> + </button>  </div> </div>	"+

			"</div>"+
		"</div>"+
	"</div>";


	return s;
}


Commentaire.prototype.getHtml = function(){
	var s= " <div id=\"list_coms\" class=\""+this.idMessage+"\">"+
		"<div id=\"comments_"+this.id+"\" class=\""+this.idMessage+"\"> <p id=\"log_friend_coms\" onclick=\"functionGoToProfil('"+this.auteur.login+"');\">"+this.auteur.login+":"+this.texte+"</p>"+
			"<p id=\"date\">"+this.date+"</p>"+
			"<input id=\"delete\" type=\"button\" value=\"delete\" onclick=\"functionDeleteCmt("+this.id+",'"+this.auteur.login+"'); return false;\"/>"+"</div></div>";
	return s;
}
function revival(key , value){
	if(value.comments != undefined){
		var c = new Message(value.id , value.author , value.texte , value.date , value.comments);
		return c;
	}
	//machi da message
	else if(value.texte != undefined){
		var c = new Commentaire(value.id , value.author , value.texte , value.date, value.idMessage);
		return c;
	}

	else if(key == 'date'){
		var d = new Date(value);
		console.log(d);
		return d;
	}
	

	else
		return value;

}

function setVirtualMessage(){

	var localdb = [];
	var follows = [];

	var user1 = {"id":1, "login":"sly"};
	var user2 = {"id":2, "login":"sdq"};
	var user3 = {"id":3, "login":"qs"};
	var user4 = {"id":4, "login":"zae"};
	var user5 = {"id":5, "login":"ezae"};

	follows[1] = new Set();
	follows[1].add(2);
	follows[1].add(4);
	follows[1].add(5);

	follows[2] = new Set();
	follows[2].add(4);
	follows[2].add(3);
	follows[2].add(5);

	follows[3] = new Set();
	follows[3].add(4);
	follows[3].add(2);
	follows[3].add(1);

	follows[4] = new Set();
	follows[4].add(3);
	follows[4].add(2);
	follows[4].add(1);

	follows[5] = new Set();
	follows[5].add(3);
	follows[5].add(2);
	follows[5].add(1);


	var com1 = new Commentaire(1,user1,"salut",new Date());
	var com2 = new Commentaire(2,user2,"salut2",new Date());
	var com3 = new Commentaire(3,user3,"salut3",new Date());
	var com4 = new Commentaire(4,user4,"salut4",new Date());
	var com5 = new Commentaire(5,user5,"salut5",new Date());
	var com6 = new Commentaire(1,user1,"salut6",new Date());
	var com7 = new Commentaire(2,user2,"salut7",new Date());
	var com8 = new Commentaire(3,user3,"salut8",new Date());
	var com9 = new Commentaire(4,user4,"salut9",new Date());
	var com10 = new Commentaire(5,user5,"salut10",new Date());

	localdb[1] = new Message(1,user1,"1er twit1",new Date(),[com2,com3]);
	localdb[2] = new Message(2,user2,"1er twit2",new Date(),[com3,com1]);
	localdb[3] = new Message(3,user3,"1er twit3",new Date(),[]);
	localdb[4] = new Message(4,user4,"1er twit4",new Date(),[com6,com5]);
	localdb[5] = new Message(5,user5,"1er twit5",new Date(),[com7,com8]);
	localdb[6] = new Message(1,user1,"1er twit1",new Date(),[com1,com10]);
	localdb[7] = new Message(2,user2,"1er twit2",new Date(),[com6]);
	localdb[8] = new Message(3,user3,"1er twit3",new Date(),[com4,com7,com8]);

}

function getFromlocalDb(from,idMin, idMax , nbMax){
	 var tab = [];
	 var nb =0;
	 var f = undefined;
	 if(from >0){
		 f = follow[from];
		 if(f == undefined){
			 f = env.sort();
		 }
	 }
	 for(var i = localdb.length-1;i>=0;i--){
		 if((nbMax>=0) && (nb >= nbMax)){
			 break;
		 }
		 if(localdb[i] == undefined){continue;}
		 if(((idMax < 0)||(localdb[i].id<idMax)) && (localdb[i].auteur.id> idMin)){
			 if((from <0) || (localdb[i].id = from) || f.has(localdb[i].auteur.id)){
				 tab.push(localdb[i]);
				 nb++;
				 return tab;
			 }
		 }
	 }

}

function init(){
	noConnection = true;
	env = new Object();
	env.id = -1;
	env.msgs = [];
	env.fromId = -1;
	env.fromLogin = undefined;
	env.minId = -1;
	env.maxId = -1;
	env.login = null;
	env.auteur = null;
	env.follows = null;
	env.key = null;
	env.follows= new Set();
	env.nbrMsg = 0;
	env.abonne = null;
	setVirtualMessage();
}
/*
##################################################################################
***********************************Connexion**************************************
##################################################################################
*/
function connecte(log , pass){
	$.ajax({
		type: "GET",
		url:"user/login",
		data:"login="+log+"&&mdp="+pass,
		dataType:"JSON",
		success:function(rep){
			connecteResponse(rep);
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert('errr '+textStatus);
		}
	});
}

function Connexion(f){
	  var login = f.log;
	  var mdp = f.mdp;
	  var ok = verificationConnexion(login , mdp);
	  if(ok){
		  connecte(login , mdp);
	  }
}
function verificationConnexion(login , pass){
	if(login =='' | pass ==''){
		alert('informations manquantes!!');
		return false;
	}
	return true;
}

function connecteResponse(rep){
	 if(rep.ko == undefined){
		 env.id = rep.id;
		 env.key = rep.key;
		 env.login = rep.login;
		 env.nbrMsg = rep.twit;
		 env.abonne = rep.abonne;
		 env.follows = new Set();

		 for(var i=0; i< rep.follows[0].length;i++){
			 env.follows.add(rep.follows[0][i]);
		 }
		 if(!noConnection){
			 if(follows[rep.id] == undefined){
				 follows[rep.id]= new Set();
			 }
			 for(var i=0; i< rep.follows.length;i++){
				 follows[rep.id].add(rep.follows[i]);
			 }
		 }
		
		 makeMainePanel();

	 }
	 else
		 	foncErreur(rep.ko);

}
function traitement_connexion(){
	user= new Object();
	$('#formulaire').serialize();
	user.log = $("#connexion_log").val();
	user.mdp =  $("#connexion_pwd").val();
	Connexion(user);
}

/*
##################################################################################
***********************************Creation***************************************
##################################################################################
*/

function create(nom , prenom ,login ,mdp ,adresse){
	$.ajax({
		type: "GET",
		url:"user/create",
		data:"nom="+nom+"&&prenom="+prenom+"&&login="+login+"&&mdp="+mdp+"&&adresse="+adresse,
		dataType:"JSON",
		success:function(rep){
		
			createResponse(rep);
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert('errr '+textStatus);
		}
	});
}

function creation(f){
		var nom = f.nom;
		var prenom = f.prenom;
		var login = f.log;
		var mdp = f.mdp;
		var mdpv = f.mdpv;
		var adresse = f.email;
		var ok = verificationInformation(nom,prenom,login , mdp,mdpv,adresse);
		if(ok){
		  create(nom,prenom,login , mdp,adresse);
		}

}

function verificationInformation(nom,prenom,login , mdp,mdpv,adresse){
	if(nom =='' || prenom == '' || login ==='' || mdp ==='' || adresse =='' || mdpv ==''){
		alert('informations manquantes!!');
		$("#idError").html('');
 	  $("#idError").html("<h2>informations manquantes!!</h2>");
		return false;
	}
	else if(mdp.lenght < 6){
		$("#idError").html('');
 	 	$("#idError").html("<div id=\"idError\" style =\"color: red\"><h2>Votre mot de passe est trop court !!</h2></div>");
		return false;
	}
	else if(mdp != mdpv){
		$("#idError").html('');
 	 	$("#idError").html("<div id=\"idError\" style =\"color: red\"><h2>Votre mot de passe et sa confirmation ne sont pas identiques !!</h2></div>");
		return false;
	}
	
	return true;
}

function createResponse(rep){
	 if(rep.ko == undefined){
		 if(rep.ok == 1){
			 	alert("Koulchi Mebrouk");
		 		makeSignInPanel();
		 }
	 }else {
		 	
		  	foncErreur(rep.ko);
	 }
	 if(!noConnection){
			 if(follows[rep.id] == undefined){
				 follows[rep.id]= new Set();
			 }
			 for(var i=0; i< rep.follows.length;i++){
				 follows[rep.id].add(rep.follows[i]);
			 }
		 }
}
function traitement_inscription(){

	user= new Object();
	user.prenom = $("#creat_prenom").val();
	user.nom =  $("#creat_nom").val();
	user.log = $("#creat_log").val();
	user.email =  $("#creat_email").val();
	user.mdp =  $("#creat_pwd").val();
	user.mdpv =  $("#creat_pwdR").val();

	creation(user);
}

/*
##################################################################################
***********************************logOut*****************************************
##################################################################################
*/

function deco(login){
	$.ajax({
		type: "GET",
		url:"user/out",
		data:"login="+login,
		dataType:"JSON",
		success:function(rep){
			decoResponse(rep);
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert('errr '+textStatus);
		}
	});
}

function deconnexion(f){
	  var login = f.login;
	  var ok = verificationInformation(login);
	  if(ok){
		  deco(login);
	  }
}
function verificationLog(login){
	if(login ===''){
		return false;
	}
	return true;
}

function decoResponse(rep){
	 if(rep.ko == undefined){
		 if(rep.ok == 1){
			 	alert("ihi qim dilaman");
				init();
				makeSignInPanel();
		 }
	 }else {
		  	foncErreur(rep.ko);
	 }
	 if(noConnection){
			 if(follows[rep.id] == undefined){
				 follows[rep.id]= new Set();
			 }
			 for(var i=0; i< rep.follows.length;i++){
				 follows[rep.id].add(rep.follows[i]);
			 }
		 }
}
function logOut(){
	logout = new Object();
	logout.login = env.login;
	deconnexion(logout);
}

/*
##################################################################################
***********************************!!!!!!*****************************************
##################################################################################
*/

function makeMainePanel(){
	env.minId = -1;
	
	document.body.innerHTML = "";
	var mainPanel = '<!DOCTYPE html>'+
		'<html>'+
			'<head>'+
				'<meta charset="UTF-8">'+
				'<title>Twister</title>'+
				'<link rel="stylesheet" href="design.css" />'+
				'<script type="text/javascript" src="controleur.js"></script>'+
				'<script src="jquery.js"></script>'+
			'</head>'+

			'<script>'+

			'</script>'+
			'<header>'+
			'<div id="barre_accueil">'+
				'<div id ="logo">'+
						'<img src="images/logo.png" alt="logo twistter"/>'+
				'</div>'+
				'<div id="recherche">'+
				'	<input id="recherche_amis" type="text"  size="35" />'+
				'	<input  type="submit" value= "rech"  onclick="recherche_amis(); return false;"  />'+
				'</div>'+
				'<div id="boutton_surf">'+
				'		<li id="accueil"> <div onclick="makeMainePanel();"> accueil </div> </li>'+
				'		<li > <div onclick="makeProfilPanel();"> profil </div> </li>'+
				'		<li id="logout"> <div onclick="logOut();"> logout </div> </li>'+
		'		</div>'+
		'	</div>'+
		'</header>'+
		'<body>'+
			'<div id="Conteneur">'+
				'<ul id="box_left">'+
					'<li id = "profil">'+
						'<div id ="logo" onclick="functionGoToProfil("'+env.login+'");"> <img src="images/avatar.gif" alt="avatar login"/> </div>'+
						'<div id ="login" onclick="functionGoToProfil("'+env.login+'");"> '+env.login+'</div>'+
						' <button id="RefreshButton" type="button"  onclick="makeMainePanel();"> Refresh</button>'+
					'</li>'+
					'<li id = "tweets">'+
					'	<h5 id="twit">tweets :'+env.nbrMsg+' </h5>'+
					'</li>'+
					'<li id = "nbr_flowers">'+
						"<h5 id=\"followers\"> followers :"+env.follows.size+"</h5>"+
					'</li>'+
					'<li id = "nbr_abonnee">'+
					'	<h5 id="abonnées">abonnées : '+ env.abonne +'</h5>'+
				'	</li>'+
			'	</ul>'+
			'<div id="box_right">'+
				'<div id = "List_friends">'+


				'</div>'+
			'</div>'+
			'<div id=\'middle\'>'+
				'<div id="mes_posts">'+
					'<label for="text-area"> <img src="images/logo_p.gif" alt="avatar_login"/></label>'+
					'<textarea rows="7" cols="65" name="text-area" id="text-area" > what\'s up ?</textarea>'+
					'<div > <input type="submit" id="publier" value="publier" onclick="functionPublier(); return false;"/> </div>'+
				'</div>'+
				'<div id="messages">'+
				'</div>'+
			
			'</div>'+
	'</body>'+
	'</html>';

	$('body').append(mainPanel);
	
	/** AFFICHER LES FOLLOWERS SUR LA BOX DE DROITE **/
	env.follows.forEach(function(value) {
		 var ami = value.loginfriend;
		 console.log(ami);
		 addFriendHtml(ami);
	});
	
	/** AFFICHER LES 10 DERNIER MESSAGES **/

	refreshMessage();
	

	}
////////////////////////////////////////////////////////////////////////////////////////////
function makeProfilPanel(){
	env.minId = -1;
	
	document.body.innerHTML = "";
	var mainPanel = '<!DOCTYPE html>'+
		'<html>'+
			'<head>'+
				'<meta charset="UTF-8">'+
				'<title>Twister</title>'+
				'<link rel="stylesheet" href="design.css" />'+
				'<script type="text/javascript" src="controleur.js"></script>'+
				'<script src="jquery.js"></script>'+
			'</head>'+

			'<script>'+

			'</script>'+
			'<header>'+
			'<div id="barre_accueil">'+
				'<div id ="logo">'+
						'<img src="images/logo.png" alt="logo twistter"/>'+
				'</div>'+
				'<div id="recherche">'+
				'	<input id="recherche_amis" type="text"  size="35" />'+
				'	<input  type="submit" value= "rech"  onclick="recherche_amis(); return false;"  />'+
				'</div>'+
				'<div id="boutton_surf">'+
				'		<li id="accueil"> <div onclick="makeMainePanel();"> accueil </div> </li>'+
				'		<li > <div onclick="makeProfilPanel();"> profil </div> </li>'+
				'		<li id="logout"> <div onclick="logOut();"> logout </div> </li>'+
		'		</div>'+
		'	</div>'+
		'</header>'+
		'<body>'+
			'<div id="Conteneur">'+
				'<ul id="box_left">'+
					'<li id = "profil">'+
						'<div id ="logo"> <img src="images/avatar.gif" alt="avatar login"/> </div>'+
						'<div id ="login"> '+env.login+'</div>'+
					'</li>'+
					'<li id = "tweets">'+
					'	<h5>tweets : '+env.nbrMsg +'</h5>'+
					'</li>'+
					'<li id = "nbr_flowers">'+
					'	<script> document.getElementById("nbr_flowers").innerHTML = "<h5> followers : '+env.abonne +'</h5>"; </script>'+
					'</li>'+
					'<li id = "nbr_abonnee">'+
					'	<h5>abonnées : '+env.follows.size+'</h5>'+
				'	</li>'+
			'	</ul>'+
			'<div id="box_right">'+
				'<div id = "List_friends">'+

				'</div>'+
			'</div>'+
			'<div id=\'middle\'>'+
				'<div id="messages">'+

				'</div>'+
			'</div>'+
	'</body>'+
	'</html>';

	$('body').append(mainPanel);
		env.follows.forEach(function(value) {
			 var ami = value.loginfriend;
			 addFriendHtml(ami);
		});
	MesMessages(env.login);
	}
/////////////////////////////////////////////////////////////////////////////

//	MesMessages(login);
function makeFreindProfilPanel(login){
	env.minId = -1;
	document.body.innerHTML = "";
	var mainPanel = '<!DOCTYPE html>'+
		'<html>'+
			'<head>'+
				'<meta charset="UTF-8">'+
				'<title>Twister</title>'+
				'<link rel="stylesheet" href="design.css" />'+
				'<script type="text/javascript" src="controleur.js"></script>'+
				'<script src="jquery.js"></script>'+
			'</head>'+

			'<script>'+

			'</script>'+
			'<header>'+
			'<div id="barre_accueil">'+
				'<div id ="logo">'+
						'<img src="images/logo.png" alt="logo twistter"/>'+
				'</div>'+
				'<div id="recherche">'+
				'	<input id="recherche_amis" type="text"  size="35" />'+
				'	<input  type="submit" value= "rech"  onclick="recherche_amis(); return false;"  />'+
				'</div>'+
				'<div id="boutton_surf">'+
				'		<li id="accueil"> <div onclick="makeMainePanel();"> accueil </div> </li>'+
				'		<li > <div onclick="makeProfilPanel();"> profil </div> </li>'+
				'		<li id="logout"> <div onclick="logOut();"> logout </div> </li>'+
		'		</div>'+
		'	</div>'+
		'</header>'+
		'<body>'+
			'<div id="Conteneur">'+
				'<ul id="box_left">'+
					'<li id = "profil">'+
						'<div id ="logo"> <img src="images/avatar.gif" alt="avatar login"/> </div>'+
						'<div id ="login"> '+login	+'</div>'+
					'</li>'+
					'<li id = "tweets">'+
					'	<h5> </h5>'+
					'</li>'+
					'<li id = "nbr_flowers">'+
					'	<h5> Vous êtes sur le profil de '+login+'</h5>'+
					'</li>'+
					'<li id = "nbr_abonnee">'+
					'	<h5> </h5>'+
				'	</li>'+
			'	</ul>'+
			'<div id="box_right">'+
				'<div id = "List_friends">'+
							"<button id=\"addFriend\" onclick=\"functionAjouterAmi('"+login+"');\"> ajouter comme ami </button>"+
							"<button id=\"deleteFriend\" onclick=\"functionSupprimerAmi('"+login+"');\"> Supprimer comme Ami </button>"+
				'</div>'+
			'</div>'+
			'<div id=\'middle\'>'+
				'<div id="messages">'+

				'</div>'+
			'</div>'+
	'</body>'+
	'</html>';

	$('body').append(mainPanel);
	MesMessages(login);
}
///////////////////////////////////////////////////////////////////////////////////

function makeRegesterPanel(){
	
	document.body.innerHTML = "";
	var from = "<div id=\"connexion_main\">"+
	"<h1>Enregistrement</h1>"+
	"<form class=\"connexion_main_form\" method=\"get\" accept-charset=\"utf-8\"  onsubmit=\"traitement_inscription(); return false;\" >"+
					"<div class=\"nomPrenom\">"+
				"<div class=\"connexion_ids\">"+
									"<div class=\"in\">"+
											"<label for=\"creat_prenom\">Prenom</label>"+
									"</div>"+
									"<input type=\"text\" id=\"creat_prenom\" name = \"prenom\" required/>"+
							"</div>"+
				"<div class=\"connexion_ids\">"+
									"<div class=\"in\">"+
											"<spam>Nom</spam>"+
									"</div>"+
									"<input type=\"text\" id=\"creat_nom\" name = \"nom\" required/>"+
							"</div>"+
					"</div>"+
					"<div class=\"info\">"+
				"<div class=\"connexion_info\">"+
									"<spam>Login</spam>"+
									"<input type=\"text\" id=\"creat_log\" name = \"log\" required/>"+
							"</div>"+
				"<div class=\"connexion_info\">"+
									"<spam>Email</spam>"+
									"<input type=\"email\" id=\"creat_email\" name= \"email\" required/>"+
							"</div>"+
							"<div class=\"connexion_info\">"+
									"<spam>Mot de Passe</spam>"+
									"<input type=\"password\" id=\"creat_pwd\" name = \"password\" required/>"+
							"</div>"+
							"<div class=\"connexion_info\">"+
									"<spam>Retapez</spam>"+
									"<input type=\"password\" id=\"creat_pwdR\" name = \"password\" required/>"+
							"</div>"+
					"</div>"+
					"<div class=\"button\">"+
						"<div class=\"connexion_button\">"+
									"<input type=\"submit\" id=\"inscription\" value = \"inscription\"/>"+
							"</div>"+
							"<div class=\"connexion_button\">"+
									"<input type=\"button\" id=\"annuler\" value = \"Annuler\" onclick=\" annuler();return false;\"/>"+
							"</div>"+
					"</div>"+
				"</form>"+
				"<div class=\"sign_in\" id=\"sign_in\"><spam><a href=\"javascript:makeSignInPanel()\">sign in</a></spam></div>"+
				"<div id=\"idError\"></div>"+
				"</div>";

	$('body').append(from);
}

function makeSignInPanel(){
	
	document.body.innerHTML = "";
	var signInpage = "<!DOCTYPE html>"+
	"<html>"+
	"<head>"+
		"<meta charset=\"utf-8\">"+
		"<title>Twister</title>"+
		"<link rel=\"stylesheet\" href=\"design.css\" >"+
		"<link rel=\"stylesheet\" href=\"css_crea.css\">"+
		"</head>"+
		"<div id=\"connexion_main\">" +
		"<h1>Bienvenu sur Twister</h1>" +
		"<h1>ouvrir une session</h1>" +
		"<form class=\"connexion_main_form\"  method=\"get\" accept-charset=\"utf-8\"  onsubmit=\"traitement_connexion(); return false;\">" +
			"<div class=\"connexion_ids\"><label for=\"log\">Login</label><input id=\"connexion_log\" type=\"text\" name = \"log\" required/></div>" +
			"<div class=\"connexion_ids\"><spam>Mot de Passe</spam><input id=\"connexion_pwd\" type=\"password\" name = \"password\" required/></div> " +
				"<div class=\"connexion_button\"><input id=\"connecte\" type=\"submit\" value = \"connexion\"/></div>" +
				"<div class=\"link\">"+
					"<div class=\"connexion_link\"><spam><a href=\"#\">Mot de passe perdu</a></spam></div>" +
					"<div class=\"connexion_link\" id=\"creation_compte\"><spam><a href=\"javascript:makeRegesterPanel()\">creation compte</a></spam></div>" +
				"</div>" +
		"</form>" +
		"<div id=\"idError\"></div>" +
	"</div>";

	$('body').append(signInpage);
}


/*
##################################################################################
**********************************MESSAGES****************************************
##################################################################################
*/


function MesMessages(login){
	 var noConnexion =false;
	 if(!noConnexion){
		 $.ajax({
			 type:"GET",
			 url:"message/liste",
			 data:"login="+login,
			 dataType:"JSON",
			 success:function(rep){
					
					completeMassageReponse(rep);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
				 alert(textStatus);
			 }
	});
	 }
	 else{
		 	completeMassageReponse()
	 }

}




 function completeMassageReponse(rep){
	 var newRep = getStringFromObject(rep);
	 var tab = JSON.parse(newRep,revival);
	 $("#showmore").remove();
	
	 for(var i=0;i<tab.length;i++){ 
		
		 $("#messages").prepend(tab[i].getHtml());
		 for(var j=0 ; j < tab[i].comments.length ; j++ ){
			 var c = tab[i].comments[j];

		 	 $("#Commentaires_"+tab[i].id).prepend(c.getHtml());
		 }
		 if((env.minId <0) || (tab[i].id < env.minId)){
			 env.minId = tab[i].id;
		 }
	 }

}


 function getStringFromObject(rep){
	 
	 var newRep ="[";
	 for(var i=0; i <rep.length ; i++){
	 newRep+="{ \"author\" : {\"idAuthor\" :"+rep[i].author.idAuthor+" , \"login\" : \""+rep[i].author.login+"\"}, \"id\" :"+rep[i].id+", \"texte\" :\""+rep[i].texte+"\" ,\"date\" : \""+rep[i].date+"\"";
	 if(rep[i].comments != undefined){
		 newRep+=", \"comments\" : [";
		 for(var j=0 ; j < rep[i].comments.length ; j++){
			 newRep+="{ \"idMessage\":"+rep[i].comments[j].idMessage+" ,\"author\" : {\"idAuthor\" :"+rep[i].comments[j].author.idAuthor+" , \"login\" : \""+rep[i].comments[j].author.login+"\"}, \"id\" :"+rep[i].comments[j].id+", \"texte\" :\""+rep[i].comments[j].texte+"\" ,\"date\" : \""+rep[i].comments[j].date+"\"";
			 if(j+1 ==  rep[i].comments.length)
			 		newRep+=" }";
			 	else
			 		newRep+=" },";
		 }
		 newRep+="]";
	}
	 	if(i+1 == rep.length)
	 		newRep+=" }";
	 	else
	 		newRep+=" },";
	 }
	 	newRep+="]";
	 return newRep;
 }

 function refreshMessage(){
	 $.ajax({
			 type:"GET",
			 url:"message/refresh",
			 data:"login="+env.login+"&idMin="+env.minId,
			 success:function(rep){
				 completeRefreshMassageReponse(rep);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
				 alert(textStatus);
			 }
		 });		
 }
 function completeRefreshMassageReponse(rep){

	 var tab = JSON.parse(rep,revival);

	 $("#showmore").remove();
	
	 for(var i=0;i<tab.length;i++){ 
		
		 $("#messages").append(tab[i].getHtml());
		 for(var j=0 ; j < tab[i].comments.length ; j++ ){
			 var c = tab[i].comments[j];

		 	 $("#Commentaires_"+tab[i].id).prepend(c.getHtml());
		 }
		 if((env.minId <0) || (tab[i].id < env.minId)){
			 env.minId = tab[i].id;
		 }
	 }
	 var btnRefresh = "<button id=\"showmore\" onclick=\"refreshMessage();\"> afficher plus </button>";
	 $("#middle").append(btnRefresh);
}
	 
 



 ///////////////////////////////////NEW MESSAGE
 function functionPublier(){
	 	var text = $("#text-area").val();
	 	newMessage(text);

	 }
 
 function newMessage(texte){
	 var message = texte;
	 var noConnexion =false;
	 if(!noConnexion){
	 $.ajax({
			 type:"GET",
			 url:"message/new",
			 data:"log="+env.login+"&message="+message,
			 dataType:"JSON",
			 success:function(rep){
					
					newMessageResponse(rep);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
				 alert("Il ya un probleme");
			 }
	 	});
	 }
	 else{
		 var auteur = new Object();
		 auteur.id = 2;
		 auteur.login = "lou";
		 var msg1 = new Message(1,auteur,message,new Date(),[]);
		 var html = msg1.getHtml();
		 $("#messages").prepend(html);
	 }

}
 function newMessageResponse(rep){

	 if(rep.ko == undefined){
	
		 var auteur = new Object();
		 auteur.id = env.id;
		 auteur.login = env.login;
		 env.nbrMsg++;
		 $("#twit").remove();
		 var h = "<h5 id=\"twit\"> tweets :"+env.nbrMsg+"</h5>";
		 $("#tweets").append(h);
		 var msg1 = new Message(rep.idMessage,auteur,rep.texte,rep.date,[]);
		 var html = msg1.getHtml();
		 $("#messages").prepend(html);
		 
	 }
	 else{
		alert(rep.ko);

	 }
 }
 
////////////////////////////////// DELETE MESSAGE
 function functionDeleteMsg(id_msg,log){
  	
  	if(log == env.login){
  		$("#message_"+id_msg).remove();
 		deleteMessage(id_msg);
  	}
 }

 function deleteMessage(message){

 	$.ajax({
 			type:"GET",
 			url:"message/delete",
 			data:"log="+env.login+"&idMessage="+message,
 			dataType:"JSON",
 			success:function(rep,message){
 				 deleteMessageResponse(rep,message);
 			},
 			error:function(jqXHR,textStatus,errorThrown){
 				alert("le messsage n'a pas était supprimer");
 			}
 	 });

 }
 function deleteMessageResponse(rep,id_msg){

 	if(rep.ko == undefined){
 		 env.nbrMsg--;
		 $("#twit").remove();
		 var h = "<h5 id=\"twit\"> tweets :"+env.nbrMsg+"</h5>";
		 $("#tweets").append(h);
 		
 	}
 	else{
 		alert("le message n'a pas etait supprimer");
 	}
 }

 ////////////////////////////////////////////////////////////////////////////////
 /////////////////////////////////////////////new COmment
 function functionComment(id_msg){
 	 var texte =  $("#text-area-coms").val();
 	 newComment(texte,id_msg);
 }

 
 function newComment(texte,id_msg){
	 var comment = texte;
	 var noConnexion =false;
	 if(!noConnexion){
	 $.ajax({
			 type:"GET",
			 url:"comment/new",
			 data:"login="+env.login+"&idmsg="+id_msg+"&comment="+texte,
			 dataType:"JSON",
			 success:function(rep){
					newCommentResponse(rep,id_msg);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
				 alert("Il ya un probleme");
			 }
	 	});
	 }
	 else{
		 var rep = new Object();
		 rep.idCommentaire =1;
		 rep.texte = texte;
		 rep.date = new Date();
		 newCommentResponse(rep,id_msg);
	 }

}
 function newCommentResponse(rep,id_msg){
	 if(rep.ko == undefined){
		 var auteur = new Object();
		 auteur.id = env.id;
		 auteur.login = env.login;
		 var cm1 = new Commentaire(rep.idCommentaire,auteur,rep.texte,rep.date,id_msg);
		 var html = cm1.getHtml();
		 console.log(html);
         var style = $("#Commentaires_"+id_msg).attr('style' , 'display:block');
		 $("#Commentaires_"+id_msg).prepend(html);

	 }
	 else{
		alert(rep.ko);

	 }
 }
///////////////////////////////////////////////////////DeleteComment

 

function functionDeleteCmt(id_Comment,log){
	 if(log == env.login){
		 var id_msg = $("#comments_"+id_Comment).parent().attr("class");
		 delteComment(id_Comment,id_msg);
	 }
	 else{
		 alert("Ce Commentaire n'est pas le votre !");
	 }
}

function delteComment(idComment,id_msg){
	var noConnexion =false;
	if(!noConnexion){
	$.ajax({
			type:"GET",
			url:"comment/delete",
			data:"idComment="+idComment+"&idMessage="+id_msg,
			dataType:"JSON",
			success:function(rep){
				 deleteCommentResponse(rep,id_msg,idComment);
			},
			error:function(jqXHR,textStatus,errorThrown){
				alert("Il ya un probleme");
			}
	 });
	}
}
function deleteCommentResponse(rep,id_msg,idComment){


	if(rep.ko == undefined){
		$("#comments_"+idComment).remove();
	}
	else{
	 alert(rep.ko);

	}
}


 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 //////////////////////////////////////////////ADD FRIEND
 function functionAjouterAmi(login){
	 var ajouter = true;
	 env.follows.forEach(function(value){
		 var ami = value.loginfriend;
		 if(login == ami){
			 ajouter = false;
			 alert("Vous êtes déja ami ! ");
		 }
	 });
	 if(ajouter){
		 newFriend(login);
	 }
 }
 function newFriend(friendLog){
	 var noConnexion =false;
	 if(!noConnexion){
	 $.ajax({
			 type:"GET",
			 url:"friend/new",
			 data:"login="+env.login+"&friendLog="+friendLog,
			 dataType:"JSON",
			 success:function(rep){
					newFriendResponse(rep,friendLog);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
				 alert("Il ya un probleme Freidn FUnction");
			 }
	 	});
	 }
	

}
 function newFriendResponse(rep,friendLog){

	 if(rep.ko == undefined){
		 if(rep.ok == 1){
			 var to = new Object();
			 to.idFriend = rep.idto;
			 to.loginfriend = rep.logTo;
			 env.follows.add(to);
			 addFriendHtml(friendLog);
			 $("#followers").remove();
			 var h = "<h5 id=\"followers\"> followers :"+env.follows.size+"</h5>";
			 $("#nbr_flowers").append(h);
		 }
	 }
	 else{
		alert(rep.ko);

	 }
 }

/////////////////////////////////////////////////////////////////////////
////////////////////////////////////DELETE FRIEND

function functionSupprimerAmi(login){
	 var supprimer = false;
	 env.follows.forEach(function(value){
		 var ami = value.loginfriend;
		 if(login == ami){
			 supprimer = true;
		 }
	 });
	 if(supprimer){
		 deleteFriend(login);
	 }
	 else{
		 alert("Vous êtes pas ami !");
	 }
	
}
function deleteFriend(friendLog){
	var noConnexion =false;
	if(!noConnexion){
	$.ajax({
			type:"GET",
			url:"friend/Delete",
			data:"login="+env.login+"&friendLog="+friendLog,
			dataType:"JSON",
			success:function(rep){

				 deleteFriendResponse(rep,friendLog);
			},
			error:function(jqXHR,textStatus,errorThrown){
				alert("Il ya un probleme Freidn FUnction");
			}
	 });
	}
	else{
		var auteur = new Object();
		auteur.loginfriend = "newFriend";
		env.follows.add(auteur);
		console.log(env.follows);
	}

}
function deleteFriendResponse(rep,friendLog){
	if(rep.ko == undefined){
		if(rep.ok == 1){
			env.follows.forEach(function(value) {
				if(value.loginfriend == friendLog){
					env.follows.delete(value);
					 $("#followers").remove();
					 var h = "<h5 id=\"followers\"> followers :"+env.follows.size+"</h5>";
					 $("#nbr_flowers").append(h);
					 $("#log_friend_"+friendLog).remove();
					
				}
			});
		
			console.log(env.follows);
			env.follows[0].delete(friendLog);
		}
	}
	else{
	 alert(rep.ko);

	}
}
///////////////////////////////////////////////////////////////
//////////////////////////////// RECHERCHE AMIS 
function recherche_amis(){
	var val = $("#recherche_amis").val();
	if(val != env.login){
		FindFriend(val);
	}
	else{
		if(confirm("Voulez-vous aller sur votre profil ?")){
			functionGoToProfil(val);
		}
		else{
			alert("Cherchez un autre utilisateur !!");
		}
		
	}
}
function FindFriend(login){
	$.ajax({
		type:"GET",
 		url:"user/list",
 		data:"login="+login,
 		dataType:"JSON",
 		success:function(rep){
 			FindFriendResponse(rep,login);
 		},
 		error:function(jqXHR,textStatus,errorThrown){
			alert("Il ya un probleme FINDFRIEND FUnction");
		}
	});
}
function FindFriendResponse(rep,login){
	console.log(rep);
	if(rep.ko == undefined){
		
		$("#addOrNot").remove();
		var html = "<div class=\"log_friend\" id=\"addOrNot\"> <p onclick = \"functionGoToProfil('"+login+"');\">"+login+" </p> <button id=\"addFriend\" onclick=\"functionAjouterAmi('"+login+"');\"> +</button> <button id =\"deleteFriend\"  onclick=\"functionSupprimerAmi('"+login+"');\"> - </button> </div>"; 
		$("#List_friends").prepend(html);	
	}
	else{
		alert("Cette utilisateur n'existe pas");
	}
}

function addFriendHtml(friendLog){
	 var html = "<p id=\"log_friend_"+friendLog+"\" class=\"log_friend\" onclick = \"functionGoToProfil('"+friendLog+"');\"><img src=\"images/logo_f.gif\" alt=\"avatar_login\"/> "+friendLog+"</p>";
	 $("#List_friends").append(html);
}


 /////////////////////////////////////////////////////////
 //////////////////////////REDIRECTION
 function functionGoToProfil(login){
	 if(login != env.login){
		 makeFreindProfilPanel(login);
	 }
	 else{
		 makeProfilPanel();
	 }
 }
 /////////////////////////////////////////////////////////////
 function write(id , text){
 	  document.getElementById("\""+id+"\"").innerHTML = text;
 }





 ////////////////////////////////////////////////********************************///////////////
 /////FONCTION DE TRAITEMENT DES CLIQUES


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
function Makeprofil(){
		$("#mes_posts").remove();
}




////////////////////////////////////////////////


	function foncErreur(code){
		 var msg;
			if ( code == 6){
			 msg = "insertion impossible";
		 }else if(code == 7){
			 msg = "attributs vides";

		 }else if ( code == 8){
			 msg = "login déja utilisé";
		 }else if ( code == 9){
			 msg = "adresse déja utlisée";
		 }else if ( code == 1000){
			 msg = "brobleme de bdd";
		 }
		 else if ( code == 3){
			 msg = "login not exist";
		 }
		 else if ( code == 2){
			 msg = "mot de passe incorecte";
		 }
		 else if ( code == 4){
			 msg = "probleme de deconnexion";
		 }
		 else if ( code == 11){
			 msg = "vous êtes déja connecté ailleur";
		 }
		 else if ( code == 20){
			 msg = "message incorrecte";
		 }
		 else if ( code == 21){
			 msg = "message non envoyé: veillez réessayer !";
		 }
		 else if ( code == 23){
			 msg = "commentaire non envoyé !";
		 }
		 else if ( code == 13){
			 msg = "log de l'ami n'existe pas !";
		 }
		 msgBox = "<div id =\"idErreur\"><h2>"+msg+"</h2></div>";
		 $("#idError").html('');
		 $("#idError").html(msgBox);
	 }
