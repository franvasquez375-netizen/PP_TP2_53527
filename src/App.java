import exepciones.CupoExcedidoException;
import hilos.EnvioTicketsThread;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean continuar = true;
        int id = 1;

        //Creo la lista de estudiantes
        List<Estudiante> listaEstudiantes = new ArrayList<>();

        System.out.println("REGISTRO DE ESTUDIANTES: ");
        System.out.println("======================");

        while (continuar) {
            System.out.println("Ingese legajo del estudiante: ");
            String legajo = sc.nextLine();
            System.out.println("Ingese nombre y apellido del estudiante: ");
            String apenomb = sc.nextLine();
            listaEstudiantes.add(new Estudiante(legajo, apenomb));
            System.out.println("desea crear otro estudiante  S/N?");
            String respuesta = sc.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) ? true : false;
        }


        System.out.println("\n\nREGISTRO DE EVENTOS: ");
        System.out.println("===============================");
        System.out.println("Ingrese la cantidad de eventos:");
        int cantEventos = sc.nextInt();
        sc.nextLine();

        for (int eventosCreados = 0; eventosCreados < cantEventos; eventosCreados++) {

            //Leer valores del evento
            System.out.println("Ingrese el título del evento " + id);
            String tituloEvento = sc.nextLine();
            System.out.println("Ingrese el costo base del evento");
            double costoBase = sc.nextDouble();
            sc.nextLine();
            System.out.println("El evento tendra costo para los participantes S/N?");
            String respuesta = sc.nextLine().trim().toLowerCase();
            boolean esGratuito = true;
            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                esGratuito = false;
            }
            //Crear evento, le paso el valor leído de los atributos
            EventoUniversitario evento = new EventoUniversitario("EVT-" + id, tituloEvento, costoBase, esGratuito);

            //Leer valores de la sala
            System.out.println("Ingrese el nombre de la sala");
            String nombreSala = sc.nextLine();
            //Crear sala con los valores leídos
            Sala sala = new Sala(id, nombreSala);
            //Asigno sala creada al evento
            evento.asignarSala(sala);

            //Crear evento de copia
            EventoUniversitario eventoCopia = new EventoUniversitario(evento);

            //Pedir cantidad de actividades para un evento
            System.out.println("\n\nREGISTRO DE ACTIVIDADES PARA EL EVENTO " + evento.getTitulo());
            System.out.println("================================================================");
            System.out.println("Ingrse la cantidad de actividades para el evento:");
            int cantidadActividades = sc.nextInt(); //leo la cantidad de actividades que va a relizarse para el evento.
            sc.nextLine();
            int idActividad = 1;
            for (int j = 0; j < cantidadActividades; j++) {
                System.out.println("Ingrese si la actividad es una charla, un taller o un curso:");
                String tipoActividad = sc.nextLine().trim().toLowerCase();
                System.out.println("Ingrese el título de la actividad:" + idActividad);
                String tituloActividad = sc.nextLine();
                int cupoMinimoRequerido = 5;
                System.out.println("Ingrese el cupo máximo de la actividad (mayor o igual al cupo mínimo):");
                int cupoMaximo = sc.nextInt();
                //Uso un while para asegurarme de que el cupo máximo sea mayor que el mínimo
                while (cupoMaximo < cupoMinimoRequerido) {
                    System.out.println("CUPO MÁXIMO MENOR QUE EL CUPO MÍNIMO, ingrese un cupo máximo mayor o igual al cupo mínimo:");
                    cupoMaximo = sc.nextInt();
                }
                sc.nextLine();
                //Construyo la actividad con los datos leídos
                evento.crearActividad(idActividad, tituloActividad, cupoMaximo, tipoActividad);
                ++idActividad;
            }

            //MOSTRAR LISTA DE ACTIVIDADES CREADAS
            List<Actividad> actividades = evento.getActividades();
            System.out.println("\nLista de actividades del evento:");
            for (Actividad act : actividades) {
                System.out.println("Id: " + act.getId());
                System.out.println("Nombre: " + act.getTitulo());
                System.out.println("Cupo máximo: " + act.getCupoMaximo());
                System.out.println("------------------------------------------------------");
            }

            //INSCRIPCIÓN DE ESTUDIANTES EN ACTIVIDADES
            System.out.println("\nINSCRIPCIÓN DE ESTUDIANTES");
            System.out.println("=========================================================");
            System.out.println("¿Cuántas inscripciones desea realizar para este evento?");
            int cantInscripciones = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < cantInscripciones; i++) {
                System.out.println("Ingrese legajo del estudiante a inscribir:");
                String legajo = sc.nextLine();

                System.out.println("Ingrese ID de la actividad a la que se va a inscribir:");
                int idBuscado = sc.nextInt();
                sc.nextLine();

                // Busco al estudiante en la lista y lo inscribo
                for (Estudiante estudiante : listaEstudiantes) {
                    if (estudiante.getLegajo().equals(legajo)) {
                        //Uso un try-catch para el manejo de la excepción
                        try {
                            // Uso idBuscado - 1 para obtener el índice correcto del ArrayList
                            evento.getActividades().get(idBuscado - 1).inscribir(estudiante);
                            System.out.println("Estudiante inscripto con éxito.");
                            System.out.println("Desea confirmar la inscripción S/N?");
                            String confimacion = sc.nextLine().trim().toLowerCase();
                            if (confimacion.equals("s") || confimacion.equals("si") || confimacion.equals("sí")) {
                                int ultimaPos = evento.getActividades().get(idBuscado - 1).getInscripciones().size() - 1;
                                Inscripcion inscripcionActual = evento.getActividades().get(idBuscado - 1).getInscripciones().get(ultimaPos);
                                inscripcionActual.confirmar();
                            }

                        } catch (CupoExcedidoException e) {
                            System.out.println("Error al inscribir. " + e.getMessage());
                        }
                    }
                }
            }

            //Emisión de certificados para los estudiantes correspondientes
            for(Actividad actividad : evento.getActividades()){
                if(actividad instanceof Certificable certificable){
                    System.out.println("CERTIFICADOS EMITIDOS PARA LA ACTIVIDAD: "+ actividad.getTitulo());
                    for(Inscripcion inscripcion : actividad.getInscripciones()){
                        String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                        System.out.println(certificado);
                    }
                }
            }

            //Bloque para intentar persistir a evento
            try{
                evento.persistirEvento();
                EventoUniversitario copiaDesdeArchivo = evento.recuperarEvento(evento.getId());

                Thread envioTicketsThread = new hilos.EnvioTicketsThread(evento);
                envioTicketsThread.start();

                //Mostrar datos
                System.out.println("\n\nDatos del evento: ");
                evento.mostrarDatos();


                List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
                List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
                List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);

                System.out.println("Talleres encontrados: " + talleres.size());
                System.out.println("Charlas encontradas: " + charlas.size());
                System.out.println("Cursos encontrados: " + cursos.size());

                System.out.println("Costo de materiales de talleres: $ " + evento.calcularCostoMateriales(talleres));
                System.out.println("Costo de materiales de cursos: $ "+ evento.calcularCostoMateriales(cursos));
                System.out.println("Costo de materiales de todas las actividades: $"+ evento.calcularCostoMateriales(evento.getActividades()));
                System.out.println("---------------------------------");
                //Mostrar datos del evento reconstruido
                System.out.println("\n\nDatos del evento reconstruido: ");
                copiaDesdeArchivo.mostrarDatos();
            } catch (FileNotFoundException e){
                System.out.println("Error: no se encontró el archivo: "+e.getMessage());
            } catch (ClassNotFoundException e){
                System.out.println("Error: no fue posible reconstruir el objeto almacenado: "+e.getMessage());
            } catch (IOException e){
                System.out.println("Error: Se produjo un error de E/S: "+ e.getMessage());
            }


            id++;

            //Mostrar número de eventos creados
            System.out.println("\n\nNúmero de eventos creados (originales + copia): " + EventoUniversitario.getCantidadEventos());


        }
    }
}