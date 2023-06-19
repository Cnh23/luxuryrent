package com.rent.luxury.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.luxury.model.Alquiler;
import com.rent.luxury.model.AlquilerForm;
import com.rent.luxury.model.Contacto;
import com.rent.luxury.model.ContactoForm;
import com.rent.luxury.model.Estados;
import com.rent.luxury.model.Role;
import com.rent.luxury.model.Usuarios;
import com.rent.luxury.model.UsuariosForm;
import com.rent.luxury.model.Vehiculos;
import com.rent.luxury.model.VehiculosForm;
import com.rent.luxury.repository.AlquilerRepositorio;
import com.rent.luxury.repository.ContactoRepositorio;
import com.rent.luxury.repository.RoleRepository;
import com.rent.luxury.repository.UsuariosRepositorio;
import com.rent.luxury.repository.VehiculosRepositorio;
import com.rent.luxury.services.AlquilerService;
import com.rent.luxury.services.EstadoService;
import com.rent.luxury.services.UsuarioService;
import com.rent.luxury.services.VehiculoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Controlador principal de la aplicación Luxury Rent.
 * Este controlador maneja las solicitudes relacionadas con la página de inicio, catálogo de vehículos,
 * inicio de sesión, perfil de usuario y otras funcionalidades relacionadas.
 */
@Controller
public class ControladorPrincipal {
	@Autowired
	private UsuariosRepositorio usuariosRepositorio;
	@Autowired
	private RoleRepository rolesRepositorio;
	@Autowired
	private VehiculosRepositorio vehiculosRepositorio;
	@Autowired
	private AlquilerRepositorio alquilerRepositorio;
	@Autowired
	private ContactoRepositorio contactoRepositorio;
	@Autowired
	private AlquilerService alquilerServicio;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private EstadoService estadoService;

//////////////////////////////////////////////////////////////////////////////////////////////////////////    
	
