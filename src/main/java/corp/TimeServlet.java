package corp;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Optional;

@WebServlet({"/time", "/time/"})
public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String timezone = req.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {
            timezone = getTimezoneFromCookies(req).orElse("UTC");
        } else {
            timezone = normalizeTimezone(timezone);
            saveTimezoneToCookies(resp, timezone);
        }

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(timezone);
        } catch (Exception e) {
            zoneId = ZoneId.of("UTC");
        }

        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Context context = new Context();
        context.setVariable("currentTime", currentTime.format(formatter));
        context.setVariable("timezone", zoneId);

        templateEngine.process("time", context, resp.getWriter());
    }

    private Optional<String> getTimezoneFromCookies(HttpServletRequest req) {
        if (req.getCookies() != null) {
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> "lastTimezone".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst();
        }

        return Optional.empty();
    }

    private void saveTimezoneToCookies(HttpServletResponse resp, String timezone) {
        Cookie cookie = new Cookie("lastTimezone", timezone);
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public String normalizeTimezone(String timezone) {
        return timezone.replace(" ", "+");
    }
}
