package ru.job4j.todo.filter;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.job4j.todo.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс-фильтр для авторизации пользователей {@link User}.
 * @author: Egor Bekhterev
 * @date: 10.03.2023
 * @project: job4j_todo
 */
@ThreadSafe
@Component
@Order(1)
public class AutorizationFilter extends HttpFilter {

    /**
     * Проверяет, что входящий адрес не является общедоступным.
     * @param uri - входящий адрес.
     * @return true - общедоступный адрес, false - закрытый адрес.
     */
    private boolean isAlwaysPermitted(String uri) {
        return uri.startsWith("/users/register") || uri.startsWith("/users/login");
    }

    /**
     * Проверяет, является ли адрес общедоступным. Если нет - проверяет, вошел ли {@link User} в систему.
     * Если пользователь не зашел, вызывается страница входа в систему. Наконец, если пользователь авторизован -
     * выполняется запрос. Вызывается следующий в цепочке фильтр.
     * @param request - запрос.
     * @param response - ответ.
     * @param chain - фильтр из цепочки фильтров.
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            var loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }
}
