Local Env Setup.

1. Clone the project to your local machine
2. import the maven project in to eclipse workspace.
3. right-click on project > Maven > Disable Maven Nature
4. right-click on project > Configure > Convert to Maven Project
5. right-click on project > properties > Deployment Assembly . (Add the jar files to tomcat deployment).
     The way to add this: 
	- click on Add 
	- select Java Build Path Entries 
	- select Maven Dependencies 
	- Finish 
That should add your Maven Dependencies into WEB-INF/lib. 