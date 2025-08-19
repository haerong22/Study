
<h1> HTTP Header </h1>

```
HTTP/1.1 200 OK
Date: Thu, 14 Dec 2023 10:30:00 GMT
Content-Type: application/json;charset=UTF-8
Content-Length: 156
Connection: keep-alive
Server: nginx/1.18.0
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: *
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
```



```
HTTP/1.1 200 OK                                    
Date: Thu, 14 Dec 2023 10:30:00 GMT                          
Content-Type: application/json;charset=UTF-8              
Content-Length: 156                            
Connection: keep-alive                                   
Server: nginx/1.18.0                             
Access-Control-*                   
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
보안 헤더들 (X-Content-Type-Options, X-Frame-Options 등) 
```

<h1> WebSocket Header </h1>

```
GET /ws/chat?userId=1 HTTP/1.1
Host: localhost:8080
Connection: Upgrade
Upgrade: websocket
Sec-WebSocket-Version: 13
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Protocol: chat
Origin: http://localhost:3000
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)
```

```
HTTP/1.1 101 S`witching Protocols`
Connection: Upgrade
Upgrade: websocket
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
Sec-WebSocket-Protocol: chat
```


<h1> Frame Header </h1>

```

+-+-+-+-+-------+-+-------------+-------------------------------+
|F|R|R|R| opcode|M| Payload len |    Extended payload length    |
|I|S|S|S|  (4)  |A|     (7)     |             (16/64)           |
|N|V|V|V|       |S|             |   (if payload len==126/127)   |
| |1|2|3|       |K|             |                               |
+-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
|     Extended payload length continued, if payload len == 127  |
+ - - - - - - - - - - - - - - - +-------------------------------+
|                               |Masking-key, if MASK set to 1  |
+-------------------------------+-------------------------------+
| Masking-key (continued)       |          Payload Data         |
+-------------------------------- - - - - - - - - - - - - - - - +
:                     Payload Data continued ...                :
+ - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
|                     Payload Data continued ...                |
+---------------------------------------------------------------+
```