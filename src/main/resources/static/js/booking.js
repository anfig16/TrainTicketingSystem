const logoutBtn = document.getElementById('logout-button');
const seatMapDiv = document.getElementById('seat-map');
const confirmBtn = document.getElementById('confirm-btn');
const confirmationBox = document.getElementById('confirmation-box');
const ticketInfo = document.getElementById('ticket-info');

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


//Get selected route from session storage
const selectedRoute = JSON.parse(sessionStorage.getItem('selectedRoute'));
if (!selectedRoute) {
	alert('No route selected');
	window.location.href = 'home.html';
}
document.getElementById('route-title').innerText = `Select Your Seat for ${selectedRoute.routeName} (${selectedRoute.date})`;

let seats = [];
let selectedSeats = [];
let passenger = null;

//Retrieve logged-in passenger info from database
async function fetchPassenger() {
	try {
		const res = await fetch('/passengers/sessionPassenger', { credentials: 'include' });
		if (res.ok) {
			passenger = await res.json();
		} else if (res.status === 401) {
			alert('You must log in first');
			window.location.href = 'login.html';
		} else {
			alert('Failed to fetch passenger info');
		}
	} catch (err) {
		console.error(err);
		alert('Error fetching passenger info');
	}
}

//Retrieve seats for the trainId associated with the selected route
async function fetchSeats(trainID) {
	const res = await fetch(`/seats/train/${trainID}`);
	seats = await res.json();
	renderSeats();
}

//Display the seat map
function renderSeats() {
	seatMapDiv.innerHTML = '';
	seats.forEach(seat => {
		const seatBtn = document.createElement('button');
		seatBtn.innerText = seat.seatNo;
		seatBtn.className = 'seat';
		if (!seat.seatAvailable) seatBtn.disabled = true;
		if (selectedSeats.includes(seat.seatNo)) seatBtn.classList.add('selected');

		seatBtn.addEventListener('click', () => {
			if (selectedSeats.includes(seat.seatNo)) {
				selectedSeats = selectedSeats.filter(s => s !== seat.seatNo);
			} else {
				selectedSeats.push(seat.seatNo);
			}
			renderSeats();
		});

		seatMapDiv.appendChild(seatBtn);
	});
}

//Confirm seat booking
confirmBtn.addEventListener('click', async () => {
	if (selectedSeats.length === 0) {
		alert('Select at least one seat');
		return;
	}
	if (!passenger) {
		alert('Passenger info not loaded');
		return;
	}

	const tickets = selectedSeats.map(seatNo => ({
		date: selectedRoute.date,
		price: 50, //Default price
		passenger: { pId: passenger.pId },
		seat: { seatNo: seatNo },
		route: { routeName: selectedRoute.routeName }
	}));

	try {
		for (const ticket of tickets) {
			const res = await fetch('/tickets/createTicket', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(ticket),
				credentials: 'include'
			});
			if (!res.ok) throw new Error(`Booking failed for seat ${ticket.seat.seatNo}`);
		}
		
		//Hide seat map
		seatMapDiv.style.display = 'none';
		confirmBtn.style.display = 'none';

		//Show confirmation info
		confirmationBox.style.display = 'block';
		ticketInfo.innerHTML = `Route: ${selectedRoute.routeName}<br>Date: ${selectedRoute.date}<br>Seats: ${selectedSeats.join(', ')}<br>`;

		//Navigation buttons
		let confirmationButtons = document.getElementById('confirmation-buttons');
		if (!confirmationButtons) {
		    confirmationButtons = document.createElement('div');
		    confirmationButtons.id = 'confirmation-buttons';
		    confirmationButtons.className = 'confirmation-buttons';
		    confirmationBox.appendChild(confirmationButtons);
		}

		//Clear previous buttons
		confirmationButtons.innerHTML = '';

		const backBtn = document.createElement('button');
		backBtn.innerText = 'Back to Routes';
		backBtn.className = 'confirm-button';
		backBtn.addEventListener('click', () => window.location.href = 'home.html');

		const myTicketsBtn = document.createElement('button');
		myTicketsBtn.innerText = 'My Tickets';
		myTicketsBtn.className = 'confirm-button';
		myTicketsBtn.addEventListener('click', () => window.location.href = 'tickets.html');

		//Add buttons to container
		confirmationButtons.appendChild(backBtn);
		confirmationButtons.appendChild(myTicketsBtn);

		//Reset selected seats
		selectedSeats = [];
		fetchSeats(selectedRoute.train.trainID);

	} catch (err) {
		alert(err.message);
	}
});

//Load page
(async () => {
	await fetchPassenger();
	await fetchSeats(selectedRoute.train.trainID);
})()

