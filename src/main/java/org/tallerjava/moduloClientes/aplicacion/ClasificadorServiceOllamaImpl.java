package org.tallerjava.moduloClientes.aplicacion;

import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class ClasificadorServiceOllamaImpl implements ClasificadorService {

    @Override
    public String clasificar(String texto) {
        try {
            String textoEscapado = texto.replace("\\", "\\\\").replace("\"", "\\\"");
            String prompt = "Responde solo NEGATIVO, POSITIVO o NEUTRO para este texto: " + textoEscapado;
            String json = "{\"model\":\"llama3\",\"prompt\":\"" + prompt + "\",\"stream\":false}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://ollama:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) return "NEUTRO";

            String body = response.body();
            if (body.contains("NEGATIVO")) return "NEGATIVO";
            if (body.contains("POSITIVO")) return "POSITIVO";
            return "NEUTRO";
        } catch (Exception e) {
            return "NEUTRO";
        }
    }
}
