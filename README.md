TinyStartApp
============

It contains one activity, MainActivity, and one executable file, SleepExec.exe :

1/ SleepExec.exe : is a cross compiled c++ executable file, for ARM v7a processors, that sleeps 10 seconds.
(In the activity I suppose that this executable returns a text message when it starts and when it finishes).
I have included it as assets in this simple Android application. 

2/ MainActivity: is the activity that :
- copy the executable from the assets to the application directory.
- By clicking on the start/stop button the application start to lunch the executable, 
and by clicking on the start/stop button , again , the application stops the process of the executable, etc.
