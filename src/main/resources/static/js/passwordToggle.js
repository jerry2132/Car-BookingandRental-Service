    function togglePasswordVisibility(inputId) {
        const passwordInput = document.getElementById(inputId);
        const togglePasswordButton = document.getElementById("`toggle${inputId.charAt(0).toUpperCase() + inputId.slice(1)}`");

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            togglePasswordButton.innerHTML = '<i class="far fa-eye"></i>';
        } else {
            passwordInput.type = "password";
            togglePasswordButton.innerHTML = '<i class="far fa-eye-slash"></i>';
        }
    }
    
    
        function validatePasswords() {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('reEnterPassword').value;

        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return false; // Prevent the form from submitting
        }

        return true; // Allow the form to submit
    }