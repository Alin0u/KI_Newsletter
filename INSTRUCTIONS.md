# How to use our application?

## Backend

### application-secure.properties
Copy the file application-secure-sample.properties to application-secure.properties

### API-Key
1. Login to you OpenAI-Account: [OpenAI](https://openai.com/)
2. Navigate to API-Keys: [API-Keys](https://platform.openai.com/api-keys)
3. Create a new Key
4. Insert it into your application-secure.properties file ('spring.ai.openai.api-key=')

### Database
For the login to work properly you need a database (in our case it's a mariadb)

1. Install mariadb locally 
2. Create a database 
3. Create a table which looks like this:

+----+--------------------------------------------------------------+-------+----------+
| id | password                                                     | role  | username |
+----+--------------------------------------------------------------+-------+----------+
|  1 | hashed password                                              | admin | pa-admin |
|  2 | hashed password                                              | user  | pa-user  |
+----+--------------------------------------------------------------+-------+----------+

A hashed password can be generated here: [Bcrypt Password-Generator](https://bcrypt-generator.com/)

4. Insert the information from your database into the application-secure.properties: 

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mariadb://localhost:3306/<database_name>
spring.datasource.username=<database_username>
spring.datasource.password=<database_password>

## No Database available (just for developement)

Will be implemented soon

## PublicKey & PrivateKey

You can create a public and private key by using these command on a linux commandline:
1. Create a keypair: "keytool -genkeypair -alias jwtkey -keyalg RSA -keysize 2048 -keystore jwt.jks -validity 3650"
2. Extract the public key: "keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey"
3. Save the publicKey in the folder 'resources' with the name 'app.pub'
4. Extract the privateKey:
keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.p12 -srcstoretype JKS -deststoretype PKCS12
openssl pkcs12 -in jwt.p12 -nocerts -out private.pem
5. Decode the private Key: openssl rsa -in private.pem -out decrypted_private.pem
6. Save the privateKey in the folder 'resources' with the name 'app.key'


