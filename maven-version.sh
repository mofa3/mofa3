#统一版本打包 -Alpha -SNAPSHOT -RELEASE

mvn versions:set -DnewVersion=0.0.1-RELEASE
#mvn clean deploy -Dmaven.test.skip
#mvn clean install -Dmaven.test.skip

mvn -s /Users/mc/develop/apache-maven-3.5.0/conf/settings-sonatype.xml clean javadoc:jar deploy -P release