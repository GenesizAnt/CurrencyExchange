package com.example.currencyservlet;

import com.example.currencyexchange.CurrencyDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerCommand extends HttpServlet {

    private final CurrencyDAO currencyDAO;

    public ControllerCommand() {
        this.currencyDAO = new CurrencyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String command = request.getParameter("command");

        if (command.equalsIgnoreCase("getAll")) {
//            currencyDAO.getAllCurrent();
            getServletContext().getRequestDispatcher("/showAllCurrent.jsp").forward(request, response);
        }
    }
}
