package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealsRepository;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter FORMATTER_FOR_VIEW = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealsRepository repository;

    public void init(){
        repository = new InMemoryMealsRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String actions = req.getParameter("action");
        if (actions == null) {
            actions = "meals";
        }
        String forward;
        int id;
        Meal meal;
        RequestDispatcher dispatcher;
        switch (actions) {
            case "edit":
                log.debug("try edit meal");
                forward = "meal.jsp";
                id = Integer.parseInt(req.getParameter("id"));
                dispatcher = req.getRequestDispatcher(forward);
                meal = repository.findById(id);
                req.setAttribute("meal", meal);
                dispatcher.forward(req, resp);
                break;
            case "create":
                log.debug("try create meal");
                forward = "meal.jsp";
                dispatcher = req.getRequestDispatcher(forward);
                meal = new Meal();
                req.setAttribute("meal", meal);
                dispatcher.forward(req, resp);
                break;
            case "delete":
                log.debug("try delete meal");
                id = Integer.parseInt(req.getParameter("id"));
                repository.deleteById(id);
                resp.sendRedirect("meals");
                break;
            default:
                log.debug("show all meals");
                forward = "meals.jsp";
                req.setAttribute("FORMATTER", FORMATTER_FOR_VIEW);
                req.setAttribute("allMeals", MealsUtil.filteredByStreams(repository.findAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                dispatcher = req.getRequestDispatcher(forward);
                dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Integer mealId = null;
        if (!req.getParameter("id").isEmpty()) {
            mealId = Integer.parseInt(id);
        }
        Meal meal = new Meal(mealId, LocalDateTime.parse(req.getParameter("dateTime")), req.getParameter("description"),Integer.parseInt(req.getParameter("calories")));
        repository.save(meal);
        resp.sendRedirect("meals");
    }
}
