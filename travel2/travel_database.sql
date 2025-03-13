DROP DATABASE IF EXISTS travel4;
CREATE DATABASE travel4;
USE travel4;

-- User Table (Base table with role enum)
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role ENUM('ADMIN', 'BUYER', 'VENDOR') NOT NULL
);

-- AdminUser Table (inherits from User)
CREATE TABLE admin_user (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

-- Buyer Table (inherits from User)
CREATE TABLE buyer (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

-- VendorUser Table (inherits from User)
CREATE TABLE vendor_user (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

-- Attraction Table (Updated with img_url and link_url)
CREATE TABLE attraction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    location VARCHAR(255),
    available_tickets INT,
    img_url VARCHAR(255), -- URL to image in static/image folder
    link_url VARCHAR(255) -- URL to attraction website
);

-- OrderDetail Table
CREATE TABLE order_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(10,2),
    buyer_id BIGINT UNIQUE,
    FOREIGN KEY (buyer_id) REFERENCES buyer(id) ON DELETE CASCADE
);

-- Ticket Table
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT,
    price DECIMAL(10,2) NOT NULL,
    orderdetail_id BIGINT,
    attraction_id BIGINT,
    FOREIGN KEY (orderdetail_id) REFERENCES order_detail(id) ON DELETE CASCADE,
    FOREIGN KEY (attraction_id) REFERENCES attraction(id) ON DELETE CASCADE
);

-- TicketHistory Table
CREATE TABLE ticket_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unique_code VARCHAR(255) NOT NULL UNIQUE,
    attraction_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    total_price DOUBLE NOT NULL,
    buyer_id BIGINT NOT NULL,
    redeemed BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (attraction_id) REFERENCES attraction(id),
    FOREIGN KEY (buyer_id) REFERENCES buyer(id)
);

-- Insert data into User table
INSERT INTO user (username, password, email, role) VALUES
('shopper1@gmail.com', '$2b$12$vVUgTloOpMhA7t06XsGC1O1z43vFO0q/BAIc4yO4E5zj5eVLeAuWi', 'buyer1@example.com', 'BUYER'),
('buyer2', '$2a$10$XURP2XJ6Zc4ZQ8Q7g2bJ2e6Qz1J2bX8J8ZQ8Q7g2bJ2e6Qz1J2bX8', 'buyer2@example.com', 'BUYER'),
('admin1@gmail.com', '$2b$12$Lc0rowmThSLDIGMJ7URDZunOm6kxaJUUoC5oF9kLqwRphMa1TpC2W', 'admin1@example.com', 'ADMIN'),
('vendor1', '$2a$12$a2OMjdyGUWqCQ1tkQm4CI.XT15P8jUoqom9n2F7COU04LRBx5yAPC', 'vendor1@example.com', 'VENDOR');

-- Insert AdminUser
INSERT INTO admin_user (id) SELECT id FROM user WHERE role = 'ADMIN';

-- Insert Buyers
INSERT INTO buyer (id) SELECT id FROM user WHERE role = 'BUYER';

-- Insert VendorUsers
INSERT INTO vendor_user (id) SELECT id FROM user WHERE role = 'VENDOR';

-- Insert data into Attraction table (Updated with img_url, link_url, and full addresses)
INSERT INTO attraction (name, description, price, location, available_tickets, img_url, link_url) VALUES
('Zilker Metropolitan Park', 'Large urban park with trails, gardens, and picnic areas.', 10.00, '2100 Barton Springs Rd, Austin, TX 78704', 100, '/image/zilker_park.jpg', 'https://www.austintexas.gov/department/zilker-metropolitan-park'),
('Barton Springs Pool', 'Natural spring-fed pool with a constant temperature of around 68-70°F.', 8.00, '2201 Barton Springs Rd, Austin, TX 78704', 50, '/image/barton_springs.jpg', 'https://www.austintexas.gov/department/barton-springs-pool'),
('Lady Bird Lake', 'Popular area for kayaking, paddleboarding, and scenic trails.', 15.00, '1820 S Lakeshore Blvd, Austin, TX 78741', 75, '/image/lady_bird_lake.jpg', 'https://www.austintexas.gov/department/lady-bird-lake'),
('Mount Bonnell', 'Scenic lookout offering panoramic views of Austin and the Colorado River.', 5.00, '3800 Mt Bonnell Rd, Austin, TX 78731', 200, '/image/mount_bonnell.jpg', 'https://www.austintexas.gov/page/mount-bonnell'),
('Texas State Capitol', 'Historic landmark with free guided tours showcasing Texas history and architecture.', 12.00, '1100 Congress Ave, Austin, TX 78701', 60, '/image/texas_capitol.jpg', 'https://tspb.texas.gov/plan/tours/tours.html');

-- Insert data into OrderDetail table
INSERT INTO order_detail (buyer_id, total_price) VALUES 
(1, 0),
(2, 0);
