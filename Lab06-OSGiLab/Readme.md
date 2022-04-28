# Dependency methods used

| Module                                              | Dependency method                                             |
|-----------------------------------------------------|---------------------------------------------------------------|
| Asteroids                                           | Bundle context API                                            |
| Bullet                                              | Bundle context API                                            |
| Collision                                           | Bundle context API                                            |
| Common, CommonAsteroids, CommonBullet, CommonEnemy  | Export bnd                                                    |
| Core                                                | Dependency injection                                          |
| Enemy                                               | Dependency injection                                          |
| Player                                              | Dependency injection                                          |

# Commands

Template

````bash
# Create project
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=org.sonatype.mcookbook -DartifactId=osgi-project -Dversion=1.0-SNAPSHOT

# Create bundle
mvn pax:create-bundle -Dpackage=dk.sdu.mmmi -Dname=core-bundle -Dversion=1.0-SNAPSHOT

# Create wrapper
mvn pax:wrap-jar -g groupId -a artifactId -v version
````

My usage

````bash
# Create project
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=dk.sdu.mmmi -DartifactId=Lab06-OSGiLab -Dversion=1.0-SNAPSHOT

# Create bundle
mvn pax:create-bundle "-Dpackage=dk.sdu.mmmi.SOMETHING" "-Dversion=1.0-SNAPSHOT"

# Create wrapper
mvn pax:wrap-jar "-DgroupId=dk.sdu.mmmi.libgdx-wrap" "-DartifactId=libgdx-wrap" "-Dversion=1.0-SNAPSHOT"
````

