# CMP405

Assignment can be found at:
http://comet.lehman.cuny.edu/sfakhouri/teaching/cmp/cmp405/s16/hw/hw5.html

Notes:

1) There are two MenuFrames (MenuFrame, Menu2Frame)
The original (MenuFrame) was what I used for my initial testing directly to an IP and Port.
Menu2Frame (super creative name, I know!) was to implement the broadcast protocol required for the assignment.
You can just comment out whichever out you dont want to use.

2) Evolution of the broadcast protocol
You will find that there is two lines that have my broadcast message.  We found that the initial protocol
was inadequate.  If I was to initate a session with ????? john_doe, and they reply back with 
##### john_doe ##### <ip_address>, john will never know my name to populate the title bar of his chat window.
Consequently, we just concat ????? USER_NAME to the end of the broadcast which solves this problem.
You can just comment out whichever out you dont want to use.

3) Main flow/logic of the program
There are a few scenarios that can happen once the program is started.
a) We send out a broadcast ?????
b) We receive a broadcast directed at us ????? USER_NAME
c) We receive a broadcast_reply which is directed to us ##### USER_NAME
d) normal message with no protocol-esque type of prefix.

The program will only add a user to the list of chat sessions if the protocol is used:
a) If I send you a broadcast ????? john_doe, I will add you to the list once you reply with ##### john_doe
b) If I am reciving a broadcast looking for me, I will add you to the list, auto reply back with ##### USER_NAME
and open a new chat session.
c) This ties in with option A above.  This is the response we were waiting for.  
We add them to the list, and we open a new window
d) We check if they're on the list (I know I could use exists for the arraylist, 
and I can also improve this using a hashmap.  This was something I wanted to get back to, and ran out of time).  If they're
on the list, we take the message, pass it to the corresponding chat window

random other note...hastags in the readme aren't showing up.../sigh
