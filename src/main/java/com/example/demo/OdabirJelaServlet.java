package com.example.demo;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "odabirJela", value = "/odabir-jela")
public class OdabirJelaServlet extends HttpServlet {

    private ArrayList<String> ponedeljak = new ArrayList<>();
    private ArrayList<String> utorak = new ArrayList<>();
    private ArrayList<String> sreda = new ArrayList<>();
    private ArrayList<String> cetvrtak = new ArrayList<>();
    private ArrayList<String> petak = new ArrayList<>();

    public void init() {

        String ponedeljakPath = "";
        String utorakPath = "";
        String sredaPath = "";
        String cetvrtakPath = "";
        String petakPath = "";

        try {
            ponedeljakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("ponedeljak.txt").toURI()).toString();
            utorakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("utorak.txt").toURI()).toString();
            sredaPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("sreda.txt").toURI()).toString();
            cetvrtakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("cetvrtak.txt").toURI()).toString();
            petakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("petak.txt").toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File ponedeljakFile = new File(ponedeljakPath);
        File utorakFile = new File(utorakPath);
        File sredaFile = new File(sredaPath);
        File cetvrtakFile = new File(cetvrtakPath);
        File petakFile = new File(petakPath);

        Scanner ponedeljakScanner = null;
        Scanner utorakScanner = null;
        Scanner sredaScanner = null;
        Scanner cetvrtakScanner = null;
        Scanner petakScanner = null;

        try {
            ponedeljakScanner = new Scanner(ponedeljakFile);
            utorakScanner = new Scanner(utorakFile);
            sredaScanner = new Scanner(sredaFile);
            cetvrtakScanner = new Scanner(cetvrtakFile);
            petakScanner = new Scanner(petakFile);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        while (ponedeljakScanner.hasNext() | utorakScanner.hasNext() | sredaScanner.hasNext() | cetvrtakScanner.hasNext() | petakScanner.hasNext()){
            if (ponedeljakScanner.hasNext()){
                String curr = ponedeljakScanner.nextLine();
                synchronized (this){
                    ponedeljak.add(curr);
                }
            }
            if (utorakScanner.hasNext()){
                String curr = utorakScanner.nextLine();
                synchronized (this){
                    utorak.add(curr);
                }
            }
            if (sredaScanner.hasNext()){
                String curr = sredaScanner.nextLine();
                synchronized (this){
                    sreda.add(curr);
                }
            }
            if (cetvrtakScanner.hasNext()){
                String curr = cetvrtakScanner.nextLine();
                synchronized (this){
                    cetvrtak.add(curr);
                }
            }
            if (petakScanner.hasNext()){
                String curr = petakScanner.nextLine();
                synchronized (this){
                    petak.add(curr);
                }
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getSession().getAttribute("ordered") != null){
            if (request.getSession().getAttribute("ordered").equals(true)){
                response.sendRedirect("http://localhost:8080/demo_war_exploded/potvrda");
            }
        }

        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-lg-6\">\n" +
                "                    <h1>Odaberite Vas rucak:</h1>\n" +
                "                    <form method=\"post\", action=\"/demo_war_exploded/odabrana-jela\">  \n" +
                "                        <div class=\"form-group\">\n" +
                "                        <label for=\"ponedeljak\">Ponedeljak</label>\n" +
                "                        <select name=\"ponedeljak\" class=\"ponedeljak\"> ");

        int c = 0;
        for (String dish : ponedeljak){
            c++;
            out.println("<option value=\"" + c +"\">" + dish + "</option>");
        }

        out.println(
                        "                        </select>" +
                        "                        <label for=\"utorak\">Utorak</label>\n" +
                        "                        <select name=\"utorak\" class=\"utorak\"> ");

        c = 0;
        for (String dish : utorak){
            c++;
            out.println("<option value=\"" + c +"\">" + dish + "</option>");
        }

        out.println(
                        "                        </select>" +
                        "                        <label for=\"sreda\">Sreda</label>\n" +
                        "                        <select name=\"sreda\" class=\"sreda\"> ");

        c = 0;
        for (String dish : sreda){
            c++;
            out.println("<option value=\"" + c +"\">" + dish + "</option>");
        }

        out.println(
                        "                        </select>" +
                        "                        <label for=\"cetvrtak\">Cetvrtak</label>\n" +
                        "                        <select name=\"cetvrtak\" class=\"cetvrtak\"> ");

        c = 0;
        for (String dish : cetvrtak){
            c++;
            out.println("<option value=\"" + c +"\">" + dish + "</option>");
        }

        out.println(
                        "                        </select>" +
                        "                        <label for=\"petak\">Petak</label>\n" +
                        "                        <select name=\"petak\" class=\"petak\"> ");

        c = 0;
        for (String dish : petak){
            c++;
            out.println("<option value=\"" + c +"\">" + dish + "</option>");
        }

        out.println(
                "                        </select>" +
                "                        <button type=\"submit\" id=\"btn\" class=\"btn btn-primary\">Potvrdite unos</button>\n" +
                "                    </form>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void destroy() {
    }
}