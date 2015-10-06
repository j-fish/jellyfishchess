jellyfish
=========
Jellefishchess project is a Java UCI middleware for driving chess engines and UI's for playing chess.
It's purpose is basicly to pratice Java, software design and have fun programming - nothing more.

3D OpenGL user interfaces are available on the 2 two branches of the repository. 
Librairies used for OpenGL are LWJGL 2.9.1 : http://legacy.lwjgl.org/ 

Project IDE setup :
  - NetBeans recomended.
  - In NetBeans, right click on project >> properties >> run. In the VM Options define path towards lwjgl-2.9.1\native\windows directory. Example : -Djava.library.path=C:\Users\your user name\lwjgl-2.9.1\native\windows. Visit http://legacy.lwjgl.org/ for further help if needed.
  - Stockfish chess engine : http://stockfishchess.org/  stockfish_x64 or stockfish_32bit .exe must be in project-directory/jfgjellyfishchess/ directory. Constants for engine lauch can be modified or checked in ExternalEngineConst.java in fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants package. If you wish to use a Linux or Mac OS Stockfish build then extra setup will be requiered.

Recomended chess engine : Stockfish http://stockfishchess.org/<br>

Under BSD3 license.
