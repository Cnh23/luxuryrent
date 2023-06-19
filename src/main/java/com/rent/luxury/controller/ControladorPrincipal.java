package com.rent.luxury.controller;

import java.io.IOException;
import java.security.Principal;
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
	
	@GetMapping("/iniciarsesion")
	public String getInicioSesion(UsuariosForm usuForm) {
		return "/iniciarsesion";
	}
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
	
	@GetMapping("/cerrarsesion")
	public String cerrarSesion(HttpSession session) {
	session.invalidate();
	return "redirect:/inicio";
	}
	
	@PostMapping("/darseDeBaja")
	public String darseDeBaja(HttpSession session, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
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
	
	@GetMapping("/modificarusuarioperfil")
	public String getmodificardatosperfil(Model modelo, HttpSession session, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
	        return "redirect:/iniciarsesion";
	    }
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");
	    
	    modelo.addAttribute("usuario", usuario);

	    return "profile";
	}

	@PostMapping("/modificarusuarioperfil")
	public String modificarUsuarioPerfil(@ModelAttribute("usuario") Usuarios usuario, BindingResult result, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
	        return "redirect:/iniciarsesion";
	    }
		
		if (result.hasErrors()) {
	        return "profile";
	    }
	    usuarioService.actualizarUsuario(usuario);
	    return "redirect:/profile";
	}
	
	@PostMapping("/cambiarcontrasena")
	public String cambiarContrasena(@RequestParam("currentPassword") String currentPassword,
	                                @RequestParam("newPassword") String newPassword,
	                                @RequestParam("confirmPassword") String confirmPassword,
	                                HttpSession session,
	                                Model model, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
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
	    return "redirect:/profile"; // Cambia "profile" por la vista correspondiente
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping("/about")
	public String getInfo(UsuariosForm usuForm) {
		return "/about";
	}
	
	@GetMapping("/services")
	public String getSericios(UsuariosForm usuForm) {
		return "/services";
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/profile")
	public String getperfil(Model modelo, HttpSession session, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
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
	    return "/profile";
	}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/register")
	public String getRegistro(UsuariosForm usuForm) {
		return "/register";
	}
	
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
	
	@PostMapping("/reserva")
    public String postReserva(@RequestParam("vehiculoId") Integer vehiculoId,
                              @RequestParam("fechaRecogida") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaRecogida,
                              @RequestParam("fechaEntrega") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEntrega,
                              HttpSession session, Principal principal) {
	    if (principal == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
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


	@GetMapping("/dashboard")
	public String getDashboard(Model model, HttpSession session) {
	    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

	    if (usuario == null) {
	        // El usuario no está autenticado, redirige a la página de inicio de sesión
	        return "redirect:/iniciarsesion";
	    }

	    String username = usuario.getNombre(); // Obtén el nombre de usuario desde el objeto Usuarios
	    model.addAttribute("username", username); // Agrega el nombre de usuario al modelo
	    return "dashboard";
	}

	
	@GetMapping("/rents")
	public String getRents(@RequestParam(defaultValue = "asc") String orden, Model model, HttpSession session) {
		List<Alquiler> alquileres;
		if (orden.equals("desc")) {
			alquileres = alquilerServicio.obtenerAlquileresOrdenadosPorFechaDescendente();
		} else {
			alquileres = alquilerServicio.obtenerAlquileresOrdenadosPorFechaAscendente();
		}
		model.addAttribute("listado", alquileres);
		model.addAttribute("orden", orden);
		return "/rents";
	}
	
	@GetMapping("/users")
	public String getUsers(UsuariosForm usuForm) {
		return "/users";
	}
	
	@GetMapping("/vehicles")
	public String getVehicles(UsuariosForm usuForm) {
		return "vehicles";
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@GetMapping(path = "/listausuarios")
	public String getListaUsuarios(@RequestParam(name = "busqueda", required = false) String busqueda, Model modelo) {
		List<Role> listado = (List<Role>) rolesRepositorio.findAll();
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		modelo.addAttribute("listadoroles", listado);
		if (busqueda != null && !busqueda.isEmpty()) {
			usuarios = usuariosRepositorio.findByRoleNombrerol(busqueda);
		} else {
			usuarios = (List<Usuarios>) usuariosRepositorio.findAll();
		}
		modelo.addAttribute("listado", usuarios);
		return "listausuarios";
	}
	
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
	        return "redirect:/listavehiculos";
	    }else {
	        return "redirect:/listavehiculos?error=Archivo vacío";
	    }
	}
	
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
	@GetMapping(path = "/listavehiculos")
	public String getListaVehiculos(@RequestParam(name = "busqueda", required = false) String busqueda,
			HttpSession session, Model modelo) {
		// Obtiene el usuario de la sesión
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		// Agrega el rol del usuario al modelo
		modelo.addAttribute("rolUsuario", usuario.getRole().getNombrerol());
		List<Vehiculos> listado = (List<Vehiculos>) vehiculosRepositorio.findAll();
		List<Vehiculos> vehiculos = new ArrayList<Vehiculos>();
		modelo.addAttribute("listadomarcas", listado);
		if (busqueda != null && !busqueda.isEmpty()) {
			vehiculos = vehiculosRepositorio.findByMarca(busqueda);
		} else {
			vehiculos = (List<Vehiculos>) vehiculosRepositorio.findAll();
		}
		modelo.addAttribute("listado", vehiculos);
		return "listavehiculos";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@GetMapping("/listarent")
	public String obtenerAlquileres(@RequestParam(defaultValue = "asc") String orden, Model model, HttpSession session) {
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
	@GetMapping(path = "/listaclientes")
	public String getListaClientes(@RequestParam(name = "busqueda", required = false) String busqueda, Model modelo) {
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
	@GetMapping("/insertarusuarios")
	public String getInsertarusuarios(UsuariosForm usuForm, Model modelo) {
		List<Role> listado = (List<Role>) rolesRepositorio.findAll();
		modelo.addAttribute("listadoroles", listado);
		return "/insertarusuarios";
	}

	@PostMapping(path = "/insertarusuarios")
	public String checkusuariosInfo(@Valid UsuariosForm usuForm, BindingResult bindingResult, Model modelo) {
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
		return "/insertarusuarios";
	}

	@GetMapping("/insertarvehiculos")
	public String getInsertarvehiculos(VehiculosForm vehForm, Model modelo) {
		return "/insertarvehiculos";
	}

	@PostMapping(path = "/insertarvehiculos")
	public String checkvehiculosInfo(@Valid VehiculosForm vehForm, BindingResult bindingResult, Model modelo) {
		if (bindingResult.hasErrors()) {
			return "/insertarvehiculos";
		}
		Vehiculos vehiculo = new Vehiculos(vehForm.getMarca(), vehForm.getModelo(), vehForm.getTipo(), vehForm.getMatricula(),
				vehForm.getKilometraje(), vehForm.getPreciopordia(), vehForm.getAnio(), vehForm.getEnlaceImagen());
		vehiculosRepositorio.save(vehiculo);
		modelo.addAttribute("mensaje", "Vehículo " + vehForm.getMarca() + " " + vehForm.getModelo()
				+ " ha sido dado de alta satisfactoriamente.");
		return "/insertarvehiculos";
	}

	@GetMapping("/insertaralquiler")
	public String getInsertaralquiler(AlquilerForm alqForm, Model modelo) {
		List<Usuarios> listado = (List<Usuarios>) usuariosRepositorio.findAll();
		List<Usuarios> clientes = listado.stream().filter(u -> u.getRole().getNombrerol().equals("CLIENTE"))
				.collect(Collectors.toList());
		List<Vehiculos> listado2 = (List<Vehiculos>) vehiculosRepositorio.findAll();
		modelo.addAttribute("listadousuarios", clientes);
		modelo.addAttribute("listadovehiculos", listado2);
		return "/insertaralquiler";
	}

	@PostMapping(path = "/insertaralquiler")
	public String checkalquilerInfo(@Valid AlquilerForm alqForm, BindingResult bindingResult, Model modelo) {
		List<Usuarios> listado = (List<Usuarios>) usuariosRepositorio.findAll();
		List<Usuarios> clientes = listado.stream().filter(u -> u.getRole().getNombrerol().equals("CLIENTE"))
				.collect(Collectors.toList());
		List<Vehiculos> listado2 = (List<Vehiculos>) vehiculosRepositorio.findAll();
		if (bindingResult.hasErrors()) {
			modelo.addAttribute("listadousuarios", clientes);
			modelo.addAttribute("listadovehiculos", listado2);
			return "/insertaralquiler";
		}
		modelo.addAttribute("listadousuarios", clientes);
		modelo.addAttribute("listadovehiculos", listado2);
		int diasAlquiler = (int) ChronoUnit.DAYS.between(alqForm.getFechaRecogida().toInstant(),
				alqForm.getFechaEntrega().toInstant());
		if (diasAlquiler < 3) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(alqForm.getFechaRecogida());
			calendar.add(Calendar.DAY_OF_YEAR, 3);
			alqForm.setFechaEntrega(calendar.getTime());
		}
		// Validar si el vehículo ya está alquilado en el rango de fechas especificado
		List<Alquiler> alquileresVehiculo = alquilerRepositorio.findByVehiculoIdAndFechas(alqForm.getVehiculo().getId(),
				alqForm.getFechaRecogida(), alqForm.getFechaEntrega());
		if (!alquileresVehiculo.isEmpty()) {
			modelo.addAttribute("errorVehiculo", "El vehículo ya está alquilado en el rango de fechas especificado.");
			return "insertaralquiler";
		}

		// Validar si el cliente ya tiene un vehículo alquilado en el rango de fechas
		// especificado
		List<Alquiler> alquileresCliente = alquilerRepositorio.findByUsuariosIdAndFechas(alqForm.getUsuarios().getId(),
				alqForm.getFechaRecogida(), alqForm.getFechaEntrega());
		if (!alquileresCliente.isEmpty()) {
			modelo.addAttribute("errorCliente",
					"El cliente ya tiene un vehículo alquilado en el rango de fechas especificado.");
			return "insertaralquiler";
		}
		Alquiler alquiler = new Alquiler(alqForm.getFechaRecogida(), alqForm.getFechaEntrega(), alqForm.getUsuarios(), alqForm.getEstado(),
				alqForm.getVehiculo());
		alquilerRepositorio.save(alquiler);
		modelo.addAttribute("mensaje",
				"El alquiler con fecha " + alqForm.getFechaRecogida() + " ha sido dado de alta satisfactoriamente.");
		return "/insertaralquiler";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/modificarusuarios")
	public String modificarUsuario(@RequestParam("id") Integer id, Model modelo) {
		Usuarios usuario = usuariosRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		modelo.addAttribute("usuarios", usuario);
		List<Role> listado = (List<Role>) rolesRepositorio.findAll();
		modelo.addAttribute("listadoroles", listado);
		return "modificarusuarios";
	}

	@PostMapping("/modificarusuarios")
	public String guardarModificacionUsuario(@ModelAttribute("usuarios") Usuarios usuario,
			@RequestParam("id") Integer id, BindingResult result) {
		if (result.hasErrors()) {
			return "modificarusuarios";
		}
		usuario.setId(id);
		usuarioService.actualizarUsuario(usuario);
		return "redirect:/listausuarios";
	}

	@GetMapping("/eliminarusuarios")
	public String eliminarUsuario(@RequestParam Integer id) {
		// Eliminar usuario por ID
		usuariosRepositorio.deleteById(id);
		return "redirect:/listausuarios";
	}

	@GetMapping("/modificarvehiculos")
	public String modificarVehiculo(@RequestParam("id") Integer id, Model modelo) {
		Vehiculos vehiculo = vehiculosRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
		modelo.addAttribute("vehiculos", vehiculo);
		return "modificarvehiculos";
	}

	@PostMapping("/modificarvehiculos")
	public String guardarModificacionVehiculo(@ModelAttribute("vehiculos") Vehiculos vehiculo,
			@RequestParam("id") Integer id, BindingResult result) {
		if (result.hasErrors()) {
			return "modificarvehiculos";
		}
		vehiculo.setId(id);
		vehiculoService.actualizarVehiculo(vehiculo);
		return "redirect:/listavehiculos";
	}

	@GetMapping("/eliminarvehiculo")
	public String eliminarVehiculo(@RequestParam Integer id) {
		// Eliminar usuario por ID
		vehiculosRepositorio.deleteById(id);
		return "redirect:/listavehiculos";
	}

	@GetMapping("/modificaralquiler")
	public String modificarAlquiler(@RequestParam("id") Integer id, Model modelo) {
		List<Usuarios> listado = (List<Usuarios>) usuariosRepositorio.findAll();
		List<Usuarios> clientes = listado.stream().filter(u -> u.getRole().getNombrerol().equals("CLIENTE"))
				.collect(Collectors.toList());
		List<Vehiculos> listado2 = (List<Vehiculos>) vehiculosRepositorio.findAll();
		modelo.addAttribute("listadousuarios", clientes);
		modelo.addAttribute("listadovehiculos", listado2);
		Alquiler alquiler = alquilerRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado"));
		modelo.addAttribute("alquiler", alquiler);
		return "modificaralquiler";
	}

	@PostMapping("/modificaralquiler")
	public String guardarModificacionAlquiler(@ModelAttribute("alquiler") Alquiler alquiler,
			@RequestParam("id") Integer id, BindingResult result, Model modelo) {
		List<Usuarios> listado = (List<Usuarios>) usuariosRepositorio.findAll();
		List<Usuarios> clientes = listado.stream().filter(u -> u.getRole().getNombrerol().equals("CLIENTE"))
				.collect(Collectors.toList());
		List<Vehiculos> listado2 = (List<Vehiculos>) vehiculosRepositorio.findAll();
		if (result.hasErrors()) {
			modelo.addAttribute("listadousuarios", clientes);
			modelo.addAttribute("listadovehiculos", listado2);
			return "/modificaralquiler";
		}
		modelo.addAttribute("listadousuarios", clientes);
		modelo.addAttribute("listadovehiculos", listado2);
		int diasAlquiler = (int) ChronoUnit.DAYS.between(alquiler.getFechaRecogida().toInstant(),
				alquiler.getFechaEntrega().toInstant());
		if (diasAlquiler < 3) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(alquiler.getFechaRecogida());
			calendar.add(Calendar.DAY_OF_YEAR, 3);
			alquiler.setFechaEntrega(calendar.getTime());
		}
		// Validar si el vehículo ya está alquilado en el rango de fechas especificado
		List<Alquiler> alquileresVehiculo = alquilerRepositorio.findByVehiculoIdAndFechas(
				alquiler.getVehiculo().getId(), alquiler.getFechaRecogida(), alquiler.getFechaEntrega());
		if (!alquileresVehiculo.isEmpty()) {
			modelo.addAttribute("error", "El vehículo ya está alquilado en el rango de fechas especificado.");
			return "formulario-alquiler";
		}

		// Validar si el cliente ya tiene un vehículo alquilado en el rango de fechas
		// especificado
		List<Alquiler> alquileresCliente = alquilerRepositorio.findByUsuariosIdAndFechas(alquiler.getUsuarios().getId(),
				alquiler.getFechaRecogida(), alquiler.getFechaEntrega());
		if (!alquileresCliente.isEmpty()) {
			modelo.addAttribute("error",
					"El cliente ya tiene un vehículo alquilado en el rango de fechas especificado.");
			return "formulario-alquiler";
		}
		alquiler.setId(id);
		alquilerServicio.actualizarAlquiler(alquiler);
		modelo.addAttribute("mensaje",
				"El alquiler con fecha " + alquiler.getFechaRecogida() + " ha sido modificado satisfactoriamente.");
		return "redirect:/listarent";
	}

	@GetMapping("/eliminaralquiler")
	public String eliminarAlquiler(@RequestParam Integer id) {
		// Eliminar usuario por ID
		alquilerRepositorio.deleteById(id);
		return "redirect:/rents";
	}
	
	@GetMapping("/usuarios.json")
	@ResponseBody
	public List<Usuarios> exportAllUsuarios() {
	    return (List<Usuarios>) usuariosRepositorio.findAll();
	}
	
	@GetMapping("/vehiculos.json")
	@ResponseBody
	public List<Vehiculos> exportAllVehiculos() {
	    return (List<Vehiculos>) vehiculosRepositorio.findAll();
	}
	
	@GetMapping("/alquiler.json")
	@ResponseBody
	public List<Alquiler> exportAllAlq() {
	    return (List<Alquiler>) alquilerRepositorio.findAll();
	}
}
