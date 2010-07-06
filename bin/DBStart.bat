rem
rem Author:		Jennifer Morgan
rem	Date:		26-Jun-2010
rem	Purpose:	Script to start the Derby Database Network Server
rem	
set JAVA_HOME="C:\Program Files\Java\jdk1.6.0_20"
set DERBY_INSTALL=C:\Program Files\Sun\JavaDB
rem set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;.
rem set CLASSPATH=%DERBY_INSTALL%\lib\derbyclient.jar;%DERBY_INSTALL%\lib\derbytools.jar;.
set CLASSPATH=%DERBY_INSTALL%\lib\derbyrun.jar;.
rem Verify that we have setup the CLASSPATH and DERBY_INSTALL correctly
rem java org.apache.derby.tools.sysinfo
rem Start the SQL session using ij tool and create a test database 
rem connect 'jdbc:derby:MyDbTest;create=true';
rem http://db.apache.org/derby/papers/DerbyTut/ij_intro.html
rem java org.apache.derby.tools.ij
java -jar "%DERBY_INSTALL%\lib\derbyrun.jar" server start
