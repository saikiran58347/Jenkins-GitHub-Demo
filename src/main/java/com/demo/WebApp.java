
package com.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebApp {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", 8081), 0);

        server.createContext("/", WebApp::handleRequest);
        server.start();

        System.out.println("CGPA Calculator running at http://localhost:8081");
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        String result = "";

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8);

                String gradesInput = getValue(body, "grades");
                String creditsInput = getValue(body, "credits");

                if (gradesInput.isBlank() || creditsInput.isBlank()) {
                    throw new IllegalArgumentException(
                            "Please enter grades and credits.");
                }

                String[] grades = gradesInput.split(",", -1);
                String[] creditValues = creditsInput.split(",", -1);

                if (grades.length != creditValues.length) {
                    throw new IllegalArgumentException(
                            "Enter one credit value for each grade.");
                }

                int[] credits = new int[creditValues.length];

                for (int i = 0; i < credits.length; i++) {
                    credits[i] = Integer.parseInt(
                            creditValues[i].trim());
                }

                double cgpa = App.calculateCGPA(grades, credits);

                boolean failed = false;

                for (String grade : grades) {
                    if ("F".equalsIgnoreCase(grade.trim())) {
                        failed = true;
                        break;
                    }
                }

                result = "<div class='result'><h2>CGPA: "
                        + String.format("%.2f", cgpa)
                        + "</h2><p>Status: "
                        + (failed ? "Failed subject present"
                                  : "No failed subjects")
                        + "</p></div>";

            } catch (Exception e) {
                String message = e.getMessage();

                if (e instanceof NumberFormatException) {
                    message = "Credits must be whole numbers: 2, 3, or 4.";
                }

                result = "<div class='error'>Error: "
                        + escapeHtml(message == null
                                ? "Invalid input." : message)
                        + "</div>";
            }
        }

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>CGPA Calculator</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #eef2ff;
                            padding: 30px 15px;
                        }
                        .card {
                            max-width: 500px;
                            margin: auto;
                            padding: 25px;
                            background: white;
                            border-radius: 12px;
                            box-shadow: 0 4px 15px #0002;
                        }
                        h1 { color: #304fbd; text-align: center; }
                        label { display: block; margin-top: 18px; font-weight: bold; }
                        input {
                            width: 100%;
                            box-sizing: border-box;
                            padding: 12px;
                            margin-top: 8px;
                        }
                        button {
                            width: 100%;
                            padding: 12px;
                            margin-top: 20px;
                            background: #304fbd;
                            color: white;
                            border: none;
                            cursor: pointer;
                        }
                        .result { background: #dcfce7; padding: 12px; margin-top: 20px; }
                        .error { background: #fee2e2; padding: 12px; margin-top: 20px; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <h1>CGPA Calculator</h1>
                        <p>Grades: S=10, A=9, B=8, C=7, D=6, E=5, F=0</p>
                        <p>Allowed credits: 2, 3, or 4</p>
                        <form method="POST">
                            <label>Grades (comma-separated)</label>
                            <input name="grades" placeholder="S,A,B,F" required>
                            <label>Credits (comma-separated)</label>
                            <input name="credits" placeholder="4,4,3,2" required>
                            <button type="submit">Calculate CGPA</button>
                        </form>
                        RESULT_PLACEHOLDER
                    </div>
                </body>
                </html>
                """.replace("RESULT_PLACEHOLDER", result);

        byte[] response = html.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);

        try (var output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    private static String getValue(String body, String key) {
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);

            if (parts.length == 2 &&
                    URLDecoder.decode(parts[0], StandardCharsets.UTF_8)
                            .equals(key)) {
                return URLDecoder.decode(
                        parts[1], StandardCharsets.UTF_8);
            }
        }

        throw new IllegalArgumentException("Missing field: " + key);
    }

    private static String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
