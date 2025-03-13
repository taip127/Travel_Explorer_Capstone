document
    .getElementById("registerForm") // Updated form ID to match your Thymeleaf form
    .addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent the form from submitting

        // Clear all previous error messages
        document
            .querySelectorAll(".error-message")
            .forEach((el) => (el.style.display = "none"));

        // Get form field values
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        let valid = true;

        // Validate email
        if (!validateEmail(email)) {
            valid = false;
            document.getElementById("emailError").style.display = "block";
        }

        // Validate password
        if (!validatePassword(password)) {
            valid = false;
            document.getElementById("passwordError").style.display = "block";
        }

        // Submit the form if all validations pass
        if (valid) {
            alert("Form submitted successfully!");
            this.submit(); // Submit the form
        }
    });

// Email validation function
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Password validation function
function validatePassword(password) {
    // At least 8 characters, 1 uppercase, 1 lowercase, 1 number
    const re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return re.test(password);
}