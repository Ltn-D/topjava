package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealsRepositoryImpl;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    MealsRepository repository = new InMemoryMealsRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward;
        String actions = req.getParameter("action");
        RequestDispatcher dispatcher;

        if (actions.equalsIgnoreCase("meals")) {
            log.debug("show all meals");
            forward = "meals.jsp";
            req.setAttribute("allMeals", MealsUtil.getAll(repository.findAll()));
            dispatcher = req.getRequestDispatcher(forward);
            dispatcher.forward(req, resp);
        } else if (actions.equalsIgnoreCase("edit")) {
            log.debug("try edit meal");
            forward = "meal.jsp";
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            dispatcher = req.getRequestDispatcher(forward);
            Meal meal = repository.findById(mealId);
            req.setAttribute("meal", meal);
            System.out.println(meal);
            dispatcher.forward(req, resp);
        } else if (actions.equalsIgnoreCase("create")) {
            log.debug("try create meal");
            forward = "meal.jsp";
            dispatcher = req.getRequestDispatcher(forward);
            Meal meal = new Meal();
            req.setAttribute("meal", meal);
            dispatcher.forward(req,resp);
        } else if (actions.equalsIgnoreCase("delete")) {
            log.debug("try delete meal");
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            repository.deleteByID(mealId);
            resp.sendRedirect("meals?action=meals");
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String id = req.getParameter("id");
        Integer mealId = null;
        if (!req.getParameter("id").isEmpty()) {
            mealId = Integer.parseInt(id);
        }
        Meal meal = new Meal(mealId, dateTime, req.getParameter("description"),Integer.parseInt(req.getParameter("calories")));
        repository.save(meal);
        resp.sendRedirect("meals?action=meals");
    }
}
