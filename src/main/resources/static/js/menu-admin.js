// Script central para menu-admin.html

document.addEventListener("DOMContentLoaded", function () {
  urlIniciarVista = "/acceso/iniciarAdministrador";
  urlCierreVista = "/acceso/cerrarSesion";

  cargarPuestos();
  cargarBonificacionesTipos();
  initTabs();
  
  const btnLogout = document.getElementById('btn-logout');
  if (btnLogout) {
    btnLogout.addEventListener('click', function(e){
      e.preventDefault();
      submit('/acceso/logoutAdministrador','');
    });
  }
});

// Inicializa el comportamiento de las pestañas
function initTabs() {
  const tabButtons = document.querySelectorAll(".tab-btn");
  tabButtons.forEach((btn) => {
    btn.addEventListener("click", () => switchTab(btn.dataset.tab));
    btn.addEventListener("keydown", (e) => {
      if (e.key === "Enter" || e.key === " ") {
        e.preventDefault();
        switchTab(btn.dataset.tab);
      }
    });
  });
}

function switchTab(tabId) {
  document.querySelectorAll(".tab-btn").forEach((b) => b.classList.remove("active"));
  const activeBtn = document.querySelector(`.tab-btn[data-tab="${tabId}"]`);
  if (activeBtn) activeBtn.classList.add("active");

  document.querySelectorAll(".tab-panel").forEach((p) => p.classList.remove("active"));
  const panel = document.getElementById(tabId);
  if (panel) panel.classList.add("active");
  
  if (tabId === "tab-emular") {
    const sel = document.getElementById("peajeSelect");
    if (sel && sel.options.length === 0) cargarPuestos();
    document.getElementById("matricula")?.focus();
  }

  if (tabId === "tab-bonificaciones") {
    const selBon = document.getElementById("bon-tipo");
    if (selBon && selBon.options.length === 0) cargarBonificacionesTipos();
    const selPuesto = document.getElementById("bon-puesto");
    if (selPuesto && selPuesto.options.length === 0) cargarPuestosEn("bon-puesto");
  }
}

// Cargar puestos
function cargarPuestos() {
  window._selectTargetForPuestos = "peajeSelect";
  submit("/puestos/listar", "");
}

function cargarPuestosEn(selectId) {
  window._selectTargetForPuestos = selectId;
  submit("/puestos/listar", "");
}

function cargarBonificacionesTipos() {
  submit("/bonificaciones/listarTipos", "");
}

// Handlers para respuestas del servidor
function mostrar_puestosLista(lista) {
  const selId = window._selectTargetForPuestos || "peajeSelect";
  const sel = document.getElementById(selId);
  if (!sel) return;
  sel.innerHTML = '<option value="">Seleccionar puesto</option>';
  if (!Array.isArray(lista)) return;
  lista.forEach((valor) => {
    const opt = document.createElement("option");
    opt.value = valor;
    opt.textContent = valor;
    sel.appendChild(opt);
  });
  delete window._selectTargetForPuestos;
}

function mostrar_bonificacionesTipos(lista) {
  const sel = document.getElementById("bon-tipo");
  if (!sel) return;
  sel.innerHTML = '<option value="">Seleccionar bonificación</option>';
  if (!Array.isArray(lista)) return;
  lista.forEach((nombre) => {
    const opt = document.createElement("option");
    opt.value = nombre;
    opt.textContent = nombre;
    sel.appendChild(opt);
  });
}

function mostrar_datosUsuario(datos) {
  document.getElementById("nombreUsuario").textContent = datos.nombre + " " + datos.apellido;
}

function mostrar_mensaje(texto) {
  try {
    if (typeof mostrarMensaje === 'function') {
      mostrarMensaje(texto);
    } else {
      alert(texto);
    }
  } catch (e) {
    alert(texto);
  }
}

function mostrar_logoutExitoso(param) {
  let url = '/login-admin.html';
  if (typeof param === 'string') url = param;
  else if (param && param.parametro) url = param.parametro;
  else if (param && param.param) url = param.param;
  try {
    window.location.href = url;
  } catch (e) {
    window.location.href = '/login-admin.html';
  }
}

// Validaciones
function validarSelect(selectId, mensaje) {
  const sel = document.getElementById(selectId);
  if (!sel) return true;
  if (!sel.value || sel.value === "") {
    try {
      excepcionDeAplicacion(mensaje);
    } catch (e) {
      alert(mensaje);
    }
    sel.focus();
    return false;
  }
  return true;
}

function validarNotEmpty(inputId, mensaje) {
  const inp = document.getElementById(inputId);
  if (!inp) return true;
  if (inp.value.trim() === "") {
    try {
      excepcionDeAplicacion(mensaje);
    } catch (e) {
      alert(mensaje);
    }
    inp.focus();
    return false;
  }
  return true;
}
