package bacit.web.bacit_web;
import bacit.web.bacit_models.FjerneUtstyrModel;
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
import java.sql.SQLException;

@WebServlet(name = "FjerneUtstyr", value = "/admin/fjerne-utstyr")
public class FjerneUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        SlettUtstyrInput(out, null);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        FjerneUtstyrModel Utstyr = new FjerneUtstyrModel();

        Utstyr.setUtstyr(request.getParameter("utstyrnavn"));
        PrintWriter out = response.getWriter();

        if (SjekkUtstyr(Utstyr)) {
            try {
                FjernUtstyr(Utstyr, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Utstyret har nå blitt fjernet");
            out.println("under ser du hvilket utstyr du som har blitt fjernet: <br>" +
                    "<br>Utstyr navn: " + Utstyr.getUtstyr());

            HtmlHelper.writeHtmlEnd(out);
        } else {
            SlettUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    public void FjernUtstyr(FjerneUtstyrModel Utstyr, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "Delete from Utstyr where Utstyr_Navn = ?;";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Utstyr.getUtstyr());


            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void SlettUtstyrInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Fjerne Utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-utstyr' method='POST'>");
        out.println("<br><br> <label for='utstyr navn'>Utstyr navn</label>");
        out.println("<input type='text' name='utstyrnavn' placeholder='Skriv inn navnet'/>");


        out.println("<br><br> <input type='submit' value='Fjern utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkUtstyr(FjerneUtstyrModel model) {
        if (model.getUtstyr() == null)
            return false;
        if (model.getUtstyr().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}

