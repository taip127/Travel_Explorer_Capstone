Travel Explorer
Travel Explorer is a Spring Boot web application designed to facilitate ticket purchasing and management for attractions around Austin, TX. It supports multiple user roles (Admin, Buyer, Vendor) with a secure, role-based authentication system. Buyers can browse attractions, manage their cart, and checkout, while vendors can verify tickets via a website or REST API. Admins can manage attractions through a dedicated interface. The app features a responsive UI with dynamic content based on user roles.

Features
User Roles:
Admin: Add, edit, and delete attractions via a management interface.
Buyer: Browse attractions, add tickets to a cart with real-time updates, and checkout.
Vendor: Verify tickets using a web interface or REST API (/api/vendor/verify-ticket) to check if they are valid, redeemed, or invalid.
Attractions Page: Displays attractions with images, descriptions, prices, locations, and links to official websites.
Enhanced Shopping Cart: Real-time updates and validation for ticket quantities and availability.
Ticket Verification: Vendors can validate tickets via a web form or REST API using unique codes stored in ticket_history.
Dynamic Navigation & Content: Navigation bar and page content adapt based on user role (e.g., Admin sees management links, Buyers see cart).
Additional Pages:
Home Page: Welcomes users with an overview of the platform.
Destinations Page: Highlights interesting places near Austin, TX.
Security: Role-based authentication with BCrypt-encrypted passwords and CSRF protection.
Responsive Design: Light/dark mode toggle for user preference.
Tech Stack
Backend: Spring Boot, Spring Security, Spring Data JPA (Hibernate)
Frontend: Thymeleaf, HTML5, CSS3, JavaScript
Database: MySQL
Build Tool: Maven
Dependencies:
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-thymeleaf
mysql-connector-j
lombok
Prerequisites
Java: JDK 17 or higher
Maven: 3.6.0 or higher
MySQL: 8.0 or higher
IDE: IntelliJ IDEA, Eclipse, or VS Code (optional)
Setup Instructions
1. Clone the Repository
bash

Collapse

Wrap

Copy
git clone https://github.com/yourusername/travel-explorer.git
cd travel-explorer
2. Configure the Database
Install MySQL and create a database:
sql

Collapse

Wrap

Copy
CREATE DATABASE travel3;
Update src/main/resources/application.properties with your MySQL credentials:
properties

Collapse

Wrap

Copy
spring.datasource.url=jdbc:mysql://localhost:3306/travel3
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
logging.level.org.springframework=INFO
logging.level.com.tai.travel2=DEBUG
Run the provided SQL script (travel3.sql) to initialize the schema and data:
bash

Collapse

Wrap

Copy
mysql -u your_username -p travel3 < src/main/resources/travel3.sql
3. Add Static Images
Place attraction images in src/main/resources/static/image/:

zilker_park.jpg
barton_springs.jpg
lady_bird_lake.jpg
mount_bonnell.jpg
texas_capitol.jpg
4. Build and Run
bash

Collapse

Wrap

Copy
mvn clean install
mvn spring-boot:run
Access the app at http://localhost:8080.
5. Default Users
Buyer: shopper1@gmail.com / shopper123
Admin: admin1@gmail.com / admin123
Vendor: vendor1 / vendorpass
Project Structure
text

Collapse

Wrap

Copy
travel-explorer/
├── src/
│   ├── main/
│   │   ├── java/com/tai/travel2/
│   │   │   ├── config/           # Spring Security configuration
│   │   │   ├── controller/       # REST and MVC controllers
│   │   │   ├── model/            # JPA entities (User, Attraction, etc.)
│   │   │   ├── repository/       # JPA repositories
│   │   │   ├── service/          # Business logic
│   │   │   └── Travel2Application.java  # Main application class
│   │   ├── resources/
│   │   │   ├── static/           # CSS, JS, and image files
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   └── image/
│   │   │   ├── templates/        # Thymeleaf HTML templates (home, attractions, cart, etc.)
│   │   │   ├── application.properties  # App configuration
│   │   │   └── travel3.sql       # Database schema and data
│   └── test/                     # Unit/integration tests (to be added)
├── pom.xml                       # Maven dependencies
└── README.md
Usage
Browse Attractions:
Visit /attractions to see available attractions.
Click an image to visit the attraction’s website.
Select quantities and click "Place Order" to add to cart.
Manage Cart:
Go to /orderdetail to view and update your cart in real-time.
Click "Proceed to Checkout" to complete the purchase.
Admin Management:
Log in as an admin and visit /attractions/manage (or similar) to add, edit, or delete attractions.
Vendor Verification:
Web: Log in as a vendor, go to /vendor, and enter a ticket’s unique code to verify.
API: Use POST /api/vendor/verify-ticket with uniqueCode in the body (e.g., via Postman):
text

Collapse

Wrap

Copy
Content-Type: application/x-www-form-urlencoded
uniqueCode=550e8400-e29b-41d4-a716-446655440000
Responses: "Ticket Verified", "This ticket was already redeemed", or "Ticket Invalid".
Explore Destinations:
Visit /destinations to see interesting places near Austin, TX.
API Endpoints
Vendor Ticket Verification:
Endpoint: POST /api/vendor/verify-ticket
Request: uniqueCode=<code> (form-urlencoded)
Response: Plain text (e.g., "Ticket Verified")
Auth: Requires ROLE_VENDOR
Future Enhancements
Add QR code generation for tickets on the receipt page.
Implement unit and integration tests.
Enhance the destinations page with interactive maps.
Add payment processing integration.
Contributing
Fork the repository.
Create a new branch: git checkout -b feature/your-feature.
Commit changes: git commit -m "Add your feature".
Push to the branch: git push origin feature/your-feature.
Open a pull request.
License
This project is licensed under the MIT License - see the LICENSE file for details.

Contact
For questions or feedback, reach out via GitHub Issues.
