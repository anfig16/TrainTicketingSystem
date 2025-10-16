//Toggle between signup and login forms
const signupBtn = document.getElementById("signup-slider");
const loginBtn = document.getElementById("login-slider");
const signupForm = document.getElementById("signup-form-inner");
const loginForm  = document.getElementById("login-form-inner");
const logoutBtn = document.getElementById('logout-button');

//Default view is signup form
signupForm.style.display = 'block';
loginForm.style.display = "none";
signupBtn.classList.add("active");

//Toggle to signup form when Sign Up button clicked
signupBtn.addEventListener('click', (e) => {
	//Stop page from reloading
	e.preventDefault();
	signupForm.style.display = 'block';
	loginForm.style.display = 'none';
	signupBtn.classList.add("active");
	loginBtn.classList.remove("active");
	
	//Clear error message when switching forms
	document.getElementById('error-message').textContent = '';
});

//Toggle to log in form when Log In button clicked
loginBtn.addEventListener('click', (e) => {
	//Stop page from reloading
	e.preventDefault();
	signupForm.style.display = 'none';
	loginForm.style.display = 'block';
	loginBtn.classList.add("active");
	signupBtn.classList.remove("active");
	
	//Clear error message when switching forms
	document.getElementById('error-message').textContent = '';
});

//Send passenger signup info to database once sign up button is clicked
signupForm.addEventListener('submit', async (e) => {
	//Stop page from reloading
	e.preventDefault();
	
	//Save passenger input as an object
	const passengerData = {
	    pName: document.getElementById('name').value,
	    email: document.getElementById('email').value,
	    passcode: document.getElementById('password').value,
		phone: document.getElementById('phone').value
	  };
	
	//Fetch API- save passenger input to backend (MySQL database)
	const response = await fetch('/passengers/createPassenger', {
	      method: 'POST',
	      headers: {
	        'Content-Type': 'application/json',
	      },
		  credentials: 'include',
	      body: JSON.stringify(passengerData)
	});
	
	//Get text response
	const message = await response.text(); 
	
	if (response.ok) {
		  window.location.href = 'home.html';
		} else {
		  document.getElementById('error-message').textContent = message;
		}
});

//Verify login info from database
loginForm.addEventListener('submit', async (e) => {
	//Stop page from reloading
	e.preventDefault();
	
	//Save passenger login input
	const email = document.getElementById('email-login').value;
	const password = document.getElementById('password-login').value;
	
	
	//Login process
	const response = await fetch('/passengers/login', {
	  method: 'POST',
	  headers: { 'Content-Type': 'application/json' },
	  credentials: 'include',
	  body: JSON.stringify({ email: email, passcode: password })
	});

	const message = await response.text(); 
	
	if (response.ok) {
	  window.location.href = 'home.html';
	} else {
	  document.getElementById('error-message').textContent = message;
	}
	
});

//Logout a passenger
logoutBtn.addEventListener('click', async (e) => {
	e.preventDefault();
	const response = await fetch('/passengers/logout', {
		method: 'POST',
		credentials: 'include' 
	});

	if (response.ok) {
		window.location.href = 'index.html'; 
	} else {
		alert('Logout failed. Please try again.');
	}
});

//When loading the signup/login page, check for any error messages to display
document.addEventListener('DOMContentLoaded', async () => {
  const params = new URLSearchParams(window.location.search);
  const message = params.get('message');
  const error = document.getElementById('error-message');

  if (message) {
    if (error) {
      error.textContent = message;
    }
  }

  //Clean up the URL
  if (window.history.replaceState) {
	const cleanUrl = window.location.pathname;
    window.history.replaceState({}, document.title, cleanUrl);
  }
  
  	const result = await fetch('/passengers/checkSession', {
  		credentials: 'include'
  	});
  	const data = await result.json();
  	
  	if(data.loggedIn) {
  		error.textContent = "You are already logged in"
  	}
});