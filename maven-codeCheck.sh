# 代码风格编译检查
mvn -s /Users/mc/develop/apache-maven-3.5.0/conf/settings-sonatype.xml clean pmd:check -U

# 打包
#mvn -s /Users/mc/develop/apache-maven-3.5.0/conf/settings-sonatype.xml clean package -Dmaven.test.skip