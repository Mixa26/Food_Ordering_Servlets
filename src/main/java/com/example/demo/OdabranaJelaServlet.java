package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

@WebServlet(name = "odabranaJela", value = "/odabrana-jela")
public class OdabranaJelaServlet extends HttpServlet {

    private static String lozinka = "";

    private ArrayList<HttpSession> sessions = new ArrayList<>();

    private ArrayList<String> ponedeljak = new ArrayList<>();
    private ArrayList<Integer> ponedeljakCount = new ArrayList<>();
    private ArrayList<String> utorak = new ArrayList<>();
    private ArrayList<Integer> utorakCount = new ArrayList<>();
    private ArrayList<String> sreda = new ArrayList<>();
    private ArrayList<Integer> sredaCount = new ArrayList<>();
    private ArrayList<String> cetvrtak = new ArrayList<>();
    private ArrayList<Integer> cetvrtakCount = new ArrayList<>();
    private ArrayList<String> petak = new ArrayList<>();
    private ArrayList<Integer> petakCount = new ArrayList<>();

    public void init() {

        String ponedeljakPath = "";
        String utorakPath = "";
        String sredaPath = "";
        String cetvrtakPath = "";
        String petakPath = "";
        String passwordPath = "";

        try {
            ponedeljakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("ponedeljak.txt").toURI()).toString();
            utorakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("utorak.txt").toURI()).toString();
            sredaPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("sreda.txt").toURI()).toString();
            cetvrtakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("cetvrtak.txt").toURI()).toString();
            petakPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("petak.txt").toURI()).toString();
            passwordPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("password.txt").toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File ponedeljakFile = new File(ponedeljakPath);
        File utorakFile = new File(utorakPath);
        File sredaFile = new File(sredaPath);
        File cetvrtakFile = new File(cetvrtakPath);
        File petakFile = new File(petakPath);
        File passwordFile = new File(passwordPath);

        Scanner ponedeljakScanner = null;
        Scanner utorakScanner = null;
        Scanner sredaScanner = null;
        Scanner cetvrtakScanner = null;
        Scanner petakScanner = null;
        Scanner passwordScanner = null;

        try {
            ponedeljakScanner = new Scanner(ponedeljakFile);
            utorakScanner = new Scanner(utorakFile);
            sredaScanner = new Scanner(sredaFile);
            cetvrtakScanner = new Scanner(cetvrtakFile);
            petakScanner = new Scanner(petakFile);
            passwordScanner = new Scanner(passwordFile);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        if (passwordScanner.hasNext()){
            lozinka = passwordScanner.nextLine();
        }

        while (ponedeljakScanner.hasNext() | utorakScanner.hasNext() | sredaScanner.hasNext() | cetvrtakScanner.hasNext() | petakScanner.hasNext()){
            if (ponedeljakScanner.hasNext()){
                String curr = ponedeljakScanner.nextLine();
                synchronized (this){
                    ponedeljak.add(curr);
                    ponedeljakCount.add(0);
                }
            }
            if (utorakScanner.hasNext()){
                String curr = utorakScanner.nextLine();
                synchronized (this){
                    utorak.add(curr);
                    utorakCount.add(0);
                }
            }
            if (sredaScanner.hasNext()){
                String curr = sredaScanner.nextLine();
                synchronized (this){
                    sreda.add(curr);
                    sredaCount.add(0);
                }
            }
            if (cetvrtakScanner.hasNext()){
                String curr = cetvrtakScanner.nextLine();
                synchronized (this){
                    cetvrtak.add(curr);
                    cetvrtakCount.add(0);
                }
            }
            if (petakScanner.hasNext()){
                String curr = petakScanner.nextLine();
                synchronized (this){
                    petak.add(curr);
                    petakCount.add(0);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        if (req.getParameter("lozinka") == null || !req.getParameter("lozinka").equals(lozinka)){
            out.println("<html><head><title>Niste autorizovani</title></head><body><h1>Netacna sifra, niste autorizovani!</h1></body></html>");
            return;
        }

        if (req.getParameter("del") != null && req.getParameter("del").equals("true")){
            doDelete(req,resp);
        }

        out.println("<html>\n" +
            "    <head>\n" +
            "        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
            "        \n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div class=\"container\">\n" +
            "            <div class=\"row\">\n" +
            "                <div class=\"col-lg-6\">\n" +
            "                    <button class=\"btn btn-danger\" onclick=\"window.location.href=\'http://localhost:8080/demo_war_exploded/odabrana-jela?lozinka=albatroz&del=true\'\">Ocisti</button>" +
            "                    <h1>Ponedeljak</h1>" +
            "                    <table class=\"table table-dark\">\n" +
            "                        <thead>\n" +
            "                          <tr>\n" +
            "                            <th scope=\"col\">#</th>\n" +
            "                            <th scope=\"col\">Jelo</th>\n" +
            "                            <th scope=\"col\">Kolicina</th>\n" +
            "                          </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody>\n");

        for (int i = 0; i < ponedeljak.size(); i++){
            out.println("" +
                    "<tr>" +
                        "<th scope=\"row\">" + Integer.toString(i+1) + "</th>" +
                        "<td>" + ponedeljak.get(i) + "</td>" +
                        "<td>" + ponedeljakCount.get(i) + "</td>" +
                    "</tr>");
        }

        out.println(
            "                        </tbody>\n" +
            "                      </table>\n" +
            "                </div>\n" +
            "            </div>\n");

        out.println(
            "            <div class=\"row\">\n" +
            "                <div class=\"col-lg-6\">\n" +
            "                    <h1>Utorak</h1>" +
            "                    <table class=\"table table-dark\">\n" +
            "                        <thead>\n" +
            "                          <tr>\n" +
            "                            <th scope=\"col\">#</th>\n" +
            "                            <th scope=\"col\">Jelo</th>\n" +
            "                            <th scope=\"col\">Kolicina</th>\n" +
            "                          </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody>\n");

        for (int i = 0; i < utorak.size(); i++){
            out.println("" +
                    "<tr>" +
                    "<th scope=\"row\">" + Integer.toString(i+1) + "</th>" +
                    "<td>" + utorak.get(i) + "</td>" +
                    "<td>" + utorakCount.get(i) + "</td>" +
                    "</tr>");
        }

        out.println(
            "                        </tbody>\n" +
            "                      </table>\n" +
            "                </div>\n" +
            "            </div>\n");

        out.println(
            "            <div class=\"row\">\n" +
            "                <div class=\"col-lg-6\">\n" +
            "                    <h1>Sreda</h1>" +
            "                    <table class=\"table table-dark\">\n" +
            "                        <thead>\n" +
            "                          <tr>\n" +
            "                            <th scope=\"col\">#</th>\n" +
            "                            <th scope=\"col\">Jelo</th>\n" +
            "                            <th scope=\"col\">Kolicina</th>\n" +
            "                          </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody>\n");

        for (int i = 0; i < sreda.size(); i++){
            out.println("" +
                    "<tr>" +
                    "<th scope=\"row\">" + Integer.toString(i+1) + "</th>" +
                    "<td>" + sreda.get(i) + "</td>" +
                    "<td>" + sredaCount.get(i) + "</td>" +
                    "</tr>");
        }

        out.println(
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                </div>\n" +
                "            </div>\n");

        out.println(
                "            <div class=\"row\">\n" +
                "                <div class=\"col-lg-6\">\n" +
                "                    <h1>Cetvrtak</h1>" +
                "                    <table class=\"table table-dark\">\n" +
                "                        <thead>\n" +
                "                          <tr>\n" +
                "                            <th scope=\"col\">#</th>\n" +
                "                            <th scope=\"col\">Jelo</th>\n" +
                "                            <th scope=\"col\">Kolicina</th>\n" +
                "                          </tr>\n" +
                "                        </thead>\n" +
                "                        <tbody>\n");

        for (int i = 0; i < cetvrtak.size(); i++){
            out.println("" +
                    "<tr>" +
                    "<th scope=\"row\">" + Integer.toString(i+1) + "</th>" +
                    "<td>" + cetvrtak.get(i) + "</td>" +
                    "<td>" + cetvrtakCount.get(i) + "</td>" +
                    "</tr>");
        }

        out.println(
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                </div>\n" +
                "            </div>\n");

        out.println(
                "            <div class=\"row\">\n" +
                "                <div class=\"col-lg-6\">\n" +
                "                    <h1>Petak</h1>" +
                "                    <table class=\"table table-dark\">\n" +
                "                        <thead>\n" +
                "                          <tr>\n" +
                "                            <th scope=\"col\">#</th>\n" +
                "                            <th scope=\"col\">Jelo</th>\n" +
                "                            <th scope=\"col\">Kolicina</th>\n" +
                "                          </tr>\n" +
                "                        </thead>\n" +
                "                        <tbody>\n");

        for (int i = 0; i < petak.size(); i++){
            out.println("" +
                    "<tr>" +
                    "<th scope=\"row\">" + Integer.toString(i+1) + "</th>" +
                    "<td>" + petak.get(i) + "</td>" +
                    "<td>" + petakCount.get(i) + "</td>" +
                    "</tr>");
        }

        out.println(
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                </div>\n" +
                "            </div>\n");

        out.println(
            "        </div>\n" +
            "        <script src=\"/show_cameras.js\"></script>\n" +
            "    </body>\n" +
            "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("ordered") != null && req.getSession().getAttribute("ordered").equals(true))return;

        req.getSession().setAttribute("ordered", true);

        synchronized (this){
            sessions.add(req.getSession());
        }

        ponedeljakCount.set(Integer.parseInt(req.getParameter("ponedeljak")) - 1, ponedeljakCount.get(Integer.parseInt(req.getParameter("ponedeljak")) - 1) + 1);
        utorakCount.set(Integer.parseInt(req.getParameter("utorak")) - 1, utorakCount.get(Integer.parseInt(req.getParameter("utorak")) - 1) + 1);
        sredaCount.set(Integer.parseInt(req.getParameter("sreda")) - 1, sredaCount.get(Integer.parseInt(req.getParameter("sreda")) - 1) + 1);
        cetvrtakCount.set(Integer.parseInt(req.getParameter("cetvrtak")) - 1, cetvrtakCount.get(Integer.parseInt(req.getParameter("cetvrtak")) - 1) + 1);
        petakCount.set(Integer.parseInt(req.getParameter("petak")) - 1, petakCount.get(Integer.parseInt(req.getParameter("petak")) - 1) + 1);

        resp.sendRedirect("http://localhost:8080/demo_war_exploded/potvrda");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        for (int i = 0; i < ponedeljakCount.size(); i++){
            synchronized (this){
                ponedeljakCount.set(i,0);
            }
        }

        for (int i = 0; i < utorakCount.size(); i++){
            synchronized (this){
                utorakCount.set(i,0);
            }
        }

        for (int i = 0; i < sredaCount.size(); i++){
            synchronized (this){
                sredaCount.set(i,0);
            }
        }

        for (int i = 0; i < cetvrtakCount.size(); i++){
            synchronized (this){
                cetvrtakCount.set(i,0);
            }
        }

        for (int i = 0; i < petakCount.size(); i++){
            synchronized (this){
                petakCount.set(i,0);
            }
        }

        synchronized (this) {
            for (int i = 0; i < sessions.size(); i++) {
                sessions.get(i).setAttribute("ordered", false);
            }
        }

        resp.sendRedirect("http://localhost:8080/demo_war_exploded/odabrana-jela?lozinka=" + lozinka);
    }
}
