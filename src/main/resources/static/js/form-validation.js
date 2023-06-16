// Example starter JavaScript for disabling form submissions if there are invalid fields

// Esta función se ejecuta de inmediato y se utiliza para evitar conflictos de nombres y mantener el ámbito local.
(function () {
  'use strict'

  // Selecciona todos los formularios que tienen la clase "needs-validation" y los guarda en la variable "forms".
  var forms = document.querySelectorAll('.needs-validation')

  // Recorre todos los formularios encontrados usando el método "forEach".
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      // Agrega un event listener para el evento "submit" en cada formulario.
      form.addEventListener('submit', function (event) {
        // Verifica si el formulario no es válido utilizando el método "checkValidity()" del formulario.
        if (!form.checkValidity()) {
          // Si el formulario no es válido, se detiene el evento de envío.
          event.preventDefault()
          // También se detiene la propagación del evento para evitar que se propague a elementos superiores.
          event.stopPropagation()
        }

        // Añade la clase "was-validated" al formulario.
        // Esto activa los estilos de validación personalizados de Bootstrap.
        form.classList.add('was-validated')
      }, false)
    })
})()
