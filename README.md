<h1>Chatbox </h1>
<p> A point-to-point desktop messaging app</p>
<h2>Working</h2>
<p>
  Chatbox is a desktop P2P instant messaging application which makes use of Java Socket Programming.
  Chatbox first creates a server which performs the job of routing messages from sender to receiver client.
  Server holds a reference to the connection of all clients and reads a message which contains sender and receiver field in it.
  Then the server looks up for the Socket refernce to the receiver and pushed message to Client's OutputStream.
</p>
