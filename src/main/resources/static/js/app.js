const notesList = document.getElementById("notesList");
const randomNoteBtn = document.getElementById("randomNoteBtn");
const randomNoteDiv = document.getElementById("randomNote");
const noteForm = document.getElementById("noteForm");
const messageDiv = document.getElementById("message");

const API_BASE = "/api/notes";

async function fetchAllNotes() {
    try {
        const res = await fetch(API_BASE);
        if (!res.ok) throw new Error("Failed to fetch notes.");
        const notes = await res.json();
        renderNotes(notes);
    } catch (err) {
        console.error(err);
        messageDiv.textContent = "Error loading notes.";
        messageDiv.className = "text-danger";
    }
}

function renderNotes(notes) {
    notesList.innerHTML = "";
    if (notes.length === 0) {
        notesList.innerHTML = "<li class='list-group-item'>No notes found.</li>";
        return;
    }
    notes.forEach(note => {
        const li = document.createElement("li");
        li.className = "list-group-item";
        li.textContent = `${note.title} - ${new Date(note.createdAt).toLocaleString()}`;
        notesList.appendChild(li);
    });
}

randomNoteBtn.addEventListener("click", async () => {
    try {
        const res = await fetch(`${API_BASE}/random`);
        if (res.status === 204) {
            randomNoteDiv.style.display = "block";
            randomNoteDiv.textContent = "No notes available.";
            return;
        }
        if (!res.ok) throw new Error("Failed to fetch random note.");
        const note = await res.json();
        randomNoteDiv.style.display = "block";
        randomNoteDiv.textContent = `${note.title}\n\n${note.content}\n\nCreated at: ${new Date(note.createdAt).toLocaleString()}`;
    } catch (err) {
        randomNoteDiv.style.display = "block";
        randomNoteDiv.textContent = "Error loading random note.";
        console.error(err);
    }
});

noteForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    messageDiv.textContent = "";
    messageDiv.className = "";
    const title = document.getElementById("title").value.trim();
    const content = document.getElementById("content").value.trim();

    if (!title || !content) {
        messageDiv.textContent = "Both title and content are required.";
        messageDiv.className = "text-danger";
        return;
    }

    try {
        const res = await fetch(API_BASE, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, content }),
        });
        if (!res.ok) throw new Error("Failed to add note.");
        const newNote = await res.json();
        messageDiv.textContent = "Note added successfully!";
        messageDiv.className = "text-success";
        noteForm.reset();
        fetchAllNotes();
    } catch (err) {
        messageDiv.textContent = "Error adding note.";
        messageDiv.className = "text-danger";
        console.error(err);
    }
});

window.onload = fetchAllNotes;
