// =============================================
// Sub Task 3: JavaScript Form Validation
// =============================================

function validateForm() {
  let isValid = true;

  // Clear previous errors
  clearErrors();

  // --- 1. Student Name: should not be empty ---
  const name = document.getElementById("studentName").value.trim();
  if (name === "") {
    showError("nameError", "studentName", "Student name cannot be empty.");
    isValid = false;
  }

  // --- 2. Email: must be in proper format ---
  const email = document.getElementById("email").value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (email === "") {
    showError("emailError", "email", "Email ID cannot be empty.");
    isValid = false;
  } else if (!emailRegex.test(email)) {
    showError(
      "emailError",
      "email",
      "Enter a valid email (e.g. name@domain.com).",
    );
    isValid = false;
  }

  // --- 3. Mobile: must be 10 valid digits ---
  const mobile = document.getElementById("mobile").value.trim();
  const mobileRegex = /^[6-9]\d{9}$/;
  if (mobile === "") {
    showError("mobileError", "mobile", "Mobile number cannot be empty.");
    isValid = false;
  } else if (!mobileRegex.test(mobile)) {
    showError(
      "mobileError",
      "mobile",
      "Enter a valid 10-digit mobile number (starts with 6-9).",
    );
    isValid = false;
  }

  // --- 4. Department: must be selected ---
  const dept = document.getElementById("department").value;
  if (dept === "") {
    showError("deptError", "department", "Please select your department.");
    isValid = false;
  }

  // --- 5. Gender: at least one must be selected ---
  const genderSelected = document.querySelector('input[name="gender"]:checked');
  if (!genderSelected) {
    showError("genderError", null, "Please select your gender.");
    isValid = false;
  }

  // --- 6. Feedback Comments: not blank, min 10 words ---
  const feedback = document.getElementById("feedback").value.trim();
  const wordCount = feedback === "" ? 0 : feedback.split(/\s+/).length;
  if (feedback === "") {
    showError(
      "feedbackError",
      "feedback",
      "Feedback comments cannot be blank.",
    );
    isValid = false;
  } else if (wordCount < 10) {
    showError(
      "feedbackError",
      "feedback",
      `Feedback must be at least 10 words. Currently: ${wordCount} word(s).`,
    );
    isValid = false;
  }

  // --- If all valid, show success ---
  if (isValid) {
    document.getElementById("feedbackForm").style.display = "none";
    document.getElementById("successMsg").style.display = "block";
  }

  return false; // Prevent default form submission
}

// Helper: show error message and highlight field
function showError(errorId, fieldId, message) {
  document.getElementById(errorId).textContent = message;
  if (fieldId) {
    document.getElementById(fieldId).classList.add("input-error");
  }
}

// Helper: clear all errors and highlights
function clearErrors() {
  const errorIds = [
    "nameError",
    "emailError",
    "mobileError",
    "deptError",
    "genderError",
    "feedbackError",
  ];
  const fieldIds = ["studentName", "email", "mobile", "department", "feedback"];

  errorIds.forEach((id) => {
    document.getElementById(id).textContent = "";
  });

  fieldIds.forEach((id) => {
    document.getElementById(id).classList.remove("input-error");
  });

  document.getElementById("successMsg").style.display = "none";
  document.getElementById("feedbackForm").style.display = "block";
}
