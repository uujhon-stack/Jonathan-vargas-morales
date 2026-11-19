
import java.util.Scanner;

public class TrainTicketSystem {

    // Scanner para leer los datos ingresados por el cajero
    static Scanner sc = new Scanner(System.in);

    // Credenciales de acceso
    static final String USUARIO = "jonathan";
    static final String CONTRASENA = "2210";

    // Arreglo para almacenar los trenes
    // Columnas: ID, modelo, capacidad, horario, estado
    static String[][] trenes = new String[100][5];
    static int totalTrenes = 3;
    static int siguienteIdTren = 4;
    // Matriz para almacenar los pasajeros
// Columnas: ID, nombre, apellido, origen, destino
    static String[][] pasajeros = new String[100][5];

    static int totalPasajeros = 0;
    static int siguienteIdPasajero = 1;

    static String[][] tickets = new String[500][7];
    static int totalTickets = 0;
    static int siguienteIdTicket = 1;

    static String[][] ventas = new String[500][5];
    static int totalVentas = 0;
    static int siguienteIdVenta = 1;

    public static void main(String[] args) {

        inicializarTrenes();

        if (iniciarSesion()) {
            mostrarMenu();
        }

        System.out.println("Programa finalizado.");
    }

    // Inicializar los tres trenes automáticamente
    public static void inicializarTrenes() {

        trenes[0][0] = "1";
        trenes[0][1] = "Tren 1";
        trenes[0][2] = "4";
        trenes[0][3] = "8:00 AM";
        trenes[0][4] = "Programado";

        trenes[1][0] = "2";
        trenes[1][1] = "Tren 2";
        trenes[1][2] = "4";
        trenes[1][3] = "1:00 PM";
        trenes[1][4] = "Programado";

        trenes[2][0] = "3";
        trenes[2][1] = "Tren 3";
        trenes[2][2] = "4";
        trenes[2][3] = "6:00 PM";
        trenes[2][4] = "Programado";

    }


    public static void menuTickets() {
        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       GESTION DE TICKETS");
            System.out.println("================================");
            System.out.println("1. Registrar ticket");
            System.out.println("2. Consultar todos los tickets");
            System.out.println("3. Consultar ticket por ID");
            System.out.println("4. Actualizar ticket");
            System.out.println("5. Eliminar ticket");
            System.out.println("6. Volver al menu principal");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarTicket();
                    break;
                case 2:
                    consultarTickets();
                    break;
                case 3:
                    consultarTicketPorId();
                    break;
                case 4:
                    actualizarTicket();
                    break;
                case 5:
                    eliminarTicket();
                    break;
                case 6:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }


    public static void menuVentas() {
        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       GESTION DE VENTAS");
            System.out.println("================================");
            System.out.println("1. Registrar venta");
            System.out.println("2. Consultar todas las ventas");
            System.out.println("3. Consultar venta por ID");
            System.out.println("4. Actualizar venta");
            System.out.println("5. Eliminar venta");
            System.out.println("6. Volver al menu principal");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarVenta();
                    break;
                case 2:
                    consultarVentas();
                    break;
                case 3:
                    consultarVentaPorId();
                    break;
                case 4:
                    actualizarVenta();
                    break;
                case 5:
                    eliminarVenta();
                    break;
                case 6:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }

    public static void registrarVenta() {
        if (totalVentas >= ventas.length) {
            System.out.println("No hay espacio para mas ventas.");
            return;
        }

        if (totalTickets == 0) {
            System.out.println("No hay tickets registrados.");
            return;
        }

        consultarTickets();

        int idTicket = leerEntero("Ingrese el ID del ticket que desea vender: ");
        int posicionTicket = buscarTicket(idTicket);

        if (posicionTicket == -1) {
            System.out.println("Ticket no encontrado.");
            return;
        }

        if (buscarVentaPorTicket(idTicket) != -1) {
            System.out.println("Este ticket ya tiene una venta registrada.");
            return;
        }

        int precio = Integer.parseInt(tickets[posicionTicket][6]);
        int cantidad = 1;
        int total = precio * cantidad;

        ventas[totalVentas][0] = String.valueOf(siguienteIdVenta);
        ventas[totalVentas][1] = String.valueOf(idTicket);
        ventas[totalVentas][2] = String.valueOf(precio);
        ventas[totalVentas][3] = String.valueOf(cantidad);
        ventas[totalVentas][4] = String.valueOf(total);

        System.out.println("Venta registrada correctamente.");
        System.out.println("ID de venta: " + siguienteIdVenta);
        System.out.println("Total: $" + total);

        totalVentas++;
        siguienteIdVenta++;
    }

