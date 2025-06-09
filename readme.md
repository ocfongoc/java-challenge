### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years

### Developer Experience

I have 6 years of experience with Java and 4 years experience Spring Boot as Full Stack Developer. However, I am actually more frontend focused.

### Project Improvements Made

1. **JWT Authentication**
   - Implemented JWT token-based security
   - Protected employee endpoints
   - Added login endpoint for token generation

2. **Caching**
   - Added caching for database operations
   - Implemented cache eviction policies

3. **Testing**
   - Added unit tests for controllers 
   - Added integration tests for services

4. **Documentation**
   - Enhanced Swagger documentation
   - Added detailed API response codes
   - Improved code comments and documentation

### Futher Considerations on Improvements

If given more time, I would implement:
- Refresh token mechanism
- Rate limiting
- Pagination for list endpoints
- Enhanced monitoring and logging
