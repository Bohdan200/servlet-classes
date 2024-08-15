package corp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String timezone = req.getParameter("timezone");

        ZoneId zoneId = timezone != null ? ZoneId.of(normalizeTimezone(timezone)) : ZoneId.of("UTC");

        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Current UTC Time</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Current Time</h1>");
            out.println("<p>Time: " + currentTime.format(formatter) + "</p>");
            out.println("<p>Timezone: " + zoneId + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public String normalizeTimezone(String timezone) {
        return timezone.replace(" ", "+");
    }
}
