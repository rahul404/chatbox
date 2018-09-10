<h1>Chatbox </h1>
<p> A point-to-point desktop messaging app</p>
<h2>Motivation</h2>
<p>
  The objective of implementing a point to point messaging application was to understand basics of Java Network Programming and implement the Computer Network Concepts in application. This application makes use of TCP/IP protocol using Java Socket programming. The demonstration was given by running the app on laptop geographically seperated.
</p>
<h2>Working</h2>
<p>
  Chatbox is a desktop P2P instant messaging application which makes use of Java Socket Programming.
  Chatbox first creates a server which performs the job of routing messages from sender to receiver client.
  Server holds a reference to the connection of all clients and reads a message which contains sender and receiver field in it.
  Then the server looks up for the Socket refernce to the receiver and pushed message to Client's OutputStream.
</p>
