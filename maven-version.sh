#统一版本打包 -Alpha -SNAPSHOT -RELEASE

mvn versions:set -DnewVersion=1.0.0-SNAPSHOT
#mvn clean deploy -Dmaven.test.skip
mvn clean install -Dmaven.test.skip
