// Script central para menu-propietario.html

// Variables globales para notificaciones
window._notifications = [];
window._notifPage = 1;
window._notifTotalPages = 1;
window._notifPageSize = 5;
window._notifTotalItems = 0;
window._notifTotalUnread = 0;

// Función para cargar las notificaciones (paginadas: 5 por página)
function cargarNotificaciones(page = 1, pageSize = 5) {
  submit("/notificaciones/listar", "page=" + encodeURIComponent(page) + "&pageSize=" + encodeURIComponent(pageSize));
}

// Función para actualizar el contador de notificaciones
function actualizarContadorNotificaciones() {
  const count = document.getElementById("notificationCount");
  const unread = typeof window._notifTotalUnread !== "undefined" && window._notifTotalUnread !== null
    ? window._notifTotalUnread
    : window._notifications ? window._notifications.filter((n) => !n.leida).length : 0;
  const total = unread;
  count.textContent = total;
  count.style.display = total > 0 ? "block" : "none";
}

// Función para marcar todas las notificaciones como leídas
async function marcarComoLeidas() {
  try {
    const response = await fetch("/notificaciones/marcarLeidas", { method: "POST" });
    if (!response.ok) {
      throw new Error("Error al marcar las notificaciones como leídas");
    }
    await cargarNotificaciones();
  } catch (error) {
    console.error("Error:", error);
  }
}

// Función para actualizar el contenido del dropdown de notificaciones
function actualizarDropdownNotificaciones() {
  const dropdown = document.getElementById("notificationDropdown");
  dropdown.innerHTML = "";

  const header = document.createElement("div");
  header.className = "notification-header";

  const title = document.createElement("h4");
  title.textContent = "Notificaciones";

  const markReadBtn = document.createElement("button");
  markReadBtn.className = "mark-read-btn";
  markReadBtn.textContent = "Marcar como leídas";
  markReadBtn.onclick = marcarComoLeidas;

  header.appendChild(title);
  const headerTools = document.createElement("div");
  headerTools.style.display = "flex";
  headerTools.style.gap = "8px";
  headerTools.style.alignItems = "center";
  
  const anyUnread = typeof window._notifTotalUnread !== "undefined" && window._notifTotalUnread !== null
    ? window._notifTotalUnread > 0
    : window._notifications.some((n) => !n.leida);
  if (anyUnread) {
    headerTools.appendChild(markReadBtn);
  }
  header.appendChild(headerTools);
  dropdown.appendChild(header);

  if (!Array.isArray(window._notifications) || window._notifications.length === 0) {
    const noNotifs = document.createElement("div");
    noNotifs.className = "notification-item";
    noNotifs.textContent = "No hay notificaciones nuevas";
    dropdown.appendChild(noNotifs);
  } else {
    window._notifications.forEach((notif) => {
      const notifItem = document.createElement("div");
      notifItem.className = "notification-item " + (notif.leida ? "read" : "unread");

      const mensaje = document.createElement("div");
      mensaje.textContent = notif.mensaje;

      const tiempo = document.createElement("div");
      tiempo.className = "notification-time";
      tiempo.textContent = notif.fechaHora;

      notifItem.appendChild(mensaje);
      notifItem.appendChild(tiempo);
      dropdown.appendChild(notifItem);
    });
  }

  const totalPages = window._notifTotalPages || 1;
  let currentPage = window._notifPage || 1;
  if (totalPages > 1) {
    const pager = document.createElement("div");
    pager.className = "notification-pager";
    pager.style.display = "flex";
    pager.style.gap = "6px";
    pager.style.alignItems = "center";
    pager.style.justifyContent = "center";

    const first = document.createElement("button");
    first.textContent = "«";
    first.title = "Primera página";
    first.disabled = currentPage <= 1;
    first.onclick = () => { cargarNotificaciones(1, window._notifPageSize || 5); };

    const prev = document.createElement("button");
    prev.textContent = "‹";
    prev.title = "Anterior";
    prev.disabled = currentPage <= 1;
    prev.onclick = () => { if (currentPage > 1) cargarNotificaciones(currentPage - 1, window._notifPageSize || 5); };

    pager.appendChild(first);
    pager.appendChild(prev);

    const maxButtons = 7;
    let start = Math.max(1, currentPage - Math.floor(maxButtons / 2));
    let end = Math.min(totalPages, start + maxButtons - 1);
    start = Math.max(1, end - maxButtons + 1);

    for (let i = start; i <= end; i++) {
      const pbtn = document.createElement("button");
      pbtn.textContent = String(i);
      pbtn.className = i === currentPage ? "page-btn active" : "page-btn";
      pbtn.disabled = i === currentPage;
      pbtn.onclick = (function (page) {
        return function () { cargarNotificaciones(page, window._notifPageSize || 5); };
      })(i);
      pager.appendChild(pbtn);
    }

    const next = document.createElement("button");
    next.textContent = "›";
    next.title = "Siguiente";
    next.disabled = currentPage >= totalPages;
    next.onclick = () => { if (currentPage < totalPages) cargarNotificaciones(currentPage + 1, window._notifPageSize || 5); };

    const last = document.createElement("button");
    last.textContent = "»";
    last.title = "Última página";
    last.disabled = currentPage >= totalPages;
    last.onclick = () => { cargarNotificaciones(totalPages, window._notifPageSize || 5); };

    pager.appendChild(next);
    pager.appendChild(last);
    dropdown.appendChild(pager);
  }
}

// Función para mostrar/ocultar el dropdown de notificaciones
function toggleNotifications() {
  const dropdown = document.getElementById("notificationDropdown");
  dropdown.classList.toggle("show");
}

// Handler para respuestas de notificaciones
function mostrar_notificaciones(payload) {
  if (Array.isArray(payload)) {
    window._notifications = payload;
    window._notifPage = 1;
    window._notifTotalPages = 1;
    window._notifPageSize = payload.length;
    window._notifTotalItems = payload.length;
  } else if (payload && typeof payload === "object" && payload.items) {
    window._notifications = Array.isArray(payload.items) ? payload.items : [];
    window._notifPage = payload.page || 1;
    window._notifTotalPages = payload.totalPages || 1;
    window._notifPageSize = payload.pageSize || window._notifications.length || 5;
    window._notifTotalItems = payload.totalItems || window._notifications.length || 0;
    window._notifTotalUnread = typeof payload.totalUnread !== "undefined" ? payload.totalUnread : null;
  } else {
    window._notifications = [];
    window._notifPage = 1;
    window._notifTotalPages = 1;
    window._notifPageSize = 5;
    window._notifTotalItems = 0;
    window._notifTotalUnread = 0;
  }
  actualizarContadorNotificaciones();
  actualizarDropdownNotificaciones();
  if (typeof renderNotificationsTable === "function") {
    renderNotificationsTable();
  }
}

function mostrar_datosUsuario(datos) {
  document.getElementById("nombreUsuario").textContent = datos.nombre + " " + datos.apellido;
}

function mostrar_redirLoginPropietario(url) {
  if (typeof url === "string" && url.length > 0) {
    window.location.href = url;
  } else {
    window.location.href = "/login-propietario.html";
  }
}
