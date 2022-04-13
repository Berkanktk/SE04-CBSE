Template
````
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=org.sonatype.mcookbook -DartifactId=osgi-project -Dversion=1.0-SNAPSHOT

mvn pax:create-bundle -Dpackage=dk.sdu.mmmi -Dname=core-bundle -Dversion=1.0-SNAPSHOT

mvn pax:wrap-jar -g groupId -a artifactId -v version
````

My usage
````
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=dk.sdu.mmmi -DartifactId=Lab06-OSGiLab -Dversion=1.0-SNAPSHOT

mvn pax:create-bundle "-Dpackage=dk.sdu.mmmi.SOMETHING" "-Dversion=1.0-SNAPSHOT"

mvn pax:wrap-jar "-DgroupId=dk.sdu.mmmi.libgdx-wrap" "-DartifactId=libgdx-wrap" "-Dversion=1.0-SNAPSHOT"
````
