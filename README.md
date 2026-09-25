PP_TP2_53527

Trabajo Práctico N° 2 de Paradigmas de Programación. Desarrollo de un sistema de gestión para eventos universitarios implementado en Java (JDK 17) con IntelliJ IDEA.

Resumen de Ejercicios Implementados

* **Ejercicio 1 - Modelado y Clases Abstractas:** Estructura principal del sistema con la clase `EventoUniversitario`, `Sala`, `Estudiante` e `Inscripcion`. Implementación de la jerarquía de `Actividad` (clase abstracta) con sus subclases específicas: `Charla`, `Curso` y `Taller`.
* **Ejercicio 2 - Excepciones Personalizadas e Interfaces:** Creación de la excepción `CupoExcedidoException` para validar la capacidad máxima de las actividades al inscribir alumnos. Definición de la interfaz `Certificable` e implementación en `Curso` y `Taller` para la emisión de certificados.
* **Ejercicio 3 - Persistencia de Datos:** Serialización de objetos en archivos binarios (`.dat`). El sistema guarda el estado completo del evento mediante el método `persistirEvento()` y permite recuperarlo mediante `recuperarEvento()`.
* **Ejercicio 4 - Concurrencia y Hilos:** Implementación del envío asíncrono de entradas mediante la clase `EnvioTicketsThread` (que extiende de `Thread`). Procesa los tickets de los estudiantes confirmados en un hilo secundario sin congelar el flujo ni la interfaz del programa principal.

<img width="1152" height="887" alt="image" src="https://github.com/user-attachments/assets/0c8f7beb-08ad-46f8-a28d-2a7bad98a1ea" />

<img width="1187" height="832" alt="image" src="https://github.com/user-attachments/assets/288a34ba-34c3-4dc5-bdbd-b78ac0f45a7b" />

