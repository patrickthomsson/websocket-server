
(function($) {
	
	var url = "ws://" + window.location.hostname +":8082/connectfour";
	var websocket;

	//deals with form submission issues, we don't want the page to reload on submit
	if(location.href.indexOf("?#") == -1) {
		location.assign(location.href + "?#");
	}
	
	$(function() {
		testSupport();
		$("#connectButton").click(connect);
		$("#closeButton").click(disconnet);
		$("#chatForm").submit(function(event) {
			var name = getUserName();
			var message = $("#inputMessage").val();
			sendChatMessage(name, message);
			$("#inputMessage").attr("value", "");
		});
		$("#invite").click(sendInvitation);
		$("#user").focus();
		
		$('#inputMessage').keypress(function(e) {
			if(e.which == 13){
				$("#chatForm").submit();
   			}
		});
	});
	
	var testSupport = function () {
		if (window.WebSocket) {
			$("#support").text("HTML5 WebSocket is supported in your browser.");
		} else {
			$("#support").text("HTML5 WebSocket is not supported in your browser, try Google Chrome.");
		}
	}
	
	var connect = function() {
		if(!getUserName()) {
			return;
		}
		log("CONNECTING");
		$("#status").text("Connecting...");
	
		initWebSocket(url);
		$("#user").attr("disabled", "disabled");
		$("#connectButton").attr("disabled", "disabled");
	}
	
	var disconnet = function() {
		log("CLOSING CONNECTION");
		websocket.close();
		$("#connectButton").removeAttr("disabled");
		$("#user").removeAttr("disabled");
	}
	
	var initWebSocket = function(url) {
		websocket = new WebSocket(url);
		
		websocket.onopen = function() {
			log("CONNECTION OPEN");
			$("#status").css("color", "green");
			$("#status").text("Connected to " + url);
			sendInstruction("NEW_USER:" + getUserName());
			$("#inputMessage").focus();
		}
		websocket.onmessage = function(e) {
			log("GOT MESSAGE: " + e.data);
			receiveMessage(e.data);
		}
		websocket.onclose = function(e) {
			log("CONNECTION CLOSED");
			$("#status").css("color", "red");
			$("#status").text("Not connected");
		}
		websocket.onerror = function(e) {
			log("ERROR:" + e);
		}
	}
	
	var sendChatMessage = function(name, message) {
		if(name && message) {
			var fullMessage = name + ": " + message;
			sendInstruction("MESSAGE:" + fullMessage);
		} else {
			log("NO NAME OR MESSAGE FOUND, WILL NOT SEND MESSAGE");
		}
	}
	
	var sendInstruction = function(instruction) {
		log("OUTGOING MESSAGE: " + instruction);
		websocket.send(instruction);
	}
	
	var receiveMessage = function(message) {
		var msg = new Message(message);
		
		if(msg.startsWith("MESSAGE:")) {
			receiveChatMessage(message);
		}
		else if(msg.startsWith("USERLIST:")) {
			log("got userlist message: " + msg);
			updateUserlist(message.split("USERLIST:")[1].split(","));
			log("updated userlist");
		} 
		else if(msg.startsWith("INVITATION:FROM:")) {
			var gameAccepted = confirm("Do you want to play with " + msg.message.split(":")[2] + "?");
			sendInstruction(msg.message + (gameAccepted ? ":ACCEPTED" : ":DECLINED"));
		}
		else if(msg.startsWith("INVITATION:TO:")) {
			var invited = msg.message.split(":")[2];
			var accepted = msg.message.split(":")[3] === "ACCEPTED";
			alert(invited + " has " + (accepted ? "accepted" : "declined") + " your invitation.");
		}
	}
	
	var updateUserlist = function(userlist) {
		$("#userlist").text("");
		for(var i=0; i<userlist.length; i++) {
			$("#userlist").append(userlist[i] + "\n");
		}
		
		$("#inviteList option").remove();
		for(var i=0; i<userlist.length; i++) {
			if(userlist[i] !== getUserName()) {
				$("#inviteList").append($("<option>" + userlist[i] + "</option>"));
			}
		}
	}
	
	function sendInvitation(event) {
		event.preventDefault();
		var invited = $("#inviteList option:selected").val();
		if(invited) {
			sendInstruction("INVITATION:" + invited);	
		}
	}

	var log = function(message) {
		var d = new Date();
		var timestamp = d.toLocaleTimeString() + ":" + d.getMilliseconds();
		console.log(timestamp + ": " + message);
	}
	
	var receiveChatMessage = function(message) {
		appendChatMessage(message.split("MESSAGE:")[1]);
	}
	
	var appendChatMessage = function(message) {
		$("#messages").append(message + "\n");
	}
	
	var getUserName = function() {
		return $("#user").val();
	}
	
	Function.prototype.method = function(name, func) {
	    this.prototype[name] = func;
	    return this;
	};
	String.method("matches", function(regexp) {
		return this.search(new RegExp(regexp)) != -1;
	});
	
	function Message(message) {
		this.message = message;
	}
	Message.method("startsWith", function(prefix) {
		return this.message.matches("^" + prefix);
	});
	
})(jQuery);
