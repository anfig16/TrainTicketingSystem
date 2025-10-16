const logoutBtn = document.getElementById('logout-button');
const ticketsContainer = document.getElementById('tickets-container');

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

let passenger = null;
let tickets = [];

//Retrive logged-in passenger saved session attributes
async function fetchPassenger() {
	try {
		const res = await fetch('/passengers/sessionPassenger', { credentials: 'include' });
		if (res.ok){
			passenger = await res.json();
			
			const header = document.getElementById('tickets-header');
			header.innerText = `${passenger.pName}'s Tickets`;
		} 
		else if (res.status === 401){window.location.href = 'login.html';} 
	} catch (err) {
		console.error(err);
	}
}

//Retrieve tickets for logged-in passenger
async function fetchTickets() {
	if (!passenger) return;

	try {
		const res = await fetch(`/tickets/passenger/${passenger.pId}`, { credentials: 'include' });
		if (!res.ok) throw new Error('Failed to fetch tickets');
		tickets = await res.json();
		renderTickets();
	} catch (err) {
		console.error(err);
		ticketsContainer.innerText = 'Error fetching tickets';
	}
}

//Group tickets by routeName + date (groups multiple seats since individual seats are stored in individual tickets)
function groupTickets(tickets) {
	const grouped = {};
	tickets.forEach(ticket => {
		const key = `${ticket.route.routeName} - ${ticket.date}`;
		if (!grouped[key]) grouped[key] = [];
		grouped[key].push(ticket);
	});
	return grouped;
}

//Delete a ticket
async function deleteTicket(ticketNum) {
    try {
        const res = await fetch(`/tickets/${ticketNum}`, {
            method: 'DELETE',
            credentials: 'include'
        });
        if (!res.ok) throw new Error('Failed to delete ticket');

        // Remove ticket from the array and re-render
        tickets = tickets.filter(t => t.ticketNum !== ticketNum);
        renderTickets();
    } catch (err) {
        console.error(err);
        alert(err.message);
    }
}

//Display all tickets
function renderTickets() {
    ticketsContainer.innerHTML = '';
    if (tickets.length === 0) {
        ticketsContainer.innerText = 'No tickets booked yet.';
        return;
    }

    const grouped = groupTickets(tickets);

    Object.keys(grouped).forEach(groupKey => {
        const groupTickets = grouped[groupKey];

        const groupDiv = document.createElement('div');
        groupDiv.className = 'ticket-group';

        const title = document.createElement('h2');
        title.innerText = groupKey;
        groupDiv.appendChild(title);

        const seatList = document.createElement('ul');
        groupTickets.forEach(ticket => {
            const li = document.createElement('li');
            li.innerText = `Seat: ${ticket.seat.seatNo} | Price: $${ticket.price}`;

            //Delete ticket button
            const delBtn = document.createElement('button');
            delBtn.innerText = 'Cancel';
            delBtn.addEventListener('click', () => deleteTicket(ticket.ticketNum));

            li.appendChild(delBtn);
            seatList.appendChild(li);
        });

        groupDiv.appendChild(seatList);
        ticketsContainer.appendChild(groupDiv);
    });
}

//Load initial page
(async () => {
	await fetchPassenger();
	await fetchTickets();
})();