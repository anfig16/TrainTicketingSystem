const logoutBtn = document.getElementById('logout-button');

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

//Nav bar button event listeners
document.getElementById("Routes").addEventListener("click", () => {
  window.location.href = "home.html";
});

document.getElementById("Tickets").addEventListener("click", () => {
  window.location.href = "tickets.html";
});



//Stores routes retrieved from database
let routes = [];

//Retrive stored routes from database
async function fetchRoutes() {
	const res = await fetch('/routes/getAll');
	routes = await res.json();
	renderRoutes(routes);
}

async function fetchPassenger() {
	try {
		const res = await fetch('/passengers/sessionPassenger', { credentials: 'include' });
		if (res.ok) {
			passenger = await res.json();

			//Insert logged-in passenger's name in greeting
			const greetingDiv = document.getElementById('greeting');
			greetingDiv.innerText = `Hello ${passenger.pName}, where would you like to go today?`;
		} else if (res.status === 401) {
			window.location.href = 'login.html';
		}
	} catch (err) {
		console.error(err);
	}
}

//Display routes
function renderRoutes(routeList) {
	const container = document.getElementById('routes-container');
	//Clear any previously displayed routes
	container.innerHTML = ''; 
	
	//Display all routes
	routeList.forEach(route => {
		const card = document.createElement('div');
		card.className = 'route-card';
		card.innerHTML = `
      		<h2>${route.routeName}</h2>
      		<p>Start: <strong>${route.startDest}</strong><br>End: <strong>${route.endDest}</strong></p>
      		<p>Date: ${route.date}</p>`;

		//Hover sliding highlight effect using CSS
		card.addEventListener('click', () => {
			sessionStorage.setItem('selectedRoute', JSON.stringify(route));
			window.location.href = 'booking.html';
		});

		container.appendChild(card);
	});
}

//Searching/filtering routes through search bar
function filterRoutes() {
  const startVal = document.getElementById('search-start').value.toLowerCase();
  const endVal = document.getElementById('search-end').value.toLowerCase();
  const dateVal = document.getElementById('search-date').value;

  const filtered = routes.filter(r =>
    r.startDest.toLowerCase().includes(startVal) &&
    r.endDest.toLowerCase().includes(endVal) &&
    (dateVal ? r.date === dateVal : true)
  );

  renderRoutes(filtered);
}

//Event listeners
document.getElementById('search-start').addEventListener('input', filterRoutes);
document.getElementById('search-end').addEventListener('input', filterRoutes);
document.getElementById('search-date').addEventListener('change', filterRoutes);

//Display all the routes when page loads initially
fetchPassenger();
fetchRoutes();