CLIENT_KEYSTORE=C:\Users\gadzi\IdeaProjects\jsec\mtlsclient\src\main\resources\client-nonprod.jks
SERVER_KEYSTORE=C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\server-nonprod.jks
JAVA_CA_CERTS=$JAVA_HOME/jre/lib/security/cacerts

# Generate a client and server RSA 2048 key pair
keytool -genkeypair -alias mtlsclient -keyalg RSA -keysize 2048 -dname "CN=Client,OU=Client,O=PlumStep,L=San Francisco,S=CA,C=U" -keypass changeme -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsclient\src\main\resources\client-nonprod.jks -storepass changeme -validity 365000
keytool -genkeypair -alias mtlsserver -keyalg RSA -keysize 2048 -dname "CN=Server,OU=Server,O=PlumStep,L=San Francisco,S=CA,C=U" -keypass changeme -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\server-nonprod.jks -storepass changeme -validity 365000

# Export public certificates for both the client and server
keytool -exportcert -alias mtlsclient -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\client-public.cer -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsclient\src\main\resources\client-nonprod.jks -storepass changeme
keytool -exportcert -alias mtlsserver -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\server-public.cer -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\server-nonprod.jks -storepass changeme

# Import the client and server public certificates into each others keystore
keytool -importcert -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsclient\src\main\resources\client-nonprod.jks -alias mtls-server-public-cert -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\server-public.cer -storepass changeme -noprompt
keytool -importcert -keystore C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\server-nonprod.jks -alias mtls-client-public-cert -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\client-public.cer -storepass changeme -noprompt

# Import server and client public certificates into java cacerts to enable https communication
keytool -import -alias mtls-client-public-cert -keystore "%JAVA_HOME%\lib\security\cacerts" -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\client-public.cer
keytool -import -alias mtls-server-public-cert -keystore "%JAVA_HOME%\lib\security\cacerts" -file C:\Users\gadzi\IdeaProjects\jsec\mtlsserver\src\main\resources\pcerts\server-public.cer