    public static int buscarVenta(int id) {
        for (int i = 0; i < totalVentas; i++) {
            if (Integer.parseInt(ventas[i][0]) == id) {
                return i;
            }
        }

        return -1;
    }

    public static int buscarVentaPorTicket(int idTicket) {
        for (int i = 0; i < totalVentas; i++) {
            if (Integer.parseInt(ventas[i][1]) == idTicket) {
                return i;
            }
        }

        return -1;
    }

    public static void mostrarDatosVenta(int posicion) {
        System.out.println("----------------------------");
        System.out.println("ID de venta: " + ventas[posicion][0]);
        System.out.println("ID del ticket: " + ventas[posicion][1]);
        System.out.println("Precio: $" + ventas[posicion][2]);
        System.out.println("Cantidad: " + ventas[posicion][3]);
        System.out.println("Total: $" + ventas[posicion][4]);
        System.out.println("----------------------------");
    }

    public static void consultarVentas() {
        System.out.println("\nLISTADO DE VENTAS");

        if (totalVentas == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (int i = 0; i < totalVentas; i++) {
            mostrarDatosVenta(i);
        }
    }

    public static void consultarVentaPorId() {
        int id = leerEntero("Ingrese el ID de la venta: ");
        int posicion = buscarVenta(id);

        if (posicion == -1) {
            System.out.println("Venta no encontrada.");
        } else {
            mostrarDatosVenta(posicion);
        }
    }

    public static void actualizarVenta() {
        consultarVentas();

        if (totalVentas == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID de la venta que desea actualizar: ");
        int posicion = buscarVenta(id);

        if (posicion == -1) {
            System.out.println("Venta no encontrada.");
            return;
        }

        consultarTickets();

        int idTicket = leerEntero("Ingrese el nuevo ID del ticket: ");
        int posicionTicket = buscarTicket(idTicket);

        if (posicionTicket == -1) {
            System.out.println("Ticket no encontrado.");
            return;
        }

        int ventaExistente = buscarVentaPorTicket(idTicket);

        if (ventaExistente != -1 && ventaExistente != posicion) {
            System.out.println("Este ticket ya pertenece a otra venta.");
            return;
        }

        int precio = Integer.parseInt(tickets[posicionTicket][6]);
        int cantidad = 1;
        int total = precio * cantidad;

        ventas[posicion][1] = String.valueOf(idTicket);
        ventas[posicion][2] = String.valueOf(precio);
        ventas[posicion][3] = String.valueOf(cantidad);
        ventas[posicion][4] = String.valueOf(total);

        System.out.println("Venta actualizada correctamente.");
    }

    public static void eliminarVenta() {
        consultarVentas();

        if (totalVentas == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID de la venta que desea eliminar: ");
        int posicion = buscarVenta(id);

        if (posicion == -1) {
            System.out.println("Venta no encontrada.");
            return;
        }

        for (int i = posicion; i < totalVentas - 1; i++) {
            ventas[i] = ventas[i + 1];
        }

        ventas[totalVentas - 1] = new String[5];
        totalVentas--;

        System.out.println("Venta eliminada correctamente.");
    }




    public static void registrarTicket() {
        if (totalTickets >= tickets.length) {
            System.out.println("No hay espacio para mas tickets.");
            return;
        }

        if (totalPasajeros == 0 || totalTrenes == 0) {
            System.out.println("Debe existir al menos un pasajero y un tren.");
            return;
        }

        System.out.println("\nREGISTRAR TICKET");
        consultarPasajeros();

        int idPasajero = leerEntero("Ingrese el ID del pasajero: ");
        int posPasajero = buscarPasajero(idPasajero);

        if (posPasajero == -1) {
            System.out.println("Pasajero no encontrado.");
            return;
        }

        consultarTrenes();

        int idTren = leerEntero("Ingrese el ID del tren: ");
        int posTren = buscarTren(idTren);

        if (posTren == -1) {
            System.out.println("Tren no encontrado.");
            return;
        }

        if (!trenes[posTren][4].equals("Programado")) {
            System.out.println("El tren no esta disponible para ventas.");
            return;
        }

        int puesto = buscarPuestoDisponible(idTren, -1);

        if (puesto == -1) {
            System.out.println("El tren no tiene puestos disponibles.");
            return;
        }

        String origen = pasajeros[posPasajero][3];
        String destino = pasajeros[posPasajero][4];

        int precio = calcularPrecio(origen, destino);

        if (precio == -1) {
            System.out.println("El trayecto del pasajero no es valido.");
            return;
        }

        tickets[totalTickets][0] = String.valueOf(siguienteIdTicket);
        tickets[totalTickets][1] = String.valueOf(idPasajero);
        tickets[totalTickets][2] = String.valueOf(idTren);
        tickets[totalTickets][3] = String.valueOf(puesto);
        tickets[totalTickets][4] = origen;
        tickets[totalTickets][5] = destino;
        tickets[totalTickets][6] = String.valueOf(precio);

        System.out.println("Ticket registrado correctamente.");
        System.out.println("ID del ticket: " + siguienteIdTicket);
        System.out.println("Puesto asignado: " + puesto);
        System.out.println("Precio: $" + precio);

        totalTickets++;
        siguienteIdTicket++;
    }

    public static int buscarPuestoDisponible(int idTren, int excluirTicket) {
        int posTren = buscarTren(idTren);

        if (posTren == -1) {
            return -1;
        }

        int capacidad = Integer.parseInt(trenes[posTren][2]);

        for (int puesto = 1; puesto <= capacidad; puesto++) {
            boolean ocupado = false;

            for (int i = 0; i < totalTickets; i++) {
                if (i != excluirTicket
                        && Integer.parseInt(tickets[i][2]) == idTren
                        && Integer.parseInt(tickets[i][3]) == puesto) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) {
                return puesto;
            }
        }

        return -1;
    }

    public static int calcularPrecio(String origen, String destino) {
        int posicionOrigen = posicionEstacion(origen);
        int posicionDestino = posicionEstacion(destino);

        if (posicionOrigen == -1 || posicionDestino == -1
                || posicionOrigen == posicionDestino) {
            return -1;
        }

        return Math.abs(posicionOrigen - posicionDestino) * 20000;
    }

    public static int posicionEstacion(String estacion) {
        if (estacion.equalsIgnoreCase("Santa Fe")) {
            return 0;
        } else if (estacion.equalsIgnoreCase("Cisneros")) {
            return 1;
        } else if (estacion.equalsIgnoreCase("Puerto Berrio")) {
            return 2;
        }

        return -1;
    }

    public static void consultarTickets() {
        System.out.println("\nLISTADO DE TICKETS");

        if (totalTickets == 0) {
            System.out.println("No hay tickets registrados.");
            return;
        }

        for (int i = 0; i < totalTickets; i++) {
            mostrarDatosTicket(i);
        }
    }

    public static void mostrarDatosTicket(int posicion) {
        int idPasajero = Integer.parseInt(tickets[posicion][1]);
        int idTren = Integer.parseInt(tickets[posicion][2]);

        int posPasajero = buscarPasajero(idPasajero);
        int posTren = buscarTren(idTren);

        System.out.println("----------------------------");
        System.out.println("ID Ticket: " + tickets[posicion][0]);

        if (posPasajero != -1) {
            System.out.println("Pasajero: " + pasajeros[posPasajero][1]
                    + " " + pasajeros[posPasajero][2]);
        }

        if (posTren != -1) {
            System.out.println("Tren: " + trenes[posTren][1]);
        }

        System.out.println("Puesto: " + tickets[posicion][3]);
        System.out.println("Origen: " + tickets[posicion][4]);
        System.out.println("Destino: " + tickets[posicion][5]);
        System.out.println("Precio: $" + tickets[posicion][6]);
        System.out.println("----------------------------");
    }

    public static int buscarTicket(int id) {
        for (int i = 0; i < totalTickets; i++) {
            if (Integer.parseInt(tickets[i][0]) == id) {
                return i;
            }
        }

        return -1;
    }

    public static void consultarTicketPorId() {
        int id = leerEntero("Ingrese el ID del ticket: ");
        int posicion = buscarTicket(id);

        if (posicion == -1) {
            System.out.println("Ticket no encontrado.");
        } else {
            mostrarDatosTicket(posicion);
        }
    }

    public static void actualizarTicket() {
        consultarTickets();

        if (totalTickets == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID del ticket que desea actualizar: ");
        int posicion = buscarTicket(id);

        if (posicion == -1) {
            System.out.println("Ticket no encontrado.");
            return;
        }

        consultarPasajeros();

        int idPasajero = leerEntero("Ingrese el nuevo ID del pasajero: ");
        int posPasajero = buscarPasajero(idPasajero);

        if (posPasajero == -1) {
            System.out.println("Pasajero no encontrado.");
            return;
        }

        consultarTrenes();

        int idTren = leerEntero("Ingrese el nuevo ID del tren: ");
        int posTren = buscarTren(idTren);

        if (posTren == -1 || !trenes[posTren][4].equals("Programado")) {
            System.out.println("Tren no disponible.");
            return;
        }

        int puesto = buscarPuestoDisponible(idTren, posicion);

        if (puesto == -1) {
            System.out.println("El tren no tiene puestos disponibles.");
            return;
        }

        String origen = pasajeros[posPasajero][3];
        String destino = pasajeros[posPasajero][4];
        int precio = calcularPrecio(origen, destino);

        if (precio == -1) {
            System.out.println("Trayecto no valido.");
            return;
        }

        tickets[posicion][1] = String.valueOf(idPasajero);
        tickets[posicion][2] = String.valueOf(idTren);
        tickets[posicion][3] = String.valueOf(puesto);
        tickets[posicion][4] = origen;
        tickets[posicion][5] = destino;
        tickets[posicion][6] = String.valueOf(precio);

        System.out.println("Ticket actualizado correctamente.");
    }

    public static void eliminarTicket() {
        consultarTickets();

        if (totalTickets == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID del ticket que desea eliminar: ");
        int posicion = buscarTicket(id);

        if (posicion == -1) {
            System.out.println("Ticket no encontrado.");
            return;
        }

        for (int i = posicion; i < totalTickets - 1; i++) {
            tickets[i] = tickets[i + 1];
        }

        tickets[totalTickets - 1] = new String[7];
        totalTickets--;

        System.out.println("Ticket eliminado correctamente.");
    }


    // Inicio de sesión
    public static boolean iniciarSesion() {

        int intentos = 3;

        while (intentos > 0) {

            System.out.println("\n================================");
            System.out.println("   FERROCARRILES DE ANTIOQUIA");
            System.out.println("================================");

            System.out.print("Usuario: ");
            String usuarioIngresado = sc.nextLine();

            System.out.print("Contrasena: ");
            String contrasenaIngresada = sc.nextLine();

            if (usuarioIngresado.equals(USUARIO)
                    && contrasenaIngresada.equals(CONTRASENA)) {

                System.out.println("\nInicio de sesion exitoso.");
                return true;

            } else {

                intentos--;

                System.out.println("\nUsuario o contrasena incorrectos.");
                System.out.println("Intentos restantes: " + intentos);

            }
        }

        System.out.println("\nAcceso bloqueado por intentos fallidos.");
        return false;
    }

    // Menu principal
    public static void mostrarMenu() {

        int opcion;

        do {

            System.out.println("\n================================");
            System.out.println("       MENU PRINCIPAL");
            System.out.println("================================");
            System.out.println("1. Gestionar pasajeros");
            System.out.println("2. Gestionar trenes");
            System.out.println("3. Gestionar tickets");
            System.out.println("4. Gestionar ventas");
            System.out.println("5. Controlar recorrido de trenes");
            System.out.println("6. Salir");
            System.out.println("================================");
            System.out.print("Seleccione una opcion: ");

            if (!sc.hasNextInt()) {
                System.out.println("Debe ingresar un numero.");
                sc.nextLine();
                opcion = 0;
                continue;
            }

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    menuPasajeros();
                    break;

                case 2:
                    menuTrenes();
                    break;

                case 3:
                    menuTickets();
                    break;
                case 4:
                    menuVentas();
                    break;

                case 5:
                    System.out.println("Modulo de recorridos pendiente de implementar.");
                    break;

                case 6:
                    System.out.println("Cerrando el sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);

    }

    // Consultar los trenes registrados
    public static void consultarTrenes() {
        System.out.println("\nLISTADO DE TRENES");

        if (totalTrenes == 0) {
            System.out.println("No hay trenes registrados.");
            return;
        }

        for (int i = 0; i < totalTrenes; i++) {
            System.out.println("----------------------------");
            System.out.println("ID: " + trenes[i][0]);
            System.out.println("Modelo: " + trenes[i][1]);
            System.out.println("Capacidad: " + trenes[i][2]);
            System.out.println("Horario: " + trenes[i][3]);
            System.out.println("Estado: " + trenes[i][4]);
        }

        System.out.println("----------------------------");
    }

    public static void menuTrenes() {
        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       GESTION DE TRENES");
            System.out.println("================================");
            System.out.println("1. Registrar tren");
            System.out.println("2. Consultar trenes");
            System.out.println("3. Actualizar tren");
            System.out.println("4. Eliminar tren");
            System.out.println("5. Volver al menu principal");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarTren();
                    break;
                case 2:
                    consultarTrenes();
                    break;
                case 3:
                    actualizarTren();
                    break;
                case 4:
                    eliminarTren();
                    break;
                case 5:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 5);
    }

    public static void registrarTren() {
        if (totalTrenes >= trenes.length) {
            System.out.println("No hay espacio para mas trenes.");
            return;
        }

        System.out.println("\nREGISTRAR TREN");

        trenes[totalTrenes][0] = String.valueOf(siguienteIdTren);

        System.out.print("Ingrese el modelo del tren: ");
        trenes[totalTrenes][1] = sc.nextLine();

        int capacidad = leerEntero("Ingrese la capacidad: ");

        while (capacidad <= 0) {
            System.out.println("La capacidad debe ser mayor que cero.");
            capacidad = leerEntero("Ingrese la capacidad: ");
        }

        trenes[totalTrenes][2] = String.valueOf(capacidad);

        System.out.print("Ingrese el horario: ");
        trenes[totalTrenes][3] = sc.nextLine();

        trenes[totalTrenes][4] = "Programado";

        System.out.println("Tren registrado correctamente.");
        System.out.println("ID asignado: " + siguienteIdTren);

        totalTrenes++;
        siguienteIdTren++;
    }

    public static void actualizarTren() {
        consultarTrenes();

        if (totalTrenes == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID del tren que desea actualizar: ");
        int posicion = buscarTren(id);

        if (posicion == -1) {
            System.out.println("No se encontro el tren.");
            return;
        }

        System.out.print("Ingrese el nuevo modelo: ");
        trenes[posicion][1] = sc.nextLine();

        int capacidad = leerEntero("Ingrese la nueva capacidad: ");

        while (capacidad <= 0) {
            System.out.println("La capacidad debe ser mayor que cero.");
            capacidad = leerEntero("Ingrese la nueva capacidad: ");
        }

        trenes[posicion][2] = String.valueOf(capacidad);

        System.out.print("Ingrese el nuevo horario: ");
        trenes[posicion][3] = sc.nextLine();

        System.out.println("Tren actualizado correctamente.");
    }

    public static void eliminarTren() {
        consultarTrenes();

        if (totalTrenes == 0) {
            return;
        }

        int id = leerEntero("Ingrese el ID del tren que desea eliminar: ");
        int posicion = buscarTren(id);

        if (posicion == -1) {
            System.out.println("No se encontro el tren.");
            return;
        }

        for (int i = posicion; i < totalTrenes - 1; i++) {
            trenes[i] = trenes[i + 1];
        }

        trenes[totalTrenes - 1] = new String[5];
        totalTrenes--;

        System.out.println("Tren eliminado correctamente.");
    }

    public static int buscarTren(int id) {
        for (int i = 0; i < totalTrenes; i++) {
            if (Integer.parseInt(trenes[i][0]) == id) {
                return i;
            }
        }

        return -1;
    }



    // MENU DE PASAJEROS
    public static void menuPasajeros() {

        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       GESTION DE PASAJEROS");
            System.out.println("================================");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Consultar todos");
            System.out.println("3. Consultar por ID");
            System.out.println("4. Actualizar pasajero");
            System.out.println("5. Eliminar pasajero");
            System.out.println("6. Regresar al menu principal");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarPasajero();
                    break;
                case 2:
                    consultarPasajeros();
                    break;
                case 3:
                    consultarPasajeroPorId();
                    break;
                case 4:
                    actualizarPasajero();
                    break;
                case 5:
                    eliminarPasajero();
                    break;
                case 6:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }

    // LEER NUMEROS ENTEROS
    public static int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    // SELECCIONAR ESTACION
    public static String seleccionarEstacion(String mensaje) {

        int opcion;

        do {
            System.out.println("\n" + mensaje);
            System.out.println("1. Santa Fe");
            System.out.println("2. Cisneros");
            System.out.println("3. Puerto Berrio");

            opcion = leerEntero("Seleccione una estacion: ");

            switch (opcion) {
                case 1:
                    return "Santa Fe";
                case 2:
                    return "Cisneros";
                case 3:
                    return "Puerto Berrio";
                default:
                    System.out.println("Estacion no valida.");
            }

        } while (true);
    }

    // REGISTRAR PASAJERO
    public static void registrarPasajero() {

        if (totalPasajeros >= pasajeros.length) {
            System.out.println("No hay espacio para mas pasajeros.");
            return;
        }

        System.out.println("\nREGISTRO DE PASAJERO");

        String nombre;

        do {
            System.out.print("Nombre: ");
            nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
            }
        } while (nombre.isEmpty());

        String apellido;

        do {
            System.out.print("Apellido: ");
            apellido = sc.nextLine().trim();

            if (apellido.isEmpty()) {
                System.out.println("El apellido no puede estar vacio.");
            }
        } while (apellido.isEmpty());

        String origen = seleccionarEstacion("Seleccione el origen:");
        String destino;

        do {
            destino = seleccionarEstacion("Seleccione el destino:");

            if (origen.equals(destino)) {
                System.out.println("El origen y destino no pueden ser iguales.");
            }
        } while (origen.equals(destino));

        pasajeros[totalPasajeros][0] =
                String.valueOf(siguienteIdPasajero);
        pasajeros[totalPasajeros][1] = nombre;
        pasajeros[totalPasajeros][2] = apellido;
        pasajeros[totalPasajeros][3] = origen;
        pasajeros[totalPasajeros][4] = destino;

        System.out.println("\nPasajero registrado correctamente.");
        System.out.println("ID asignado: " + siguienteIdPasajero);

        totalPasajeros++;
        siguienteIdPasajero++;
    }

    // CONSULTAR TODOS LOS PASAJEROS
    public static void consultarPasajeros() {

        if (totalPasajeros == 0) {
            System.out.println("\nNo hay pasajeros registrados.");
            return;
        }

        System.out.println("\nLISTADO DE PASAJEROS");

        for (int i = 0; i < totalPasajeros; i++) {

            System.out.println("----------------------------");
            System.out.println("ID: " + pasajeros[i][0]);
            System.out.println("Nombre: " + pasajeros[i][1]);
            System.out.println("Apellido: " + pasajeros[i][2]);
            System.out.println("Origen: " + pasajeros[i][3]);
            System.out.println("Destino: " + pasajeros[i][4]);
        }

        System.out.println("----------------------------");
    }

    // BUSCAR PASAJERO POR ID
    public static int buscarPasajero(int id) {

        for (int i = 0; i < totalPasajeros; i++) {

            if (Integer.parseInt(pasajeros[i][0]) == id) {
                return i;
            }
        }

        return -1;
    }

    // CONSULTAR PASAJERO POR ID
    public static void consultarPasajeroPorId() {

        int id = leerEntero("\nIngrese el ID del pasajero: ");

        int posicion = buscarPasajero(id);

        if (posicion == -1) {
            System.out.println("No existe un pasajero con ese ID.");
            return;
        }

        System.out.println("\nDATOS DEL PASAJERO");
        System.out.println("ID: " + pasajeros[posicion][0]);
        System.out.println("Nombre: " + pasajeros[posicion][1]);
        System.out.println("Apellido: " + pasajeros[posicion][2]);
        System.out.println("Origen: " + pasajeros[posicion][3]);
        System.out.println("Destino: " + pasajeros[posicion][4]);
    }

    // ACTUALIZAR PASAJERO
    public static void actualizarPasajero() {

        int id = leerEntero("\nIngrese el ID del pasajero: ");

        int posicion = buscarPasajero(id);

        if (posicion == -1) {
            System.out.println("No existe un pasajero con ese ID.");
            return;
        }

        System.out.println("\nDATOS DEL PASAJERO");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. Origen");
        System.out.println("4. Destino");

        int opcion = leerEntero("Seleccione el dato que desea actualizar: ");

        switch (opcion) {

            case 1:
                System.out.print("Ingrese el nuevo nombre: ");
                String nombre = sc.nextLine().trim();

                if (!nombre.isEmpty()) {
                    pasajeros[posicion][1] = nombre;
                    System.out.println("Nombre actualizado.");
                } else {
                    System.out.println("El nombre no puede estar vacio.");
                }
                break;

            case 2:
                System.out.print("Ingrese el nuevo apellido: ");
                String apellido = sc.nextLine().trim();

                if (!apellido.isEmpty()) {
                    pasajeros[posicion][2] = apellido;
                    System.out.println("Apellido actualizado.");
                } else {
                    System.out.println("El apellido no puede estar vacio.");
                }
                break;

            case 3:
                String nuevoOrigen = seleccionarEstacion("Seleccione el nuevo origen:");

                if (!nuevoOrigen.equals(pasajeros[posicion][4])) {
                    pasajeros[posicion][3] = nuevoOrigen;
                    System.out.println("Origen actualizado.");
                } else {
                    System.out.println("El origen no puede ser igual al destino.");
                }
                break;

            case 4:
                String nuevoDestino = seleccionarEstacion("Seleccione el nuevo destino:");

                if (!nuevoDestino.equals(pasajeros[posicion][3])) {
                    pasajeros[posicion][4] = nuevoDestino;
                    System.out.println("Destino actualizado.");
                } else {
                    System.out.println("El destino no puede ser igual al origen.");
                }
                break;

            default:
                System.out.println("Opcion no valida.");
        }
    }

    // ELIMINAR PASAJERO
    public static void eliminarPasajero() {

        int id = leerEntero("\nIngrese el ID del pasajero que desea eliminar: ");

        int posicion = buscarPasajero(id);

        if (posicion == -1) {
            System.out.println("No existe un pasajero con ese ID.");
            return;
        }

        System.out.println("Pasajero: " + pasajeros[posicion][1]
                + " " + pasajeros[posicion][2]);

        System.out.print("Confirma la eliminacion? (S/N): ");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            for (int i = posicion; i < totalPasajeros - 1; i++) {
                pasajeros[i] = pasajeros[i + 1];
            }

            pasajeros[totalPasajeros - 1] = new String[5];

            totalPasajeros--;

            System.out.println("Pasajero eliminado correctamente.");

        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

}
