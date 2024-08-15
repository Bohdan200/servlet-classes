package corp;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import java.time.ZoneId;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone == null || isValidTimezone(normalizeTimezone(timezone))) {
            chain.doFilter(req, res);
        } else {
            res.setContentType("text/html;charset=UTF-8");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("<html><body><h1>Invalid timezone</h1></body></html>");
            res.getWriter().close();
        }
    }

    private boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String normalizeTimezone(String timezone) {
        return timezone.replace(" ", "+");
    }
}