package bacit.web.bacit_web;
import bacit.web.bacit_models.FjernAnsattModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(name= "FjernAnsattServlet", value = "/admin/fjerne-ansatt")
public class FjernAnsattServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        FjernAnsattInput(out, null);
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try {
            visTabell(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    FjernAnsattModel model = new FjernAnsattModel();
    HtmlHelper HtmlHelper = new HtmlHelper();
    model.setAnsatt_ID(request.getParameter("Ansattnummeret"));

    PrintWriter out = response.getWriter();
    HtmlHelper.writeHtmlStartCss(out);
    HtmlHelper.writeHtmlStartKnappLogo(out);

    if (CheckFjernAnsatt(model)) {
        try {
            AnsattFjernet(model, out);
        } catch (SQLException ex) {
            out.println(ex.getMessage());
        }
        HtmlHelper.writeHtmlStart(out, "Ansatt er fjernet!");
        out.println("<br><b>Ansattnummeret</b>" + model.getAnsatt_ID());

        HtmlHelper.writeHtmlEnd(out);

    } else {
        FjernAnsattInput(out, "Det har oppstått noe feil");
    }
}
    public void AnsattFjernet(FjernAnsattModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "DELETE FROM Ansatt WHERE Ansatt_ID = ?;";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getAnsatt_ID());

            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void FjernAnsattInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Fjern Ansatt");
        if(feilMelding !=null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-ansatt' method='POST'>");

            out.println("<br><br>");
            out.println("<input type='text' name='Ansattnummeret' placeholder='Skriv inn ansattnummeret til den ansatte du ønsker å slette fra systemet'/>");

            out.println("<br><br> <input type='submit' value='Fjern ansatt'/>");
            out.println("</form>");

            HtmlHelper.writeHtmlEnd(out);


    }

    public void visTabell(PrintWriter out) throws SQLException{
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String ShowTable = "SELECT DISTINCT Ansatt_ID, Fornavn, Etternavn, Mobilnummer, Epost, Adresse, Bynavn, Postnummer FROM Ansatt";
            PreparedStatement kode = db.prepareStatement(ShowTable);
            ResultSet rs;
            rs = kode.executeQuery();

            out.println("<div id=Sentrere>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Ansatt ID</th>" +
                    "<th>Fornavn</th>" +
                    "<th>Etternavn</th>" +
                    "<th>Mobilnummer</th>" +
                    "<th>Epost</th>" +
                    "<th>Adresse</th>" +
                    "<th>Bynavn</th>" +
                    "<th>Postnummer</th>"+
                    "</tr>");
            while(rs.next()){
                out.println("<tr>"+
                        "<td>" + rs.getInt("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "<td>" + rs.getInt("Mobilnummer") + "</td>" +
                        "<td>" + rs.getString("Epost") + "</td>" +
                        "<td>" + rs.getString("Adresse") + "</td>" +
                        "<td>" + rs.getString("Bynavn") + "</td>" +
                        "<td>" + rs.getInt("Postnummer") + "</td>" +
                        "</tr>");
            }
            out.println("</div>");


            db.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean CheckFjernAnsatt(FjernAnsattModel model) {
        if(model.getAnsatt_ID() == null)
            return false;
        if(model.getAnsatt_ID().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}

