<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div>
        <input type="text" id="userinput" /> <br> <input type="submit"
           value="Send Message to Server" onclick="start()" />
    </div>
    <div id="messages"></div>
    <script type="text/javascript">
        var webSocket = new WebSocket(
                'ws://localhost:8080/clearview/websocket');

        webSocket.onerror = function(event) {
            onError(event)
        };

        webSocket.onopen = function(event) {
            onOpen(event)
        };

        webSocket.onmessage = function(event) {
            onMessage(event)
        };

        function onMessage(event) {
            document.getElementById('messages').innerHTML += '<br />'
                    + event.data;
        }

        function onOpen(event) {
            document.getElementById('messages').innerHTML = 'Now Connection established';
        }

        function onError(event) {
            alert(event.data);
        }

        function start() {
            var text = document.getElementById("userinput").value;

            webSocket.send(text);
            return false;
        }
    </script>
</body>

</html>