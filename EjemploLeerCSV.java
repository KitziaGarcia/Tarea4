import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EjemploLeerCSV {
    private static Map<String, Integer> palabrasMap = new HashMap<>();
    private static Map<String, Integer> numerosMap = new HashMap<>();

    public static void main(String[] args) {
        leer();
        escribirResultados();
    }

    public static void leer() {
        HashMap<String, Integer> frecuenciaPalabras = new HashMap<>();
        String linea;

        try (BufferedReader leer = new BufferedReader(new FileReader("CSV.csv"))) {
            // Leer el archivo linea por linea.
            while ((linea = leer.readLine()) != null) {
                linea = linea.toLowerCase().replace(",", " "); // Pasar a minuscula toda la linea.
                String[] palabras = linea.split("\\s+"); // Separar las palabras de la linea.

                // Agregar las palabras al ArrayList, incrementando su frecuencia.
                for (String palabra : palabras) {
                    frecuenciaPalabras.put(palabra, frecuenciaPalabras.getOrDefault(palabra, 0) + 1);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("No encontró el archivo");
        } catch (IOException ex) {
            System.err.println("No se pudo leer el archivo");
        }

        // Separar palabras y números en mapas distintos.
        for (Map.Entry<String, Integer> entry : frecuenciaPalabras.entrySet()) {
            String clave = entry.getKey();
            if (clave.matches(".*\\d.*")) {
                numerosMap.put(clave, entry.getValue());
            } else {
                palabrasMap.put(clave, entry.getValue());
            }
        }
    }

    public static void escribirResultados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("frecuenciaPalabras.txt"))) {
            // Escribir las palabras con su respectiva frecuencia en el archivo nuevo.
            writer.write("Palabras:\n");
            for (Map.Entry<String, Integer> entry : palabrasMap.entrySet()) {
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }

            // Escribir los numeros con su respectiva frecuencia en el archivo nuevo.
            writer.write("\nNúmeros:\n");
            for (Map.Entry<String, Integer> entry : numerosMap.entrySet()) {
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("No se pudo escribir el archivo");
        }
    }
}