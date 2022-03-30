## To generate the artifacts and deploy 

Run: 

`./release.sh`

## Test

Run: 

`./run-test.sh`

The above scripts runs a cURL request against the API Gateway created by CDK.

## Getting Started
```
cdk init --language=java
rm pom.xml
rm -rf src
rm -rf target
mkdir cdk
mv cdk.json cdk/
```
