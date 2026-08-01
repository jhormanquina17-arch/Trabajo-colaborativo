Trabajo en Equipo - Consumo de API con Token en Android

Integrantes del Proyecto

Creador del repositorio: Andrés Quina

Colaboradores: Nicole García y Alex Rosero

Organización del Trabajo

Opción elegida: Opción A — Turnos con rotación.

Justificación: Nos turnamos quién escribía el código por cada paso de la guía. Empezó escribiendo el integrante con menos experiencia guiado por el otro mediante pantalla compartida. Cada vez que cambiábamos de turno, hacíamos commit y push, y el siguiente hacía pull antes de tocar el código para mantenernos sincronizados.

Avance Conjunto

Guía base: Avanzamos paso a paso siguiendo la estructura de la guía base, implementando Retrofit, el almacenamiento del token y las peticiones protegidas de manera colaborativa.

Extensión: Coordinamos los ajustes requeridos para completar los requerimientos adicionales de la extensión de forma fluida.

Dificultades de Sincronización y Resolución

Dificultad encontrada: Tuvimos una incompatibilidad menor con la versión del plugin de Gradle de Android (AGP) al clonar el repositorio en diferentes entornos de desarrollo, además de asegurar que los pull y push se hicieran en el orden correcto para evitar sobreescribir los cambios del compañero.

Cómo lo resolvimos: Estándarizamos temporalmente la configuración de compilación y AGP en las máquinas de ambos y seguimos estrictamente la dinámica de turnos (hacer commit, push y pull) antes de continuar con la escritura de código.

Resolución del Reto Final
Para resolver el reto final de mostrar un mensaje visible en pantalla cuando el login falla, modificamos la lógica del flujo de autenticación interceptando el resultado negativo (por ejemplo, en el bloque de error de la respuesta o del ViewModel). Cuando las credenciales son incorrectas, disparamos un Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show() para notificar al usuario de manera clara sin alterar el funcionamiento exitoso de la app. Decidimos hacerlo así para mantener separadas las responsabilidades de manejo de errores de interfaz y el flujo normal de navegación con el token.