    /**
     * Método que maneja la solicitud GET "/inicio".
     * Devuelve la página de inicio con una lista de vehículos destacados.
     *
     * @param modelo El objeto Model para agregar atributos a la vista.
     * @return La vista "index" que muestra la página de inicio.
     */
	@GetMapping(path = "/inicio")
	public String iniPg(Model modelo) {
	    List<Vehiculos> vehiculos = (List<Vehiculos>) vehiculosRepositorio.findByAlquiladoFalse();
	    Date fechaActual = new Date();
	    for (Alquiler alquiler : alquilerRepositorio.findAll()) {
	        if (alquiler.getFechaRecogida().before(fechaActual) && (alquiler.getFechaEntrega() == null || alquiler.getFechaEntrega().after(fechaActual))) {
	            vehiculos.remove(alquiler.getVehiculo());
	        }
	    }
	    
	    // Ordenar los vehículos por precio de forma ascendente
	    Collections.sort(vehiculos, new Comparator<Vehiculos>() {
	        @Override
	        public int compare(Vehiculos v1, Vehiculos v2) {
	            return Double.compare(v1.getPreciopordia(), v2.getPreciopordia());
	        }
	    });
	    
	    // Obtener los primeros 6 vehículos con el precio más bajo
	    List<Vehiculos> vehiculosDestacados = vehiculos.subList(0, Math.min(6, vehiculos.size()));
	    
	    modelo.addAttribute("lista", vehiculosDestacados);
	    return "index";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * Método que maneja la solicitud GET "/catalogovehiculos".
     * Devuelve la página del catálogo de vehículos con la lista de vehículos disponibles.
     *
     * @param modelo El objeto Model para agregar atributos a la vista.
     * @return La vista "catalogo" que muestra el catálogo de vehículos.
     */
	@GetMapping("/catalogovehiculos")
	public String getCatalogo(Model modelo) {
	    List<Vehiculos> vehiculos = (List<Vehiculos>) vehiculosRepositorio.findByAlquiladoFalse();
	    Date fechaActual = new Date();
	    for (Alquiler alquiler : alquilerRepositorio.findAll()) {
	        if (alquiler.getFechaRecogida().before(fechaActual) && (alquiler.getFechaEntrega() == null || alquiler.getFechaEntrega().after(fechaActual))) {
	        	vehiculos.remove(alquiler.getVehiculo());
	        }
	    }
	    modelo.addAttribute("lista", vehiculos);
		return "catalogo";
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * Método que maneja la solicitud GET "/iniciarsesion".
     * Devuelve la página de inicio de sesión.
     *
     * @param usuForm El objeto UsuariosForm para almacenar los datos del formulario de inicio de sesión.
     * @return La vista "iniciarsesion" que muestra la página de inicio de sesión.
     */
	@GetMapping("/iniciarsesion")
	public String getInicioSesion(UsuariosForm usuForm) {
		return "/iniciarsesion";
	}
	
    /**
     * Método que maneja la solicitud POST "/iniciarsesion".
     * Verifica las credenciales de inicio de sesión y redirige a la página correspondiente según el rol del usuario.
     *
     * @param usuForm        El objeto UsuariosForm que contiene los datos del formulario de inicio de sesión.
     * @param bindingResult  El objeto BindingResult para realizar la validación de los datos del formulario.
     * @param session        La HttpSession para almacenar la información de la sesión.
     * @param modelo         El objeto Model para agregar atributos a la vista.
     * @return La vista correspondiente según el rol del usuario.
     */
	@PostMapping("/iniciarsesion")
	public String checksesionini(@Valid UsuariosForm usuForm, BindingResult bindingResult, HttpSession session,
	Model modelo) {
	String email = usuForm.getEmail();
	String passwd = usuForm.getPassword();
	Optional<Usuarios> usuarioEncontrado = usuariosRepositorio.findByEmail(email);
	if (usuarioEncontrado.isPresent()) {
	// Almacenar el rol del usuario en la variable de sesión
	session.setAttribute("rol", usuarioEncontrado.get().getRole().getNombrerol());
	// Almacenar el objeto Usuario en la sesión
	session.setAttribute("usuario", usuarioEncontrado.get());
	
	if (usuarioEncontrado.get().getRole().getNombrerol().equals("ADMIN")
	&& usuarioEncontrado.get().getPassword().equals(passwd)) {
	return "redirect:/dashboard";
	}  else if (usuarioEncontrado.get().getRole().getNombrerol().equals("CLIENTE")
	&& usuarioEncontrado.get().getPassword().equals(passwd)) {
	return "redirect:/inicio";
	}
	}
	modelo.addAttribute("mensaje",
	"El correo " + usuForm.getEmail() + " no existe, o la contraseña es incorrecta.");
	return "iniciarsesion";
	}
	
    /**
     * Método que maneja la solicitud GET "/cerrarsesion".
     * Cierra la sesión del usuario y redirige a la página de inicio.
     *
     * @param session La HttpSession para invalidar la sesión del usuario.
     * @return La vista "redirect:/inicio" que redirige a la página de inicio.
     */
	@GetMapping("/cerrarsesion")
	public String cerrarSesion(HttpSession session) {
	session.invalidate();
	return "redirect:/inicio";
	}
    /**
     * Método que maneja la solicitud POST "/darseDeBaja".
     * Permite al usuario darse de baja eliminando su información de la base de datos y cerrando la sesión.
     *
     * @param session La HttpSession para verificar la sesión del usuario y eliminar su información.
     * @return La vista "redirect:/inicio" que redirige a la página de inicio.
     */
	@PostMapping("/darseDeBaja")
	public String darseDeBaja(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
	    // Obtener el usuario en sesión
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

	    // Eliminar al usuario de la base de datos
	    usuariosRepositorio.delete(usuario);

	    // Eliminar la sesión del usuario
	    session.invalidate();

	    return "redirect:/inicio";
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Método que maneja la solicitud GET "/modificarusuarioperfil".
     * Devuelve la página de modificación de datos de perfil de usuario.
     *
     * @param modelo  El objeto Model para agregar atributos a la vista.
     * @param session La HttpSession para verificar la sesión del usuario.
     * @return La vista "profile" que muestra la página de modificación de datos de perfil.
     */
	@GetMapping("/modificarusuarioperfil")
	public String getmodificardatosperfil(Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");
	    
	    modelo.addAttribute("usuario", usuario);

	    return "profile";
	}
    /**
     * Método que maneja la solicitud POST "/modificarusuarioperfil".
     * Modifica los datos de perfil de usuario y guarda los cambios en la base de datos.
     *
     * @param usuario El objeto Usuarios que contiene los datos modificados del usuario.
     * @param result  El objeto BindingResult para realizar la validación de los datos del formulario.
     * @param session La HttpSession para verificar la sesión del usuario.
     * @return La vista "/profile" que muestra la página de perfil de usuario actualizado.
     */
	@PostMapping("/modificarusuarioperfil")
	public String modificarUsuarioPerfil(@ModelAttribute("usuario") Usuarios usuario, BindingResult result ,HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		if (result.hasErrors()) {
	        return "profile";
	    }
	    usuarioService.actualizarUsuario(usuario);
	    return "/profile";
	}
	
    /**
     * Método que maneja la solicitud POST "/cambiarcontrasena".
     * Cambia la contraseña del usuario y guarda los cambios en la base de datos.
     *
     * @param currentPassword La contraseña actual del usuario.
     * @param newPassword     La nueva contraseña del usuario.
     * @param confirmPassword La confirmación de la nueva contraseña del usuario.
     * @param session         La HttpSession para verificar la sesión del usuario.
     * @param model           El objeto Model para agregar atributos a la vista.
     * @return La vista "profile" que muestra la página de perfil de usuario.
     */
	@PostMapping("/cambiarcontrasena")
	public String cambiarContrasena(@RequestParam("currentPassword") String currentPassword,
	                                @RequestParam("newPassword") String newPassword,
	                                @RequestParam("confirmPassword") String confirmPassword,
	                                HttpSession session,
	                                Model model) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
	    // Obtener el usuario actual de la sesión (asumiendo que tienes un atributo "usuario" en la sesión)
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

	    // Verificar que la contraseña actual sea correcta
	    if (!usuario.getPassword().equals(currentPassword)) {
	        model.addAttribute("error", "La contraseña actual no es correcta");
	        return "redirect:/profile"; // Cambia "profile" por la vista correspondiente
	    }

	    // Verificar que la nueva contraseña y la confirmación coincidan
	    if (!newPassword.equals(confirmPassword)) {
	        model.addAttribute("error", "La nueva contraseña y la confirmación no coinciden");
	        return "redirect:/profile"; // Cambia "profile" por la vista correspondiente
	    }

	    // Actualizar la contraseña del usuario en la base de datos
	    usuario.setPassword(newPassword);
	    usuariosRepositorio.save(usuario);

	    model.addAttribute("success", "La contraseña se ha cambiado correctamente");
	    return "profile"; // Cambia "profile" por la vista correspondiente
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping("/about")
	public String getInfo(UsuariosForm usuForm) {
		return "/about";
	}
	
	/**
	 * Método que maneja la solicitud GET "/services".
	 * Devuelve la página de servicios.
	 *
	 * @param usuForm El objeto UsuariosForm para almacenar los datos del formulario de usuario.
	 * @return La vista "services" que muestra la página de servicios.
	 */
	@GetMapping("/services")
	public String getSericios(UsuariosForm usuForm) {
		return "/services";
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Método que maneja la solicitud GET "/profile".
	 * Devuelve la página de perfil de usuario con las reservas del usuario.
	 *
	 * @param modelo  El objeto Model para agregar atributos a la vista.
	 * @param session La HttpSession para verificar la sesión del usuario.
	 * @return La vista "profile" que muestra la página de perfil de usuario con las reservas.
	 */
	@GetMapping("/profile")
	public String getperfil(Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

	    // Obtener las reservas del usuario
	    List<Alquiler> reservas = alquilerServicio.obtenerReservasPorUsuario(usuario);

	    // Agregar las reservas al modelo
	    modelo.addAttribute("reservas", reservas);

	    // Agregar el usuario al modelo
	    modelo.addAttribute("usuario", usuario);

	    // Devolver la vista "profile.html"
	    return "profile";
	}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/register")
	public String getRegistro(UsuariosForm usuForm) {
		return "/register";
	}
	
	/**
	 * Método que maneja la solicitud POST "/register".
	 * Registra un nuevo usuario y guarda los datos en la base de datos.
	 *
	 * @param usuForm        El objeto UsuariosForm que contiene los datos del formulario de registro.
	 * @param bindingResult  El objeto BindingResult para realizar la validación de los datos del formulario.
	 * @param session        La HttpSession para verificar la sesión del usuario.
	 * @param modelo         El objeto Model para agregar atributos a la vista.
	 * @return La vista "/register" que muestra la página de registro con un mensaje de confirmación.
	 */
	@PostMapping("/register")
	public String checkregistro(@Valid UsuariosForm usuForm, BindingResult bindingResult, HttpSession session,
			Model modelo) {
		if (bindingResult.hasErrors()) {
			return "/registro";
		}
		Role rol = rolesRepositorio.findByNombrerol("CLIENTE");
		Usuarios usuario = new Usuarios(usuForm.getNombre(), usuForm.getApellidos(), usuForm.getDni(), usuForm.getDireccion(),
				usuForm.getTelefono(), usuForm.getEmail(), usuForm.getPassword(), rol);
		usuariosRepositorio.save(usuario);
		modelo.addAttribute("mensaje", "Gracias por registrarte, " + usuForm.getNombre() + " " + usuForm.getApellidos()
				+ ". Por favor, Inicie sesión con sus credenciales.");
		return "/register";
	}
	@GetMapping("/contactar")
	public String getInsertarvehiculos(ContactoForm contactoForm, Model modelo) {
		return "/contact";
	}

	/**
	 * Método que maneja la solicitud POST "/contactar".
	 * Guarda los datos del formulario de contacto en la base de datos y muestra un mensaje de confirmación.
	 *
	 * @param contactoForm   El objeto ContactoForm que contiene los datos del formulario de contacto.
	 * @param bindingResult  El objeto BindingResult para realizar la validación de los datos del formulario.
	 * @param modelo         El objeto Model para agregar atributos a la vista.
	 * @return La vista "/contact" que muestra la página de contacto con un mensaje de confirmación.
	 */
	@PostMapping(path = "/contactar")
	public String checkvehiculosInfo(@Valid ContactoForm contactoForm, BindingResult bindingResult, Model modelo) {
		if (bindingResult.hasErrors()) {
			modelo.addAttribute("mensaje", "");
			return "/contact";
		}
		Contacto contacto = new Contacto(contactoForm.getNombre(), contactoForm.getEmail(), contactoForm.getMensaje());
		contactoRepositorio.save(contacto);
		modelo.addAttribute("mensaje", "Su mensaje ha sido enviado satisfactoriamente. Por favor, espere a que nos pongamos en contacto con usted.");
        // Limpiar los campos
        ContactoForm nuevoContactoForm = new ContactoForm();
        modelo.addAttribute("contactoForm", nuevoContactoForm);
		return "/contact";
	}
	
	/**
	 * Maneja la solicitud POST para crear una reserva.
	 *
	 * @param vehiculoId      el ID del vehículo seleccionado
	 * @param fechaRecogida   la fecha de recogida de la reserva
	 * @param fechaEntrega    la fecha de entrega de la reserva
	 * @param session         la sesión actual
	 * @return la vista o la redirección correspondiente
	 */
	@PostMapping("/reserva")
    public String postReserva(@RequestParam("vehiculoId") Integer vehiculoId,
                              @RequestParam("fechaRecogida") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaRecogida,
                              @RequestParam("fechaEntrega") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEntrega,
                              HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
        // Obtener el usuario de la sesión
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        // Obtener el vehículo por su ID
        Vehiculos vehiculo = vehiculoService.obtenerVehiculoPorId(vehiculoId);

        if (vehiculo == null) {
            // Manejar el caso en el que el vehículo no existe
            return "El vehículo seleccionado no existe.";
        }

        // Resto de la lógica de validación y creación del alquiler
        LocalDate fechaRecogidaLocal = fechaRecogida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaEntregaLocal = fechaEntrega.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int diasAlquiler = (int) ChronoUnit.DAYS.between(fechaRecogidaLocal, fechaEntregaLocal);
        if (diasAlquiler < 3) {
            fechaEntregaLocal = fechaRecogidaLocal.plusDays(3);
        }

        // Validar si el vehículo ya está alquilado en el rango de fechas especificado
        List<Alquiler> alquileresVehiculo = alquilerRepositorio.findByVehiculoIdAndFechas(vehiculoId, fechaRecogida, fechaEntrega);
        if (!alquileresVehiculo.isEmpty()) {
            // Manejar el caso en el que el vehículo ya está alquilado en el rango de fechas especificado
            return "El vehículo ya está alquilado en el rango de fechas especificado.";
        }

        // Validar si el cliente ya tiene un vehículo alquilado en el rango de fechas especificado
        List<Alquiler> alquileresCliente = alquilerRepositorio.findByUsuariosIdAndFechas(usuario.getId(), fechaRecogida, fechaEntrega);
        if (!alquileresCliente.isEmpty()) {
            // Manejar el caso en el que el cliente ya tiene un vehículo alquilado en el rango de fechas especificado
            return "El cliente ya tiene un vehículo alquilado en el rango de fechas especificado.";
        }

        // Crear el objeto Alquiler con los datos proporcionados
        Estados estado = estadoService.obtenerEstadoPorNombre("APROBADA"); // Obtener el estado por defecto
        Alquiler alquiler = new Alquiler(fechaRecogida, fechaEntrega, usuario, estado, vehiculo);

        // Guardar el alquiler en tu repositorio o servicio correspondiente
        alquilerRepositorio.save(alquiler);

        // Redirigir al catálogo o a la página de confirmación de reserva
        return "redirect:/catalogovehiculos";
    }

	/**
	 * Retorna la vista del dashboard para el usuario autenticado.
	 *
	 * @param model    el modelo utilizado para agregar atributos a la vista
	 * @param session  la sesión actual
	 * @return la vista del dashboard o la redirección correspondiente
	 */
	@GetMapping("/dashboard")
	public String getDashboard(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

	    if (usuario == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
	        return "redirect:/iniciarsesion";
	    }

	    String username = usuario.getNombre(); // Obtén el nombre de usuario desde el objeto Usuarios
	    model.addAttribute("username", username); // Agrega el nombre de usuario al modelo
	    return "dashboard";
	}

	/**
	 * Retorna la vista de alquileres con una lista de usuarios y vehículos disponibles.
	 *
	 * @param orden    el orden de los resultados (ascendente o descendente)
	 * @param model    el modelo utilizado para agregar atributos a la vista
	 * @param session  la sesión actual
	 * @return la vista de alquileres o la redirección correspondiente
	 */
	@GetMapping("/rents")
	public String getRents(@RequestParam(defaultValue = "asc") String orden, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }

	    List<Usuarios> listado = (List<Usuarios>) usuariosRepositorio.findAll();
	    List<Usuarios> clientes = listado.stream().filter(u -> u.getRole().getNombrerol().equals("CLIENTE"))
	            .collect(Collectors.toList());
	    List<Vehiculos> listado2 = (List<Vehiculos>) vehiculosRepositorio.findAll();
	    model.addAttribute("listadousuarios", clientes);
	    model.addAttribute("listadovehiculos", listado2);
		return "/rents";
	}
	
	/**
	 * Retorna la vista de usuarios con una lista de usuarios y roles disponibles.
	 *
	 * @param busqueda   el término de búsqueda opcional para filtrar usuarios por su rol
	 * @param usuForm    el formulario utilizado para el envío de datos
	 * @param modelo     el modelo utilizado para agregar atributos a la vista
	 * @param session    la sesión actual
	 * @return la vista de usuarios o la redirección correspondiente
	 */
	@GetMapping("/users")
	public String getListaUsuarios(@RequestParam(name = "busqueda", required = false) String busqueda, UsuariosForm usuForm, Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		List<Role> listado = (List<Role>) rolesRepositorio.findAll();
		modelo.addAttribute("listadoroles", listado);
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		if (busqueda != null && !busqueda.isEmpty()) {
			usuarios = usuariosRepositorio.findByRoleNombrerol(busqueda);
		} else {
			usuarios = (List<Usuarios>) usuariosRepositorio.findAll();
		}
		modelo.addAttribute("listado", usuarios);
		return "/users";
	}
	
	/**
	 * Obtiene la lista de vehículos según los parámetros de búsqueda y los agrega al modelo.
	 * Si no hay una sesión activa, redirige al inicio de sesión.
	 *
	 * @param busqueda Parámetro de búsqueda opcional para filtrar los vehículos por marca.
	 * @param modelo   Objeto Model para agregar los atributos necesarios al modelo.
	 * @param session  Objeto HttpSession para verificar la existencia de una sesión activa.
	 * @return La vista "vehicles" que muestra la lista de vehículos.
	 */
	@GetMapping("/vehicles")
	public String getListaVehiculos(@RequestParam(name = "busqueda", required = false) String busqueda, Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		List<Vehiculos> listado = (List<Vehiculos>) vehiculosRepositorio.findAll();
		List<Vehiculos> vehiculos = new ArrayList<Vehiculos>();
		modelo.addAttribute("listadomarcas", listado);
		if (busqueda != null && !busqueda.isEmpty()) {
			vehiculos = vehiculosRepositorio.findByMarca(busqueda);
		} else {
			vehiculos = (List<Vehiculos>) vehiculosRepositorio.findAll();
		}
		modelo.addAttribute("listado", vehiculos);
		return "vehicles";
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Carga los datos de un archivo JSON en la base de datos de usuarios.
	 * Si el archivo está vacío, redirige a la página de lista de usuarios con un mensaje de error.
	 *
	 * @param archivo Archivo MultipartFile que contiene los datos en formato JSON.
	 * @return Redirecciona a la página de lista de usuarios.
	 * @throws IOException Si ocurre un error al leer el archivo.
	 */
	@PostMapping("/cargarjson")
	public String cargarDatosJson(@RequestParam("archivo") MultipartFile archivo) throws IOException {
	    if (!archivo.isEmpty()) {
	        byte[] bytes = archivo.getBytes();
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(bytes);
	        JsonNode usuariosNode = rootNode.get("usuarios");
	        List<Usuarios> usuarios = objectMapper.readValue(usuariosNode.toString(), new TypeReference<List<Usuarios>>() {});
	        usuariosRepositorio.saveAll(usuarios);
	        return "redirect:/listausuarios";
	    } else {
	        return "redirect:/listausuarios?error=Archivo vacío";
	    }
	}

	/**
	 * Carga los datos de un archivo JSON en la base de datos de vehículos.
	 * Si el archivo está vacío, redirige al panel de control con un mensaje de error.
	 *
	 * @param archivo Archivo MultipartFile que contiene los datos en formato JSON.
	 * @return Redirecciona al panel de control.
	 * @throws IOException Si ocurre un error al leer el archivo.
	 */
	@PostMapping("/cargarjsonveh")
	public String cargarDatosJsonVeh(@RequestParam("archivo") MultipartFile archivo) throws IOException {
	    if (!archivo.isEmpty()) {
	        byte[] bytes = archivo.getBytes();
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(bytes);
	        JsonNode vehiculosNode = rootNode.get("vehiculos");
	        List<Vehiculos> vehiculos = objectMapper.readValue(vehiculosNode.toString(), new TypeReference<List<Vehiculos>>() {});
	                // Agrega el vehículo como un nuevo registro en la base de datos
	        vehiculosRepositorio.saveAll(vehiculos);
	        return "redirect:/dashboard";
	    }else {
	        return "redirect:/vehicles?error=Archivo vacío";
	    }
	}
	
	/**
	 * Carga los datos de un archivo JSON en la base de datos de alquileres.
	 * Si el archivo está vacío, redirige a la página de lista de alquileres con un mensaje de error.
	 *
	 * @param archivo Archivo MultipartFile que contiene los datos en formato JSON.
	 * @return Redirecciona a la página de lista de alquileres.
	 * @throws IOException Si ocurre un error al leer el archivo.
	 */
	@PostMapping("/cargarjsonalq")
	public String cargarDatosJsonAlq(@RequestParam("archivo") MultipartFile archivo) throws IOException {
	    if (!archivo.isEmpty()) {
	        byte[] bytes = archivo.getBytes();
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(bytes);
	        JsonNode vehiculosNode = rootNode.get("alquiler");
	        List<Alquiler> alq = objectMapper.readValue(vehiculosNode.toString(), new TypeReference<List<Alquiler>>() {});
	        alquilerRepositorio.saveAll(alq);
	        return "redirect:/listarent";
	    } else {
	        return "redirect:/listarent?error=Archivo vacío";
	    }
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Obtiene la lista de alquileres ordenados según el parámetro de orden y los agrega al modelo.
	 * Si no hay una sesión activa, redirige al inicio de sesión.
	 *
	 * @param orden  Parámetro de orden para especificar la dirección del ordenamiento ("asc" o "desc").
	 * @param model  Objeto Model para agregar los atributos necesarios al modelo.
	 * @param session  Objeto HttpSession para verificar la existencia de una sesión activa.
	 * @return La vista "listarent" que muestra la lista de alquileres.
	 */
	@GetMapping("/listarent")
	public String obtenerAlquileres(@RequestParam(defaultValue = "asc") String orden, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		// Obtiene el usuario de la sesión
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		// Agrega el rol del usuario al modelo
		model.addAttribute("rolUsuario", usuario.getRole().getNombrerol());
		List<Alquiler> alquileres;
		if (orden.equals("desc")) {
			alquileres = alquilerServicio.obtenerAlquileresOrdenadosPorFechaDescendente();
		} else {
			alquileres = alquilerServicio.obtenerAlquileresOrdenadosPorFechaAscendente();
		}
		model.addAttribute("listado", alquileres);
		model.addAttribute("orden", orden);
		return "listarent";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Obtiene la lista de clientes según el parámetro de búsqueda y los agrega al modelo.
	 * Si no hay una sesión activa, redirige al inicio de sesión.
	 *
	 * @param busqueda Parámetro de búsqueda opcional para filtrar los clientes por DNI.
	 * @param modelo   Objeto Model para agregar los atributos necesarios al modelo.
	 * @param session  Objeto HttpSession para verificar la existencia de una sesión activa.
	 * @return La vista "listaclientes" que muestra la lista de clientes.
	 */
	@GetMapping(path = "/listaclientes")
	public String getListaClientes(@RequestParam(name = "busqueda", required = false) String busqueda, Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		if (busqueda != null && !busqueda.isEmpty()) {
			usuarios = usuariosRepositorio.findByDni(busqueda);
		} else {
			usuarios = (List<Usuarios>) usuariosRepositorio.findAll();
		}
		modelo.addAttribute("listado", usuarios);
		return "listaclientes";
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Verifica la información del formulario de usuarios, guarda un nuevo usuario en la base de datos
	 * y redirige a la página de lista de usuarios.
	 * Si no hay una sesión activa, redirige al inicio de sesión.
	 *
	 * @param usuForm        Objeto UsuariosForm que contiene los datos del formulario.
	 * @param bindingResult  Objeto BindingResult para validar los campos del formulario.
	 * @param modelo         Objeto Model para agregar los atributos necesarios al modelo.
	 * @param session        Objeto HttpSession para verificar la existencia de una sesión activa.
	 * @return Redirecciona a la página de lista de usuarios.
	 */
	@PostMapping(path = "/insertarusuarios")
	public String checkusuariosInfo(@Valid UsuariosForm usuForm, BindingResult bindingResult, Model modelo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            // No hay una sesión activa, redirigir al inicio de sesión
            return "redirect:/iniciarsesion";
        }
		List<Role> listado = (List<Role>) rolesRepositorio.findAll();
		if (bindingResult.hasErrors()) {
			modelo.addAttribute("listadoroles", listado);
			return "/insertarusuarios";
		}
		modelo.addAttribute("listadoroles", listado);
		Usuarios usuario = new Usuarios(usuForm.getNombre(), usuForm.getApellidos(), usuForm.getDni(), usuForm.getDireccion(),
				usuForm.getTelefono(), usuForm.getEmail(), usuForm.getPassword(), usuForm.getRole());
		usuariosRepositorio.save(usuario);
		modelo.addAttribute("mensaje", "Usuario " + usuForm.getNombre() + " " + usuForm.getApellidos()
				+ " ha sido dado de alta satisfactoriamente.");
		return "redirect:/users";
	}
	
	/**
	 * Verifica la información del formulario de vehículos, guarda un nuevo vehículo en la base de datos
	 * y redirige a la página de lista de vehículos.
	 * Si no hay una sesión activa, redirige al inicio de sesión.
	 *
	 * @param vehForm        Objeto VehiculosForm que contiene los datos del formulario.
	 * @param bindingResult  Objeto BindingResult para validar los campos del formulario.
	 * @param modelo         Objeto Model para agregar los atributos necesarios al modelo.
	 * @param session        Objeto HttpSession para verificar la existencia de una sesión activa.
	 * @return Redirecciona a la página de lista de vehículos.
	 */
	@PostMapping(path = "/insertarvehiculos")
	public String checkvehiculosInfo(@Valid VehiculosForm vehForm, BindingResult bindingResult, Model modelo) {
	    if (bindingResult.hasErrors()) {
	        return "vehicles";
	    }
	    Vehiculos vehiculo = new Vehiculos(vehForm.getMarca(), vehForm.getModelo(), vehForm.getTipo(), vehForm.getMatricula(),
	            vehForm.getKilometraje(), vehForm.getPreciopordia(), vehForm.getAnio(), vehForm.getEnlaceImagen());
	    vehiculosRepositorio.save(vehiculo);
	    modelo.addAttribute("mensaje", "Vehículo " + vehForm.getMarca() + " " + vehForm.getModelo()
	            + " ha sido dado de alta satisfactoriamente.");
	    return "vehicles";
	}

	/**

	Elimina un vehículo de la base de datos según su ID y redirige a la página de lista de vehículos.
	@param id ID del vehículo a eliminar.
	@return Redirecciona a la página de lista de vehículos.
	*/
	@GetMapping("/eliminarvehiculo")
	public String eliminarVehiculo(@RequestParam Integer id) {
		// Eliminar usuario por ID
		vehiculosRepositorio.deleteById(id);
		return "redirect:/vehicles";
	}

	/**
	 * Exporta todos los usuarios de la base de datos en formato JSON.
	 *
	 * @return Lista de usuarios en formato JSON.
	 */
	@GetMapping("/usuarios.json")
	@ResponseBody
	public List<Usuarios> exportAllUsuarios() {
	    return (List<Usuarios>) usuariosRepositorio.findAll();
	}
	
	/**
	 * Exporta todos los vehículos de la base de datos en formato JSON.
	 *
	 * @return Lista de vehículos en formato JSON.
	 */
	@GetMapping("/vehiculos.json")
	@ResponseBody
	public List<Vehiculos> exportAllVehiculos() {
	    return (List<Vehiculos>) vehiculosRepositorio.findAll();
	}
	/**
	 * Exporta todos los registros de alquiler de la base de datos en formato JSON.
	 *
	 * @return Lista de registros de alquiler en formato JSON.
	 */
	@GetMapping("/alquiler.json")
	@ResponseBody
	public List<Alquiler> exportAllAlq() {
	    return (List<Alquiler>) alquilerRepositorio.findAll();
	}
}
