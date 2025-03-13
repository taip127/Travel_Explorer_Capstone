Travel Explorer

Travel Explorer is a Spring Boot web application for purchasing and managing tickets for attractions around Austin, TX. It supports Admin, Buyer, and Vendor roles with secure login, a shopping cart, and ticket verification.

Features:
- User Roles:
  - Admin: Add, edit, delete attractions.
  - Buyer: Browse attractions, manage cart, checkout.
  - Vendor: Verify tickets via web or REST API.
- Attractions: Shows images, descriptions, prices, locations, and website links.
- Cart: Real-time updates and validation.
- Ticket Verification: Web form and REST API (/api/vendor/verify-ticket).
- Pages: Home, Destinations (near Austin, TX), Attractions, Cart.
- Dynamic Content: Navigation and content based on user role.
- Security: Role-based auth with BCrypt passwords.
- Design: Light/dark mode toggle.

Tech Stack:
- Backend: Spring Boot, Spring Security, Spring Data JPA (Hibernate)
- Frontend: Thymeleaf, HTML5, CSS3, JavaScript
- Database: MySQL
- Build: Maven

Prerequisites:
- Java: JDK 17+
- Maven: 3.6.0+
- MySQL: 8.0+

Setup:
1. Clone the repo:
   git clone https://github.com/yourusername/travel-explorer.git
   cd travel-explorer
2. Configure MySQL:
   - Create database: CREATE DATABASE travel3;
   - Update src/main/resources/application.properties:
     spring.datasource.url=jdbc:mysql://localhost:3306/travel3
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
   - Run SQL: mysql -u your_username -p travel3 < src/main/resources/travel3.sql
3. Add images to src/main/resources/static/image/:
   - zilker_park.jpg, barton_springs.jpg, lady_bird_lake.jpg, mount_bonnell.jpg, texas_capitol.jpg
4. Build and run:
   mvn clean install
   mvn spring-boot:run
5. Visit: http://localhost:8080

Default Users:
- Buyer: shopper1@gmail.com / shopper123
- Admin: admin1@gmail.com / admin123
- Vendor: vendor1 / vendorpass

Usage:
- Browse: /attractions (click images for websites, add to cart)
- Cart: /orderdetail (view, update, checkout)
- Admin: /attractions/manage (manage attractions)
- Vendor: /vendor (verify tickets) or POST /api/vendor/verify-ticket with uniqueCode

Project Structure:
travel-explorer/
  src/
    main/
      java/com/tai/travel2/ - Config, controllers, models, services
      resources/
        static/ - CSS, JS, images
        templates/ - HTML pages
        application.properties - Config
        travel3.sql - DB schema
    test/ - Tests
  pom.xml - Dependencies
  README.txt

Future Enhancements:
- QR codes for tickets
- Tests
- Interactive maps for destinations
- Payment integration

Contributing:
1. Fork the repo
2. Branch: git checkout -b feature/your-feature
3. Commit: git commit -m "Add your feature"
4. Push: git push origin feature/your-feature
5. Pull request

License: MIT (see LICENSE file)

Contact: GitHub Issues (https://github.com/yourusername/travel-explorer/issues)
