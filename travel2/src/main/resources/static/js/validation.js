document
  .getElementById("signupForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent the form from submitting

    // Clear all previous error messages
    document
      .querySelectorAll(".error-message")
      .forEach((el) => (el.style.display = "none"));

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const tel = document.getElementById("tel").value.trim();
    const password = document.getElementById("password").value.trim();
    const refer = document.getElementById("refer").value;
    const terms = document.getElementById("terms").checked;

    let valid = true;

    if (!name) {
      valid = false;
      document.getElementById("nameError").style.display = "block";
    }

    if (!validateEmail(email)) {
      valid = false;
      document.getElementById("emailError").style.display = "block";
    }

    if (!validatePhone(tel)) {
      valid = false;
      document.getElementById("telError").style.display = "block";
    }

    if (!validatePassword(password)) {
      valid = false;
      document.getElementById("passwordError").style.display = "block";
    }

    if (!refer) {
      valid = false;
      document.getElementById("referError").style.display = "block";
    }

    if (!terms) {
      valid = false;
      document.getElementById("termsError").style.display = "block";
    }

    if (valid) {
      alert("Form submitted successfully!");
      this.submit(); // Submit the form if all validations pass
    }
  });

function validateEmail(email) {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
}

function validatePhone(phone) {
  // Accepts formats like: 123-456-7890, (123) 456-7890, or 1234567890
  const re = /^(?:\+?\d{1,3}[- ]?)?\(?\d{3}\)?[- ]?\d{3}[- ]?\d{4}$/;
  return re.test(phone);
}

function validatePassword(password) {
  // At least 8 characters, 1 uppercase, 1 lowercase, 1 number
  const re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
  return re.test(password);
}