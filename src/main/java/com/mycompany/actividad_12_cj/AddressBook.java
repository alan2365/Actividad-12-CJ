package com.mycompany.actividad_12_cj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALAN
 */
public class AddressBook {

    static HashMap<String, String> contactos = new HashMap<String, String>();

    static Scanner entradaEscaner = new Scanner(System.in);

    static File archivo = null;
    static FileReader leer = null;
    static BufferedReader datos = null;
    static FileWriter escribir = null;
    static PrintWriter linea = null;

    static String nombre = "";
    static String numero = "";
    static String registro = "";
    static final String COMA = ",";
    static int opc = 0;

    public static void main(String[] args) {
        
        load();
        
        do {

            System.out.println("Directorio\n"
                    + "1 Mostrar contactos de la agenda\n"
                    + "2 Crear un nuevo contacto\n"
                    + "3 Borrar un contacto\n"
                    + "0 Salir\n");

            opc = Integer.parseInt(entradaEscaner.nextLine());

            switch (opc) {
                case 1:
                    list();
                    break;
                case 2:
                    create();
                    save();
                    break;
                case 3:
                    delete();
                    save();
                    break;
            }
        } while (opc != 0);
    }

    public static void load() {

        archivo = new File("contactos.txt");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();

            } catch (IOException ex) {
                Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {

                load2();
                leer = new FileReader(archivo);
                datos = new BufferedReader(leer);

            } catch (IOException ex) {
                Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void load2() {
        registro = "";

        try {

            leer = new FileReader(archivo);
            datos = new BufferedReader(leer);

            while (registro != null) {
                try {

                    registro = datos.readLine();

                    if (registro != null) {
                        String[] fields = registro.split(COMA);

                        numero = fields[0];
                        nombre = fields[1];
                        contactos.put(numero, nombre);

                    }

                } catch (IOException ex) {
                    Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            leer.close();
            datos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void save() {

        try {
            resetArchivo();
            escribir = new FileWriter(archivo, true);
            linea = new PrintWriter(escribir);

            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                linea.println(entry.getKey() + "," + entry.getValue());
            }

            linea.close();
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void list() {

        System.out.println("CONTACTOS:");
        for (Map.Entry<String, String> entry : contactos.entrySet()) {
            System.out.println("{" + entry.getKey() + "}:{" + entry.getValue() + "}");
        }

        System.out.println();
    }

    public static void create() {

        System.out.println("\nNombre");
        nombre = entradaEscaner.nextLine();

        System.out.println("\nNumero");
        numero = entradaEscaner.nextLine();

        contactos.put(numero, nombre);

        try {

            escribir = new FileWriter(archivo, true);
            linea = new PrintWriter(escribir);
            linea.println(numero + nombre);

            linea.close();
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println();
    }

    public static void delete() {
        System.out.println("Borrar Contacto");
        System.out.println("Para eliminar un contacto, Ingresa el numero de telefono");
        numero = entradaEscaner.nextLine();
        System.out.println();
        contactos.remove(numero);
    }

    public static void resetArchivo() {
        try {

            BufferedWriter writer = null;
            escribir = new FileWriter(archivo);
            writer = new BufferedWriter(escribir);
            PrintWriter escribir1 = new PrintWriter(escribir);

            writer.write("");

            escribir1.close();
            writer.close();
            escribir.close();

        } catch (IOException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
