async function api(method, url, body) {
  const res = await fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: body ? JSON.stringify(body) : undefined,
  });
  const data = await res.json();
  if (!res.ok) throw new Error(data.message || "Request failed");
  return data;
}

function label(player) {
  if (player === "RED") return "Red";
  if (player === "BLUE") return "Blue";
  return "";
}

function render(state) {
  const cells = document.querySelectorAll("#board .cell");
  state.board.forEach((row, rowIndex) => {
    row.forEach((player, columnIndex) => {
      const cell = cells[rowIndex * row.length + columnIndex];
      if (player === "RED") cell.style.background = "red";
      else if (player === "BLUE") cell.style.background = "blue";
      else cell.style.background = "white";
    });
  });

  document.getElementById("turn").textContent =
    "Current turn: " + label(state.currentPlayer) + "  Moves: " + state.moveCount;

  const result = document.getElementById("result");
  if (state.status === "WIN") result.textContent = label(state.winner) + " wins";
  else if (state.status === "DRAW") result.textContent = "Draw";
  else result.textContent = "";
}

function warn(message) {
  const result = document.getElementById("result");
  const current = result.textContent;
  if (current === "Draw" || current.endsWith(" wins")) {
    result.textContent = current + ". " + message;
  } else {
    result.textContent = message;
  }
}

document.getElementById("board").addEventListener("click", async (event) => {
  const cell = event.target.closest(".cell");
  if (!cell) return;
  const cells = [...document.querySelectorAll("#board .cell")];
  const column = cells.indexOf(cell) % 7;
  try {
    render(await api("POST", "/api/game/moves", { column }));
  } catch (err) {
    warn(err.message);
  }
});

document.getElementById("reset").addEventListener("click", async () => {
  try {
    render(await api("POST", "/api/game/reset"));
  } catch (err) {
    warn(err.message);
  }
});

api("GET", "/api/game").then(render).catch((err) => warn(err.message));
